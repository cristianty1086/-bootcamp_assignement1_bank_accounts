package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {

    // crear
    Mono<BankAccount> createBankAccount(BankAccount bankAccount);
    // leer
    Mono<BankAccount> readBankAccount(Integer bankAccountId);
    // actualizar
    Mono<BankAccount> updateBankAccount(BankAccount bankAccount);
    // delete
    Mono<Void> deleteBankAccount(Integer bankAccountId);
    // leer todas
    Flux<BankAccount> listarTodos();
    Mono<Long> countAccountByType(Integer bankAccountId, Integer bankAccountType);
}
