package com.example.czareg.foodment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ListaPrzepisow extends AppCompatActivity {
    ArrayList<String> przepisyL;
    private ListView list ;
    private ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_przepisow);
        list = (ListView) findViewById(R.id.listView);


        przepisyL = new ArrayList<String>();
            String [] lista;
            try {
                lista = getAssets().list("");
                if (lista.length > 0) {
                    for (String file : lista) {
                        if(file.contains(".txt")) {
                            przepisyL.add(file);
                        }
                    }
                }
            } catch (IOException e) {

            }

        adapter = new ArrayAdapter<String>(this, R.layout.wiersz, przepisyL);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToPrzepis(i);
            }
        });
    }
    private void goToPrzepis(int i) {
        Intent intent = new Intent(this, Przepis.class);
        intent.putExtra("przepis", przepisyL.get(i));
        startActivity(intent);

    }
}
