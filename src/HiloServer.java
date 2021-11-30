import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HiloServer extends Thread  {


    //La aplicación representa un juego en el que desde el servidor se lanzan
    //preguntas a los jugadores conectados y estos responden, si la respuesta es
    //correcta se irán anotando puntos.
    //Los jugadores deben darse de alta para poder participar, por lo que desde el
    //servidor se pedirán ciertos datos que el cliente debe enviar(nombre, apellido,
    //edad, nick y contraseña). Seria conveniente que todos los datos introducidos
    //se validen a través de patrones.
    //Una vez creado el nuevo jugador puede empezar a jugar.
    //Cuando un jugador decida acabar su partida el servidor le devolverá el total de
    //puntos obtenidos durante la partida.
    //La dinámica del juego es libre, el programador es quien decide como se juega.



    Socket c = new Socket();

    public HiloServer(Socket c) {
        this.c = c;
    }

    public void run() {



        try {
            byte[] mensajeRecibido = null;

            KeyGenerator keygen = null;
            Cipher desCipher = null;
            String mensajeRecibidoDescifrado = "";

            System.out.println("Obteniendo generador de claves con cifrado DES");
            try {
                keygen = KeyGenerator.getInstance("DES");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Generando clave");
            SecretKey key = keygen.generateKey();
            // //////////////////////////////////////////////////////////////
            // CONVERTIR CLAVE A STRING Y VISUALIZAR/////////////////////////
            // obteniendo la version codificada en base 64 de la clave

            System.out.println("La clave es: " + key);

            // //////////////////////////////////////////////////////////////
            // CREAR CIFRADOR Y PONER EN MODO DESCIFRADO//////////////////
            System.out.println("Obteniendo objeto Cipher con cifraddo DES");
            try {
                desCipher = Cipher.getInstance("DES");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Configurando Cipher para desencriptar");
            try {
                desCipher.init(Cipher.DECRYPT_MODE, key);
            } catch (InvalidKeyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Enviamos la clave
            ObjectOutputStream oos = new ObjectOutputStream(c.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
            oos.writeObject(key);
            try {
                do {
                    try {
                        //
                        mensajeRecibido = (byte[]) ois.readObject();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServerJuego.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mensajeRecibidoDescifrado = new String(desCipher.doFinal(mensajeRecibido));
                    System.out.println("El texto enviado por el cliente y descifrado por el servidor es : " + new String(mensajeRecibidoDescifrado));

                } while (!mensajeRecibidoDescifrado.equals("end"));

            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(ServerJuego.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(ServerJuego.class.getName()).log(Level.SEVERE, null, ex);
            }

            // cierra los paquetes de datos, el socket y el servidor
            ois.close();
            oos.close();

            c.close();

            System.out.println("Fin de la conexion");


        } catch (IOException ex) {
            Logger.getLogger(ServerJuego.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


}
