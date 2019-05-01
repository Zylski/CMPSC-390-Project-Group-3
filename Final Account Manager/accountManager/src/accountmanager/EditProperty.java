package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Professional x64 edition - Netbeans 8
 *Date: 04/29/2019
 *Project: EditProperty
 * 
 * Purpose: To edit a property and it's value to a selected account
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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



public class EditProperty
{
    //attrib
    private Scene scene;
    
    //constructor
    public EditProperty() //throws IOException
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
        account.setPropStatusBar("Property not edited");
        
        //Title
        Text scenetitle = new Text("Edit Property");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //text area for questions
        Text areaLabel = new Text("Property");
        TextArea area = new TextArea();
        area.wrapTextProperty().set(true);
        grid.add(areaLabel, 0, 1);
        grid.add(area, 0, 2);
        area.setText(a.getAdditionalProperties().get(apTarget).getProperty());
        
        //text field for answers
        Text fieldLabel = new Text("Value");
        TextField field = new TextField();
        grid.add(fieldLabel, 0, 3);
        grid.add(field, 0, 4);
        field.setText(a.getAdditionalProperties().get(apTarget).getPropertyAnswer());
        
        
        //Submit button
        Button Btn = new Button();
        Btn.setText("Edit Property");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(Btn);
        grid.add(hbBtn, 0, 7);
        final Text submitPress = new Text();
        grid.add(submitPress, 0, 8);

        //button action
        Btn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                //Variables
                String areaInput = area.getText();    // get question
                String fieldInput = field.getText(); // get answer
                
                //set text color
                submitPress.setFill(Color.FIREBRICK);
                
                //Check if required fields are input or password fields don't match
                
                if(areaInput.isEmpty())
                {
                    submitPress.setText("Please enter a property!");
                }
                else if(fieldInput.isEmpty())
                {
                    submitPress.setText("Please enter a value for the property!");
                }
                //if everything matches up, edit the property
                else
                {
                    //make new database object for the json file
                    Database db = new Database();
                    
                    //make new Security Question object
                    AdditionalProperties ap = new AdditionalProperties(areaInput, fieldInput);
                    
                    //edit property in main account list
                    account.editProperty(ap, aTarget,apTarget);
                    
                    //give db current account list
                    db.setAccount(account.getAccountList());
                    
                    //convert that to json
                    Gson gson = new Gson();
                    String json = gson.toJson(db);
                    
                    PrintWriter writer = null;
                    try 
                    {
                        writer = new PrintWriter(file, "UTF-8"); //think about using json/xml file
                    } 
                    catch (FileNotFoundException ex) 
                    {
                        Logger.getLogger(EditProperty.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (UnsupportedEncodingException ex) 
                    {
                        Logger.getLogger(EditProperty.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //encrypt contents
                    try
                    {
                    Encrypt e = new Encrypt();
                    json = e.encrypt(json, masterPass);
                    
                    }
                    catch(NoSuchAlgorithmException ne)
                    {
                        Logger.getLogger(EditProperty.class.getName()).log(Level.SEVERE, null, ne);
                    }
                    catch(NoSuchPaddingException p)
                    {
                        Logger.getLogger(EditProperty.class.getName()).log(Level.SEVERE, null, p);
                    }
                    catch(InvalidKeyException i)
                    {
                        Logger.getLogger(EditProperty.class.getName()).log(Level.SEVERE, null, i);
                    }
                    catch(IllegalBlockSizeException ib)
                    {
                        Logger.getLogger(EditProperty.class.getName()).log(Level.SEVERE, null, ib);
                    }
                    catch(BadPaddingException bp)
                    {
                        Logger.getLogger(EditProperty.class.getName()).log(Level.SEVERE, null, bp);
                    }
                    writer.print(json);
                            
                    writer.close();
                    
                    //status message
                    submitPress.setText("Property Edited");
                    account.setPropStatusBar("Property edited");

                    //close the stage
                    stage.close();
                }
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
