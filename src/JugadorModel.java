import java.io.Serializable;
import java.util.Arrays;

public class JugadorModel implements Serializable {

    private String nombre;
    private String apellido;
    private int edad;
    private String nick;
    private char[] password;
    private int finalScore;

    public JugadorModel(String nombre, String apellido, int edad, String nick, char[] password, int finalScore) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nick = nick;
        this.password = password;
        this.finalScore = finalScore;
    }

    public JugadorModel() {
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SCORE: " +
                "NICK=" + nick +
                "FINAL SCORE= " + finalScore
                ;
    }
}
