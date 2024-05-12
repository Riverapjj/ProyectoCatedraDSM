package com.example.RP200198_ME180102_MH180095;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RP200198_ME180102_MH180095.basededatos.DBHelper;
import com.example.RP200198_ME180102_MH180095.modelos.Contactos;

public class InfoActivity extends AppCompatActivity {

    private EditText txt_nombre, txt_apellido, txt_num, txt_correo;
    private ImageButton btn_cancelar, btn_guardar;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();

        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_nombre.setId(intent.getIntExtra("id",-1));

        txt_apellido = (EditText) findViewById(R.id.txt_apellido);
        txt_num = (EditText) findViewById(R.id.txt_num);
        txt_correo = (EditText) findViewById(R.id.txt_correo);
        btn_cancelar = (ImageButton) findViewById(R.id.btn_cancelar);
        btn_guardar = (ImageButton) findViewById(R.id.btn_guardar);
        dbHelper = new DBHelper(this);

        if (!intentIsEmpty(intent)) {
            txt_nombre.setText(intent.getStringExtra("nombre"));
            txt_apellido.setText(intent.getStringExtra("apellido"));
            txt_num.setText(intent.getStringExtra("numero"));
            txt_correo.setText(intent.getStringExtra("correo"));
        }

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intentIsEmpty(intent)) {
                    try {
                        dbHelper.agregar_contacto(new Contactos(txt_nombre.getText().toString().trim(), txt_apellido.getText().toString().trim(),
                                txt_num.getText().toString().trim(), txt_correo.getText().toString().trim()));

                        Toast.makeText(getApplicationContext(), "Contacto agregado a la agenda", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Error al agregar contacto", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    try {
                        dbHelper.editar_contacto(new Contactos(txt_nombre.getText().toString().trim(), txt_apellido.getText().toString().trim(),
                                txt_num.getText().toString().trim(), txt_correo.getText().toString().trim()), txt_nombre.getId());

                        Toast.makeText(getApplicationContext(), "Contacto actualizado con exito", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al actualizar contacto", Toast.LENGTH_SHORT).show();
                    }
                }

                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean intentIsEmpty(Intent intent) {
        if (intent != null && intent.hasExtra("id")) {
            return false;
        } else {
            return true;
        }
    }

}