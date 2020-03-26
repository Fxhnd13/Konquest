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
        jugadorEnTurno++;
        actions.add(new Action(turno,0,null));
        if(jugadorEnTurno==players.size()){
            jugadorEnTurno=0;
            terminarTurno(bitacora, spacePanel);
        }
    }
    
    public void terminarTurno(JTextArea bitacora, JPanel spacePanel){
        turno++;
        actions.add(new Action(turno,0, null));
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
            int indiceGanador = 0;
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
    
    public int planetasConquistadosPor(String nombre){
        int cantidad = 0;
        for (Planet planet : planets) {
            if(planet.getConqueror().equals(nombre))cantidad++;
        }
        return cantidad;
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
                    Return regreso = new Return(destino.getName(), salida.getName(), cantidadSobranteAtacante, turno+Utilities.calcularDistancia(destino, salida));
                    returns.add(regreso);
                }
                destino.setShips(cantidadSobranteDefensor);
                bitacora.setText(bitacora.getText()+"\nTurno "+turno+": El planeta "+destino.getName()+" se ha defendido del ataque del conquistador "+salida.getConqueror());
            }else if(cantidadSobranteDefensor<cantidadSobranteAtacante){
                destino.setConqueror(salida.getConqueror());
                destino.setShips(cantidadSobranteDefensor+cantidadSobranteAtacante);
                bitacora.setText(bitacora.getText()+"\nTurno "+turno+": El planeta "+destino.getName()+" ha caído ante el conquistador "+salida.getConqueror());
            }else{
                bitacora.setText(bitacora.getText()+"\nTurno "+turno+": Ha habido un empate, el planeta se ha defendido pero quedado sin naves.");
            }
        }else{
            destino.setShips(destino.getShips()+atack.getShips());
            bitacora.setText(bitacora.getText()+"\nTurno "+turno+": Han llegado los refuerzos del planeta "+salida.getName()+" al planeta "+destino.getName());
        }
    }
    
    public void confirmAtack(JLabel label, JButton buton, JTextArea bitacora)       {
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántas naves desea enviar? (Disponibles: "+Utilities.planetAt(exitCell, planets).getShips()+")"));
        if(cantidad <= Utilities.planetAt(exitCell, planets).getShips()){
            Atack atack = new Atack(Utilities.planetAt(exitCell, planets).getName(), Utilities.planetAt(destinyCell, planets).getName(),
                                        cantidad, turno+1, 1+turno+Utilities.calcularDistancia(exitCell, destinyCell), 
                                        Utilities.planetAt(exitCell, planets).getDeathPercentage());
            int confirmacion = 0;
            if(Utilities.planetAt(exitCell, planets).getConqueror().equals(Utilities.planetAt(destinyCell, planets).getConqueror())){
                confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea enviar refuerzos de "+Utilities.planetAt(exitCell, planets).getName()+" hacia "+Utilities.planetAt(destinyCell, planets).getName()+" con "+atack.getShips()+" naves?");
                mensajes.add("Turno "+turno+": El conquistador "+Utilities.planetAt(exitCell, planets).getConqueror()+" ha enviado refuerzos del planeta "+Utilities.planetAt(exitCell, planets).getName()+" hacia el planeta "+Utilities.planetAt(destinyCell, planets).getName()+" que llegará en el turno "+atack.getTargetTurn());
            }else{
                confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea enviar un ataque de "+Utilities.planetAt(exitCell, planets).getName()+" hacia "+Utilities.planetAt(destinyCell, planets).getName()+" con una cantidad de "+cantidad+" naves?"
                        + "\nLlegará en el turno: "+atack.getTargetTurn());
                mensajes.add("Turno "+turno+": El conquistador "+Utilities.planetAt(exitCell, planets).getConqueror()+" ha enviado un ataque del planeta "+Utilities.planetAt(exitCell, planets).getName()+" hacia el planeta "+Utilities.planetAt(destinyCell, planets).getName()+" que llegará en el turno "+atack.getTargetTurn());
            }
            if(confirmacion == JOptionPane.OK_OPTION){
                Utilities.planetAt(exitCell, planets).setShips(Utilities.planetAt(exitCell, planets).getShips()-cantidad);
                actions.add(new Action(turno,1, atack));
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
        DefaultTableModel modelTablePlayers = (DefaultTableModel) tablePlayers.getModel();//obtenemos los jugadores que se desea tener
        for (int i = 0; i < modelTablePlayers.getRowCount(); i++) {//por cada fila
            //accedemos a la informacion de cada jugador 
            Player player = new Player(modelTablePlayers.getValueAt(i, 0).toString(),modelTablePlayers.getValueAt(i, 2).toString(), modelTablePlayers.getValueAt(i, 1).toString());
            this.configuration.getPlayers().add(player);//agregamos al jugador al juego
            this.players.add(player);
        }
    }
    
    public void doPlanets(JTable tablePlanets, int filas, int columnas){
        DefaultTableModel modelTablePlanets = (DefaultTableModel) tablePlanets.getModel();
        int[] posiciones = Utilities.numerosAleatoriosEntre(modelTablePlanets.getRowCount(), filas*columnas);
        for (int i = 0; i < modelTablePlanets.getRowCount(); i++) {
            Planet planet = new Planet();
            planet.setName(modelTablePlanets.getValueAt(i, 0).toString());
            planet.setShips(Integer.parseInt(modelTablePlanets.getValueAt(i, 1).toString()));
            planet.setProduction(Integer.parseInt(modelTablePlanets.getValueAt(i, 2).toString()));
            planet.setDeathPercentage(Double.parseDouble(modelTablePlanets.getValueAt(i,3).toString()));
            planet.setConqueror(modelTablePlanets.getValueAt(i, 4).toString());
            planet.setPositionX(posicionXY(posiciones[i], filas, columnas)[0]);
            planet.setPositionY(posicionXY(posiciones[i], filas, columnas)[1]);
            this.configuration.getPlanets().add(planet);
            this.planets.add(planet);
        }
    }

    void doPlanets(int cantidad, int filas, int columnas, ArrayList<Player> players) {
        ArrayList<String> nombres = Utilities.nombres(cantidad+players.size());
        int[] posiciones = Utilities.numerosAleatoriosEntre(cantidad+players.size(), filas*columnas);
        for (int i = 0; i < (cantidad+players.size()); i++) {
            Planet planet = new Planet();
            planet.setDeathPercentage(Utilities.porcentajeDeMuerteAleatorio());
            planet.setProduction((int)((Math.random()*20)+1));
            planet.setShips((int)((Math.random()*20)+1));
            planet.setName(nombres.get(i));
            if(i<players.size()){
                planet.setConqueror(players.get(i).getName());
            }else{
                planet.setConqueror("Nadie");
            }
            planet.setPositionX(posicionXY(posiciones[i], filas, columnas)[0]);
            planet.setPositionY(posicionXY(posiciones[i], filas, columnas)[1]);
            this.planets.add(planet);
            this.configuration.getPlanets().add(planet);
        }
    }
    
    public int[] posicionXY(int valor, int filas, int columnas){
        int[] posicion = new int[2];
        for (int i = 0; i < filas; i++) {
            if(valor>(columnas*i)&&valor<=(columnas*(i+1))){
                posicion[0] = i;
                posicion[1] = valor-(columnas*i)-1;
            }
        }
        return posicion;
    }

    public void reset(){
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
