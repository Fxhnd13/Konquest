/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BackEnd.Configuration.ConfigurationNeutrales;
import BackEnd.Configuration.GameConfiguration;
import BackEnd.Objects.Map;
import BackEnd.Objects.Planet;
import BackEnd.Objects.Player;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose_
 */
public class GameManager {
    
    private GameConfiguration configuration; 
    
    public JPanel doMap(Map map){
        JPanel SpacePanel = new JPanel(new GridLayout(map.getFilas(), map.getColumnas()));
        for (int i = 0; i < map.getFilas(); i++) {
            for (int j = 0; j < map.getColumnas(); j++) {
                Cell cell = new Cell();
                cell.setPosicionX(i);//configuramos la posicion en x
                cell.setPosicionY(j);//configuramos la posicion en y
                
            }
        }
        this.configuration.setMap(map);
        return SpacePanel;
    }
    
    public void configurationOfUI(JTable tablePlayers, JTable tablePlanets){
        DefaultTableModel modelTablePlayers = (DefaultTableModel) tablePlayers.getModel();
        DefaultTableModel modelTablePlanets = (DefaultTableModel) tablePlanets.getModel();
        for (int i = 0; i < modelTablePlayers.getRowCount(); i++) {
            Player player = new Player(modelTablePlayers.getValueAt(i, 0).toString(), (Integer) modelTablePlayers.getValueAt(i, 1));
            this.configuration.getPlayers().add(player);
        }
        for (int i = 0; i < modelTablePlanets.getRowCount(); i++) {
            Planet planet = new Planet();
            planet.setName(modelTablePlanets.getValueAt(i, 0).toString());
            planet.setNaves((Integer) modelTablePlanets.getValueAt(i, 1));
            planet.setProduction((Integer) modelTablePlanets.getValueAt(i, 2));
            planet.setDeathPercentage((Double) modelTablePlanets.getValueAt(i,3));
            planet.setConqueror(modelTablePlanets.getValueAt(i, 4).toString());
            this.configuration.getPlanets().add(planet);
            //planet.setImagePath();
            //planet.setColorPlayer();
        }
    }
    
    public int[] posicionXY(int valor){
        int[] posicion = new int[2];
        for (int i = 1; i < configuration.getMap().getFilas(); i++) {
            if((configuration.getMap().getColumnas()*i<valor)&&(configuration.getMap().getColumnas()*(i+1)>=valor)){
                posicion[0] = i;
                posicion[1] = (configuration.getMap().getColumnas()*i) - valor;
            }
        }
        return posicion;
    }
}
