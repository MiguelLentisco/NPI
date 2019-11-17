/**
 * Incluye la funcionalidad de la parte de CazaTesoros.
 * <p>
 *     CazaTesoros es la actividad principal que muestra las imágenes: 6 que hay que buscar
 *     reconociendo el patrón con la cámara y 3 QR que hay que encontrar por la Alhambra y escanear.
 * </p>
 * <p>
 *     Para ello se implementa la clase DetectorQR que usa una librería para crear un
 *     detector con la cámara.
 * </p>
 * <p>
 *     Además se crean clases modificadas de Views, DropContenedor y Imagen para poder interactuar
 *     con eventos "Arrastrar y Soltar".
 * </p>
 * <p>
 *     Finalmente muestra información adicional una vez encontrada la imagen con InfoImagen, si
 *     se pulsa sobre una imagen desbloqueada.
 * </p>
 */
package com.example.practica1_npi.CazaTesoros;