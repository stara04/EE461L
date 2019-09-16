package com.example.weatherapp;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import retrofit.Callback;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ForecastApi.create("fd3e6534fdeb1d4e4c0ce499cbae0acf");
    }
//Tara Driving
    public void enterAddress(View view){
        EditText addressInput = (EditText) findViewById(R.id.addressInput);
        String address = addressInput.getText().toString();
        //geocode(address);

        Toast myToast = Toast.makeText(this, address,
                Toast.LENGTH_SHORT);
        myToast.show();

        getWeatherInfo("30", "-97");
//        try {
//            GeoApiContext context = new GeoApiContext.Builder()
//                    .apiKey("AIzaSyDiBjCdc9k3Tm3MTj9GrUJU8u2QoH-ovVE")
//                    .build();
//            GeocodingResult[] results =  GeocodingApi.geocode(context,
//                    "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
////            Gson gson = new GsonBuilder().setPrettyPrinting().create();
////            System.out.println(gson.toJson(results[0].addressComponents));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
    //Tara Stopped Driving, Emily Driving

    private void geocode(String input) {
        Toast myToast = Toast.makeText(this, input,
                Toast.LENGTH_SHORT);
        myToast.show();

        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyDiBjCdc9k3Tm3MTj9GrUJU8u2QoH-ovVE")
                    .build();
            GeocodingResult[] results =  GeocodingApi.geocode(context,
                    "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            System.out.println(gson.toJson(results[0].addressComponents));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void getWeatherInfo(String latitude, String longitude) {
        RequestBuilder weather = new RequestBuilder();

        Request request = new Request();
        request.setLat(latitude);
        request.setLng(longitude);
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.PIG_LATIN);
        request.addExcludeBlock(Request.Block.ALERTS);
        request.addExcludeBlock(Request.Block.MINUTELY);
        request.addExcludeBlock(Request.Block.HOURLY);
        request.addExcludeBlock(Request.Block.DAILY);
        request.addExcludeBlock(Request.Block.FLAGS);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                double temperature = weatherResponse.getCurrently().getTemperature();
                String humidity = weatherResponse.getCurrently().getHumidity();
                String windSpeed = weatherResponse.getCurrently().getWindSpeed();
                String precipProb = weatherResponse.getCurrently().getPrecipProbability();
                System.out.println(temperature);
                System.out.println(humidity);
                Log.d("MainActivity", "Temp: " + weatherResponse.getCurrently().getTemperature());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // handle failure
            }
        });

    }
}
