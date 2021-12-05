import Utils.RegexUtils;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jugador {

    final int PUERTO = 5000;
    private Socket cliente;
    private String mensaje = "";
    private String key;
    private Cipher desCipher;
    private String mensajeEnviado = "";
    private byte[] mensajeEnviadoCifrado;



    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException {
        try {

            //comienzo dialogo solo cn cliente y le pido sus datos y los modifico
            //hasta que no cumplete bien los datos o decida salir estará en el while
            boolean fallido = true;
            Scanner input = new Scanner (System.in);
            JugadorModel jugadorM = new JugadorModel ();
            Jugador jugador = new Jugador();

            while (fallido) {
                System.out.println ("Buenas!! ingresa tus datos:");
                //solicito nombre
                while (fallido) {
                    System.out.println ("Ingresa tu nombre:");
                    String nombre = input.nextLine ();
                    if (RegexUtils.matches (nombre, RegexUtils.NOMBRE_APELLIDO)) {
                        jugadorM.setNombre (nombre);
                        System.out.println ("has ingresado " + nombre);
                        fallido = false;
                    } else {
                        System.out.println ("EL NOMBRE NO PUEDE ESTAR VACIO, Y DEBE SER ALFABETICO");
                    }
                }
                fallido = true;
                //solicito apellido
                while (fallido) {
                    System.out.println ("Ingresa tu apellido:");
                    String apellido = input.nextLine ();
                    if (RegexUtils.matches (apellido, RegexUtils.NOMBRE_APELLIDO)) {
                        jugadorM.setApellido (apellido);
                        System.out.println ("has ingresado " + apellido);
                        fallido = false;
                    } else {
                        System.out.println ("EL APELLIDO NO PUEDE ESTAR VACIO, Y DEBE SER ALFABETICO");
                    }
                }
                fallido = true;
                //solicito edad
                while (fallido) {
                    System.out.println ("Ingresa tu edad:");
                    String edad = input.nextLine ();
                    if ((RegexUtils.matches (edad, RegexUtils.NON_NEGATIVE_INTEGER_FORMAT)) && Integer.parseInt (edad) > 17 && Integer.parseInt (edad) < 110) {
                        jugadorM.setEdad (Integer.parseInt (edad));
                        System.out.println ("has ingresado " + edad);
                        fallido = false;
                    } else {
                        System.out.println ("LA EDAD DEBE SER UN NUMERO Y ESTAR COMPRENDIDA ENTRE 18 Y 109");
                    }
                }
                fallido = true;
                //solicito nick
                while (fallido) {
                    System.out.println ("Ingresa tu NICK:");
                    String nick = input.nextLine ();
                    if (RegexUtils.matches (nick, RegexUtils.TEXT_FORMAT)) {
                        jugadorM.setNick (nick);
                        System.out.println ("has ingresado " + nick);
                        fallido = false;
                    } else {
                        System.out.println ("EL NICK NO PUEDE ESTAR VACIO, Y PUEDE SER ALFANUMERICO");
                    }
                }
                fallido = true;
                //solicito pass
                while (fallido) {
                    System.out.println ("Ingresa tu password: (8 CARACTERES, UN NUMERO, UNA MAYUSCULA Y UN CARACTER ESPECIAL)");
                    String passTemp = input.nextLine ();
                    if (!RegexUtils.matches (passTemp, RegexUtils.PASSWORD_REGEX)) {

                        ////////////////////////////////////probar si lo puedo encriprar guardar y comprobar encriptado por ahora va como char []
                        char[] pass = passTemp.toCharArray ();
                        jugadorM.setPassword (pass);
                        ////////////////////////////////////

                        StringBuilder texto = new StringBuilder ();
                        for (int i = 0; i < pass.length; i++) {
                            texto.append ("*");
                        }
                        System.out.println ("has ingresado: " + texto);
                        fallido = false;
                    } else {
                        System.out.println ("EL PASSWORD DEBE CONTENER AL MENOS: 8 CARACTERES, UN NUMERO, UNA MAYUSCULA Y UN CARACTER ESPECIAL");
                    }
                }
            }

            //una vez creado, conecto con el servidor y le envio el cliente
            jugador.initClient (jugadorM);

        } catch (InvalidKeyException ex) {
            Logger.getLogger (Jugador.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger (Jugador.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger (Jugador.class.getName ()).log (Level.SEVERE, null, ex);
        }
    }


    public void initClient(JugadorModel c) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        try {

            // /////////////////////////////////////////////////////////////////////////
            // Crea el socket y solicita conexion
            cliente = new Socket ("localhost", PUERTO);
            //COMIENZO CON los flujos
            //PROBAR SI PUEDO COMPROBAR SI NICK YA UTILIZADO..SI ME DA EL TIEMPO

            boolean partida = true;


            //creamos los flujos
            //voy a recibir del server la clave para enviarle los mensajes encriptados

            ObjectOutputStream oos = new ObjectOutputStream (cliente.getOutputStream ());
            ObjectInputStream ois = new ObjectInputStream (cliente.getInputStream ());

            System.out.println (c.getNick () + ": 1a) Leyendo la clave publica recibida por el server para iniciar comunicacion segura.");


            //IN
            //obtenemos la clave publica
            PublicKey clave = (PublicKey) ois.readObject ();//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<1A) ENTRA clave
            System.out.println (c.getNick () + ": La clave recibida es: " + clave);



            //OUT
            //SALIDA INFO JUGADOR AL SERVER
            System.out.println (c.getNick () + ": 1b) enviando info de jugador al server.....");

            Cipher cipher = Cipher.getInstance ("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, clave);
            //directamente cifrarlo en un array de bytes

            SealedObject jugadorCipher = new SealedObject(c, cipher);
            oos.writeObject(jugadorCipher);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>1B) sale info jugador



            //IN
            //Leo mensaje intrucciones plano
            String mensaje = ois.readObject ().toString ();//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<2A) ENTRA instruccion plana
            System.out.println (c.getNick () + ": 2a) Instrucciones recibidas: " + mensaje);

            //Verificamos la firma
            System.out.println (c.getNick () + ": 3a) Verificando firma...");
            Signature verificadsa = Signature.getInstance ("SHA1WITHRSA");
            verificadsa.initVerify (clave);

            verificadsa.update (mensaje.getBytes ());
            byte[] firma = (byte[]) ois.readObject ();//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<3A) ENTRA firma
            boolean check = verificadsa.verify (firma);

            //Compruebo la veracidad de la firma
            if (check)
                System.out.println ("\nLas instrucciones son autenticas y podemos empezar a jugar...");
            else
                System.out.println ("\nNo sigas!!! el mensaje que has recibido ha sido alterado. Comunicate con nosotros al telefono...");


            ///EMPIEZA EL JUEGO

            System.out.print ("\n*******************************3 EN LINEA*******************************\n");
            Scanner sc = new Scanner (System.in);
            System.out.print ("Reocgiendo mensajes\n");

            do {
                System.out.print ("Escribir nuevo texto, finaliza con end\n");
                mensajeEnviado = sc.nextLine ();

                // CIFRAR MENSAJE

                mensajeEnviadoCifrado = desCipher.doFinal (mensajeEnviado.getBytes ());

                oos.writeObject (mensajeEnviadoCifrado);//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2B) sale info partida

            } while (!mensajeEnviado.equals ("end"));


            // cierra salida, entrada y el socket
            oos.close ();
            ois.close ();
            cliente.close ();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        } catch (SignatureException e) {
            e.printStackTrace ();
        }
    }





}