package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.MovementRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(MovementServiceImpl.class);

    @Autowired
    MovementRepository movementRepository;

    @Override
    public Mono<Movement> createCostumer(Mono<Movement> movement) {
        LOGGER.info("Solicitud realizada para crear Movement");
        return movement.flatMap(movementRepository::insert);
    }

    @Override
    public Mono<Movement> readCostumer(Integer movementId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un Movement");
        return movementRepository.findById(movementId);
    }

    @Override
    public Mono<Movement> updateCostumer(Movement movement) {
        LOGGER.info("Solicitud realizada para actualizar al Movement");
        movementRepository.findById(movement.getId())
                .map( currMovement -> {
                    LOGGER.info("Cliente encontrado para el id: " + movement.getId());
                    currMovement.setMovementType(movement.getMovementType());
                    currMovement.setAmount(movement.getAmount());
                    currMovement.setBankAccountId(movement.getBankAccountId());
                    return movementRepository.save(currMovement);
                });

        return Mono.just(movement);
    }

    @Override
    public Mono<Void> deleteCostumer(Integer movementId) {
        LOGGER.info("Solicitud realizada para crear Movement");
        movementRepository.deleteById(movementId);
        return null;
    }

    @Override
    public Flux<Movement> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los Movement");
        return movementRepository.findAll();
    }
}
