package TPO.BATALLA_NAVAL.INTERFAZ;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import TPO.BATALLA_NAVAL.CLASES.ArchivoJugadores;

// HILO DE EJECUCION - forma 1: heredar de Thread y sobreescribir run().
//
// Por que un hilo aparte: Swing hace todo lo de la ventana (dibujar, atender clicks) en un
// unico hilo, el "Event Dispatch Thread". Si ese hilo se quedara esperando al disco,
// la ventana se congelaria. Por eso el archivo se lee en este hilo separado.
public class HiloCargaJugadores extends Thread {

    private ArchivoJugadores archivo;  //Referencia al objeto que lee el archivo
    private PanelInicio panel;

    public HiloCargaJugadores(ArchivoJugadores archivo, PanelInicio panel) {
        super("Hilo-CargaJugadores");   // super() llama al constructor de Thread y le pone nombre al hilo
        this.archivo = archivo;
        this.panel = panel;
    }

    // Esto es lo que ejecuta el hilo nuevo. Se dispara con start(), nunca llamando a run() directo.
    @Override
    public void run() {

        try {

            ArrayList<String> nombres = archivo.leerJugadores();  //Lista con los nombres de los jugadores 

            // Regla de Swing: la ventana solo se toca desde su propio hilo.
            // invokeLater le pasa el trabajo al hilo de la ventana para que lo haga el.
            SwingUtilities.invokeLater(() -> panel.mostrarJugadores(nombres));  //Mostramos la lista en panel inicio

        } catch (IOException e) {

            SwingUtilities.invokeLater(() ->
                    panel.mostrarError("No se pudo leer el archivo de jugadores: " + e.getMessage()));

        } finally {

            // Se ejecuta siempre, haya salido bien o mal: vuelve a habilitar la pantalla.
            SwingUtilities.invokeLater(() -> panel.terminarCarga());
        }
    }
}
