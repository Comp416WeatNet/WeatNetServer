package model;

public class WeatherResponseModel {
    private Coord coord;
    private Weather[] weather;
    private String base;
    private WeatherDetails main;
    private int visibility;
    private Wind wind;



    private class Coord{
        private double lon;
        private double lat;
    }
    private class Weather{
        private int id;
        private String main;
        private String description;
        private String icon;
    }
    private class WeatherDetails{
        private double temp;
        private double feels_like;
        private double temp_min;
        private int pressure;
        private int humidity;
    }
    private class Wind{
        private double speed;
        private int deg;
    }
    private class Rain{
        private double oneH;
    }
}
