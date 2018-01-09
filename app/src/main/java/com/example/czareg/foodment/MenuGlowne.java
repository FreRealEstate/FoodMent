package com.example.czareg.foodment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuGlowne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_glowne);

        final Button lodowka = (Button) findViewById(R.id.lodowka);

        lodowka.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToLodowka();
            }
        });
        final Button przepisy = (Button) findViewById(R.id.przepisy);

        przepisy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToPrzepisy();
            }
        });
        final Button dieta = (Button) findViewById(R.id.dieta);

        dieta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToDieta();
            }
        });
    }

    private void goToLodowka() {
        Intent intent = new Intent(this, Lodowka.class);
        startActivity(intent);

    }
    private void goToPrzepisy() {
        Intent intent = new Intent(this, Przepisy.class);
        startActivity(intent);

    }
    private void goToDieta() {
        Intent intent = new Intent(this, Dieta.class);
        Bundle bundle = getIntent().getExtras();
        intent.putExtra("wzrost", bundle.getString("wzrost"));
        intent.putExtra("waga", bundle.getString("waga"));
        intent.putExtra("wiek", bundle.getString("wiek"));
        startActivity(intent);

    }
}
