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
import BackEnd.Utilities.Utilities;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose_
 */
public class GameManager {
    
    private GameConfiguration configuration = new GameConfiguration(); 
    
    
    
    public void doPlayers(JTable tablePlayers){
        DefaultTableModel modelTablePlayers = (DefaultTableModel) tablePlayers.getModel();//obtenemos los jugadores que se desea tener
        for (int i = 0; i < modelTablePlayers.getRowCount(); i++) {//por cada fila
            //accedemos a la informacion de cada jugador 
            Player player = new Player(modelTablePlayers.getValueAt(i, 0).toString(), (Integer) modelTablePlayers.getValueAt(i, 1));
            this.configuration.getPlayers().add(player);//agregamos al jugador al juego
        }
    }
    
    public void doPlanets(JTable tablePlanets, int filas, int columnas){
        DefaultTableModel modelTablePlanets = (DefaultTableModel) tablePlanets.getModel();
        int[] posiciones = Utilities.numerosAleatoriosEntre(modelTablePlanets.getRowCount(), filas*columnas);
        for (int i = 0; i < modelTablePlanets.getRowCount(); i++) {
            Planet planet = new Planet();
            planet.setName(modelTablePlanets.getValueAt(i, 0).toString());
            planet.setNaves((Integer) modelTablePlanets.getValueAt(i, 1));
            planet.setProduction((Integer) modelTablePlanets.getValueAt(i, 2));
            planet.setDeathPercentage((Double) modelTablePlanets.getValueAt(i,3));
            planet.setConqueror(modelTablePlanets.getValueAt(i, 4).toString());
            planet.setPositionX(posicionXY(posiciones[i])[0]);
            planet.setPositionY(posicionXY(posiciones[i])[1]);
            this.configuration.getPlanets().add(planet);
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
