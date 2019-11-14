package com.example.practica1_npi;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Clase que hereda de ImageView hecha para poder ser interactuada con el método "drag & drop", y
 * que podemos consultar si es un código QR o no (imagen normal); o si ya ha sido desbloqueada.
 */
public class Imagen extends androidx.appcompat.widget.AppCompatImageView implements View.OnLongClickListener {
    // Si la imagen ha sido desbloqueada, por defecto no.
    boolean desbloqueada = false;
    // Si la imagen es QR, por defecto no.
    boolean esQR = false;

    /**
     * Constructor básico
     */
    public Imagen(Context context) {
        super(context);
        // Establece esta clase como "oyente" al darle un click largo
        setOnLongClickListener(this);
    }

    /**
     * Constructor con atributos XML
     */
    public Imagen(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Obtenemos los atributos
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Imagen,
                0, 0);
        // Establecemos los atributos y los desechamos
        try {
            esQR = a.getBoolean(R.styleable.Imagen_esQR, false);
        } finally {
            a.recycle();
        }
        // Establece esta clase como "oyente" al darle un click largo
        setOnLongClickListener(this);
    }

    /**
     * Constructor con atributos XML
     */
    public Imagen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // Obtenemos los atributos
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Imagen,
                0, 0);
        // Establecemos los atributos y los desechamos
        try {
            esQR = a.getBoolean(R.styleable.Imagen_esQR, false);
        } finally {
            a.recycle();
        }
        // Establece esta clase como "oyente" al darle un click largo
        setOnLongClickListener(this);
    }
    public void imagenDesbloqueada() {
        desbloqueada = true;
    }

    /**
     * Establece lo que realiza la imagen cuando es pulsada un tiempo largo: si no estaba desbloqueada,
     * se guarda su ID y se manda empezando un nuevo proceso "Drag & Drop"; si está desbloqueada
     * entonces se muestra la información de la imagen.
     */
    @Override
    public boolean onLongClick(View v) {
        // Si la imagen está desbloqueada "Drag & Drop"
        if (!desbloqueada) {
            // Guardamos la ID de la imagen
            ClipData.Item item = new ClipData.Item(Integer.toString(v.getId()));
            // La información es en texto plano
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            // Encapsulamos los datos para mandarlos
            ClipData data = new ClipData((CharSequence) v.getTag(), mimeTypes, item);
            // La sombra de la imagen al ser arrastrado, se usa la que viene por defecto
            View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);
            // Empezamos el drag & drop
            v.startDragAndDrop(data, dragShadow, v, 0);
            // Si no, mostramos su información
        } else {
            // Creamos un recuadro (Dialog) con la información de la imagen
            InfoImagen info = new InfoImagen((Activity) v.getContext(), v.getId());
            // Mostramos la información
            info.show();
        }
        return true;
    }
}
