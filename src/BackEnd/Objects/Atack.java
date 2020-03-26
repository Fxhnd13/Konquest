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
public class Atack {
    
    private String nameExitPlanet, nameDestinyPlanet;
    private boolean victoria = false;
    private double porcentajeDeMuerte;
    private int ships, exitTurn, targetTurn, navesAliadasEliminadas, navesEnemigasEliminadas;

    public Atack(String exitPlanet, String destinyPlanet, int ships, int exitTurn, int targetTurn, double porcentaje){
        this.nameExitPlanet = exitPlanet;
        this.nameDestinyPlanet = destinyPlanet;
        this.ships = ships;
        this.exitTurn = exitTurn;
        this.targetTurn = targetTurn;
        this.porcentajeDeMuerte = porcentaje;
    }

    public double getPorcentajeDeMuerte() {
        return porcentajeDeMuerte;
    }

    public void setPorcentajeDeMuerte(double porcentajeDeMuerte) {
        this.porcentajeDeMuerte = porcentajeDeMuerte;
    }
    
    public String getNameExitPlanet() {
        return nameExitPlanet;
    }

    public void setNameExitPlanet(String nameExitPlanet) {
        this.nameExitPlanet = nameExitPlanet;
    }

    public String getNameDestinyPlanet() {
        return nameDestinyPlanet;
    }

    public void setNameDestinyPlanet(String nameDestinyPlanet) {
        this.nameDestinyPlanet = nameDestinyPlanet;
    }

    public int getShips() {
        return ships;
    }

    public void setShips(int ships) {
        this.ships = ships;
    }

    public int getExitTurn() {
        return exitTurn;
    }

    public void setExitTurn(int exitTurn) {
        this.exitTurn = exitTurn;
    }

    public int getTargetTurn() {
        return targetTurn;
    }

    public void setTargetTurn(int targetTurn) {
        this.targetTurn = targetTurn;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public void setVictoria(boolean victoria) {
        this.victoria = victoria;
    }

    public int getNavesAliadasEliminadas() {
        return navesAliadasEliminadas;
    }

    public void setNavesAliadasEliminadas(int navesAliadasEliminadas) {
        this.navesAliadasEliminadas = navesAliadasEliminadas;
    }

    public int getNavesEnemigasEliminadas() {
        return navesEnemigasEliminadas;
    }

    public void setNavesEnemigasEliminadas(int navesEnemigasEliminadas) {
        this.navesEnemigasEliminadas = navesEnemigasEliminadas;
    }
    
}
