/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

import Analizadores.Objects.Token;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author jose_
 */
public class Player {
    
    private String name=null, color, type=null;
    private int navesCreadas=0, navesDestruidas=0, planetasConquistados=0, ataquesRealizados=0;
    private ArrayList<String> planetas = new ArrayList<String>();
    private Token firsToken;
    
    public Player(String name, String color, String type){
        this.name = name;
        this.color = color;
        this.type = type;
    }

    public Player() {}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNavesCreadas() {
        return navesCreadas;
    }

    public void setNavesCreadas(int navesCreadas) {
        this.navesCreadas = navesCreadas;
    }

    public int getNavesDestruidas() {
        return navesDestruidas;
    }

    public void setNavesDestruidas(int navesDestruidas) {
        this.navesDestruidas = navesDestruidas;
    }

    public int getPlanetasConquistados() {
        return planetasConquistados;
    }

    public void setPlanetasConquistados(int planetasConquistados) {
        this.planetasConquistados = planetasConquistados;
    }

    public int getAtaquesRealizados() {
        return ataquesRealizados;
    }

    public void setAtaquesRealizados(int ataquesRealizados) {
        this.ataquesRealizados = ataquesRealizados;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getColorPlayer() {
        return color;
    }

    public void setColorPlayer(String colorPlayer) {
        this.color = colorPlayer;
    }

    public ArrayList<String> getPlanetas() {
        return planetas;
    }

    public void setPlanetas(ArrayList<String> planetas) {
        this.planetas = planetas;
    }

    public Token getFirsToken() {
        return firsToken;
    }

    public void setFirsToken(Token firsToken) {
        this.firsToken = firsToken;
    }
    
    public boolean isValid(){
        if(this.name!=null&&this.type!=null&&!this.planetas.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    public void aumentarPlanetasConquistados(){
        this.planetasConquistados++;
    }
    
    public void disminuirPlanetasConquistados(){
        this.planetasConquistados++;
    }
}
