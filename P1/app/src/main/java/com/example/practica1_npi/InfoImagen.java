package com.example.practica1_npi;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Clase que hereda de Dialog para poder mostrar información en un recuadro por encima de la
 * actividad actual (CazaTesoros deberá ser) mostrando información sobre la imagen seleccionada.
 */
public class InfoImagen extends Dialog implements android.view.View.OnClickListener {

    // La id de la imagen seleccionada
    private int id_imagen;

    /**
     * Constructor con la actividad actual y la imagen seleccionada
     */
    public InfoImagen(Activity a, int id_imagen) {
        super(a);
        this.id_imagen = id_imagen;
    }

    /**
     * Cuando se crea InfoImagen se va a crear un recuadro con el texto de la imagen seleccionada
     * y su imagen; cuando se quiera cerrar se pulsa el botón que tiene.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pide una ventana sin título
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        String texto = "";
        int imagen = 0;
        // Según la imagen seleccionada se toma el texto y la imagen correspondiente
        switch (id_imagen) {
            case R.id.imagen1:
                texto = "Texto de prueba 1\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.i1;
                break;
            case R.id.imagen2:
                texto = "Texto de prueba 2\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.i2;
                break;
            case R.id.imagen3:
                texto = "Texto de prueba 3\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.i3;
                break;
            case R.id.imagen4:
                texto = "Texto de prueba 4\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.i4;
                break;
            case R.id.imagen5:
                texto = "Texto de prueba 5\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.i5;
                break;
            case R.id.imagen6:
                texto = "Texto de prueba 6\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.i6;
                break;
            case R.id.qr1:
                texto = "Texto de prueba 7\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.qr1;
                break;
            case R.id.qr2:
                texto = "Texto de prueba 8\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.qr2;
                break;
            case R.id.qr3:
                texto = "Texto de prueba 9\nTexto Texto\nTexto Texto\nTexto Texto \nTexto\nTexto\nTexto";
                imagen = R.drawable.qr3;
                break;
        }
        // Se establece el layout
        setContentView(R.layout.info_imagen);
        // Se busca la zona de texto y se pone la información
        TextView text = findViewById(R.id.textoInfo);
        text.setText(texto);
        // Se busca la zona de imagen y se pone la imagen correspondiente
        ImageView img = findViewById(R.id.imagenInfo);
        img.setImageResource(imagen);
        // Se busca el botón y se le establece como "oyente" de click
        Button ok = findViewById(R.id.boton_ok);
        ok.setOnClickListener(this);
    }

    /**
     * Cuando se cliquea en el botón se cierra la ventana
     */
    @Override
    public void onClick(View v) {
        // Se cierra
        dismiss();
    }

}
