package pl.czak.num2words;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;

public class TranslatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    String[][] examples = {
        { "cero",                               "0" },
        { "ochenta y seis",                     "86" },
        { "ciento veintidós",                   "122" },
        { "trescientos",                        "300" },
        { "mil",                                "1000" },
        { "mil tres",                           "1003" },
        { "ochenta y seis mil ochenta y siete", "86087" },
        { "un millón",                          "1000000" },
    };

    String spanish(int number) {
        return new Translator().translate(number);
    }

    @Test
    public void spanishTranslations() {
        for (int i = 0; i < examples.length; i++) {
            String expected = examples[i][0];
            int number      = Integer.parseInt(examples[i][1]);
            assertEquals(expected, spanish(number));
        }
    }

    @Test
    public void defaultConstructor() {
        new Translator();
    }

    @Test
    public void spanishLocaleIsSupported() {
        new Translator(new Locale("es"));
    }

    @Test
    public void unsupportedLocale() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unsupported locale: en");

        new Translator(Locale.ENGLISH);
    }

}
