package com.example.RP200198_ME180102_MH180095.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.RP200198_ME180102_MH180095.modelos.Contactos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNOMBRE = "agendadb.db";
    private Context contexto;
    public static String DBUBICACION = null;

    private SQLiteDatabase contactosDB;

    public DBHelper(Context contexto) {
        super(contexto, DBNOMBRE, null, 1);
        this.DBUBICACION  = "/data/data/" + contexto.getPackageName() + "/databases/";
        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = contexto.getDatabasePath(DBNOMBRE).getPath();
        if(contactosDB != null && contactosDB.isOpen()) {
            return;
        }
        contactosDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(contactosDB!=null) {
            contactosDB.close();
        }
    }

    public List<Contactos> obtener_contactos() {
        Contactos contactos = null;
        List<Contactos> lista_contactos = new ArrayList<>();
        openDatabase();
        Cursor cursor = contactosDB.rawQuery("SELECT * FROM Contactos", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contactos = new Contactos(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));
            lista_contactos.add(contactos);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return lista_contactos;
    }

    public Contactos obtener_contacto(int id) {
        Contactos contacto = null;
        List<Contactos> lista_contactos = new ArrayList<>();
        openDatabase();
        Cursor cursor = contactosDB.rawQuery("SELECT * FROM Contactos WHERE id=?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            // Creamos un objeto Contactos utilizando los datos del cursor
            contacto = new Contactos(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));
        }
        /*cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contactos = new Contactos(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));
            lista_contactos.add(Contactos);
            cursor.moveToNext();
        }*/
        cursor.close();
        closeDatabase();
        return contacto;
    }

    // Método para agregar un nuevo registro
    public void agregar_contacto(Contactos contactos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contacto = new ContentValues();
        // Llena el ContentValues con los valores del nuevo registro
        contacto.put("nombre", contactos.getNombre());
        contacto.put("apellido", contactos.getApellido());
        contacto.put("numero", contactos.getNumero());
        contacto.put("correo", contactos.getCorreo());
        // Inserta el registro en la tabla
        db.insert("Contactos", null, contacto);
        db.close();
    }

    // Método para editar un registro existente
    public void editar_contacto(Contactos contactos, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contacto = new ContentValues();
        // Llena el ContentValues con los valores actualizados del registro
        contacto.put("nombre", contactos.getNombre());
        contacto.put("apellido", contactos.getApellido());
        contacto.put("numero", contactos.getNumero());
        contacto.put("correo", contactos.getCorreo());
        // Actualiza el registro en la tabla
        db.update("Contactos", contacto, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para eliminar un registro
    public void eliminar_contacto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Elimina el registro de la tabla basado en la condición
        db.delete("Contactos", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
