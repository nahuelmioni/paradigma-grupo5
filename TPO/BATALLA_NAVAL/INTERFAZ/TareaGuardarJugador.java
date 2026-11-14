package TPO.BATALLA_NAVAL.INTERFAZ;

import java.io.IOException;

import javax.swing.SwingUtilities;

import TPO.BATALLA_NAVAL.CLASES.ArchivoJugadores;
import TPO.BATALLA_NAVAL.CLASES.JugadorExistenteException;

// HILO DE EJECUCION - forma 2: implementar la interfaz Runnable.
// Esta clase es solo la TAREA; el hilo se crea afuera con:
//     new Thread(new TareaGuardarJugador(...)).start();
// (ver PanelInicio.crearJugador). Escribe el jugador nuevo en el archivo sin congelar la ventana.
public class TareaGuardarJugador implements Runnable {

    private ArchivoJugadores archivo;
    private String nombre;
    private PanelInicio panel;

    public TareaGuardarJugador(ArchivoJugadores archivo, String nombre, PanelInicio panel) {
        this.archivo = archivo;
        this.nombre = nombre;
        this.panel = panel;
    }

    @Override
    public void run() {

        try {

            archivo.agregarJugador(nombre);

            SwingUtilities.invokeLater(() -> panel.jugadorCreado(nombre));

        } catch (JugadorExistenteException e) {   // Excepcion propia: el nombre ya estaba guardado

            SwingUtilities.invokeLater(() -> panel.mostrarError(e.getMessage()));

        } catch (IOException e) {                 // Excepcion comun: fallo al escribir el archivo

            SwingUtilities.invokeLater(() ->
                    panel.mostrarError("No se pudo guardar el jugador: " + e.getMessage()));

        } finally {

            SwingUtilities.invokeLater(() -> panel.terminarGuardado());
        }
    }
}
