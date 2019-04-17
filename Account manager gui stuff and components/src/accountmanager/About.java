package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: About Page
 * 
 * Purpose: An about page showing credits and stuff
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;



public class About
{
    //attrib
    private Scene scene;
    
    //constructor
    public About() //throws IOException
    {
        
    }
    
//**STAGE**********************************************************************    
    public Scene openScene(Stage stage) 
    {
        
        //form area        s
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(1);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        VBox vbox = new VBox();
        
        //Title
        Text scenetitle = new Text("Account Manager version 0.0001");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //setup image
        ImageView imageView = new ImageView();
        File file = new File("resources/shinra.jpg");
        Image image = new Image(getClass().getResourceAsStream("resources/shinra1.jpg"));
        imageView.setImage(image);
        grid.add(imageView, 0, 1);
        
        final Text displayText = new Text();
        //set text color
        displayText.setFont(Font.font("Lucida Console", FontWeight.BOLD, 12));
        grid.add(displayText, 0, 2);
        
        //display error message
        displayText.setText("By Damian Zylski and Barry Hoinacki\nCopyright 2019");
        
        
        //the scene
        StackPane root = new StackPane();
        //vbox.getChildren().addAll(scenetitle,imageView,displayText); //added all the  elements to root
        root.getChildren().add(grid); //added all the grid elements to root
        
        scene = new Scene(root, 400, 400);//displaying scene with everything in root
        
        stage.setResizable(false);
        
        return scene;
    }
    
}
