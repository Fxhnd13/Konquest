/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Utilities;

import BackEnd.Objects.Planet;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author jose_
 */
public class Utilities {
    
    public static boolean verifyIfPlanetIsConquest(String name, ArrayList<Planet> planets){
        boolean valor = false;
        for (Planet planet : planets) {
            if(planet.getName().equals(name)) valor = true;
        }
        return valor;
    }
    
    public static int[] numerosAleatoriosEntre(int cantidad, int b){
        int[] numeros = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            int numero = (int) (Math.random() * b) + 1;
            while(existe(numero, numeros)){
                numero = (int) (Math.random() * b) + 1;
            }
            numeros[i] = numero;
        }
        return numeros;
    }
    
    public static boolean existe(int numero, int numeros[]){
        boolean valor = false;
        for (int i = 0; i < 52; i++)
        {
            if(numero==numeros[i])valor=true;
        }
        return valor;
    }
    
    public static Planet planetAt(int x, int y, ArrayList<Planet> planets){
        Planet planet = null;
        for (Planet temp : planets) {
            if(temp.getPositionX()==x&&temp.getPositionY()==y){
                planet = temp;
            }
        }
        return planet;
    }
    
    public static void addBorder(JLabel label, Border b ) {
        String str = b.getClass().toString();
        str = str.substring( str.lastIndexOf('.') + 1 );
        label.setLayout( new BorderLayout() );
        label.add(new JLabel( str,JLabel.CENTER ),BorderLayout.CENTER );
        label.setBorder( b );
    }
    
//        int filas = Integer.parseInt(JOptionPane.showInputDialog(null, "filas"));
//        int columnas = Integer.parseInt(JOptionPane.showInputDialog(null, "columnas"));
//        this.SpacePanel.setLayout(new GridLayout(filas, columnas));
//        for (int i = 0; i < filas; i++) {
//            for (int j = 0; j < columnas; j++) {
//                Planet planet = new Planet();
//                planet.setText("Planeta en ["+i+"]["+j+"]");
//                planet.setSize(planet.getWeidth(), planet.getHeight());
//                planet.setVisible(true);
//                this.SpacePanel.add(planet);
//            }
//        }
//        scroll.validate();
//        scroll.repaint();
    
}
