/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaddatabase;

import com.google.gson.Gson;
import java.io.File;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import javax.xml.bind.DatatypeConverter;
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
public class LoadDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Type in name for database you wish to load m'lord: ");
        String databasename = scnr.nextLine();//Add if-else to check whether or not file exists later on
        
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
        
        db.writeOutput();
       

    
}
}
