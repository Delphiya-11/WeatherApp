package com.example.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class City {

    /**
     *
     * (Required)
     *
     */
    @SerializedName("id")
    @Expose
    private Integer id;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("name")
    @Expose
    private String name;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("coord")
    @Expose
    private Coords coord;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("country")
    @Expose
    private String country;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("population")
    @Expose
    private Integer population;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("timezone")
    @Expose
    private Integer timezone;

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
    public Coords getCoord() {
        return coord;
    }

    /**
     *
     * (Required)
     *
     */
    public void setCoord(Coords coord) {
        this.coord = coord;
    }

    /**
     *
     * (Required)
     *
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * (Required)
     *
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     *
     * (Required)
     *
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTimezone() {
        return timezone;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

}

class Coords {

    /**
     *
     * (Required)
     *
     */
    @SerializedName("lat")
    @Expose
    private Double lat;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("lon")
    @Expose
    private Double lon;

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

}

public class FutureWeather {

    /**
     *
     * (Required)
     *
     */
    @SerializedName("cod")
    @Expose
    private String cod;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("message")
    @Expose
    private Double message;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("list")
    @Expose
    private List<Object> list = null;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("city")
    @Expose
    private City city;

    /**
     *
     * (Required)
     *
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * (Required)
     *
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * (Required)
     *
     */
    public Double getMessage() {
        return message;
    }

    /**
     *
     * (Required)
     *
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     *
     * (Required)
     *
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     *
     * (Required)
     *
     */
    public List<Object> getList() {
        return list;
    }

    /**
     *
     * (Required)
     *
     */
    public void setList(List<Object> list) {
        this.list = list;
    }

    /**
     *
     * (Required)
     *
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * (Required)
     *
     */
    public void setCity(City city) {
        this.city = city;
    }

}
