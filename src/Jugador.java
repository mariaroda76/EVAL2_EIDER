import Utils.RegexUtils;
import Utils.StringUtils;

import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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


    private String nombre;
    private String apellido;
    private int edad;
    private String nick;
    private char[] password;

    public Jugador(String nombre, String apellido, int edad, String nick, char[] password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nick = nick;
        this.password = password;
    }

    public Jugador() {
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException {
        try {

            //comienzo dialogo solo cn cliente y le pido sus datos y los modifico
            //hasta que no cumplete bien los datos o decida salir estará en el while
            boolean fallido = true;
            Scanner input = new Scanner (System.in);
            Jugador jugador = new Jugador ();

            while (fallido) {
                System.out.println ("Buenas!! ingresa tus datos:");
                //solicito nombre
                while (fallido) {
                    System.out.println ("Ingresa tu nombre:");
                    String nombre = input.nextLine ();
                    if (RegexUtils.matches (nombre, RegexUtils.NOMBRE_APELLIDO)) {
                        jugador.setNombre (nombre);
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
                        jugador.setApellido (apellido);
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
                        jugador.setEdad (Integer.parseInt (edad));
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
                        jugador.setNick (nick);
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
                        jugador.setPassword (pass);
                        ////////////////////////////////////

                        StringBuilder texto = new StringBuilder();
                        for (int i = 0; i < pass.length; i++) {
                            texto.append("*");
                        }
                        System.out.println ("has ingresado: " + texto);
                        fallido = false;
                    } else {
                        System.out.println ("EL PASSWORD DEBE CONTENER AL MENOS: 8 CARACTERES, UN NUMERO, UNA MAYUSCULA Y UN CARACTER ESPECIAL");
                    }
                }
            }



            // Crea el cliente
            Jugador c = new Jugador ();
            c.initClient ();
        } catch (InvalidKeyException ex) {
            Logger.getLogger (Jugador.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger (Jugador.class.getName ()).log (Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger (Jugador.class.getName ()).log (Level.SEVERE, null, ex);
        }
    }


    public void initClient() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        try {

            // /////////////////////////////////////////////////////////////////////////
            // Crea el socket y solicita conexion
            cliente = new Socket ("localhost", PUERTO);
            //COMIENZO CON los flujos
            //PROBAR SI PUEDO COMPROBAR SI NICK YA UTILIZADO..SI ME DA EL TIEMPO


            //creamos los flujos
            ObjectInputStream ois = new ObjectInputStream (cliente.getInputStream ());
            ObjectOutputStream oos = new ObjectOutputStream (cliente.getOutputStream ());
            //recogemos del flujo la clave simetrica
            SecretKey key = (SecretKey) ois.readObject ();
            System.out.println ("le clave es : " + key);
            System.out.println ("Configurando Cipher para encriptar");
            desCipher = Cipher.getInstance ("DES");

            desCipher.init (Cipher.ENCRYPT_MODE, key);
            Scanner sc = new Scanner (System.in);
            System.out.print ("Reocgiendo mensajes\n");

            do {
                System.out.print ("Escribir nuevo texto, finaliza con end\n");
                mensajeEnviado = sc.nextLine ();

                // CIFRAR MENSAJE

                mensajeEnviadoCifrado = desCipher.doFinal (mensajeEnviado.getBytes ());

                oos.writeObject (mensajeEnviadoCifrado);

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
        }
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
}