import java.io.IOException;
import java.util.*;

public class Game {


    Jugador jugadorActual;


    public static Map<Integer, String> preguntas;

    static {
        preguntas = new HashMap<> ();
        preguntas.put (0, "¿Definición de las siglas PHP?");
        preguntas.put (1, "¿PHP es un lenguaje?");
        preguntas.put (2, "¿Qué es POO?");
        preguntas.put (3, "¿Lenguaje de programación de propósito general, " +
                "concurrente, orientado a objetos, que fue diseñado específicamente " +
                "para tener tan pocas dependencias de implementación como fuera posible. " +
                "Su intención es permitir que los desarrolladores de aplicaciones escriban " +
                "el programa una vez y lo ejecuten en cualquier dispositivo? ");
        preguntas.put (4, "¿Es un lenguaje de programación?");
        preguntas.put (5, "¿Qué es MongoDB?");
        preguntas.put (6, "¿Mascota oficial de Linux?");
        preguntas.put (7, "¿Ejemplos de software libre?");
        preguntas.put (8, "¿Cuáles son los tipos de lenguaje de programación?");
        preguntas.put (9, "¿Que es un Lenguaje Maquina?");
        preguntas.put (10, "¿Algunos tipos de datos que se manejan en Programación?");
        preguntas.put (11, "¿Qué es el compilador Java?");
        preguntas.put (12, "¿Que es una Clase en Programación?");
        preguntas.put (13, "¿Que es un atributo en Programación ?");
        preguntas.put (14, "¿Qué es MVC?");

    }

    public static Map<Integer, List<String>> respuestasPosibles;

    static {
        respuestasPosibles = new HashMap<> ();
        respuestasPosibles.put (0, Arrays.asList (
                "Hypertext Pre-Processor",
                "Page Hypertext Processor",
                "Processor web page",
                "Server Processor"));
        respuestasPosibles.put (1, Arrays.asList (
                "Interpretado",
                "Compilado",
                "Ensamblador",
                "Maquina"));
        respuestasPosibles.put (2, Arrays.asList (
                "Programación orientada a objetos",
                "Programación por procesos"));
        respuestasPosibles.put (3, Arrays.asList (
                "JAVA",
                "PHP",
                "PYTHON",
                "C#"));
        respuestasPosibles.put (4, Arrays.asList (
                "HTML5",
                "AJAX",
                "PYTHON",
                "MONGO"));
        respuestasPosibles.put (5, Arrays.asList (
                "Sistema de base de datos NoSQL orientado a documentos",
                "Sistema de base de datos con  lenguaje de consulta estructurada"));
        respuestasPosibles.put (6, Arrays.asList (
                "Tux",
                "Rux",
                "GNU",
                "Pacman"));
        respuestasPosibles.put (7, Arrays.asList (
                "Adobe Reader",
                "Okular",
                "Photoshop",
                "Winrar"));
        respuestasPosibles.put (8, Arrays.asList (
                "MS-DOS, C, C++, Java, Visual Basic, SQL, SQL Server",
                "El lenguaje máquina, Lenguajes ensambladores y de lenguajes de alto nivel",
                "Estructural, Máquina",
                "De bajo nivel y de alto nivel"));
        respuestasPosibles.put (9, Arrays.asList (
                "Es el que entiende la computadora, es su lenguaje natural. En él sólo se pueden utilizar dos símbolos, el cero (0) y el uno (1).",
                "Es el que entiende la computadora y es legible para el ser humano, es su lenguaje natural."));
        respuestasPosibles.put (10, Arrays.asList (
                "String, Boleano, Numeros.",
                "Simbólicos, de estructura, de cadena.",
                "Tipo Java, C++, Smalltalk.",
                " Visual Basic, Delphi, Perl."));
        respuestasPosibles.put (11, Arrays.asList (
                "Es un programa contable que traduce un programa escrito en un lenguaje de programación a otro lenguaje de programación.",
                "Es un programa que transforma código fuente escrito en java a un código neutral a la plataforma conocido como java.",
                "Es un entorno de desarrollo integrado libre hecho principalmente para el lenguaje de programación Java.",
                "Es un atributo de una parte de los datos que indica al ordenador (y/o al programador) algo sobre la clase de datos sobre los que se va a procesar."));
        respuestasPosibles.put (12, Arrays.asList (
                "Es una colección o conjunto de objetos que comparten características comunes entre si.",
                "Es un atributo de una parte de los datos que indica al ordenador (y/o al programador) algo sobre la clase de datos sobre los que se va a procesar",
                "Es un programa contable que traduce un programa escrito en un lenguaje de programación a otro lenguaje de programación."));
        respuestasPosibles.put (13, Arrays.asList (
                "Es un entorno de desarrollo integrado libre hecho principalmente para un lenguaje de programación.",
                "Son las cosas individuales que diferencian una clase de objetos de otros y determinan la apariencia estado y otras cualidades de la clase.",
                "Es un atributo de una parte de los datos que indica al ordenador (y/o al programador) algo sobre la clase de datos sobre los que se va a procesar."));
        respuestasPosibles.put (14, Arrays.asList (
                "Modelo Vista Controlador",
                "Moda Visten Cuantos",
                "Macro Vista Controlador"));
    }

    public static Map<Integer, Integer> respuestasCorrectas;

    static {
        respuestasCorrectas = new HashMap<> ();
        respuestasCorrectas.put (0, 0);
        respuestasCorrectas.put (1, 0);
        respuestasCorrectas.put (2, 0);
        respuestasCorrectas.put (3, 0);
        respuestasCorrectas.put (4, 2);
        respuestasCorrectas.put (5, 0);
        respuestasCorrectas.put (6, 0);
        respuestasCorrectas.put (7, 1);
        respuestasCorrectas.put (8, 3);
        respuestasCorrectas.put (9, 0);
        respuestasCorrectas.put (10, 0);
        respuestasCorrectas.put (11, 1);
        respuestasCorrectas.put (12, 0);
        respuestasCorrectas.put (13, 1);
        respuestasCorrectas.put (14, 0);

    }

    public static void iniciarJuego() throws IOException {

        ///Scanner input= new Scanner(socket.getInputStream());

        for (Map.Entry<Integer, String> preguntasM : preguntas.entrySet()) {
            System.out.println(preguntasM.getValue());
            System.out.println ("\tSelecciona respuesta Correcta: ");
            for (Map.Entry<Integer, List<String>> RespPosM : respuestasPosibles.entrySet()) {

                if(preguntasM.getKey() == RespPosM.getKey() ){
                    List<String> pregList = RespPosM.getValue ();
                    for (int i = 0; i <pregList.size () ; i++) {
                        System.out.println ("\t\t"+ i +"-"+ pregList.get (i));

                    }
                }
            }



        }

        

    }

    public static void main(String[] args) throws IOException {
        iniciarJuego ();
    }

}
