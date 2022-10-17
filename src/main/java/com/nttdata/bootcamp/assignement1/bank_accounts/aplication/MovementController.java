package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("movement")
public class MovementController {
    @Autowired
    MovementService movementService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movement> createCostumer(@RequestBody Movement movement){
        System.out.println(movement);
        return movementService.createCostumer(Mono.just(movement));
    }

    @GetMapping(value = "get/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Movement> getCostumerById(@PathVariable("id") Integer id){
        return movementService.readCostumer(id);
    }

    @PutMapping(value = "update/{id}")
    @ResponseBody
    public Mono<Movement> updateCostumer(@RequestBody Movement movement){
        return movementService.updateCostumer(movement);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteCostumerById(@PathVariable("id") Integer id){
        return movementService.deleteCostumer(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Movement> listarTodos(){
        return movementService.listarTodos();
    }

}
