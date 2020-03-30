/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizadores.Objects;

/**
 *
 * @author fxhnd
 */
public class Token {

    private int line;
    private int column;
    private String lexem;
    private String type;
    
    public Token(String lexem, String type, int line, int column){
        this.line = line;
        this.lexem = lexem;
        this.type = type;
        if(type.equals("CADENA")){
            this.lexem = lexem.replaceAll("\"", "");
        }
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getLexem() {
        return lexem;
    }

    public void setLexem(String lexem) {
        this.lexem = lexem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
