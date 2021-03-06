package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: Create Database
 * 
 * Purpose: To create a gui to create a new database for the account manager. It will ask for the
 * name of the account, the location, and master password. Contents will be encrypted.
 * Thanks to Oracle.com and StackOverflow.com for examples
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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



public class CreateDatabase
{
    //attrib
    private Scene scene;
    
    //constructor
    public CreateDatabase() //throws IOException
    {
        
    }
    
//**STAGE**********************************************************************    
    public Scene openScene(Stage stage) 
    {        
        //form area
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        //make account manager instance to get db name back to main stage
        AccountManager account = new AccountManager();
        
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
        
        TextField genField = new TextField();
        genField.setVisible(false);
        grid.add(genField, 2,3);

        //make a generate password button
        Button genPassBtn = new Button();
        genPassBtn.setText("Generate Password");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn3.getChildren().add(genPassBtn);
        grid.add(hbBtn3, 2, 2);
        final Text genPress = new Text();
        grid.add(genPress, 2, 3);

        genPassBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                Random r = new Random();
                r.setSeed(System.nanoTime());
                int length = r.nextInt(10) + 8;
                //generate random password
                PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                        .useDigits(true)
                        .useLower(true)
                        .useUpper(true)
                        .usePunctuation(false)
                        .build();

                String randomPassword = passwordGenerator.generate(length);
                
                //set password to fields 
                pwBox.setText(randomPassword);
                pwCBox.setText(randomPassword);
                
                //set text color
                genPress.setFill(Color.FIREBRICK);
                
                //set password to field
                genField.setText(randomPassword);
                genField.setStyle("-fx-text-fill: red;");
                
                //reveal password - You need to be able to jot down the master password right?
                genField.setVisible(true);
                genField.setEditable(false);
            }
        });
        
        //fileLocation
        //Database name
        Label fileName = new Label("File Location:");
        grid.add(fileName, 0, 4);
        
        TextField fileField = new TextField();
        fileField.setPrefWidth(200);
        grid.add(fileField, 1, 4);
        
        //Browse files button
        Button fileBtn = new Button();
        fileBtn.setText("Browse");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn2.getChildren().add(fileBtn);
        grid.add(hbBtn2, 2, 4);
        final Text filePress = new Text();
        grid.add(filePress, 2, 5);
        
        fileBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                  
                  //variables
                  Scanner inputFile = null;    //input file scanner
                  FileInputStream fin = null;  //input file stream
                  File file = null;
                  String f = "NULL";
                  String nInput = nameTextField.getText();
                  String name = "New Database";
                  if(nInput.isEmpty())
                  {
                    name = "New Database";
                  }
                  else
                  {
                      name = nInput;
                  }
                  
                  //save account dialogue
                  SaveDirectory save = new SaveDirectory();
                  file = save.saveFile(stage,name);
                  f = file.getPath();
                  
                  fileField.setText(f);
                    
            }
        });
        
        //Submit button
        Button createBtn = new Button();
        createBtn.setText("Create Database");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(createBtn);
        grid.add(hbBtn, 1, 5);
        final Text submitPress = new Text();
        grid.add(submitPress, 1, 6);
        
        //make new database object for the json file
        Database db = new Database();
        //button action
        createBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                //Variables
                String nameInput = nameTextField.getText();    // get db name
                String passWordInput = pwBox.getText(); // get password
                String passWordConfirm = pwCBox.getText(); //get confirm password
                String fileInput = fileField.getText();    // get file name
                
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
                else if(fileInput.isEmpty())
                {
                    submitPress.setText("Please enter file name and location!");
                }
                //if everything matches up, create a new account class and add to database
                else
                {
                    //set master password in db object
                    db.setMasterpassword(passWordInput);
                    
                    //convert that to json
                    Gson gson = new Gson();
                    String json = gson.toJson(db);
                    
                    PrintWriter writer = null;
                    try 
                    {
                        writer = new PrintWriter(fileInput, "UTF-8"); //think about using json/xml file
                    } 
                    catch (FileNotFoundException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (UnsupportedEncodingException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //copy the master password back to main stage
                    account.getMasterPassword(passWordInput);
                    
                    //encrypt contents
                    try
                    {
                    Encrypt e = new Encrypt();
                    json = e.encrypt(json, passWordInput);
                    
                    }
                    catch(NoSuchAlgorithmException ne)
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ne);
                    }
                    catch(NoSuchPaddingException p)
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, p);
                    }
                    catch(InvalidKeyException i)
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, i);
                    }
                    catch(IllegalBlockSizeException ib)
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ib);
                    }
                    catch(BadPaddingException bp)
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, bp);
                    }
                    writer.print(json);
                    //writer.print(nameInput);
                            
                    writer.close();
                    
                    //clear last used file
                    try
                    {
                    PrintWriter w = new PrintWriter("LastUsedFile.dat");
                    w.print("");
                    w.close();
                    }
                    catch (FileNotFoundException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Save the last used file location?
                    PrintWriter w = null;
                    try 
                    {
                        w = new PrintWriter("LastUsedFile.dat", "UTF-8"); //think about using json/xml file
                    } 
                    catch (FileNotFoundException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (UnsupportedEncodingException ex) 
                    {
                        Logger.getLogger(CreateDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    w.print(fileInput);
                    
                    w.close();
                    
                    //status message
                    submitPress.setText("Database Created");
                    
                    //set created boolean
                    account.isCreated(true);
                    
                    //copy the db back to main stage
                    account.getDB(fileInput);                                      
                    
                    //close the stage
                    stage.close();
                }
            }
        });
        
        
        //the scene
        StackPane root = new StackPane();
        root.getChildren().add(grid); //added all the grid elements to root
        
        scene = new Scene(root, 550, 400);//displaying scene with everything in root
        
        stage.setResizable(true);
        
        return scene;
    }
    
}
