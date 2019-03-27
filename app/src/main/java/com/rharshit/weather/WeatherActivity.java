package com.rharshit.weather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.rharshit.weather.api.ApiHandler;
import com.rharshit.weather.api.WeatherInfo;
import com.rharshit.weather.api.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mContext = this;

        int woeid = getIntent().getIntExtra("woeid", 0);
        Log.d(TAG, "onCreate: woeid: " + woeid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.metaweather.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiHandler handler = retrofit.create(ApiHandler.class);

        Call<WeatherInfo> call = handler.getWeather(woeid);
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                WeatherInfo info = response.body();

                ((TextView) findViewById(R.id.weather_state_name)).setText(
                        info.consolidated_weather.get(0).weather_state_name
                );
                ((TextView) findViewById(R.id.the_temp)).setText(
                        info.consolidated_weather.get(0).the_temp.toString()
                );
                ((TextView) findViewById(R.id.humidity)).setText(
                        String.valueOf(info.consolidated_weather.get(0).humidity)
                );
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Toast.makeText(mContext, "Failed retrieving weather",
                        Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
