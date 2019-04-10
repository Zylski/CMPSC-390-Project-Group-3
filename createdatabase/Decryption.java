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
import java.util.Base64;
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
public class Decryption {   
    public static void decrypt (String databasename, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        File f = new File(databasename + "_encrypted.txt");
       
        //String encoded = new String(Files.readAllBytes(f.toPath()));
        
      SecretKeySpec skeyspec = new SecretKeySpec(password.getBytes(), "Blowfish");
      Cipher cipher = Cipher.getInstance("Blowfish");
      
      String text = new String(Files.readAllBytes(f.toPath()));
      
      cipher.init(Cipher.DECRYPT_MODE, skeyspec);
      
      byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(text));
        
//        byte[] decoded = Base64.getDecoder().decode(encoded); //put string in here to get decoded byte array
//        
        String decryptedStr = new String(decrypted); // for UT-8encoding
        
        //print out decoded database
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(databasename + "_decrypted.txt", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.print(decryptedStr);
        writer.close();
    }
  }

