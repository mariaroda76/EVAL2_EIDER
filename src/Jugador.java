import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jugador
{

    final int                PUERTO  = 5000;
    private Socket           cliente;
    private String           mensaje = "";
    private String           key;
    private Cipher           desCipher;
    private String           mensajeEnviado  = "";
    private byte[]           mensajeEnviadoCifrado;


    private String nombre;
    private String apellido;
    private int edad;
    private String nick;
    private String password;

    public Jugador(String nombre, String apellido, int edad, String nick, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nick = nick;
        this.password = password;
    }

    public Jugador() {
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException
    {
        try {

            //comienzo dialogo solo cn cliente y le pido sus datos y los modifico
            //hasta que no cumplete bien los datos o decida salir estará en el while
            boolean fallido = true;

            while(fallido) {
                System.out.println ("Buenas!! ingresa tus datos:");




            }




            // Crea el cliente
            Jugador c = new Jugador ();
            c.initClient();
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void initClient() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException
    {
        try
        {

            // /////////////////////////////////////////////////////////////////////////
            // Crea el socket y solicita conexion
            cliente = new Socket("localhost", PUERTO);

            //creamos los flujos
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            //recogemos del flujo la clave simetrica
            SecretKey key = (SecretKey) ois.readObject();
            System.out.println("le clave es : " + key);
            System.out.println("Configurando Cipher para encriptar");
            desCipher = Cipher.getInstance("DES");

            desCipher.init(Cipher.ENCRYPT_MODE, key);
            Scanner sc = new Scanner(System.in);
            System.out.print("Reocgiendo mensajes\n");

            do
            {
                System.out.print("Escribir nuevo texto, finaliza con end\n");
                mensajeEnviado = sc.nextLine();

                // CIFRAR MENSAJE

                mensajeEnviadoCifrado = desCipher.doFinal(mensajeEnviado.getBytes());

                oos.writeObject(mensajeEnviadoCifrado);

            } while (!mensajeEnviado.equals("end"));


            // cierra salida, entrada y el socket
            oos.close();
            ois.close();
            cliente.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




}