package com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, Integer> {


}