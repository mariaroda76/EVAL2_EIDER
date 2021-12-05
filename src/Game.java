import java.util.*;

public class Game {

    Jugador jugadorActual;
    


    public static Map<Integer, String> preguntas;

    static {
        preguntas = new HashMap<> ();
        preguntas.put (14, "¿Definición de las siglas PHP?");
        preguntas.put (15, "¿PHP es un lenguaje?");
        preguntas.put (16, "¿Qué es POO?");
        preguntas.put (17, "¿Lenguaje de programación de propósito general, " +
                "concurrente, orientado a objetos, que fue diseñado específicamente " +
                "para tener tan pocas dependencias de implementación como fuera posible. " +
                "Su intención es permitir que los desarrolladores de aplicaciones escriban " +
                "el programa una vez y lo ejecuten en cualquier dispositivo? ");
        preguntas.put (21, "¿Es un lenguaje de programación?");
        preguntas.put (24, "¿Qué es MongoDB?");
        preguntas.put (28, "¿Mascota oficial de Linux?");
        preguntas.put (29, "¿Ejemplos de software libre?");
        preguntas.put (34, "¿Cuáles son los tipos de lenguaje de programación?");
        preguntas.put (35, "¿Que es un Lenguaje Maquina?");
        preguntas.put (36, "¿Algunos tipos de datos que se manejan en Programación?");
        preguntas.put (37, "¿Qué es el compilador Java?");
        preguntas.put (38, "¿Que es una Clase en Programación?");
        preguntas.put (39, "¿Que es un atributo en Programación ?");
        preguntas.put (40, "¿Qué es MVC?");

    }

    public static Map<Integer, List<String>> respuestasPosibles;

    static {
        respuestasPosibles = new HashMap<> ();
        respuestasPosibles.put (14, Arrays.asList (
                "Hypertext Pre-Processor",
                "Page Hypertext Processor",
                "Processor web page",
                "Server Processor"));
        respuestasPosibles.put (15, Arrays.asList (
                "Interpretado",
                "Compilado",
                "Ensamblador",
                "Maquina"));
        respuestasPosibles.put (16, Arrays.asList (
                "Programación orientada a objetos",
                "Programación por procesos"));
        respuestasPosibles.put (17, Arrays.asList (
                "JAVA",
                "PHP",
                "PYTHON",
                "C#"));
        respuestasPosibles.put (21, Arrays.asList (
                "HTML5",
                "AJAX",
                "PYTHON",
                "MONGO"));
        respuestasPosibles.put (24, Arrays.asList (
                "Sistema de base de datos NoSQL orientado a documentos",
                "Sistema de base de datos con  lenguaje de consulta estructurada"));
        respuestasPosibles.put (28, Arrays.asList (
                "Tux",
                "Rux",
                "GNU",
                "Pacman"));
        respuestasPosibles.put (29, Arrays.asList (
                "Adobe Reader",
                "Okular",
                "Photoshop",
                "Winrar"));
        respuestasPosibles.put (34, Arrays.asList (
                "MS-DOS, C, C++, Java, Visual Basic, SQL, SQL Server",
                "El lenguaje máquina, Lenguajes ensambladores y de lenguajes de alto nivel",
                "Estructural, Máquina",
                "De bajo nivel y de alto nivel"));
        respuestasPosibles.put (35, Arrays.asList (
                "Es el que entiende la computadora, es su lenguaje natural. En él sólo se pueden utilizar dos símbolos, el cero (0) y el uno (1).",
                "Es el que entiende la computadora y es legible para el ser humano, es su lenguaje natural."));
        respuestasPosibles.put (36, Arrays.asList (
                "String, Boleano, Numeros.",
                "Simbólicos, de estructura, de cadena.",
                "Tipo Java, C++, Smalltalk.",
                " Visual Basic, Delphi, Perl."));
        respuestasPosibles.put (37, Arrays.asList (
                "Es un programa contable que traduce un programa escrito en un lenguaje de programación a otro lenguaje de programación.",
                "Es un programa que transforma código fuente escrito en java a un código neutral a la plataforma conocido como java.",
                "Es un entorno de desarrollo integrado libre hecho principalmente para el lenguaje de programación Java.",
                "Es un atributo de una parte de los datos que indica al ordenador (y/o al programador) algo sobre la clase de datos sobre los que se va a procesar."));
        respuestasPosibles.put (38, Arrays.asList (
                "Es una colección o conjunto de objetos que comparten características comunes entre si.",
                "Es un atributo de una parte de los datos que indica al ordenador (y/o al programador) algo sobre la clase de datos sobre los que se va a procesar",
                "Es un programa contable que traduce un programa escrito en un lenguaje de programación a otro lenguaje de programación."));
        respuestasPosibles.put (39, Arrays.asList (
                "Es un entorno de desarrollo integrado libre hecho principalmente para un lenguaje de programación.",
                "Son las cosas individuales que diferencian una clase de objetos de otros y determinan la apariencia estado y otras cualidades de la clase.",
                "Es un atributo de una parte de los datos que indica al ordenador (y/o al programador) algo sobre la clase de datos sobre los que se va a procesar."));
        respuestasPosibles.put (40, Arrays.asList (
                "Modelo Vista Controlador",
                "Moda Visten Cuantos",
                "Macro Vista Controlador"));
    }

    public static Map<Integer, Integer> respuestasCorrectas;
    static {
        respuestasCorrectas = new HashMap<> ();
        respuestasCorrectas.put (14, 0);
        respuestasCorrectas.put (15, 0);
        respuestasCorrectas.put (16, 0);
        respuestasCorrectas.put (17, 0);
        respuestasCorrectas.put (21, 2);
        respuestasCorrectas.put (24, 0);
        respuestasCorrectas.put (28, 0);
        respuestasCorrectas.put (29, 1);
        respuestasCorrectas.put (34, 3);
        respuestasCorrectas.put (35, 0);
        respuestasCorrectas.put (36, 0);
        respuestasCorrectas.put (37, 1);
        respuestasCorrectas.put (38, 0);
        respuestasCorrectas.put (39, 1);
        respuestasCorrectas.put (40, 0);

    }

}
