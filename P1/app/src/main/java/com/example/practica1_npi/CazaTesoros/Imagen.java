package com.example.practica1_npi.CazaTesoros;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

import com.example.practica1_npi.R;

/**
 * ImagenView modificada.
 * Al hacer click largo empieza un evento de arrastrar y soltar si la imagen no está desbloqueada.
 * Si está desbloqueada entonces lanza un Dialog con información sobre la imagen.
 * Guarda si es de tipo QR o no.
 */
public class Imagen extends androidx.appcompat.widget.AppCompatImageView implements View.OnLongClickListener {

    /**
     * Si la imagen ha sido desbloqueada (encontrada). Por defecto no.
     */
    private boolean desbloqueada = false;
    /**
     * Si la imagen es de tipo QR. Por defecto no.
     */
    private boolean esQR = false;
    /**
     * Info sobre la imagen.
     */
    private String info = "";
    /**
     * Pista sobre la imagen.
     */
    private String pista = "";

    /**
     * Constructor básico.
     * @param context El contexto donde se crea la imagen.
     */
    public Imagen(Context context) {
        super(context);
        // Escucha de clicks largos
        setOnLongClickListener(this);
    }

    /**
     * Constructor con atributos XML.
     * @param context El contexto donde se crea la imagen.
     * @param attrs Los atributos de la imagen.
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
            info = a.getString(R.styleable.Imagen_info);
            pista = a.getString(R.styleable.Imagen_pista);
        } finally {
            a.recycle();
        }
        // Escucha de clicks largos
        setOnLongClickListener(this);
    }

    /**
     * Constructor con atributos XML y con estilo.
     * @param context El contexto donde se crea la imagen.
     * @param attrs Los atributos de la imagen.
     * @param defStyleAttr El estilo por defecto para aplicar a la imagen.
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
            info = a.getString(R.styleable.Imagen_info);
            pista = a.getString(R.styleable.Imagen_pista);
        } finally {
            a.recycle();
        }
        // Establece esta clase como "oyente" al darle un click largo
        setOnLongClickListener(this);
    }

    /**
     * Si la imagen es QR.
     * @return Devuelve True si la imagen es QR, False en caso contrario.
     */
    public boolean getEsQR() {
        return esQR;
    }

    /**
     * Si la imagen está desbloqueada.
     * @return Devuelve True si la imagen está desbloqueada, False en caso contrario.
     */
    public boolean getDesbloqueada() { return desbloqueada; }

    /**
     * Devuelve información de la imagen.
     * @return Información sobre la imagen.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Devuelve la pista de la imagen.
     * @return Pista sobre la imagen.
     */
    public String getPista() {
        return pista;
    }

    /**
     * Cuando se hace un click largo en la imagen.
     * Si está desbloqueada se muestra su información mediante InfoImagen (Dialog),
     * en caso contrario se empieza a realizar el evento arrastrar y soltar.
     * @param view La view donde se ha hecho click.
     * @see DropContenedor#onDrag(View, DragEvent)
     * @see InfoImagen
     * @return Si se ha consumido el evento de click largo.
     */
    @Override
    public boolean onLongClick(View view) {
        // Si está desbloqueada, arrastrar y soltar
        if (!desbloqueada) {
            // Guardamos la ID de la imagen
            ClipData.Item item = new ClipData.Item(Integer.toString(getId()));
            // Info en texto plano
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            // Encapsulamos los datos
            ClipData data = new ClipData((CharSequence) getTag(), mimeTypes, item);
            // Sombra de la imagen al ser arrastrado
            View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(this);
            // Arrastrar y soltar
            startDragAndDrop(data, dragShadow, this, 0);
        // Si no, mostramos su información
        } else
            // Información de la imagen mostrada
            new InfoImagen((Activity) getContext(), this).show();
        return true;
    }

    /**
     * Desbloquea la imagen y la cambia.
     * Se cambia por una imagen nueva que se le pasa.
     * @param imagen La imagen nueva en formato bitmap.
     */
    @Override
    public void setImageBitmap(Bitmap imagen) {
        desbloqueada = true;
        super.setImageBitmap(Bitmap.createScaledBitmap(imagen, 400, 400, false));
    }

    /**
     * Desbloquea la imagen y la cambia.
     * Se cambia por una imagen de QR guardada, con la id indicada.
     * @param idResource La id de la imagen a cambiar.
     */
    @Override
    public void setImageResource(int idResource) {
        desbloqueada = true;
        super.setImageResource(idResource);
    }

}
