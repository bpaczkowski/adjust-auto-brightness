package pl.freki.adjustautobrightness;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent brightnessServiceIntent = new Intent(context, BrightnessService.class);

        brightnessServiceIntent.setAction(BrightnessService.setBrightnessAdjAction);

        context.startService(brightnessServiceIntent);

        context.startService(new Intent(context, ScreenOnService.class));
    }

}
