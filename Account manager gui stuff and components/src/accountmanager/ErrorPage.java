package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: Error Page
 * 
 * Purpose: An error page that is displayed when a database is not loaded
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
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
import javafx.scene.control.Label;
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



public class ErrorPage
{
    //attrib
    private Scene scene;
    
    //constructor
    public ErrorPage() //throws IOException
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
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Title
        Text scenetitle = new Text("Error!");
        scenetitle.setFill(Color.FIREBRICK);
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
        grid.add(scenetitle, 0, 0, 2, 1);
        //Database name
        //Label dbName = new Label("Database Name:");
        
        final Text displayText = new Text();
        //set text color
        displayText.setFill(Color.FIREBRICK);
        displayText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 12));
        grid.add(displayText, 0, 1);
        
        //display error message
        displayText.setText("Error! No database has been opened or created!");
        
        
        //the scene
        StackPane root = new StackPane();
        root.getChildren().add(grid); //added all the grid elements to root
        
        scene = new Scene(root, 400, 400);//displaying scene with everything in root
        
        stage.setResizable(true);
        
        return scene;
    }
    
}
