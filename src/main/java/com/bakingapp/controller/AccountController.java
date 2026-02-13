package com.bakingapp.controller;

import com.bakingapp.dto.AccountDto;
import com.bakingapp.dto.FundTransferDto;
import com.bakingapp.dto.TransactionDto;
import com.bakingapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        AccountDto savedAccount = accountService.createAccount(accountDto);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("{id}/deposit")
    public ResponseEntity<AccountDto> depositAmount(@PathVariable Long id, @RequestBody Map<String, Double>request){
        AccountDto updatedAccount = accountService.depositAmount(id, request.get("balance"));
        return ResponseEntity.ok(updatedAccount);
    }

    @PutMapping("{id}/withdraw")
    public ResponseEntity<AccountDto> withdrawAmount(@PathVariable Long id, @RequestBody Map<String, Double>request){
        AccountDto updatedAccount = accountService.withdrawAmount(id, request.get("balance"));
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping()
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accountDtos = accountService.getAllAccounts();
        return ResponseEntity.ok(accountDtos);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long id){
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Account Deleted Successfully");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> fundTransfer(@RequestBody FundTransferDto fundTransferDto){
        accountService.fundTransfer(fundTransferDto);
        return ResponseEntity.ok("Fund Transfer Successfully");
    }

    @GetMapping("{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransaction(@PathVariable("id") Long accountId){
        List<TransactionDto> transactions = accountService.getAllTransaction(accountId);
        return ResponseEntity.ok(transactions);
    }

}
