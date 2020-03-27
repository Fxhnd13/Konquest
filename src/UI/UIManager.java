/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BackEnd.Objects.Planet;
import BackEnd.Objects.Player;
import BackEnd.Utilities.Utilities;
import java.awt.BorderLayout;
import java.util.ArrayList;
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
}
