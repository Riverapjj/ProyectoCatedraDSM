package com.example.RP200198_ME180102_MH180095.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.RP200198_ME180102_MH180095.ContactoActivity;
import com.example.RP200198_ME180102_MH180095.R;
import com.example.RP200198_ME180102_MH180095.basededatos.DBHelper;
import com.example.RP200198_ME180102_MH180095.modelos.Contactos;

import java.util.List;

public class ListaContactosAdaptador extends BaseAdapter {
    private Context context;
    private List<Contactos> lista_contactos;
    private DBHelper dbHelper;

    public ListaContactosAdaptador(Context context, List<Contactos> lista_contactos) {
        this.context = context;
        this.lista_contactos = lista_contactos;
    }

    @Override
    public int getCount() {
        return lista_contactos.size();
    }

    @Override
    public Object getItem(int posicion) {
        return lista_contactos.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return lista_contactos.get(posicion).getId();
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
        View vista = View.inflate(context, R.layout.contactos_lista, null);
        TextView lbl_nombre = (TextView)vista.findViewById(R.id.lbl_nombre);
        TableLayout table_contacto = (TableLayout) vista.findViewById(R.id.table_contacto);
        lbl_nombre.setText(String.valueOf(lista_contactos.get(posicion).getNombre().concat(" ").concat(lista_contactos.get(posicion).getApellido())));
        table_contacto.setId(lista_contactos.get(posicion).getId());
        dbHelper = new DBHelper(context);
        table_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contactos contacto = dbHelper.obtener_contacto(table_contacto.getId());

                Intent intent = new Intent(context, ContactoActivity.class);
                intent.putExtra("id", table_contacto.getId());
                intent.putExtra("nombre", contacto.getNombre());
                intent.putExtra("apellido", contacto.getApellido());
                intent.putExtra("numero", contacto.getNumero());
                intent.putExtra("correo", contacto.getCorreo());
                intent.putExtra("wha", contacto.getWha());
                context.startActivity(intent);
            }
        });

        return vista;
    }
}
