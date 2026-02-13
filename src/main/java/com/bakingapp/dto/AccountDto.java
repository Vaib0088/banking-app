package com.bakingapp.dto;

import lombok.AllArgsConstructor;
/*import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private double balance;
}*/

public record AccountDto(Long id,
                         String accountHolderName,
                         double balance) {
}
