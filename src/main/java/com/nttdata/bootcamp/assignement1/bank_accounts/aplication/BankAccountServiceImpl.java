package com.nttdata.bootcamp.assignement1.bank_accounts.aplication;

import com.nttdata.bootcamp.assignement1.bank_accounts.infraestructure.BankAccountRepository;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccount;
import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccountType;
import com.nttdata.bootcamp.assignement1.bank_accounts.utilities.AppConstants;
import com.nttdata.bootcamp.assignement1.bank_accounts.utilities.BuilderUrl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("testerror")
public class BankAccountServiceImpl implements BankAccountService {

    // LogBack
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<BankAccount> createBankAccount(BankAccount bankAccount) {
        LOGGER.info("Solicitud realizada para crear BankAccount");
        if( bankAccount == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "no envio ningun dato", null);
        }

        if(  bankAccount.getHeadlines() == null || bankAccount.getHeadlines().size() == 0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Indique el(los) cliente(s) titular(es) de la cuenta", null);
        }

        if(  bankAccount.getBankAccountType() == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Indique el tipo de cuenta", null);
        }

        //a

        // Monto minimo de apertura igual a cero
        if( bankAccount.getMinimalOpenAmount() < 0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Monto minimo de apertura es de cero", null);
        }

        // maximo numero de operaciones al mes sin comision
        bankAccount.setMaxOperationWhithoutComision(50); // prueba 50
        bankAccount.setTaxComision(0.02); // por ejemplo 2%

        // considerando tasas y maximo de movimientos al mes segun tipo de cuenta
        if( bankAccount.getBankAccountType() == BankAccountType.current_account ) {
            bankAccount.setTaxMaintenance(0.0d);
            bankAccount.setMaxMonthMovement(30);// maximo 30 movimientos como ejemplo
        } else if( bankAccount.getBankAccountType() == BankAccountType.saving_account ) {
            bankAccount.setTaxMaintenance(0.04d);// por ejemplo 4%
            bankAccount.setMaxMonthMovement(-1);// sin limite
        } else if( bankAccount.getBankAccountType() == BankAccountType.fixed_term_account ) {
            bankAccount.setTaxMaintenance(0.0d);
            bankAccount.setMaxMonthMovement(1);
        }

        if( bankAccount.getCostumerType().equals("person") ) {
            // el cliente personal solo puede tener un maximo de una cuenta de ahorro,
            // una cuenta corriente o cuentas a plazo fijo.
            String url = BuilderUrl.buildCountAccountsByType(bankAccount.getCostumerId(),
                    bankAccount.getBankAccountType());
            RestTemplate restTemplate = new RestTemplate();
            Long count = restTemplate.getForObject(url, Long.class);

            if( count == null ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: al obtener el numero de cuentas del cliente por tipo de cuenta. Contactese con el soporte tecnico.", null);
            }
            if(count > 0) {
                if(bankAccount.getBankAccountType() == BankAccountType.saving_account) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permite una cuenta de ahorro como maximo", null);
                }
                if(bankAccount.getBankAccountType() == BankAccountType.fixed_term_account) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permite una cuenta de plazo fijo como maximo", null);
                }
                if(bankAccount.getBankAccountType() == BankAccountType.current_account) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permite una cuenta corriente como maximo", null);
                }
            }
        }

        if( bankAccount.getCostumerType().equals("enterprise") ) {
            // el cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo
            // pero si multiples cuentas corrientes
            if(bankAccount.getBankAccountType() == BankAccountType.saving_account) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Una cuenta empresarial no puede tener cuenta de ahorro", null);
            }
            if(bankAccount.getBankAccountType() == BankAccountType.fixed_term_account) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Una cuenta empresarial no puede tener cuenta de plazo fijo", null);
            }
        }

        // buscar entre las cuentas, cuantos tienen deuda pendiente
        String url = BuilderUrl.buildGetCreditsByCostumerId(bankAccount.getCostumerId());
        String strBankAccount = restTemplate.getForObject(url, String.class);
        JSONArray jsonArray = new JSONArray(strBankAccount);
        int[] cuentas_pendientes = {0};
        jsonArray.forEach(it -> {
            JSONObject jsonObject = (JSONObject) it;
            String nextDatePayment = jsonObject.getString("nextDatePayment");
            if( nextDatePayment == null || nextDatePayment.isEmpty() ) {
                LOGGER.warn("Sin informacion de fecha de pago siguiente");
            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDateTime d1 = LocalDateTime.parse(nextDatePayment, dtf);
                LocalDateTime d2 = LocalDateTime.now();
                if(d1.isBefore(d2)) {
                    LOGGER.info("Tiene una deuda pendiente");
                    cuentas_pendientes[0] += 1;
                }
            }
        });

        if( cuentas_pendientes[0] > 0 ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tiene un credito pendiente de pago, no podra crear una nueva cuenta bancaria", null);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        bankAccount.setCreatedAt(LocalDateTime.now().format(dtf));

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public Mono<BankAccount> readBankAccount(String bankAccountId) {
        LOGGER.info("Solicitud realizada para obtener la informacion de un BankAccount");
        return bankAccountRepository.findById(bankAccountId);
    }

    @Override
    public Flux<BankAccount> readBankAccountByCostumer(BigInteger costumerId) {
        LOGGER.info("Solicitud realizada para obtener las cuentas bancarias de un cliente");
        return bankAccountRepository.findByCostumerId(costumerId);
    }

    @Override
    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount) {
        LOGGER.info("Solicitud realizada para actualizar al BankAccount");
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public Mono<Void> deleteBankAccount(String bankAccountId) {
        LOGGER.info("Solicitud realizada para crear BankAccount");
        return bankAccountRepository.deleteById(bankAccountId);
    }

    @Override
    public Flux<BankAccount> listarTodos() {
        LOGGER.info("Solicitud realizada para el envio de todos los BankAccount");
        return bankAccountRepository.findAll();
    }

    @Override
    public Mono<Long> countAccountByType(BigInteger costumerId, Integer bankAccountType) {

        if(bankAccountType.equals(1) ) {
            return bankAccountRepository.countByCostumerIdAndBankAccountType(costumerId, BankAccountType.saving_account);
        }

        if(bankAccountType.equals(2) ) {
            return bankAccountRepository.countByCostumerIdAndBankAccountType(costumerId, BankAccountType.current_account);
        }

        if(bankAccountType.equals(3) ) {
            return bankAccountRepository.countByCostumerIdAndBankAccountType(costumerId, BankAccountType.fixed_term_account);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de cuenta bancaria es incorrecto", null);
    }

    @Override
    public Flux<BankAccount> listarTodosBeetween(String dateInit, String dateEnd) {
        LOGGER.info("Solicitud realizada para el envio de todos los BankAccount en un periodo determinado");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return bankAccountRepository.findAll().filter(a -> {
            if( dateInit != null && dateEnd != null ) {
                LocalDateTime d1 = LocalDateTime.parse(a.getCreatedAt(), dtf);
                LocalDateTime d2 = LocalDateTime.parse(dateInit, dtf);
                LocalDateTime d3 = LocalDateTime.parse(dateEnd, dtf);
                return (d1.isAfter(d2) && d1.isBefore(d3));
            } else if(dateInit != null) {
                LocalDateTime d1 = LocalDateTime.parse(a.getCreatedAt(), dtf);
                LocalDateTime d2 = LocalDateTime.parse(dateInit, dtf);
                return d1.isAfter(d2);
            } else if(dateEnd != null) {
                LocalDateTime d1 = LocalDateTime.parse(a.getCreatedAt(), dtf);
                LocalDateTime d3 = LocalDateTime.parse(dateEnd, dtf);
                return d1.isBefore(d3);
            } else {
                return false;
            }
        });
    }
}
