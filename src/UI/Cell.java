/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BackEnd.Objects.Planet;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.accessibility.AccessibleContext;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author jose_
 */
public class Cell extends JLabel {
    
    private Planet planet = null; 
    private String imagePath, datos;
    private int posicionX, posicionY;
    JDialog info = new JDialog();
    JLabel nombre = new JLabel("  Nombre: ");
    JLabel produccion = new JLabel("  Produccion: ");
    JLabel naves = new JLabel("  Naves: ");
    JLabel porcentaje = new JLabel("  Porcentaje de muerte: ");
    JLabel conquistador = new JLabel("  Conquistador: ");

    public Cell(int x, int y){
        posicionX = x;
        posicionY = y;
        info.setLayout(new GridLayout(5, 1));
        info.setTitle("Informacion de la celda");
        info.add(nombre);
        info.add(produccion);
        info.add(naves);
        info.add(porcentaje);
        info.add(conquistador);
        info.setSize(300, 120);
        info.setFocusable(false);
        info.setResizable(false);
    }
    
    public void showInfo(MouseEvent e){
        info.setVisible(true);
        info.setLocation(570, 380);
    }
    
    public void hideInfo(MouseEvent e){
        info.setVisible(false);
    }
    
    public String getImagePath(){
        return imagePath;
    }
    
    public void setImagePath(String path){
        this.imagePath = path;
    }
    
    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }
    
    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Component getLabelFor() {
        return labelFor;
    }

    public void setLabelFor(Component labelFor) {
        this.labelFor = labelFor;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }

    public void setAccessibleContext(AccessibleContext accessibleContext) {
        this.accessibleContext = accessibleContext;
    }
    
}
