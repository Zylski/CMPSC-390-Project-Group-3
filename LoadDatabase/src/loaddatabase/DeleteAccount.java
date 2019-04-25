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
public class DeleteAccount {
    
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
        
        System.out.println("Type in name of the account you wish to delete: ");
        String accountLabel = scnr.nextLine();
        
        ArrayList<Account> account = new ArrayList<Account>();
        
        account = db.getAccount();
        
        for(int i = 0;i<account.size();i++)
		{
            if (account.get(i).compareToString(accountLabel) == true){
                account.remove(i);
                System.out.println("Account removed");
            }
            else
			{
            if (i == account.size() - 1)
			{
				System.out.println("Account not found/No account was deleted.");
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
