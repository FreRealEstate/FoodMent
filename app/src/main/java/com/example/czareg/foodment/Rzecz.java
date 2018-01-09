package com.example.czareg.foodment;

/**
 * Created by Czareg on 08.01.2018.
 */

public class Rzecz {
    private String nazwa;
    private String ilosc;
    private String jednostka;
    private String dataPrzydatnosci;
    public Rzecz(String nazwa, String ilosc, String jednostka,String data) {
        this.nazwa=nazwa;
        this.ilosc = ilosc;
        this.jednostka = jednostka;
        this.dataPrzydatnosci=data;
    }
    public Rzecz(String nazwa, String ilosc, String jednostka) {
        this.nazwa=nazwa;
        this.ilosc = ilosc;
        this.jednostka = jednostka;
        this.dataPrzydatnosci="-";
    }
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getIlosc() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
    }

    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public String getDataPrzydatnosci() {
        return dataPrzydatnosci;
    }

    public void setDataPrzydatnosci(String dataPrzydatnosci) {
        this.dataPrzydatnosci = dataPrzydatnosci;
    }
}
