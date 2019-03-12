package com.android.candz.smartmirrorcapstone;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

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
    private JsonObject jsonWeatherData;
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
        weatherIcon = findViewById(R.id.weatherIcon1);


        dateText.setText(currentDate());
        setBackgroundColor();
        timer();

        //Prompt user to enter a ZipCode to pull weather information from.

        weatherText.setText(getCurrentWeather("27616"));
        weatherText.setTextColor(Color.WHITE);

        //Change the Image that WeatherIcon displays based on the type of weather that is currently
        //occurring.  Values can be obtained from WeatherFetch.fetchCurrentWeatherType()
        //Possible values are Clear sky, few clouds, scattered clouds, broken clouds,
        //shower rain, rain, thunderstorm, snow, mist


        if (confirmWeatherData())
        {
            String weatherTypeIconId = getCurrentWeatherType();
            setWeatherTypeIcon(weatherTypeIconId);
        }

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
                                timeText.setText(String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec));
                            }
                        });
                    }
                    catch (Exception e)
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
//        WeatherFetch wf = new WeatherFetch();
//        JSONObject json = wf.getJSON(this, zipCode);
//
//
//      WeatherLib is an option to interface with the OpenWeatherMap API
//      http://survivingwithandroid.github.io/WeatherLib/android_weatherlib_start.html
//
//        WeatherConfig config = new WeatherConfig();
//        config.unitSystem = WeatherConfig.UNIT_SYSTEM.I;
//        config.lang = "en";
//        config.ApiKey = "28b3d92963fef3ce6717737997d698b8";
//        config.maxResult = 1;
//        config.numDays = 1;
//
//        try
//        {
//            WeatherClient client = (new WeatherClient.ClientBuilder()).attach(this)
//                    .provider(new OpenweathermapProviderType())
//                    .httpClient(com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault.class)
//                    .config(config)
//                    .build();
//
//            client.getCurrentCondition();
//        }
//        catch (Throwable t)
//        {
//            t.printStackTrace();
//        } WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();

        String jsonString = WeatherFetch.getWeatherJsonZip("27616");

        jsonWeatherData = WeatherFetch.convertJsonStringtoJsonObject(jsonString);


        //Crashes here
        String temperature = WeatherFetch.fetchCurrentTemperature(jsonWeatherData);
        String degreeSymbol = "\u00b0";
        //Format the string so that only the relevant digits are shown.
        // temperature = String.
        return temperature + degreeSymbol;
    }

    public String getCurrentWeatherType()
    {

        return WeatherFetch.fetchCurrentWeatherType(jsonWeatherData);
    }


    private void setWeatherTypeIcon(String weatherTypeIconId)
    {

        /**
         * This is needed because drawable file names cannot contain numbers.
         * 01d = a
         * 01n = aa
         * 02d = aaa
         * 02n = aaaa
         * 03d = b
         * 03n = bb
         * 04d = bbb
         * 04n = bbbb
         * 09d = c
         * 09n = cc
         * 10d = ccc
         * 10n = cccc
         * 11d = d
         * 11n = dd
         * 13d = ddd
         * 13n = dddd
         * 50d = e
         * 50n = ee
         */

        switch (weatherTypeIconId)
        {
            case "01d":
                weatherIcon.setImageResource(R.drawable.a);
                break;
            case "01n":
                weatherIcon.setImageResource(R.drawable.aa);
                break;
            case "02d":
                weatherIcon.setImageResource(R.drawable.aaa);
                break;
            case "02n":
                weatherIcon.setImageResource(R.drawable.aaaa);
                break;
            case "03d":
                weatherIcon.setImageResource(R.drawable.b);
                break;
            case "03n":
                weatherIcon.setImageResource(R.drawable.bb);
                break;
            case "04d":
                weatherIcon.setImageResource(R.drawable.bbb);
                break;
            case "04n":
                weatherIcon.setImageResource(R.drawable.bbbb);
                break;
            case "09d":
                weatherIcon.setImageResource(R.drawable.c);
                break;
            case "09n":
                weatherIcon.setImageResource(R.drawable.cc);
                break;
            case "10d":
                weatherIcon.setImageResource(R.drawable.ccc);
                break;
            case "10n":
                weatherIcon.setImageResource(R.drawable.cccc);
                break;
            case "11d":
                weatherIcon.setImageResource(R.drawable.d);
                break;
            case "11n":
                weatherIcon.setImageResource(R.drawable.dd);
                break;
            case "13d":
                weatherIcon.setImageResource(R.drawable.ddd);
                break;
            case "13n":
                weatherIcon.setImageResource(R.drawable.dddd);
                break;
            case "50d":
                weatherIcon.setImageResource(R.drawable.e);
                break;
            case "50n":
                weatherIcon.setImageResource(R.drawable.ee);
                break;
            default:
                weatherIcon.setImageResource(R.drawable.weather_cloud);

        }
    }

    public boolean confirmWeatherData()
    {

        return WeatherFetch.confirmWeatherData(jsonWeatherData);
    }

}