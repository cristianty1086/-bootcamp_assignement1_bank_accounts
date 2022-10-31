package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.AssociateBankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("associate_bank_account")
public class AssociateBankAccountController {
    @Autowired
    AssociateBankAccountService associateBankAccountService;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AssociateBankAccount> createAssociateBankAccount(@RequestBody AssociateBankAccount associateBankAccount){
        System.out.println(associateBankAccount);
        return associateBankAccountService.createAssociateBankAccount(associateBankAccount);
    }

    @GetMapping(value = "get/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<AssociateBankAccount> getAssociateBankAccountById(@PathVariable("id") String id){
        return associateBankAccountService.readAssociateBankAccount(id);
    }

    @PutMapping(value = "update")
    @ResponseBody
    public Mono<AssociateBankAccount> updateAssociateBankAccount(@RequestBody AssociateBankAccount associateBankAccount){
        return associateBankAccountService.updateAssociateBankAccount(associateBankAccount);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Mono<Void> deleteAssociateBankAccountById(@PathVariable("id") String id){
        return associateBankAccountService.deleteAssociateBankAccount(id);
    }

    @GetMapping(value = "getAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<AssociateBankAccount> listarTodos(){
        return associateBankAccountService.listarTodos();
    }

    @GetMapping(value = "get_by_bankaccount/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<AssociateBankAccount> getByBankAccountId(@PathVariable("id") String id){
        return associateBankAccountService.getByBankAccountId(id);
    }


    @GetMapping(value = "get_by_debitcard/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Flux<AssociateBankAccount> getByDebitCardId(@PathVariable("id") String id){
        return associateBankAccountService.getByDebitCardId(id);
    }


}
