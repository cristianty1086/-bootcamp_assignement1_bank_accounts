package com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccountType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, Integer> {
    public Flux<BankAccount> findByCostumerId(Integer costumerId);

    public Mono<Long> countByCostumerIdAndBankAccountType(Integer costumerId, BankAccountType bankAccountType);
}