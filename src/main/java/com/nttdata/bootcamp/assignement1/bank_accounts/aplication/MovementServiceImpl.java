package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.MovementRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.DebitCard;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.MovementType;
import com.nttdata.bootcamp.assignement1.bank_accounts.utilities.BuilderUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MovementServiceImpl implements MovementService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(MovementServiceImpl.class);

    @Autowired
    MovementRepository movementRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<Movement> createMovement(Movement movement) {
        LOGGER.info("Solicitud realizada para crear Movement");
        if( movement == null ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Envie datos del movimiento", null);
        }
        if( movement.getBankAccountId() == null || movement.getBankAccountId().isEmpty() ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Envie la cuenta bancaria asociada", null);
        }
        if( movement.getMovementType() == null ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Envie el tipo de movimiento", null);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        movement.setRegisterDate(dtf.format(localDateTime));

        String url = BuilderUrl.buildGetBankAccount(movement.getBankAccountId());
        BankAccount bankAccount = restTemplate.getForObject(url, BankAccount.class);
        if( bankAccount == null ) {
            LOGGER.error("Error: credito no encontrado");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: credito no encontrado", null);
        }
        double salida = 0.0;
        if (movement.getMovementType() == MovementType.deposit) {
            salida = bankAccount.getBalance() + movement.getAmount();
        } else if (movement.getMovementType() == MovementType.withdrawal) {
            salida = bankAccount.getBalance() - movement.getAmount();
            if(  salida < 0 ) {
                LOGGER.error("Error, el movimiento de retiro es mayor al disponible en su cuenta");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, el movimiento de retiro es mayor al disponible en su cuenta", null);
            }
        }

        // actualizar el saldo
        bankAccount.setBalance( salida );
        String url2 = BuilderUrl.buildUpdateBankAccount();
        restTemplate.put(url2, bankAccount);

        return movementRepository.save(movement);
    }

    @Override
    public Mono<Movement> readMovement(String movementId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un Movement");
        return movementRepository.findById(movementId);
    }

    @Override
    public Mono<Movement> updateMovement(Movement movement) {
        LOGGER.info("Solicitud realizada para actualizar al Movement");
        return movementRepository.save(movement);
    }

    @Override
    public Mono<Void> deleteMovement(String movementId) {
        LOGGER.info("Solicitud realizada para crear Movement");
        return movementRepository.deleteById(movementId);
    }

    @Override
    public Flux<Movement> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los Movement");
        return movementRepository.findAll();
    }

    @Override
    public Flux<Movement> getLastTen(String debitCardId) {
        LOGGER.info("Solicitud realizada para el envio de los ultimos 10");

        String url = BuilderUrl.buildGetDebitCard(debitCardId);
        DebitCard debitCard = restTemplate.getForObject(url, DebitCard.class);
        if ( debitCard == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, tarjeta de debito no encontrado", null);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return movementRepository.findByBankAccountId(debitCard.getPrincipalBankAccountId())
                .sort((a1,a2) -> {
                    LocalDateTime d1 = LocalDateTime.parse(a1.getRegisterDate(), dtf);
                    LocalDateTime d2 = LocalDateTime.parse(a2.getRegisterDate(), dtf);
                    return -1*d1.compareTo(d2);
                })
                .limitRate(10);
    }
}
