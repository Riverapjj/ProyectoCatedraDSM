package com.example.RP200198_ME180102_MH180095;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ContactoActivity extends AppCompatActivity {

    ImageButton btn_eliminar, btn_modificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        btn_eliminar = (ImageButton) findViewById(R.id.btn_eliminar);
        btn_modificar = (ImageButton) findViewById(R.id.btn_editar);

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactoActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }
}