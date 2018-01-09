package com.example.czareg.foodment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
        ArrayList <String> przepisyL = new ArrayList<String>();
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

        // wybieranie przepisu
        Random rand = new Random();
        int i=rand.nextInt(przepisyL.size());

        intent.putExtra("przepis", przepisyL.get(i));
        startActivity(intent);

    }
    private void goToPrzepisy() {
        Intent intent = new Intent(this, ListaPrzepisow.class);
        startActivity(intent);

    }
}
