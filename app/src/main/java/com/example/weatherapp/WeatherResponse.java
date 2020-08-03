package com.example.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


class Clouds {

    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}

class Coord {

    @SerializedName("lon")
    @Expose
    private Double lon;

    @SerializedName("lat")
    @Expose
    private Double lat;

    /**
     *
     * (Required)
     *
     */
    public Double getLon() {
        return lon;
    }

    /**
     *
     * (Required)
     *
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     *
     * (Required)
     *
     */
    public Double getLat() {
        return lat;
    }

    /**
     *
     * (Required)
     *
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

}

class Main {

    @SerializedName("temp")
    @Expose
    public Double temp;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("pressure")
    @Expose
    public Integer pressure;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("temp_min")
    @Expose
    public Double tempMin;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("temp_max")
    @Expose
    public Double tempMax;

    /**
     *
     * (Required)
     *
     */
    public Double getTemp() {
        return temp;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTemp(Double temp) {
        this.temp = temp;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getPressure() {
        return pressure;
    }

    /**
     *
     * (Required)
     *
     */
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     *
     * (Required)
     *
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    /**
     *
     * (Required)
     *
     */
    public Double getTempMin() {
        return tempMin;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    /**
     *
     * (Required)
     *
     */
    public Double getTempMax() {
        return tempMax;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

}

class Sys {

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("message")
    @Expose
    private Double message;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;

    @SerializedName("sunset")
    @Expose
    private Integer sunset;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

}

public class WeatherResponse {

    @SerializedName("coord")
    @Expose
    private Coord coord;

    @SerializedName("weather")
    @Expose
    private List<Object> weather = null;

    @SerializedName("base")
    @Expose
    private String base;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("visibility")
    @Expose
    private Integer visibility;

    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("clouds")
    @Expose
    private Clouds clouds;

    @SerializedName("dt")
    @Expose
    private Integer dt;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("cod")
    @Expose
    private Integer cod;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Object> getWeather() {
        return weather;
    }

    public void setWeather(List<Object> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    /**
     *
     * (Required)
     *
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getVisibility() {
        return visibility;
    }

    /**
     *
     * (Required)
     *
     */
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    /**
     *
     * (Required)
     *
     */
    public Wind getWind() {
        return wind;
    }

    /**
     *
     * (Required)
     *
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     *
     * (Required)
     *
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     *
     * (Required)
     *
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getDt() {
        return dt;
    }

    /**
     *
     * (Required)
     *
     */
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    /**
     *
     * (Required)
     *
     */
    public Sys getSys() {
        return sys;
    }

    /**
     *
     * (Required)
     *
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * (Required)
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * (Required)
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * (Required)
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getCod() {
        return cod;
    }

    /**
     *
     * (Required)
     *
     */
    public void setCod(Integer cod) {
        this.cod = cod;
    }

}

class Wind {

    @SerializedName("speed")
    @Expose
    private Double speed;

    @SerializedName("deg")
    @Expose
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

}