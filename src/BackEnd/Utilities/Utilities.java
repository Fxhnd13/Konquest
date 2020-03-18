/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Utilities;

import BackEnd.Objects.Planet;
import java.util.ArrayList;

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
