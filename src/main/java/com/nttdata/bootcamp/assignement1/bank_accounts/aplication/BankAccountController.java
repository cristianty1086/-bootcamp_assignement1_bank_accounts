package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;


@RestController
@RequestMapping("bank_account")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount){
        return bankAccountService.createBankAccount(bankAccount);
    }

    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<BankAccount> getBankAccountById(@PathVariable("id") String id){
        return bankAccountService.readBankAccount(id);
    }

    @GetMapping(value = "get_by_costumer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<BankAccount> getCostumerId(@PathVariable("id") BigInteger id){
        return bankAccountService.readBankAccountByCostumer(id);
    }

    @PutMapping(value = "update")
    @ResponseBody
    public Mono<BankAccount> updateBankAccount(@RequestBody BankAccount bankAccount){
        return bankAccountService.updateBankAccount(bankAccount);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteBankAccountById(@PathVariable("id") String id){
        return bankAccountService.deleteBankAccount(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<BankAccount> listarTodos(){
        return bankAccountService.listarTodos();
    }

    @GetMapping(value = "count_accounts_by_type/{id}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Long> countAccountByType(@PathVariable("id") BigInteger id, @PathVariable("type") Integer type){
        return bankAccountService.countAccountByType(id, type);
    }

}
