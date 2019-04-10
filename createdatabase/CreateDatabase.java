/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdatabase;

/**
 *
 * @author bh19601
 */
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
    public static String Encrypt (String str){
        
        return str;
    }
	
	
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner scnr = new Scanner(System.in);
        String databasename = "";
        String masterPW = "";
        String masterPWcheck = "";
        String name = "";
        String username = "";
        String password = "";
        String passwordcheck = "";
        String url = "";
        String desc = "";
        
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
        
        while (name.length() < 1){
            System.out.println("Input what account is for: ");
            name = scnr.nextLine();
        }
        
        while (username.length() < 1){
            System.out.println("Input username of account: ");
            username = scnr.nextLine();
        }
        
        while (password.length() < 1){
            System.out.println("Input password of account: ");
            password = scnr.nextLine();
            System.out.println("Type in password again");
            passwordcheck = scnr.nextLine();
                while (!password.equals(passwordcheck)){
                    System.out.println("Passwords didn't match, try again: ");
                    System.out.println("Input password of account: ");
                    password = scnr.nextLine();
                    System.out.println("Type in password again");
                    passwordcheck = scnr.nextLine();
            }
        }
        
        while (url.length() < 1){
            System.out.println("Input url associated with the account: ");
            url = scnr.nextLine();
        }
        
        while (desc.length() < 1){
            System.out.println("Input description for the account: ");
            desc = scnr.nextLine();
        }
        System.out.println("");
        System.out.println("master password = " + masterPW);
        System.out.println("name for account = " + name);
        System.out.println("username for account = " + username);
        System.out.println("password for account = " + password);
        System.out.println("url for account = " + url);
        System.out.println("description for account = " + desc);
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(databasename + ".txt", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.println("master password = " + masterPW);
		writer.println("");
        writer.println("name for account = " + name);
        writer.println("username for account = " + username);
        writer.println("password for account = " + password);
        writer.println("url for account = " + url);
        writer.println("description for account = " + desc);
        writer.close();
        
        String password_encrypt = "Password$2";
        
        Encryption.encrypt(databasename, password_encrypt);
        
        Decryption.decrypt(databasename, password_encrypt);
        
  }


    
}
