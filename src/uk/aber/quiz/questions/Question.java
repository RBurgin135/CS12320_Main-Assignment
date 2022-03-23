package uk.aber.quiz.questions;

import java.util.Scanner;

/**
 * a super-class responsible for holding all the information related to each question in the QuestionBanks and Quizzes.
 */
public abstract class Question implements QuestionInterface{
    String prompt;
    final Scanner input = new Scanner(System.in);
    boolean answered;

    /**
     * empty constructor
     */
    public Question(){
        answered = false;
    }
    /**
     * constructor for the uk.aber.quiz.questions.Question Superclass
     * @param prompt the prompt the student will answer
     */
    public Question(String prompt){
        answered = false;
        this.prompt = prompt;
    }

    /**
     * tests whether the question has been answered
     * @return returns true if the question has been answered
     */
    public boolean isAnswered(){
        return answered;
    }

    /**
     * gets the prompt for use showing to the user
     * @return the prompt of the question
     */
    @Override
    public String toString() {
        return prompt;
    }
}
