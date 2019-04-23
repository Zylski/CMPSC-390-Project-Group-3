/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaddatabase;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author barry
 */
public class AddAccount {
    
     public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
        String name;
        String username;
        String password;
        String url;
        String desc;
        String cat;
        String sqAnswer;
        String question;
        String answer;
        String apAnswer;
        String property;
        String propertyAnswer;
        
        Account a = new Account();
        Scanner scnr = new Scanner(System.in);
         
         System.out.println("Name");
         name = scnr.nextLine();
         a.setLabel(name);
         
         System.out.println("UserName");
         username = scnr.nextLine();
         a.setUsername(username);
         
         System.out.println("Password");
         password = scnr.nextLine();
         a.setPassword(password);
         
         System.out.println("URL");
         url = scnr.nextLine();
         a.setUrl(url);
         
         System.out.println("Description");
         desc = scnr.nextLine();
         a.setDescription(desc);
         
         System.out.println("Category");
         cat = scnr.nextLine();
         a.setCategory(cat);
         
         System.out.println("Input a security question? (y/n)");
         sqAnswer = scnr.nextLine();
         sqAnswer = sqAnswer.toLowerCase();
         ArrayList<SecurityQuestion> sq = new ArrayList<SecurityQuestion>();
         if (sqAnswer.equals("y")){
             while (sqAnswer.equals("y")){
         
         System.out.println("Question");
         question = scnr.nextLine();
         
         
         System.out.println("Answer");
         answer = scnr.nextLine();
         
         sq.add(new SecurityQuestion(question, answer));
         
         System.out.println("Add another one? (y/n)");
         sqAnswer = scnr.nextLine();
         sqAnswer = sqAnswer.toLowerCase();
             }
         }
         else{
             sq.add(new SecurityQuestion());
         }
         
         System.out.println("Input an additional property? (y/n)");
         apAnswer = scnr.nextLine();
         apAnswer = apAnswer.toLowerCase();
         
         ArrayList<AdditionalProperties> ap = new ArrayList<AdditionalProperties>();
         
         if (apAnswer.equals("y")){
         while (apAnswer.equals("y")){
         
         System.out.println("Property");
         property = scnr.nextLine();
         
         System.out.println("Peroperty Answer");
         propertyAnswer = scnr.nextLine();
         
         ap.add(new AdditionalProperties(property, propertyAnswer));
         
         System.out.println("Again? (y/n)");
         apAnswer = scnr.nextLine();
         apAnswer = apAnswer.toLowerCase();
         }
         }
         else{
             ap.add(new AdditionalProperties());
         }
         
         Gson gson = new Gson();
         
         System.out.println("Type in name for database you wish to add this account to m'lord: ");
        String databasename = scnr.nextLine();
        
        boolean fileExistance = false;
        
        File f_encrypt = new File(databasename + "_encrypted.json");

        
        fileExistance = f_encrypt.exists();
        
        if (fileExistance == true){
            System.out.println("File does exist");     
        }     
        else{     
            System.out.println("File does not exist");
            return;
   }
        String dbStringE = new String(Files.readAllBytes(f_encrypt.toPath()));
        String dbString;
        String password_encrypt = "Password$2";
        Decryption.decrypt(databasename, password_encrypt);
        
        File f_decrypt = new File(databasename + "_decrypted.json");
        
        String dbStringD = new String(Files.readAllBytes(f_decrypt.toPath()));
        
        Database db = gson.fromJson(dbStringD, Database.class);
        
        db.addAccount(a);
        
        dbString = gson.toJson(db, Database.class);
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(databasename + ".json", "UTF-8"); //think about using json/xml file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.print(dbString);

        writer.close();
        
        Encryption.encrypt(databasename, password_encrypt);
        
        Decryption.decrypt(databasename, password_encrypt);
        
        
        
}
}
