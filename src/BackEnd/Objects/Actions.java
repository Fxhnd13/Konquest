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
public class Actions {
    
    private ArrayList<Action> accionesARealizar = new ArrayList<Action>();
    private ArrayList<Action> accionesRealizadas = new ArrayList<Action>();

    public ArrayList<Action> getAccionesARealizar() {
        return accionesARealizar;
    }

    public void setAccionesARealizar(ArrayList<Action> accionesARealizar) {
        this.accionesARealizar = accionesARealizar;
    }

    public ArrayList<Action> getAccionesRealizadas() {
        return accionesRealizadas;
    }

    public void setAccionesRealizadas(ArrayList<Action> accionesRealizadas) {
        this.accionesRealizadas = accionesRealizadas;
    }
    
}
