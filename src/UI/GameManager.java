/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Analizadores.Objects.ErrorMessage;
import Analizadores.Objects.Token;
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
    private int turno = 1, jugadorEnTurno = 0, state = 0, mode = 0;
    private Atacks atacks = new Atacks();
    private Actions actions = new Actions();
    private Cell exitCell = null, destinyCell = null;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private ArrayList<Return> returns = new ArrayList<Return>();
    private ArrayList<String> mensajes = new ArrayList<String>();

    public void realizarAccionesDeTurno(JTextArea bitacora, JPanel spacePanel){
        for (Action action : actions.getAccionesRealizadas()) {
            if(action.getTurn()==turno){
                actions.getAccionesARealizar().add(action);
            }
        }
        terminarTurno(bitacora, spacePanel);
    }
    
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
                Utilities.getPlayer(salida, players).setPlanetasConquistados(Utilities.getPlayer(salida, players).getPlanetasConquistados() + 1);
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
        if(mode == 0){
            this.state = 0;
            this.exitCell = null;
            this.destinyCell = null;
            label.setText("Planeando...");
            buton.setText("Atacar");
        }
    }

    public void showDistance(JLabel label, JButton buton) {
        JOptionPane.showMessageDialog(null, "La distancia entre el planeta " + Utilities.planetAt(exitCell, planets).getName()
                + " y el planeta " + Utilities.planetAt(destinyCell, planets).getName() + " es de " + GameUtilities.calcularDistancia(exitCell, destinyCell));
        cancelAtack(label, buton);
    }

    public void doPlayers(JTable tablePlayers) {
        GameUtilities.doPlayers(tablePlayers, players, configuration);
        players = new ArrayList<Player>(configuration.getPlayers());
    }

    public void doPlanets(JTable tablePlanets, int filas, int columnas) {
        GameUtilities.doPlanets(tablePlanets, filas, columnas, players, planets, configuration);
        planets = new ArrayList<Planet>(configuration.getPlanets());
    }

    void doPlanets(int cantidad, int filas, int columnas) {
        GameUtilities.doPlanets(cantidad, filas, columnas, players, planets, configuration);
        planets = new ArrayList<Planet>(configuration.getPlanets());
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

    void verificarDatosJuego(ArrayList<Token> tokens, ArrayList<ErrorMessage> errores) {
        ArrayList<Token> tokensMapa = GameUtilities.getTokens(tokens, 1);
        ArrayList<Token> tokensPlanetas = GameUtilities.getTokens(tokens, 2);
        ArrayList<Token> tokensJugadores = GameUtilities.getTokens(tokens, 3);
        ArrayList<Token> tokensPlanetasNeutrales = GameUtilities.getTokens(tokens, 4);
        for (int i = 0; i < tokensMapa.size(); i++) {
            switch (tokens.get(i).getType()) {
                case "PR_ID": {
                    i++;
                    configuration.getMap().setId(tokens.get(i).getLexem());
                    break;
                }
                case "PR_FILAS": {
                    i++;
                    configuration.getMap().setFilas(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
                case "PR_COLUMNAS": {
                    i++;
                    configuration.getMap().setColumnas(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
                case "PR_AZAR": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        this.alAzar = true;
                    }
                    break;
                }
                case "PR_CANTIDAD_PLANETAS_NEUTRALES": {
                    i++;
                    configuration.getMap().setPlanetasNeutrales(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
                case "PR_MAPA_CIEGO": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().setMapaCiego(true);
                    }
                    break;
                }
                case "PR_ACUMULAR": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().setMapaCiego(true);
                    }
                    break;
                }
                case "PR_MOSTRAR_NAVES": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().getNeutrales().setShowShips(true);
                    } else {
                        configuration.getMap().getNeutrales().setShowShips(false);
                    }
                    break;
                }
                case "PR_MOSTRAR_ESTADISTICAS": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().getNeutrales().setShowStadistics(true);
                    } else {
                        configuration.getMap().getNeutrales().setShowStadistics(false);
                    }
                    break;
                }
                case "PR_PRODUCCION": {
                    i++;
                    configuration.getMap().getNeutrales().setProduction(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
                case "PR_FINALIZACION": {
                    i++;
                    configuration.getMap().setFinalization(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
            }
        }
        if (!GameUtilities.verificarValidezMapa(configuration.getMap(), this.alAzar)) {
            Token token = tokens.get(tokens.size() - 1);
            errores.add(new ErrorMessage(token.getLine(), token.getColumn(), token.getLexem(), "Faltan Datos."));
        }
        for (int i = 0; i < tokensJugadores.size(); i++) {
            switch (tokensJugadores.get(i).getType()) {
                case "LLAVE_A": {
                    Player player = new Player();
                    player.setFirsToken(tokensJugadores.get(i));
                    i++;
                    while (!tokensJugadores.get(i).getType().equals("LLAVE_C")) {
                        switch (tokensJugadores.get(i).getType()) {
                            case "PR_NOMBRE": {
                                i++;
                                if (player.getName() == null) {
                                    player.setName(tokensJugadores.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensJugadores.get(i).getLine(), tokensJugadores.get(i).getColumn(), tokensJugadores.get(i).getLexem(), "Ya existe un valor declarado para el nombre."));
                                }
                                break;
                            }
                            case "PR_TIPO": {
                                i++;
                                if (player.getType() == null) {
                                    player.setType(tokensJugadores.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensJugadores.get(i).getLine(), tokensJugadores.get(i).getColumn(), tokensJugadores.get(i).getLexem(), "Ya existe un valor declarado para el tipo."));
                                }
                                break;
                            }
                            case "CORCHETE_A": {
                                i++;
                                while (i < tokensJugadores.size() && !tokensJugadores.get(i).getType().equals("CORCHETE_C")) {
                                    player.getPlanetas().add(tokensJugadores.get(i).getLexem());
                                    i++;
                                }
                                break;
                            }
                        }
                        i++;
                    }
                    if (!player.isValid()) {
                        errores.add(new ErrorMessage(player.getFirsToken().getLine(), player.getFirsToken().getColumn(), player.getFirsToken().getLexem(), "Faltan datos en la estructura del jugador."));
                    } else {
                        this.configuration.getPlayers().add(player);
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < tokensPlanetas.size(); i++) {
            switch (tokensPlanetas.get(i).getType()) {
                case "LLAVE_A": {
                    Planet planeta = new Planet();
                    planeta.setFirstToken(tokensPlanetas.get(i));
                    i++;
                    while (!tokensPlanetas.get(i).getType().equals("LLAVE_C")) {
                        switch (tokensPlanetas.get(i).getType()) {
                            case "PR_NOMBRE": {
                                i++;
                                if (planeta.getName() == null) {
                                    planeta.setName(tokensPlanetas.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para el nombre."));
                                }
                                break;
                            }
                            case "PR_PORCENTAJE_MUERTES": {
                                i++;
                                if (planeta.getDeathPercentage() == -1) {
                                    planeta.setDeathPercentage(Double.parseDouble(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para el porcentaje de muertes."));
                                }
                                break;
                            }
                            case "PR_PRODUCCION": {
                                i++;
                                if (planeta.getProduction() == -1) {
                                    planeta.setProduction(Integer.parseInt(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para la produccion."));
                                }
                                break;
                            }
                            case "PR_NAVES": {
                                i++;
                                if (planeta.getShips() == -1) {
                                    planeta.setShips(Integer.parseInt(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para las naves."));
                                }
                            }
                        }
                        i++;
                    }
                    if (!planeta.isValid()) {
                        errores.add(new ErrorMessage(planeta.getFirstToken().getLine(), planeta.getFirstToken().getColumn(), planeta.getFirstToken().getLexem(), "Faltan datos en la estructura del planeta."));
                    } else {
                        if (planeta.getProduction() == -1) {
                            errores.add(new ErrorMessage(planeta.getFirstToken().getLine(), planeta.getFirstToken().getColumn(), planeta.getFirstToken().getLexem(), "Faltan datos en la estructura del planeta."));
                        } else {
                            this.configuration.getPlanets().add(planeta);
                        }
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < tokensPlanetasNeutrales.size(); i++) {
            switch (tokensPlanetasNeutrales.get(i).getType()) {
                case "LLAVE_A": {
                    Planet planeta = new Planet();
                    planeta.setFirstToken(tokensPlanetasNeutrales.get(i));
                    i++;
                    while (!tokensPlanetasNeutrales.get(i).getType().equals("LLAVE_C")) {
                        switch (tokensPlanetasNeutrales.get(i).getType()) {
                            case "PR_NOMBRE": {
                                i++;
                                if (planeta.getName() == null) {
                                    planeta.setName(tokensPlanetasNeutrales.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetasNeutrales.get(i).getLine(), tokensPlanetasNeutrales.get(i).getColumn(), tokensPlanetasNeutrales.get(i).getLexem(), "Ya existe un valor declarado para el nombre."));
                                }
                                break;
                            }
                            case "PR_PORCENTAJE_MUERTES": {
                                i++;
                                if (planeta.getDeathPercentage() == -1) {
                                    planeta.setDeathPercentage(Double.parseDouble(tokensPlanetasNeutrales.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetasNeutrales.get(i).getLine(), tokensPlanetasNeutrales.get(i).getColumn(), tokensPlanetasNeutrales.get(i).getLexem(), "Ya existe un valor declarado para el porcentaje de muertes."));
                                }
                                break;
                            }
                            case "PR_PRODUCCION": {
                                i++;
                                if (planeta.getProduction() == -1) {
                                    planeta.setProduction(Integer.parseInt(tokensPlanetasNeutrales.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetasNeutrales.get(i).getLine(), tokensPlanetasNeutrales.get(i).getColumn(), tokensPlanetasNeutrales.get(i).getLexem(), "Ya existe un valor declarado para la produccion."));
                                }
                                break;
                            }
                            case "PR_NAVES": {
                                i++;
                                if (planeta.getShips() == -1) {
                                    planeta.setShips(Integer.parseInt(tokensPlanetasNeutrales.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetasNeutrales.get(i).getLine(), tokensPlanetasNeutrales.get(i).getColumn(), tokensPlanetasNeutrales.get(i).getLexem(), "Ya existe un valor declarado para las naves."));
                                }
                            }
                        }
                        i++;
                    }
                    if (!planeta.isValid()) {
                        errores.add(new ErrorMessage(planeta.getFirstToken().getLine(), planeta.getFirstToken().getColumn(), planeta.getFirstToken().getLexem(), "Faltan datos en la estructura del planeta neutral."));
                    } else {
                        this.configuration.getPlanets().add(planeta);
                    }
                    break;
                }
            }
        }
        this.configuration.doLastConfigurations();
        players = new ArrayList<Player>(configuration.getPlayers());
        if (alAzar) {
            this.doPlanets(configuration.getMap().getPlanetasNeutrales(), configuration.getMap().getFilas(), configuration.getMap().getColumnas());
        } else {
            planets = new ArrayList<Planet>(configuration.getPlanets());
        }
    }

    void verificarDatosJuegoRepeticion(ArrayList<Token> tokens, ArrayList<ErrorMessage> errores, int opcion) {
        ArrayList<Token> tokensMapa = GameUtilities.getTokens(tokens, 1);
        ArrayList<Token> tokensPlanetas = GameUtilities.getTokens(tokens, 2);
        ArrayList<Token> tokensJugadores = GameUtilities.getTokens(tokens, 3);
        ArrayList<Token> tokensAcciones = GameUtilities.getTokens(tokens, 5);
        for (int i = 0; i < tokensMapa.size(); i++) {
            switch (tokens.get(i).getType()) {
                case "PR_TURNO": {
                    i++;
                    this.turno = Integer.parseInt(tokens.get(i).getLexem());
                    break;
                }
                case "PR_ID": {
                    i++;
                    configuration.getMap().setId(tokens.get(i).getLexem());
                    break;
                }
                case "PR_FILAS": {
                    i++;
                    configuration.getMap().setFilas(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
                case "PR_COLUMNAS": {
                    i++;
                    configuration.getMap().setColumnas(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
                case "PR_MAPA_CIEGO": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().setMapaCiego(true);
                    }
                    break;
                }
                case "PR_ACUMULAR": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().setMapaCiego(true);
                    }
                    break;
                }
                case "PR_MOSTRAR_NAVES": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().getNeutrales().setShowShips(true);
                    } else {
                        configuration.getMap().getNeutrales().setShowShips(false);
                    }
                    break;
                }
                case "PR_MOSTRAR_ESTADISTICAS": {
                    i++;
                    if (tokens.get(i).getLexem().equals("true")) {
                        configuration.getMap().getNeutrales().setShowStadistics(true);
                    } else {
                        configuration.getMap().getNeutrales().setShowStadistics(false);
                    }
                    break;
                }
                case "PR_FINALIZACION": {
                    i++;
                    configuration.getMap().setFinalization(Integer.parseInt(tokens.get(i).getLexem()));
                    break;
                }
            }
        }
        for (int i = 0; i < tokensJugadores.size(); i++) {
            switch (tokensJugadores.get(i).getType()) {
                case "LLAVE_A": {
                    Player player = new Player();
                    player.setFirsToken(tokensJugadores.get(i));
                    i++;
                    while (!tokensJugadores.get(i).getType().equals("LLAVE_C")) {
                        switch (tokensJugadores.get(i).getType()) {
                            case "PR_NOMBRE": {
                                i++;
                                if (player.getName() == null) {
                                    player.setName(tokensJugadores.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensJugadores.get(i).getLine(), tokensJugadores.get(i).getColumn(), tokensJugadores.get(i).getLexem(), "Ya existe un valor declarado para el nombre."));
                                }
                                break;
                            }
                        }
                        i++;
                    }
                    this.configuration.getPlayers().add(player);
                    break;
                }
            }
        }
        for (int i = 0; i < tokensPlanetas.size(); i++) {
            switch (tokensPlanetas.get(i).getType()) {
                case "LLAVE_A": {
                    Planet planeta = new Planet();
                    planeta.setFirstToken(tokensPlanetas.get(i));
                    i++;
                    while (!tokensPlanetas.get(i).getType().equals("LLAVE_C")) {
                        switch (tokensPlanetas.get(i).getType()) {
                            case "PR_NOMBRE": {
                                i++;
                                if (planeta.getName() == null) {
                                    planeta.setName(tokensPlanetas.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para el nombre."));
                                }
                                break;
                            }
                            case "PR_PORCENTAJE_MUERTES": {
                                i++;
                                if (planeta.getDeathPercentage() == -1) {
                                    planeta.setDeathPercentage(Double.parseDouble(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para el porcentaje de muertes."));
                                }
                                break;
                            }
                            case "PR_PRODUCCION": {
                                i++;
                                if (planeta.getProduction() == -1) {
                                    planeta.setProduction(Integer.parseInt(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para la produccion."));
                                }
                                break;
                            }
                            case "PR_NAVES": {
                                i++;
                                if (planeta.getShips() == -1) {
                                    planeta.setShips(Integer.parseInt(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor declarado para las naves."));
                                }
                                break;
                            }
                            case "PR_CONQUISTADOR": {
                                i++;
                                if (planeta.getConqueror() == null) {
                                    planeta.setConqueror(tokensPlanetas.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor para el conquistador del planeta."));
                                }
                                break;
                            }
                            case "PR_POSICION_X": {
                                i++;
                                if (planeta.getPositionX() == -1) {
                                    planeta.setPositionX(Integer.parseInt(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor para la posicion x"));
                                }
                                break;
                            }
                            case "PR_POSICION_Y": {
                                i++;
                                if (planeta.getPositionY() == -1) {
                                    planeta.setPositionY(Integer.parseInt(tokensPlanetas.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensPlanetas.get(i).getLine(), tokensPlanetas.get(i).getColumn(), tokensPlanetas.get(i).getLexem(), "Ya existe un valor para la posicion y"));
                                }
                                break;
                            }
                        }
                        i++;
                    }
                    if (!planeta.isValid()) {
                        errores.add(new ErrorMessage(planeta.getFirstToken().getLine(), planeta.getFirstToken().getColumn(), planeta.getFirstToken().getLexem(), "Faltan datos en la estructura del planeta."));
                    } else {
                        this.configuration.getPlanets().add(planeta);
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < tokensAcciones.size(); i++) {
            switch (tokensAcciones.get(i).getType()) {
                case "LLAVE_A": {
                    Action action = new Action();
                    action.setFirsToken(tokensAcciones.get(i));
                    i++;
                    while (!tokensAcciones.get(i).getType().equals("LLAVE_C")) {
                        switch (tokensAcciones.get(i).getType()) {
                            case "PR_TIPO": {
                                i++;
                                if (action.getType() == -1) {
                                    action.setType(Integer.parseInt(tokensAcciones.get(i).getLexem()));
                                    if(action.getType()==1)action.setAtack(new Atack());
                                } else {
                                    errores.add(new ErrorMessage(tokensAcciones.get(i).getLine(), tokensAcciones.get(i).getColumn(), tokensAcciones.get(i).getLexem(), "Ya existe un valor declarado para el nombre."));
                                }
                                break;
                            }
                            case "PR_TURNO": {
                                i++;
                                if (action.getTurn() == -1) {
                                    action.setTurn(Integer.parseInt(tokensAcciones.get(i).getLexem()));
                                } else {
                                    errores.add(new ErrorMessage(tokensAcciones.get(i).getLine(), tokensAcciones.get(i).getColumn(), tokensAcciones.get(i).getLexem(), "Ya existe un valor de turno"));
                                }
                                break;
                            }
                            case "PR_JUGADOR": {
                                i++;
                                if (action.getPlayerName() == null) {
                                    action.setPlayerName(tokensAcciones.get(i).getLexem());
                                } else {
                                    errores.add(new ErrorMessage(tokensAcciones.get(i).getLine(), tokensAcciones.get(i).getColumn(), tokensAcciones.get(i).getLexem(), "Ya existe un valor de jugador"));
                                }
                                break;
                            }
                            case "PR_TURNO_SALIDA": {
                                i++;
                                action.getAtack().setExitTurn(Integer.parseInt(tokensAcciones.get(i).getLexem()));
                                break;
                            }
                            case "PR_TURNO_LLEGADA": {
                                i++;
                                action.getAtack().setTargetTurn((Integer.parseInt(tokensAcciones.get(i).getLexem())));
                                break;
                            }
                            case "PR_PLANETA_SALIDA": {
                                i++;
                                action.getAtack().setNameExitPlanet(tokensAcciones.get(i).getLexem());
                                break;
                            }
                            case "PR_PLANETA_DESTINO": {
                                i++;
                                action.getAtack().setNameDestinyPlanet(tokensAcciones.get(i).getLexem());
                                break;
                            }
                            case "PR_NAVES": {
                                i++;
                                action.getAtack().setShips(Integer.parseInt(tokensAcciones.get(i).getLexem()));
                                break;
                            }
                            case "PR_PORCENTAJE_MUERTES": {
                                i++;
                                action.getAtack().setPorcentajeDeMuerte(Double.parseDouble(tokensAcciones.get(i).getLexem()));
                                break;
                            }
                        }
                        i++;
                    }
                    this.actions.getAccionesRealizadas().add(action);
                    break;
                }
            }
        }
        this.configuration.doLastConfigurationsRp();
        players = new ArrayList<Player>(configuration.getPlayers());
        planets = new ArrayList<Planet>(configuration.getPlanets());
        this.mode = 2;
    }

    public int getMode() {
        return mode;
    }

    public Actions getActions() {
        return actions;
    }

    public ArrayList<Return> getReturns() {
        return returns;
    }

    public ArrayList<String> getMensajes() {
        return mensajes;
    }

    public Atacks getAtacks() {
        return atacks;
    }

    public void setAtacks(Atacks atacks) {
        this.atacks = atacks;
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
