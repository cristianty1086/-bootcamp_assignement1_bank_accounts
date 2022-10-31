package com.nttdata.bootcamp.assignement1.bank_accounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "debit_card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {
    @Id
    String id;
    String cardNumber;
    String expireMonth;
    String expireYear;
    String ccv;
    String principalBankAccountId;
}
