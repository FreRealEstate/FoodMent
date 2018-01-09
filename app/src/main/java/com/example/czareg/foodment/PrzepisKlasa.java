package com.example.czareg.foodment;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Czareg on 09.01.2018.
 */

public class PrzepisKlasa {
    String nazwaLadna;
    String nazwaZRoszerzeniem;
    ArrayList<String> listaSkladnikow;
    ArrayList<String> listaPrzygotowania;
    int czasPrzygotowania; //w minutach
    ArrayList<String> listaPorJedzenia; //śniadanie/obiad/kolacja

    public PrzepisKlasa(String nazwaLadna, String nazwaZRoszerzeniem, ArrayList<String> listaSkladnikow, ArrayList<String> listaPrzygotowania, int czasPrzygotowania, ArrayList<String> poraJedzenia) {
        this.nazwaLadna = nazwaLadna;
        this.nazwaZRoszerzeniem = nazwaZRoszerzeniem;
        this.listaSkladnikow = listaSkladnikow;
        this.listaPrzygotowania = listaPrzygotowania;
        this.czasPrzygotowania = czasPrzygotowania;
        this.listaPorJedzenia = poraJedzenia;
    }

    public PrzepisKlasa(BufferedReader reader,String nazwaZRoszerzeniem){
        String mLine;
        this.listaSkladnikow = new ArrayList<String>(10);
        this.listaPrzygotowania = new ArrayList<String>(10);
        this.listaPorJedzenia = new ArrayList<String>(3);

        try {
            while (!((mLine = reader.readLine()).equals(";"))) {
                this.nazwaLadna = mLine;
                this.nazwaZRoszerzeniem = nazwaZRoszerzeniem;
            }

            while (!((mLine = reader.readLine()).equals(";"))) {
                listaSkladnikow.add(mLine);
            }

            while (!((mLine = reader.readLine()).equals(";"))) {
                listaPrzygotowania.add(mLine);
            }

            while (!((mLine = reader.readLine()).equals(";"))) {
                this.czasPrzygotowania = Integer.parseInt(reader.readLine());
            }

            while (!((mLine = reader.readLine()).equals(";"))) {
                listaPorJedzenia.add(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getNazwaLadna() {
        return nazwaLadna;
    }

    public void setNazwaLadna(String nazwaLadna) {
        this.nazwaLadna = nazwaLadna;
    }

    public String getNazwaZRoszerzeniem() {
        return nazwaZRoszerzeniem;
    }

    public void setNazwaZRoszerzeniem(String nazwaZRoszerzeniem) {
        this.nazwaZRoszerzeniem = nazwaZRoszerzeniem;
    }

    public ArrayList<String> getListaSkladnikow() {
        return listaSkladnikow;
    }

    public void setListaSkladnikow(ArrayList<String> listaSkladnikow) {
        this.listaSkladnikow = listaSkladnikow;
    }

    public ArrayList<String> getListaPrzygotowania() {
        return listaPrzygotowania;
    }

    public void setListaPrzygotowania(ArrayList<String> listaPrzygotowania) {
        this.listaPrzygotowania = listaPrzygotowania;
    }

    public int getCzasPrzygotowania() {
        return czasPrzygotowania;
    }

    public void setCzasPrzygotowania(int czasPrzygotowania) {
        this.czasPrzygotowania = czasPrzygotowania;
    }

    public ArrayList<String> getlistaPorJedzenia() {
        return listaPorJedzenia;
    }

    public void setlistaPorJedzenia(ArrayList<String> listaPorJedzenia) {
        this.listaPorJedzenia = listaPorJedzenia;
    }
}
