package TPO.BATALLA_NAVAL.INTERFAZ;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import TPO.BATALLA_NAVAL.CLASES.ArchivoJugadores;
import TPO.BATALLA_NAVAL.CLASES.Jugador;

public class PanelInicio extends JPanel {  //JPanel = Contenedor rectangular donde pones botones, textos y listas

    private JComboBox<String> listaJugador1;  //Menu desplegable para elegir nombre que solo va a guardar <String>
    private JComboBox<String> listaJugador2;
    private JButton botonCrearJugador;
    private JButton botonComenzar;
    private JLabel etiquetaEstado;  //Etiqueta fija donde mostramos mensajes como "Cargando jugadores...""

    private VentanaPrincipal ventana;
    private ArchivoJugadores archivo;

    public PanelInicio(VentanaPrincipal ventana) {

        this.ventana = ventana;
        this.archivo = new ArchivoJugadores();

        setLayout(new GridBagLayout());   // Centra el formulario en la ventana

        JPanel formulario = new JPanel(new GridLayout(8, 1, 10, 10));  //8 filas y 1 columna, con 10 píxeles de separación horizontal y 10 de separación vertical entre cada fila.
        formulario.setPreferredSize(new Dimension(420, 380));   //Limitamos el espacio que ocupa el cuestionario del menu

        JLabel titulo = new JLabel("BATALLA NAVAL CON TRAMPOLINES", SwingConstants.CENTER);
        JLabel textoJugador1 = new JLabel("Jugador 1:");
        listaJugador1 = new JComboBox<String>();
        JLabel textoJugador2 = new JLabel("Jugador 2:");
        listaJugador2 = new JComboBox<String>();
        botonCrearJugador = new JButton("Crear jugador nuevo");
        botonComenzar = new JButton("Comenzar partida");
        etiquetaEstado = new JLabel("Cargando jugadores...", SwingConstants.CENTER);

        formulario.add(titulo);
        formulario.add(textoJugador1);
        formulario.add(listaJugador1);
        formulario.add(textoJugador2);
        formulario.add(listaJugador2);
        formulario.add(botonCrearJugador);
        formulario.add(botonComenzar);
        formulario.add(etiquetaEstado);

        add(formulario);

        botonCrearJugador.addActionListener(e -> crearJugador());  //Conectamos el botón con la acción que se debe ejecutar cuando lo aprieten
        botonComenzar.addActionListener(e -> comenzarPartida());

        bloquearControles(true);   // Mientras se lee el archivo no se puede tocar nada (Deshabilitamos botones)

        HiloCargaJugadores hiloCarga = new HiloCargaJugadores(archivo, this);
        hiloCarga.start();         // start() crea el hilo nuevo, y ese hilo ejecuta run()
    }

    // ---------------------------------------------------------------------------------
    // Metodos que llaman los hilos (a traves de invokeLater) cuando terminan su trabajo.
    // No tienen modificador: son "default", solo visibles dentro del paquete INTERFAZ,
    // que es donde estan HiloCargaJugadores y TareaGuardarJugador.
    // ---------------------------------------------------------------------------------

    void mostrarJugadores(ArrayList<String> nombres) {   //Agregamos todos los nombres de jugadores.txt a los dos desplegables

        for (String nombre : nombres) {
            listaJugador1.addItem(nombre);
            listaJugador2.addItem(nombre);
        }

        if (nombres.size() >= 2) {
            listaJugador2.setSelectedIndex(1);   // Selecciona el segundo jugador en la lista del jugador 2
        }
    }

    void jugadorCreado(String nombre) {   //Agregamos el jugador recien creado a los desplegables

        listaJugador1.addItem(nombre);
        listaJugador2.addItem(nombre);

        if (listaJugador1.getSelectedItem().equals(listaJugador2.getSelectedItem())) {  //Si ambos desplegables tenian seleccionado el mismo nombre, hace que el segundo seleccione el que se avaba de crear
            listaJugador2.setSelectedItem(nombre);
        }

        etiquetaEstado.setText("Se guardo a " + nombre + ". Jugadores guardados: " + listaJugador1.getItemCount());  //Cambiamos la etiqueta de abajo que actualiza lo ultimo que se hizo
    }

    void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        actualizarEstado();
    }

    void terminarCarga() {

        bloquearControles(false);
        actualizarEstado();
    }

    void terminarGuardado() {

        bloquearControles(false);
    }

    // ---------------------------------------------------------------------------------
    // Acciones de los botones (se ejecutan en el hilo de la ventana).
    // ---------------------------------------------------------------------------------

    private void crearJugador() {

        String nombre = JOptionPane.showInputDialog(this, "Nombre del jugador nuevo:");   //Ventana emergente para escribir

        if (nombre == null) {
            return;   // Apreto "Cancelar"
        }

        nombre = nombre.trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.");
            return;
        }

        bloquearControles(true);
        etiquetaEstado.setText("Guardando jugador...");

        // Se crea el hilo que chequea los nombres del jugadores.txt
        Thread hiloGuardado = new Thread(new TareaGuardarJugador(archivo, nombre, this), "Hilo-GuardarJugador");
        hiloGuardado.start();  //Se ejecuta el run para agregar el nombre al jugadores.txt
    }

    private void comenzarPartida() {

        // getSelectedItem() devuelve Object, y lo pasamos a string.
        String nombreJugador1 = (String) listaJugador1.getSelectedItem();
        String nombreJugador2 = (String) listaJugador2.getSelectedItem();

        if (nombreJugador1 == null || nombreJugador2 == null) {

            JOptionPane.showMessageDialog(this,
                    "Elegi un jugador para cada lado. Si todavia no hay, crealos con \"Crear jugador nuevo\".");
            return;
        }

        if (nombreJugador1.equals(nombreJugador2)) {

            JOptionPane.showMessageDialog(this, "Los dos jugadores tienen que ser distintos.");
            return;
        }

        Jugador jugador1 = new Jugador(nombreJugador1);
        Jugador jugador2 = new Jugador(nombreJugador2);

        ventana.iniciarPreparacion(jugador1, jugador2);  //Le pide a ventana que reemplace PanelInicio por PanelPreparacion
    }

    private void actualizarEstado() {

        int cantidad = listaJugador1.getItemCount();

        if (cantidad < 2) {
            etiquetaEstado.setText("Jugadores guardados: " + cantidad + ". Crea al menos 2 para jugar.");
        } else {
            etiquetaEstado.setText("Jugadores guardados: " + cantidad + ".");
        }
    }

    private void bloquearControles(boolean bloquear) {

        listaJugador1.setEnabled(!bloquear);
        listaJugador2.setEnabled(!bloquear);
        botonCrearJugador.setEnabled(!bloquear);
        botonComenzar.setEnabled(!bloquear);
    }
}
