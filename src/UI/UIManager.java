/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose_
 */
public class UIManager {
    
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
