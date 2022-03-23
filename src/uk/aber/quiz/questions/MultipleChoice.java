package uk.aber.quiz.questions;

import java.util.Scanner;

/**
 *
 */
public class MultipleChoice extends Question{
    private String studentAttempt;
    private String answer;

    /**
     * empty constructor
     */
    public MultipleChoice(){super();}
    /**
     * constructor for the uk.aber.quiz.questions.MultipleChoice subclass
     * @param prompt the prompt the student will answer
     * @param answer the correct answer to the prompt
     */
    public MultipleChoice(String prompt, String answer){
        super(prompt);
        this.answer = answer;
        studentAttempt = "NOT ATTEMPTED";
    }

    /**
     * lets the user attempt the question
     */
    public void attempt(){
        answered = true;
        System.out.println("Which choice do you think is correct?: ");
        studentAttempt = input.nextLine();
    }

    /**
     * checks to see if the provided attempt is correct
     * @return true if the student is correct
     */
    public boolean isCorrect (){
        if(studentAttempt == null) return false;
        return studentAttempt.equals(answer);
    }

    /**
     * gets the relevant data for saving the question
     * @return a String containing the data
     */
    public String getSaveData(){
        StringBuilder sb = new StringBuilder();
        sb.append("mc\n").append(prompt).append("\nend\n").append(answer); //type+prompt+answer
        return sb.toString();
    }

    /**
     * method for loading a multiple choice question
     */
    public void load(Scanner infile){
        StringBuilder sb = new StringBuilder();
        sb.append(infile.nextLine()); //prompt
        String x;
        while(true){ //all options added to prompt
            x = infile.nextLine();
            if(x.equals("end")) break;
            sb.append("\n").append(x);
        }
        prompt = sb.toString();
        answer = infile.nextLine();
    }
}
