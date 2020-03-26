/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

import BackEnd.Configuration.ConfigurationNeutrales;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author jose_
 */
public class Map {
    
    private String id = ""; 
    private int filas=-1, columnas=-1, planetasNeutrales=-1, finalization=-1;
    private boolean mapaCiego = false, acumular = false;
    private ConfigurationNeutrales neutrales = new ConfigurationNeutrales();
            
    public Map(String id, String row, String columns, JCheckBox blindMap, JCheckBox acum, String finalization, JCheckBox showShips, JCheckBox stadistics, String amount){
        if(id.isEmpty()||row.isEmpty()||columns.isEmpty()||amount.isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos necesarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            this.id = id;
            this.filas = Integer.parseInt(row);
            this.columnas = Integer.parseInt(columns);
            this.mapaCiego = blindMap.isSelected();
            this.acumular = acum.isSelected();
            if(!finalization.isEmpty())this.finalization = Integer.parseInt(finalization);
            this.neutrales.setShowShips(showShips.isSelected());
            this.neutrales.setShowStadistics(stadistics.isSelected());
            this.neutrales.setProduction(columnas);
        }
    }
    
    public Map(String id, String row, String columns, JCheckBox blindMap, JCheckBox acum, String finalization, JCheckBox showShips, JCheckBox stadistics){
        if(id.isEmpty()||row.isEmpty()||columns.isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos necesarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            this.id = id;
            this.filas = Integer.parseInt(row);
            this.columnas = Integer.parseInt(columns);
            this.mapaCiego = blindMap.isSelected();
            this.acumular = acum.isSelected();
            if(!finalization.isEmpty())this.finalization = Integer.parseInt(finalization);
            this.neutrales.setShowShips(showShips.isSelected());
            this.neutrales.setShowStadistics(stadistics.isSelected());
            this.neutrales.setProduction(columnas);
        }
    }
    
    public boolean isValid(){
        boolean valor = true;
        if(id.isEmpty()||filas==-1||columnas==-1){
            valor = false;
        }
        return valor;
    }
    
    public Map(){}

    public int getFinalization() {
        return finalization;
    }

    public void setFinalization(int finalization) {
        this.finalization = finalization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int getPlanetasNeutrales() {
        return planetasNeutrales;
    }

    public void setPlanetasNeutrales(int planetasNeutrales) {
        this.planetasNeutrales = planetasNeutrales;
    }

    public boolean isMapaCiego() {
        return mapaCiego;
    }

    public void setMapaCiego(boolean mapaCiego) {
        this.mapaCiego = mapaCiego;
    }

    public boolean isAcumular() {
        return acumular;
    }

    public void setAcumular(boolean acumular) {
        this.acumular = acumular;
    }

    public ConfigurationNeutrales getNeutrales() {
        return neutrales;
    }

    public void setNeutrales(ConfigurationNeutrales neutrales) {
        this.neutrales = neutrales;
    }
    
}
