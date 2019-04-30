package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Professional x64 edition - Netbeans 8
 *Date: 04/29/2019
 *Project: AddQuestion
 * 
 * Purpose: To add a security question and it's answer to a selected account
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



public class AddQuestion
{
    //attrib
    private Scene scene;
    
    //constructor
    public AddQuestion() //throws IOException
    {
        
    }
    
//**STAGE**********************************************************************    
    public Scene openScene(Stage stage, String file, String masterPass, Account a, int target) 
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
        
        //Title
        Text scenetitle = new Text("Add Security Question");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //text area for questions
        Text areaLabel = new Text("Question");
        TextArea area = new TextArea();
        area.wrapTextProperty().set(true);
        grid.add(areaLabel, 0, 1);
        grid.add(area, 0, 2);
        
        //text field for answers
        Text fieldLabel = new Text("Answer");
        TextField field = new TextField();
        grid.add(fieldLabel, 0, 3);
        grid.add(field, 0, 4);
        
        
        //Submit button
        Button createBtn = new Button();
        createBtn.setText("Add Security Question");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(createBtn);
        grid.add(hbBtn, 0, 7);
        final Text submitPress = new Text();
        grid.add(submitPress, 0, 8);

        //button action
        createBtn.setOnAction(new EventHandler<ActionEvent>() 
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
                    submitPress.setText("Please enter a question!");
                }
                else if(fieldInput.isEmpty())
                {
                    submitPress.setText("Please enter an answer to the question!");
                }
                //if everything matches up, create a new account class and add to database
                else
                {
                    //make new database object for the json file
                    Database db = new Database();
                    
                    //make new Security Question object
                    SecurityQuestion sq = new SecurityQuestion(areaInput, fieldInput);
                    
                    //add security question to account list
                    account.addQuestion(sq, target);
                    
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
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (UnsupportedEncodingException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //encrypt contents
                    try
                    {
                    Encrypt e = new Encrypt();
                    json = e.encrypt(json, masterPass);
                    
                    }
                    catch(NoSuchAlgorithmException ne)
                    {
                        
                    }
                    catch(NoSuchPaddingException p)
                    {
                        
                    }
                    catch(InvalidKeyException i)
                    {
                        
                    }
                    catch(IllegalBlockSizeException ib)
                    {
                        
                    }
                    catch(BadPaddingException bp)
                    {
                        
                    }
                    writer.print(json);
                            
                    writer.close();
                    
                    //status message
                    submitPress.setText("Database Created");

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
