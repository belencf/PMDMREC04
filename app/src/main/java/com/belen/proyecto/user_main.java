package com.belen.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class user_main extends AppCompatActivity {
    private ListView listitems;
    private Adaptador adaptador;
    private AdminSQL baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
       listitems=(ListView) findViewById(R.id.ListProv);
       ArrayList<Provincia> lista=new ArrayList<>();
        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = AdminSQL.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
       lista=baseDatos.recuperaProvincias();
       adaptador=new Adaptador(this,lista);
       listitems.setAdapter(adaptador);
   }

}


