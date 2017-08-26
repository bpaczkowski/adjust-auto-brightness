package pl.freki.adjustautobrightness;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;

public class BrightnessService extends IntentService {
    public static final String setBrightnessAdjAction = "pl.freki.adjustautobrightness.action.setBrightnessAdj";
    private static final String setAndSaveBrightnessAdjAction = "pl.freki.adjustautobrightness.action.setAndSaveBrightnessAdj";
    private static final String brightnessAdjIntentKey = "pl.freki.adjustautobrightness.intent.brightnessAdj";
    public static final String brightnessAdjPrefKey = "brightnessAdj";
    private static final String brightnessAdjSettingsKey = "screen_auto_brightness_adj";
    private SharedPreferences preferences;

    public BrightnessService() {
        super("BrightnessService");

        preferences = PreferenceManager.getDefaultSharedPreferences(MainApplication.getContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (action.equals(setAndSaveBrightnessAdjAction)) {
            int brightnessAdjPercentage = intent.getIntExtra(brightnessAdjIntentKey, 0);

            saveBrightnessAdjPercentageValue(brightnessAdjPercentage);
            setBrightnessAdjPercentage();
        }

        if (action.equals(setBrightnessAdjAction)) {
            setBrightnessAdjPercentage();
        }
    }

    public static void setAndSaveBrightnessAdjPercentage(Context context, int brightnessAdjPercentage) {
        Intent intent = new Intent(context, BrightnessService.class);

        intent.setAction(setAndSaveBrightnessAdjAction);
        intent.putExtra(brightnessAdjIntentKey, brightnessAdjPercentage);

        context.startService(intent);
    }

    private void saveBrightnessAdjPercentageValue(int brightnessAdj) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(brightnessAdjPrefKey, brightnessAdj);
        editor.apply();
    }

    private void setBrightnessAdjPercentage() {
        int brightnessAdj = preferences.getInt(brightnessAdjPrefKey, 0);

        // sometimes it needs to be set with a different value first for it to become effective
        Settings.System.putInt(getContentResolver(), brightnessAdjSettingsKey, brightnessAdj - 1);

        try {
            Thread.sleep(50);
        } catch (InterruptedException ignore) { }

        Settings.System.putInt(getContentResolver(), brightnessAdjSettingsKey, brightnessAdj);
    }

}
