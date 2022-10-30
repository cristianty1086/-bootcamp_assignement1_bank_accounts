package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface BankAccountService {

    // crear
    Mono<BankAccount> createBankAccount(BankAccount bankAccount);
    // leer
    Mono<BankAccount> readBankAccount(String bankAccountId);
    // cuentas bancarias por cliente
    Flux<BankAccount> readBankAccountByCostumer(BigInteger costumerId);
    // actualizar
    Mono<BankAccount> updateBankAccount(BankAccount bankAccount);
    // delete
    Mono<Void> deleteBankAccount(String bankAccountId);
    // leer todas
    Flux<BankAccount> listarTodos();
    Mono<Long> countAccountByType(BigInteger bankAccountId, Integer bankAccountType);
}
