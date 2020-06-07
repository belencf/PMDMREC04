package com.belen.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class admin_main extends AppCompatActivity {
    private Button add;
    private Button asign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        add=(Button) findViewById(R.id.fase_add);
        asign=(Button) findViewById(R.id.fase_select);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Fase_add.class);
                startActivityForResult(intent,0);
            }
        });
        asign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Fase_select.class);
                startActivityForResult(intent,0);
            }

        });
    }
}
