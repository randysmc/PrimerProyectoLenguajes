/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import analizador.Analizador;
import analizador.Lexema;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import ventana.VentanaPrincipal;


public class ControladorVentana {
    ControladorLexema contrLexema;
    private String textoEntrada;
    private JTextArea textArArchivo;

  

    public ControladorVentana() {
        contrLexema = new ControladorLexema();
    }
    
    
    public void buscarPatron(String textoBusqueda){
        contrLexema.getPatron(textoBusqueda);
    }
      
    public void obtenerTablaLex(JTable tabla){
        contrLexema.getTablaLexemas(tabla);

    }
    
    public void obtenerTablaErr(JTable tablaE){
        contrLexema.getTablaErr(tablaE);
    }
    
    public void analizarArchivo(String textoAnalizar){
        contrLexema.iniciar(textoAnalizar);
        
    }
    
    
    public String leerArhivo(){
     JFileChooser fc = new JFileChooser(); //Ventana donde podremos escoger nuestro archivo
     fc.showOpenDialog(null);//abre el cuadro de dialogo con un campo nulo
     File archivo = fc.getSelectedFile();//file para seleccionar
     String nombre = archivo.getParent();
        System.out.println(nombre);
     
        //System.out.println(archivos);  
        try {
            FileReader fr = new FileReader(archivo); //al filereader le pasamos el parametro var del archivo
            BufferedReader br = new BufferedReader(fr);//reservamos memoria para escribir
            String texto=""; //var para guardar el texto
            String linea="";
            
            while ((linea=br.readLine()) != null ) {
                texto+=linea+"\n"; 
                
            }
            textoEntrada = texto+" ";
            JOptionPane.showMessageDialog(null,"Archivo leido correctamente");
            //System.out.println(textoEntrada);
            
        } catch (Exception e) {
            
        } 
        return textoEntrada;
        
    }

    
 
}
