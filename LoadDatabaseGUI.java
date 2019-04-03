package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: Load Database
 * 
 * To load or open an already created database account
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class LoadDatabase
{
    //attrib
    private Scene scene;
    
    //constructor
    public LoadDatabase() 
    {
        
    }
    
//**STAGE**********************************************************************    
    public File openFile(Stage stage) 
    {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Database");
        File file = fileChooser.showOpenDialog(stage);
        
        return file;
    }
    
}
