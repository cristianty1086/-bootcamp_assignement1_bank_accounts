package com.nttdata.bootcamp.assignement1.bank_accounts.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConstants {

    public static String baseUrl = "https://bootcamp37-bankaccounts.azurewebsites.net";
    public static String getCurrentUrl() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        String currentPort = "80";
        StringBuilder url  = new StringBuilder();
        url.append(AppConstants.baseUrl);
        url.append(":");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
            currentPort = appProps.getProperty("server.port");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            url.append(currentPort);
        }
        return url.toString();
    }
}
