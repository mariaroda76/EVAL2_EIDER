import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HiloServer extends Thread {


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


    Socket c = new Socket ();

    public HiloServer(Socket c) {
        this.c = c;
    }

    public void run() {
        try {
            byte[] mensajeRecibido = null;
            String mensajeRecibidoDescifrado = "";


            ObjectOutputStream oos = new ObjectOutputStream (c.getOutputStream ());
            ObjectInputStream ois = new ObjectInputStream (c.getInputStream ());


            KeyPairGenerator keygen = KeyPairGenerator.getInstance ("RSA");
            KeyPair par = keygen.generateKeyPair ();
            PrivateKey privada = par.getPrivate ();
            PublicKey publica = par.getPublic ();


            System.out.println ("La clave para el cliente es : " + publica);

            //COMUNICACION DE CLAVES , FIRMAS E INSTRUCCIONES

            try {
                //mandamos la clave publica
                oos.writeObject (publica);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>1A) sale clave

                // Creamos la firma digital
                System.out.println ("Envio de instrucciones del Juego firmadas");
                String mensaje = "Estas son las instrucciones del juego planas";

                oos.writeObject (mensaje);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2A) sale instrucciones planas

                Signature dsa = Signature.getInstance ("SHA1WITHRSA");
                dsa.initSign (privada);

                dsa.update (mensaje.getBytes ());
                byte[] firma = dsa.sign (); //MENSAJE FIRMADO
                oos.writeObject (firma);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>3A) sale instruccion firmada


            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (SignatureException ex) {
                Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            }

            //COMUNICACION DE JUGO

            try {
                do {
                    try {
                        //recibimos texto encriptado del cliente
                        mensajeRecibido = (byte[]) ois.readObject ();//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1B) RECIBIMOS COMUNICACION DE CLIENTE

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger (ServerJuego.class.getName ()).log (Level.SEVERE, null, ex);
                    }

                    //preparamos el Cipher para descifrar
                    Cipher descipher = Cipher.getInstance ("RSA");
                    descipher.init (Cipher.DECRYPT_MODE, privada);

                    mensajeRecibidoDescifrado = new String (descipher.doFinal (mensajeRecibido));
                    System.out.println ("Mensaje descifrado con clave privada: " + mensajeRecibidoDescifrado);

                    //AQUI DEBERIA ENVIARLE RESPUESTA DEL JUEGO SEA LO QUE SEA


                } while (!mensajeRecibidoDescifrado.equals ("end"));

            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger (ServerJuego.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger (ServerJuego.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace ();
            } catch (InvalidKeyException e) {
                e.printStackTrace ();
            }

            // cierra los paquetes de datos, el socket y el servidor
            ois.close ();
            oos.close ();

            c.close ();

            System.out.println ("Fin de la conexion");


        } catch (IOException | NoSuchAlgorithmException ex) {
            Logger.getLogger (ServerJuego.class.getName ()).log (Level.SEVERE, null, ex);
        }


    }


}
