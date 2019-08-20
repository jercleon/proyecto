package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Consultar extends AppCompatActivity {
    private Spinner spinner1;
    public static String stringKey = "skey";
    public String seleccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        spinner1= (Spinner)findViewById(R.id.spinner);
        String [] opciones= {"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spinner1.setAdapter(adapter);
    }
    public void consumo_mes(View view){
        seleccion = spinner1.getSelectedItem().toString();
        lanzarAct(seleccion);
    }
    public void Ahorro(View view){
        Intent i = new Intent(this, Ahorro.class);
        startActivity(i);
    }
    public void Salir (View view){
        finish();
    }

    private void lanzarAct(String val){
        Intent i = new Intent(this, consumo_por_mes.class);
        i.putExtra(stringKey,val);
        startActivity(i);
    }





}
