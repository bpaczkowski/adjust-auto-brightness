package pl.freki.adjustautobrightness;

import android.app.IntentService;
import android.content.ContentResolver;
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
            int brightnessAdj = intent.getIntExtra(brightnessAdjIntentKey, 0);

            saveBrightnessAdjValue(brightnessAdj);
            setBrightnessAdj();
        }

        if (action.equals(setBrightnessAdjAction)) {
            setBrightnessAdj();
        }
    }

    public static void setAndSaveBrightnessAdjPercentage(Context context, int brightnessAdjPercentage) {
        Intent intent = new Intent(context, BrightnessService.class);

        intent.setAction(setAndSaveBrightnessAdjAction);
        intent.putExtra(brightnessAdjIntentKey, brightnessAdjPercentage);

        context.startService(intent);
    }

    private void saveBrightnessAdjValue(int brightnessAdj) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(brightnessAdjPrefKey, brightnessAdj);
        editor.apply();
    }

    private void setBrightnessAdj() {
        ContentResolver contentResolver = getContentResolver();
        int brightnessAdj = preferences.getInt(brightnessAdjPrefKey, 0);
        int currentBrightness;

        try {
            currentBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            return;
        }

        if (currentBrightness < brightnessAdj) {
            // sometimes it needs to be set with a different value first for it to become effective
            Settings.System.putInt(getContentResolver(), brightnessAdjSettingsKey, brightnessAdj - 1);

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignore) { }

            Settings.System.putInt(getContentResolver(), brightnessAdjSettingsKey, brightnessAdj);
        }
    }

}
