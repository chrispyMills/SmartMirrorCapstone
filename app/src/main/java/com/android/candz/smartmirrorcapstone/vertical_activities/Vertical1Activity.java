package com.android.candz.smartmirrorcapstone.vertical_activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.candz.smartmirrorcapstone.R;
import com.android.candz.smartmirrorcapstone.WeatherFetch;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Vertical1Activity extends AppCompatActivity
{

    private TextView dateText;
    private TextView timeText;
    private TextView weatherText;
    private ImageView weatherIcon;
    boolean run = true; //set it to false if you want to stop the timer
    Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical1);

        dateText = findViewById(R.id.dateTextV1);
        timeText = findViewById(R.id.timeTextV1);
        weatherText = findViewById(R.id.weatherText1);



        dateText.setText(currentDate());
        setBackgroundColor();
        setTextColor(dateText, timeText);
        timer();

        //Determine zip Code of user

        weatherText.setText(getCurrentWeather("27616"));
    }


    public void timer()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (run)
                {
                    try
                    {
                        Thread.sleep(1000);
                        mHandler.post(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                Calendar c = Calendar.getInstance();
                                int sec = c.get(Calendar.SECOND);
                                int min = c.get(Calendar.MINUTE);
                                int hour = c.get(Calendar.HOUR);
                                timeText.setText(String.valueOf(hour) + ":" +
                                        String.valueOf(min) + ":" + String.valueOf(sec));
                            }
                        });
                    } catch (Exception e)
                    {
                    }
                }
            }
        }).start();
    }

    private void setBackgroundColor()
    {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(0x000000);
    }

    private void setTextColor(TextView dateText, TextView timeText)
    {
        dateText.setTextColor(Color.WHITE);
        timeText.setTextColor(Color.WHITE);
    }

    private String currentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getCurrentWeather(String zipCode)
    {
        //Currently only grabs temperature from Raleigh (RDU).
        //https://w1.weather.gov/xml/current_obs/display.php?stid=KRDU
        //https://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587
        WeatherFetch wf = new WeatherFetch();
        JSONObject json = wf.getJSON(this, zipCode);
        return "TEST";
    }

}
