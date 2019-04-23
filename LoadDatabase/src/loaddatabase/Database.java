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
public class Database {
        private String masterPassword;
        private ArrayList<Account> account = new ArrayList<Account>();

                
        public Database(){
            masterPassword = "";
            account.add(new Account());
        }
    
    public String getMasterpassword() {
        return masterPassword;
    }

    public void setMasterpassword(String masterpassword) {
        this.masterPassword = masterpassword;
    }

    public ArrayList<Account> getAccount() {
        return account;
    }

    public void setAccount(ArrayList<Account> account) {
        this.account = account;
    }
    
    public void addAccount(Account a){
        account.add(a);
    }
    public void deleteAccount(Account a){
        //input
    }
    
    public void writeOutput(){
        System.out.println("Master Password: " + masterPassword);
        System.out.println("");
        for (int i = 0;i<account.size();i++){
        account.get(i).writeOutput();
        System.out.println("");
        }
    }

}
