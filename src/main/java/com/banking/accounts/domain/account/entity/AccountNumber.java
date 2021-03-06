package com.banking.accounts.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AccountNumber {

    @NotEmpty
    public String number;

    public AccountNumber(EnumAccountType enumAccountType) {
        if ( enumAccountType == EnumAccountType.CurrentAccount)
            this.number = "00" + UUID.randomUUID().toString().substring(0,8);
        else
            this.number = "11" + UUID.randomUUID().toString().substring(0,8);
    }

}
