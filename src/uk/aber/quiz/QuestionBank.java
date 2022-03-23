package uk.aber.quiz;

import uk.aber.quiz.questions.FillTheBlanks;
import uk.aber.quiz.questions.MultipleChoice;
import uk.aber.quiz.questions.Question;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * class responsible for interacting with the contents of a question bank
 */
public class QuestionBank {
    private final Scanner input = new Scanner(System.in);
    private final String id;
    private final ArrayList<ArrayList<Question>> questions = new ArrayList<>();

    /**
     * constructor for a uk.aber.quiz.QuestionBank object
     * @param id the id for the uk.aber.quiz.QuestionBank
     */
    public QuestionBank (String id){
        this.id = id;
        questions.add(new ArrayList<>());
        questions.add(new ArrayList<>());
    }

    /**
     * main method coordinating the editing of the uk.aber.quiz.questions.Question Bank
     * gives the user a branching selection of tools to do so
     */
    public void edit () {
        System.out.println("*Accessed question bank "+id+"*");
        String choice;
        boolean loop = true;
        do {
            showOptions();
            choice = input.nextLine();
            switch (choice){
                case "0" -> listQuestions(chooseLanguage());
                case "1" -> createQuestion();
                case "2" -> removeQuestion();
                case "q" -> loop = false;
                default -> System.err.println("Invalid - not a choice");
            }
        } while (loop);
        System.out.println("*Exiting*");
    }

    /**
     * check if the questions arraylist is empty
     * @return true if the arraylist is empty
     */
    public boolean isEmpty(){
        return questions.get(0).isEmpty() && questions.get(1).isEmpty();
    }

    /**
     * adds a question to the question bank, used when loading
     * @param q the question to be added
     */
    public void addQuestion(Question q, int language){
        questions.get(language).add(q);
    }

    /**
     * gets the array of questions
     * @return the questions array
     */
    public Object[][] getQuestions() {
        Object[] english = questions.get(0).toArray();
        Object[] german = questions.get(1).toArray();
        return new Object[][]{english, german};
    }

    /**
     * shows the available options to the user
     */
    private void showOptions (){
            System.out.println("""
                                        
                    ================
                    0 list questions
                    1 add a question
                    2 remove a question
                    q go back
                    What would you like to do?:\s""");
    }

    /**
     * allows the user to create a new question for use in the question bank
     */
    private void createQuestion(){
        //find the type
        System.out.println("""
                0 multiple choice
                1 fill the blanks
                What kind of question would you like to add:\s""");
        String choice = input.nextLine();
        String language;
        for(int l=0; l<2; l++) {
            if(l == 0){
                language = "English";
            } else{
                language = "German";
            }
            System.out.println("Please enter for the "+language+" version of the Question");
            switch (choice) {
                case "0" -> addQuestion(createMultipleChoice(), l);
                case "1" -> addQuestion(createFillTheBlanks(), l);
                default -> System.err.println("Invalid - not a choice");
            }
        }
        System.out.println("*question added*");
    }

    /**
     * creates a multiple choice variant of question
     */
    private MultipleChoice createMultipleChoice(){
        String prompt = makePrompt();
        int options = getNumOptions();
        prompt+= makeOptions(options);
        String answer = makeAnswer(options);
        return new MultipleChoice(prompt, answer);
    }

    /**
     * gets the number of options for the question from the user
     * @return the number of options for the question
     */
    private int getNumOptions(){
        int options;
        System.out.println("How many options will there be?: ");
        while(true) {
            options = takeInt();
            if(options>1&&options<=10) return options;
            System.err.println("Has to be 2-10");
            System.out.println("Try again: ");
        }
    }

    /**
     * constructs the options from the user input
     * @param options the number of options
     * @return the finished String of options
     */
    private String makeOptions(int options){
        StringBuilder sb = new StringBuilder();
        for (int i=1; i<options+1; i++){
            System.out.println("\tEnter option "+i+": ");
            sb.append("\n\t\t").append(i).append(". ").append(input.nextLine());
        }
        return sb.toString();
    }

    /**
     * gets the answer from the user
     * @param options number of options for the question
     * @return the correct answer for the question
     */
    private String makeAnswer(int options){
        int a;
        System.out.println("Enter the number for the correct answer?: ");
        while(true){
            a = takeInt();
            if(options > a && a >= 1)return String.valueOf(a);
            System.err.println("Has to be 1-"+options);
            System.out.println("Try again: ");
        }
    }

    /**
     * creates a fill the blanks variant of question
     */
    private FillTheBlanks createFillTheBlanks(){
        //prompt
        System.out.println("Write the blanks with a '_'");
        String prompt = makePrompt();

        //blanks
        System.out.println("How many blanks words were there?: ");
        int noOfBlanks = takeInt();
        String[] blanks = makeBlanks(noOfBlanks);

        return new FillTheBlanks(prompt, blanks, noOfBlanks);
    }

    /**
     * lets the user fill in the blanks when making a new question
     * @param noOfBlanks the number of blanks
     * @return String array containing all of the blank words
     */
    private String[] makeBlanks(int noOfBlanks){
        String[] blanks = new String[noOfBlanks];
        for (int i=0; i<noOfBlanks; i++){
            System.out.println("\tEnter the word for blank number "+(i+1)+": ");
            blanks[i] = input.nextLine();
        }
        return blanks;
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
     * used to form the prompt for creating questions
     * @return the prompt
     */
    private String makePrompt(){
        System.out.println("What would you like the prompt to be?: ");
        return input.nextLine();
    }

    /**
     * shows information about all the questions associated with the bank
     */
    private void listQuestions (int language){
        System.out.println("Questions for bank "+id+": ");
        int i = 1;
        for (Question q : questions.get(language)) {
            if (q != null) {
                System.out.println("\t" + i + ". " + q.toString());
            }
            i++;
        }
    }

    /**
     * allows the user to remove a question from the question bank
     */
    private void removeQuestion (){
        int lang = chooseLanguage();
        listQuestions(lang);
        System.out.println("Type the associated number for the question you would like to remove:\n(Note this removes the other language equivalent) ");
        int i = takeInt()-1;
        questions.get(0).remove(i);
        questions.get(1).remove(i);
        System.out.println("*question removed*");
    }

    /**
     * lets the user choose which language to access
     * @return the int representing the language
     */
    private int chooseLanguage(){
        System.out.println("""
                Which language:
                1: English
                2: German""");
        return takeInt()-1;
    }

    /**
     * returns the value for the id
     * @return the id
     */
    public String getId(){
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}