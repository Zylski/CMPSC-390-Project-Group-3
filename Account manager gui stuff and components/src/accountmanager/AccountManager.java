package accountmanager;
/*------------------------------------------------------------------------------------
 *Programmer: Damian Zylski
 *System: Windows XP Netbeans 8
 *Date: 03/31/2019
 *Project: Account Manager
 * 
 * Purpose: To create a gui that displays user accounts added to the the database
 * and provides options to create, edit, and delete databases and accounts. This is 
 * the main interface for the program. This has been a MASSIVE learning experience. 
 * This was my first time coding in java fx. Should definitely have used fxml though.
 * 
 * -----------------------------------------------------------------------------------
 */
//import java.awt.Insets;
//import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountManager extends Application
{
//**STAGE**********************************************************************
    
    //make an array list to store accounts - Should be ok on memory.
    private static ArrayList <Account> accountList = new ArrayList <Account>();
    
    private static Account a = new Account();
    
    //the master password
    private static String masterPassword;
    
    //currently in use db name, this is needed to allow account creation
    private static String currentDB = "";
    
    //status bar
    private static TextField statusBar = new TextField();

    public AccountManager()
    {
        
    }
    
    //table
    public static TableView<Account> table = new TableView<>();
    
    //make a clipboard?
        public static Clipboard systemClipboard = Clipboard.getSystemClipboard();
        
        //make container for clipboard content?
        public static ClipboardContent content = new ClipboardContent();
        
        //for paste operations
        public static String clipboardText = "";
    
    @Override
    public void start(Stage primaryStage)
    {
        //make new stage
        Stage stage = new Stage();
        
        //set program title icon
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/shinra.jpg")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/shinra.jpg")));

        
        //the scene
        primaryStage.setTitle("Account Manager");
        
        //Title
        Text scenetitle = new Text("Account Manager");
        scenetitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        
        //menu bar
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");
        
        //make menu items
        MenuItem menuOpen = new MenuItem("Open");
        MenuItem menuExit = new MenuItem("Exit");
        MenuItem menuCut = new MenuItem("Cut");
        MenuItem menuCopy = new MenuItem("Copy");
        MenuItem menuPaste = new MenuItem("Paste");
        MenuItem menuAbout = new MenuItem("About");
        
        //set menu actions
        
        //Open is by the add account button further down
        
        //exit the program
        menuExit.setOnAction(e -> {
        System.exit(-1);
        });
        
        //Copy text
        menuCopy.setOnAction(e -> {
        copy();
        });
        
        //cut text
        menuCut.setOnAction(e -> {
        System.out.println("This might not be needed");
        });
        
        //paste text
        menuCut.setOnAction(e -> {
        System.out.println("This might not be needed");
        });
        
        //About
        menuAbout.setOnAction(e -> {
        About about = new About();
        stage.setScene(about.openScene(stage));       
        stage.showAndWait();
        });
        
        //add them to the menu
        menuFile.getItems().add(menuOpen);
        menuFile.getItems().add(menuExit);
        menuEdit.getItems().add(menuCut);
        menuEdit.getItems().add(menuCopy);
        menuEdit.getItems().add(menuPaste);
        menuHelp.getItems().add(menuAbout);

        //add menus
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
        
        //text field that acts like a status bar?
        statusBar.setEditable(false);
        
        //text area to display accounts
        TextArea accountDisplay = new TextArea();
        accountDisplay.setPrefHeight(400);  
        accountDisplay.setPrefWidth(350);
        accountDisplay.setMinWidth(100);
        accountDisplay.setEditable(false);
        
        //table view?

        // columns?
        TableColumn<Account, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

        TableColumn<Account, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        
        // column with reveal button
        TableColumn<Account, Account> revealCol = new TableColumn<>("Reveal Password");
        
        // get a set of passwords?
        ObservableSet<Account> usersWithShownPasswords = FXCollections.observableSet();

        //giving massive credit to Stack overflow for the below code in regards to cells

        passwordCol.setCellFactory(c -> {

            // cell
            TableCell<Account, String> cell = new TableCell<>();

            // if cell gets used by item from different row, update it
            cell.indexProperty().addListener((obs, oldIndex, newIndex) -> 
                updateCell(usersWithShownPasswords, cell));

            // update if password changes

            cell.itemProperty().addListener((obs, oldItem, newItem) -> 
                updateCell(usersWithShownPasswords, cell));

            // if username of password changes, update cell
            usersWithShownPasswords.addListener((SetChangeListener.Change<? extends Account> change) ->
                updateCell(usersWithShownPasswords, cell));

            return cell ;
        });
        
        
        revealCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));

        // cell for toggle buttons:
        revealCol.setCellFactory(c -> new TableCell<Account, Account>()
        {

            // create toggle button for cell:
            private final ToggleButton btn = new ToggleButton();

            
            
            {
                // button click state
                usersWithShownPasswords.addListener((Change<? extends Account> change) ->
                {
                    btn.setSelected(usersWithShownPasswords.contains(getItem()));
                });

                // if button is clicked
                btn.selectedProperty().addListener((obs, wasSelected, isNowSelected) ->
                {
                    if (isNowSelected)
                    {
                        usersWithShownPasswords.add(getItem());
                    }
                    else
                    {
                        usersWithShownPasswords.remove(getItem());
                    }
                });

                // Change text in button as needed
                btn.textProperty().bind(Bindings.when(btn.selectedProperty()).then("Hide").otherwise("Reveal"));
                setAlignment(Pos.CENTER);
            }

            // update item
            @Override
            public void updateItem(Account item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty)
                {
                    setGraphic(null);
                }
                else
                {
                    btn.setSelected(usersWithShownPasswords.contains(item));
                    setGraphic(btn);
                }
            }
        });
        
        table.getColumns().addAll(accountCol, passwordCol,revealCol);
        table.setEditable(true);
        //table.setMinWidth(500);
        
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
                  stage.showAndWait();
                  if(currentDB.isEmpty())
                  {
                      statusBar.setText("Database not created");
                  }
                  else
                  {
                      statusBar.setText("Database Created");
                      
                      //Since new db is created, clear the table
                      table.getItems().clear();
                      
                      //also clear the account list
                      clearAccount();
                  }
            }
        });
        

        //add account button
        Button addAccount = new Button();
        addAccount.setText("Add Account");
        AddAccount add = new AddAccount();
        ErrorPage error = new ErrorPage();
        addAccount.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                  //open add account page if db is created or loaded
                  if(!currentDB.isEmpty())
                  {
                      stage.setScene(add.openScene(stage,currentDB,masterPassword));

                      stage.showAndWait();
                      
                      //The below code should probably be a method because it repeats. But I'm lazy.
                      int i = 0;
                      int size = accountList.size();

                      //clear the table
                      table.getItems().clear();

                      for(i = 0; i < size; i++)
                      {

                      //create the rows in the table
                            accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                            passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                            table.getItems().add(accountList.get(i));

                      }
                      statusBar.setText("Account Added to Database");
                  }
                  //otherwise open an error page
                  else
                  {
                      stage.setScene(error.openScene(stage));
                      
                      stage.showAndWait();
                      statusBar.setText("Error: Account not added");
                  }

            }
        });
        //add load account button
        Button loadDatabase = new Button();
        loadDatabase.setText("Load Database");
        TableCell rowCell = new TableCell();
        LoadDatabase load = new LoadDatabase();
        loadDatabase.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                  stage.setScene(load.openScene(stage));
                  
                  stage.showAndWait();
                  
                  int i = 0;
                  int size = accountList.size();
                  
                  //clear the table
                  table.getItems().clear();
                  
                  for(i = 0; i < size; i++)
                  {
                   
                  //create the rows in the table
                        accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                        passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                        table.getItems().add(accountList.get(i));
                        statusBar.setText("Database Loaded");
                  }
            }
        });
        
        //open a db file from the menu bar
        menuOpen.setOnAction(e -> {
            stage.setScene(load.openScene(stage));

            stage.showAndWait();

            int i = 0;
            int size = accountList.size();

            //clear the table
            table.getItems().clear();

            for (i = 0; i < size; i++)
            {

                //create the rows in the table
                accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                table.getItems().add(accountList.get(i));
                statusBar.setText("Database Loaded");
            }
        });
        
        //add buttons
        toolBar.getItems().addAll(loadDatabase,createDB,addAccount);
        
        
        
        //seperator, Might use this in another phase?
        Separator s = new Separator();
        s.setOrientation(Orientation.HORIZONTAL);
        
        
        //size the columns for table
        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        accountCol.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        passwordCol.setMaxWidth( 1f * Integer.MAX_VALUE * 30 ); // 30% width
        revealCol.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
            
        //create root and add all the controls
        BorderPane b = new BorderPane();
        BorderPane b2 = new BorderPane();
        
        //setup the stage. So many headaches here.
        VBox vbox = new VBox();
        VBox vboxTable = new VBox();
        BorderPane root = new BorderPane();
        vbox.getChildren().addAll(menuBar,toolBar);
        table.isResizable();
        vboxTable.getChildren().addAll(table);
        vboxTable.setPrefWidth(500);
        vboxTable.setVgrow(table, Priority.ALWAYS);
        root.setTop(vbox);
        root.setRight(vboxTable);
        root.setBottom(statusBar);
        
        Scene scene = new Scene(root, 750, 500);//displaying scene with everything in root
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //use this to add accounts in other scences
    public void setAccount(String l,String u, String p, String ur, String d)
    {
        this.accountList.add(new Account(l,u,p,ur,d));
    }
    
    public void clearAccount()
    {
        this.accountList.clear();
    }
    
    public void printAccount(int i)
    {
        this.accountList.get(i).writeOutput();
    }
    
    public void getMasterPassword(String p)
    {
        this.masterPassword = p;
    }
    
    public void getDB(String d)
    {
        this.currentDB = d;
    }
    
    public void setStatusBar(String s)
    {
        this.statusBar.setText(s);
    }
    
    //needed for reveal password
    private void updateCell(ObservableSet<Account> usersWithShownPasswords,
            TableCell<Account, String> cell) {
        int index = cell.getIndex();
        TableView<Account> table = cell.getTableView();
        if (index < 0 || index >= table.getItems().size()) {
            cell.setText("");
        } else {
            Account user = table.getItems().get(index);
            if (usersWithShownPasswords.contains(user)) {
                cell.setText(user.getPassword()) ;
            } else {
                cell.setText(mask(user.getPassword()));
            }
        }
    }
    
    //to mask passwords
    private String mask(String text) 
    {
        char[] chars = new char[text.length()];
        Arrays.fill(chars, '*');
        return new String(chars);
    }
    
    private String getSelectedText()
    {
        Account b = table.getFocusModel().getFocusedItem();
        TextField tableField = new TextField();
        tableField.setText(b.toString());// = b.toString();
  TextField[] tfs = new TextField[] { statusBar, tableField };
  for( TextField tf : tfs ) {
   if( !tf.getSelectedText().isEmpty()  ) {
    return tf.getSelectedText();
   }
  }
  return null;
 }
    
  //get text for copy  
 public void copy() {
  String text = getSelectedText();
  content.putString(text);
  systemClipboard.setContent(content);
 }
 
 private TextField getFocusedTextField() {
  TextField[] tfs = new TextField[] { statusBar };
  for( TextField tf : tfs ) {
   if( tf.isFocused() ) {
    return tf;
   }
  }
  return null;  
 }
 
 public void paste() {
  
  if( !systemClipboard.hasContent(DataFormat.PLAIN_TEXT) ) {
   
   return;
  }
  
  String clipboardText = systemClipboard.getString();
  
  TextField focusedTF = getFocusedTextField();
  IndexRange range = focusedTF.getSelection();
  
  String origText = focusedTF.getText();
  
  int endPos = 0;
  String updatedText = "";
  String firstPart = "";//firstPart.substring( origText, 0, range.getStart() );
  String lastPart = "";//StringUtils.substring( origText, range.getEnd(), StringUtils.length(origText) );

  updatedText = firstPart + clipboardText + lastPart;
  
  if( range.getStart() == range.getEnd() ) {
  // endPos = range.getEnd() + StringUtils.length(clipboardText);
  } else {
  // endPos = range.getStart() + StringUtils.length(clipboardText);
  }
  
  focusedTF.setText( updatedText );
  focusedTF.positionCaret( endPos );
 }
 
   


//**Main*****************************************************************
    public static void main(String[] args) throws IOException
    {
        launch(args);
    }
    
}