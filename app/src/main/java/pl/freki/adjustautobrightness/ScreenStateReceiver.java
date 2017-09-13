package pl.freki.adjustautobrightness;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                context.startService(new Intent(context, BrightnessSetterService.class));
                break;
            case Intent.ACTION_SCREEN_OFF:
                context.stopService(new Intent(context, BrightnessSetterService.class));
                break;
        }
    }

}
