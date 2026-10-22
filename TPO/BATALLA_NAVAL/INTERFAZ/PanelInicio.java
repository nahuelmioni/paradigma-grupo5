package TPO.BATALLA_NAVAL.INTERFAZ;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import TPO.BATALLA_NAVAL.CLASES.Jugador;

public class PanelInicio extends JPanel {

    private JTextField campoJugador1;
    private JTextField campoJugador2;
    private JButton botonComenzar;

    private VentanaPrincipal ventana;

    public PanelInicio(VentanaPrincipal ventana) {

        this.ventana = ventana;

        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titulo =
                new JLabel("BATALLA NAVAL CON TRAMPOLINES");

        JLabel textoJugador1 =
                new JLabel("Nombre del jugador 1:");

        campoJugador1 = new JTextField();

        JLabel textoJugador2 =
                new JLabel("Nombre del jugador 2:");

        campoJugador2 = new JTextField();

        botonComenzar =
                new JButton("Comenzar partida");

        add(titulo);
        add(textoJugador1);
        add(campoJugador1);
        add(textoJugador2);
        add(campoJugador2);
        add(botonComenzar);

        botonComenzar.addActionListener(e -> comenzarPartida());
    }

    private void comenzarPartida() {

        String nombreJugador1 =
                campoJugador1.getText().trim();

        String nombreJugador2 =
                campoJugador2.getText().trim();

        if (nombreJugador1.isEmpty()
                || nombreJugador2.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Los dos jugadores deben ingresar un nombre."
            );

            return;
        }

        Jugador jugador1 =
                new Jugador(nombreJugador1);

        Jugador jugador2 =
                new Jugador(nombreJugador2);

        ventana.iniciarPreparacion(
                jugador1,
                jugador2
        );
    }
}
