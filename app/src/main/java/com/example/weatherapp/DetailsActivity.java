package com.example.weatherapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import pojo.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    public static String BaseUrl="http://api.openweathermap.org/";
    public static String Appid="ff8451c2173751c798dc36086bf95a7b";
    public static String lat;
    public static String lon;
    public static String city="Kolkata,IN";
    public static String unit="metric";

    DrawerLayout dl;
    NavigationView nv;
    TextView det_tv1, det_tv2, det_tv3;
    CardView det_cv1, det_cv2, det_cv3;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        lat=SplashActivity.lat;
        lon=SplashActivity.lon;

        //Initialize views from the layout
        dl = findViewById(R.id.drawerLayout);
        nv = findViewById(R.id.navigationView);
        det_tv1 = findViewById(R.id.otherTextView1);
        det_tv2=findViewById(R.id.otherTextView2);
        det_tv3=findViewById(R.id.otherTextView3);
        det_cv1=findViewById(R.id.otherCardView1);
        det_cv2=findViewById(R.id.otherCardView2);
        det_cv3=findViewById(R.id.otherCardView3);

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
                        startActivity(new Intent(DetailsActivity.this, SearchActivity.class));
                        break;
                    case R.id.temp:
                        startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                        break;
                    case R.id.wind:
                        startActivity(new Intent(DetailsActivity.this, WindActivity.class));
                        break;
                    case R.id.sun:
                        startActivity(new Intent(DetailsActivity.this, SunActivity.class));
                        break;
                    case R.id.details:
                        startActivity(new Intent(DetailsActivity.this, DetailsActivity.class));
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

                    Double lon=weatherResponse.getCoord().getLon();
                    Double lat=weatherResponse.getCoord().getLat();
                    String country=weatherResponse.sys.getCountry();
                    String[] c=city.split(",");
                    Integer press=weatherResponse.main.getPressure();
                    Integer hum=weatherResponse.main.getHumidity();

                    String stringBuilder1="\n"+
                            "Longitude: "+ lon +
                            "\n"+
                            "Latitude: "+ lat
                            ;

                    String stringBuilder2="\n"+
                            "Country: "+ country+
                            "\n"+
                            "City: "+c[0]
                            ;

                    String stringBuilder3="\n"+
                            "Today's Pressure: "+ press+" Hg"+
                            "\n"+
                            "Today's Humidity: "+hum+" g/m3"
                            ;

                    det_tv1.setText(stringBuilder1);
                    det_tv2.setText(stringBuilder2);
                    det_tv3.setText(stringBuilder3);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                det_tv1.setText(t.getMessage());
                det_tv2.setText(t.getMessage());
                det_tv3.setText(t.getMessage());
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
                det_tv1.setText(t.getMessage());
                det_tv2.setText(t.getMessage());
                det_tv3.setText(t.getMessage());
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
