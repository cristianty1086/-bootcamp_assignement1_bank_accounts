package com.nttdata.bootcamp.assignement1.bank_accounts.utilities;

import com.nttdata.bootcamp.assignement1.bank_accounts.model.BankAccountType;

import java.math.BigInteger;

public class BuilderUrl {
    public static String buildCountAccountsByType(BigInteger costumerId, BankAccountType accountType) {
        if(costumerId == null) {
            return  null;
        }
        if(accountType == null) {
            return  null;
        }

        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/bank_account/count_accounts_by_type/");
        ss.append(costumerId);
        ss.append("/");
        ss.append(accountType.ordinal()+1);

        return ss.toString();
    }

    public static String buildGetBankAccount(String bankAccountId) {
        if(bankAccountId == null) {
            return  null;
        }
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/bank_account/get/");
        ss.append(bankAccountId);

        return ss.toString();
    }

    public static String buildUpdateBankAccount() {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/bank_account/update");

        return ss.toString();
    }

    public static String buildGetDebitCard(String debitCardId) {
        if(debitCardId == null) {
            return  null;
        }
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/debit_card/get/");
        ss.append(debitCardId);

        return ss.toString();
    }
    public static String buildCreateMovement() {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/movement/create");

        return ss.toString();
    }
    public static String buildGetCreditsByCostumerId(BigInteger costumerId) {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/credit/get_by_costumer");
        ss.append(costumerId);

        return ss.toString();
    }
    public static String buildGetAssociateBankAccount(String debitCardId) {
        StringBuilder ss = new StringBuilder();
        ss.append(AppConstants.getCurrentUrl());
        ss.append("/associate_bank_account/get_by_debitcard");
        ss.append(debitCardId);

        return ss.toString();
    }
}
