package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {

    // crear
    Mono<BankAccount> createCostumer(Mono<BankAccount> bankAccount);
    // leer
    Mono<BankAccount> readCostumer(Integer bankAccountId);
    // actualizar
    Mono<BankAccount> updateCostumer(BankAccount bankAccount);
    // delete
    Mono<Void> deleteCostumer(Integer bankAccountId);
    // leer todas
    Flux<BankAccount> listarTodos();
}
