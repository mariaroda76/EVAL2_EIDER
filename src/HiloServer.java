
import Utils.LogMe;

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HiloServer extends Thread {

    private static LogMe myLog = new LogMe ();
    JugadorModel j;

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



    // EL GETTER LO CREO POR SI TUVIESE LA NECESIDAD DE USAR EL CLORE HANDLER
    public static LogMe getMyLog() {
        return myLog;
    }


    Socket c = new Socket ();

    public HiloServer(Socket c) {
        this.c = c;
    }

    public void run() {


        try {

            System.out.println ("\nSe ha recibido una nueva conexión...");
            byte[] mensajeRecibido = null;
            String mensajeRecibidoDescifrado = "";


            ObjectOutputStream oos = new ObjectOutputStream (c.getOutputStream ());
            ObjectInputStream ois = new ObjectInputStream (c.getInputStream ());


            KeyPairGenerator keygen = KeyPairGenerator.getInstance ("RSA");
            KeyPair par = keygen.generateKeyPair ();
            PrivateKey privada = par.getPrivate ();
            PublicKey publica = par.getPublic ();


            System.out.println ("\nLa clave para comunicacion segura con el nuevo jugador es : " + publica);

            //COMUNICACION DE CLAVES , FIRMAS E INSTRUCCIONES

            try {
                //OUT
                //enviamos la clave publica
                System.out.println ("1a) enviando clave publica para comunicacion segura...");
                oos.writeObject (publica);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>1A) sale clave


                //IN
                //RECIBE INFO DEL JUGADOR
                SealedObject jugadorCipher = (SealedObject) ois.readObject ();//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1B) entra info jugador
                //preparamos el Cipher para descifrar

                Cipher descipher = Cipher.getInstance ("RSA");
                //Cipher descipher = Cipher.getInstance ("T"); //test error log con no such algorithm
                descipher.init (Cipher.DECRYPT_MODE, privada);

                JugadorModel jugadorDescipher = (JugadorModel) jugadorCipher.getObject (descipher);
                j=jugadorDescipher;
                System.out.println ("Nuevo jugador conectado : " + jugadorDescipher.getNick ());

                //LOG ACTIVIDAD
                myLog.getLoggerA ().log (Level.INFO, "Nuevo jugador conectado : " + jugadorDescipher.getNick ());


                //OUT
                // Creamos instrucciones y enviamos
                System.out.println ("2a) enviando instrucciones del Juego firmadas...");
                String mensaje = "Estas son las instrucciones del juego planas";

                oos.writeObject (mensaje);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2A) sale instrucciones planas

                //OUT
                // Creamos la firma digital
                System.out.println ("3a) enviando firma...");
                Signature dsa = Signature.getInstance ("SHA1WITHRSA");
                dsa.initSign (privada);

                dsa.update (mensaje.getBytes ());
                byte[] firma = dsa.sign ();
                oos.writeObject (firma);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>3A) sale instruccion firmada


            } catch (NoSuchAlgorithmException ex) {
                myLog.getLoggerE ().log (Level.SEVERE, null, ex);
                //Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (IOException ex) {
                myLog.getLoggerE ().log (Level.SEVERE, null, ex);
                //Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                myLog.getLoggerE ().log (Level.SEVERE, null, ex);
               // Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (SignatureException ex) {
                myLog.getLoggerE ().log (Level.SEVERE, null, ex);
                //Logger.getLogger (HiloServer.class.getName ()).log (Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException e) {
                myLog.getLoggerE ().log (Level.SEVERE, null, e);
                //e.printStackTrace ();
            } catch (IllegalBlockSizeException e) {
                myLog.getLoggerE ().log (Level.SEVERE, null, e);
                //e.printStackTrace ();
            } catch (BadPaddingException e) {
                myLog.getLoggerE ().log (Level.SEVERE, null, e);
                //e.printStackTrace ();
            } catch (ClassNotFoundException e) {
                myLog.getLoggerE ().log (Level.SEVERE, null, e);
                //e.printStackTrace ();
            }

            //COMUNICACION DE JUEGO

            Game juego = new Game ();
            juego.iniciarJuego (oos, ois, privada,j);


            // cierra los paquetes de datos, el socket y el servidor
            ois.close ();
            oos.close ();

            c.close ();

            System.out.println ("Fin de la conexion");


        } catch (IOException | NoSuchAlgorithmException ex) {
            myLog.getLoggerE ().log (Level.SEVERE, null, ex);
            //Logger.getLogger (ServerJuego.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException e) {
            myLog.getLoggerE ().log (Level.SEVERE, null, e);
            //e.printStackTrace ();
        } catch (InvalidKeyException e) {
            myLog.getLoggerE ().log (Level.SEVERE, null, e);
            //e.printStackTrace ();
        } catch (IllegalBlockSizeException e) {
            myLog.getLoggerE ().log (Level.SEVERE, null, e);
            //e.printStackTrace ();
        } catch (BadPaddingException e) {
            myLog.getLoggerE ().log (Level.SEVERE, null, e);
            //e.printStackTrace ();
        }


    }



}
