package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    public static String BaseUrl="http://api.openweathermap.org/";
    public static String Appid="ff8451c2173751c798dc36086bf95a7b";
    //    public static String lat="22.480560";
//    public static String lon="88.314866";
    public static String lat;
    public static String lon;
    LocationManager locationManager;
    public static String city;
    public static String unit="metric";

    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle toggle;
    CardView cardView;
    EditText et;
    ListView listView;
    String data[]=new String[9];
    int icons[]={R.drawable.flag, R.drawable.equator, R.drawable.longitude, R.drawable.global, R.drawable.meter, R.drawable.humidity, R.drawable.sunrise, R.drawable.sea, R.drawable.wind};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        cardView=findViewById(R.id.cardView);
        et=findViewById(R.id.et);
        listView=findViewById(R.id.listView);
        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.navigationView);

        //ActionBarDrawerToggle is initialized to sync drawer open and closed states
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);

        dl.addDrawerListener(toggle);
        toggle.syncState();

        //The Hamburger icon is applied to the action bar for working with the nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        //On clicking of any menu items, actions will be performed accordingly
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.search:
                        startActivity(new Intent(SearchActivity.this, SearchActivity.class));
                        break;
                    case R.id.temp:
                        startActivity(new Intent(SearchActivity.this, MainActivity.class));
                        break;
                    case R.id.wind:
                        startActivity(new Intent(SearchActivity.this, WindActivity.class));
                        break;
                    case R.id.sun:
                        startActivity(new Intent(SearchActivity.this, SunActivity.class));
                        break;
                    case R.id.details:
                        startActivity(new Intent(SearchActivity.this, DetailsActivity.class));
                        break;
                    default: return(true);
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item))
            return true;
        else if(item.getItemId()==R.id.exit) {
            finishAffinity();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    public void getDet(View v) {
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {}
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service=retrofit.create(WeatherService.class);

        city=et.getText().toString();
        Call<WeatherResponse> call=service.getCurrentWeatherDataC(city,unit,Appid);
        //Call<WeatherResponse> call=service.getCurrentWeatherData(lat,lon,Appid);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if(response.code()==200){
                    WeatherResponse weatherResponse=response.body();
                    assert weatherResponse!=null;

                    data[3]="Temp: "+ Math.round(weatherResponse.main.temp) + "℃";

                    Integer sr=weatherResponse.sys.getSunrise();
                    Integer ss=weatherResponse.sys.getSunset();

                    Calendar cal1 = Calendar.getInstance(Locale.ENGLISH);
                    cal1.setTimeInMillis(sr * 1000L);
                    String date1 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal1).toString();

                    Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
                    cal2.setTimeInMillis(ss * 1000L);
                    String date2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();

                    String[] time1=date1.split(" ");
                    String[] time2=date2.split(" ");

                    data[6]="Sunrise: "+time1[1]+"AM";
                    data[7]="Sunset: "+time2[1]+"PM";

                    data[8]="Wind Speed: "+ weatherResponse.getWind().getSpeed()+"Kmph";
                    data[2]="Longitude: " + weatherResponse.getCoord().getLon();
                    data[1]="Latitude: " + weatherResponse.getCoord().getLat();
                    data[0]="Country: "+ weatherResponse.sys.getCountry();
                    data[4]="Pressure: "+weatherResponse.main.getPressure()+"Hg";
                    data[5]="Humidity: "+weatherResponse.main.getHumidity()+"g/m3";

                    CustomAdapter customAdapter = new CustomAdapter(SearchActivity.this, data, icons);
                    listView.setAdapter(customAdapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Toast.makeText(SearchActivity.this, "Invalid City Name", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
