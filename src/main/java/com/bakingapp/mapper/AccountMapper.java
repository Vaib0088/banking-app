package com.bakingapp.mapper;

import com.bakingapp.dto.AccountDto;
import com.bakingapp.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(
                accountDto.id(),
                accountDto.accountHolderName(),
                accountDto.balance()
        );
        return account;
    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }
}
