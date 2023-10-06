package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * DebitCardController define los endpoints
 * para los movimientos
 * @author Cristian TY / Nttdata
 */
@RestController
@RequestMapping("movement")
public class MovementController {
    @Autowired
    MovementService movementService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movement> createMovement(@RequestBody Movement movement){
        System.out.println(movement);
        return movementService.createMovement(movement);
    }

    @GetMapping(value = "get/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Movement> getMovementById(@PathVariable("id") String id){
        return movementService.readMovement(id);
    }

    @PutMapping(value = "update")
    @ResponseBody
    public Mono<Movement> updateMovement(@RequestBody Movement movement){
        return movementService.updateMovement(movement);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteMovementById(@PathVariable("id") String id){
        return movementService.deleteMovement(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Movement> listarTodos(){
        return movementService.listarTodos();
    }

    @GetMapping(value = "get_last_ten_movements/{debitCardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<Movement> getLastTenMovements(@PathVariable("debitCardId") String debitCardId){
        return movementService.getLastTen(debitCardId);
    }
}
