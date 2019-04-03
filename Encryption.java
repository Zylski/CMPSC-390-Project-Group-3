/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

/**
 *
 * @author barry
 */
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Encryption {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        String test = "hi there l";   //get string
//        byte[] bytes = test.getBytes(); //convert to byte array
//       //String str = new String(DatatypeConverter.parseBase64Binary(test));

        //Set up file
        File f = new File("database.txt");

        byte[] fileContent = Files.readAllBytes(f.toPath());
        
        
        String encoded = Base64.getEncoder().encodeToString(fileContent); //put byte into this to get encoded string
        
        byte [] decoded = Base64.getDecoder().decode(encoded); //put string in here to get decoded byte array
        
        String decodedStr = new String(decoded); // for UT-8encoding
        
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("databasedecoded.txt", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.println(decodedStr);
        writer.close();
        
        try {
            writer = new PrintWriter("databaseencoded.txt", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.println(encoded);
        writer.close();
        
    }
    
}
