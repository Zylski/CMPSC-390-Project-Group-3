/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accountmanager;

/**
 *
 * @author barry
 */
public class SecurityQuestion {
    
    private String Question;
    private String Answer;
    
    public SecurityQuestion(){
        Question = "";
        Answer = "";
    }
    
    public SecurityQuestion(String q, String a){
        Question = q;
        Answer = a;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }
    
    public void writeOutput(){
        if(Question.equals("")){
            
        }
        else{
        System.out.println("Security question is: " + Question);
        }
        if(Question.equals("")){
            
        }
        else{        
        System.out.println("Secuirty question answer is: " + Answer);
        }
    }
    
}
