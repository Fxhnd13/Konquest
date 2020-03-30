package Analizadores.AnalizadorArchivoConfiguracion;

import java_cup.runtime.Symbol;
import Analizadores.Objects.Token;
import Analizadores.Objects.ErrorMessage;
import java.util.ArrayList;

%%
%class LexerConf
%public
%cup
%char
%line
%column
%full

L = [a-zA-Z]
S = "_"|"-"|"$"
D = [0-9]
N = [1-9]
ID = ({L}|{S})({L}|{D}|{S})*
ENTERO = 0|(({N})({D})*)
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

%{
    private ArrayList<Token> tokens = new ArrayList<Token>();
    private ArrayList<ErrorMessage> errores = new ArrayList<ErrorMessage>();
    private boolean existenTokens = false;
    
    public void analizar(){
        try{
            while(true){

                // Obtener el token analizado y mostrar su información
                Symbol sym = this.next_token();
                if (!this.existenTokens())
                break;
            }
        }catch (Exception e){
          System.out.println(e.toString());
        }
    }

    public boolean existenTokens(){
        return this.existenTokens;
    }

    public ArrayList<Token> getTokens(){
        return this.tokens;
    }
        
    public ArrayList<ErrorMessage> getErrores(){
        return this.errores;
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }

%}

%eof{
 
 /* Código a ejecutar al finalizar el análisis, en este caso cambiaremos el valor de una variable bandera */
 this.existenTokens = false;
 
%eof}

%%

<YYINITIAL>{
    "MAPA"              {this.existenTokens = true; return symbol(sym.PR_MAPA, "MAPA");}
    "JUGADORES"         {this.existenTokens = true; return symbol(sym.PR_JUGADORES, "JUGADORES");}
    "PLANETAS"          {this.existenTokens = true; return symbol(sym.PR_PLANETAS, "PLANETAS");}
    "PLANETAS_NEUTRALES" {this.existenTokens = true; return symbol(sym.PR_PLANETAS_NEUTRALES, "PLANETAS_NEUTRALES");}
    "id"                {tokens.add(new Token(yytext(), "PR_ID", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_ID, "id");}
    "tamano"            {tokens.add(new Token(yytext(), "PR_SIZE", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_SIZE, "tamaño");}
    "filas"             {tokens.add(new Token(yytext(), "PR_FILAS", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_FILAS, "filas");}
    "columnas"          {tokens.add(new Token(yytext(), "PR_COLUMNAS", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_COLUMNAS, "columnas");}
    "alAzar"            {tokens.add(new Token(yytext(), "PR_AZAR", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_AZAR, "alAzar");}
    "planetasNeutrales" {tokens.add(new Token(yytext(), "PR_CANTIDAD_PLANETAS_NEUTRALES", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_CANTIDAD_PLANETAS_NEUTRALES, "planetasNeutrales");}
    "mapaCiego"         {tokens.add(new Token(yytext(), "PR_MAPA_CIEGO", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_MAPA_CIEGO, "mapaCiego");}
    "acumular"          {tokens.add(new Token(yytext(), "PR_ACUMULAR", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_ACUMULAR, "acumular");}
    "NEUTRALES"         {this.existenTokens = true; return symbol(sym.PR_NEUTRALES, "NEUTRALES");}
    "mostrarNaves"      {tokens.add(new Token(yytext(), "PR_MOSTRAR_NAVES", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_MOSTRAR_NAVES, "mostrarNaves");}
    "mostrarEstadisticas" {tokens.add(new Token(yytext(), "PR_MOSTRAR_ESTADISTICAS", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_MOSTRAR_ESTADISTICAS, "mostrarEstadisticas");}
    "finalizacion"      {tokens.add(new Token(yytext(), "PR_FINALIZACION", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_FINALIZACION, "finalizacion");}
    "nombre"            {tokens.add(new Token(yytext(), "PR_NOMBRE", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_NOMBRE,"nombre");}
    "naves"             {tokens.add(new Token(yytext(), "PR_NAVES", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_NAVES, "naves");}
    "produccion"        {tokens.add(new Token(yytext(), "PR_PRODUCCION", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_PRODUCCION, "produccion");}
    "porcentajeMuertes" {tokens.add(new Token(yytext(), "PR_PORCENTAJE_MUERTES", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_PORCENTAJE_MUERTES, "porcentajeMuertes");}
    "planetas"          {tokens.add(new Token(yytext(), "PR_PLANETAS_JUGADORES", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_PLANETAS_JUGADORES, "planetas");}
    "tipo"              {tokens.add(new Token(yytext(), "PR_TIPO", yyline, yycolumn));this.existenTokens = true; return symbol(sym.PR_TIPO, "tipo");}
    "HUMANO"            {tokens.add(new Token(yytext(), "TIPO", yyline, yycolumn));this.existenTokens = true; return symbol(sym.TIPO, yytext());}
    "DIFICIL"           {tokens.add(new Token(yytext(), "TIPO", yyline, yycolumn));this.existenTokens = true; return symbol(sym.TIPO, yytext());}
    "FACIL"             {tokens.add(new Token(yytext(), "TIPO", yyline, yycolumn));this.existenTokens = true; return symbol(sym.TIPO, yytext());}
    ("true")|("false")  {tokens.add(new Token(yytext(), "BOOLEAN", yyline, yycolumn));this.existenTokens = true; return symbol(sym.BOOLEAN, yytext());}
    "{"                 {this.existenTokens = true; return symbol(sym.LLAVE_A, "{");}
    "}"                 {this.existenTokens = true; return symbol(sym.LLAVE_C, "}");}
    "["                 {this.existenTokens = true; return symbol(sym.CORCHETE_A, "[");}
    "]"                 {this.existenTokens = true; return symbol(sym.CORCHETE_C, "]");}
    ":"                 {this.existenTokens = true; return symbol(sym.ASIGNACION, ":");}
    ({ID})              {tokens.add(new Token(yytext(), "ID", yyline, yycolumn));this.existenTokens = true; return symbol(sym.ID, yytext()); }
    "\""                {this.existenTokens = true; return symbol(sym.COMILLA, "\"");}
    ","                 {this.existenTokens = true; return symbol(sym.SEPARADOR, ",");}
    ({ENTERO})          {tokens.add(new Token(yytext(), "ENTERO", yyline, yycolumn));this.existenTokens = true; return symbol(sym.ENTERO, yytext());}
    ({ENTERO})(".")({D})* {tokens.add(new Token(yytext(), "DOUBLE", yyline, yycolumn));this.existenTokens= true; return symbol(sym.DOUBLE, yytext());}
    ({WhiteSpace})      {/* ignore */}
    [^]                 {errores.add(new ErrorMessage(yyline, yycolumn, yytext(), "No es un caracter aceptado por el lenguaje")); }
}