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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            catch(FileNotFoundException e)
            {
                Logger.getLogger(OpenDirectory.class.getName()).log(Level.SEVERE, null, e);
            }
            String lastFile = lastUsed.nextLine();
            File l = new File(lastFile);
            Path filepath = l.toPath();
            if (Files.exists(filepath)) 
            {
            file = new File(lastFile);
            lastFile = lastFile.substring(0,lastFile.lastIndexOf(File.separator));
            file = new File(lastFile);
            }
        }
        
        //try to open last used file or defualt directory
        FileChooser fileChooser = new FileChooser();
        System.out.println(file);
        fileChooser.setTitle("Open Database");
        fileChooser.setInitialDirectory(file);
        file = fileChooser.showOpenDialog(stage);
        
        //to combat the null pointer exception
        if (file != null) 
        {
           return file; 
        }
        else
        {
            file = new File("");
            return file;
        }
        
        
    }
    
}
