package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Professional x64 edition - Netbeans 8
 *Date: 04/29/2019
 *Project: DeleteProperty
 * 
 * Purpose: To Delete a property
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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



public class DeleteProperty
{
    //attrib
    private Scene scene;
    
    //constructor
    public DeleteProperty() //throws IOException
    {
        
    }
    
//**STAGE**********************************************************************    
    public Scene openScene(Stage stage, String file, String masterPass, Account a, int aTarget, AdditionalProperties ap, int apTarget) 
    {
        
        //form area        s
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        //make account manager instance to do stuff in the main window from here
        AccountManager account = new AccountManager();
        account.setPropStatusBar("Property not Deleted");
        
        //Title
        Text scenetitle = new Text("Do you really want to delete this property?");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //A radio button with an empty string for its label
        RadioButton rb1 = new RadioButton("Yes");
        grid.add(rb1, 1, 2);
        //Setting a text label
        //A radio button with the specified label
        RadioButton rb2 = new RadioButton("No");
        grid.add(rb2, 1, 3);
        
        //Submit button
        Button Btn = new Button();
        Btn.setText("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(Btn);
        grid.add(hbBtn, 1, 8);
        final Text submitPress = new Text();
        grid.add(submitPress, 1, 9);

        //button action
        Btn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                // set text color
                submitPress.setFill(Color.FIREBRICK);

                //Choose yes or no based on radio button
                if (rb1.isSelected())
                {

                    //remove the property from the list
                    account.deleteProperty(aTarget, apTarget);

                    //Do this to clear a file?
                    try
                    {
                        PrintWriter w = new PrintWriter(file);
                        w.print("");
                        w.close();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(DeleteProperty.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //append edited account to DB file
                    PrintWriter writer = null;
                    try
                    {
                        FileWriter fw = new FileWriter(file, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        writer = new PrintWriter(bw); //think about using json/xml file
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(DeleteProperty.class.getName()).log(Level.SEVERE, null, ex);
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
                                accountList.get(i).getSecurityQuestion(), accountList.get(i).getAdditionalProperties());
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
                        Logger.getLogger(DeleteProperty.class.getName()).log(Level.SEVERE, null, ne);
                    }
                    catch (NoSuchPaddingException p)
                    {
                        Logger.getLogger(DeleteProperty.class.getName()).log(Level.SEVERE, null, p);
                    }
                    catch (InvalidKeyException i)
                    {
                        Logger.getLogger(DeleteProperty.class.getName()).log(Level.SEVERE, null, i);
                    }
                    catch (IllegalBlockSizeException ib)
                    {
                        Logger.getLogger(DeleteProperty.class.getName()).log(Level.SEVERE, null, ib);
                    }
                    catch (BadPaddingException bp)
                    {
                        Logger.getLogger(DeleteProperty.class.getName()).log(Level.SEVERE, null, bp);
                    }
                    writer.print(json);

                    writer.close();
                    account.setPropStatusBar("Property Deleted");
                }
                else
                {
                    //set status bar
                    account.setPropStatusBar("Property not Deleted");
                }

                //close the stage
                stage.close();
            }
        });
        
        
        //the scene
        StackPane root = new StackPane();
        root.getChildren().add(grid); //added all the grid elements to root
        
        scene = new Scene(root, 500, 400);//displaying scene with everything in root
        
        stage.setResizable(true);
        
        return scene;
    }
    
}
