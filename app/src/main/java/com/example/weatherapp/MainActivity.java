package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import pojo.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static String BaseUrl="http://api.openweathermap.org/";
    public static String Appid="ff8451c2173751c798dc36086bf95a7b";
//    public static String lat="22.480560";
//    public static String lon="88.314866";
    public static String lat;
    public static String lon;
    public static String city="Kolkata,IN";
    public static String unit="metric";

    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle toggle;
    CardView cardView;
    TextView textView,tv;
    ListView listView;
    String days[]={"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    int icons[]={R.drawable.ic_sun, R.drawable.ic_clouds, R.drawable.ic_moon, R.drawable.ic_rain, R.drawable.ic_sunny, R.drawable.ic_flash, R.drawable.ic_snowflake};
    ArrayList<String> lt=new ArrayList<>(5);
    int dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat=SplashActivity.lat;
        lon=SplashActivity.lon;

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        cardView=findViewById(R.id.cardView);
        textView=findViewById(R.id.textView2);
        listView=findViewById(R.id.listView);
        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.navigationView);
        tv = findViewById(R.id.textView);

        //ActionBarDrawerToggle is initialized to sync drawer open and closed states
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);

        dl.addDrawerListener(toggle);
        toggle.syncState();

        //The Hamburger icon is applied to the action bar for working with the nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.violet)));

        //On clicking of any menu items, actions will be performed accordingly
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.search:
                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                        break;
                    case R.id.temp:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case R.id.wind:
                        startActivity(new Intent(MainActivity.this, WindActivity.class));
                        break;
                    case R.id.sun:
                        startActivity(new Intent(MainActivity.this, SunActivity.class));
                        break;
                    case R.id.details:
                        startActivity(new Intent(MainActivity.this, DetailsActivity.class));
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

        Call<WeatherData> call1=service.getFutureWeatherData(lat,lon,Appid);
        call1.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                if(response.code()==200){
                    WeatherData weatherResponse=response.body();
                    assert weatherResponse!=null;

                    long a=Math.round(weatherResponse.getList().get(0).getMain().getTemp()-273);

                    String stringBuilder="\n"+
                            "Temperature: "+ a + "℃"
                            ;
                    if(a<=15) cardView.setBackgroundColor(getResources().getColor(R.color.light));
                    else if(a<=20) cardView.setBackgroundColor(getResources().getColor(R.color.dark));
                    else if(a<=25) cardView.setBackgroundColor(getResources().getColor(R.color.bet));
                    else if(a<=30) cardView.setBackgroundColor(getResources().getColor(R.color.yellow));
                    else if (a<=40) cardView.setBackgroundColor(getResources().getColor(R.color.orangish));
                    else cardView.setBackgroundColor(getResources().getColor(R.color.red));
                    textView.setText(stringBuilder);

                    int i=dayOfWeek-1;
                    ArrayList<Integer> t=new ArrayList<>(5);
                    if(i==7) i=0;
                    lt.add(days[i]+" "+Math.round((weatherResponse.getList().get(0).getMain().getTemp()-273))+"℃");
                    t.add(colRet((weatherResponse.getList().get(0).getMain().getTemp()-273)));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+Math.round((weatherResponse.getList().get(8).getMain().getTemp()-273))+"℃");
                    t.add(colRet((weatherResponse.getList().get(8).getMain().getTemp()-273)));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+Math.round((weatherResponse.getList().get(16).getMain().getTemp()-273))+"℃");
                    t.add(colRet((weatherResponse.getList().get(16).getMain().getTemp()-273)));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+Math.round((weatherResponse.getList().get(24).getMain().getTemp()-273))+"℃");
                    t.add(colRet((weatherResponse.getList().get(24).getMain().getTemp()-273)));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+Math.round((weatherResponse.getList().get(32).getMain().getTemp()-273))+"℃");
                    t.add(colRet((weatherResponse.getList().get(32).getMain().getTemp()-273)));
                    int[] it=new int[5];
                    String[] item = lt.toArray(new String[5]);
                    for (int j =0; j < 5; j++)
                        it[j] = t.get(j);

                    CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, item, it);
                    listView.setAdapter(customAdapter);
                    Log.w("list",lt.toString());
//                    Log.w("icon",weatherResponse.getList().get(0).getMain().getTemp().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherData> call, @NonNull Throwable t) {
                textView.setText(t.getMessage());
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

    static int colRet(Double temp) {
        if(temp<=3.0) return(R.drawable.ic_snowflake);
        if(temp<=10.0) return(R.drawable.ic_cold);
        if(temp<=20.0) return(R.drawable.ic_rain);
        if(temp<=25.0) return(R.drawable.ic_clouds);
        if(temp<=30.0) return(R.drawable.ic_sun);
        if(temp<=35.0) return(R.drawable.ic_sunny);
        return(R.drawable.ic_temperature);
    }

}
