/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizadores.Objects;

/**
 *
 * @author jose_
 */
public class ErrorMessage {
    
        private int linea, columna;
        private String cadena, causa;

    public ErrorMessage(int linea, int columna, String cadena, String causa) {
        this.linea = linea;
        this.columna = columna;
        this.cadena = cadena;
        this.causa = causa;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }
    
}
