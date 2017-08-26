package pl.freki.adjustautobrightness;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    private static MainApplication instance;

    public static MainApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }
}