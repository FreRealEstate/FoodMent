package com.example.czareg.foodment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Lodowka extends AppCompatActivity {
    TextView zawartosc=null;
    TextView nazwa=null;
    TextView ilosc=null;
    TextView jednostka=null;
    TextView data=null;
    Button dodawanie=null;
    Button usuwanie=null;
    public Vector<Rzecz> lodowka=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodowka);
        lodowka=new Vector<Rzecz>();

        zawartosc= (TextView) findViewById(R.id.zawartosc);
        dodawanie = (Button) findViewById(R.id.dodawanie);

        dodawanie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnAddData(v);
            }
        });
        usuwanie = (Button) findViewById(R.id.usuwanie);
        usuwanie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnDelData(v);
            }
        });
        nazwa = (TextView) findViewById(R.id.listaPrzepisow);
        ilosc = (TextView)findViewById(R.id.ilosc);
        jednostka = (TextView) findViewById(R.id.jednostka);
        data = (TextView) findViewById(R.id.data);
        loadData();
    }

    void loadData(){
        lodowka.clear();

        File file=getApplicationContext().getFileStreamPath("lodowka.txt");
        String lineFromFile;

        if(file.exists()){
            try {
                BufferedReader reader=new BufferedReader(new InputStreamReader(openFileInput("lodowka.txt")));
                while((lineFromFile=reader.readLine())!=null){
                    StringTokenizer tokens=new StringTokenizer(lineFromFile,";");
                    Rzecz rzecz=new Rzecz(tokens.nextToken(),tokens.nextToken(),tokens.nextToken(),tokens.nextToken());
                    lodowka.add(rzecz);
                }
                reader.close();
                setTextToTextView();

            } catch (FileNotFoundException e) {
                e.getStackTrace();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }else {
            zawartosc.setText("Lodowka pusta. Dodaj przedmioty");
        }
    }
    void setTextToTextView(){
        String text="";

        for(int i=0;i<lodowka.size();i++){
            text=text+lodowka.get(i).getNazwa() + " " +lodowka.get(i).getIlosc() + " " +lodowka.get(i).getJednostka() + " " +lodowka.get(i).getDataPrzydatnosci() + "\n";
        }
        zawartosc.setText(text);
        if(lodowka.size()==0)
            zawartosc.setText("Lodowka pusta. Dodaj przedmioty");
    }

    void btnAddData(View v){
        String n=nazwa.getText().toString();
        String il=ilosc.getText().toString();
        String je=jednostka.getText().toString();
        String d=data.getText().toString();

        Rzecz nowaRzecz=new Rzecz(n,il,je,d);
        lodowka.add(nowaRzecz);
        setTextToTextView();
        saveData();


    }
    void btnDelData(View v) {
        String n=nazwa.getText().toString();
        for(int i=0;i<lodowka.size();i++){
            if(lodowka.get(i).getNazwa().equals(n)){
                lodowka.remove(i);
                setTextToTextView();
                saveData();
                //Toast.makeText(Lodowka.this,"Usunieto!",Toast.LENGTH_SHORT).show();
                break;
            }else{
                Toast.makeText(Lodowka.this,"Brak produktu o podanej nazwie!",Toast.LENGTH_SHORT).show();
            }

        }
    }
    void saveData(){
        try{
            FileOutputStream file=openFileOutput("lodowka.txt",MODE_PRIVATE);
            OutputStreamWriter outputFile=new OutputStreamWriter(file);
            for(int i=0;i<lodowka.size();i++)
            {
                outputFile.write(lodowka.get(i).getNazwa() + ";" +lodowka.get(i).getIlosc() + ";" +lodowka.get(i).getJednostka() + ";" +lodowka.get(i).getDataPrzydatnosci() + "\n");
            }
            outputFile.flush();
            outputFile.close();

            //Toast.makeText(Lodowka.this,"Dodano!",Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
