import Utils.LogMe;

import java.net.*;
import java.io.*;
import java.util.logging.Level;


public class ServerJuego {

    private static LogMe myLog = new LogMe ();

    final int PUERTO = 5000;
    private ServerSocket Servidor;
    private Socket socket;



    public static LogMe getMyLog() {
        return myLog;
    }

    public static void main(String[] args) throws Exception {

        // Crea el servidor
        ServerJuego s = new ServerJuego ();
        s.initServer ();

    }


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


    public void initServer() throws IOException, ClassNotFoundException {


        System.out.println ("Incializando servidor");

        //LOG ACTIVIDAD
        myLog.getLoggerA ().log (Level.INFO, "Incializando servidor");


        // Crea el Socket de servicio
        Servidor = new ServerSocket (PUERTO);
        socket = new Socket ();
        // Espera conexión de un cliente
        System.out.println ("Esperando conexion de jugadores...");



        while (true) {
            socket = Servidor.accept ();
            HiloServer comunicacion = new HiloServer (socket);
            comunicacion.start ();
        }




    }


}





















