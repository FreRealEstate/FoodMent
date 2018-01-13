package com.example.czareg.foodment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Lodowka extends AppCompatActivity {
    private static final String TAG = Lodowka.class.getSimpleName();
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
                }
                in.close();
                setTextToTextView();

            } catch (IOException e) {
                e.getStackTrace();
                Log.d(TAG, "IOException e");
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

        if( n != null && !n.isEmpty() && il != null && !il.isEmpty() && je != null && !je.isEmpty() && d != null && !d.isEmpty()){
            Rzecz nowaRzecz=new Rzecz(n,il,je,d);
            lodowka.add(nowaRzecz);
            setTextToTextView();
            saveData();
            nazwa.setText("");
            ilosc.setText("");
            jednostka.setText("");
            data.setText("");
            Toast.makeText(Lodowka.this,"Dodano!",Toast.LENGTH_SHORT).show();

        } else{
            Toast.makeText(Lodowka.this,"Uzupełnij wszystkie pola!",Toast.LENGTH_SHORT).show();
        }

    }
    void btnDelData(View v) {
        String n=nazwa.getText().toString();
        for(int i=0;i<lodowka.size();i++){
            if(lodowka.get(i).getNazwa().equals(n)){
                lodowka.remove(i);
                setTextToTextView();
                saveData();
                nazwa.setText("");
                ilosc.setText("");
                jednostka.setText("");
                data.setText("");
                Toast.makeText(Lodowka.this,"Usunieto!",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    void saveData(){
        String lineFromFile;
        String filename = "lodowka.txt";
        String filepath = "settings";
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        try{
            FileOutputStream fos = new FileOutputStream(myExternalFile);

            for(int i=0;i<lodowka.size();i++)
            {
                fos.write(lodowka.get(i).getNazwa().getBytes());
                fos.write(";".getBytes());
                fos.write(lodowka.get(i).getIlosc().getBytes());
                fos.write(";".getBytes());
                fos.write(lodowka.get(i).getJednostka().getBytes());
                fos.write(";".getBytes());
                fos.write(lodowka.get(i).getDataPrzydatnosci().getBytes());
                fos.write("\n".getBytes());
            }

            fos.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
