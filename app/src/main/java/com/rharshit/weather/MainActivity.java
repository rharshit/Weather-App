package com.rharshit.weather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rharshit.weather.api.ApiHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etSearch;
    private ListView lvSearch;

    private Context mContext;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        etSearch = findViewById(R.id.et_search_city);
        lvSearch = findViewById(R.id.list_search_cities);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.metaweather.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiHandler handler = retrofit.create(ApiHandler.class);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = etSearch.getText().toString();
                Log.d(TAG, "afterTextChanged: City: " + query);

                if(!query.equals("")){
                    Call<List<City>> call = handler.getCities(query);
                    call.enqueue(new Callback<List<City>>() {
                        @Override
                        public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                            List<City> cities = response.body();

                            lvSearch.setAdapter(new CityViewAdapter(mContext, cities));
                        }

                        @Override
                        public void onFailure(Call<List<City>> call, Throwable t) {
                            Toast.makeText(mContext, "Failed retrieving data",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
