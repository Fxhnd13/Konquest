/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

import BackEnd.Configuration.ConfigurationNeutrales;

/**
 *
 * @author jose_
 */
public class Map {
    
    private String id; 
    private int filas, columnas, planetasNeutrales;
    private boolean alAzar = false, mapaCiego = false, acumular = false;
    private ConfigurationNeutrales neutrales = new ConfigurationNeutrales();
            
    public Map(){}

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

    public boolean isAlAzar() {
        return alAzar;
    }

    public void setAlAzar(boolean alAzar) {
        this.alAzar = alAzar;
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
