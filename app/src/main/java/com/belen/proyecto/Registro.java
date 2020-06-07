package com.belen.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    private EditText user, pwd, name, lastname, mail;
    private Button Breg;
    private AdminSQL baseDatos;

    private boolean validar(String dato) {
        if (dato.length() == 0 || dato.contains(" ")) {
            return false;
        } else {
            return true;
        }
    }
    private void gestion(){
        Breg = (Button) findViewById(R.id.reg);
        Breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = (EditText) findViewById(R.id.usname);
                pwd = (EditText) findViewById(R.id.pwd);
                name = (EditText) findViewById(R.id.nombre);
                lastname = (EditText) findViewById(R.id.apellidos);
                mail = (EditText) findViewById(R.id.mail);
                if (validar(user.getText().toString()) & (validar(pwd.getText().toString()))) {
                    Usuario admin = new Usuario(user.getText().toString(), pwd.getText().toString(), name.getText().toString(),
                            lastname.getText().toString(), mail.getText().toString());

                }
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();

        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = AdminSQL.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Breg = (Button) findViewById(R.id.reg);
        Breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = (EditText) findViewById(R.id.usname);
                pwd = (EditText) findViewById(R.id.pwd);
                name = (EditText) findViewById(R.id.nombre);
                lastname = (EditText) findViewById(R.id.apellidos);
                mail = (EditText) findViewById(R.id.mail);
                if (validar(user.getText().toString()) & (validar(pwd.getText().toString()))) {
                    Usuario admin = new Usuario(user.getText().toString(), pwd.getText().toString(), name.getText().toString(),
                            lastname.getText().toString(), mail.getText().toString());
                    boolean existe = baseDatos.verificar(admin.getUser(), admin.getPass(), 0);
                    if (!existe) {
                        long id = baseDatos.adduser(admin);
                        Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivityForResult(intent, 0);
                    } else {
                        Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User y pass son obligatorios y sin espacios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


