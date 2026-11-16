package TPO.BATALLA_NAVAL.INTERFAZ;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import TPO.BATALLA_NAVAL.CLASES.CasillaYaAtacadaException;
import TPO.BATALLA_NAVAL.CLASES.Coordenada;
import TPO.BATALLA_NAVAL.CLASES.Jugador;
import TPO.BATALLA_NAVAL.CLASES.Partida;
import TPO.BATALLA_NAVAL.CLASES.Resultado;

public class PanelJuego extends JPanel {

    private VentanaPrincipal ventana;

    private Partida partida;  //Objeto partida

    private JLabel etiquetaTurno;  //Indica de quien es el turno
    private JLabel etiquetaResultado;  //Arranca con esperando disparo

    private JTextArea tableroPropio;  //Tablero propio dibujado con texto ASCII

    private JButton[][] botonesDisparo;

    private String resumenUltimoTurno;

    public PanelJuego(VentanaPrincipal ventana, Jugador jugador1, Jugador jugador2) {   //Constructor

        this.ventana = ventana;

        partida = new Partida(jugador1,jugador2);

        resumenUltimoTurno = null; 

        mostrarPantallaEspera();
    }

    private void mostrarPantallaEspera() {

        removeAll();

        setLayout(new BorderLayout());  //Nuevo layout para la pantalla espera

        Jugador jugador = partida.getJugadorActual();

        String textoMensaje = "<html><div style='text-align:center;'>";  //HTML para que swing entienda el salto de linea (<br>) y la negrita (<b>...</b>)

        if (resumenUltimoTurno != null) {  //Si hubo un turno antes

                textoMensaje = textoMensaje + "<br><br><b>Turno anterior:</b><br>" + resumenUltimoTurno.replace("\n", "<br>") + "<br><br>";
        }

        textoMensaje = textoMensaje + "<b>Turno de " + jugador.getNombre() + "</b><br>" + "Presiona cuando estes listo para continuar." + "</div></html>";


        JLabel mensaje = new JLabel(textoMensaje,SwingConstants.CENTER);  //Centramos el mensaje

        JButton botonListo = new JButton("Estoy listo");  //Nuevo boton

        botonListo.addActionListener(e ->
                mostrarPantallaJuego()
        );

        add(mensaje, BorderLayout.CENTER);
        add(botonListo, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void mostrarPantallaJuego() {  //Combinamos crear interfaz y actualizar pantalla

        removeAll();

        setLayout(new BorderLayout());

        crearInterfaz();
        actualizarPantalla();

        revalidate();
        repaint();
    }

    private void crearInterfaz() {

        etiquetaTurno = new JLabel();  //Inicialmente vacio (indicará de quien es el turno)

        add(
                etiquetaTurno,
                BorderLayout.NORTH
        );

        JPanel panelCentral = 
                new JPanel(
                        new GridLayout(  //1 fila y 2 columnas (mitad izquierda para tu tablero, mitad derecha para el tablero enemigo), con 10 píxeles de separación entre ambos
                                1,
                                2, 
                                10,
                                10
                        )
                );

        crearTableroPropio(panelCentral); //Columna 1
        crearBotoneraDisparo(panelCentral); //Columna 2

        add(panelCentral,BorderLayout.CENTER);

        etiquetaResultado = 
                new JLabel(
                        "Esperando disparo..."  //Valor inicial antes de disparar
                );

        add(
                etiquetaResultado,
                BorderLayout.SOUTH
        );
    }

    private void crearTableroPropio(
            JPanel panelCentral) {

        JPanel panelPropio = new JPanel(new BorderLayout());  //Sub panel de la izquierda

        JLabel tituloPropio = new JLabel("Tu tablero", SwingConstants.CENTER);

        tableroPropio = new JTextArea();  //Area donde se alojará el tablero propio dibujado en ASCII

        tableroPropio.setEditable(false);  //Impide escritura en ese tablero propio

        tableroPropio.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));  // MONOSPACED hace que todas las letras y espacios midan exactamente los mismos píxeles de ancho

        panelPropio.add(tituloPropio, BorderLayout.NORTH);

        panelPropio.add(new JScrollPane(tableroPropio), BorderLayout.CENTER);  //Envuelve el JTextArea en un panel con barras de desplazamiento por si la ventana se achica y el texto no entra completo.

        panelCentral.add(panelPropio);  //Lo mete en la primera colu,na
    }

    private void crearBotoneraDisparo(JPanel panelCentral) {

        JPanel contenedorRival = new JPanel(new BorderLayout()); // Contiene titulo + botonera rival

        JLabel tituloRival = new JLabel("Tablero rival", SwingConstants.CENTER); // Titulo del tablero interactivo

        JPanel panelDisparos = new JPanel(new GridLayout(11,11)); // Grilla de coordenadas y botones

        botonesDisparo = new JButton[10][10];

        panelDisparos.add( new JLabel(""));  //Casilla vacia arriba a la izquierda

        for (int columna = 1; columna <= 10; columna++) {  //Los numeros de arriba

            JLabel numero = new JLabel(String.valueOf(columna), SwingConstants.CENTER);

            panelDisparos.add(numero);
        }

        for (int fila = 0; fila < 10; fila++) {  //Agregamos las letras de la izquierda

            JLabel letra =
                    new JLabel(
                            String.valueOf(
                                    (char) ('A' + fila)
                            ),
                            SwingConstants.CENTER
                    );

            panelDisparos.add(letra);

            for (int columna = 0; columna < 10; columna++) {  //Aprovechamos el for que recorre las filas para ira gregando los botones

                JButton boton = new JButton();

                botonesDisparo[fila][columna] = boton;

                final int filaSeleccionada = fila;

                final int columnaSeleccionada = columna;

                boton.addActionListener(e ->
                        disparar(filaSeleccionada, columnaSeleccionada)
                );

                panelDisparos.add(boton);
            }
        }

        contenedorRival.add(tituloRival, BorderLayout.NORTH);  //Nombre del rival arriba del tablero botonera

        contenedorRival.add(panelDisparos, BorderLayout.CENTER);  //Botonera

        panelCentral.add(contenedorRival); //Agregamos el panel a la derecha 
    }

    private void disparar(int fila, int columna) {

        Coordenada coordenada = new Coordenada(fila, columna);

        Jugador jugadorQueDispara = partida.getJugadorActual(); // Guarda quien hizo el disparo antes de cambiar el turno
                                                                 

        try {

            Resultado resultado = partida.disparar(coordenada); //De la clase partida

            resumenUltimoTurno = jugadorQueDispara.getNombre() + " disparo en " + coordenada + ".\nResultado: " + resultado+ "."; // Guarda la informacion que vera el siguiente jugador

            actualizarTableros(); //Vuelve a dibujar el JTextArea de la izquierda

            JOptionPane.showMessageDialog(  //una ventana emergente frenando el juego hasta que apretás Aceptar
                    this,
                    "Resultado: "
                            + resultado  //En donde cayó
            );

            if (partida.termino()) {

                terminarPartida();

                return;
            }

            partida.cambiarTurno();  //Se invierten los roles

            mostrarPantallaEspera();

        } catch (CasillaYaAtacadaException e) {

            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private void actualizarBotoneraRival() {

        Jugador rival = partida.getRivalActual(); // Obtiene el tablero enemigo del jugador actual

        for (int fila = 0; fila < 10; fila++) {

            for (int columna = 0; columna < 10; columna++) {

                Coordenada coordenada =
                        new Coordenada(fila, columna); // Coordenada que estamos representando

                JButton boton =
                        botonesDisparo[fila][columna]; // Boton correspondiente a esa coordenada

                if (rival.getTablero().yaRecibioBomba(coordenada)) {

                    char simbolo =
                            rival.getTablero()
                                    .getSimbolo(coordenada, false); // Consulta el estado real del tablero rival

                    boton.setText(
                            String.valueOf(simbolo)
                    ); // O, X, R, etc.

                    boton.setEnabled(false); // Impide volver a disparar ahi

                } else {

                    boton.setText(""); // Casilla desconocida: no revela contenido rival

                    boton.setEnabled(true); // Todavia puede recibir un disparo
                }
            }
        }
    }

    private void actualizarPantalla() {

        Jugador actual =
                partida.getJugadorActual();

        etiquetaTurno.setText("Turno de: " + actual.getNombre());

        actualizarTableros();
        actualizarBotoneraRival();
    }

    private void actualizarTableros() {

        Jugador actual = partida.getJugadorActual();

        tableroPropio.setText(
                actual
                .getTablero()
                .dibujar(true)
        );

    }

    private void terminarPartida() {

        try {

            Jugador ganador =
                    partida.getGanador();

            JOptionPane.showMessageDialog(
                    this,
                    "Ganador: "
                            + ganador.getNombre()
            );

            etiquetaTurno.setText(
                    "Partida finalizada. Ganador: " + ganador.getNombre()
            );

            actualizarBotoneraRival();   // Muestra tambien el ultimo disparo, el que termino la partida

            desactivarDisparos();  //Desactivamos todas las casillas para no poder disparar mas

            mostrarBotonVolver();

        } catch (
                IllegalStateException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void mostrarBotonVolver() {

        JButton botonVolver = new JButton("Volver al menu principal");

        botonVolver.addActionListener(e -> ventana.volverAlInicio());

        remove(etiquetaResultado);              // Sacamos el mensaje de abajo que muestra el resultado para reemplazarlo por el botón de volver 
        add(botonVolver, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void desactivarDisparos() {  //Recorre todas las casillas desactivandolas para no poder disparar ya que terminó la partida

        for (int fila = 0;
                fila < 10;
                fila++) {

            for (int columna = 0;
                    columna < 10;
                    columna++) {

                botonesDisparo
                        [fila]
                        [columna]
                        .setEnabled(false);
            }
        }
    }
}
