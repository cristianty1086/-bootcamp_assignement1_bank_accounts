package com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends ReactiveMongoRepository<Movement, Integer> {

}
