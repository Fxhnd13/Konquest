/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BackEnd.Configuration.ConfigurationNeutrales;
import BackEnd.Configuration.GameConfiguration;
import BackEnd.Objects.Action;
import BackEnd.Objects.Actions;
import BackEnd.Objects.Atack;
import BackEnd.Objects.Atacks;
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
    private int turno = 1, jugadorEnTurno = 0, state = 0;
    private Atacks atacks = new Atacks();
    private Actions actions = new Actions();
    private Cell exitCell = null, destinyCell = null;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private ArrayList<Return> returns = new ArrayList<Return>();
    private ArrayList<String> mensajes = new ArrayList<String>();

    public void cambiarJugador(JTextArea bitacora, JPanel spacePanel) {
        actions.getAccionesARealizar().add(new Action(turno, 0, null, players.get(jugadorEnTurno).getName()));
        jugadorEnTurno++;
        if (jugadorEnTurno == players.size()) {
            jugadorEnTurno = 0;
            terminarTurno(bitacora, spacePanel);
        }
    }

    public void terminarTurno(JTextArea bitacora, JPanel spacePanel) {
        turno++;
        for (Action action : actions.getAccionesARealizar()) {
            switch (action.getType()) {
                case 0:
                    actions.getAccionesRealizadas().add(action);
                    break;
                case 1: {
                    Planet salida = Utilities.getPlanetaPorNombre(action.getAtack().getNameExitPlanet(), planets);
                    Planet destino = Utilities.getPlanetaPorNombre(action.getAtack().getNameDestinyPlanet(), planets);
                    action.getAtack().setExitTurn(turno);
                    action.getAtack().setTargetTurn(turno + Utilities.turnosParaLlegar(GameUtilities.calcularDistancia(salida, destino)));
                    atacks.getAtaquesActivos().add(action.getAtack());
                    if (salida.getConqueror().equals(destino.getConqueror())) {
                        mensajes.add("Turno " + action.getAtack().getExitTurn() + ": Se han enviado refuerzos del planeta " + action.getAtack().getNameExitPlanet() + " hacia el planeta " + action.getAtack().getNameDestinyPlanet() + " que llegará en el turno " + action.getAtack().getTargetTurn());
                    } else {
                        mensajes.add("Turno " + action.getAtack().getExitTurn() + ": Se ha enviado un ataque del planeta " + action.getAtack().getNameExitPlanet() + " hacia el planeta " + action.getAtack().getNameDestinyPlanet() + " que llegará en el turno " + action.getAtack().getTargetTurn());
                    }
                    actions.getAccionesRealizadas().add(action);
                    break;
                }
            }
        }
        for (int i = 0; i < atacks.getAtaquesActivos().size(); i++) {
            if (atacks.getAtaquesActivos().get(i).getTargetTurn() == turno) {
                realizarAtaque(atacks.getAtaquesActivos().get(i), bitacora);
                atacks.getAtaquesRealizados().add(atacks.getAtaquesActivos().get(i));
                atacks.getAtaquesActivos().remove(i);
                repintarMapa(spacePanel);
            }
        }
        for (Return aReturn : returns) {
            if (aReturn.getTargetTurn() == turno) {
                realizarLLegada(aReturn);
                bitacora.setText(bitacora.getText() + "\nHan regresado las tropas al planeta " + aReturn.getDestino() + " del ataque realizado al planeta " + aReturn.getSalida());
            }
        }
        for (Planet planet : planets) {
            planet.setShips(planet.getShips() + planet.getProduction());
            if (!planet.getConqueror().equals("Nadie")) {
                Utilities.getPlayer(planet, players).setNavesCreadas(Utilities.getPlayer(planet, players).getNavesCreadas() + planet.getProduction());
            }
            if (this.configuration.getMap().isAcumular()) {
                planet.setProduction(planet.getProduction() + 1);
            }
        }
        for (String mensaje : mensajes) {
            bitacora.setText(bitacora.getText() + "\n" + mensaje);
        }
        actions.getAccionesARealizar().clear();
        mensajes.clear();
        verificarVictoria();
    }

    public void verificarVictoria() {
        if (configuration.getMap().getFinalization() == -1) {
            boolean valor = true;
            for (int i = 0; i < players.size(); i++) {
                valor = true;
                for (Planet planet : planets) {
                    if (!players.get(i).getName().equals(planet.getConqueror())) {
                        valor = false;
                        break;
                    }
                }
                if (valor) {
                    break;
                }
            }
            if (valor) {
                victoria = true;
            }
        } else {
            if (configuration.getMap().getFinalization() == turno) {
                victoria = true;
            }
        }
    }

    public void realizarLLegada(Return a) {
        Planet destino = Utilities.getPlanetaPorNombre(a.getDestino(), planets);
        destino.setShips(destino.getShips() + a.getNaves());
    }

    public void realizarAtaque(Atack atack, JTextArea bitacora) {
        Planet destino = Utilities.getPlanetaPorNombre(atack.getNameDestinyPlanet(), planets);
        Planet salida = Utilities.getPlanetaPorNombre(atack.getNameExitPlanet(), planets);
        if (!salida.getConqueror().equals(destino.getConqueror())) {
            int cantidadQueEliminaElAtacante = (int) (destino.getShips() * atack.getPorcentajeDeMuerte());
            int cantidadQueEliminaElDefensor = (int) (atack.getShips() * destino.getDeathPercentage());
            if (!destino.getConqueror().equals("Nadie")) {
                Utilities.getPlayer(destino, players).setNavesDestruidas(Utilities.getPlayer(destino, players).getNavesDestruidas() + cantidadQueEliminaElAtacante);
            }
            if (!salida.getConqueror().equals("Nadie")) {
                Utilities.getPlayer(salida, players).setNavesDestruidas(Utilities.getPlayer(salida, players).getNavesDestruidas() + cantidadQueEliminaElAtacante);
            }
            atack.setNavesAliadasEliminadas((cantidadQueEliminaElDefensor > atack.getShips()) ? atack.getShips() : cantidadQueEliminaElDefensor);
            atack.setNavesEnemigasEliminadas((cantidadQueEliminaElAtacante > destino.getShips()) ? destino.getShips() : cantidadQueEliminaElAtacante);
            int cantidadSobranteAtacante = atack.getShips() - cantidadQueEliminaElDefensor;
            int cantidadSobranteDefensor = destino.getShips() - cantidadQueEliminaElAtacante;
            if (cantidadSobranteDefensor > cantidadSobranteAtacante) {
                if (cantidadSobranteAtacante > 0) {
                    Return retorno = new Return(destino.getName(), salida.getName(), cantidadSobranteAtacante, turno + GameUtilities.calcularDistancia(destino, salida));
                    returns.add(retorno);
                }
                destino.setShips(cantidadSobranteDefensor);
                bitacora.setText(bitacora.getText() + "\nTurno " + turno + ": El planeta " + destino.getName() + " se ha defendido del ataque del conquistador " + salida.getConqueror());
            } else if (cantidadSobranteDefensor < cantidadSobranteAtacante) {
                atack.setVictoria(true);
                destino.setConqueror(salida.getConqueror());
                destino.setShips(cantidadSobranteDefensor + cantidadSobranteAtacante);
                if (!salida.getConqueror().equals("Nadie")) {
                    Utilities.getPlayer(salida, players).setPlanetasConquistados(Utilities.getPlayer(salida, players).getPlanetasConquistados() + 1);
                }
                if (!destino.getConqueror().equals("Nadie")) {
                    Utilities.getPlayer(destino, players).setPlanetasConquistados(Utilities.getPlayer(destino, players).getPlanetasConquistados() - 1);
                }
                bitacora.setText(bitacora.getText() + "\nTurno " + turno + ": El planeta " + destino.getName() + " ha caído ante el conquistador " + salida.getConqueror());
            } else {
                bitacora.setText(bitacora.getText() + "\nTurno " + turno + ": Ha habido un empate, el planeta se ha defendido pero quedado sin naves.");
            }
        } else {
            destino.setShips(destino.getShips() + atack.getShips());
            bitacora.setText(bitacora.getText() + "\nTurno " + turno + ": Han llegado los refuerzos del planeta " + salida.getName() + " al planeta " + destino.getName());
        }
    }

    public void confirmAtack(JLabel label, JButton buton, JTextArea bitacora) {
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántas naves desea enviar? (Disponibles: " + Utilities.planetAt(exitCell, planets).getShips() + ")"));
        if (cantidad <= Utilities.planetAt(exitCell, planets).getShips()) {
            Atack atack = new Atack(Utilities.planetAt(exitCell, planets).getName(), Utilities.planetAt(destinyCell, planets).getName(),
                    cantidad, Utilities.planetAt(exitCell, planets).getDeathPercentage());
            int confirmacion = 0;
            if (Utilities.planetAt(exitCell, planets).getConqueror().equals(Utilities.planetAt(destinyCell, planets).getConqueror())) {
                confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea enviar refuerzos de " + Utilities.planetAt(exitCell, planets).getName() + " hacia " + Utilities.planetAt(destinyCell, planets).getName() + " con " + atack.getShips() + " naves?"
                        + "\nLlegará en el turno: " + (1 + turno + Utilities.turnosParaLlegar(GameUtilities.calcularDistancia(exitCell, destinyCell))));
            } else {
                confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea enviar un ataque de " + Utilities.planetAt(exitCell, planets).getName() + " hacia " + Utilities.planetAt(destinyCell, planets).getName() + " con una cantidad de " + cantidad + " naves?"
                        + "\nLlegará en el turno: " + (1 + turno + Utilities.turnosParaLlegar(GameUtilities.calcularDistancia(exitCell, destinyCell))));
            }
            if (confirmacion == JOptionPane.OK_OPTION) {
                Utilities.planetAt(exitCell, planets).setShips(Utilities.planetAt(exitCell, planets).getShips() - cantidad);
                players.get(jugadorEnTurno).setAtaquesRealizados(players.get(jugadorEnTurno).getAtaquesRealizados() + 1);
                actions.getAccionesARealizar().add(new Action(turno, 1, atack, players.get(jugadorEnTurno).getName()));
                cancelAtack(label, buton);
            } else {
                cancelAtack(label, buton);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No posee la cantidad de naves que desea enviar.", "Error", JOptionPane.ERROR_MESSAGE);
            cancelAtack(label, buton);
        }
    }

    public void cancelAtack(JLabel label, JButton buton) {
        this.state = 0;
        this.exitCell = null;
        this.destinyCell = null;
        label.setText("Planeando...");
        buton.setText("Atacar");
    }

    public void showDistance(JLabel label, JButton buton) {
        JOptionPane.showMessageDialog(null, "La distancia entre el planeta " + Utilities.planetAt(exitCell, planets).getName()
                + " y el planeta " + Utilities.planetAt(destinyCell, planets).getName() + " es de " + GameUtilities.calcularDistancia(exitCell, destinyCell));
        cancelAtack(label, buton);
    }

    public void doPlayers(JTable tablePlayers) {
        GameUtilities.doPlayers(tablePlayers, players, configuration);
    }

    public void doPlanets(JTable tablePlanets, int filas, int columnas) {
        GameUtilities.doPlanets(tablePlanets, filas, columnas, players, planets, configuration);
    }

    void doPlanets(int cantidad, int filas, int columnas) {
        GameUtilities.doPlanets(cantidad, filas, columnas, players, planets, configuration);
    }

    public void reset(JPanel spacePanel, JTextArea bitacora) {
        if (spacePanel != null) {
            spacePanel.removeAll();
        }
        if (bitacora != null) {
            bitacora.setText("");
        }
        this.configuration = new GameConfiguration();
        this.planets.clear();
        this.players.clear();
        this.actions.getAccionesARealizar().clear();
        this.actions.getAccionesRealizadas().clear();
        this.returns.clear();
        this.atacks.getAtaquesActivos().clear();
        this.atacks.getAtaquesRealizados().clear();
    }

    public void repintarMapa(JPanel spacePanel) {
        for (int i = 0; i < spacePanel.getComponentCount(); i++) {
            Cell cell = (Cell) spacePanel.getComponent(i);
            if (Utilities.planetAt(cell, planets) != null) {
                for (Player player : players) {
                    if (Utilities.planetAt(cell, planets).getConqueror().equals(player.getName())) {
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
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
