package com.example.weatherapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

import pojo.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SunActivity extends AppCompatActivity {

    public static String BaseUrl="http://api.openweathermap.org/";
    public static String Appid="ff8451c2173751c798dc36086bf95a7b";
    public static String lat;
    public static String lon;
    public static String city="Kolkata,IN";
    public static String unit="metric";

    DrawerLayout dl;
    NavigationView nv;
    TextView sun_tv1,sun_tv2;
    CardView sun_cv1, sun_cv2;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun);

        lat=SplashActivity.lat;
        lon=SplashActivity.lon;

        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.navigationView);
        sun_tv1=findViewById(R.id.sunTextView1);
        sun_tv2=findViewById(R.id.sunTextView2);
        sun_cv1=findViewById(R.id.sunCardView1);
        sun_cv2=findViewById(R.id.sunCardView2);

        //ActionBarDrawerToggle is initialized to sync drawer open and closed states
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);

        dl.addDrawerListener(toggle);
        toggle.syncState();

        //The Hamburger icon is applied to the action bar for working with the nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        //On clicking of any menu items, actions will be performed accordingly
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.search:
                        startActivity(new Intent(SunActivity.this, SearchActivity.class));
                        break;
                    case R.id.temp:
                        startActivity(new Intent(SunActivity.this, MainActivity.class));
                        break;
                    case R.id.wind:
                        startActivity(new Intent(SunActivity.this, WindActivity.class));
                        break;
                    case R.id.sun:
                        startActivity(new Intent(SunActivity.this, SunActivity.class));
                        break;
                    case R.id.details:
                        startActivity(new Intent(SunActivity.this, DetailsActivity.class));
                        break;
                    default: return(true);
                }
                return true;
            }
        });

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service=retrofit.create(WeatherService.class);

//        Call<WeatherResponse> call=service.getCurrentWeatherDataC(city,unit,Appid);
        Call<WeatherResponse> call=service.getCurrentWeatherData(lat,lon,Appid);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if(response.code()==200){
                    WeatherResponse weatherResponse=response.body();
                    assert weatherResponse!=null;

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

                    String stringBuilder1="\n"+
                            "Sunrise: "+ time1[1]+" AM"
                            ;

                    String stringBuilder2="\n"+
                            "Sunset: "+ time2[1]+" PM"
                            ;

                    sun_tv1.setText(stringBuilder1);
                    sun_tv2.setText(stringBuilder2);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                sun_tv1.setText(t.getMessage());
                sun_tv2.setText(t.getMessage());
            }
        });

        Call<WeatherData> call1=service.getFutureWeatherData(lat,lon,Appid);
        call1.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                if(response.code()==200){
                    WeatherData weatherResponse=response.body();
                    assert weatherResponse!=null;
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherData> call, @NonNull Throwable t) {
                sun_tv1.setText(t.getMessage());
                sun_tv2.setText(t.getMessage());
            }
        });
    }

    //On clicking any Menu Item this function is called and it returns the ID to the main function
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

}
