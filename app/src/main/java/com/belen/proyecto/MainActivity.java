package com.belen.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button BLogin;
    private Button BRegistro;
    private EditText user;
    private EditText password;
    private AdminSQL baseDatos;
    public void onStart(){
        super.onStart();

        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = AdminSQL.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
    }
    public void onDestroy(){
        super.onDestroy();
        if (baseDatos!=null){    // Pechamos a base de datos.
            baseDatos.pecharBD();
            baseDatos=null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BLogin = (Button) findViewById(R.id.Login);
        BRegistro = (Button) findViewById(R.id.registro);
        user=(EditText) findViewById(R.id.user);
        password=(EditText) findViewById(R.id.password);
        final RadioGroup opciones = findViewById(R.id.Opciones);
        final RadioButton isAdmin = findViewById(R.id.isAdmin);
        final RadioButton isUser = findViewById(R.id.isUser);
        user.setVisibility(user.INVISIBLE);
        password.setVisibility(password.INVISIBLE);
        BLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), user_main.class);
                startActivityForResult(intent, 0);
            }
        });
        opciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int seleccionado = opciones.getCheckedRadioButtonId();
                if (seleccionado == isAdmin.getId()) {
                    user.setVisibility(user.VISIBLE);
                    password.setVisibility(password.VISIBLE);
                    BLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String usuario=user.getText().toString();
                            String pwd=password.getText().toString();
                            boolean ok=baseDatos.verificar(usuario,pwd,1);
                            if (ok){
                                Intent intent = new Intent (v.getContext(), admin_main.class);
                                startActivityForResult(intent, 0);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Incorrecto",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else if (seleccionado == isUser.getId()) {
                    user.setVisibility(user.INVISIBLE);
                    password.setVisibility(password.INVISIBLE);
                    BLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent (v.getContext(), user_main.class);
                            startActivityForResult(intent, 0);
                        }
                    });
                }
            }

        });
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        BRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Registro.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}