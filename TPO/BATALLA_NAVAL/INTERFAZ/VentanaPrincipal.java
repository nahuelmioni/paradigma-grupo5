package TPO.BATALLA_NAVAL.INTERFAZ;

import javax.swing.JFrame;

import TPO.BATALLA_NAVAL.CLASES.Jugador;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {  //Apenas se crea la ventana, crea el panel inicio para mostrar algo (constructor)

        setTitle("Batalla Naval con Trampolines");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Al cerrar la ventana, el programa muere
        setLocationRelativeTo(null);  //Se centra la pantalla en el medio del monitor

        PanelInicio panelInicio = new PanelInicio(this);  //Se crea panel inicio con referencia a la ventana

        setContentPane(panelInicio);  //Se enchufa panelInicio a la ventana
    }

    public void iniciarPreparacion(Jugador jugador1, Jugador jugador2) {

        PanelPreparacion panelPreparacion =
                new PanelPreparacion(this, jugador1, jugador2);   //Se crea el panel preparacion

        setContentPane(panelPreparacion);  //Se enchufa panelPreparacion a la ventana

        revalidate();
        repaint();
    }

    public void iniciarJuego(Jugador jugador1, Jugador jugador2) {

        PanelJuego panelJuego =
                new PanelJuego(this, jugador1, jugador2);

        setContentPane(panelJuego);  //Se enchufa panelJuego a la ventana

        revalidate();
        repaint();
    }

    // Vuelve al menu principal: pone un PanelInicio nuevo en la ventana.
    // Ese panel arranca otro HiloCargaJugadores, asi que la lista muestra tambien los jugadores creados.
    public void volverAlInicio() {

        PanelInicio panelInicio = new PanelInicio(this);  //Crea un nuevo panel inicio 

        setContentPane(panelInicio);  //Enchufa el nuevo panel inicio

        revalidate();
        repaint();
    }
}