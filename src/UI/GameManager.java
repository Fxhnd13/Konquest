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
import java.awt.GridLayout;
import javax.swing.ImageIcon;
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
        JPanel SpacePanel = new JPanel(new GridLayout(map.getFilas(), map.getColumnas()));//hacemos las columans y las filas
        int iTemp = 1;
        for (int i = 0; i < map.getFilas(); i++) {//por cada fila
            for (int j = 0; j < map.getColumnas(); j++) { //por cada columna
                Cell cell = new Cell();//agregamos una nueva celda
                cell.setPosicionX(i);//configuramos la posicion en x
                cell.setPosicionY(j);//configuramos la posicion en y
                cell.setPlanet(Utilities.planetAt(i,j, configuration.getPlanets()));//asignamos el planeta a la celda (de ser nulo no se dibuja planeta)
                if(cell.getPlanet()==null){
                    cell.setIcon(new ImageIcon(getClass().getResource("/Images/0.png")));
                }else{
                    if(iTemp==3||iTemp==5||iTemp==7||iTemp==8){
                        cell.setIcon(new ImageIcon(getClass().getResource("/Images/"+iTemp+".jpg")));
                    }else{
                        cell.setIcon(new ImageIcon(getClass().getResource("Images/"+iTemp+".png")));
                    }
                }
                iTemp = (iTemp==10)?1:(iTemp+1); 
            }
        }
        this.configuration.setMap(map);
        return SpacePanel;
    }
    
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
