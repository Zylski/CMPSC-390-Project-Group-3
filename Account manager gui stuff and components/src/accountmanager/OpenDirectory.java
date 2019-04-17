package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: OpenDirectory
 * 
 * To open a directory
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class OpenDirectory
{
    //attrib
    private Scene scene;
    
    //constructor
    public OpenDirectory() 
    {
        
    }
    
//**STAGE**********************************************************************    
    public File openFile(Stage stage) 
    {
        
        FileChooser fileChooser = new FileChooser();
        File file = new File("C:\\Users\\Zylski\\Documents\\AccountManager Databases");
        fileChooser.setTitle("Open Database");
        fileChooser.setInitialDirectory(file);
        file = fileChooser.showOpenDialog(stage);
        
        return file;
    }
    
}
