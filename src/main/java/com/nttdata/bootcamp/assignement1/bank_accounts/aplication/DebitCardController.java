package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.DebitCard;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * DebitCardController define los endpoints
 * para las tarjetas de debito
 * @author Cristian TY / Nttdata
 */
@RestController
@RequestMapping("debit_card")
public class DebitCardController {
    @Autowired
    DebitCardService debitCardService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DebitCard> createMovement(@RequestBody DebitCard debitCard){
        System.out.println(debitCard);
        return debitCardService.createDebitCard(debitCard);
    }

    @GetMapping(value = "get/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<DebitCard> getMovementById(@PathVariable("id") String id){
        return debitCardService.readDebitCard(id);
    }

    @PutMapping(value = "update")
    @ResponseBody
    public Mono<DebitCard> updateMovement(@RequestBody DebitCard debitCard){
        return debitCardService.updateDebitCard(debitCard);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteMovementById(@PathVariable("id") String id){
        return debitCardService.deleteDebitCard(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<DebitCard> listarTodos(){
        return debitCardService.listarTodos();
    }

    @GetMapping(value = "get_saldo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Double> getSaldo(@PathVariable("id") String id){
        return debitCardService.getSaldo(id);
    }

}
