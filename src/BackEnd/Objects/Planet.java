/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

import Analizadores.Objects.Token;
import java.awt.Component;
import javax.accessibility.AccessibleContext;
import javax.swing.JLabel;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author jose_
 */
public class Planet{
    
    private int ships=-1, production=-1, positionX, positionY; //alto y ancho respectivamente
    private String name=null, conqueror;
    private double deathPercentage=-1;
    private Token firstToken;

    public Planet(String name, String ships, String production, String Deathless, String Conquer) {
        this.name = name;
        this.ships = Integer.parseInt(ships);
        this.production = Integer.parseInt(production);
        this.deathPercentage = Double.parseDouble(Deathless);
        this.conqueror = Conquer;
    }

    public Planet() {}

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
    public String getConqueror(){
        return conqueror;
    }
    
    public void setConqueror(String conqueror){
        this.conqueror = conqueror;
    }
    
    public int getShips() {
        return ships;
    }

    public void setShips(int ships) {
        this.ships = ships;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDeathPercentage() {
        return deathPercentage;
    }

    public void setDeathPercentage(double deathPercentage) {
        this.deathPercentage = deathPercentage;
    }

    public Token getFirstToken() {
        return firstToken;
    }

    public void setFirstToken(Token firstToken) {
        this.firstToken = firstToken;
    }
    
    public boolean isValid(){
        if(this.name!=null&&this.ships!=-1&&this.deathPercentage!=-1){
            return true;
        }else{
            return false;
        }
    }
}
