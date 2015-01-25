package pl.czak.cuanto;

import android.app.Application;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by czak on 22.01.2015.
 */
public class MyApplication extends Application {
    private TextToSpeech tts;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(new Locale("es"));
            }
        });
    }

    public TextToSpeech getTts() {
        return tts;
    }
}
