package com.example.czareg.foodment;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

public class Przepis extends AppCompatActivity {
    TextView nazwa=null;
    ImageView imageView=null;
    private ListView listaSkladnikow ;
    private ArrayAdapter<String> adapterSkladnikow ;
    private ListView listaPrzygotowania ;
    private ArrayAdapter<String> adapterPrzygotowania ;
    private ArrayList<String> listaBrakujacychPrzedmiotow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepis);
        nazwa= (TextView) findViewById(R.id.nazwa);
        imageView= (ImageView) findViewById(R.id.imageView);
        listaSkladnikow= (ListView) findViewById(R.id.listViewSkladniki);
        listaPrzygotowania= (ListView) findViewById(R.id.listViewPrzygotowanie);

        final Button dodaj = (Button) findViewById(R.id.dodaj);

        listaBrakujacychPrzedmiotow=new ArrayList<String>(5);

        ArrayList<String> listaS=new ArrayList<String>(10);
        ArrayList<String> listaP=new ArrayList<String>(10);

        Bundle bundle = getIntent().getExtras();
        String nazwaPliku=bundle.getString("przepis");
        String nazwaPlikuBezRozsz=nazwaPliku.replace(".txt","");
        String ladnaNazwa="";
        int czasPrzygotowania=0;


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

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(nazwaPliku), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            int i=1;
            while (!((mLine = reader.readLine()).equals( ";"))){
                ladnaNazwa=mLine;
            }
            while (!((mLine = reader.readLine()).equals( ";"))){
                if(nazwyRzeczy.contains(mLine)){
                    listaS.add(mLine+" (w lodówce)");
                }else{
                    listaS.add(mLine);
                    listaBrakujacychPrzedmiotow.add(mLine);
                }

            }
            while (!((mLine = reader.readLine()).equals( ";"))){
                listaP.add(mLine);
            }
            czasPrzygotowania=Integer.parseInt(reader.readLine());

            } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        nazwa.setText(ladnaNazwa+" ("+czasPrzygotowania+" min)");
        AssetManager assetManager = getAssets();
        InputStream is = null;
        try {
            String path=nazwaPlikuBezRozsz+".jpg";
            is = assetManager.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        imageView.setImageBitmap(bitmap);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.wiersz, listaS);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.wiersz, listaP);

        listaSkladnikow.setAdapter(adapter1);
        listaPrzygotowania.setAdapter(adapter2);

        dodaj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dodawanieDoListyZakupow();
            }
        });
    }

    void dodawanieDoListyZakupow(){
        int iloscPosiadanychProduktow=listaBrakujacychPrzedmiotow.size();
        if(iloscPosiadanychProduktow>0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Przepis.this);
            String polszczyznaTakaUpierdliwa=" przedmiotów ";
            if(iloscPosiadanychProduktow==1) polszczyznaTakaUpierdliwa=" przedmiot ";
            if(iloscPosiadanychProduktow==2 ||iloscPosiadanychProduktow==3 || iloscPosiadanychProduktow==4) polszczyznaTakaUpierdliwa=" przedmioty ";
            builder.setMessage("Na pewno chcesz dodać " + iloscPosiadanychProduktow + polszczyznaTakaUpierdliwa+"do listy zakupów?");
            builder.setCancelable(true);
            builder.setNegativeButton("Dodaj wszystkie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int g) {
                    //ODCZYT Z PLIKU
                    Vector<String> listaZakupow=new Vector<String>(5);
                    String lineFromFile;
                    String filename = "listaZakupow.txt";
                    String filepath = "settings";
                    File myExternalFile = new File(getExternalFilesDir(filepath), filename);

                    if(myExternalFile.exists()){
                        try {
                            FileInputStream fis = new FileInputStream(myExternalFile);
                            DataInputStream in = new DataInputStream(fis);
                            BufferedReader br =
                                    new BufferedReader(new InputStreamReader(in));

                            while((lineFromFile=br.readLine())!=null){
                                listaZakupow.add(lineFromFile);
                            }
                            in.close();

                        } catch (IOException e) {
                            e.getStackTrace();
                        }
                    }
                    //DODAJEMY BRAKUJACE

                    for(int i=0;i<listaBrakujacychPrzedmiotow.size();i++){
                        if(!(listaZakupow.contains(listaBrakujacychPrzedmiotow.get(i))))
                            listaZakupow.add(listaBrakujacychPrzedmiotow.get(i));
                    }

                    //ZAPIS DO PLIKU
                    try{
                        FileOutputStream fos = new FileOutputStream(myExternalFile);

                        for(int i=0;i<listaZakupow.size();i++)
                        {
                            fos.write(listaZakupow.get(i).getBytes());
                            fos.write("\n".getBytes());
                        }

                        fos.close();
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                    Toast.makeText(Przepis.this,"Dodano",Toast.LENGTH_SHORT).show();


                }
            });
            builder.setPositiveButton("Cofnij", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int g) {
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
