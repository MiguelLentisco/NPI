package com.example.practica1_npi;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Esta clase hereda de ConstraintLayout que nos permita establecer ciertas zonas como las zonas
 * donde se va a poder depositar la imagen seleccionada. Según sea la zona "Pista" o "Scan" hará
 * una cosa u otra.
 */
public class DropContainer extends ConstraintLayout implements View.OnDragListener {

    /**
     * Constructor básico
     */
    public DropContainer(Context context) {
        super(context);
        // Establece de oyente del arrastre
        setOnDragListener(this);
    }

    /**
     * Constructor con XML
     */
    public DropContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Establece de oyente del arrastre
        setOnDragListener(this);
    }

    /**
     * Constructor con XML
     */
    public DropContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // Establece de oyente del arrastre
        setOnDragListener(this);
    }

    /**
     * Cuando se ha iniciado un arrastre se llama a este método que hace diversas cosas. Si se
     * introduce una imagen cambia de color y si se deja caer en una se encarga de realizar su
     * acción. Si es "Scan" llamará a la cámara/código QR y si es "Clue", dará una pista.
     */
    @Override
    public boolean onDrag(View v, DragEvent event) {
        // Obtenemos la acción que se está produciendo
        int action = event.getAction();
        // Manejamos la acción
        switch (action) {
            // Cuando el arrastre ha comenzado
            case DragEvent.ACTION_DRAG_STARTED:
                // Si coincide entonces aceptamos que se pueda recibir información
                return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
            // Cuando la imagen entra en el contenedor
            case DragEvent.ACTION_DRAG_ENTERED:
                // Ponemos de color de fondo al contenedor amarillo
                v.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                // Invalidamos para que se redibuje con el nuevo color
                v.invalidate();
                return true;
            // Ignoramos esto
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            // Cuando se termina el arrastre
            case DragEvent.ACTION_DRAG_EXITED:
            case DragEvent.ACTION_DRAG_ENDED:
                // Quitamos el color amarillo si lo tenía
                v.getBackground().clearColorFilter();
                // Invalidamos para que se redibuje con el nuevo color
                v.invalidate();
                return true;
            // Cuando se suelta la imagen en el contenedor
            case DragEvent.ACTION_DROP:
                // Obtenemos la información del arrastre
                ClipData.Item item = event.getClipData().getItemAt(0);
                // Obtenemos la id de la imagen que se ha dejado caer
                int idActual = Integer.parseInt(item.getText().toString());
                // Actualizamos la idactual de CazaTesoros
                CazaTesoros act = (CazaTesoros) v.getContext();
                act.actualizarIdActual(idActual);
                // Quitamos el color amarillo si lo tenía
                v.getBackground().clearColorFilter();
                // Invalidamos para que se redibuje con el nuevo color
                v.invalidate();
                // Si el contenedor es "Scan", hacemos foto
                if (this == v.findViewById(R.id.scan)) {
                    act.hacerFoto();
                    // Si el contenedor es "Clue" damos pista
                } else if (this == v.findViewById(R.id.clue)) {
                    act.darPista();
                }
                return true;
            default:
                break;
        }
        return false;
    }
}
