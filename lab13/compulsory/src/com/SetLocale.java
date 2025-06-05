package com;

import java.util.Locale;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class SetLocale {
    private static Locale currentLocale = Locale.getDefault();

    public static void setCurrentLocale(Locale locale) {
        currentLocale = locale;
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void execute(Locale locale) {
        setCurrentLocale(locale);
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", locale);
        String pattern = messages.getString("locale.set");
        System.out.println(MessageFormat.format(pattern, locale.getDisplayName(locale)));
    }
}
