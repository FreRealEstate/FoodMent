package com.example.czareg.foodment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText wzrostTxt, wagaTxt, wiekTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wzrostTxt=(EditText) findViewById(R.id.wzrost);
        wagaTxt=(EditText) findViewById(R.id.waga);
        wiekTxt=(EditText) findViewById(R.id.wiek);
        final Button gotowe = (Button) findViewById(R.id.gotowe);
        gotowe.setEnabled(true);

        gotowe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToMenu();
            }
        });

        wiekTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //gotowe.setEnabled(!charSequence.equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void goToMenu() {

        Intent intent = new Intent(this, MenuGlowne.class);
        intent.putExtra("wzrost", wzrostTxt.getText().toString());
        intent.putExtra("waga", wagaTxt.getText().toString());
        intent.putExtra("wiek", wiekTxt.getText().toString());
        startActivity(intent);

    }
}
