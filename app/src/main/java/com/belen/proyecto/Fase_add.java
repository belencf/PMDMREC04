package com.belen.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Fase_add extends AppCompatActivity {
    EditText fasename;
    EditText fasetext;
    Button guardar;
    AdminSQL baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fase_add);
        fasename=(EditText) findViewById(R.id.faseName);
        fasetext=(EditText) findViewById(R.id.caract);
        guardar=(Button) findViewById(R.id.save);
        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = AdminSQL.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fasename.length()!=0) {
                    Fase fase = new Fase(fasename.getText().toString(), fasetext.getText().toString());
                    long r = baseDatos.addfase(fase);
                    if (r != -1) {
                        Toast.makeText(getApplicationContext(), "Creada Fase", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), admin_main.class);
                        startActivityForResult(intent, 0);
                    }
                }else{Toast.makeText(getApplicationContext(), "La fase no puede ir en blanco", Toast.LENGTH_LONG).show();

                }
            }


        });

    }
}
