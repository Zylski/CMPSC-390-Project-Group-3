/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaddatabase;

import java.io.File;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import javax.xml.bind.DatatypeConverter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author barry
 */
public class LoadDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String databasename = "testname";
        //Scanner scnr = new Scanner(System.in);
        //System.out.println("Type in name for database you wish to load m'lord: ");
        //String databasename = scnr.nextLine();//Add if-else to check whether or not file exists later on
        
       // File f = new File(databasename + ".txt");
        
        String encoded = new String(Files.readAllBytes(Paths.get(databasename + ".txt")), "UTF-8");//Get what is alredy encoded from file into a string     
        
        byte [] decoded = Base64.getDecoder().decode(encoded); //put string in here to get decoded byte array
        
        String decodedStr = new String(decoded); // for UT-8encoding
        
        //print out decoded database
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("databasedecoded.txt", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoadDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.println(decodedStr);
        writer.close();
    }
    
}
