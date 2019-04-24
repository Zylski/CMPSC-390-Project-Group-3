/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaddatabase;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author barry
 */
public class EditAccount {
        public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
        Scanner scnr = new Scanner(System.in);
        System.out.println("Type in name for database you wish to load m'lord: ");
        String databasename = scnr.nextLine();
        
          Gson gson = new Gson();
        
        boolean fileExistance = false;
        
        File f = new File(databasename + "_encrypted.json");
        
        fileExistance = f.exists();
        
        if (fileExistance == true){
            System.out.println("File does exist");
        }
        
        else{
            System.out.println("File does not exist.");
            return;
        }
        
        String password_encrypt = "Password$2";
        Decryption.decrypt(databasename, password_encrypt);
        
        File f_decrypt = new File(databasename + "_decrypted.json");
        
        String dbStringD = new String(Files.readAllBytes(f_decrypt.toPath()));
        
        Database db = gson.fromJson(dbStringD, Database.class);
        
        System.out.println("Type in name of the account you wish to edit: ");
        String accountLabel = scnr.nextLine();
        
        ArrayList<Account> account = new ArrayList<Account>();
        
        account = db.getAccount();
        
        String whatToEdit;
        String theEdit;
        String oneOrOther;
        int whichOne = 0;
        ArrayList<SecurityQuestion> sq = new ArrayList<SecurityQuestion>();
        ArrayList<AdditionalProperties> ap = new ArrayList<AdditionalProperties>();
        
        for(int i = 0;i<account.size();i++){
            if (account.get(i).compareToString(accountLabel) == true){
                sq = account.get(i).getSecurityQuestion();
                ap = account.get(i).getAdditionalProperties();
                System.out.println("What do you want to edit in this account?");
                whatToEdit = scnr.nextLine();
                whatToEdit = whatToEdit.toLowerCase();
                if (whatToEdit.equals("label")){
                    System.out.println("Type in what you want to change the label to: ");
                    theEdit = scnr.nextLine();
                    account.get(i).setLabel(theEdit);
                    break;
                }
                    
                if (whatToEdit.equals("username")){
                    System.out.println("Type in what you want to change the username to: ");
                    theEdit = scnr.nextLine();
                    account.get(i).setUsername(theEdit);
                    break;
                }
                    
                if (whatToEdit.equals("password")){
                    System.out.println("Type in what you want to change the password to: ");
                    theEdit = scnr.nextLine();
                    account.get(i).setPassword(theEdit);
                    break;
                }
                    
                if (whatToEdit.equals("category")){
                    System.out.println("Type in what you want to change the category to: ");
                    theEdit = scnr.nextLine();
                    account.get(i).setCategory(theEdit); 
                    break;
                }
                    
                if (whatToEdit.equals("url")){
                    System.out.println("Type in what you want to change the url to: ");
                    theEdit = scnr.nextLine();
                    account.get(i).setUrl(theEdit);
                    break;
                } 
                
                if (whatToEdit.equals("description")){
                    System.out.println("Type in what you want to change the description to: ");
                    theEdit = scnr.nextLine();
                    account.get(i).setDescription(theEdit);
                    break;
                }
                    
                if (whatToEdit.equals("security question")){
                    System.out.println("Do you want to change the question or the answer?");
                    oneOrOther = scnr.nextLine();
                    oneOrOther = oneOrOther.toLowerCase();
                    
                    if(oneOrOther.equals("question")){
                        System.out.println("Which question? (number): ");
                        whichOne = scnr.nextInt();
                        whichOne = whichOne - 1;
                        System.out.println("Type in what to change the question");
                        theEdit = scnr.nextLine();
                        System.out.println(theEdit);
                        sq.get(whichOne).setQuestion(theEdit);
                    }
                    
                    if(oneOrOther.equals("answer")){
                        System.out.println("Which answer? (number): ");
                        whichOne = scnr.nextInt();
                        whichOne = whichOne - 1;
                        System.out.println("Type in what to change the answer");
                        theEdit = scnr.nextLine();
                        System.out.println(theEdit);
                        sq.get(whichOne).setAnswer(theEdit);
                    }
                    else{
                        System.out.println("Nothing change/Wrong input");
                    }
                    
                    break;
                }
                    
                if (whatToEdit.equals("additional property")){
                    System.out.println("Do you want to change the property or the property answer?");

                    oneOrOther = scnr.nextLine();
                    oneOrOther = oneOrOther.toLowerCase();
                    if(oneOrOther.equals("property")){
                        System.out.println("Which property? (number): ");
                        whichOne = scnr.nextInt();
                        whichOne = whichOne - 1;
                        System.out.println("Type in what to change the question");
                        theEdit = scnr.nextLine();
                        ap.get(whichOne).setProperty(theEdit);
                    }
                    
                    if(oneOrOther.equals("property answer")){
                        System.out.println("Which property answer? (number): ");
                        whichOne = scnr.nextInt();
                        whichOne = whichOne - 1;
                        System.out.println("Type in what to change the answer");
                        theEdit = scnr.nextLine();
                        ap.get(whichOne).setPropertyAnswer(theEdit);
                    }
                    else{
                        System.out.println("Nothing change/Wrong input");
                    }
                    
                    break;
                }
            }
            else{
            if (i == account.size() - 1){
            System.out.println("Account not found/No account was edited.");
            }
        }
        }
        
        String dbString = gson.toJson(db, Database.class);
        
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
