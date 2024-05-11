package com.example.RP200198_ME180102_MH180095.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.RP200198_ME180102_MH180095.basededatos.DBHelper;
import com.example.RP200198_ME180102_MH180095.modelos.Contactos;

import java.util.ArrayList;
import java.util.List;

public class ContactosDAO {

    private DBHelper dbHelper;

    public ContactosDAO(Context context) {
        dbHelper = new DBHelper(context);

        try {
           // dbHelper.createDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long agregar_contacto(String nombre, String apellido, String numero, String correo) {
        SQLiteDatabase db = null;
        long resultlado = -1;

        try {
            db = dbHelper.getWritableDatabase();

            ContentValues contacto = new ContentValues();
            contacto.put("nombre", nombre);
            contacto.put("apellido", apellido);
            contacto.put("numero", numero);
            contacto.put("correo", correo);

            resultlado = db.insert("Contactos", null, contacto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return resultlado;
    }

    public List<Contactos> obtener_contactos() {
        List<Contactos> contactos = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            //db = dbHelper.openDatabase();
            cursor = db.rawQuery("SELECT * FROM Contactos", null);

            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("id");

                if (columnIndex != -1) {
                    do {
                        int id = cursor.getInt(columnIndex);
                        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                        String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
                        String numero = cursor.getString(cursor.getColumnIndexOrThrow("numero"));
                        String correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"));
                    } while (cursor.moveToNext());
                } else {
                    Log.e("Obtener contactos", "La tabla se encuentra vacia.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        }

        return contactos;
    }

    public int actualizar_contacto(int id, String nombre, String apellido, String numero, String correo) {
        SQLiteDatabase db = null;
        int filas_actualizadas = 0;

        try {
            //db = dbHelper.openDatabase();

            ContentValues contacto = new ContentValues();
            contacto.put("nombre", nombre);
            contacto.put("apellido", apellido);
            contacto.put("numero", numero);
            contacto.put("correo", correo);

            filas_actualizadas = db.update("Contactos", contacto, "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return filas_actualizadas;
    }

    public void eliminar_contacto(int id) {
        SQLiteDatabase db = null;

        try {
            //db = dbHelper.openDatabase();
            db.delete("Contactos", "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


    }
}
