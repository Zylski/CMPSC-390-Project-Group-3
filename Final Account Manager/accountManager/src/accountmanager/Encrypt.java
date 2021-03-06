package accountmanager;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/10/2019
 *Project: Encrypt
 * 
 * To encrypt a string, based on some code from Bart
 * 
 * -----------------------------------------------------------------------------------
 */

//class account

public class Encrypt 
{
    
    
    //constructors
    public Encrypt() //defualt
    {
        
    }
    
    public String encrypt(String text, String password)throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
      SecretKeySpec skeyspec = new SecretKeySpec(password.getBytes(), "Blowfish");
      Cipher cipher = Cipher.getInstance("Blowfish");
      
      cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
      
      byte[] encrypted = cipher.doFinal(text.getBytes());
      
      return Base64.getEncoder().encodeToString(encrypted);
    }
    
}
