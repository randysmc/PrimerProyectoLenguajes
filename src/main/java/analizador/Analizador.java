/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import enums.Token;
import java.util.ArrayList;
import java.util.Scanner;

public class Analizador {

    ArrayList<String> arrIdentificadores;
    ArrayList<String> arrNumeros;
    ArrayList<String> arrDecimales;
    ArrayList<String> arrErrores;
    ArrayList<String> arrLexemas;
    ArrayList<String> arrSignosPuntuacion;
    ArrayList<String> arrOperadoresAritmeticos;
    ArrayList<String> arrSignosAgrupacion;

    String lexema; //Aqui guardaremos el lexema que se forma al analizar cada palabra
    int estado; //Guardamos los distintos estados a los que el lexema se puede mover
    int indice;// llevamos el control del indice de la palabra que estamos analizando
    String palabraLimpia;
    Token tipoToken;
    int cantErroresLexicos;
    int cantLexemas, cantDigitos, cantDecimales, cantSignosAgrupacion,cantOperadoresAr, cantSignosPunt;
    
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int eleccion = 0;
        Analizador analizador = new Analizador();

        while (eleccion != 1) {
            analizador.inicializarVariables();
            System.out.println("\n\nIngrese la palabra a descomponer");
            String palabraEntrada = teclado.nextLine();
            palabraEntrada = palabraEntrada+" ";
            analizador.getPalabraEntrada(palabraEntrada);
            analizador.mostrarResultados();
        }

    }

    public void inicializarVariables() {
        arrIdentificadores = new ArrayList();
        arrNumeros = new ArrayList();
        arrDecimales = new ArrayList();
        arrErrores = new ArrayList();
        arrSignosAgrupacion = new ArrayList();
        arrOperadoresAritmeticos = new ArrayList();
        arrSignosPuntuacion = new ArrayList();
        lexema = "";
        estado = 0;
        indice = 0;
        palabraLimpia = "";
        cantErroresLexicos = 0; cantLexemas=0;
        cantDigitos=0; cantDecimales=0; 
        cantSignosAgrupacion=0; cantOperadoresAr=0; 
        cantSignosPunt=0;

    }
    
    public void mostrarResultados(){
        System.out.println("cantidad de errores: " +cantErroresLexicos);
    
    }

    public void getPalabraEntrada(String palabraEntrada) {
        //for para recorrer la palabra indice por indice y obtener cada caracter
        for (indice = 0; indice < palabraEntrada.length(); indice++) {
            //almacenamos el caracter de cada indice en letra
            char letra = palabraEntrada.charAt(indice);

            //Sentencia para elimiar caracteres extras que puedan venir en la palabra, como 
            //tab, retorno carro, salto de linea etc...
            switch (letra) {
                case '\r':
                case '\t':
                case '\n':
                case '\b':
                case '\f':

                    break;
                default:
                    //guardamos nuestra palabra sin caracteres extranos
                    palabraLimpia += letra;
            }
        }
        analizarPalabra(palabraLimpia);

    }

    public void analizarPalabra(String palabraLimpia) {

        for (indice = 0; indice < palabraLimpia.length(); indice++) {
            char letra = palabraLimpia.charAt(indice);

            switch (estado) {
                case 0:
                    if (Character.isSpaceChar(letra)) {
                        estado = 0;
                        //Verificamos si es un caracter de tipo puntuacion
                    } else if ((letra == '.') || (letra == ',') || (letra == ':') || (letra == ';')) {
                        System.out.println("Vino un signo de puntuacion: '" + letra + "'");
                        System.out.println("Moviendose al estado 1");
                        lexema = "" + letra;
                        estado = 1;

                        //si es operador aritmetico
                    } else if ((letra == '+') || (letra == '-') || (letra == '*') || (letra == '/') || (letra == '%')) {
                        System.out.println("Vino un operador aritmetico: '" + letra + "'");
                        System.out.println("Moviendose al estado 2");
                        lexema = "" + letra;
                        estado = 2;

                        //signo de agrupacion
                    } else if ((letra == '(') || (letra == ')') || (letra == '[') || (letra == ']') || (letra == '{') || (letra == '}')) {
                        System.out.println("Vino un signo de agrupacion: '" + letra + "'");
                        System.out.println("Moviendose al estado 3");
                        lexema = "" + letra;
                        estado = 3;

                        //si es digito
                    } else if (Character.isDigit(letra)) {
                        System.out.println("Vino un digito: '" + letra + "'");
                        System.out.println("Moviendose al estado 4");
                        lexema = "" + letra;
                        estado = 4;

                        //si es letra
                    } else if (Character.isLetter(letra)) {
                        System.out.println("Vino una letra");
                        System.out.println("moviendose al estado 5");
                        lexema = "" + letra;
                        estado = 5;
                    } else {

                        System.out.println("Error lexico, aca no debe suceder :" + letra);
                        cantErroresLexicos++;
                        arrErrores.add("" + letra);
                        //estado=0;

                    }

                    break;

                //Estado de aceptacion
                case 1:
                    //en esta condicion verificamos si es un signo de puntuacion
                    //se mantiene en el mismo estado si lo es, sino se acepta el lexema
                    if ((letra == '.') || (letra == ',') || (letra == ':') || (letra == ';')) {
                        System.out.println("otro signo de puntuacion");
                        System.out.println("sigo en el estado 1");
                        lexema += letra;
                        estado = 1;

                        //primer estado de aceptacion
                    } else {
                        System.out.println("Lexema encontrado");
                        System.out.println(lexema);
                        //arrLexemas.add(lexema);
                        indice--;
                        lexema = "";
                        estado = 0;

                    }
                    break;

                //Estado de aceptacion
                case 2:
                    if ((letra == '+') || (letra == '-') || (letra == '*') || (letra == '/') || (letra == '%')) {
                        System.out.println("otro operador aritmetico");
                        System.out.println("sigo en el estado 2");
                        lexema += letra;
                        estado = 2;

                        //segundo estado de aceptacion
                    } else {
                        System.out.println("Lexema encontrado");
                        System.out.println(lexema);
                        arrLexemas.add(lexema);
                        indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //tercer estado de aceptacion
                case 3:
                    if ((letra == '(') || (letra == ')') || (letra == '[') || (letra == ']') || (letra == '{') || (letra == '}')) {
                        System.out.println("signo de agrupacion");
                        System.out.println("sigo en el estado 3");
                        lexema += letra;
                        estado = 3;

                    } else {
                        System.out.println("Lexema encontrado");
                        System.out.println(lexema);
                        //arrLexemas.add(lexema);
                        indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //este es un estado de aceptacion si viene un digito seguido de uno o mas digitos
                //si viene un punto, se va a otro estado
                //aqui se puede generar un error
                case 4:
                    if (Character.isDigit(letra)) {
                        System.out.println("Vino un digito: '" + letra + "'");
                        System.out.println("Continuo en el estado 4");
                        lexema += letra;
                        estado = 4;
                    } //Es un estado de aceptacion si vienen solo digitos
                    else if (Character.isSpaceChar(letra)) {
                        System.out.println("Lexema encontrado");
                        lexema += letra;
                        System.out.println(lexema);
                        indice--;
                        lexema = "";
                        estado = 0;

                        //si viene un punto se va a otro estado
                    } else if (letra == '.') {
                        System.out.println("Vino un signo: '" + letra + "'");
                        System.out.println("Moviendose al estado 6");
                        lexema += letra;
                        estado = 6;

                        //Si viene algo diferente de un digito o un punto es un error lexico
                    } else {
                        System.out.println("Error lexico");
                        lexema += letra;
                        System.out.println("Lexema: " + lexema);
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //Si viene un digito o una letra es un estado de aceptacion, de lo contrario es un error lexico
                case 5:
                    if (Character.isLetterOrDigit(letra)) {
                        System.out.println("Vino una letra o digito");
                        System.out.println("moviendose al estado 7");
                        lexema += letra;
                        estado = 7;
                    } else {
                        System.out.println(letra);
                        System.out.println("Error lexico, caracter no valido");
                        lexema += letra;
                        System.out.println("error: " + lexema);
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //si viene un digito despues de un punto se pasa al estado 8
                //si viene un caracter diferente es un error
                case 6:
                    if (Character.isDigit(letra)) {
                        System.out.println("Vino un digito:");
                        System.out.println("moviendose al estado 8");
                        lexema += letra;
                        estado = 8;

                    } else {
                        System.out.println("Error lexico, no sigue un digito despues del punto");
                        lexema += letra;
                        System.out.println("Error: " + lexema);
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //si viene una letra o un digito seguido de una letra es un estado de aceptacion
                //si viene un caracter distinto es un error
                case 7:
                    if (Character.isLetterOrDigit(letra)) {
                        System.out.println("vino una letra o digito de nuevo");
                        System.out.println("Sigo en el estado 7");
                        lexema += letra;
                        estado = 7;
                        //se evalua el estado de aceptacion
                    } else if (Character.isSpaceChar(letra)) {
                        System.out.println("Lexema encontrado");
                        System.out.println(lexema);
                        indice--;
                        lexema = "";
                        estado = 0;
                        //error
                    } else {
                        System.out.println("Error lexico");
                        lexema += letra;
                        System.out.println("Lexema: " + lexema + " incorrecto");
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }

                    break;

                //si viene un digito o mas despues de un punto es un estado de aceptacion
                //si viene un caracter distinto a un digito es un error
                case 8:
                    if (Character.isDigit(letra)) {
                        System.out.println("vino un digito");
                        System.out.println("Sigo en el estado 8");
                        lexema += letra;
                        estado = 8;
                    } else if (Character.isSpaceChar(letra)) {
                        System.out.println("Lexema encontrado");
                        System.out.println(lexema);
                        indice--;
                        lexema = "";
                        estado = 0;

                    } else {
                        System.out.println("Error lexico");
                        lexema += letra;
                        System.out.println(lexema);
                        indice--;
                        lexema = "";
                        estado = 0;

                    }
                    break;
                default:
                    break;

            }

        }
    }

}
