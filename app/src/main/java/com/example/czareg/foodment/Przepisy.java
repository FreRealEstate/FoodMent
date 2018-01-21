package com.example.czareg.foodment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

public class Przepisy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepisy);

        final Button zaproponuj = (Button) findViewById(R.id.zaproponuj);

        zaproponuj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToZaproponuj();
            }
        });

        final Button przepisy = (Button) findViewById(R.id.przepisy);

        przepisy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToPrzepisy();
            }
        });
    }
    private void goToZaproponuj() {
        Intent intent = new Intent(this, Przepis.class);
        //-----------------------------UZYSKANIE PRZEPISOW---------------------------------
        ArrayList <String> przepisyL = new ArrayList<String>();
        ArrayList <PrzepisKlasa> listaPrzepisow = new ArrayList<PrzepisKlasa>(10);

        String [] lista;
        try {
            lista = getAssets().list("");
            if (lista.length > 0) {
                for (String file : lista) {
                    if (file.contains(".txt")) {
                        przepisyL.add(file);

                        String nazwaLadna="";
                        String nazwaZRoszerzeniem="";
                        ArrayList<String> listaSkladnikow=new ArrayList<String>(10);
                        ArrayList<String> listaPrzygotowania= new ArrayList<String>(10);;
                        int czasPrzygotowania=0; //w minutach
                        ArrayList<String> listaPorJedzenia= new ArrayList<String>(4);

                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(
                                    new InputStreamReader(getAssets().open(file), "UTF-8"));
                            String mLine;
                            while (!((mLine = reader.readLine()).equals( ";"))){
                                nazwaLadna=mLine;
                                nazwaZRoszerzeniem=file;
                            }
                            while (!((mLine = reader.readLine()).equals( ";"))){
                                listaSkladnikow.add(mLine);
                            }
                            while (!((mLine = reader.readLine()).equals( ";"))){
                                listaPrzygotowania.add(mLine);
                            }
                            while (!((mLine = reader.readLine()).equals( ";"))){
                                czasPrzygotowania=Integer.parseInt(mLine);
                            }
                            while (!((mLine = reader.readLine()).equals( ";"))){
                                listaPorJedzenia.add(mLine);
                            }

                            listaPrzepisow.add(new PrzepisKlasa(nazwaLadna,nazwaZRoszerzeniem,listaSkladnikow,listaSkladnikow,czasPrzygotowania,listaPorJedzenia));

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        reader.close();
                    }
                }
            }
        } catch (IOException e) {
            Toast.makeText(Przepisy.this,"IOException",Toast.LENGTH_SHORT).show();
        }
        //-----------------------------UZYSKANIE PRZEPISOW---------------------------------

        //-----------------------------UZYSKANIE LODOWKI---------------------------------

        ArrayList<String> nazwyRzeczy=new ArrayList<>();
        Vector<Rzecz> lodowka=new Vector<Rzecz>();
        String lineFromFile;
        String filename = "lodowka.txt";
        String filepath = "settings";
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);

        if(myExternalFile.exists()){
            try {
                FileInputStream fis = new FileInputStream(myExternalFile);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(in));
                while((lineFromFile=br.readLine())!=null){
                    StringTokenizer tokens=new StringTokenizer(lineFromFile,";");
                    Rzecz rzecz=new Rzecz(tokens.nextToken(),tokens.nextToken(),tokens.nextToken(),tokens.nextToken());
                    lodowka.add(rzecz);
                    nazwyRzeczy.add(rzecz.getNazwa());
                }
                in.close();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }

        //-----------------------------UZYSKANIE LODOWKI---------------------------------

        //-----------------------------UZYSKANIE BMI---------------------------------
        Bundle bundle = getIntent().getExtras();
        double wzrost = (double) (Integer.parseInt(bundle.getString("wzrost"))) / 100;
        int waga = Integer.parseInt(bundle.getString("waga"));
        int wiek = Integer.parseInt(bundle.getString("wiek"));
        double bmiDouble = waga / (wzrost * wzrost);
        DecimalFormat dec = new DecimalFormat("#0.00");
        String bmiString = String.valueOf(dec.format(bmiDouble));
        //-----------------------------UZYSKANIE BMI---------------------------------


        //-----------------------------UZYSKANIE CZASU---------------------------------

        DateFormat df = new SimpleDateFormat("HH");
        String date = df.format(Calendar.getInstance().getTime());
        int aktualnaGodzina=Integer.parseInt(date);
        //Toast.makeText(Przepisy.this,date,Toast.LENGTH_SHORT).show(); TESTOWANIE DATY 1/1

        //-----------------------------UZYSKANIE CZASU---------------------------------

        //-----------------------------UTWORZENIE MAPY PUNKTACJI---------------------------------

        SortedMap<Integer, PrzepisKlasa> mapaPunktacji = new TreeMap(Collections.reverseOrder());


        //Vector<Integer> vPunktow=new Vector<>(); TESTOWANIE PROPOZYCJI 1/3
        //Vector<String> vNazw=new Vector<>();


        for(int i=0;i<listaPrzepisow.size();i++){
            int punktacja=0;
            if(bmiDouble>30 && listaPrzepisow.get(i).getlistaPorJedzenia().contains("dietetyczne")){
                    punktacja+=1;
            }
            if(aktualnaGodzina>5 && aktualnaGodzina<12 && listaPrzepisow.get(i).getlistaPorJedzenia().contains("śniadanie")){
                punktacja+=1;
            }
            if(aktualnaGodzina>=12 && aktualnaGodzina<16 && listaPrzepisow.get(i).getlistaPorJedzenia().contains("obiad")){
                punktacja+=1;
            }
            if(aktualnaGodzina>=16 && aktualnaGodzina<20 && listaPrzepisow.get(i).getlistaPorJedzenia().contains("kolacja")){
                punktacja+=1;
            }

            ArrayList<String> duplicatevalues=new ArrayList<>();
            for (String finalval : nazwyRzeczy) {
                if (listaPrzepisow.get(i).getListaSkladnikow().contains(finalval)) {
                    duplicatevalues.add(finalval);
                }
            }

            punktacja+=duplicatevalues.size();

            //vNazw.add(listaPrzepisow.get(i).getNazwaZRoszerzeniem()); TESTOWANIE PROPOZYCJI 2/3
            //vPunktow.add(punktacja);
            mapaPunktacji.put(punktacja,listaPrzepisow.get(i));
        }


        //-----------------------------UTWORZENIE MAPY PUNKTACJI---------------------------------



        /* TESTOWANIE PROPOZYCJI 3/3
        StringBuilder s=new StringBuilder();

        for(int i=0;i<listaPrzepisow.size();i++){
            s.append(vNazw.get(i)+" ");
            s.append(vPunktow.get(i)+" ");
        }
        Toast.makeText(Przepisy.this,s.toString(),Toast.LENGTH_LONG).show();
        */
        intent.putExtra("przepis", mapaPunktacji.get(mapaPunktacji.firstKey()).getNazwaZRoszerzeniem());
        startActivity(intent);

    }
    private void goToPrzepisy() {
        Intent intent = new Intent(this, ListaPrzepisow.class);
        startActivity(intent);

    }
}
