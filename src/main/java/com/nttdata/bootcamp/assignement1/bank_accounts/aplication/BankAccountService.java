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

    /**
     * Devuelve el numero de cuentas bancarias que tiene un cliente
     * @param costumerId
     * @param bankAccountType
     * @return
     */
    Mono<Long> countAccountByType(BigInteger costumerId, Integer bankAccountType);
    // leer todas en un intervalo de tiempo
    Flux<BankAccount> listarTodosBeetween(String dateInit, String dateEnd);
}
