package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: Add account
 * 
 * Purpose: To create a gui to create a new database for the account manager. It will ask for the
 * name of the account, the location, and master password. Contents will be encrypted.
 * Thanks to Oracle.com and StackOverflow.com for examples
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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



public class CreateDatabase
{
    //attrib
    private Scene scene;
    
    //constructor
    public CreateDatabase() //throws IOException
    {
        
    }
    
//**STAGE**********************************************************************    
    public Scene openScene(Stage stage) //throws IOException
    {
        
        //form area        s
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Title
        Text scenetitle = new Text("Create Database");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        //Database name
        Label dbName = new Label("Database Name:");
        grid.add(dbName, 0, 1);

        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);
        
        //Password
        Label pw = new Label("Master Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        //reenter password
        Label pwC = new Label("Confirm Master Password:");
        grid.add(pwC, 0, 3);

        PasswordField pwCBox = new PasswordField();
        grid.add(pwCBox, 1, 3);
        
        //Submit button
        Button createBtn = new Button();
        createBtn.setText("Create Database");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(createBtn);
        grid.add(hbBtn, 1, 7);
        final Text submitPress = new Text();
        grid.add(submitPress, 1, 8);
        createBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //Variables
                String nameInput = nameTextField.getText();    // get db name
                String passWordInput = pwBox.getText(); // get password
                String passWordConfirm = pwCBox.getText(); //get confirm password
                
                //set text color
                submitPress.setFill(Color.FIREBRICK);
                
                //Check if required fields are input or password fields don't match
                
                if(nameInput.isEmpty())
                {
                    submitPress.setText("Please enter Database Name!");
                }
                else if(passWordInput.isEmpty())
                {
                    submitPress.setText("Please enter the Master Password!");
                }
                else if(passWordConfirm.isEmpty())
                {
                    submitPress.setText("Please enter the Confirmed Master Password!");
                }
                else if(!passWordInput.equals(passWordConfirm))
                {
                    submitPress.setText("Passwords do not match!");
                }
                //if everything matches up, create a new account class and add to database
                else
                {
                    //create database file
                    PrintWriter writer = null;
                    try 
                    {
                        writer = new PrintWriter(nameTextField.getText() + ".dat", "UTF-8"); //think about using json/xml file
                    } 
                    catch (FileNotFoundException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (UnsupportedEncodingException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    writer.println("master password = " + pwBox.getText());
                            writer.println("");
                    writer.close();
                    
                     
                    

                    try 
                    {
                        File f = new File(nameTextField.getText() + ".dat");
                        byte[] fileContent = Files.readAllBytes(f.toPath());
                        //put byte into this to get encoded string
                        String encoded = Base64.getEncoder().encodeToString(fileContent);
                        writer = new PrintWriter(nameTextField.getText() + ".dat", "UTF-8"); //think about using json/xml file
                        
                        //encoded goes here
                        writer.println(encoded);
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    
                    
                    
                    writer.close();
                    submitPress.setText("Database Created");
                    //close the stage
                    stage.close();
                }
            }
        });
        
        
        //the scene
        StackPane root = new StackPane();
        root.getChildren().add(grid); //added all the grid elements to root
        
        scene = new Scene(root, 400, 400);//displaying scene with everything in root
        
        return scene;
    }
    
}
