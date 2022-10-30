package com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccountType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {
    public Flux<BankAccount> findByCostumerId(BigInteger costumerId);

    public Mono<Long> countByCostumerIdAndBankAccountType(BigInteger costumerId, BankAccountType bankAccountType);
}