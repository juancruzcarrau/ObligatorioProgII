package TADs.hash;

public class KeyNoExistenteRuntimeExeption extends RuntimeException{

    public KeyNoExistenteRuntimeExeption() {
    }

    public KeyNoExistenteRuntimeExeption(String message) {
        super(message);
    }
}
