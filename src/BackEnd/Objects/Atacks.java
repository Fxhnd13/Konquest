/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

import java.util.ArrayList;

/**
 *
 * @author jose_
 */
public class Atacks {
    
    private ArrayList<Atack> ataquesRealizados = new ArrayList<Atack>();
    private ArrayList<Atack> ataquesActivos = new ArrayList<Atack>();

    public ArrayList<Atack> getAtaquesRealizados() {
        return ataquesRealizados;
    }

    public void setAtaquesRealizados(ArrayList<Atack> ataquesRealizados) {
        this.ataquesRealizados = ataquesRealizados;
    }

    public ArrayList<Atack> getAtaquesActivos() {
        return ataquesActivos;
    }

    public void setAtaquesActivos(ArrayList<Atack> ataquesActivos) {
        this.ataquesActivos = ataquesActivos;
    }
    
}
