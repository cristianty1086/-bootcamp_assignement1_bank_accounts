package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.DebitCard;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DebitCardService {
    // crear
    Mono<DebitCard> createDebitCard(DebitCard debitCard);
    // leer
    Mono<DebitCard> readDebitCard(String debitCardId);
    // actualizar
    Mono<DebitCard> updateDebitCard(DebitCard debitCard);
    // delete
    Mono<Void> deleteDebitCard(String debitCardId);
    // leer todas
    Flux<DebitCard> listarTodos();
    // devuelve el saldo de la cuenta bancaria asociada
    Mono<Double> getSaldo(String debitCardId);
    // registra pagos usando la tarjeta de debito
    Mono<Void> makePaymentUse(String debitCardId, Double payment);
    // bankAccountId hace el pago de la tarjeta de debito debitCardId
    Mono<Void> makePaymentThirdDebitCard(String debitCardId, Double payment, String bankAccountId);
}
