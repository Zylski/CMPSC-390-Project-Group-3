package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 03/27/2019
 *Project: Edit account
 * 
 * Purpose: To edit an existing account
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class DeleteAccount
{
    //attrib
    private Scene scene;
    //constructor
    public DeleteAccount()
    {
        
    }
    
//**STAGE**********************************************************************    
    public Scene openScene(Stage stage, String fileName, String masterPass, Account a,int target) 
    {
        
        //form area        s
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        
        //Title
        Text scenetitle = new Text("Do you really want to delete this account?");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        AccountManager account = new AccountManager();

        //A radio button with an empty string for its label
        RadioButton rb1 = new RadioButton("Yes");
        grid.add(rb1, 1, 2);
        //Setting a text label
        //A radio button with the specified label
        RadioButton rb2 = new RadioButton("No");
        grid.add(rb2, 1, 3);
        
        
        //Submit button
        Button submit = new Button();
        submit.setText("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(submit);
        grid.add(hbBtn, 1, 8);
        final Text submitPress = new Text();
        grid.add(submitPress, 1, 9);
        submit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                
                //set text color
                submitPress.setFill(Color.FIREBRICK);
                
                //Choose yes or no based on radio button
                if(rb1.isSelected())
                {
                    //account.deleteChoice(true);
                    
                    //remove the account from the list
                    account.getAccountList().remove(target);

                    //Do this to clear a file?
                    try
                    {
                        PrintWriter w = new PrintWriter(fileName);
                        w.print("");
                        w.close();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //append edited account to DB file
                    PrintWriter writer = null;
                    try
                    {
                        FileWriter fw = new FileWriter(fileName, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        writer = new PrintWriter(bw); //think about using json/xml file
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //get accountList
                    ArrayList<Account> accountList = new ArrayList<Account>();
                    accountList = account.getAccountList();

                    //make new gson object
                    Gson gson = new Gson();

                    //make new db object
                    Database db = new Database();

                    //set master password
                    db.setMasterpassword(masterPass);

                    //create a new account object for json files
                    JsonAccount a = new JsonAccount();

                    //set accounts in db
                    for (int i = 0; i < accountList.size(); i++)
                    {
                        a = new JsonAccount(accountList.get(i).getLabel(), accountList.get(i).getUsername(), accountList.get(i).getPassword(),
                                accountList.get(i).getUrl(), accountList.get(i).getDescription(), accountList.get(i).getCategory(),
                                accountList.get(i).getSecurityQuestion(),accountList.get(i).getAdditionalProperties());
                        db.addAccount(a);
                    }

                    //make new json string
                    String json = gson.toJson(db, Database.class);

                    //encrypt contents
                    try
                    {
                        Encrypt e = new Encrypt();
                        json = e.encrypt(json, masterPass);
                    }
                    catch (NoSuchAlgorithmException ne)
                    {

                    }
                    catch (NoSuchPaddingException p)
                    {

                    }
                    catch (InvalidKeyException i)
                    {

                    }
                    catch (IllegalBlockSizeException ib)
                    {

                    }
                    catch (BadPaddingException bp)
                    {

                    }
                    writer.print(json);

                    writer.close();
                    account.setStatusBar("Account Deleted");
                }
                else
                {
                    //account.deleteChoice(false);
                    account.setStatusBar("Account not Deleted");
                }

                    //close the stage
                    stage.close();
                
            }
        });
        
        
        //the scene
        StackPane root = new StackPane();
        root.getChildren().add(grid); //added all the grid elements to root
        
        scene = new Scene(root, 400, 400);//displaying scene with everything in root
        
        stage.setResizable(true);
        
        return scene;
    }

}
