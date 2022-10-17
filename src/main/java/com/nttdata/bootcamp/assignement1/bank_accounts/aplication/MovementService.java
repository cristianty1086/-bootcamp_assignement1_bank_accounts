package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

    // crear
    Mono<Movement> createCostumer(Mono<Movement> movement);
    // leer
    Mono<Movement> readCostumer(Integer movementId);
    // actualizar
    Mono<Movement> updateCostumer(Movement movement);
    // delete
    Mono<Void> deleteCostumer(Integer movementId);
    // leer todas
    Flux<Movement> listarTodos();
}
