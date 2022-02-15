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
import android.os.RemoteException;
import vendor.xiaomi.hardware.displayfeature.V1_0.IDisplayFeature;

public final class AodManager {

    public static void start(IDisplayFeature mDisplayFeature){
        try{
        mDisplayFeature.setFeature(0, 25, 1, 255);
        mDisplayFeature.setFeature(0, 23, 2, 255);
        mDisplayFeature.setFeature(0, 1, 2, 255);
        mDisplayFeature.setFeature(0, 1, 0, 255);
        mDisplayFeature.setFeature(0, 1, 0, 255);
        }catch (RemoteException e){
        }
    }

    public static void stop(IDisplayFeature mDisplayFeature){
        try{
        mDisplayFeature.setFeature(0,25,0,255);
        }catch (RemoteException e){
        }
    }

}

