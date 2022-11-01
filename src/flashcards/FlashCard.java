/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flashcards;

/**
 *
 * @author Coleen Kunene
 */
public class FlashCard {


    
    private String question;
    private String answer;
    
    public FlashCard(String q, String a){
    
        question = q;
        answer = a;
    }
        public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    
}
