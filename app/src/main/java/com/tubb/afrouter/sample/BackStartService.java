package com.tubb.afrouter.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tubb.afrouter.AFRouter;

/**
 * Created by tubingbing on 17/5/26.
 */

public class BackStartService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AFRouterService afRouterService = AFRouter.getInstance().create(AFRouterService.class, this);
        afRouterService.backStart();
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
