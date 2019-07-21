package com.wambo.andy.wambo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor gyroscope;

    private MediaPlayer mpMini;
    private MediaPlayer mpWambo;

    private float lastValue;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mpMini = MediaPlayer.create(this, R.raw.mini);
        mpWambo = MediaPlayer.create(this, R.raw.wambo);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event)
    {
        // The gyroscope sensor returns a single value.
        // Sensors return 3 values, one for each axis.

        lastValue = event.values[0];

        // Log.d("SensorChanged", Float.toString(lastValue));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, gyroscope,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void tvWambo_onClick(View v)
    {
        boolean isReversed = lastValue <= 0.0f;

        if (isReversed)
        {
            mpWambo.seekTo(0);
            mpWambo.start();
        }
        else
        {
            mpMini.seekTo(0);
            mpMini.start();
        }
    }
}