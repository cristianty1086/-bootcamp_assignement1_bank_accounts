package com.nttdata.bootcamp.assignement1.bank_accounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

/**
 * Maneja la informacion del cliente
 * bankAccountType indica el tipo de cliente:
 * 1=ahorro, 2=cuenta corriente, 3=plazo fijo
 */
@Document(collection = "bank_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    String id;
    String accountNumber;
    String cciNumber;
    double taxMaintenance;
    int maxMonthMovement;
    double minimalOpenAmount;
    String costumerType;
    BigInteger costumerId;
    BankAccountType bankAccountType;
    // lista de los costumerId, titulares de la cuenta bancaria
    List<Integer> headlines;
    // lista de los costumerId, firmantes de la cuenta bancaria
    List<Integer> signatories;
    int maxOperationWhithoutComision;
    double taxComision;
    double balance;
    String createdAt;
}
