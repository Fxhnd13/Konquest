/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fxhnd
 */
public class ReaderFiles {
    
    public static String readFile(File file){
        String text = "";
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while(((line = br.readLine()) != null)){
                text += line + "\n";
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReaderFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderFiles.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                if(null!= fr)fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ReaderFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return text;
    }
    
    public static void writeFile(String text, File file, String nombre, String extension){
        FileWriter fichero = null;
        PrintWriter pw = null;
        String path = file.getAbsolutePath()+"/"+nombre+extension;
        try{
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            pw.print(text);
        } catch (IOException ex) {
            Logger.getLogger(ReaderFiles.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                if(null != fichero) fichero.close();
            } catch (IOException ex) {
                Logger.getLogger(ReaderFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void overWriteFile(String text, File file){
        FileWriter fichero = null;
        PrintWriter pw = null;
        String path = file.getAbsolutePath();
        try{
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            pw.print(text);
        } catch (IOException ex) {
            Logger.getLogger(ReaderFiles.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                if(null != fichero) fichero.close();
            } catch (IOException ex) {
                Logger.getLogger(ReaderFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
