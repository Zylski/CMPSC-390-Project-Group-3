/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaddatabase;

/**
 *
 * @author barry
 */
public class AdditionalProperties {
        private String property;
        private String propertyAnswer;

        
        
    public AdditionalProperties(){
        property = "";
        propertyAnswer = "";
    }
    public AdditionalProperties(String p, String pa){
        property = p;
        propertyAnswer = pa;
    }
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyAnswer() {
        return propertyAnswer;
    }

    public void setPropertyAnswer(String propertyAnswer) {
        this.propertyAnswer = propertyAnswer;
    }
        
     public void writeOutput(){
        if(property.equals("")){
            
        }
        else{
        System.out.println("Additional property is: " + property);
        }
        if(propertyAnswer.equals("")){
            
        }
        else{        
        System.out.println("Additional property answer is: " + propertyAnswer);
        }
    }
    
}
