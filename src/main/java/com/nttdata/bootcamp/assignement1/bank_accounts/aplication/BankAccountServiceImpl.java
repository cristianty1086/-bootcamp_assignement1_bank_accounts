package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.BankAccountRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Override
    public Mono<BankAccount> createCostumer(Mono<BankAccount> bankAccount) {
        LOGGER.info("Solicitud realizada para crear BankAccount");
        return bankAccount.flatMap(bankAccountRepository::insert);
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
