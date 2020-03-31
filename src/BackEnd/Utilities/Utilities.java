/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Utilities;

import BackEnd.Objects.Planet;
import BackEnd.Objects.Player;
import UI.Cell;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose_
 */
public class Utilities {
    
    public static int turnosParaLlegar(double cantidad){
        int valor = (int) cantidad;
        double valor2 = (double) valor;
        if(valor2<cantidad) valor++;
        return valor; 
    }
    
    public static int[] numerosAleatoriosEntre(int cantidad, int b){
        int[] numeros = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            int numero = (int) (Math.random() * b);
            while(existe(numero, numeros)){
                numero = (int) (Math.random() * b);
            }
            if(numero<1)numero++;
            numeros[i] = numero;
        }
        return numeros;
    }
    
    public static double porcentajeDeMuerteAleatorio(){
        double numero = (Math.random()+0.10);
        if(numero>1)numero-=0.10;
        return numero;
    }
    
    public static String getLetra(int i){
        String letra = "";
        switch(i){
            case 1: letra = "A"; break;
            case 2: letra = "B"; break;
            case 3: letra = "C"; break;
            case 4: letra = "D"; break;
            case 5: letra = "E"; break;
            case 6: letra = "F"; break;
            case 7: letra = "G"; break;
            case 8: letra = "H"; break;
            case 9: letra = "I"; break;
            case 10: letra = "J"; break;
            case 11: letra = "K"; break;
            case 12: letra = "L"; break;
            case 13: letra = "M"; break;
            case 14: letra = "N"; break;
            case 15: letra = "O"; break;
            case 16: letra = "P"; break;
            case 17: letra = "Q"; break;
            case 18: letra = "R"; break;
            case 19: letra = "S"; break;
            case 20: letra = "T"; break;
            case 21: letra = "U"; break;
            case 22: letra = "V"; break;
            case 23: letra = "W"; break;
            case 24: letra = "X"; break;
            case 25: letra = "Y"; break;
            case 26: letra = "Z"; break;
        }
        return letra;
    }
    
    public static ArrayList<String> nombres(int cantidad){
        ArrayList<String> nombres = new ArrayList<String>();
        int cantidadNombres = 0, repeticiones = cantidad/26;
        for(int i=0; i <= repeticiones; i++){
            if(cantidadNombres==cantidad)break;
            String palabra = "";
            for(int j=1; j<27; j++){
                if(cantidadNombres==cantidad)break;
                if(i==0){
                    palabra = getLetra(j);
                    nombres.add(palabra);
                    cantidadNombres++;
                }else{
                    palabra = getLetra(i)+getLetra(j);
                    nombres.add(palabra);
                    cantidadNombres++;
                }
            }
        }
        return nombres;
    }
    
    public static boolean existe(int numero, int numeros[]){
        boolean valor = false;
        for (int i = 0; i < numeros.length; i++)
        {
            if(numero==numeros[i])valor=true;
        }
        return valor;
    }
    
    public static Planet getPlanetaPorNombre(String nombre, ArrayList<Planet> planets){
        Planet valor = null;
        for (Planet planet : planets) {
            if(planet.getName().equals(nombre)) valor = planet;
        }
        return valor;
    }
    
    public static Planet planetAt(Cell cell, ArrayList<Planet> planets){
        Planet planet = null;
        for (Planet temp : planets) {
            if(temp.getPositionX()==cell.getPosicionX()&&temp.getPositionY()==cell.getPosicionY()){
                planet = temp;
            }
        }
        return planet;
    }
    
    public static boolean existPlayer(Player player, int index, DefaultTableModel model) {
        boolean valor = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if(player.getName().equals(model.getValueAt(i, 0).toString())||player.getColorPlayer().equals(model.getValueAt(i, 2).toString()))valor=true;
        }
        return valor;
    }

    public static boolean existPlanet(Planet planet, DefaultTableModel model) {
         boolean valor = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if(planet.getName().equals(model.getValueAt(i, 0).toString()))valor=true;
        }
        return valor;
    }
    
    public static Color getColor(String a){
        Color color = null;
        switch(a){
                case "Rojo": color = Color.RED; break;
                case "Azul": color = Color.BLUE; break;
                case "Verde": color = Color.GREEN; break;
                case "Amarillo": color = Color.YELLOW; break;
                case "Naranja": color = Color.ORANGE; break;
                case "Blanco": color = Color.WHITE; break;
        }
        return color;
    }
    
    public static String getColorByNumber(int a){
        String color = null;
        switch(a){
            case 0: color = "Rojo"; break;
            case 1: color = "Azul"; break;
            case 2: color = "Verde"; break;
            case 3: color = "Amarillo"; break;
            case 4: color = "Naranja"; break;
            case 5: color = "Blanco"; break;
        }
        return color;
    }
    
    public static int getTypePlayer(String a){
        int valor = 0;
        switch(a){
                case "Humano": valor = 0; break;
                case "Facil": valor = 1; break;
                case "Dificil": valor = 2; break;
        }
        return valor;
    }

    public static Player getPlayer(Planet planet, ArrayList<Player> players) {
        Player valor = null;
        for (Player player : players) {
            if(planet.getConqueror().equals(player.getName())){
                valor = player;
            }
        }
        return valor;
    }

    public static void ordenarJugadoresPorConquistas(ArrayList<Player> players, ArrayList<Planet> planets) {
        for (Player player : players) {
            player.setPlanetasConquistados(GameUtilities.planetasConquistadosPor(player.getName(), planets));
        }
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.size()-1; j++) {
                if(players.get(j).getPlanetasConquistados()<players.get(j+1).getPlanetasConquistados()){
                    Player aux = players.get(j+1);
                    players.set(j+1, players.get(j));
                    players.set(j, aux);
                }
            }
        }
    }
   
}
