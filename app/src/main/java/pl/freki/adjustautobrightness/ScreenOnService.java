package pl.freki.adjustautobrightness;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ScreenOnService extends Service {
    private PhoneUnlockedReceiver phoneUnlockedReceiver = new PhoneUnlockedReceiver();

    @Override
    public void onCreate() {
        MainApplication.getContext().registerReceiver(phoneUnlockedReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
    }

    @Override
    public void onDestroy() {
        MainApplication.getContext().unregisterReceiver(phoneUnlockedReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
