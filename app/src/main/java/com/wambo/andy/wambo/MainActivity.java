package com.wambo.andy.wambo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener
{
    SensorManager mSensorManager;
    Sensor mGyroscope;

    boolean isReversed = false;

    MediaPlayer mpMini;
    MediaPlayer mpWambo;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = ((TextView)findViewById(R.id.tvWambo));
        Resources res = getResources();
        tv.setTextColor(res.getColor(R.color.violet));
        tv.setRotation(180f);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mGyroscope = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

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

        isReversed = event.values[0] <= 0.0f;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mGyroscope,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void tvWambo_onClick(View v)
    {
        if (isReversed)
        {
            mpMini.seekTo(0);
            mpMini.start();
        }
        else
        {
            mpWambo.seekTo(0);
            mpWambo.start();
        }
    }
}