package uk.aber.quiz;

import uk.aber.quiz.questions.Question;

import java.util.*;

public class Quiz {
    private final Scanner input = new Scanner(System.in);
    private final Question[] questions;
    private final long startTime;
    private int pointer;
    private final int len;
    private Question currentQn;

    public Quiz(Question[] questions){
        this.questions = questions;
        pointer = 0;
        len = questions.length;
        findCurrentQn();
        startTime = System.nanoTime();
    }

    /**
     * main function responsible for the selection of an action when taking a quiz
     */
    public void takeQuiz(){
        String choice;
        do {
            showOptions();
            choice = input.nextLine();
            switch (choice) {
                case "1" -> goForward();
                case "2" -> goBack();
                case "3" -> enterAnswer();
                case "q" -> endSession();
                default  -> System.err.println("Invalid - not an option");
            }
        } while(!choice.equals("q"));
    }

    /**
     * lets the user move to the next question
     */
    private void goForward(){
        System.out.println("NEXT Question");
        if (pointer < len-1){
            pointer++;
        } else {
            pointer = 0;
        }
        findCurrentQn();
    }

    /**
     * lets the user move to the previous question
     */
    private void goBack(){
        System.out.println("PREVIOUS Question");
        if (pointer > 0){
            pointer--;
        } else {
            pointer = len-1;
        }
        findCurrentQn();
    }

    /**
     * allows the user to enter an answer for the current question
     */
    private void enterAnswer() {
        currentQn.attempt();
    }

    /**
     * shows the current question
     */
    private void showQn(){
        System.out.print("\n==== QUESTION ");
        System.out.print(pointer+1);
        System.out.print(" of ");
        System.out.print(len);
        System.out.print(" ====\n");
        System.out.println(currentQn.toString());
    }

    /**
     * shows the options available to the user taking the quiz
     */
    private void showOptions(){
        showQn();
        System.out.println("""
                
                Would would you like to do:
                1 move to the next question
                2 go back to the previous question
                3 enter answer for current question
                q end session
                """);
    }

    /**
     * ends the quiz session
     */
    private void endSession(){
        int score = calcScore();
        int unanswered = calcUnanswered();
        String elapsedTime = calcElapsedTime();
        System.out.println("\n\n=== Final Results ===");
        System.out.println("Score: "+score+"/"+len);
        System.out.println("Elapsed time: "+elapsedTime);
        if (unanswered>0){
            System.out.println(unanswered+" unanswered questions");
        }
    }

    /**
     * iterates through the questions and finds if the answers were correct
     * @return the score from the quiz
     */
    private int calcScore(){
        int score = 0;
        for (Question q : questions){
            if (q.isCorrect()){
                score ++;
            }
        }
        return score;
    }

    /**
     * find the time that elapsed since the start of the quiz
     * @return the elapsed time
     */
    private String calcElapsedTime(){
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        int sec;
        int min=0;
        for (sec = 0; elapsedTime > 1000000000; elapsedTime-=1000000000){
            sec++;
            if (sec >= 60){
                min ++;
                sec -=60;
            }
        }
        return min+" minutes, and "+sec+" seconds";
    }

    /**
     * finds the number of questions that the user has left unanswered
     * @return number of questions left unanswered
     */
    private int calcUnanswered(){
        int tally=0;
        for (Question q : questions) {
            if(!q.isAnswered()) tally++;
        }
        return tally;
    }

    /**
     * finds the current question
     */
    private void findCurrentQn(){
        currentQn = questions[pointer];
    }
}
