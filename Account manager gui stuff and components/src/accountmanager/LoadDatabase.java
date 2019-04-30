package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: LoadDatabase
 * 
 * To load a database
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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



public class LoadDatabase
{
    //attrib
    private Scene scene;
    
    //constructor
    public LoadDatabase() 
    {
        
    }
     
    //**STAGE**********************************************************************    
    public Scene openScene(Stage stage) 
    {
        
        //form area        s
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        ErrorKey error = new ErrorKey();
        Scene errorScene = error.openScene(stage);
        
        //bad password check
        boolean passCheck = true;
        
        //to access arraylist
        AccountManager accountList = new AccountManager();
        
        //Title
        Text scenetitle = new Text("Load Database");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //Password
        Label pw = new Label("Please enter Master Password:");
        grid.add(pw, 0, 1);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 1);
        
        //fileLocation
        Label dbName = new Label("File Location:");
        grid.add(dbName, 0, 2);
        
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 2);
        
        //Browse files button
        Button fileBtn = new Button();
        fileBtn.setText("Browse");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(fileBtn);
        grid.add(hbBtn2, 2, 2);
        final Text filePress = new Text();
        grid.add(filePress, 2, 3);
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
                  
                  //load account
                  OpenDirectory open = new OpenDirectory();
                  file = open.openFile(stage);
                  f = file.getPath();
                  
                  nameTextField.setText(f);
                    
            }
        });
        
        //Submit button
        Button createBtn = new Button();
        createBtn.setText("Load Database");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(createBtn);
        grid.add(hbBtn, 1, 3);
        final Text submitPress = new Text();
        grid.add(submitPress, 1, 4);
        createBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                
                //Variables
                String nameInput = nameTextField.getText();    // get db name
                String passWordInput = pwBox.getText(); // get password
                //variables
                  Scanner inputFile = null;    //input file scanner
                  FileInputStream fin = null;  //input file stream
                  File file = null;
                  String f = nameInput;
                
                //set text color
                submitPress.setFill(Color.FIREBRICK);
                
                //Check if required fields are input or password fields don't match
                
                if(nameInput.isEmpty())
                {
                    submitPress.setText("Please enter file location!");
                }
                else if(passWordInput.isEmpty())
                {
                    submitPress.setText("Please enter the Master Password!");
                }
                //if everything matches up, load account
                else
                {
                    //open database file
                    String dbStringD = "";
                    try
                    {
                        System.out.println("Reading " + f +  "...");
                        fin = new FileInputStream(f);
                        file = new File(f);
                        inputFile = new Scanner(fin);
                        dbStringD = new String(Files.readAllBytes(file.toPath()));
                    }
                    catch(IOException e)
                    {
                        System.out.println("File is missing or corrupt!");
                    }
                    
                    //make new gson object
                    Gson gson = new Gson();
                    
                    
                    
                    String encrypted = inputFile.nextLine();
                    String decrypted = encrypted;
                    
                    

                    //TO DO ask for password
                    
                    try
                    {
                    Decrypt d = new Decrypt();
                    decrypted = d.decrypt(encrypted, passWordInput);
                    }
                    catch(NoSuchAlgorithmException ne)
                    {
                        System.out.println("bad key!");
                            stage.close();
                            stage.setScene(errorScene);
                            stage.showAndWait();
                    }
                    catch(NoSuchPaddingException p)
                    {
                        System.out.println("bad key!");
                            stage.close();
                            stage.setScene(errorScene);
                            stage.showAndWait();
                    }
                    catch(InvalidKeyException i)
                    {
                        System.out.println("bad key!");
                        stage.close();
                        stage.setScene(errorScene);
                           stage.showAndWait();
                    }
                    catch(IllegalBlockSizeException ib)
                    {
                        System.out.println("bad key!");
                            stage.close();
                            stage.setScene(errorScene);
                            stage.showAndWait();
                    }
                    catch(BadPaddingException bp)
                    {
                        System.out.println("bad key!");
                            stage.close();
                            stage.setScene(errorScene);
                            stage.showAndWait();
                    }
                    
                    //make strings
                    String label = "NULL";
                    String username = "NULL";
                    String password = "NULL";
                    String url = "NULL";
                    String description = "NULL";
                    String category = "NULL";
                    ArrayList<SecurityQuestion> sq = null;
                    ArrayList<AdditionalProperties> ap = null;
                    
                    //decrypt and add accounts to arraylist
                    
                    if((!decrypted.equals(encrypted)))
                    {
                        //clear old arraylist contents
                        accountList.clearAccount();
                    
                        //make new database object
                        Database db = gson.fromJson(decrypted,Database.class);
                        
                        //Add accounts back to main ArrayList
                        for(int i = 0; i < db.getAccount().size(); i++)
                        {
                            label = db.getAccount().get(i).getLabel();
                            username = db.getAccount().get(i).getUsername();
                            password = db.getAccount().get(i).getPassword();
                            url = db.getAccount().get(i).getUrl();
                            description = db.getAccount().get(i).getDescription();
                            category = db.getAccount().get(i).getCategory();
                            sq = db.getAccount().get(i).getSecurityQuestion();
                            ap = db.getAccount().get(i).getAdditionalProperties();
                            
                            //add to account list
                            accountList.addAccount(label, username, password, url, description, category, sq, ap);
                        }
                        
                        
                     
                    }
                    if(!decrypted.equals(encrypted))
                    {
                    accountList.setStatusBar("Database Loaded");
                    
                    //copy the db back to main stage
                    accountList.getDB(nameInput);
                    
                    //copy the master password back to main stage
                    accountList.getMasterPassword(passWordInput);
                    
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

                        w.print(f);

                        w.close();
                    }
                    
                    //close the file
                    try
                    {
                        fin.close();
                        inputFile.close();
                    }
                    catch(IOException e)
                    {
                        System.out.println("File is missing or corrupt!");
                    }
 
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
        

