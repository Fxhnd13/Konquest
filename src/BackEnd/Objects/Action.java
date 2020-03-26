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

    public Action(int turn, int type, Atack atack){
        this.type = type;
        this.turn = turn;
        this.atack = atack;
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
