package org.test;

import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws LoginException {
        Configuration conf = new Configuration() {
            @Override
            public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
                Map<String, String> options = new HashMap<>();
                options.put("useTicketCache", "true");
                options.put("doNotPrompt", "true");
                options.put("refreshKrb5Config", "true");
                options.put("debug", Boolean.toString(Boolean.getBoolean("kudu.jaas.debug")));
                options.put("renewTGT", "true");

                return new AppConfigurationEntry[] { new AppConfigurationEntry(
                        "com.sun.security.auth.module.Krb5LoginModule",
                        AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options) };
            }
        };
        LoginContext loginContext = new LoginContext("kudu", new Subject(), null, conf);
        loginContext.login();
        loginContext.getSubject();
    }
}
