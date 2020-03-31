/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Configuration;

import BackEnd.Objects.Map;
import BackEnd.Objects.Planet;
import BackEnd.Objects.Player;
import static BackEnd.Utilities.GameUtilities.posicionXY;
import BackEnd.Utilities.Utilities;
import java.util.ArrayList;

/**
 *
 * @author jose_
 */
public class GameConfiguration {
    
    private Map map = new Map();
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private ArrayList<Player> players = new ArrayList<Player>();

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void doLastConfigurations() {
        int color=0;
        for (Player player : players) {
            for (String planeta : player.getPlanetas()) {
                Utilities.getPlanetaPorNombre(planeta, planets).setConqueror(player.getName());
            }
            String colorReal = Utilities.getColorByNumber(color);
            player.setColor(colorReal);
            color = (color<5)? color+1:0;
        }
        for (int i = 0; i<planets.size(); i++) {
            int[] posiciones = Utilities.numerosAleatoriosEntre(planets.size(), (map.getFilas()*map.getColumnas())-1);
            if(planets.get(i).getProduction()==-1){
                planets.get(i).setProduction(this.map.getNeutrales().getProduction());
            }
            if(planets.get(i).getConqueror()==null){
                planets.get(i).setConqueror("Nadie");
            }else{
                Utilities.getPlayer(planets.get(i), players).setNavesCreadas(planets.get(i).getShips());
                Utilities.getPlayer(planets.get(i), players).setPlanetasConquistados(Utilities.getPlayer(planets.get(i), players).getPlanetasConquistados()+1);
            }
            planets.get(i).setPositionX(posicionXY(posiciones[i], map.getFilas(), map.getColumnas())[0]);
            planets.get(i).setPositionY(posicionXY(posiciones[i], map.getFilas(), map.getColumnas())[1]);
        }
    }
    
    public void doLastConfigurationsRp(){
        int color=0;
        for (Player player : players) {
            String colorReal = Utilities.getColorByNumber(color);
            player.setColor(colorReal);
            color = (color<5)? color+1:0;
        }
    }
}
