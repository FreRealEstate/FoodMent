package com.example.czareg.foodment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.text.DecimalFormat;

public class Dieta extends AppCompatActivity {
    TextView bmi=null;
    TextView ocena=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);
        bmi= (TextView) findViewById(R.id.bmi);
        ocena= (TextView) findViewById(R.id.ocena1);

        Bundle bundle = getIntent().getExtras();
        if(!bundle.getString("wzrost").equals("") && !bundle.getString("waga").equals("") && !bundle.getString("wiek").equals("")) {
            double wzrost = (double) (Integer.parseInt(bundle.getString("wzrost"))) / 100;
            int waga = Integer.parseInt(bundle.getString("waga"));
            int wiek = Integer.parseInt(bundle.getString("wiek"));
            double bmiDouble = waga / (wzrost * wzrost);
            DecimalFormat dec = new DecimalFormat("#0.00");
            String bmiString = String.valueOf(dec.format(bmiDouble));
            String status="";
            if (bmiDouble < 18.5) {
                status = "Masz niedowage";
            } else if (bmiDouble> 18.5 && bmiDouble < 25) {
                status = "Prawidlowa waga";
            } else if (bmiDouble > 25 && bmiDouble < 30) {
                status = "Masz nadwage";
            } else if (bmiDouble == 30 || bmiDouble > 30) {
                status = "Jestes otyly! Schudnij!";
            }
            String wynik=bmiString+"\n\n"+status;
            bmi.setText(bmiString);
            ocena.setText(status);
        }else{
            bmi.setText("Brak danych");
            ocena.setText("Brak danych");
        }
    }
}
