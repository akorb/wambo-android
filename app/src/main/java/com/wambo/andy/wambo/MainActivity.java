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
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener, View.OnClickListener
{
    private SensorManager sensorManager;
    private Sensor sensor;

    private MediaPlayer mpMini;
    private MediaPlayer mpWambo;

    private float lastValue;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
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
        // Log.d("SensorChanged", Arrays.toString(event.values));

        lastValue = event.values[1];
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, sensor,
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