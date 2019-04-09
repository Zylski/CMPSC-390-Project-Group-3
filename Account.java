package accountmanager;

/*
 *
 * 
 * Damian Zylski
 * Account class
 */

//class account

public class Account 
{
    //attributes
    private String label;
    private String username;
    private String password;
    private String url;
    private String description;
    
    //constructors
    public Account() //defualt
    {
        label = "None";
        username = "None";
        password = "None";
        url = "None";
        description = "None";
    }
    public Account(String l, String u, String p, String ur, String d)
    {
        label = l;
        username = u;
        password = p;
        url = ur;
        description = d;
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
    
    //Output
    public String writeOutput()
    {
        System.out.println("Label: " + label);
        System.out.println("Username: " + username);
        System.out.println("URL/Location: " + url);
        System.out.println("Description: " + description);
        return "s";
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
    public int compareTo(Account a)
    {//comparing labels
        if(this.label.compareToIgnoreCase(a.label) > 1) //greater
        {
            return 1;
        }
        else if(this.label.compareToIgnoreCase(a.label) < 1) //lesser
        {
            return -1;
        }
        else if(this.label.compareToIgnoreCase(a.label) == 0) //equal
        {
            return 0; 
        }
        return 0; //default
    }
    
    //toString
    @Override
    public String toString()
    {
        return "Quack!";
    }
    
}
