package uk.aber.users;

import java.util.Random;
import java.util.Scanner;
import uk.aber.quiz.Module;
import uk.aber.quiz.QuestionBank;
import uk.aber.quiz.questions.Question;
import uk.aber.quiz.Quiz;

/**
 * uk.aber.users.Student class responsible for being a launchpad for the student user to interact with the system
 */
public class Student {
    private final Scanner input = new Scanner(System.in);
    private int language;
    private final Teacher teacher;

    public Student(Teacher teacher){
        this.teacher = teacher;
    }

    /**
     * main method responsible for user choosing their actions
     */
    public void main(){
        String choice;
        language = chooseLanguage();
        do {
            Quiz q = chooseQuiz();
            q.takeQuiz();
            System.out.println("Would you like to take another quiz?(Y): ");
            choice = input.nextLine();
        } while(choice.equals("Y"));
    }

    /**
     * used to choose which language to use
     * @return integer value for the language
     */
    private int chooseLanguage(){
        System.out.println("""
                Please enter the corresponding number to the language you want to take the quiz in:
                1: English
                2: German""");
        return takeInt()-1;
    }

    /**
     * lets the user choose a quiz to take
     * @return the quiz chosen
     */
    private Quiz chooseQuiz(){
        Module m = chooseModule();
        QuestionBank b = chooseBank(m);
        Object[] objects = b.getQuestions()[language];
        int len = objects.length;
        Question[] questions = new Question[len];
        for(int i=0; i<len; i++){
            questions[i] = (Question) objects[i];
        }
        questions = cutDown(shuffle(questions));
        return new Quiz(questions);
    }

    /**
     * allows the user to choose the module to take the quiz from
     * @return the module
     */
    private Module chooseModule(){
        teacher.listModules();
        System.out.println("Please enter the module id for the quiz: ");
        String x;
        while (true){
            x = input.nextLine();
            if(teacher.idInModules(x)) return teacher.findModule(x);
            System.err.println("Module does not exist");
            System.out.println("Try again: ");
        }
    }

    /**
     * allows the user to choose the question bank to take the quiz from
     * @param m the module the bank is associated with
     * @return the question bank
     */
    private QuestionBank chooseBank(Module m){
        m.listBanks();
        System.out.println("Please enter the question bank id for the quiz: ");
        String x;
        while (true){
            x = input.nextLine();
            if(m.idInBanks(x)) return m.findBank(x);
            System.err.println("Bank does not exist");
            System.out.println("Try again: ");
        }
    }

    /**
     * gets an integer value from the user safely
     * @return integer input from the user
     */
    private int takeInt(){
        String s;
        while(true) {
            s = input.nextLine();
            try {
                return Integer.parseInt(s);
            } catch(NumberFormatException e){
                System.err.println(s +" is not int");
                System.out.println("try again: ");
            }
        }
    }

    /**
     * shuffles the array by picking two random indexes and swapping the associated elements
     * return the shuffled array
     */
    private Question[] shuffle(Question[] questions){
        Random random = new Random();
        int swapA, swapB; Question temp;
        int len = questions.length;

        for(int i=0; i<(len*2); i++){
            swapA = random.nextInt(len);
            swapB = random.nextInt(len);
            if(swapA!=swapB){
                temp = questions[swapA];
                questions[swapA] = questions[swapB];
                questions[swapB] = temp;
            }
        }
        return questions;
    }

    /**
     * reduces the number of questions to that of the users input
     * @param questions the shuffled questions
     * @return the reduced questions
     */
    private Question[] cutDown(Question[] questions){
        System.out.println("How many questions would you like to take from this question bank: ");
        int Q = takeInt();
        if (Q> questions.length) Q= questions.length;
        Question[] result = new Question[Q];
        if (Q >= 0) System.arraycopy(questions, 0, result, 0, Q);
        return result;
    }
}
