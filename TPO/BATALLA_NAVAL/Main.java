package TPO.BATALLA_NAVAL;

import javax.swing.SwingUtilities;

import TPO.BATALLA_NAVAL.INTERFAZ.VentanaPrincipal;

class Main {  // Modificador default: la clase solo es visible dentro del paquete TPO.BATALLA_NAVAL

    public static void main(String[] args) {

        // La ventana se crea dentro del hilo de Swing (Event Dispatch Thread), como pide Swing.
        SwingUtilities.invokeLater(() -> {

            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
