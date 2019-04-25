package accountmanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

/*
 *
 * 
 * Damian Zylski
 * Account class
 */

//class account

public class Account implements Comparable<Account>
{
    //attributes
    private String label;
    private String username;
    private String password;
    private String url;
    private String description;
    private String category;
    private final StringProperty userNamep = new SimpleStringProperty();
    private final StringProperty passwordp = new SimpleStringProperty();
    AccountManager account = new AccountManager();
    
    //constructors
    public Account() //defualt
    {
        label = "None";
        username = "None";
        password = "None";
        url = "None";
        description = "None";
        category = "None";
    }
    public Account(String l, String u, String p, String ur, String d, String c)
    {
        label = l;
        username = u;
        password = p;
        url = ur;
        description = d;
        category = c;
    }
    //mutators
    //label
    public void setLabel(String l)
    {
        label = l;
    }
    //username
    public void setUsername(String u)
    {
        username = u;
    }
    //password
    public void setPassword(String p)
    {
        password = p;
    }
    //url
    public void setUrl(String ur)
    {
        url = ur;
    }
    //description
    public void setDescription(String d)
    {
        description = d;
    }
    //category
    public void setCategory(String c)
    {
        category = c;
    }
    //accessors
    //label
    public String getLabel()
    {
        return label;
    }
    //username
    public String getUsername()
    {
        return username;
    }
    //password
    public String getPassword()
    {
        return password;
    }
    
    //url
    public String getUrl()
    {
        return url;
    }
    //description
    public String getDescription()
    {
        return description;
    }
    //category
    public String getCategory()
    {
        return category;
    }
    
    //description
    public String getInfo()
    {
        String info = "Label: " + label;
        info += "\nUsername: " + username;
        info += "\nURL/Location: " + url;
        info += "\nDescription: " + description;
        info += "\nCategory: " + category;
        return info;
    }
    
    //mask
    public String getMask()
    {
        return "*******";
    }
    
    //I can assure you whatever I was doing here did not work
    public ToggleButton getButton()
    {
        ToggleButton btn = new ToggleButton("Reveal");
        return btn;
    }
    
    //Output
    public String writeOutput()
    {
        System.out.println("Label: " + label);
        System.out.println("Username: " + username);
        System.out.println("URL/Location: " + url);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
        return "";
    }
    //Same label?
    public boolean hasSameLabel(Account a)
    {
        if(this.label.equalsIgnoreCase(a.label))
        {
            return true;
        }
        return false;
    }
    //CompareTo
    @Override
    public int compareTo(Account a)
    {//comparing labels
        if(this.label.compareTo(a.label) > 1) //greater
        {
            return 1;
        }
        else if(this.label.compareTo(a.label) < 1) //lesser
        {
            return -1;
        }
        else if(this.label.compareTo(a.label) == 0) //equal
        {
            return 0; 
        }
        return 0; //default
    }
    
    public boolean compareToString(String a)
    {//comparing labels
        if(label.equals(a) == true){
            return true;
        }
        else{
            return false;
        }
    }
    
    //toString
    @Override
    public String toString()
    {
        return "Quack!";
    }
    
    //stuff below has to do with reveal password
    public final StringProperty userNameProperty() {
            return this.userNamep;
        }

        public final java.lang.String getUserName() {
            return this.userNameProperty().get();
        }

        public final void setUserName(final java.lang.String userName) {
            this.userNameProperty().set(userName);
        }

        public final StringProperty passwordProperty() {
            return this.passwordp;
        }

        public final java.lang.String getPasswordp() {
            return this.passwordProperty().get();
        }

        public final void setPasswordp(final java.lang.String passwordp) {
            this.passwordProperty().set(passwordp);
        }
    
}
