package uk.aber.quiz;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * class responsible for interacting with the contents of a module
 */
public class Module {
    private final Scanner input = new Scanner(System.in);
    private final String id;
    private final ArrayList<QuestionBank> banks = new ArrayList<>();

    /**
     * constructor for a uk.aber.quiz.Module object
     * @param id the id for the module
     */
    public Module (String id){
        this.id = id;
    }

    /**
     * main method coordinating the editing of the module
     */
    public void edit (){
        System.out.println("*Accessed module "+id+"*");
        String choice;
        boolean loop = true;
        do {
            showOptions();
            choice = input.nextLine();
            switch (choice){
                case "0" -> listBanks();
                case "1" -> editBank();
                case "2" -> createBank();
                case "3" -> removeBank();
                case "q" -> loop = false;
                default -> System.err.println("Invalid - not a choice");
            }
        } while (loop);
        System.out.println("*Exiting*");
    }

    /**
     * adds a question bank to the module, used when loading
     * @param b the bank to be added
     */
    public void addBank(QuestionBank b){
        banks.add(b);
    }

    /**
     * check if the banks arraylist is empty
     * @return true if the arraylist is empty
     */
    public boolean isEmpty(){
        return banks.isEmpty();
    }

    /**
     * gets the array of banks associated with the module
     * @return the banks array
     */
    public Object[] getBanks() {
        return banks.toArray();
    }

    /**
     * used to find a question bank associated with an id
     * @param id the id to be found
     * @return a uk.aber.quiz.QuestionBank associated with the provided id
     */
    public QuestionBank findBank(String id){
        QuestionBank found = null;
        for (QuestionBank b : banks){
            if (b.getId().equals(id)){
                found = b;
                break;
            }
        }
        return found;
    }

    /**
     * check to see if the specified id is associated with a uk.aber.quiz.QuestionBank in banks
     * @param id the id to be checked
     * @return true if the id is in use
     */
    public boolean idInBanks(String id){
        if (id == null) return false;
        for (QuestionBank b : banks){
            if (b!= null && b.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    /**
     * shows the available options to the user
     */
    private void showOptions (){
        System.out.println("""
                                
                ================
                0 list question banks
                1 edit a question bank
                2 create a new question bank
                3 delete an empty question bank
                q go back
                What would you like to do?:\s""");
    }

    /**
     * shows information about all question banks associated with this module
     */
    public void listBanks(){
        System.out.println("Question banks for module "+id+": ");
        for (QuestionBank b : banks){
            if(b != null) {
                System.out.println("\t" + b.toString());
            }
        }
    }

    /**
     * allows the user to enter into editing a uk.aber.quiz.QuestionBank
     */
    private void editBank(){
        System.out.println("Type the id for the bank which you would like to edit: ");
        String id = input.nextLine();
        if(idInBanks(id)){
            findBank(id).edit();
        } else {
            System.err.println("There is no question bank with that id");
        }
    }

    /**
     * allows the user to create a new uk.aber.quiz.QuestionBank to the module
     */
    private void createBank(){
        //find the id
        System.out.println("What is the id of the new bank: ");
        String id = input.nextLine();
        //check if in use
        if (!idInBanks(id)) {
            banks.add(new QuestionBank(id));
            System.out.println("*Question bank added*");
        } else {
            System.err.println("Invalid - there is a question bank registered from that id already");
        }
    }

    /**
     * allows the user to delete a uk.aber.quiz.QuestionBank from the module
     * only if it is empty
     */
    private void removeBank(){
        System.out.println("Type the id for the bank which you would like to delete: ");
        String id = input.nextLine();
        if(idInBanks(id)){
            QuestionBank b = findBank(id);
            if(b.isEmpty()){
                banks.remove(b);
            } else {
                System.err.println("The bank specified is not empty");
                return;
            }
        } else {
            System.err.println("There is no question bank with that id");
            return;
        }
        System.out.println("*"+id +" removed*");
    }

    /**
     * gets the value for id
     * @return id
     */
    public String getId(){
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
