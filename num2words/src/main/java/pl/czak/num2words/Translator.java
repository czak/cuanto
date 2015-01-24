package pl.czak.num2words;

import java.util.Locale;

public class Translator {
    // 0-29
    private static String[] literals = {
            "cero",
            "uno",
            "dos",
            "tres",
            "cuatro",
            "cinco",
            "seis",
            "siete",
            "ocho",
            "nueve",
            "diez",
            "once",
            "doce",
            "trece",
            "catorce",
            "quince",
            "dieciséis",
            "diecisiete",
            "dieciocho",
            "diecinueve",
            "veinte",
            "veintiuno",
            "veintidós",
            "veintitrés",
            "veinticuatro",
            "veinticinco",
            "veintiséis",
            "veintisiete",
            "veintiocho",
            "veintinueve"
    };

    private static String[] tens = {
            "",
            "diez",
            "veinte",
            "treinta",
            "cuarenta",
            "cincuenta",
            "sesenta",
            "setenta",
            "ochenta",
            "noventa"
    };

    private static String[] hundreds = {
            "",
            "ciento",
            "doscientos",
            "trescientos",
            "cuatrocientos",
            "quinientos",
            "seiscientos",
            "setecientos",
            "ochocientos",
            "novecientos"
    };

    public Translator(Locale locale) {
        if (!locale.getLanguage().equals("es")) {
            throw new IllegalArgumentException("Unsupported locale: " + locale.getLanguage());
        }
    }

    public Translator() {
        this(new Locale("es"));
    }

    public String translate(int number) {
        if (number == 0) {
            return "cero";
        }
        if (number == 1000000) {
            return "un millón";
        }

        int thousands = (number / 1000) % 1000;
        int under = number % 1000;

        StringBuilder builder = new StringBuilder();

        if (thousands > 0) {
            if (thousands > 1) {
                builder.append(translate999(thousands));
            }
            builder.append(" mil ");
        }

        builder.append(translate999(under));

        return builder.toString().trim();
    }

    String translate99(int n) {
        // Do 29 praktycznie same wyjątki
        if (n <= 29) {
            return literals[n];
        }

        int tensDigit = (n / 10) % 10;
        int unitsDigit = n % 10;

        StringBuilder builder = new StringBuilder();

        // 1. Dziesiątki (są na pewno, minimum 3)
        builder.append(tens[tensDigit]);

        // 2. Jedności (jeśli więcej niż zero)
        if (unitsDigit > 0) {
            builder.append(" y ");
            builder.append(literals[unitsDigit]);
        }

        return builder.toString();
    }

    String translate999(int number) {
        int n = number % 1000;

        // 100 jest specifyczne
        if (n == 100) {
            return "cien";
        }

        int hundredsDigit = (n / 100) % 10;

        StringBuilder builder = new StringBuilder();

        // 1. Setki (jeśli są)
        if (hundredsDigit > 0) {
            builder.append(hundreds[hundredsDigit]);
            builder.append(' ');
        }

        // 2. Dziesiątki + jedności
        if (n % 100 > 0) {
            builder.append(translate99(n % 100));
        }

        return builder.toString().trim();
    }
}
