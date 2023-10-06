package com.nttdata.bootcamp.assignement1.bank_accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * BankAccountsApplication es la clase principal que gestiona
 * el inicio del programa
 * 
 * @author Cristian TY / Nttdata
 */
@SpringBootApplication
@EnableEurekaClient
public class BankAccountsApplication {
	/**
	 * BankAccountsApplication constructor.
	 */
	protected BankAccountsApplication() { }

	/**
	 * Lanza el webservice
	 * Metodo principal que gestiona el inicio del programa
	 * @param args
	 * argumentos de entrada
	 */
	public static void main(final String[] args) {
		SpringApplication.run(BankAccountsApplication.class, args);
	}

}
