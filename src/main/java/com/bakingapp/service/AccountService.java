package com.bakingapp.service;

import com.bakingapp.dto.AccountDto;
import com.bakingapp.dto.FundTransferDto;
import com.bakingapp.dto.TransactionDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto depositAmount(Long id, Double amount);

    AccountDto withdrawAmount(Long id, Double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccountById(Long id);

    void fundTransfer(FundTransferDto fundTransferDto);

    List<TransactionDto> getAllTransaction(Long accountId);
}
