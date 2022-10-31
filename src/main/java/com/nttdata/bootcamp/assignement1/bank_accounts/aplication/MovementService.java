package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

    // crear
    Mono<Movement> createMovement(Movement movement);
    // leer
    Mono<Movement> readMovement(String movementId);
    // actualizar
    Mono<Movement> updateMovement(Movement movement);
    // delete
    Mono<Void> deleteMovement(String movementId);
    // leer todas
    Flux<Movement> listarTodos();
    Flux<Movement> getLastTen(String debitCardId);
}
