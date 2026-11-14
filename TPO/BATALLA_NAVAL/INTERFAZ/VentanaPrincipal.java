package TPO.BATALLA_NAVAL.INTERFAZ;

import javax.swing.JFrame;

import TPO.BATALLA_NAVAL.CLASES.Jugador;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {

        setTitle("Batalla Naval con Trampolines");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelInicio panelInicio = new PanelInicio(this);

        setContentPane(panelInicio);
    }

    public void iniciarPreparacion(Jugador jugador1, Jugador jugador2) {

        PanelPreparacion panelPreparacion =
                new PanelPreparacion(this, jugador1, jugador2);

        setContentPane(panelPreparacion);

        revalidate();
        repaint();
    }

    public void iniciarJuego(Jugador jugador1, Jugador jugador2) {

        PanelJuego panelJuego =
                new PanelJuego(this, jugador1, jugador2);

        setContentPane(panelJuego);

        revalidate();
        repaint();
    }

    // Vuelve al menu principal: pone un PanelInicio nuevo en la ventana.
    // Ese panel arranca otro HiloCargaJugadores, asi que la lista muestra tambien los jugadores creados.
    public void volverAlInicio() {

        PanelInicio panelInicio = new PanelInicio(this);

        setContentPane(panelInicio);

        revalidate();
        repaint();
    }
}