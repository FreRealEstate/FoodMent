package com.example.czareg.foodment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Przepis extends AppCompatActivity {
    TextView nazwa=null;
    ImageView imageView=null;
    private ListView listaSkladnikow ;
    private ArrayAdapter<String> adapterSkladnikow ;
    private ListView listaPrzygotowania ;
    private ArrayAdapter<String> adapterPrzygotowania ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepis);
        nazwa= (TextView) findViewById(R.id.nazwa);
        imageView= (ImageView) findViewById(R.id.imageView);
        listaSkladnikow= (ListView) findViewById(R.id.listViewSkladniki);
        listaPrzygotowania= (ListView) findViewById(R.id.listViewPrzygotowanie);

        ArrayList<String> listaS=new ArrayList<String>();
        ArrayList<String> listaP=new ArrayList<String>();

        Bundle bundle = getIntent().getExtras();
        String nazwaPliku=bundle.getString("przepis");
        String nazwaPlikuBezRozsz=nazwaPliku.replace(".txt","");
        String ladnaNazwa="";
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
                listaS.add(mLine);
            }
            while (!((mLine = reader.readLine()).equals( ";"))){
                listaP.add(mLine);
            }


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
        nazwa.setText(ladnaNazwa);
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


    }
}
