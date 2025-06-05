package com;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales {
    public static void execute(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", locale);
        System.out.println(messages.getString("locales"));
        for (Locale availableLocale : Locale.getAvailableLocales()) {
            System.out.println(availableLocale.toString() + " : " + availableLocale.getDisplayName(locale));
        }
    }
}
