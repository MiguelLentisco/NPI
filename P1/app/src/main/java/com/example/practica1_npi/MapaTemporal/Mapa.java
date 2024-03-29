package com.example.practica1_npi.MapaTemporal;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.practica1_npi.R;


/**
 * Vista asociada al mapa de {@link MapaTemporal}.
 * Funciona como un ConstrainLayout para agrupar los distintos recintos del mapa en
 * activity_main.xml.
 * @see RecintoMapa
 */
public class Mapa extends ConstraintLayout {

    /**
     * Constructor usado cuando se crea la vista a partir del código.
     * @param context
     */
    public Mapa(Context context) {
        super(context);
    }

    /**
     * Constructor usado cuando se "infla" la vista a partir del XML.
     * @param context
     * @param attrs
     * @param defStyle
     */
    public Mapa(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Constructor usado cuando se "infla" la vista a partir del XML.
     * @param context
     * @param attrs
     */
    public Mapa(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Método que pinta el mapa.
     * Este método se encarga de pintar los 4 recintos del mapa, llamando al método
     * {@link #draw(Canvas)} de cada uno de ellos.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Obtengo las vistas
        View view_entrada = findViewById(R.id.recinto_entrada);
        View view_alcazaba = findViewById(R.id.recinto_alcazaba);
        View view_generalife = findViewById(R.id.recinto_generalife);
        View view_palacios = findViewById(R.id.recinto_palacios);

        // Pinto todos los recintos
        view_entrada.draw(canvas);
        view_alcazaba.draw(canvas);
        view_generalife.draw(canvas);
        view_palacios.draw(canvas);
    }
}