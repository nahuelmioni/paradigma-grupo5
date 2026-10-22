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
                new PanelJuego(jugador1, jugador2);

        setContentPane(panelJuego);

        revalidate();
        repaint();
    }
}