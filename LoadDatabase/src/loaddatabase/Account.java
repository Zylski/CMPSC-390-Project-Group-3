/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaddatabase;

import java.util.ArrayList;

/**
 *
 * @author barry
 */

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
    private String category;
    private ArrayList<SecurityQuestion> SecurityQuestions = new ArrayList<SecurityQuestion>();
    private ArrayList<AdditionalProperties> AdditionalProperties = new ArrayList<AdditionalProperties>();
    
    //constructors
    public Account() //defualt
    {
        label = "None";
        username = "None";
        password = "None";
        url = "None";
        description = "None";
        category = "";
        //SecurityQuestions.add(new SecurityQuestion());
        //AdditionalProperties.add(new AdditionalProperties());
    }
    public Account(String l, String u, String p, String ur, String d, ArrayList<SecurityQuestion> sq, ArrayList<AdditionalProperties> ap)
    {
        label = l;
        username = u;
        password = p;
        url = ur;
        description = d;
        SecurityQuestions = sq;
        AdditionalProperties = ap;
        
    }
    //mutators
    //label

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<SecurityQuestion> getSecurityQuestion() {
        return SecurityQuestions;
    }

    public void setSecurityQuestion(ArrayList<SecurityQuestion> SecurityQuestion) {
        this.SecurityQuestions = SecurityQuestion;
    }

    public ArrayList<AdditionalProperties> getAdditionalProperties() {
        return AdditionalProperties;
    }

    public void setAdditionalProperties(ArrayList<AdditionalProperties> AdditionalProperties) {
        this.AdditionalProperties = AdditionalProperties;
    }
    
    
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
    public void writeOutput()
    {
        System.out.println("Label: " + label);
        System.out.println("Username: " + username);
        System.out.println("URL/Location: " + url);
        System.out.println("Description: " + description);
        if (category.equals("")){
            System.out.println("Category: General");
        }
        else{
            System.out.println("Category: " + category);   
        }
        
        for (int i = 0;i<SecurityQuestions.size();i++){
            SecurityQuestions.get(i).writeOutput();
        }
        
        for (int i = 0;i<AdditionalProperties.size();i++){
            AdditionalProperties.get(i).writeOutput();
        }
        
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
    
}


