package com.example.practica1_npi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * Actividad para poder detectar códigos QR. Busca un código QR y lo devuelve a la actividad
 * CazaTesoros con la id del código QR encontrado.
 */
public class DetectarQRActivity extends AppCompatActivity {

    // Código para obtener la id del QR
    public static String QR_ID = "QR_ID";
    // Permisos para la cámara
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    // Si se ha obtenido ya o no los QR
    static private Boolean[] obtenidos = {false, false, false};

    // Cámara
    private CameraSource cameraSource;
    // Donde se va a mostrar lo que ve la cámara
    private SurfaceView cameraView;
    // Mensaje leído actual
    private String cadena = "";
    // Mensaje leído con anterioridad
    private String cadenaAnterior = "";

    /**
     * Creamos la actividad con el layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecemos el layout
        setContentView(R.layout.activity_detectar_qr);
        // La zona de la cámara
        cameraView = findViewById(R.id.camera);
        // Mandamos un toast que nos indica que está buscando por QR
        Toast.makeText(this, "Searching QR!", Toast.LENGTH_LONG).show();
        // Iniciamos la detección
        initQR();
    }

    public void initQR() {
        // Obtenemos el tamaño de la ventana
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // Creamos un detector que detecte códigos QR
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        // Establecemos la cámara con el detector y el tamaño de la ventana
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(width, height)
                .setAutoFocusEnabled(true)
                .build();

        // El ciclo de la cámara
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            /**
             * Cuando se crea la superficie se inicia la cámara
             */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }

            /**
             * Cuando cambia de superficie no hace nada
             */
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            /**
             * Cuando se destruye la superficie se para la cámara
             */
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        // El ciclo del detector
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            /**
             * Cuando se temrina no hace nada
             */
            @Override
            public void release() {
            }

            /**
             * Manejamos lo que ha detectado el detector, si es una ID válida entonces se envía
             * a CazaTesoros; si no, no hace nada.
             */
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detectado) {
                // Los códigos que ha detectado
                final SparseArray<Barcode> codigos = detectado.getDetectedItems();
                // Si hay algo
                if (codigos.size() > 0) {
                    // Actualizamos el mensaje que se lee
                    cadena = codigos.valueAt(0).displayValue;
                    // Verificamos que el mensaje leido anteriormente no sea igual al actual
                    // para evitar muchas llamadas con el mismo mensaje
                    if (!cadena.equals(cadenaAnterior)) {
                        // Actualizamos
                        cadenaAnterior = cadena;
                        int id = -1;
                        // Establece la id si aun no se ha descubierto y coincide con el mensaje leído
                        if (!obtenidos[0] && cadena.equals("QR1"))
                            id = 1;
                        else if (!obtenidos[1] && cadena.equals("QR2"))
                            id = 2;
                        else if (!obtenidos[2] && cadena.equals("QR3"))
                            id = 3;

                        // Si se ha leído una id correctamente
                        if (id != -1) {
                            // Se ha obtenido la correspondiente
                            obtenidos[id - 1] = true;
                            // Se hace una nueva intención de vuelta
                            Intent returnIntent = new Intent();
                            // Se añade el código QR leído
                            returnIntent.putExtra(QR_ID, id);
                            // Código OK
                            setResult(Activity.RESULT_OK, returnIntent);
                            // Se manda el intent y se acaba la actividad
                            finish();
                        }
                    }
                }

            }
        });
    }
}