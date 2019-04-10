/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author barry
 */
public class Encryption{
    public static void encrypt (String databasename, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        
       File f = new File(databasename + ".txt"); 
        
      SecretKeySpec skeyspec = new SecretKeySpec(password.getBytes(), "Blowfish");
      Cipher cipher = Cipher.getInstance("Blowfish");
      
      cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
      
      byte[] encrypted = cipher.doFinal(Files.readAllBytes(f.toPath()));
      
      String encoded = Base64.getEncoder().encodeToString(encrypted);
             
//        byte[] fileContent = Files.readAllBytes(f.toPath());
//        
//        String encoded = Base64.getEncoder().encodeToString(fileContent); //put byte into this to get encoded string
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(databasename + "_encrypted.txt", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.print(encoded);
        writer.close();
    }
}
   
