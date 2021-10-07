/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import enums.EnumToken;

/**
 *
 * @author randysmc
 */
public class Lexema {
    String cadenaError;
    String lexema;
    EnumToken nombreToken;
    int posicion;
    int cantidadApariciones;

    public Lexema(String lexema, EnumToken nombreToken, int cantidadApariciones) {
        this.lexema = lexema;
        this.nombreToken = nombreToken;
        this.cantidadApariciones=cantidadApariciones;
        //this.posicion = posicion;
    }

    public Lexema(String cadenaError) {
        this.cadenaError = cadenaError;
        //this.posicion = posicion;
    }

    public String getCadenaError() {
        return cadenaError;
    }

    public void setCadenaError(String cadenaError) {
        this.cadenaError = cadenaError;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public EnumToken getNombreToken() {
        return nombreToken;
    }

    public void setNombreToken(EnumToken nombreToken) {
        this.nombreToken = nombreToken;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getCantidadApariciones() {
        return cantidadApariciones;
    }

    public void setCantidadApariciones(int cantidadApariciones) {
        this.cantidadApariciones = cantidadApariciones;
    }
    
    
 
}
