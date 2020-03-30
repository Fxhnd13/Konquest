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
public class Atributo {
    
    private String tipo, valor = null;
    
    public Atributo(String tipo, String valor){
        this.tipo = tipo;
        this.valor = valor; 
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
