/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Objects;

import Analizadores.Objects.Token;

/**
 *
 * @author jose_
 */
public class Action {
    
    private int turn=-1, type=-1;
    private Atack atack = null;
    private Return retorno = null;
    private String playerName=null;
    private Token firsToken=null;

    public Action(int turn, int type, Atack atack, String playerName){
        this.type = type;
        this.turn = turn;
        this.playerName = playerName;
        switch(type){
            case 0: break;
            case 1: this.atack = atack; break;
        }
        if(type!=0){
            System.out.println("Se realizó un ataque\nSalida: "+atack.getNameExitPlanet()
                    +"\nDestino: "+atack.getNameDestinyPlanet()
                    +"\nNaves: "+atack.getShips()
                    +"\nTurno Salida: "+atack.getExitTurn()
                    +"\nTurno Llegada: "+atack.getTargetTurn()
                    +"\n\n");
        }else{
            System.out.println("EL jugador "+this.playerName+" termino su turno.\n\n");
        }
    }

    public Action() {
    }

    public Token getFirsToken() {
        return firsToken;
    }

    public void setFirsToken(Token firsToken) {
        this.firsToken = firsToken;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Return getRetorno() {
        return retorno;
    }

    public void setRetorno(Return retorno) {
        this.retorno = retorno;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
}
