package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 03/31/2019
 *Project: Account Manager
 * 
 * Purpose: To create a gui that displays user accounts added to the the database
 * and provides options to create, edit, and delete databases and accounts. This is 
 * the main interface for the program.
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountManager extends Application
{
//**STAGE**********************************************************************
    
    @Override
    public void start(Stage primaryStage)
    {
        //the scene
        primaryStage.setTitle("Account Manager");
        
        //Title
        Text scenetitle = new Text("Account Manager");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        
        //make new stage
        Stage stage = new Stage();
        
        //menu bar
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        //add menus
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        
        //text area to display accounts
        TextArea accountDisplay = new TextArea();
        accountDisplay.setPrefHeight(400);  
        accountDisplay.setPrefWidth(350);
        accountDisplay.setMinWidth(100);
        accountDisplay.setEditable(false);
        
        
        //toolbar
        ToolBar toolBar = new ToolBar();
        //create db button
        Button createDB = new Button();
        createDB.setText("Create Database");

        createDB.setOnAction(new EventHandler<ActionEvent>()
        {  
            @Override
            public void handle(ActionEvent event)
            {
                  //Open create db gui
                  CreateDatabase create = new CreateDatabase();
                  stage.setScene(create.openScene(stage));
                  stage.show();               
            }
        });
        

        //add account button
        Button addAccount = new Button();
        addAccount.setText("Add Account");
        addAccount.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                  //open add account gui
                  AddAccount add = new AddAccount();
                  stage.setScene(add.openScene(stage));
                  stage.show();
            }
        });
        //add account button
        Button loadDatabase = new Button();
        loadDatabase.setText("Load Database");
        loadDatabase.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                  //variables
                  Scanner inputFile = null;    //input file scanner
                  FileInputStream fin = null;  //input file stream
                  File file = null;
                  
                  //load account
                  LoadDatabase load = new LoadDatabase();
                  file = load.openFile(stage);
                  String f = file.getPath();
                  System.out.println(f);
                    try
                    {
                        System.out.println("Reading " + f +  "...");
                        fin = new FileInputStream(f);
                        inputFile = new Scanner(fin);
                    }
                    catch(FileNotFoundException e)
                    {
                        System.out.println("File is missing or corrupt!");
                    }
                    //Display contents of the database
                    while(inputFile.hasNextLine())
                    {
                        
                        accountDisplay.appendText(inputFile.nextLine());
                        accountDisplay.appendText("\n");
                    }
                    accountDisplay.focusedProperty();
                    //close the file
                    try
                    {
                        fin.close();
                        inputFile.close();
                    }
                    catch(IOException e)
                    {
                        System.out.println("File is missing or corrupt!");
                    }
            }
        });
        //add buttons
        toolBar.getItems().addAll(loadDatabase,createDB,addAccount);
        
        
        
        //seperator, Might use this in another phase?
        Separator s = new Separator();
        s.setOrientation(Orientation.HORIZONTAL);
        
        //text field that acts like a status bar?
        TextField statusBar = new TextField();
        statusBar.setEditable(false);
        
            
        //create root and add all the controls
        BorderPane b = new BorderPane();
        BorderPane b2 = new BorderPane();
        
        //setup the stage. So many headaches here.
        VBox vbox = new VBox();
        BorderPane root = new BorderPane();
        vbox.getChildren().addAll(menuBar,toolBar);
        root.setTop(vbox);
        root.setRight(accountDisplay);
        root.setBottom(statusBar);
        
        
        
        Scene scene = new Scene(root, 500, 500);//displaying scene with everything in root
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//**Main*****************************************************************
    public static void main(String[] args) throws IOException
    {
        launch(args);
    }
    
}