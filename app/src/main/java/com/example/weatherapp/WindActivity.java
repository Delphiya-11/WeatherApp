package com.example.weatherapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import pojo.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WindActivity extends AppCompatActivity {

    public static String BaseUrl="http://api.openweathermap.org/";
    public static String Appid="ff8451c2173751c798dc36086bf95a7b";
    public static String lat;
    public static String lon;
    public static String city="Kolkata,IN";
    public static String unit="metric";

    DrawerLayout dl;
    NavigationView nv;
    TextView wind_tv;
    CardView wind_cv;
    ListView listView;
    ActionBarDrawerToggle toggle;

    String days[]={"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    ArrayList<String> lt=new ArrayList<>(5);
    int dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind);

        lat=SplashActivity.lat;
        lon=SplashActivity.lon;

        //Initialize views from the layout
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.navigationView);
        wind_tv = findViewById(R.id.windTextView);
        wind_cv=findViewById(R.id.windCardView);
        listView=findViewById(R.id.windListView);

        //ActionBarDrawerToggle is initialized to sync drawer open and closed states
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open_menu, R.string.close_menu);

        dl.addDrawerListener(toggle);
        toggle.syncState();

        //The Hamburger icon is applied to the action bar for working with the nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blueGrey)));

        //On clicking of any menu items, actions will be performed accordingly
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.search:
                        startActivity(new Intent(WindActivity.this, SearchActivity.class));
                        break;
                    case R.id.temp:
                        startActivity(new Intent(WindActivity.this, MainActivity.class));
                        break;
                    case R.id.wind:
                        startActivity(new Intent(WindActivity.this, WindActivity.class));
                        break;
                    case R.id.sun:
                        startActivity(new Intent(WindActivity.this, SunActivity.class));
                        break;
                    case R.id.details:
                        startActivity(new Intent(WindActivity.this, DetailsActivity.class));
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


                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                wind_tv.setText(t.getMessage());
            }
        });

        Call<WeatherData> call1=service.getFutureWeatherData(lat,lon,Appid);
        call1.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                if(response.code()==200){
                    WeatherData weatherResponse=response.body();
                    assert weatherResponse!=null;
                    int i=dayOfWeek-1;
                    Double speed=weatherResponse.getList().get(0).getWind().getSpeed();

                    String stringBuilder="\n"+
                            "Wind Speed: "+ speed+" kmph"
                            ;

                    wind_tv.setText(stringBuilder);
                    ArrayList<Integer> t=new ArrayList<>(5);
                    if(i==7) i=0;
                    lt.add(days[i]+" "+weatherResponse.getList().get(0).getWind().getSpeed()+" kmph");
                    t.add(colRet((weatherResponse.getList().get(0).getWind().getSpeed())));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+weatherResponse.getList().get(8).getWind().getSpeed()+" kmph");
                    t.add(colRet((weatherResponse.getList().get(8).getWind().getSpeed())));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+weatherResponse.getList().get(16).getWind().getSpeed()+" kmph");
                    t.add(colRet((weatherResponse.getList().get(16).getWind().getSpeed())));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+weatherResponse.getList().get(24).getWind().getSpeed()+" kmph");
                    t.add(colRet((weatherResponse.getList().get(24).getWind().getSpeed())));
                    i++; if(i==7) i=0;
                    lt.add(days[i]+" "+weatherResponse.getList().get(32).getWind().getSpeed()+" kmph");
                    t.add(colRet((weatherResponse.getList().get(32).getWind().getSpeed())));

                    int[] it=new int[5];
                    String[] item = lt.toArray(new String[5]);
                    for (int j =0; j < 5; j++)
                        it[j] = t.get(j);

                    CustomAdapter customAdapter = new CustomAdapter(WindActivity.this, item, it);
                    listView.setAdapter(customAdapter);
                    Log.w("list",lt.toString());
//                    Log.w("icon",weatherResponse.getList().get(0).getMain().getTemp().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherData> call, @NonNull Throwable t) {
                wind_tv.setText(t.getMessage());
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

    static int colRet(Double speed) {
        if(speed<=1.0) return(R.drawable.breeze);
        if(speed<=2.0) return(R.drawable.wind_icon);
        if(speed<=3.0) return(R.drawable.windy);
        if(speed<=4.0) return(R.drawable.wind);
        if(speed<=5.0) return(R.drawable.umbrella);
        if(speed<=6.0) return(R.drawable.tornado);
        return(R.drawable.tornado);
    }

}
