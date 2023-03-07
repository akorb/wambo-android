package com.wambo.andy.wambo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener, View.OnClickListener
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
        TextView tvWambo = findViewById(R.id.tvWambo);
        tvWambo.setOnClickListener(this);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
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

    private void playWambo() {
        mpWambo.seekTo(0);
        mpWambo.start();
    }

    private void playMini() {
        mpMini.seekTo(0);
        mpMini.start();
    }

    @Override
    public void onClick(View view) {
        boolean phoneIsUpsideDown = lastValue < 0.0f;

        if (phoneIsUpsideDown)
        {
            playWambo();
        }
        else
        {
            playMini();
        }
    }
}