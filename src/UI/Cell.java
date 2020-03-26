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
    
    private String imagePath, datos;
    private int posicionX, posicionY;
    JDialog info = new JDialog();
    JLabel nombre = new JLabel();
    JLabel produccion = new JLabel();
    JLabel naves = new JLabel();
    JLabel porcentaje = new JLabel();
    JLabel conquistador = new JLabel();

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

    Cell() {
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
    
    public void showInfo(MouseEvent e, Planet planet){
        info.setVisible(true);
        nombre.setText(" Nombre: "+planet.getName());
        produccion.setText(" Produccion: "+planet.getProduction());
        naves.setText(" Naves: "+planet.getShips());
        porcentaje.setText(" Porcentaje de muerte: "+planet.getDeathPercentage());
        conquistador.setText(" Conquistador: "+planet.getConqueror());
        nombre.setVisible(true);
        produccion.setVisible(true);
        naves.setVisible(true);
        porcentaje.setVisible(true);
        conquistador.setVisible(true);
        info.setLocation(570, 380);
    }
    
    public void showStadistics(MouseEvent e, Planet planet){
        info.setVisible(true);
        nombre.setText(" Nombre: "+planet.getName());
        produccion.setText(" Produccion: "+planet.getProduction());
        porcentaje.setText(" Porcentaje de muerte: "+planet.getDeathPercentage());
        conquistador.setText(" Conquistador: "+planet.getConqueror());
        nombre.setVisible(true);
        produccion.setVisible(true);
        naves.setVisible(false);
        porcentaje.setVisible(true);
        conquistador.setVisible(true);
        info.setLocation(570, 380);
    }
    
    public void showName(MouseEvent e, Planet planet){
        info.setVisible(true);
        nombre.setText(" Nombre: "+planet.getName());
        nombre.setVisible(true);
        produccion.setVisible(true);
        naves.setVisible(true);
        porcentaje.setVisible(true);
        conquistador.setVisible(true);
        info.setLocation(570, 380);
    }
    
    public void showShips(MouseEvent e, Planet planet){
        info.setVisible(true);
        nombre.setText(" Nombre: "+planet.getName());
        naves.setText(" Naves: "+planet.getShips());
        nombre.setVisible(true);
        naves.setVisible(true);
        produccion.setVisible(false);
        porcentaje.setVisible(false);
        conquistador.setVisible(false);
        info.setLocation(570,380);
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
