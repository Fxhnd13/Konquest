/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Analizadores.Objects.ErrorMessage;
import BackEnd.Objects.Action;
import BackEnd.Objects.Atack;
import BackEnd.Objects.Atacks;
import BackEnd.Objects.Planet;
import BackEnd.Objects.Player;
import BackEnd.Utilities.ReaderFiles;
import BackEnd.Utilities.Utilities;
import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose_
 */
public class UIManager {
    
    private File archivoAbierto = null;
    
    public void doErrorTable(JTable table, ArrayList<ErrorMessage> errores){
        cleanTable(table);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (ErrorMessage error : errores) {
            model.addRow(new String[]{error.getCadena(), String.valueOf(error.getLinea()), String.valueOf(error.getColumna()), error.getCausa()});
        }
    }
    
    public void doResultTable(JTable table, ArrayList<Player> players, ArrayList<Planet> planets){
        cleanTable(table);
        Utilities.ordenarJugadoresPorConquistas(players, planets);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Player player : players) {
            model.addRow(new String[]{player.getName(), String.valueOf(player.getPlanetasConquistados()), String.valueOf(player.getNavesCreadas()), String.valueOf(player.getNavesDestruidas()), String.valueOf(player.getAtaquesRealizados())});
        }
        JOptionPane.showMessageDialog(null, "El ganador de la partida fue "+players.get(0).getName()+" ¡Felicidades!", "Ganador", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void cleanTable(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    
    public void cleanLabel(JTextField text){
        text.setText("");
    }
    
    public static void addBorder(JLabel label, Border b ) {
        label.setBorder( b );
    }
    
    public String openProject(String text){
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String textoInicial = "";
        int resultado = selector.showOpenDialog(null);
        if(resultado==0){    
            archivoAbierto = selector.getSelectedFile();
            if((archivoAbierto == null)||(archivoAbierto.getName().equals(""))){
                JOptionPane.showMessageDialog(null, "Nombre de archivo no valido","Error",JOptionPane.ERROR_MESSAGE);
            }else{
                textoInicial = ReaderFiles.readFile(archivoAbierto);
            }
        }
        return textoInicial;
    }
    
    public void saveProyect(String text){
        if(archivoAbierto != null){
            ReaderFiles.overWriteFile(text, archivoAbierto);
        }else{
            JFileChooser selector = new JFileChooser();
            selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int resultado = selector.showOpenDialog(null);
            if(resultado==0){
                String nombre = JOptionPane.showInputDialog("Ingrese el nombre:.");
                if(nombre != null){
                    if(!nombre.isEmpty()){
                            ReaderFiles.writeFile(text, selector.getSelectedFile(), nombre, ".txt");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ingrese un nombre valido.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    void doAtacksDone(JTable AtacksReportTable, ArrayList<Atack> atacks) {
        AtacksReportTable.removeAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Planeta Salida");
        model.addColumn("Planeta Destino");
        model.addColumn("Naves Aliadas Destruidas");
        model.addColumn("Naves Enemigas Destruidas");
        model.addColumn("Resultado");
        for (Atack atack : atacks) {
            String resultado = (atack.isVictoria())? "Ataque Exitoso":"Ataque Fallido";
            model.addRow(new String[]{atack.getNameExitPlanet(), atack.getNameDestinyPlanet(), String.valueOf(atack.getNavesAliadasEliminadas()),
                                      String.valueOf(atack.getNavesEnemigasEliminadas()),resultado});
        }
        AtacksReportTable.setModel(model);
    }
    
    void doAtacksToDo(JTable AtacksReportTable, ArrayList<Atack> atacks){
        AtacksReportTable.removeAll();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Planeta Salida");
        model.addColumn("Planeta Destino");
        model.addColumn("Naves");
        model.addColumn("Turno de salida");
        model.addColumn("Turno de llegada");
        for (Atack atack : atacks) {
            String resultado = (atack.isVictoria())? "Ataque Exitoso":"Ataque Fallido";
            model.addRow(new String[]{atack.getNameExitPlanet(), atack.getNameDestinyPlanet(), String.valueOf(atack.getShips()),
                                      String.valueOf(atack.getExitTurn()),String.valueOf(atack.getTargetTurn())});
        }
        AtacksReportTable.setModel(model);
    }
    
    void escribirRepeticion(GameManager gameManager){
        String texto = "";
        texto+= "{";
        texto+= "\nMAPA : {";
        texto+= "\n    id : \""+gameManager.getConfiguration().getMap().getId()+"\",";
        texto+= "\n    tamano : {";
        texto+= "\n        filas : "+gameManager.getConfiguration().getMap().getFilas()+",";
        texto+= "\n        columnas : "+gameManager.getConfiguration().getMap().getColumnas();
        texto+= "\n    },";
        texto+= "\n    mapaCiego : "+gameManager.getConfiguration().getMap().isMapaCiego()+",";
        texto+= "\n    acumular : "+gameManager.getConfiguration().getMap().isAcumular()+",";
        texto+= "\n    NEUTRALES : {";
        texto+= "\n        mostrarNaves : "+gameManager.getConfiguration().getMap().getNeutrales().isShowShips()+",";
        texto+= "\n        mostrarEstadisticas : "+gameManager.getConfiguration().getMap().getNeutrales().isShowStadistics();
        texto+= "\n    },";
        texto+="\n     finalizacion : "+gameManager.getConfiguration().getMap().getFinalization();
        texto+= "\n},";
        texto+="\nJUGADORES : [";
        for (Player player : gameManager.getConfiguration().getPlayers()) {
            texto+="\n      {";
            texto+="\n          nombre : \""+player.getName()+"\"";
            texto+="\n      },";
        }
        texto = texto.substring(0, texto.length()-1);
        texto+="\n],";
        texto+="\nPLANETAS : [";
        for (Planet planet : gameManager.getConfiguration().getPlanets()) {
            texto+="\n      {";
            texto+="\n          nombre : \""+planet.getName()+"\",";
            texto+="\n          naves : "+planet.getShips()+",";
            texto+="\n          produccion : "+planet.getProduction()+",";
            texto+="\n          porcentajeMuertes : "+planet.getDeathPercentage()+",";
            texto+="\n          conquistador : \""+planet.getConqueror()+"\",";
            texto+="\n          posicionX : "+planet.getPositionX()+",";
            texto+="\n          posicionY : "+planet.getPositionY();
            texto+="\n      },";
        }
        texto = texto.substring(0, texto.length()-1);
        texto+="\n],";
        texto+="\nACCIONES : [";
        for (Action action : gameManager.getActions().getAccionesRealizadas()) {
            texto+="\n     {";
            texto+="\n          tipo : "+action.getType()+",";
            texto+="\n          jugador : \""+action.getPlayerName()+"\",";
            texto+="\n          turno : "+action.getTurn()+",";
            if(action.getAtack()!=null){
                texto+="\n          turnoSalida : "+action.getAtack().getExitTurn()+",";
                texto+="\n          turnoLlegada : "+action.getAtack().getTargetTurn()+",";
                texto+="\n          naves : "+action.getAtack().getShips()+",";
                texto+="\n          planetaSalida : \""+action.getAtack().getNameExitPlanet()+"\",";
                texto+="\n          planetaDestino : \""+action.getAtack().getNameDestinyPlanet()+"\",";
                texto+="\n          porcentajeMuertes : "+action.getAtack().getPorcentajeDeMuerte();
            }else{
                texto = texto.substring(0, texto.length()-1);
            }
            texto+="\n    },";
        }
        texto = texto.substring(0, texto.length()-1);
        texto+="\n]";
        texto+="\n}";
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resultado = selector.showOpenDialog(null);
        if(resultado==0){
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre:.");
            if(nombre != null){
                if(!nombre.isEmpty()){
                        ReaderFiles.writeFile(texto, selector.getSelectedFile(), nombre, ".txt");
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese un nombre valido.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    void escribirPartida(GameManager gameManager){
        String texto = "";
        texto+= "{";
        texto+= "\nMAPA : {";
        texto+= "\n    turno : "+gameManager.getTurno()+",";
        texto+= "\n    id : \""+gameManager.getConfiguration().getMap().getId()+"\",";
        texto+= "\n    tamano : {";
        texto+= "\n        filas : "+gameManager.getConfiguration().getMap().getFilas()+",";
        texto+= "\n        columnas : "+gameManager.getConfiguration().getMap().getColumnas();
        texto+= "\n    },";
        texto+= "\n    mapaCiego : "+gameManager.getConfiguration().getMap().isMapaCiego()+",";
        texto+= "\n    acumular : "+gameManager.getConfiguration().getMap().isAcumular()+",";
        texto+= "\n    NEUTRALES : {";
        texto+= "\n        mostrarNaves : "+gameManager.getConfiguration().getMap().getNeutrales().isShowShips()+",";
        texto+= "\n        mostrarEstadisticas : "+gameManager.getConfiguration().getMap().getNeutrales().isShowStadistics();
        texto+= "\n    },";
        texto+= "\n    finalizacion : {"+gameManager.getConfiguration().getMap().getFinalization()+",";
        texto+= "\n},";
        texto+="\nJUGADORES : [";
        for (Player player : gameManager.getConfiguration().getPlayers()) {
            texto+="\n      {";
            texto+="\n          nombre : \""+player.getName()+"\"";
            texto+="\n      },";
        }
        texto = texto.substring(0, texto.length()-1);
        texto+="\n],";
        texto+="\nPLANETAS : [";
        for (Planet planet : gameManager.getConfiguration().getPlanets()) {
            texto+="\n      {";
            texto+="\n          nombre : \""+planet.getName()+"\",";
            texto+="\n          naves : "+planet.getShips()+",";
            texto+="\n          produccion : "+planet.getProduction()+",";
            texto+="\n          porcentajeMuertes : "+planet.getDeathPercentage()+",";
            texto+="\n          conquistador : \""+planet.getConqueror()+"\",";
            texto+="\n          posicionX : "+planet.getPositionX()+",";
            texto+="\n          posicionY : "+planet.getPositionY();
            texto+="\n      },";
        }
        texto = texto.substring(0, texto.length()-1);
        texto+="\n],";
        texto+="\nACCIONES : [";
        for (Action action : gameManager.getActions().getAccionesRealizadas()) {
            texto+="\n     {";
            texto+="\n          tipo : "+action.getType()+",";
            texto+="\n          jugador : \""+action.getPlayerName()+"\",";
            texto+="\n          turno : "+action.getTurn()+",";
            if(action.getAtack()!=null){
                texto+="\n          turnoSalida : "+action.getAtack().getExitTurn()+",";
                texto+="\n          turnoLlegada : "+action.getAtack().getTargetTurn()+",";
                texto+="\n          naves : "+action.getAtack().getShips()+",";
                texto+="\n          planetaSalida : \""+action.getAtack().getNameExitPlanet()+"\",";
                texto+="\n          planetaDestino : \""+action.getAtack().getNameDestinyPlanet()+"\",";
                texto+="\n          porcentajeMuerte : "+action.getAtack().getPorcentajeDeMuerte();
            }else{
                texto = texto.substring(0, texto.length()-1);
            }
            texto+="\n    },";
        }
        texto = texto.substring(0, texto.length()-1);
        texto+="\n]";
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resultado = selector.showOpenDialog(null);
        if(resultado==0){
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre:.");
            if(nombre != null){
                if(!nombre.isEmpty()){
                        ReaderFiles.writeFile(texto, selector.getSelectedFile(), nombre, ".txt");
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese un nombre valido.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
