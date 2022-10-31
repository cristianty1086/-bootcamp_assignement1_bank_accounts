package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.BankAccountRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.DebitCardRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.*;
import com.nttdata.bootcamp.assignement1.bank_accounts.utilities.BuilderUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class DebitCardServiceImpl implements DebitCardService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    @Autowired
    DebitCardRepository debitCardRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Mono<DebitCard> createDebitCard(DebitCard debitCard) {
        LOGGER.info("Solicitud realizada para crear DebitCard");
        if( debitCard == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no envio ningun dato", null);
        }

        if( debitCard.getPrincipalBankAccountId() == null || debitCard.getPrincipalBankAccountId().isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "se requiere una cuenta bancaria asociada", null);
        }

        return debitCardRepository.save(debitCard);
    }

    @Override
    public Mono<DebitCard> readDebitCard(String debitCardId) {
        LOGGER.info("Solicitud realizada para obtener las cuentas bancarias de un cliente");
        return debitCardRepository.findById(debitCardId);
    }

    @Override
    public Mono<DebitCard> updateDebitCard(DebitCard debitCard) {
        LOGGER.info("Solicitud realizada para actualizar al BankAccount");
        return debitCardRepository.save(debitCard);
    }

    @Override
    public Mono<Void> deleteDebitCard(String debitCardId) {
        LOGGER.info("Solicitud realizada para crear BankAccount");
        return debitCardRepository.deleteById(debitCardId);
    }

    @Override
    public Flux<DebitCard> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los DebitCard");
        return debitCardRepository.findAll();
    }

    @Override
    public Mono<Double> getSaldo(String debitCardId) {
        // obtener la informacion de la tarjeta de debito
        String url1 = BuilderUrl.buildGetDebitCard(debitCardId);
        DebitCard debitCard = restTemplate.getForObject(url1, DebitCard.class);
        if(debitCard == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de la tarjeta de debito no encontrada", null);
        }
        if(debitCard.getPrincipalBankAccountId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de la tarjeta de debito no encontrada", null);
        }

        // obtener la informacion de la cuenta bancaria principal asociada
        String url2 = BuilderUrl.buildGetBankAccount(debitCard.getPrincipalBankAccountId());
        BankAccount bankAccount = restTemplate.getForObject(url2, BankAccount.class);

        if(bankAccount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de la cuenta bancaria asociada no encontrada", null);
        }

        // devolver el saldo de la cuenta
        return Mono.just(bankAccount.getBalance());
    }

    @Override
    public Mono<Void> makePaymentUse(String debitCardId, Double payment) {
        // obtener la informacion de la tarjeta de debito
        String url1 = BuilderUrl.buildGetDebitCard(debitCardId);
        DebitCard debitCard = restTemplate.getForObject(url1, DebitCard.class);
        if(debitCard == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de la tarjeta de debito no encontrada", null);
        }
        if(debitCard.getPrincipalBankAccountId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de la tarjeta de debito no encontrada", null);
        }

        // obtener la informacion de la cuenta bancaria principal asociada
        String url2 = BuilderUrl.buildCreateMovement();
        try{
            restTemplate.postForObject(url2, makeMovementPayment(debitCard.getPrincipalBankAccountId(), payment), Movement.class);
        } catch (ResponseStatusException ex) {
            LOGGER.info(ex.getMessage());
            // si no hay saldo en la cuenta principal, verificar en las cuentas
            // en el orden en que fueron asociadas
            if ( !makePaymentUsingAssociated(debitCardId, payment) ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo procesar el pago, no tiene fondos", null);
            }
        }

        return Mono.empty();
    }

    private Movement makeMovementPayment(String bankAccountId, Double payment) {
        Movement movement = new Movement();
        movement.setBankAccountId(bankAccountId);
        movement.setAmount(payment);
        movement.setMovementType(MovementType.withdrawal);
        return movement;
    }

    private boolean makePaymentUsingAssociated(String debitCardId, Double payment) {
        String url3 = BuilderUrl.buildGetAssociateBankAccount(debitCardId);
        AssociateBankAccount[] associatesBankAccount = restTemplate.getForObject(url3, AssociateBankAccount[].class);

        if(associatesBankAccount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo procesar el pago, no tiene fondos en la cuenta principal", null);
        }
        boolean[] isPaymentProcessed = {false};
        Arrays.stream(associatesBankAccount)
                .sorted((a1,a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()))
                .forEach(a -> {
                    // si proceso el pago, ya no procesar en las demas cuentas bancarias
                    if (isPaymentProcessed[0]) {
                        return;
                    }
                    // Obtener la cuenta bancaria
                    String url2 = BuilderUrl.buildCreateMovement();
                    try{
                        restTemplate.postForObject(url2,
                                makeMovementPayment(a.getBankAccountId(), payment),
                                Movement.class);
                        isPaymentProcessed[0] = true;
                    } catch (ResponseStatusException ex) {
                        LOGGER.info(ex.getMessage());
                    }
                });
        return isPaymentProcessed[0];
    }

    @Override
    public Mono<Void> makePaymentThirdDebitCard(String debitCardId, Double payment, String bankAccountId) {
        String url1 = BuilderUrl.buildGetDebitCard(debitCardId);
        DebitCard debitCard = restTemplate.getForObject(url1, DebitCard.class);
        if(debitCard == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de la tarjeta de debito no encontrada", null);
        }
        if(debitCard.getPrincipalBankAccountId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de la tarjeta de debito no encontrada", null);
        }

        // obtener la informacion de la cuenta bancaria principal asociada
        String url2 = BuilderUrl.buildGetBankAccount(debitCard.getPrincipalBankAccountId());
        BankAccount bankAccount = restTemplate.getForObject(url2, BankAccount.class);

        // obtener la informacion de la cuenta bancaria principal asociada
        String url3 = BuilderUrl.buildGetBankAccount(bankAccountId);
        BankAccount bankAccount2 = restTemplate.getForObject(url3, BankAccount.class);

        String url4 = BuilderUrl.buildCreateMovement();
        Movement movement = new Movement();
        movement.setBankAccountId(bankAccount2.getId());
        movement.setAmount(payment);
        movement.setMovementType(MovementType.withdrawal);
        try{
            restTemplate.postForObject(url4, movement, Movement.class);
        } catch (ResponseStatusException ex) {
            LOGGER.info(ex.getMessage());
        }

        String url5 = BuilderUrl.buildCreateMovement();
        Movement movement2 = new Movement();
        movement2.setBankAccountId(bankAccount.getId());
        movement2.setAmount(payment);
        movement2.setMovementType(MovementType.deposit);
        try{
            restTemplate.postForObject(url5, movement2, Movement.class);
        } catch (ResponseStatusException ex) {
            LOGGER.info(ex.getMessage());
        }

        return Mono.empty();
    }
}
