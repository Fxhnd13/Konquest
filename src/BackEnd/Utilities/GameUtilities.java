/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Utilities;

import Analizadores.Objects.Token;
import BackEnd.Configuration.GameConfiguration;
import BackEnd.Objects.Map;
import BackEnd.Objects.Planet;
import BackEnd.Objects.Player;
import UI.Cell;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose_
 */
public class GameUtilities {
    
    public static ArrayList<Token> getTokens(ArrayList<Token> tokens, int opcion){
        ArrayList<Token> resultado = new ArrayList<Token>();
        for (int i = 0; i < tokens.size(); i++) {
            switch(opcion){
                case 1:{
                    if(tokens.get(i).getType().equals("PR_MAPA")){
                        i++;
                        while(i<tokens.size()&&(!tokens.get(i).getType().equals("PR_ACCIONES"))&&(!tokens.get(i).getType().equals("PR_PLANETAS"))&&(!tokens.get(i).getType().equals("PR_PLANETAS_NEUTRALES"))&&(!tokens.get(i).getType().equals("PR_JUGADORES"))){
                            resultado.add(tokens.get(i));
                            i++;
                        }
                    }
                    break;
                }
                case 2:{
                    if(tokens.get(i).getType().equals("PR_PLANETAS")){
                        i++;
                        while(i<tokens.size()&&(!tokens.get(i).getType().equals("PR_ACCIONES"))&&(!tokens.get(i).getType().equals("PR_MAPA"))&&(!tokens.get(i).getType().equals("PR_PLANETAS_NEUTRALES"))&&(!tokens.get(i).getType().equals("PR_JUGADORES"))){
                            resultado.add(tokens.get(i));
                            i++;
                        }
                    }
                    break;
                }
                case 3:{
                    if(tokens.get(i).getType().equals("PR_JUGADORES")){
                        i++;
                        while(i<tokens.size()&&(!tokens.get(i).getType().equals("PR_ACCIONES"))&&(!tokens.get(i).getType().equals("PR_MAPA"))&&(!tokens.get(i).getType().equals("PR_PLANETAS_NEUTRALES"))&&(!tokens.get(i).getType().equals("PR_PLANETAS"))){
                            resultado.add(tokens.get(i));
                            i++;
                        }
                    }
                    break;
                }
                case 4:{
                    if(tokens.get(i).getType().equals("PR_PLANETAS_NEUTRALES")){
                        i++;
                        while(i<tokens.size()&&(!tokens.get(i).getType().equals("PR_ACCIONES"))&&(!tokens.get(i).getType().equals("PR_MAPA"))&&(!tokens.get(i).getType().equals("PR_PLANETAS"))&&(!tokens.get(i).getType().equals("PR_JUGADORES"))){
                            resultado.add(tokens.get(i));
                            i++;
                        }
                    }
                    break;
                }
                case 5:{
                    if(tokens.get(i).getType().equals("PR_ACCIONES")){
                        i++;
                        while(i<tokens.size()&&(!tokens.get(i).getType().equals("PR_MAPA"))&&(!tokens.get(i).getType().equals("PR_PLANETAS"))&&(!tokens.get(i).getType().equals("PR_JUGAODRES"))){
                            resultado.add(tokens.get(i));
                            i++;
                        }
                    }
                }
            }
        }
        return resultado;
    }
    
    public static double calcularDistancia(Cell salida, Cell destino){
        double valor = 0;
            int distanciaEnX = salida.getPosicionX()-destino.getPosicionX();
            int distanciaEnY = salida.getPosicionY()-destino.getPosicionY();
            valor = Math.sqrt((distanciaEnX*distanciaEnX)+(distanciaEnY*distanciaEnY));
        return valor;
    }
    
    public static int calcularDistancia(Planet destino, Planet salida) {
        int valor = 0;
            int distanciaEnX = salida.getPositionX()-destino.getPositionX();
            int distanciaEnY = salida.getPositionY()-destino.getPositionY();
            valor = (int) Math.sqrt((distanciaEnX*distanciaEnX)+(distanciaEnY*distanciaEnY));
        return valor;
    }
    
    public static boolean verifyIfPlanetIsConquest(String name, ArrayList<Planet> planets){
        boolean valor = false;
        for (Planet planet : planets) {
            if(planet.getName().equals(name)) valor = true;
        }
        return valor;
    }
    
    public static void doPlayers(JTable tablePlayers, ArrayList<Player> players, GameConfiguration configuration){
        DefaultTableModel modelTablePlayers = (DefaultTableModel) tablePlayers.getModel();//obtenemos los jugadores que se desea tener
        for (int i = 0; i < modelTablePlayers.getRowCount(); i++) {//por cada fila
            //accedemos a la informacion de cada jugador 
            Player player = new Player(modelTablePlayers.getValueAt(i, 0).toString(),modelTablePlayers.getValueAt(i, 2).toString(), modelTablePlayers.getValueAt(i, 1).toString());
            players.add(player);
            configuration.getPlayers().add(player);//agregamos al jugador al juego
        }
    }
    
    public static void doPlanets(JTable tablePlanets, int filas, int columnas, ArrayList<Player> players, ArrayList<Planet> planets, GameConfiguration configuration){
        DefaultTableModel modelTablePlanets = (DefaultTableModel) tablePlanets.getModel();
        int[] posiciones = Utilities.numerosAleatoriosEntre(modelTablePlanets.getRowCount(), filas*columnas);
        for (int i = 0; i < modelTablePlanets.getRowCount(); i++) {
            Planet planet = new Planet();
            Planet planet2 = new Planet();
            planet.setName(modelTablePlanets.getValueAt(i, 0).toString()); planet2.setName(planet.getName());
            planet.setShips(Integer.parseInt(modelTablePlanets.getValueAt(i, 1).toString())); planet2.setShips(planet.getShips());
            planet.setProduction(Integer.parseInt(modelTablePlanets.getValueAt(i, 2).toString())); planet2.setProduction(planet.getProduction());
            planet.setDeathPercentage(Double.parseDouble(modelTablePlanets.getValueAt(i,3).toString())); planet2.setDeathPercentage(planet.getDeathPercentage());
            planet.setConqueror(modelTablePlanets.getValueAt(i, 4).toString()); planet2.setConqueror(planet.getConqueror());
            planet.setPositionX(posicionXY(posiciones[i], filas, columnas)[0]); planet2.setPositionX(planet.getPositionX());
            planet.setPositionY(posicionXY(posiciones[i], filas, columnas)[1]); planet2.setPositionY(planet.getPositionY());
            if(Utilities.getPlayer(planet, players)!=null)Utilities.getPlayer(planet, players).setNavesCreadas(planet.getShips());
            if(Utilities.getPlayer(planet, players)!=null)Utilities.getPlayer(planet, players).setPlanetasConquistados(Utilities.getPlayer(planet, players).getPlanetasConquistados()+1);
            planets.add(planet);
            configuration.getPlanets().add(planet2);
        }
    }

    public static void doPlanets(int cantidad, int filas, int columnas, ArrayList<Player> players, ArrayList<Planet> planets, GameConfiguration configuration) {
        ArrayList<String> nombres = Utilities.nombres(cantidad+players.size());
        int[] posiciones = Utilities.numerosAleatoriosEntre(cantidad+players.size(), filas*columnas);
        for (int i = 0; i < (cantidad+players.size()); i++) {
            Planet planet = new Planet();
            Planet planet2 = new Planet();
            planet.setDeathPercentage(Utilities.porcentajeDeMuerteAleatorio()); planet2.setDeathPercentage(planet.getDeathPercentage());
            planet.setProduction((int)((Math.random()*20)+1)); planet2.setProduction(planet.getProduction());
            planet.setShips((int)((Math.random()*20)+1)); planet2.setShips(planet.getShips());
            planet.setName(nombres.get(i)); planet2.setName(planet.getName());
            if(i<players.size()){
                planet.setConqueror(players.get(i).getName()); planet2.setConqueror(planet.getConqueror());
                players.get(i).setNavesCreadas(planet.getShips());
                players.get(i).setPlanetasConquistados(1);
            }else{
                planet.setConqueror("Nadie");
            }
            planet.setPositionX(posicionXY(posiciones[i], filas, columnas)[0]); planet2.setPositionX(planet.getPositionX());
            planet.setPositionY(posicionXY(posiciones[i], filas, columnas)[1]); planet2.setPositionY(planet.getPositionY());
            planets.add(planet);
            configuration.getPlanets().add(planet2);
        }
    }
    
    public static int[] posicionXY(int valor, int filas, int columnas){
        int[] posicion = new int[2];
        for (int i = 0; i < filas; i++) {
            if(valor>(columnas*i)&&valor<=(columnas*(i+1))){
                posicion[0] = i;
                posicion[1] = valor-(columnas*i)-1;
            }
        }
        return posicion;
    }
    
    public static int planetasConquistadosPor(String nombre, ArrayList<Planet> planets){
        int cantidad = 0;
        for (Planet planet : planets) {
            if(planet.getConqueror().equals(nombre))cantidad++;
        }
        return cantidad;
    }
    
    public static boolean verificarValidezMapa(Map mapa, boolean azar){
        boolean valor = true;
        if(mapa.getFilas()==-1) valor=false;
        if(mapa.getColumnas()==-1) valor = false;
        if(mapa.getId()== null) valor = false;
        if(azar&&mapa.getPlanetasNeutrales()==-1)valor=false;
        return valor;
    }
    
}
