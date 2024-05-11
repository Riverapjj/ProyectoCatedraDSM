package com.example.RP200198_ME180102_MH180095;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.RP200198_ME180102_MH180095.DAO.ContactosDAO;
import com.example.RP200198_ME180102_MH180095.basededatos.DBHelper;
import com.example.RP200198_ME180102_MH180095.modelos.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    FloatingActionButton agregar_contacto;
    TableLayout lista_contactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agregar_contacto = (FloatingActionButton) findViewById(R.id.btn_agregar);
        lista_contactos = (TableLayout) findViewById(R.id.lista_contactos);
        dbHelper = new DBHelper(this);

        try {
            dbHelper.copyDatabaseFromAssets();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Contactos> lista_contactos = dbHelper.obtener_contactos();

        for (Contactos contacto : lista_contactos) {
            imprimir_contactos(contacto.getId(), contacto.getNombre().concat(" ").concat(contacto.getApellido()));
        }

        agregar_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("nombre", "Alexander");
                startActivity(intent);
            }
        });
    }

    public void imprimir_contactos(int id, String nombre_completo) {
        TableRow fila_contacto = new TableRow(this);

        lista_contactos.setId(id);

        TextView nombre_fila = new TextView(this);
        nombre_fila.setText(nombre_completo);
        fila_contacto.addView(nombre_fila);
    }
}