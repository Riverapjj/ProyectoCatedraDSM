package com.example.RP200198_ME180102_MH180095.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.RP200198_ME180102_MH180095.MainActivity;

public class WhatsAppHelper {
    public static boolean verificarNumeroWhatsApp(Context context, String numeroTelefono) {
        // Construir el URI de WhatsApp para abrir la conversación con el número de teléfono dado
        String uri = "whatsapp://send?phone=" + Uri.encode(numeroTelefono);

        // Crear un Intent con el URI de WhatsApp
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        // Verificar si WhatsApp está instalado en el dispositivo
        PackageManager packageManager = context.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }

    public static void abrirConversacionWhatsApp(Context context, String numeroTelefono) {
        // Construir el URI de WhatsApp para abrir la conversación con el número de teléfono dado
        String uri = "whatsapp://send?phone=" + Uri.encode(numeroTelefono);

        // Crear un Intent con el URI de WhatsApp
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        // Verificar si WhatsApp está instalado en el dispositivo
        PackageManager packageManager = context.getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            // WhatsApp está instalado, iniciar la conversación
            context.startActivity(intent);
        } else {
            // WhatsApp no está instalado, redirigir a otra actividad de tu aplicación
            Intent intentCambiar = new Intent(context, MainActivity.class);
            context.startActivity(intentCambiar);
        }
    }
}
