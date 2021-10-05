package controladores;

import analizador.Analizador;
import analizador.Lexema;
import java.util.ArrayList;

public class ControladorLexema {

    Analizador analizador;
    ArrayList<Lexema> arregloLexemas;
    ArrayList<Lexema> arregloErrores;

    public ControladorLexema() {
        analizador = new Analizador();
    }


    
    public static void main(String[] args) {
        
        ControladorLexema contrLexema = new ControladorLexema();
        String palabraEntrada = "hola afds45 jeej amonos 15f3.14";
        palabraEntrada += " ";
        contrLexema.iniciar(palabraEntrada);
       
    }
    
    public void iniciar(String palabraEntrada){
        analizador.iniciarAnalizador(palabraEntrada);
        mostrarLexemas(analizador.getArregloLexemas());
        mostrarErrores(analizador.getArregloErrores());
    }
    
    public void mostrarLexemas(ArrayList<Lexema> arregloLexemas) {
        System.out.println("El tamanio es: " +arregloLexemas.size());
        //System.out.println(arregloLexemas.get(0).getLexema());
        String nombre = "";
        String tipoToken = "";
        String error = "";
        System.out.println("aca vamos a mostrar el resultado");
        
        for (int i = 0; i <arregloLexemas.size(); i++) {
            nombre = arregloLexemas.get(i).getLexema()+"\n";
            tipoToken = arregloLexemas.get(i).getNombreToken()+"\n";
            System.out.println("Nombre: " +nombre+ "Tipo token: " +tipoToken+"Posicion: "+(i+1));
        }


    }
    
    public void mostrarErrores(ArrayList<Lexema> arregloErrores){
        String nombre="";
        for (int i = 0; i <arregloErrores.size(); i++) {
            nombre= arregloErrores.get(i).getCadenaError()+"\n";
            System.out.println("Cadena de error: " +nombre+"Posicion: " +(i+1));
        }
        
    }

}
