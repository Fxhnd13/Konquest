/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Configuration;

/**
 *
 * @author jose_
 */
public class ConfigurationNeutrales {
    
    private boolean showShips, showStadistics;
    private int production;

    public boolean isShowShips() {
        return showShips;
    }

    public void setShowShips(boolean showShips) {
        this.showShips = showShips;
    }

    public boolean isShowStadistics() {
        return showStadistics;
    }

    public void setShowStadistics(boolean showStadistics) {
        this.showStadistics = showStadistics;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }
    
}
