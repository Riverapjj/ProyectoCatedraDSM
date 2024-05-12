package com.example.RP200198_ME180102_MH180095;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import 	android.content.pm.PackageInfo;
import java.net.URI;


import com.example.RP200198_ME180102_MH180095.basededatos.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactoActivity extends AppCompatActivity {

    private ImageButton btn_eliminar, btn_modificar, btn_whatsapp;
    private TextView lbl_nombre, txt_num, txt_correo;
    private FloatingActionButton btn_atras;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);

        btn_eliminar = (ImageButton) findViewById(R.id.btn_eliminar);
        btn_modificar = (ImageButton) findViewById(R.id.btn_editar);
        btn_whatsapp = (ImageButton) findViewById(R.id.btn_Whatsapp);

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

                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Desea eliminar este contacto ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.show();

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


        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaMensajeWhatsApp(intent.getStringExtra("numero"),"");
            }
        });
    }


    public void enviaMensajeWhatsApp(String numero, String msj) {

        try {
            String numeroTel = numero;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String uri = "whatsapp://send?phone=" + numeroTel + "&text=" + msj;
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_LONG)
                .show();
        }
    }


    public void aceptar() {
        Intent intent = new Intent(ContactoActivity.this, MainActivity.class);
        dbHelper.eliminar_contacto(lbl_nombre.getId());
        startActivity(intent);

        Toast t=Toast.makeText(this,"Contacto eliminado con exito", Toast.LENGTH_SHORT);
        t.show();
    }

    public void cancelar() {
        //finish();
    }

}