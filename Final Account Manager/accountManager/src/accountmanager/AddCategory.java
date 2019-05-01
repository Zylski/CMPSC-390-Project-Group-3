package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/24/2019
 *Project: Add Category
 * 
 * Purpose: To add a category to the account manager
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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



public class AddCategory
{
    //attrib
    private Scene scene;
    
    //constructor
    public AddCategory() //throws IOException
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
        
        //make account manager instance to get db name back to main stage
        AccountManager account = new AccountManager();
        
        //Title
        Text scenetitle = new Text("Add Category");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        //Database name
        Label catName = new Label("Category Name:");
        grid.add(catName, 0, 1);

        TextField catInput = new TextField();
        grid.add(catInput, 1, 1);
        
        //Submit button
        Button createBtn = new Button();
        createBtn.setText("Add Category");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(createBtn);
        grid.add(hbBtn, 1, 2);
        final Text submitPress = new Text();
        grid.add(submitPress, 1, 3);
        createBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                //Variables
                String category = catInput.getText();    // get category name
                
                //set text color
                submitPress.setFill(Color.FIREBRICK);
                
                //Check if required fields are input or password fields don't match
                
                if(category.isEmpty())
                {
                    submitPress.setText("Please enter category name!");
                }
                //if everything matches up, add the new category to account manager
                else
                {
                    //transfer new category name back to account manager
                    account.getCategory(category);

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
