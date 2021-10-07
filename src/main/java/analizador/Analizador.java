/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import enums.EnumToken;
import java.util.ArrayList;
import java.util.Scanner;

public class Analizador {

    String lexema; //Aqui guardaremos el lexema que se forma al analizar cada palabra
    int estado; //Guardamos los distintos estados a los que el lexema se puede mover
    int indice;// llevamos el control del indice de la palabra que estamos analizando
    String palabraLimpia;
    ArrayList<Lexema> arregloLexemas;
    ArrayList<Lexema> arregloErrores;
    Lexema lex;
    int contadorId, contadorEnteros, contadorDec, contadorSigAg, contadorOpAr, contadorSigPu;

    public void iniciarAnalizador(String palabraEntrada){
        inicializarVariables();
        palabraLimpia=getPalabraEntrada(palabraEntrada);
        analizarPalabra(palabraLimpia);
        
    }

    public void inicializarVariables() {
        lexema = "";
        estado = 0;
        indice = 0;
        palabraLimpia = "";
        arregloLexemas = new ArrayList<Lexema>();
        arregloErrores = new ArrayList<Lexema>();
        contadorId=0;
        contadorEnteros=0;
        contadorDec=0;
        contadorSigAg=0;
        contadorOpAr=0;
        contadorSigPu=0;

    }

    public String getPalabraEntrada(String palabraEntrada) {
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
                //case ' ':

                    break;
                default:
                    //guardamos nuestra palabra sin caracteres extranos
                    palabraLimpia += letra;
            }
        }
        return palabraLimpia;
        //analizarPalabra(palabraLimpia);

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
                        System.out.println("Vino un signo de puntuacion: '" + letra + "', me muevo al estado 1");
                        lexema = "" + letra;
                        estado = 1;

                        //si es operador aritmetico
                    } else if ((letra == '+') || (letra == '-') || (letra == '*') || (letra == '/') || (letra == '%')) {
                        System.out.println("Vino un operador aritmetico: '" + letra + "', me muevo al estado 2");
                        lexema = "" + letra;
                        estado = 2;

                        //signo de agrupacion
                    } else if ((letra == '(') || (letra == ')') || (letra == '[') || (letra == ']') || (letra == '{') || (letra == '}')) {
                        System.out.println("Vino un signo de agrupacion: '" + letra + "', me muevo al estado 3");
                        lexema = "" + letra;
                        estado = 3;

                        //si es digito
                    } else if (Character.isDigit(letra)) {
                        System.out.println("Vino un digito: '" + letra + "', me muevo al estado 4");
                        lexema = "" + letra;
                        estado = 4;

                        //si es letra
                    } else if (Character.isLetter(letra)) {
                        System.out.println("Vino una letra: '"+ letra +"', me muevo al estado 5");
                        lexema = "" + letra;
                        estado = 5;
                    } else {
                        lexema = "" + letra;
                        System.out.println("Encontramos un error Lexico: '" + letra+"', regreso al estado 0");
                        llenarArregloErrores(lexema);
                        estado = 0;
                        

                    }
                    break;

                //Estado de aceptacion
                case 1:
                    //en esta condicion verificamos si es un signo de puntuacion
                    //se mantiene en el mismo estado si lo es, sino se acepta el lexema
                    if ((letra == '.') || (letra == ',') || (letra == ':') || (letra == ';')) {
                        System.out.println("Vino otro signo de puntuacion: '"+letra+"', sigo en el estado 1");                   
                        lexema += letra;
                        estado = 1;

                        //primer estado de aceptacion
                    } else {
                        System.out.println("Lexema encontrado: " +lexema);
                        llenarArregloLexema(lexema, EnumToken.SIGNO_PUNTUACION);
                        contadorSigPu++;
                        indice--;
                        lexema = "";
                        estado = 0;

                    }
                    break;

                //Estado de aceptacion
                case 2:
                    if ((letra == '+') || (letra == '-') || (letra == '*') || (letra == '/') || (letra == '%')) {
                        System.out.println("Vino otro operador aritmetico: '"+letra+"', sigo en el estado 2");
                        lexema += letra;
                        estado = 2;

                        //segundo estado de aceptacion
                    } else {
                        System.out.println("Lexema encontrado: " +lexema);
                        llenarArregloLexema(lexema, EnumToken.OPERADOR_ARITMETICO);
                        contadorOpAr++;
                        indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //tercer estado de aceptacion
                case 3:
                    if ((letra == '(') || (letra == ')') || (letra == '[') || (letra == ']') || (letra == '{') || (letra == '}')) {
                        System.out.println("Vino otro signo de agrupacion: '"+letra+"', sigo en el estado 3");
                        lexema += letra;
                        estado = 3;

                    } else {
                        System.out.println("Lexema encontrado");
                        System.out.println(lexema);
                        llenarArregloLexema(lexema, EnumToken.SIGNO_AGRUPACION);
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
                        //System.out.println("Vino un digito: '" + letra + "'");
                        //System.out.println("Continuo en el estado 4");
                        lexema += letra;
                        estado = 4;
                    } //Es un estado de aceptacion si vienen solo digitos
                    else if (Character.isSpaceChar(letra)) {
                        System.out.println("Lexema encontrado");
                        lexema += letra;
                        System.out.println(lexema);
                        llenarArregloLexema(lexema, EnumToken.ENTERO);
                        indice--;
                        lexema = "";
                        estado = 0;

                        //si viene un punto se va a otro estado
                    } else if (letra == '.') {
                        //System.out.println("Vino un signo: '" + letra + "'");
                        //System.out.println("Moviendose al estado 6");
                        lexema += letra;
                        estado = 6;

                        //Si viene algo diferente de un digito o un punto es un error lexico
                    } else {
                        System.out.println("Error lexico");
                        lexema += letra;
                        System.out.println("Lexema: " + lexema);
                        llenarArregloErrores(lexema);
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //Si viene un digito o una letra es un estado de aceptacion, de lo contrario es un error lexico
                case 5:
                    if (Character.isLetterOrDigit(letra)) {
                        //System.out.println("Vino una letra o digito");
                        //System.out.println("moviendose al estado 7");
                        lexema += letra;
                        estado = 7;
                    } else {
                        System.out.println(letra);
                        System.out.println("Error lexico, caracter no valido");
                        lexema += letra;
                        System.out.println("error: " + lexema);
                        llenarArregloErrores(lexema);
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //si viene un digito despues de un punto se pasa al estado 8
                //si viene un caracter diferente es un error
                case 6:
                    if (Character.isDigit(letra)) {
                        //System.out.println("Vino un digito:");
                        //System.out.println("moviendose al estado 8");
                        lexema += letra;
                        estado = 8;

                    } else {
                        System.out.println("Error lexico, no sigue un digito despues del punto");
                        lexema += letra;
                        System.out.println("Error: " + lexema);
                        llenarArregloErrores(lexema);
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }
                    break;

                //si viene una letra o un digito seguido de una letra es un estado de aceptacion
                //si viene un caracter distinto es un error
                case 7:
                    if (Character.isLetterOrDigit(letra)) {
                        //System.out.println("vino una letra o digito de nuevo");
                        //System.out.println("Sigo en el estado 7");
                        lexema += letra;
                        estado = 7;
                        //se evalua el estado de aceptacion
                    } else if (Character.isSpaceChar(letra)) {
                        System.out.println("Lexema encontrado");
                        System.out.println(lexema);
                        llenarArregloLexema(lexema, EnumToken.IDENTIFICADOR);
                        indice--;
                        lexema = "";
                        estado = 0;
                        //error
                    } else {
                        System.out.println("Error lexico");
                        lexema += letra;
                        System.out.println("Lexema: " + lexema + " incorrecto");
                        llenarArregloErrores(lexema);
                        //indice--;
                        lexema = "";
                        estado = 0;
                    }

                    break;

                //si viene un digito o mas despues de un punto es un estado de aceptacion
                //si viene un caracter distinto a un digito es un error
                case 8:
                    if (Character.isDigit(letra)) {
                        //System.out.println("vino un digito");
                        //System.out.println("Sigo en el estado 8");
                        lexema += letra;
                        estado = 8;
                    } else if (Character.isSpaceChar(letra)) {
                        System.out.println("Lexema encontrado");

                        llenarArregloLexema(lexema, EnumToken.DECIMAL);

                        indice--;
                        lexema = "";
                        estado = 0;

                    } else {
                        System.out.println("Error lexico");
                        lexema += letra;
                        //System.out.println(lexema);
                        llenarArregloErrores(lexema);
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

    public void llenarArregloLexema(String lexema, EnumToken tipoToken) {
        lex = new Lexema(lexema, tipoToken);
        arregloLexemas.add(lex);

    }

    public void llenarArregloErrores(String cadenaError) {
        lex = new Lexema(cadenaError);
        arregloErrores.add(lex);

    }

    public ArrayList<Lexema> getArregloLexemas() {
        return arregloLexemas;
    }

    public void setArregloLexemas(ArrayList<Lexema> arregloLexemas) {
        this.arregloLexemas = arregloLexemas;
    }



    public ArrayList<Lexema> getArregloErrores() {
        return arregloErrores;
    }

    public void setArregloErrores(ArrayList<Lexema> arregloErrores) {
        this.arregloErrores = arregloErrores;
    }

    
    
}
