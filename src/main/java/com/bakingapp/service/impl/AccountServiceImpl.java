package com.bakingapp.service.impl;

import com.bakingapp.dto.AccountDto;
import com.bakingapp.dto.FundTransferDto;
import com.bakingapp.dto.TransactionDto;
import com.bakingapp.entity.Account;
import com.bakingapp.entity.Transaction;
import com.bakingapp.exception.AccountException;
import com.bakingapp.mapper.AccountMapper;
import com.bakingapp.repository.AccountRepository;
import com.bakingapp.repository.TransactionRepository;
import com.bakingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto depositAmount(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new AccountException("Account does not exist"));
        double total = amount + account.getBalance();
        account.setBalance(total);
        Account updatedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDto withdrawAmount(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new AccountException("Account does not exist"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient Balance");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account updatedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new AccountException("Account does not exist"));
        accountRepository.deleteById(id);
    }

    @Override
    public void fundTransfer(FundTransferDto fundTransferDto) {
        Account toAccount = accountRepository.findById(fundTransferDto.toAccount())
                .orElseThrow(()->new RuntimeException("Receivers Account does not exist"));

        Account fromAccount = accountRepository.findById(fundTransferDto.fromAccount())
                .orElseThrow(()->new RuntimeException("Senders Account does not exist"));

        toAccount.setBalance(toAccount.getBalance() + fundTransferDto.balance());
        accountRepository.save(toAccount);

        fromAccount.setBalance(fromAccount.getBalance() - fundTransferDto.balance());
        accountRepository.save(fromAccount);

        Transaction transactionDebit = new Transaction();
        transactionDebit.setAccountId(fundTransferDto.fromAccount());
        transactionDebit.setAmount(fundTransferDto.balance());
        transactionDebit.setTransactionType("TRANSFER_DEBIT");
        transactionDebit.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transactionDebit);

        Transaction transactionCredit = new Transaction();
        transactionCredit.setAccountId(fundTransferDto.toAccount());
        transactionCredit.setAmount(fundTransferDto.balance());
        transactionCredit.setTransactionType("TRANSFER_CREDIT");
        transactionCredit.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transactionCredit);
    }

    @Override
    public List<TransactionDto> getAllTransaction(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
        return transactions.stream()
                .map((transaction)->mapToTransactionDto(transaction)).collect(Collectors.toList());
    }

    private TransactionDto mapToTransactionDto(Transaction transaction){
        return new TransactionDto(transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp());
    }

}
