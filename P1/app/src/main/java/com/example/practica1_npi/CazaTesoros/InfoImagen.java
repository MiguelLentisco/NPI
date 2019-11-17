package com.example.practica1_npi.CazaTesoros;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practica1_npi.R;

/**
 * Dialog modificado.
 * Se muestra la información de una imagen desbloqueada que haya sido seleccionada, con un
 * recuadro por encima de la actividad CazaTesoros. Tiene un botón para cerrar la ventana.
 */
public class InfoImagen extends Dialog implements android.view.View.OnClickListener {

    /**
     * ID de la imagen de la que se pide información.
     * @see Imagen
     */
    private Imagen img;

    /**
     * Constructor básico.
     * @param activity La actividad donde se ha creado el dialogo.
     * @param img La imagen de la que se muestra la información.
     * @see Imagen
     */
    public InfoImagen(Activity activity, Imagen img) {
        super(activity);
        this.img = img;
    }

    /**
     * Cuando se crea el diálogo.
     * Se crea un recuadro con el texto de la imagen seleccionada y su imagen. Cuando se quiera
     * cerrar se pulsa el botón para quitar este dialogo.
     * @param savedInstanceState Conjunto de datos del estado de la instancia.
     * @see Imagen#onLongClick(View)
     * @see Imagen#getInfo()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pide una ventana sin título
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Se establece el layout
        setContentView(R.layout.info_imagen);
        // Se pone a la escucha al botón
        Button ok = findViewById(R.id.boton_ok);
        ok.setOnClickListener(this);

        // Se pone la información
        TextView text = findViewById(R.id.textoInfo);
        text.setText(img.getInfo());
        // Se pone la imagen
        ImageView foto = findViewById(R.id.imagenInfo);
        foto.setImageDrawable(img.getDrawable());
    }

    /**
     * Al hacer click en el botón se cierra el diálogo.
     * @param v EL botón donde se hace click.
     */
    @Override
    public void onClick(View v) {
        dismiss();
    }
}
