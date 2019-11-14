package com.example.practica1_npi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * Actividad principal de CazaTesoros. Consiste en ir encontrando diversos patrones geométricos
 * en la Alhambra que se pueden detectar con la cámara y también códigos QR escondidos. Después de
 * encontrarlos se puede ver la información al respecto y además hay pistas por si no los encuentras.
 */
public class CazaTesoros extends AppCompatActivity {

    // Código de respuesta para el detector QR
    public static final int BARCODE_RESULT = 1400;
    // Código de respuesta para la cámara
    public static final int CAMERA_RESULT = 1200;

    // La id de la imagen actual
    private int idActual;

    // Actualiza la id actual
    public void actualizarIdActual(int idActual) {
        this.idActual = idActual;
    }

    /**
     * Cuando se crea la actividad se muestra el layout y se comprueba si se tienen permisos
     * para poder usar la cámara.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se pone el layout
        setContentView(R.layout.activity_caza_tesoros);
        // Chequea si se tienen permisos para usar la cámara
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tienen permisos se piden
            requestPermissions(new String[]{Manifest.permission.CAMERA}, DetectarQRActivity.MY_PERMISSIONS_REQUEST_CAMERA);
        }

        // Para recibir gestos
        new GesturePerformListener(findViewById(R.id.gesture_treasure_hunt));
    }

    /**
     * Cuando se llama a otra actividad y se recoge el resultado se procesa según el tipo de respuesta.
     * Si ha sido de la camára se actualiza la imagen de la foto correspondiente y si es del código
     * QR se revela la que tenía oculta.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Si el resultado es correcto
        if (RESULT_OK == resultCode) {
            // Tomamos la imagen actual
            Imagen img = findViewById(idActual);
            // Si el código es de la cámara
            if (CAMERA_RESULT == requestCode){
                // Tomamos los datos
                Bundle extras = data.getExtras();
                // Obtenemos la imagen hecha con la cámara
                Bitmap bmp = (Bitmap) extras.get("data");
                // Ponemos a la imagen que se ha seleccionado con la foto hecha
                img.setImageBitmap(Bitmap.createScaledBitmap(bmp, 400, 400, false));
                // Si el código es del detector QR
            } else if (BARCODE_RESULT == requestCode) {
                // Tomamos la id del QR encontrado
                int id = data.getIntExtra(DetectarQRActivity.QR_ID, -1);
                int foto = -1;
                // Tomamos la imagen correspondiente a la ID
                switch (id) {
                    case 1:
                        foto = R.drawable.qr1;
                        break;
                    case 2:
                        foto = R.drawable.qr2;
                        break;
                    case 3:
                        foto = R.drawable.qr3;
                        break;
                }
                // Ponemos la imagen seleccionada con la imagen revelada
                img.setImageResource(foto);
            }
            // Mandamos un toast felicitando
            Toast.makeText(this, "Congratulations! Keep on like this.", Toast.LENGTH_LONG).show();
            // La imagen ahora está desbloqueada
            img.imagenDesbloqueada();
        }
    }

    /**
     * Mira la imagen seleccionada con idActual y si es QR lanza una actividad a DetectarQRActivity
     * (detector QR) y si no es QR entonces una actividad a la cámara.
     */
    public void hacerFoto() {
        // Imagen seleccionada
        Imagen img = findViewById(idActual);
        // Si es QR
        if (img.esQR) {
            // Nueva intención para detector QR
            Intent it = new Intent(this, DetectarQRActivity.class);
            // Empieza una actividad con resultado con el código detectorQR
            startActivityForResult(it, CazaTesoros.BARCODE_RESULT);
        } else {
            // Nueva inteción para la cámara
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Empieza una actividad con resultado con el código cámara
            startActivityForResult(it, CazaTesoros.CAMERA_RESULT);
        }
    }

    /**
     * Da una pista con un toast según la imagen que esté seleccionada
     */
    public void darPista() {
        String pista = "";
        // Según la imagen seleccionada da una pista acorde
        switch (idActual) {
            case R.id.imagen1:
                pista = "Busca por algun sitio del principio";
                break;
            case R.id.imagen2:
                pista = "Busca por algun sitio del final";
                break;
            case R.id.imagen3:
                pista = "Busca por algun sitio entre medias";
                break;
            case R.id.imagen4:
                pista = "Busca por algun sitio de por dentro";
                break;
            case R.id.imagen5:
                pista = "Busca por algun sitio de por arriba";
                break;
            case R.id.imagen6:
                pista = "Busca por algun sitio de la pared";
                break;
            case R.id.qr1:
                pista = "GRAAAAAAAAAAAAAAAUR!";
                break;
            case R.id.qr2:
                pista = "Tolon tolon tolon tolon!";
                break;
            case R.id.qr3:
                pista = "Muy circular esto.";
                break;
        }
        // Manda la pista con un toast
        Toast.makeText(this, pista, Toast.LENGTH_LONG).show();
    }
}

