package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.BankAccountRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccountType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Override
    public Mono<BankAccount> createCostumer(Mono<BankAccount> bankAccount) {
        LOGGER.info("Solicitud realizada para crear BankAccount");
        if( bankAccount == null ) {
            return null;
        }
        Optional<BankAccount> bankAccount1 = bankAccount.blockOptional();
        if( !bankAccount1.isPresent() ) {
            LOGGER.error("Error, datos enviados incompletos");
            return null;
        }

        BankAccount _bankAccount = bankAccount1.get();

        if( _bankAccount.getCostumerType().equals("person") ) {
            // TODO el cliente personal solo puede tener un maximo de una cuenta de ahorro,
            // una cuenta corriente o cuentas a plazo fijo.
            if( _bankAccount.getBankAccountType() == BankAccountType.current_account ) {
            } else if( _bankAccount.getBankAccountType() == BankAccountType.saving_account ) {
            } else if( _bankAccount.getBankAccountType() == BankAccountType.fixed_term_account ) {
            }
        }

        if( _bankAccount.getCostumerType().equals("enterprise") ) {
            // TODO el cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo
            // pero si multiples cuentas corrientes
        }

        if( _bankAccount.getBankAccountType() == BankAccountType.current_account ) {
            _bankAccount.setTaxMaintenance(0.0d);
            _bankAccount.setMaxMonthMovement(30);// maximo 30 movimientos como ejemplo
        } else if( _bankAccount.getBankAccountType() == BankAccountType.saving_account ) {
            _bankAccount.setTaxMaintenance(0.04d);// por ejemplo 4%
            _bankAccount.setMaxMonthMovement(-1);// sin limite
        } else if( _bankAccount.getBankAccountType() == BankAccountType.fixed_term_account ) {
            _bankAccount.setTaxMaintenance(0.0d);
            _bankAccount.setMaxMonthMovement(1);
        } else {
            LOGGER.error("Error, datos enviados incompletos");
            return null;
        }
        return bankAccountRepository.save(_bankAccount);
    }

    @Override
    public Mono<BankAccount> readCostumer(Integer bankAccountId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un BankAccount");
        return bankAccountRepository.findById(bankAccountId);
    }

    @Override
    public Mono<BankAccount> updateCostumer(BankAccount bankAccount) {
        LOGGER.info("Solicitud realizada para actualizar al BankAccount");
        bankAccountRepository.findById(bankAccount.getId())
                .map( currBankAccount -> {
                    LOGGER.info("Cliente encontrado para el id: " + bankAccount.getId());
                    currBankAccount.setAccountNumber(bankAccount.getAccountNumber());
                    currBankAccount.setCciNumber(bankAccount.getCciNumber());
                    currBankAccount.setBankAccountType(bankAccount.getBankAccountType());
                    currBankAccount.setHeadlines(bankAccount.getHeadlines());
                    currBankAccount.setSignatories(bankAccount.getSignatories());
                    return bankAccountRepository.save(currBankAccount);
                });

        return Mono.just(bankAccount);
    }

    @Override
    public Mono<Void> deleteCostumer(Integer bankAccountId) {
        LOGGER.info("Solicitud realizada para crear BankAccount");
        bankAccountRepository.deleteById(bankAccountId);
        return null;
    }

    @Override
    public Flux<BankAccount> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los BankAccount");
        return bankAccountRepository.findAll();
    }
}
