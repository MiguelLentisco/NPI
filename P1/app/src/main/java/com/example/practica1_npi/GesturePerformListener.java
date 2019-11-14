package com.example.practica1_npi;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Está clase permite reconocer gestos para pasar de una cláse a otro
 */
public class GesturePerformListener  implements GestureOverlayView.OnGesturePerformedListener {

    // Libreria de los gestos detectables
    private static GestureLibrary gestureLibrary;
    // La puntuación mínima para que sea bueno el gesto
    private static final double GESTURE_SCORE = 3.0;

    /**
     * Constructor, se carga la libreria de gestos y se pone para recibir gestos del objeto
     * GestureOverlayView que se le pasa.
     */
    public GesturePerformListener(GestureOverlayView gestureOverlayView) {
        Activity act = (Activity) gestureOverlayView.getContext();
        // Cargamos los gestos de la liberia si no lo están
        if (gestureLibrary == null)
            gestureLibrary = GestureLibraries.fromRawResource(act, R.raw.gesture);
        // Si no carga, da error y acaba
        if (!gestureLibrary.load()) {
            Log.e("Gesture", "La libreria de gestos no se cargó.");
            act.finish();
        } else {
            // Nos ponemos a escuchar
            gestureOverlayView.addOnGesturePerformedListener(this);
        }
    }

    /**
     * Cuando se hace un gesto en un objeto de tipo GestureOverlayView se invoca a este método.
     * Chequeamos si ha sido una M o una Q con cierto umbral y redirigimos a la actividad que toque.
     */
    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        // Reconocemos el gesto y la lista de match
        ArrayList<Prediction> predictionList = gestureLibrary.recognize(gesture);

        // Si hay alguna al menos
        if (predictionList.size() > 0) {
            Activity act = (Activity) gestureOverlayView.getContext();
            Class actividad_objetivo = null;
            StringBuilder messageBuffer = new StringBuilder();

            // Obtenemos el mejor match
            Prediction firstPrediction = predictionList.get(0);

            // Si el gesto coincide con alguno por encima de un umbral
            if (firstPrediction.score > GESTURE_SCORE) {
                String action = firstPrediction.name;

                // Seleccionamos la actividad según el gesto
                switch (action) {
                    // CazaTesoros
                    case "q":
                        actividad_objetivo = CazaTesoros.class;
                        messageBuffer.append("Activating TreasureHunt!");
                        break;
                    // Linea temporal
                    case "m":
                        actividad_objetivo = MainActivity.class;
                        messageBuffer.append("Activating TimeLine!");
                        break;
                }

                // Si hacemos un gesto para ir a la actividad distinta a la actual
                if (act.getClass() != actividad_objetivo) {
                    Intent intent = new Intent(act, actividad_objetivo);
                    act.startActivity(intent);
                }
            } else
                messageBuffer.append("You need to draw Q or M.");
            // Toast con el resultado
            Toast.makeText(act, messageBuffer.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}