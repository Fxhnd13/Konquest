/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

/**
 *
 * @author jose_
 */
public class Action {
    
    private int turn, type;
    private Atack atack = null;
    private String playerName;

    public Action(int turn, int type, Atack atack, String playerName){
        this.type = type;
        this.turn = turn;
        this.atack = atack;
        this.playerName = playerName;
        if(type!=0){
            System.out.println("Se realizó un ataque\nSalida: "+atack.getNameExitPlanet()
                    +"\nDestino: "+atack.getNameDestinyPlanet()
                    +"\nNaves: "+atack.getShips()
                    +"\nTurno Salida: "+atack.getExitTurn()
                    +"\nTurno Llegada: "+atack.getTargetTurn());
        }else{
            System.out.println("EL jugador "+this.playerName+" termino su turno.");
        }
    }
    
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Atack getAtack() {
        return atack;
    }

    public void setAtack(Atack atack) {
        this.atack = atack;
    }
}
