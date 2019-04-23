/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaddatabase;

/**
 *
 * @author bh19601
 */
import com.google.gson.Gson;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import javax.xml.bind.DatatypeConverter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CreateDatabase {

    /**
     * @param args the command line arguments
     */
    
//    public static String Encrypt (String str){
//        
//        return str;
//    }
//	
	
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner scnr = new Scanner(System.in);
        String databasename = "";
        String masterPW = "";
        String masterPWcheck = "";
        Database db = new Database();
        
        //Make strings into an array so i could bot with one method to fill in stuff?
        
        System.out.println("Type in name for database: ");
        databasename = scnr.nextLine();
        if (databasename.length() == 0) databasename = "database";
        
        while (masterPW.length() < 1) {
        System.out.println("Input master password for database: ");
        masterPW = scnr.nextLine();
        
        System.out.println("Type in master password again");
         masterPWcheck = scnr.nextLine();
             while (!masterPW.equals(masterPWcheck)){
                    System.out.println("Passwords didn't match, try again: ");
                    System.out.println("Input master password for database: ");
                    masterPW = scnr.nextLine();
                    System.out.println("Type in password again");
                    masterPWcheck = scnr.nextLine();
            }
        }
      
        System.out.println("");
        System.out.println("master password = " + masterPW);
        db.setMasterpassword(masterPW);
        
        Gson gson = new Gson();

        String json = gson.toJson(db);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(databasename + ".json", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.print(json);

        writer.close();
        
        String password_encrypt = "Password$2";
        
        Encryption.encrypt(databasename, password_encrypt);
        
        Decryption.decrypt(databasename, password_encrypt);
        
  }


    
}
