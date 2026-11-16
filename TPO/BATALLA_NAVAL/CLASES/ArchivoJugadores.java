package TPO.BATALLA_NAVAL.CLASES;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

// SOLID - Principio de Responsabilidad Unica (la "S"):
// esta clase tiene un solo motivo para cambiar: como y donde se guardan los jugadores.
// La ventana no sabe nada del archivo, y esta clase no sabe nada de la ventana.
public class ArchivoJugadores {

    public static final String ARCHIVO_POR_DEFECTO = "jugadores.txt";

    private Path ruta;

    public ArchivoJugadores() {
        this(ARCHIVO_POR_DEFECTO);   // Le pasa el archivo por defecto al segundo constructor
    }

    public ArchivoJugadores(String nombreArchivo) {
        this.ruta = Paths.get(nombreArchivo);
    }

    // synchronized: si dos hilos usan el mismo archivo a la vez, uno espera a que termine el otro.
    public synchronized ArrayList<String> leerJugadores() throws IOException {  //Si el disco falla le tira error al que lo llamó

        ArrayList<String> nombres = new ArrayList<String>();

        if (!Files.exists(ruta)) {
            return nombres;   // Primera vez que se juega: todavia no hay archivo, la lista queda vacia
        }

        // try-with-resources: el lector se cierra solo al salir del bloque, aunque haya una excepcion.
        // Es lo que en Java reemplaza a un destructor para los recursos externos como los archivos.
        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {  //Arrancamos el lector 

            String linea = lector.readLine();

            while (linea != null) {  //Vamos agregando todas las lineas del lugadores.txt al arraylist

                linea = linea.trim();

                if (!linea.isEmpty()) {
                    nombres.add(linea);
                }

                linea = lector.readLine();  //Lee la linea en la que se encuentra y va a la siguiente
            }
        }  //Se cierra el lector

        return nombres;
    }

    public synchronized void agregarJugador(String nombre) throws IOException, JugadorExistenteException {  //Lo llama TareaGuardar

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }

        nombre = nombre.trim();

        for (String existente : leerJugadores()) {   // Recorrido con for-each

            if (existente.equalsIgnoreCase(nombre)) {  //Si coincide con alguno de los jugadores existentes
                throw new JugadorExistenteException(nombre);
            }
        }

        // CREATE: si el archivo no existe lo crea. APPEND: escribe al final, sin borrar lo que habia.
        try (BufferedWriter escritor = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            escritor.write(nombre);
            escritor.newLine();
        }
    }
}
