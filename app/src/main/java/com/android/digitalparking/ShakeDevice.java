package com.android.digitalparking;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDevice implements SensorEventListener {
    private static final float SHAKE_THRESHOLD_GRAVITY = 6.7F;
    private static final float SHAKE_SLOP_TIME_MS = 5000;
    private static final float SHAKE_COUNT_REST_TIME_MS = 600;

    private OnShakeListener mListner;
    private long mShakeTimestamp;
    private int mShakeCount;

public void setOnShakeListner(OnShakeListener listener){
    this.mListner=listener;

}
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (mListner != null) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            //gForce will be close to 1 when there is no movement.
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);


            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                //ignore shake events too close to each other (5000ms)
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                //reset the shake count after 3 second of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_REST_TIME_MS < now) {
                    mShakeCount = 0;
                }
                mShakeTimestamp = now;
                mShakeCount++;

                mListner.onshake(mShakeCount);
            }
        }
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface OnShakeListener {
        void onshake (int count);
    }

}
