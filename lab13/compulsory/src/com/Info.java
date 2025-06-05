package com;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public static void execute(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("res.Messages", SetLocale.getCurrentLocale());
        String pattern = messages.getString("info");
        System.out.println(MessageFormat.format(pattern, locale.getDisplayName(SetLocale.getCurrentLocale())));

        System.out.println("Country: " + locale.getDisplayCountry(SetLocale.getCurrentLocale()) +
                " (" + locale.getDisplayCountry(locale) + ")");
        System.out.println("Language: " + locale.getDisplayLanguage(SetLocale.getCurrentLocale()) +
                " (" + locale.getDisplayLanguage(locale) + ")");
        System.out.println("Currency: " + Currency.getInstance(locale));
        System.out.println("Week Days: " + String.join(", ", new DateFormatSymbols(locale).getWeekdays()));
        System.out.println("Months: " + String.join(", ", new DateFormatSymbols(locale).getMonths()));
        System.out.println("Today: " + DateFormat.getDateInstance(DateFormat.LONG, locale).format(new Date())
                + " (" + DateFormat.getDateInstance(DateFormat.LONG, SetLocale.getCurrentLocale()).format(new Date()) + ")");
    }
}
