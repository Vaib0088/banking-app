package com.bakingapp.dto;

public record FundTransferDto(Long fromAccount,
                              Long toAccount,
                              double balance) {
}
