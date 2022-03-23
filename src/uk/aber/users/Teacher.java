package uk.aber.users;

import java.util.ArrayList;
import java.util.Scanner;
import uk.aber.quiz.Module;

/**
 * uk.aber.users.Teacher class responsible for being a launchpad for the teacher user to interact with the system
 */
public class Teacher {
    private final Scanner input = new Scanner(System.in);
    private final ArrayList<Module> modules;

    /**
     * constructor for a uk.aber.users.Teacher object
     * @param modules the existing modules loaded from the text file
     */
    public Teacher (ArrayList<Module> modules){
        this.modules = modules;
    }

    /**
     * main method coordinating how the user interacts with the modules
     * gives the user a branching choice to select the task they want to complete
     */
    public Object[] main () {
        boolean loop = true;
        do {
            showOptions();
            switch (input.nextLine()) {
                case "0" -> listModules();
                case "1" -> editModule();
                case "2" -> listQnBanks();
                case "3" -> createModule();
                case "4" -> removeModule();
                case "q" -> loop = false;
                default -> System.err.println("Invalid - not a choice");
            }
        } while (loop);
        System.out.println("*** Goodbye ***\nThank you for using the system.");
        return modules.toArray();
    }

    /**
     * displays all the options for the user
     */
    private void showOptions (){
        System.out.println("""
                
                ================
                0 list modules
                1 edit module
                2 list question banks from a module
                3 add a new module
                4 remove an empty module
                q end the session
                What would you like to do?:\s""");
    }

    /**
     * shows information about all the modules
     */
    void listModules(){
        System.out.println("Modules: ");
        for (Module m : modules){
            if (m != null) {
                System.out.println("\t" + m.toString());
            }
        }
    }

    /**
     * allows the user to enter into editing a module
     */
    private void editModule(){
        System.out.println("Type the id for the module which you would like to edit: ");
        String id = input.nextLine();
        if(idInModules(id)){
            findModule(id).edit();
        } else {
            System.err.println("There is no module with that id");
        }
    }

    /**
     * shows information of all the question banks associated with a specified module
     */
    private void listQnBanks(){
        System.out.println("Type the id for the module which you would like to list the question banks for: ");
        String id = input.nextLine();
        if(idInModules(id)){
            findModule(id).listBanks();
        } else {
            System.err.println("There is no module with that id");
        }
    }

    /**
     * allows the user to create a new module
     */
    private void createModule(){
        //find the id
        System.out.println("What is the id of the new module: ");
        String id = input.nextLine();
        //check if in use
        if (!idInModules(id)) {
            modules.add(new Module(id));
            System.out.println("*module added*");
        } else {
            System.err.println("Invalid - there is a module registered from that id already");
        }
    }

    /**
     * allows the user to delete a uk.aber.quiz.Module from modules
     */
    private void removeModule(){
        System.out.println("Type the id for the module which you would like to delete: ");
        String id = input.nextLine();
        if(idInModules(id)){
            Module m = findModule(id);
            if(m.isEmpty()){
                modules.remove(m);
            } else {
                System.err.println("The Module specified is not empty");
            }
        } else {
            System.err.println("There is no Module with that id");
        }
    }

    /**
     * check to see if the specified id is associated with a uk.aber.quiz.Module
     * @param id the id to be checked
     * @return true if the id is in use
     */
    boolean idInModules(String id){
        if (id == null) return false;
        for (Module m : modules){
            if (m!= null && m.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    /**
     * used to find a question bank associated with an id
     * @param id the id to be found
     * @return a uk.aber.quiz.QuestionBank associated with the provided id
     */
    Module findModule(String id){
        Module found = null;
        for (Module m : modules){
            if (m.getId().equals(id)){
                found = m;
                break;
            }
        }
        return found;
    }
}
