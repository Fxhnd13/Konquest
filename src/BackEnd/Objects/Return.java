/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

/**
 *
 * @author jose_
 */
public class Return {
   
    private String salida, destino;
    private int naves, targetTurn;

    public Return(String salida, String destino, int naves, int targetTurn) {
        this.targetTurn = targetTurn;
        this.salida = salida;
        this.destino = destino;
        this.naves = naves;
    }

    public int getTargetTurn() {
        return targetTurn;
    }

    public void setTargetTurn(int targetTurn) {
        this.targetTurn = targetTurn;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getNaves() {
        return naves;
    }

    public void setNaves(int naves) {
        this.naves = naves;
    }
    
}
