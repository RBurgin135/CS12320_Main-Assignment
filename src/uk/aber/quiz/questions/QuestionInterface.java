package uk.aber.quiz.questions;

import java.util.Scanner;

/**
 * an interface implemented by all Questions
 */
public interface QuestionInterface {
    /**
     * whats shown to the user when its called
     * @return the prompt of the qn
     */
    String toString();

    /**
     * checks the actual answer against the answer the user has given
     * @return true if correct
     */
    boolean isCorrect();

    /**
     * get the relevant data to be saved
     * @return the save data
     */
    String getSaveData();

    /**
     * loads the data from the given file
     * @param infile the data of the file
     */
    void load(Scanner infile);

    /**
     * allows the user to attempt answering the question
     */
    void attempt();

    /**
     * getter for answered
     * @return boolean value of answered
     */
    boolean isAnswered();
}