package com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.AssociateBankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.DebitCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

@Repository
public interface AssociateBankAccountRepository extends ReactiveMongoRepository<AssociateBankAccount, String> {

    Flux<AssociateBankAccount> findByBankAccountId(String bankAccountId);
    Flux<AssociateBankAccount> findByDebitCardId(String debitCardId);
}
