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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
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
        //check for last used file
        FileInputStream last = null;
        Scanner lastUsed = null;
        File file = new File(System.getenv("SystemDrive")); //The windows system root drive as default. No Macs allowed!
        File lastPath = new File("LastUsedFile.dat");
        Path path = lastPath.toPath();

        if (Files.exists(path))
        {
            try
            {
                last = new FileInputStream("LastUsedFile.dat");
                lastUsed = new Scanner(last);
            }
            catch (FileNotFoundException e)
            {

            }
            String lastFile = lastUsed.nextLine();
            File l = new File(lastFile);
            Path filepath = l.toPath();
            if (Files.exists(filepath))
            {
                file = new File(lastFile);
                lastFile = lastFile.substring(0, lastFile.lastIndexOf(File.separator));
                file = new File(lastFile);
            }
        }
        FileChooser f = new FileChooser();
        f.setTitle("Save Database");
        f.setInitialDirectory(file);
        f.setInitialFileName(name + ".json");
        file = f.showSaveDialog(stage);
        
        return file;
    }
    
}
