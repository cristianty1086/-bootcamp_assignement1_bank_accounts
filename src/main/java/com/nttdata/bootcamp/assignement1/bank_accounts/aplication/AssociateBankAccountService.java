package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.AssociateBankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssociateBankAccountService {
    // crear
    Mono<AssociateBankAccount> createAssociateBankAccount(AssociateBankAccount associateBankAccount);
    // leer
    Mono<AssociateBankAccount> readAssociateBankAccount(String associateBankAccountId);
    // actualizar
    Mono<AssociateBankAccount> updateAssociateBankAccount(AssociateBankAccount associateBankAccount);
    // delete
    Mono<Void> deleteAssociateBankAccount(String associateBankAccountId);
    // leer todas
    Flux<AssociateBankAccount> listarTodos();
    Flux<AssociateBankAccount> getByBankAccountId(String bankAccountId);
    Flux<AssociateBankAccount> getByDebitCardId(String debitCardId);
}
