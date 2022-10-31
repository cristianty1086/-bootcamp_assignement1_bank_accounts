package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.AssociateBankAccountRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.DebitCardRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.AssociateBankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.DebitCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AssociateBankAccountServiceImpl implements AssociateBankAccountService {
    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(AssociateBankAccountServiceImpl.class);
    @Autowired
    AssociateBankAccountRepository associateBankAccountRepository;

    @Override
    public Mono<AssociateBankAccount> createAssociateBankAccount(AssociateBankAccount associateBankAccount) {
        LOGGER.info("Solicitud realizada para asociar una tarjeta de debito a una cuenta bancaria");
        if( associateBankAccount == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no envio ningun dato", null);
        }

        if( associateBankAccount.getBankAccountId() == null || associateBankAccount.getBankAccountId().isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "se requiere una cuenta bancaria asociada", null);
        }

        if( associateBankAccount.getDebitCardId() == null || associateBankAccount.getDebitCardId().isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "se requiere una cuenta bancaria asociada", null);
        }


        return associateBankAccountRepository.save(associateBankAccount);
    }

    @Override
    public Mono<AssociateBankAccount> readAssociateBankAccount(String associateBankAccountId) {
        LOGGER.info("Solicitud realizada para obtener las cuentas bancarias de un cliente");
        return associateBankAccountRepository.findById(associateBankAccountId);
    }

    @Override
    public Mono<AssociateBankAccount> updateAssociateBankAccount(AssociateBankAccount associateBankAccount) {
        LOGGER.info("Solicitud realizada para actualizar al BankAccount");
        return associateBankAccountRepository.save(associateBankAccount);
    }

    @Override
    public Mono<Void> deleteAssociateBankAccount(String associateBankAccount) {
        LOGGER.info("Solicitud realizada para crear BankAccount");
        return associateBankAccountRepository.deleteById(associateBankAccount);
    }

    @Override
    public Flux<AssociateBankAccount> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los DebitCard");
        return associateBankAccountRepository.findAll();
    }

    @Override
    public Flux<AssociateBankAccount> getByBankAccountId(String bankAccountId) {
        LOGGER.info("Solicitud realizada para el envio de todos los DebitCard");
        return associateBankAccountRepository.findByBankAccountId(bankAccountId);
    }

    @Override
    public Flux<AssociateBankAccount> getByDebitCardId(String debitCardId) {
        LOGGER.info("Solicitud realizada para el envio de todos los DebitCard");
        return associateBankAccountRepository.findByDebitCardId(debitCardId);
    }
}
