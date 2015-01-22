package pl.czak.cuanto;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by czak on 22.01.2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
