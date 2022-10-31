package com.nttdata.bootcamp.assignement1.bank_accounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "associate_bank_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociateBankAccount {
    @Id
    String id;
    String bankAccountId;
    String debitCardId;
    BigInteger createdAt;
}
