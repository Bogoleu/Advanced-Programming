package app;

import com.DisplayLocales;
import com.SetLocale;
import com.Info;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            ResourceBundle messages = ResourceBundle.getBundle("res.Messages", SetLocale.getCurrentLocale());
            System.out.print(messages.getString("prompt") + " ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("locales")) {
                DisplayLocales.execute(SetLocale.getCurrentLocale());
            } else if (command.startsWith("set ")) {
                String langTag = command.substring(4).trim();
                Locale locale = Locale.forLanguageTag(langTag);
                SetLocale.execute(locale);
            } else if (command.equalsIgnoreCase("info")) {
                Info.execute(SetLocale.getCurrentLocale());
            } else if (command.equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println(messages.getString("invalid"));
            }
        }
    }
}
