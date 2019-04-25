package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 04/01/2019
 *Project: SaveDirectory
 * 
 * To save a directory
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class SaveDirectory
{
    //attrib
    private Scene scene;
    
    //constructor
    public SaveDirectory() 
    {
        
    }
    
//**STAGE**********************************************************************    
    public File saveFile(Stage stage, String name) 
    {
        
        FileChooser f = new FileChooser();
        File file = new File("C:\\Users\\Zylski\\Documents\\AccountManager Databases");
        f.setTitle("Save Database");
        f.setInitialDirectory(file);
        f.setInitialFileName(name + ".json");
        file = f.showSaveDialog(stage);
        
        return file;
    }
    
}
