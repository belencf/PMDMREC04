package com.belen.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Fase_select extends AppCompatActivity {
    private Spinner spn2;
    private Spinner spn3;
    Button asignar;
    AdminSQL baseDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fase_select);
        spn2 = (Spinner)findViewById(R.id.Provincias);
        spn3 = (Spinner)findViewById(R.id.fases);
        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = AdminSQL.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
        ArrayList<String> pr = baseDatos.recuperanombreProvincias();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_personalizado,pr);
        spn2.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ArrayList<String> fases = baseDatos.recuperanombreFase();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item_personalizado, fases);
        spn3.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        asignar=(Button) findViewById(R.id.saved);
        asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r=baseDatos.updatefase(spn2.getSelectedItem().toString(),spn3.getSelectedItem().toString());
                if (r>0){
                    Toast.makeText(getApplicationContext(),"Asignada Fase" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(v.getContext(), admin_main.class);
                    startActivityForResult(intent, 0);
                }
            }


        });
    }
}
