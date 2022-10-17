package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bank_account")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount){
        return bankAccountService.createCostumer(Mono.just(bankAccount));
    }

    @GetMapping(value = "get/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<BankAccount> getBankAccountById(@PathVariable("id") Integer id){
        return bankAccountService.readCostumer(id);
    }

    @PutMapping(value = "update/{id}")
    @ResponseBody
    public Mono<BankAccount> updateBankAccount(@RequestBody BankAccount bankAccount){
        return bankAccountService.updateCostumer(bankAccount);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteBankAccountById(@PathVariable("id") Integer id){
        return bankAccountService.deleteCostumer(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<BankAccount> listarTodos(){
        return bankAccountService.listarTodos();
    }

}
