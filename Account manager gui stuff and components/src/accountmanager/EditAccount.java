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
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class EditAccount
{
    //attrib
    private Scene scene;
    private static String fieldCategory = "General";
    //constructor
    public EditAccount()
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
        Text scenetitle = new Text("Edit Account");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        AccountManager account = new AccountManager();
        
        account.setStatusBar("Account not Edited");
        
        //Account label
        Label accountLabel = new Label("Account Label:");
        grid.add(accountLabel, 0, 1);

        TextField labelTextField = new TextField();
        grid.add(labelTextField, 1, 1);
        labelTextField.setText(a.getLabel());
        //UserName
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 2);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);
        userTextField.setText(a.getUsername());
        //Password
        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);
        pwBox.setText(a.getPassword());
        //reenter password
        Label pwC = new Label("Confirm Password:");
        grid.add(pwC, 0, 4);

        PasswordField pwCBox = new PasswordField();
        grid.add(pwCBox, 1, 4);
        pwCBox.setText(a.getPassword());
        //URL
        Label urlLabel = new Label("URL/Location:");
        grid.add(urlLabel, 0, 5);

        TextField urlField = new TextField();
        grid.add(urlField, 1, 5);
        urlField.setText(a.getUrl());
        //Category
        Label catLabel = new Label("Category:");
        grid.add(catLabel, 0, 6);

        TextField categoryField = new TextField();
        grid.add(categoryField, 1, 6);
        categoryField.setText(a.getCategory());
        
        
        //Description
        Label descLabel = new Label("Description:");
        grid.add(descLabel, 0, 7);

        TextArea descTextField = new TextArea();
        descTextField.setPrefHeight(100);  
        descTextField.setPrefWidth(200);   
        grid.add(descTextField, 1, 7);
        descTextField.setText(a.getDescription());
        
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
                //Variables
                String labelInput = labelTextField.getText();    // get label
                String userNameInput = userTextField.getText(); //get username
                String passWordInput = pwBox.getText(); // get password
                String passWordConfirm = pwCBox.getText(); //get confirm password
                String urlInput = urlField.getText(); //get url
                String description = descTextField.getText(); //get description
                String category = categoryField.getText(); //get category
                
                //set text color
                submitPress.setFill(Color.FIREBRICK);
                
                
                //Check if required fields are input or password fields don't match
                
                if(labelInput.isEmpty())
                {
                    submitPress.setText("Please enter Account name!");
                }
                else if(userNameInput.isEmpty())
                {
                    submitPress.setText("Please enter Username!");
                }
                else if(passWordInput.isEmpty())
                {
                    submitPress.setText("Please enter the Password!");
                }
                else if(passWordConfirm.isEmpty())
                {
                    submitPress.setText("Please enter the Confirmed Password!");
                }
                else if(!passWordInput.equals(passWordConfirm))
                {
                    submitPress.setText("Passwords do not match!");
                }
                //if everything matches up, create a new account class and add to database
                else
                {
                    //fill url and description fields with space if empty
                    if(urlInput.isEmpty())
                    {
                        urlInput = " ";
                    }
                    if(description.isEmpty())
                    {
                        description = " ";
                    }
                    if(category.isEmpty())
                    {
                        category = "General";
                    }
                    if(fieldCategory.equalsIgnoreCase("all") || fieldCategory.equalsIgnoreCase("categories"))
                    {
                        fieldCategory = "General";
                    }
                    categoryField.setText(fieldCategory);
                    //TEMP Display input fields
                    submitPress.setText("Account Edited\n");
                    
                    System.out.println(target);
                    
                    //edit said account!
                    account.editAccount(target, labelInput, userNameInput, passWordInput, urlInput, description, category);
                    
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
                    ArrayList <Account> accountList = new ArrayList <Account>();
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
                    for(int i = 0; i < accountList.size(); i++)
                    {
                        a = new JsonAccount(accountList.get(i).getLabel(),accountList.get(i).getUsername(),accountList.get(i).getPassword(),
                                accountList.get(i).getUrl(),accountList.get(i).getDescription(),accountList.get(i).getCategory(),
                                accountList.get(i).getSecurityQuestion(),accountList.get(i).getAdditionalProperties());
                        db.addAccount(a);
                    }

                    
                    //make new json string
                    String json = gson.toJson(db,Database.class);
                    
                    //encrypt contents
                    try
                    {
                    Encrypt e = new Encrypt();
                    json = e.encrypt(json,masterPass);
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
                    
                    account.setStatusBar("Account Edited");

                    //close the stage
                    stage.close();
                }
            }
        });
        
        
        //the scene
        StackPane root = new StackPane();
        root.getChildren().add(grid); //added all the grid elements to root
        
        scene = new Scene(root, 400, 400);//displaying scene with everything in root
        
        stage.setResizable(true);
        
        return scene;
    }
    
    //to set the default category in the text field
    public void setFieldCategory(String c)
    {
        this.fieldCategory = c;
    }
    
    
}
