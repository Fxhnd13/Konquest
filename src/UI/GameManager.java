/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BackEnd.Configuration.ConfigurationNeutrales;
import BackEnd.Configuration.GameConfiguration;
import BackEnd.Objects.Action;
import BackEnd.Objects.Atack;
import BackEnd.Objects.Map;
import BackEnd.Objects.Planet;
import BackEnd.Objects.Player;
import BackEnd.Objects.Return;
import BackEnd.Utilities.GameUtilities;
import BackEnd.Utilities.Utilities;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose_
 */
public class GameManager {
    
    private GameConfiguration configuration = new GameConfiguration(); 
    private boolean alAzar = false, victoria = false;
    private int turno = 1, jugadorEnTurno=0, state = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private ArrayList<Atack> atacks = new ArrayList<Atack>();
    private ArrayList<Return> returns = new ArrayList<Return>();
    private ArrayList<Action> actions = new ArrayList<Action>();
    private ArrayList<String> mensajes = new ArrayList<String>();
    private Cell exitCell = null, destinyCell= null;
    
    public void cambiarJugador(JTextArea bitacora, JPanel spacePanel){
        actions.add(new Action(turno+1,0,null, players.get(jugadorEnTurno).getName()));
        jugadorEnTurno++;
        if(jugadorEnTurno==players.size()){
            jugadorEnTurno=0;
            terminarTurno(bitacora, spacePanel);
        }
    }
    
    public void terminarTurno(JTextArea bitacora, JPanel spacePanel){
        turno++;
        for (Atack atack : atacks) {
            if(atack.getTargetTurn()==turno){
                realizarAtaque(atack, bitacora);
                repintarMapa(spacePanel);
            }
        }
        for (Return aReturn : returns) {
            if(aReturn.getTargetTurn()==turno){
                realizarLLegada(aReturn);
                bitacora.setText(bitacora.getText()+"\nHan regresado las tropas al planeta "+aReturn.getDestino()+" del ataque realizado al planeta "+aReturn.getSalida());
            }
        }
        for (Planet planet : planets) {
            planet.setShips(planet.getShips()+planet.getProduction());
            if(this.configuration.getMap().isAcumular()){
                planet.setProduction(planet.getProduction()+1);
            }
        }
        for (String mensaje : mensajes) {
            bitacora.setText(bitacora.getText()+"\n"+mensaje);
        }
        mensajes.clear();
        verificarVictoria();
    }
    
    public void verificarVictoria(){
        if(configuration.getMap().getFinalization()==-1){
            boolean valor = true;
            for (int i = 0; i < players.size(); i++) {
                valor = true;
                for (Planet planet : planets) {
                    if(!players.get(i).getName().equals(planet.getConqueror())){
                        valor=false;
                        break;
                    }
                }
                if(valor)break;
            }
            if(valor){
                victoria = true;
            }
        }else{
            if(configuration.getMap().getFinalization()==turno){
                victoria = true;
            }
        }
    }
    
    public void realizarLLegada(Return a){
        Planet destino = Utilities.getPlanetaPorNombre(a.getDestino(), planets);
        destino.setShips(destino.getShips()+a.getNaves());
    }
    
    public void realizarAtaque(Atack atack, JTextArea bitacora){
        Planet destino = Utilities.getPlanetaPorNombre(atack.getNameDestinyPlanet(), planets);
        Planet salida = Utilities.getPlanetaPorNombre(atack.getNameExitPlanet(), planets);
        int cantidadQueEliminaElAtacante = (int) (atack.getShips() * atack.getPorcentajeDeMuerte());
        int cantidadQueEliminaElDefensor = (int) (destino.getShips()*destino.getDeathPercentage());
        int cantidadSobranteAtacante = atack.getShips()-cantidadQueEliminaElDefensor;
        int cantidadSobranteDefensor = destino.getShips() - cantidadQueEliminaElAtacante;
        if(!salida.getConqueror().equals(destino.getConqueror())){
            if(cantidadSobranteDefensor>cantidadSobranteAtacante){
                if(cantidadSobranteAtacante>0){
                    Return regreso = new Return(destino.getName(), salida.getName(), cantidadSobranteAtacante, turno+GameUtilities.calcularDistancia(destino, salida));
                    returns.add(regreso);
                }
                destino.setShips(cantidadSobranteDefensor);
                bitacora.setText(bitacora.getText()+"\nTurno "+(turno+1)+": El planeta "+destino.getName()+" se ha defendido del ataque del conquistador "+salida.getConqueror());
            }else if(cantidadSobranteDefensor<cantidadSobranteAtacante){
                destino.setConqueror(salida.getConqueror());
                destino.setShips(cantidadSobranteDefensor+cantidadSobranteAtacante);
                bitacora.setText(bitacora.getText()+"\nTurno "+(turno+1)+": El planeta "+destino.getName()+" ha caído ante el conquistador "+salida.getConqueror());
            }else{
                bitacora.setText(bitacora.getText()+"\nTurno "+(turno+1)+": Ha habido un empate, el planeta se ha defendido pero quedado sin naves.");
            }
        }else{
            destino.setShips(destino.getShips()+atack.getShips());
            bitacora.setText(bitacora.getText()+"\nTurno "+(turno+1)+": Han llegado los refuerzos del planeta "+salida.getName()+" al planeta "+destino.getName());
        }
    }
    
    public void confirmAtack(JLabel label, JButton buton, JTextArea bitacora){
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántas naves desea enviar? (Disponibles: "+Utilities.planetAt(exitCell, planets).getShips()+")"));
        if(cantidad <= Utilities.planetAt(exitCell, planets).getShips()){
            Atack atack = new Atack(Utilities.planetAt(exitCell, planets).getName(), Utilities.planetAt(destinyCell, planets).getName(),
                                        cantidad, turno+1, 1+turno+GameUtilities.calcularDistancia(exitCell, destinyCell), 
                                        Utilities.planetAt(exitCell, planets).getDeathPercentage());
            int confirmacion = 0;
            if(Utilities.planetAt(exitCell, planets).getConqueror().equals(Utilities.planetAt(destinyCell, planets).getConqueror())){
                confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea enviar refuerzos de "+Utilities.planetAt(exitCell, planets).getName()+" hacia "+Utilities.planetAt(destinyCell, planets).getName()+" con "+atack.getShips()+" naves?");
                mensajes.add("Turno "+(turno+1)+": El conquistador "+Utilities.planetAt(exitCell, planets).getConqueror()+" ha enviado refuerzos del planeta "+Utilities.planetAt(exitCell, planets).getName()+" hacia el planeta "+Utilities.planetAt(destinyCell, planets).getName()+" que llegará en el turno "+atack.getTargetTurn());
            }else{
                confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea enviar un ataque de "+Utilities.planetAt(exitCell, planets).getName()+" hacia "+Utilities.planetAt(destinyCell, planets).getName()+" con una cantidad de "+cantidad+" naves?"
                        + "\nLlegará en el turno: "+atack.getTargetTurn());
                mensajes.add("Turno "+(turno+1)+": El conquistador "+Utilities.planetAt(exitCell, planets).getConqueror()+" ha enviado un ataque del planeta "+Utilities.planetAt(exitCell, planets).getName()+" hacia el planeta "+Utilities.planetAt(destinyCell, planets).getName()+" que llegará en el turno "+atack.getTargetTurn());
            }
            if(confirmacion == JOptionPane.OK_OPTION){
                Utilities.planetAt(exitCell, planets).setShips(Utilities.planetAt(exitCell, planets).getShips()-cantidad);
                actions.add(new Action(turno+1,1,atack, players.get(jugadorEnTurno).getName()));
                atacks.add(atack);
                cancelAtack(label, buton);
            }else{
                cancelAtack(label, buton);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No posee la cantidad de naves que desea enviar.", "Error", JOptionPane.ERROR_MESSAGE);
            cancelAtack(label, buton);
        }
    }
    
    public void cancelAtack(JLabel label, JButton buton){
        this.state = 0;
        this.exitCell = null;
        this.destinyCell = null;
        label.setText("Planeando...");
        buton.setText("Atacar");
    }
    
    public void doPlayers(JTable tablePlayers){
        GameUtilities.doPlayers(tablePlayers, players, configuration);
    }
    
    public void doPlanets(JTable tablePlanets, int filas, int columnas){
        GameUtilities.doPlanets(tablePlanets, filas, columnas, planets, configuration);
    }

    void doPlanets(int cantidad, int filas, int columnas) {
        GameUtilities.doPlanets(cantidad, filas, columnas, players, planets, configuration);
    }

    public void reset(JPanel spacePanel, JTextArea bitacora){
        spacePanel.removeAll();
        bitacora.setText("");
        this.configuration = new GameConfiguration();
        this.planets.clear();
        this.players.clear();
        this.actions.clear();
        this.returns.clear();
        this.atacks.clear();
    }
    
    public void repintarMapa(JPanel spacePanel){
        for (int i = 0; i < spacePanel.getComponentCount(); i++) {
            Cell cell = (Cell) spacePanel.getComponent(i);
            if(Utilities.planetAt(cell, planets)!=null){
                for (Player player : players) {
                    if(Utilities.planetAt(cell, planets).getConqueror().equals(player.getName())){
                        UIManager.addBorder(cell, new LineBorder(Utilities.getColor(player.getColorPlayer())));
                    }
                }
            }
        }
    }

    public boolean isVictoria() {
        return victoria;
    }

    public void setVictoria(boolean victoria) {
        this.victoria = victoria;
    }
    
    public int getState(){
        return state;
    }
    
    public void setState(int state){
        this.state = state;
    }
    
    public Cell getExitCell() {
        return exitCell;
    }

    public void setExitCell(Cell exitCell) {
        this.exitCell = exitCell;
    }

    public Cell getDestinyCell() {
        return destinyCell;
    }

    public void setDestinyCell(Cell destinyCell) {
        this.destinyCell = destinyCell;
    }

    public GameConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(GameConfiguration configuration) {
        this.configuration = configuration;
    }

    public boolean isAlAzar() {
        return alAzar;
    }

    public void setAlAzar(boolean alAzar) {
        this.alAzar = alAzar;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int Turno) {
        this.turno = Turno;
    }

    public int getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    public void setIndiceJugador(int indiceJugador) {
        this.jugadorEnTurno = indiceJugador;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public ArrayList<Planet> getPlanets() {
        return planets;
    }
}
