package TPO.BATALLA_NAVAL.INTERFAZ;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import TPO.BATALLA_NAVAL.CLASES.Coordenada;
import TPO.BATALLA_NAVAL.CLASES.Jugador;
import TPO.BATALLA_NAVAL.CLASES.ELEMENTOS_MARINOS.Barco;

public class PanelPreparacion extends JPanel {

    private VentanaPrincipal ventana;

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;

    private int indiceBarco;
    private int trampolinesColocados;

    private boolean horizontal;

    private JButton[][] botonesTablero;

    private JLabel etiquetaJugador;
    private JLabel etiquetaInstruccion;
    private JButton botonOrientacion;

    private String[] nombresBarcos = {
            "Lancha",
            "Lancha",
            "Fragata",
            "Acorazado"
    };

    private int[] largosBarcos = {
            2,
            2,
            3,
            4
    };

    public PanelPreparacion(
            VentanaPrincipal ventana,
            Jugador jugador1,
            Jugador jugador2) {

        this.ventana = ventana;

        this.jugador1 = jugador1;
        this.jugador2 = jugador2;

        this.jugadorActual = jugador1;

        this.indiceBarco = 0;
        this.trampolinesColocados = 0;

        this.horizontal = true;

        mostrarPantallaEspera();
    }

    private void mostrarPantallaEspera() {

        removeAll();

        setLayout(new BorderLayout());

        JLabel mensaje = new JLabel(
                jugadorActual.getNombre()
                        + ", presiona el boton cuando estes listo para preparar tu tablero.",
                SwingConstants.CENTER
        );

        JButton botonListo = new JButton("Estoy listo");

        botonListo.addActionListener(e ->
                mostrarPantallaPreparacion()
        );

        add(mensaje, BorderLayout.CENTER);
        add(botonListo, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void mostrarPantallaPreparacion() {

        removeAll();

        setLayout(new BorderLayout());

        crearInterfaz();
        actualizarInformacion();

        revalidate();
        repaint();
    }

    private void crearInterfaz() {

        JPanel panelSuperior =
                new JPanel(new GridLayout(3, 1, 5, 5));

        etiquetaJugador = new JLabel();
        etiquetaInstruccion = new JLabel();

        botonOrientacion =
                new JButton("Orientacion: Horizontal");

        botonOrientacion.addActionListener(e ->
                cambiarOrientacion()
        );

        panelSuperior.add(etiquetaJugador);
        panelSuperior.add(etiquetaInstruccion);
        panelSuperior.add(botonOrientacion);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelTablero =
                new JPanel(new GridLayout(11, 11));

        botonesTablero = new JButton[10][10];

        panelTablero.add(new JLabel(""));

        for (int columna = 1; columna <= 10; columna++) {

            JLabel numero =
                    new JLabel(
                            String.valueOf(columna),
                            SwingConstants.CENTER
                    );

            panelTablero.add(numero);
        }

        for (int fila = 0; fila < 10; fila++) {

            JLabel letra =
                    new JLabel(
                            String.valueOf(
                                    (char) ('A' + fila)
                            ),
                            SwingConstants.CENTER
                    );

            panelTablero.add(letra);

            for (int columna = 0; columna < 10; columna++) {

                JButton boton = new JButton();

                botonesTablero[fila][columna] = boton;

                final int filaSeleccionada = fila;
                final int columnaSeleccionada = columna;

                boton.addActionListener(e ->
                        seleccionarCasilla(
                                filaSeleccionada,
                                columnaSeleccionada
                        )
                );

                panelTablero.add(boton);
            }
        }

        add(panelTablero, BorderLayout.CENTER);
    }

    private void cambiarOrientacion() {

        horizontal = !horizontal;

        if (horizontal) {

            botonOrientacion.setText(
                    "Orientacion: Horizontal"
            );

        } else {

            botonOrientacion.setText(
                    "Orientacion: Vertical"
            );
        }
    }

    private void seleccionarCasilla(
            int fila,
            int columna) {

        Coordenada coordenada =
                new Coordenada(fila, columna);

        if (indiceBarco < 4) {

            colocarBarco(coordenada);

        } else {

            colocarTrampolin(coordenada);
        }
    }

    private void colocarBarco(
            Coordenada coordenada) {

        try {

            int largo =
                    largosBarcos[indiceBarco];

            Barco barco = new Barco(
                    nombresBarcos[indiceBarco],
                    largo
            );

            boolean colocado =
                    jugadorActual
                            .getTablero()
                            .colocarBarco(
                                    barco,
                                    coordenada,
                                    horizontal
                            );

            if (!colocado) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se puede colocar el barco en esa posicion."
                );

                return;
            }

            marcarBarco(
                    coordenada,
                    largo,
                    horizontal
            );

            indiceBarco++;

            actualizarInformacion();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void marcarBarco(
            Coordenada coordenada,
            int largo,
            boolean horizontal) {

        int fila = coordenada.getFila();
        int columna = coordenada.getColumna();

        for (int i = 0; i < largo; i++) {

            if (horizontal) {

                botonesTablero[fila][columna + i]
                        .setText("B");

            } else {

                botonesTablero[fila + i][columna]
                        .setText("B");
            }
        }
    }

    private void colocarTrampolin(
            Coordenada coordenada) {

        boolean colocado =
                jugadorActual
                        .getTablero()
                        .colocarTrampolin(coordenada);

        if (!colocado) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se puede colocar el trampolin en esa posicion."
            );

            return;
        }

        botonesTablero
                [coordenada.getFila()]
                [coordenada.getColumna()]
                .setText("T");

        trampolinesColocados++;

        actualizarInformacion();

        if (trampolinesColocados == 4) {

            terminarPreparacionJugador();
        }
    }

    private void actualizarInformacion() {

        etiquetaJugador.setText(
                "Preparacion de: "
                        + jugadorActual.getNombre()
        );

        if (indiceBarco < 4) {

            etiquetaInstruccion.setText(
                    "Coloca "
                            + nombresBarcos[indiceBarco]
                            + " - Largo "
                            + largosBarcos[indiceBarco]
            );

            botonOrientacion.setEnabled(true);

        } else {

            etiquetaInstruccion.setText(
                    "Coloca trampolin "
                            + (trampolinesColocados + 1)
                            + " de 4"
            );

            botonOrientacion.setEnabled(false);
        }
    }

    private void terminarPreparacionJugador() {

        if (jugadorActual == jugador1) {

            jugadorActual = jugador2;

            indiceBarco = 0;
            trampolinesColocados = 0;
            horizontal = true;

            mostrarPantallaEspera();

        } else {

            ventana.iniciarJuego(
                    jugador1,
                    jugador2
            );
        }
    }
}