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

    private static final String DATABASE_NAME = "agendadb.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Sentencia SQL para crear la tabla "Contactos"
        //String CREATE_CONTACTS_TABLE = "CREATE TABLE Contactos (id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, nombre TEXT, apellido TEXT, numero TEXT, correo TEXT)";
        // Ejecuta la sentencia SQL
        //db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes ejecutar sentencias SQL para actualizar la base de datos
    }

    public void copyDatabaseFromAssets() throws IOException {
        // Código para copiar la base de datos desde el directorio de activos
        // Ruta de destino en el almacenamiento interno
        String outFileName = context.getDatabasePath(DATABASE_NAME).getPath();

        // Si la base de datos ya existe, no hacemos nada
        if (new File(outFileName).exists()) {
            return;
        }

        // Abre el archivo en los activos como un stream de entrada
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);

        // Abre el archivo de destino como un stream de salida
        OutputStream outputStream = new FileOutputStream(outFileName);

        // Transfiere los datos desde el stream de entrada al stream de salida
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        // Cierra los streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();
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

    // Método para leer todos los registros
    public List<Contactos> obtener_contactos() {
        List<Contactos> lista_contactos = new ArrayList<>();
        String selectQuery = "SELECT * FROM Contactos";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.e("DBHELPER", db.rawQuery(selectQuery, null).toString());
        // Itera a través del cursor y agrega los registros a la lista
        if (cursor.moveToFirst()) {
            do {
                Contactos contacto = new Contactos();
                // Lee los valores del cursor y configura el objeto Registro
                contacto.setNombre(cursor.getString(1));
                contacto.setApellido(cursor.getString(2));
                contacto.setNumero(cursor.getString(3));
                contacto.setCorreo(cursor.getString(4));
                // Agrega el registro a la lista
                lista_contactos.add(contacto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista_contactos;
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
