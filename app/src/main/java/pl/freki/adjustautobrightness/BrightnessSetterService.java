package pl.freki.adjustautobrightness;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BrightnessSetterService extends Service {
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onCreate() {
        final Context context = this;
        scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Intent brightnessServiceIntent = new Intent(context, BrightnessService.class);

                brightnessServiceIntent.setAction(BrightnessService.setBrightnessAdjAction);

                context.startService(brightnessServiceIntent);
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        try {
            scheduledExecutorService.shutdown();
            scheduledExecutorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) { }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
