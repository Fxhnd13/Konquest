/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Analizadores.Objects.ErrorMessage;
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
                            ReaderFiles.writeFile(text, selector.getSelectedFile(), nombre, ".json");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ingrese un nombre valido.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    
}
