package com.example.RP200198_ME180102_MH180095;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.RP200198_ME180102_MH180095.basededatos.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactoActivity extends AppCompatActivity {

    private ImageButton btn_eliminar, btn_modificar;
    private TextView lbl_nombre, txt_num, txt_correo;
    private FloatingActionButton btn_atras;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        btn_eliminar = (ImageButton) findViewById(R.id.btn_eliminar);
        btn_modificar = (ImageButton) findViewById(R.id.btn_editar);
        lbl_nombre = (TextView) findViewById(R.id.nombre_contacto);
        txt_num = (TextView) findViewById(R.id.txt_num);
        txt_correo = (TextView) findViewById(R.id.txt_correo);
        btn_atras = (FloatingActionButton) findViewById(R.id.btn_atras);
        dbHelper = new DBHelper(this);
        Intent intent = getIntent();

        lbl_nombre.setText(intent.getStringExtra("nombre").concat(" " + intent.getStringExtra("apellido")));
        lbl_nombre.setId(intent.getIntExtra("id", -1));
        txt_num.setText(intent.getStringExtra("numero"));
        txt_correo.setText(intent.getStringExtra("correo"));

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactoActivity.this, MainActivity.class);
                dbHelper.eliminar_contacto(lbl_nombre.getId());
                startActivity(intent);
            }
        });

        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModificar = new Intent(ContactoActivity.this, InfoActivity.class);
                intentModificar.putExtra("id", lbl_nombre.getId());
                intentModificar.putExtra("nombre", intent.getStringExtra("nombre"));
                intentModificar.putExtra("apellido", intent.getStringExtra("apellido"));
                intentModificar.putExtra("numero", intent.getStringExtra("numero"));
                intentModificar.putExtra("correo", intent.getStringExtra("correo"));
                startActivity(intentModificar);
            }
        });
    }
}