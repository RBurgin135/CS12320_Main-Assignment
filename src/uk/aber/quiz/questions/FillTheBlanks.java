package uk.aber.quiz.questions;

import java.util.Scanner;

public class FillTheBlanks extends Question {
    private String[] studentAttempt;
    private String[] answers;
    private int noOfBlanks;

    /**
     * empty constructor
     */
    public FillTheBlanks(){ super();}
    /**
     * constructor for the uk.aber.quiz.questions.FillTheBlanks subclass
     * @param prompt the prompt the student will answer
     * @param answers array containing the words which correspond to the blanks
     * @param noOfBlanks the number of blanks
     */
    public FillTheBlanks(String prompt, String[] answers, int noOfBlanks){
        super(prompt);
        this.noOfBlanks = noOfBlanks;
        this.answers = answers;
        studentAttempt = new String[noOfBlanks];
    }

    /**
     * lets the user attempt the question
     */
    public void attempt(){
        answered = true;
        System.out.println("Please enter each word that should be in each blank");
        for (int i=0 ; i<noOfBlanks; i++){
            System.out.println("Blank "+(i+1)+": ");
            studentAttempt[i] = input.nextLine();
        }
    }

    /**
     * gets the relevant data for saving the question
     * @return a String containing the data
     */
    public String getSaveData(){
        StringBuilder sb = new StringBuilder();
        sb.append("ftb\n").append(prompt).append("\n").append(noOfBlanks); //type+prompt+number of blanks
        for(String a : answers){ //adds list of answers
            if (a!=null){
                sb.append("\n").append(a);
            }
        }
        return sb.toString();
    }

    /**
     * method for loading a fill the blanks question
     */
    public void load(Scanner infile){
        if(infile==null) return;
        prompt = infile.nextLine();
        noOfBlanks = Integer.parseInt(infile.nextLine());
        answers = new String[noOfBlanks];
        for (int i=0; i<noOfBlanks; i++){
            answers[i] = infile.nextLine();
        }
        studentAttempt = new String[noOfBlanks];
    }

    /**
     * checks to see if the provided attempt is correct
     * @return true if the student is correct
     */
    public boolean isCorrect (){
        boolean correct = true;
        for (int i=0; i<noOfBlanks; i++){
            if(studentAttempt[i]==null || !studentAttempt[i].equals(answers[i])) {
                correct = false;
                break;
            }
        }
        return correct;
    }
}
