package com.stebanramos.challengeintergrupo.models;

public class Country {

    private String nombre;
    private String capital;
    private String region;

    public Country(String nombre, String capital, String region) {
        this.nombre = nombre;
        this.capital = capital;
        this.region = region;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
