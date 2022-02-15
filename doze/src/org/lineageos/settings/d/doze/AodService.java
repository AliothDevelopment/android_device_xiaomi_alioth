/*
 * Copyright (C) 2022 MB dataoutputstream
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings.d.doze;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import java.lang.Runnable;
import vendor.xiaomi.hardware.displayfeature.V1_0.IDisplayFeature;

public class AodService extends Service {
    private static final String TAG = "AodService";
    private static final boolean DEBUG = false;

    private IDisplayFeature mDisplayFeature;

    private BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                onDisplayOff();
            }else if (intent.getAction().equals(Intent.ACTION_DREAMING_STARTED)) {
                onDream();
            }else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                onDisplayOn();
            }
        }
    };

    @Override
    public void onCreate() {
        if (DEBUG) Log.d(TAG, "Creating service");
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_DREAMING_STARTED);
        registerReceiver(mScreenStateReceiver, screenStateFilter);
        try{
            mDisplayFeature=IDisplayFeature.getService();
        } catch (RemoteException e){
            //Nothing
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) Log.d(TAG, "Destroying service");
        super.onDestroy();
        this.unregisterReceiver(mScreenStateReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void onDisplayOff() {
        if (DEBUG) Log.d(TAG, "Display off");
        //To handle ext rom side parts
        if(DozeUtils.isAlwaysOnEnabled(this)) DozeUtils.checkDozeService(this);
    }

    private void onDisplayOn() {
        if (DEBUG) Log.d(TAG, "Display on");
        if (DozeUtils.isAlwaysOnEnabled(this)){
            AodManager.stop(mDisplayFeature);
        }
    }

    private void onDream(){
        if (DozeUtils.isAlwaysOnEnabled(this)){
        Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Dreaming started");
                    AodManager.start(mDisplayFeature);
                }
            }, 500);
        }
    }

}
