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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    
    //status bar for security
    private static TextField securityStatusBar = new TextField();
    
    //status bar for properties
    private static TextField propertiesStatusBar = new TextField();
    
    //category
    private static String category = "";
    
    //delete choice
    private static boolean wasCreated = false;

    public AccountManager()
    {
        
    }
    //GLOBAL STUFF
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
//@@@@@@FOR ACCOUNT TAB@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        
//******menu bar***********************************************************************************************************
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
        securityStatusBar.setEditable(false);
        propertiesStatusBar.setEditable(false);
        
        //text area to display accounts - this was ditched in favour of the tabe below
        TextArea accountDisplay = new TextArea();
        accountDisplay.setPrefHeight(400);  
        accountDisplay.setPrefWidth(350);
        accountDisplay.setMinWidth(100);
        accountDisplay.setEditable(false);
        
//******table view************************************************************************************************************?

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
        
        //Table context menu?
        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Delete Account");
        cm.getItems().add(mi1);
        MenuItem mi2 = new MenuItem("Edit Account");
        cm.getItems().add(mi2);
        MenuItem mi3 = new MenuItem("Copy Password to Clipboard");
        cm.getItems().add(mi3);
        MenuItem mi4 = new MenuItem("Copy Details to Clipboard");
        cm.getItems().add(mi4);
        MenuItem mi5 = new MenuItem("Cancel");
        cm.getItems().add(mi5);

        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent t)
            {
                if (t.getButton() == MouseButton.SECONDARY)
                {
                    cm.show(table, t.getScreenX(), t.getScreenY());
                }
            }
        });
        
        ErrorPage er = new ErrorPage();
        //Set action for copy to clipboard password
        mi3.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {
                    int i = 0;
                    Account a = table.getFocusModel().getFocusedItem();
                    EditAccount edit = new EditAccount();
                    String accountLabel = a.getLabel();
                    int target = 0;

                    for (i = 0; i < accountList.size(); i++)
                    {
                        if (accountList.get(i).compareToString(a.getLabel()))
                        {
                            target = i;
                            System.out.println("Target Found");
                        }
                        else
                        {
                            if (i == accountList.size() - 1)
                            {
                                System.out.println("Account not found");
                            }
                        }
                    }

                    String text = accountList.get(target).getPassword();
                    content.putString(text);
                    systemClipboard.setContent(content);
                    
                }
                //otherwise open an error page
                else
                {
                    stage.setScene(er.openScene(stage));

                    stage.showAndWait();
                    statusBar.setText("Error: Password not copied");
                }
            }
        });
        mi4.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {
                    int i = 0;
                    Account a = table.getFocusModel().getFocusedItem();
                    EditAccount edit = new EditAccount();
                    String accountLabel = a.getLabel();
                    int target = 0;

                    for (i = 0; i < accountList.size(); i++)
                    {
                        if (accountList.get(i).compareToString(a.getLabel()))
                        {
                            target = i;
                            System.out.println("Target Found");
                        }
                        else
                        {
                            if (i == accountList.size() - 1)
                            {
                                System.out.println("Account not found");
                            }
                        }
                    }

                    String text = accountList.get(target).getStringInfo();
                    content.putString(text);
                    systemClipboard.setContent(content);

                }
                //otherwise open an error page
                else
                {
                    stage.setScene(er.openScene(stage));

                    stage.showAndWait();
                    statusBar.setText("Error: Details not Copied");
                }
            }
        });
        //Cancel 
        mi5.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                System.out.println("no");
            }
        });
        //Delete and Edit are below Delete and Edit button code
        
//******TREE VIEW FOR CATEGORIES*****************************************************************************************************
        //folder icons for categories
        Node treeIcon32 = new ImageView(
                new Image(getClass().getResourceAsStream("resources/FOLDER32.png"))
        );
        
        AddAccount add1 = new AddAccount();

        TreeItem<String> treeRoot = new TreeItem<String>("Categories", treeIcon32);
        treeRoot.setExpanded(true);

        //Add all folder
        Node treeIcon16 = new ImageView(
                new Image(getClass().getResourceAsStream("resources/FOLDER16.png"))
        );
        TreeItem<String> all = new TreeItem<String>("All", treeIcon16);
        treeRoot.getChildren().add(all);

        //add a general folder
        Node treeIcon162 = new ImageView(
                new Image(getClass().getResourceAsStream("resources/FOLDER16.png"))
        );
        TreeItem<String> general = new TreeItem<String>("General", treeIcon162);
        treeRoot.getChildren().add(general);

        TreeView tree = new TreeView<String>(treeRoot);
        
        //tree actions when category is clicked
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) ->
        {
            String catName = handleMouseClicked(event,tree);
            add1.setFieldCategory(catName);
            
            //The below code should probably be a method because it repeats. But I'm lazy.
            int i = 0;
            int size = accountList.size(); //account list size

            //clear the table
            table.getItems().clear();
            for (i = 0; i < size; i++)
            {
                //Create table entry only if the category exists!
                if(accountList.get(i).getCategory().equalsIgnoreCase(catName) || catName.equalsIgnoreCase("all"))
                {
                    //create the rows in the table
                    accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                    passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                    table.getItems().add(accountList.get(i));
                }
            }
        };

        tree.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

        //tree view context menu?
        ContextMenu treeCm = new ContextMenu();
        //Add a category
        MenuItem treeMi1 = new MenuItem("Add Category");
        treeMi1.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                AddCategory addCat = new AddCategory();
                stage.setScene(addCat.openScene(stage));

                stage.showAndWait();

                addCategory(treeRoot);
                //set category back to blank
                getCategory("");
            }
        });
        treeCm.getItems().add(treeMi1);
        //cancel
        MenuItem treeMi2 = new MenuItem("Cancel");
        treeMi2.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                System.out.println("No");
            }
        });
        treeCm.getItems().add(treeMi2);

        tree.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent c)
            {
                if (c.getButton() == MouseButton.SECONDARY)
                {
                    treeCm.show(tree, c.getScreenX(), c.getScreenY());
                }
            }
        });
        
//******toolbar***************************************************************************************************************
        ToolBar toolBar = new ToolBar();
        
//******CREATE DATABASE BUTTON*****************************************************************************************************
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
                  //check if account was created and perform appropriate action
                  if(!wasCreated)
                  {
                      statusBar.setText("Database not created");
                  }
                  else
                  {
                      statusBar.setText("Database Created"); //Maybe try a boolean check for whether anything was changed?
                      
                      //Since new db is created, clear the table
                      table.getItems().clear();
                      
                      //also clear the account list
                      clearAccount();
                  }
            }
        });
        

//******ADD ACCOUNT BUTTON*****************************************************************************************************
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
                      int size = accountList.size(); //account list size
                      tree.getRoot().setExpanded(true); //expand the tree
                      int treeSize = tree.getExpandedItemCount(); //size of tree
                      String currentCat = ""; //current category
                      String treeCat = ""; //cat from tree
                      boolean catExists = false; //check if category exists
                      //clear the table
                      table.getItems().clear();
                      for (i = 0; i < size; i++)
                      {

                          //create the rows in the table
                          accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                          passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                          table.getItems().add(accountList.get(i));

                          currentCat = "TreeItem [ value: " + accountList.get(i).getCategory() + " ]";

                          //add new categories if they exist in db
                          for (int j = 1; j < treeSize; j++)
                          {
                              treeCat = tree.getTreeItem(j).toString();
                              if (treeCat.equalsIgnoreCase(currentCat))
                              {
                                  catExists = true;
                              }
                          }
                          //if category doesn't exist in the tree, add it!
                          if (!catExists)
                          {
                              category = accountList.get(i).getCategory();
                              addCategory(treeRoot);
                              category = "";
                          }
                          catExists = false;
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
//******LOAD ACCOUNT BUTTON*****************************************************************************************************
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
                int size = accountList.size(); //account list size
                tree.getRoot().setExpanded(true); //expand the tree
                int treeSize = tree.getExpandedItemCount(); //size of tree
                String currentCat = ""; //current category
                String treeCat = ""; //cat from tree
                boolean catExists = false; //check if category exists
                //clear the table
                table.getItems().clear();
                for (i = 0; i < size; i++)
                {

                    //create the rows in the table
                    accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                    passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                    table.getItems().add(accountList.get(i));

                    currentCat = "TreeItem [ value: " + accountList.get(i).getCategory() + " ]";

                    //add new categories if they exist in db
                    for (int j = 1; j < treeSize; j++)
                    {
                        treeCat = tree.getTreeItem(j).toString();
                        if (treeCat.equalsIgnoreCase(currentCat))
                        {
                            catExists = true;
                        }
                    }
                    //if category doesn't exist in the tree, add it!
                    if (!catExists)
                    {
                        category = accountList.get(i).getCategory();
                        addCategory(treeRoot);
                        category = "";
                    }
                    catExists = false;
                    statusBar.setText("Database Loaded");
                  }
            }
        });
        
        //open a db file from the menu bar
        menuOpen.setOnAction(e -> {
            stage.setScene(load.openScene(stage));

            stage.showAndWait();

            int i = 0;
            int size = accountList.size(); //account list size
            tree.getRoot().setExpanded(true); //expand the tree
            int treeSize = tree.getExpandedItemCount(); //size of tree
            String currentCat = ""; //current category
            String treeCat = ""; //cat from tree
            boolean catExists = false; //check if category exists

            //clear the table
            table.getItems().clear();
            for (i = 0; i < size; i++)
            {

                //create the rows in the table
               accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
               passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                table.getItems().add(accountList.get(i));
                
                currentCat = "TreeItem [ value: " + accountList.get(i).getCategory() + " ]";
                
                //add new categories if they exist in db
                for(int j = 1; j < treeSize; j++)
                {
                    treeCat = tree.getTreeItem(j).toString();
                    if(treeCat.equalsIgnoreCase(currentCat))
                    {
                        catExists = true;
                    }
                }
                //if category doesn't exist in the tree, add it!
                if(!catExists)
                {
                    category = accountList.get(i).getCategory();
                    addCategory(treeRoot);
                    category = "";
                }
                catExists = false;
                
                statusBar.setText("Database Loaded");
            }
        });
        
//******EDIT ACCOUNT BUTTON*****************************************************************************************************
        Button editBtn = new Button();
        editBtn.setText("Edit Selected Account");
        editBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!currentDB.isEmpty() && !accountList.isEmpty())
                {
                  int i = 0;
                  Account a = table.getFocusModel().getFocusedItem();
                  EditAccount edit = new EditAccount();
                  String accountLabel = a.getLabel();
                  int target = 0;

                    for (i = 0; i < accountList.size(); i++)
                    {
                        if (accountList.get(i).getLabel().compareTo(a.getLabel()) == 0)
                        {
                            target = i;
                            System.out.println("Target Found");
                        }
                        else
                        {
                            if (i == accountList.size() - 1)
                            {
                                System.out.println("Account not found");
                            }
                        }
                    }

                  stage.setScene(edit.openScene(stage,currentDB,masterPassword,a,target));
                  stage.showAndWait();
                  
                  int size = accountList.size(); //account list size
                tree.getRoot().setExpanded(true); //expand the tree
                int treeSize = tree.getExpandedItemCount(); //size of tree
                String currentCat = ""; //current category
                String treeCat = ""; //cat from tree
                boolean catExists = false; //check if category exists

                //clear the table
                table.getItems().clear();
                for (i = 0; i < size; i++)
                {

                    //create the rows in the table
                    accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                    passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                    table.getItems().add(accountList.get(i));

                    currentCat = "TreeItem [ value: " + accountList.get(i).getCategory() + " ]";

                    //add new categories if they exist in db
                    for (int j = 1; j < treeSize; j++)
                    {
                        treeCat = tree.getTreeItem(j).toString();
                        if (treeCat.equalsIgnoreCase(currentCat))
                        {
                            catExists = true;
                        }
                    }
                    //if category doesn't exist in the tree, add it!
                    if (!catExists)
                    {
                        category = accountList.get(i).getCategory();
                        addCategory(treeRoot);
                        category = "";
                    }
                    catExists = false;
                  }
                }
                //otherwise open an error page
                  else
                  {
                      stage.setScene(error.openScene(stage));
                      
                      stage.showAndWait();
                      statusBar.setText("Error: Account not edited");
                  }
            }
        });
        //context menu repeat code - Should really be a function
        mi2.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {
                    int i = 0;
                    Account a = table.getFocusModel().getFocusedItem();
                    EditAccount edit = new EditAccount();
                    String accountLabel = a.getLabel();
                    int target = 0;

                    for (i = 0; i < accountList.size(); i++)
                    {
                        if (accountList.get(i).getLabel().compareTo(a.getLabel()) == 0)
                        {
                            target = i;
                            System.out.println("Target Found");
                        }
                        else
                        {
                            if (i == accountList.size() - 1)
                            {
                                System.out.println("Account not found");
                            }
                        }
                    }

                    stage.setScene(edit.openScene(stage, currentDB, masterPassword, a, target));
                    stage.showAndWait();

                    int size = accountList.size(); //account list size
                    tree.getRoot().setExpanded(true); //expand the tree
                    int treeSize = tree.getExpandedItemCount(); //size of tree
                    String currentCat = ""; //current category
                    String treeCat = ""; //cat from tree
                    boolean catExists = false; //check if category exists

                    //clear the table
                    table.getItems().clear();
                    for (i = 0; i < size; i++)
                    {

                        //create the rows in the table
                        accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                        passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                        table.getItems().add(accountList.get(i));

                        currentCat = "TreeItem [ value: " + accountList.get(i).getCategory() + " ]";

                        //add new categories if they exist in db
                        for (int j = 1; j < treeSize; j++)
                        {
                            treeCat = tree.getTreeItem(j).toString();
                            if (treeCat.equalsIgnoreCase(currentCat))
                            {
                                catExists = true;
                            }
                        }
                        //if category doesn't exist in the tree, add it!
                        if (!catExists)
                        {
                            category = accountList.get(i).getCategory();
                            addCategory(treeRoot);
                            category = "";
                        }
                        catExists = false;
                    }
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
//******DELETE ACCOUNT BUTTON*****************************************************************************************************
        Button deleteBtn = new Button();
        deleteBtn.setText("Delete Selected Account");
        deleteBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!currentDB.isEmpty() && !accountList.isEmpty())
                {
                  int i = 0;
                  Account a = table.getFocusModel().getFocusedItem();
                  String accountLabel = a.getLabel() + "";
                    
                    DeleteAccount delete = new DeleteAccount();
                    

                for (i = 0; i < accountList.size(); i++)
                {
                    if (accountList.get(i).getLabel().compareTo(a.getLabel()) == 0)
                    {
                        stage.setScene(delete.openScene(stage, currentDB, masterPassword, a, i));
                        stage.showAndWait();
                        break;
                        //accountList.remove(i);
                    }
                    else
                    {
                        if (i == accountList.size() - 1)
                        {
                            System.out.println("Account not found/No account was deleted.");
                        }
                    }
                }
                  
                  //clear the table
                  table.getItems().clear();
                  
                  for(i = 0; i < accountList.size(); i++)
                  {
                   
                  //create the rows in the table
                        accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                        passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                        table.getItems().add(accountList.get(i));
                  }
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
        //delete context menu - should probably be a method
        mi1.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {
                    int i = 0;
                    Account a = table.getFocusModel().getFocusedItem();
                    String accountLabel = a.getLabel() + "";

                    DeleteAccount delete = new DeleteAccount();

                    for (i = 0; i < accountList.size(); i++)
                    {
                        if (accountList.get(i).getLabel().compareTo(a.getLabel()) == 0)
                        {
                            stage.setScene(delete.openScene(stage, currentDB, masterPassword, a, i));
                            stage.showAndWait();
                            break;
                            //accountList.remove(i);
                        }
                        else
                        {
                            if (i == accountList.size() - 1)
                            {
                                System.out.println("Account not found/No account was deleted.");
                            }
                        }
                    }

                    //clear the table
                    table.getItems().clear();

                    for (i = 0; i < accountList.size(); i++)
                    {

                        //create the rows in the table
                        accountCol.setCellValueFactory(new PropertyValueFactory<>("info"));
                        passwordCol.setCellValueFactory(new PropertyValueFactory<>("mask"));

                        table.getItems().add(accountList.get(i));
                    }
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
        
        //add buttons to the account toolbar
        toolBar.getItems().addAll(loadDatabase,createDB,addAccount,editBtn,deleteBtn);
//******TABBED INTERFACE************************************************************************************************
        // create a tabpane 
        TabPane tabpane = new TabPane();

            // create Tabs
            //Account tab
            Tab tab1 = new Tab("Account List");
            tab1.setClosable(false);
            tab1.setStyle("-fx-font-weight: normal; -fx-font-size: 13px; ");
            
            //Security Questions tab
            Tab tab2 = new Tab("Security Questions");
            tab2.setClosable(false);
            tab2.setStyle("-fx-font-weight: normal; -fx-font-size: 13px; ");
            Tooltip tip1 = new Tooltip("Select an account from the account tab to display it's security questions");
            tab2.setTooltip(tip1);
            
            //Properties tab
            Tab tab3 = new Tab("Properties");
            tab3.setClosable(false);
            tab3.setStyle("-fx-font-weight: normal; -fx-font-size: 13px; ");
            Tooltip tip2 = new Tooltip("Select an account from the account tab to display it's properties");
            tab2.setTooltip(tip2);

            // create a label 
            Label label = new Label("This is Tab: ");

            // add tabs to tab pane
            tabpane.getTabs().addAll(tab1,tab2,tab3);
        
        
        
        //seperator, Might use this in another phase?
        Separator s = new Separator();
        s.setOrientation(Orientation.HORIZONTAL);
        
//@@@@@@FOR SECURITY TAB@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//******Security tab display controls***************************************************************************************************************
        //make a grid pane
        GridPane secGrid;
        secGrid = new GridPane();
        secGrid.setAlignment(Pos.CENTER);
        secGrid.setHgap(10);
        secGrid.setVgap(10);
        secGrid.setPadding(new Insets(5, 5, 5, 5));
        Text secTitle = new Text("  Security Questions");
        Text tip = new Text(" Select an \n account from \n the table \n on the \n account tab \n to add \n security questions \n to it");
        tip.setFont(Font.font("Courier New", FontWeight.BOLD, 13)); //a tip to be on the right?
        secTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        secGrid.add(secTitle, 0, 0, 2, 1); //Not visible?
        
//******Security tab toolbar***************************************************************************************************************
        ToolBar secToolBar = new ToolBar();
        
//******Add Security Question Button***************************************************************************************************************
        Button addSec = new Button();
        addSec.setText("Add Security Question");
        addSec.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {
                    int i = 0;
                    int target = 0;
                    Account a = table.getFocusModel().getFocusedItem();
                    String accountLabel = a.getLabel() + "";

                    AddQuestion addQ = new AddQuestion();

                    for (i = 0; i < accountList.size(); i++)
                    {
                        if (accountList.get(i).getLabel().compareTo(a.getLabel()) == 0)
                        {
                            target = i;
                            stage.setScene(addQ.openScene(stage, currentDB, masterPassword, a, target));
                            stage.showAndWait();
                            break;
                        }
                        else
                        {
                            if (i == accountList.size() - 1)
                            {
                                System.out.println("Account not found/No account was deleted.");
                            }
                        }
                    }
                    
                    int row = 1; //current row
                    int size = accountList.get(target).getSecurityQuestion().size();
                    
                    //List security questions for given account
                    for(i = 0; i < size; i++)
                    {
                        System.out.println(i);
                        //text area for displaying questions
                        Text qLabel = new Text("Question " + (i+1));
                        TextArea secArea = new TextArea();
                        secArea.setEditable(false);

                        //text field for display 
                        Text aLabel = new Text("Answer");
                        TextField secField = new TextField();
                        secField.setEditable(false);
                        
                        //set question field
                        secArea.setText(accountList.get(target).getSecurityQuestion().get(i).getQuestion());
                        secGrid.add(qLabel,0,row);
                        secGrid.add(secArea,0,++row);
                        
                        //set answer field
                        String answer = accountList.get(target).getSecurityQuestion().get(i).getAnswer();
                        secField.setText("**************");
                        secGrid.add(aLabel,0,++row);
                        secGrid.add(secField,0,++row);
                        
                        //add a reveal button
                        //Button to reveal security question
                        ToggleButton secReveal = new ToggleButton("Reveal Answer");
                        secReveal.setOnAction(new EventHandler<ActionEvent>()
                        {
                            @Override
                            public void handle(ActionEvent event)
                            {
                                if(secReveal.isSelected())
                                {
                                    secField.setText(answer);
                                    secReveal.setText("Hide Answer");
                                }
                                else
                                {
                                    secField.setText("**************");
                                    secReveal.setText("Reveal Answer");
                                }
                            }
                        });
                        secGrid.add(secReveal,0,++row);
                        
                        //advance rows
                        row = row + 2;
                    }
                }
                //otherwise open an error page
                else
                {
                    stage.setScene(error.openScene(stage));
                    stage.showAndWait();
                    securityStatusBar.setText("Question not added");
                }
            }
        });
//******Edit Security Question Button***************************************************************************************************************
        Button editSec = new Button();
        editSec.setText("Edit Security Question");
        editSec.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {

                }
                //otherwise open an error page
                else
                {
                    statusBar.setText("Error: Account not edited");
                }
            }
        });
//******Delete Security Question Button***************************************************************************************************************
        Button deleteSec = new Button();
        deleteSec.setText("Delete Security Question");
        deleteSec.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {

                }
                //otherwise open an error page
                else
                {
                    statusBar.setText("Error: Account not edited");
                }
            }
        });
        
        //add buttons to tool bar
        secToolBar.getItems().addAll(addSec,editSec,deleteSec);
//@@@@@@FOR PROPERTIES TAB@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//******property tab display controls***************************************************************************************************************
        //text area for displaying properties
        TextArea propArea = new TextArea();
        propArea.setEditable(false);

        //text field for displaying property values
        TextField propField = new TextField();
        propField.setEditable(false);

        //Button to reveal security question
        Button propReveal = new Button("Reveal Answer");
        
        //make a grid pane
        GridPane propGrid;
        propGrid = new GridPane();
        propGrid.setAlignment(Pos.CENTER);
        propGrid.setHgap(10);
        propGrid.setVgap(10);
        propGrid.setPadding(new Insets(5, 5, 5, 5));
        Text propTitle = new Text("Account Properties");
        propTitle.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        propGrid.add(propTitle, 0, 0, 2, 1);
        
//******properties tab toolbar***************************************************************************************************************
        ToolBar propToolBar = new ToolBar();
        
//******Add Security Question Button***************************************************************************************************************
        Button addProp = new Button();
        addProp.setText("Add Property");
        addProp.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {

                }
                //otherwise open an error page
                else
                {
                    statusBar.setText("Error: Account not edited");
                }
            }
        });
//******Edit Security Question Button***************************************************************************************************************
        Button editProp = new Button();
        editProp.setText("Edit Property");
        editProp.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {

                }
                //otherwise open an error page
                else
                {
                    statusBar.setText("Error: Account not edited");
                }
            }
        });
//******Delete Security Question Button***************************************************************************************************************
        Button deleteProp = new Button();
        deleteProp.setText("Delete Property");
        deleteProp.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (!currentDB.isEmpty() && !accountList.isEmpty())
                {

                }
                //otherwise open an error page
                else
                {
                    statusBar.setText("Error: Account not edited");
                }
            }
        });

        //add buttons to tool bar
        propToolBar.getItems().addAll(addProp, editProp, deleteProp);
        
//!!!!!!SCENE SETUP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!        
        //size the columns for table
        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        accountCol.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        passwordCol.setMaxWidth( 1f * Integer.MAX_VALUE * 30 ); // 30% width
        revealCol.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
            
        //create root and add all the controls
        
        //setup the stage. So many headaches here.
        VBox root = new VBox(); //This is the main vbox. Contains the tabs with all content
        VBox bottom = new VBox(); //This contains the status bar
        VBox vboxTable = new VBox(); //This contains the account table. Probably not needed
        
        BorderPane accountTab = new BorderPane(); //The account tab. This was the old root before tabs
        BorderPane securityTab = new BorderPane(); //The security questions tab.        
        BorderPane propertiesTab = new BorderPane(); //The properties tab. 
        
        //give root the key nodes, menu bar and tabpane
        root.getChildren().addAll(menuBar,tabpane);
        
        //I would like the table to be resizable but I don't think this does it..
        table.isResizable();
        vboxTable.getChildren().addAll(table); //Give vbox table the table
        vboxTable.setPrefWidth(500); //set the preffered width
        vboxTable.setVgrow(table, Priority.ALWAYS); //Let the table scale vertically
        
        //build the account tab
        accountTab.setTop(toolBar);
        accountTab.setRight(vboxTable);
        accountTab.setLeft(tree);
        bottom.getChildren().addAll(s,statusBar);
        accountTab.setBottom(bottom);
        
        //make scroll pane, And add the grid to it
        ScrollPane secScroll = new ScrollPane();
        secScroll.setContent(secGrid);
        
        //set the tool bar and title to a vbox
        VBox secTop = new VBox();
        secTop.getChildren().addAll(secToolBar,secTitle);
        
        //set the border pane areas
        securityTab.setTop(secTop);
        securityTab.setRight(tip);
        securityTab.setCenter(secScroll);
        securityTab.setBottom(securityStatusBar);
        
        //build the properties tab
        
        //make scroll pane, And add the grid to it
        ScrollPane propScroll = new ScrollPane();
        propScroll.setContent(propGrid);
        
        //build the security tab
        propertiesTab.setTop(propToolBar);
        propertiesTab.setCenter(propScroll);
        propertiesTab.setBottom(propertiesStatusBar);
        
        // add content to the 3 tabs 
        tab1.setContent(accountTab); //account tab
        tab2.setContent(securityTab); //Security questions tab
        tab3.setContent(propertiesTab); //Properties tab
        
        //tab actions
        tabpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
        {

            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab
            )
            {
//***** Security Tab select action****************************************************************************************************************************               
                if (newTab == tab2)
                {
                    int i = 0;
                    int target = 0;
                    Account a = table.getFocusModel().getFocusedItem();
                    String accountLabel = a.getLabel() + "";

                    for (i = 0; i < accountList.size(); i++)
                    {
                        if (accountList.get(i).getLabel().compareTo(a.getLabel()) == 0)
                        {
                            target = i;
                            break;
                        }
                        else
                        {
                            if (i == accountList.size() - 1)
                            {
                                System.out.println("Account not found/No account was deleted.");
                            }
                        }
                    }

                    int row = 1; //current row
                    int size = accountList.get(target).getSecurityQuestion().size();

                    //List security questions for given account
                    for (i = 0; i < size; i++)
                    {
                        System.out.println(i);
                        //text area for displaying questions
                        Text qLabel = new Text("Question " + (i+1));
                        TextArea secArea = new TextArea();
                        secArea.setEditable(false);
                        secArea.wrapTextProperty().set(true);
                        secArea.setWrapText(true);
                        secArea.setPrefHeight(50);

                        //text field for display 
                        Text aLabel = new Text("Answer");
                        TextField secField = new TextField();
                        secField.setEditable(false);

                        //set question field
                        secArea.setText(accountList.get(target).getSecurityQuestion().get(i).getQuestion());
                        secGrid.add(qLabel, 0, row);
                        secGrid.add(secArea, 0, ++row);

                        //set answer field
                        String answer = accountList.get(target).getSecurityQuestion().get(i).getAnswer();
                        secField.setText("**************");
                        secGrid.add(aLabel, 0, ++row);
                        secGrid.add(secField, 0, ++row);

                        //add a reveal button
                        //Button to reveal security question
                        ToggleButton secReveal = new ToggleButton("Reveal Answer");
                        secReveal.setOnAction(new EventHandler<ActionEvent>()
                        {
                            @Override
                            public void handle(ActionEvent event)
                            {
                                if (secReveal.isSelected())
                                {
                                    secField.setText(answer);
                                    secReveal.setText("Hide Answer");
                                }
                                else
                                {
                                    secField.setText("**************");
                                    secReveal.setText("Reveal Answer");
                                }
                            }
                        });
                        secGrid.add(secReveal, 0, ++row);

                        //advance rows
                        row = row + 5;
                    }
                }
//***** Property Tab select action****************************************************************************************************************************                
                else if(newTab == tab3)
                {
                    
                }
            }
        });
        
        //Set the scene with root
        Scene scene = new Scene(root, 750, 500);//displaying scene with everything in root
        
        //set the stage and show it! Get some popcorn
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    

    
//******ACCOUNT MANAGER METHODS*****************************************************************************************************
    //use this to add accounts in other scences
    public void addAccount(String l,String u, String p, String ur, String d, String c, ArrayList<SecurityQuestion> sq, ArrayList<AdditionalProperties> ap)
    {
        this.accountList.add(new Account(l,u,p,ur,d,c,sq,ap));
    }
    //use this to edit accounts in other scences
    public void editAccount(int i,String l,String u, String p, String ur, String d, String c)
    {
        this.accountList.get(i).setLabel(l);
        this.accountList.get(i).setUsername(u);
        this.accountList.get(i).setPassword(p);
        this.accountList.get(i).setUrl(ur);
        this.accountList.get(i).setDescription(d);
        this.accountList.get(i).setCategory(c);
    }
    //to get the account list
    public <Account> ArrayList getAccountList()
    {
        return this.accountList;
    }
    //use this to clear account list
    public void clearAccount()
    {
        this.accountList.clear();
    }
    //use this to print account details
    public void printAccount(int i)
    {
        this.accountList.get(i).writeOutput();
    }
    //use this to get master password
    public void getMasterPassword(String p)
    {
        this.masterPassword = p;
    }
    //use this to get file location
    public void getDB(String d)
    {
        this.currentDB = d;
    }
    //use this to give the status bar a massage
    public void setStatusBar(String s)
    {
        this.statusBar.setText(s);
    }
    //use this to get the name of a new category
    public void getCategory(String c)
    {
        this.category = c;
    }
    //use this to check if account was created
    public void isCreated(boolean choice)
    {
        this.wasCreated = choice;
    }
    //use this to create a new category
    public void addCategory(TreeItem<String> treeRoot)
    {
        if(!category.isEmpty())
        {
            //add a new folder
            Node treeIcon162 = new ImageView(
            new Image(getClass().getResourceAsStream("resources/FOLDER16.png"))
             );
            TreeItem<String> general = new TreeItem<String>(category,treeIcon162);
            treeRoot.getChildren().add(general);
        }
    }
    //use this to add Security Questions
    public void addQuestion(SecurityQuestion sq, int target)
    {
        this.accountList.get(target).getSecurityQuestion().add(sq);
    }
    //for category actions
    private String handleMouseClicked(MouseEvent event, TreeView treeView)
    {
        Node node = event.getPickResult().getIntersectedNode();
        String name = "All";
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null))
        {
            name = (String) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
            if(name.equalsIgnoreCase("categories"))
            {
                name = "All";
            }
        }
        return name;
    }
    
//***BELOW IS FOR REVEAL BUTTON AND EDIT MENU****************************************
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
 
 //code for paste - TO DO
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
 
   


//******MAIN*****************************************************************************************************
    public static void main(String[] args) throws IOException
    {
        launch(args);
    }
    
}