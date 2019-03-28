package addaccount;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 03/27/2019
 *Project: Add account
 * 
 * Purpose: To create a gui to collect user information for adding a new account to
 * the database. Thanks to Oracle.com and StackOverflow.com for examples
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
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


public class AddAccount extends Application
{
//**STAGE**********************************************************************    
    @Override
    public void start(Stage primaryStage)
    {
        
        //form area        
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Title
        Text scenetitle = new Text("Add Account");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        //Account label
        Label accountLabel = new Label("Account Label:");
        grid.add(accountLabel, 0, 1);

        TextField labelTextField = new TextField();
        grid.add(labelTextField, 1, 1);
        //UserName
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 2);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);
        //Password
        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);
        //reenter password
        Label pwC = new Label("Confirm Password:");
        grid.add(pwC, 0, 4);

        PasswordField pwCBox = new PasswordField();
        grid.add(pwCBox, 1, 4);
        //URL
        Label urlLabel = new Label("URL/Location:");
        grid.add(urlLabel, 0, 5);

        TextField urlField = new TextField();
        grid.add(urlField, 1, 5);
        //Description
        Label descLabel = new Label("Description:");
        grid.add(descLabel, 0, 6);

        TextArea descTextField = new TextArea();
        descTextField.setPrefHeight(100);  
        descTextField.setPrefWidth(200);   
        grid.add(descTextField, 1, 6);
        
        //Submit button
        Button submit = new Button();
        submit.setText("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(submit);
        grid.add(hbBtn, 1, 7);
        final Text submitPress = new Text();
        grid.add(submitPress, 1, 8);
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
                    //TEMP Display input fields
                    submitPress.setText("Account Added\n" + labelInput + 
                            "\n" + userNameInput + "\n" + passWordInput + "\n"
                    + passWordConfirm + "\n" + urlInput + "\n" + description);
                }
            }
        });
        
        
        //the scene
        StackPane root = new StackPane();
        root.getChildren().add(grid); //added all the grid elements to root
        
        Scene scene = new Scene(root, 500, 500);//displaying scene with everything in root
        
        primaryStage.setTitle("Add Account");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//**Main*****************************************************************
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
