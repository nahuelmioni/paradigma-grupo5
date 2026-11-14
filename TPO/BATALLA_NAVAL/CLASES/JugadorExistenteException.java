package TPO.BATALLA_NAVAL.CLASES;

// Excepcion propia (checked): hereda de Exception, asi que quien llame a un metodo
// que la lanza esta obligado a atraparla (catch) o a declararla (throws).
public class JugadorExistenteException extends Exception {

    public JugadorExistenteException(String nombre) {
        super("Ya existe un jugador llamado " + nombre + ".");
    }
}
