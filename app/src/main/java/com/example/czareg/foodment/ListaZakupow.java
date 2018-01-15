package com.example.czareg.foodment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ListaZakupow extends AppCompatActivity {
    private static final String TAG = ListaZakupow.class.getSimpleName();
    TextView zawartosc=null;
    TextView nazwa=null;
    Button dodawanie=null;
    Button usuwanie=null;
    Button usunWszystko=null;
    public Vector<String> listaZakupow=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zakupow);
        listaZakupow=new Vector<String>();

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
        usunWszystko = (Button) findViewById(R.id.usunWszystko);
        usunWszystko.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnDelAllData(v);
            }
        });
        nazwa = (TextView) findViewById(R.id.listaPrzepisow);
        loadData();
    }


    void loadData(){
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
                setTextToTextView();

            } catch (IOException e) {
                e.getStackTrace();
                Log.d(TAG, "IOException e");
            }
        }else {
            zawartosc.setText("Lista zakupów pusta. Dodaj przedmioty");
        }
    }
    void setTextToTextView(){
        String text="";

        for(int i=0;i<listaZakupow.size();i++){
            text=text+listaZakupow.get(i)+"\n";
        }
        zawartosc.setText(text);
        if(listaZakupow.size()==0)
            zawartosc.setText("Lista zakupów pusta. Dodaj przedmioty");
    }

    void btnAddData(View v){
        String n=nazwa.getText().toString();

        if( n != null && !n.isEmpty()){
            listaZakupow.add(n);
            setTextToTextView();
            saveData();
            nazwa.setText("");
            Toast.makeText(ListaZakupow.this,"Dodano!",Toast.LENGTH_SHORT).show();

        } else{
            Toast.makeText(ListaZakupow.this,"Podaj nazwe produktu!",Toast.LENGTH_SHORT).show();
        }

    }
    void btnDelData(View v) {
        String n=nazwa.getText().toString();
        for(int i=0;i<listaZakupow.size();i++){
            if(listaZakupow.get(i).equals(n)){
                listaZakupow.remove(i);
                setTextToTextView();
                saveData();
                nazwa.setText("");
                Toast.makeText(ListaZakupow.this,"Usunieto!",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    void btnDelAllData(View v) {
        if(listaZakupow.size()>0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ListaZakupow.this);
            builder.setMessage("Na pewno chcesz usunąć wszystko z listy?");
            builder.setCancelable(true);
            builder.setNegativeButton("Usuń wszystkie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int g) {
                    listaZakupow.clear();
                    setTextToTextView();
                    saveData();
                    nazwa.setText("");
                    Toast.makeText(ListaZakupow.this, "Usunieto!", Toast.LENGTH_SHORT).show();

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
            else{
            Toast.makeText(ListaZakupow.this, "Nie ma nic do usunięcia!", Toast.LENGTH_SHORT).show();

        }
        }

    void saveData(){
        String lineFromFile;
        String filename = "listaZakupow.txt";
        String filepath = "settings";
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
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
    }
}
