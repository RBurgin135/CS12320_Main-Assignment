package uk.aber.quiz;

import uk.aber.quiz.questions.FillTheBlanks;
import uk.aber.quiz.questions.MultipleChoice;
import uk.aber.quiz.questions.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class responsible for directly reading and writing the data to and from the data file.
 * This class also distributes the data after reading from the file, as well as collecting the data from the different
 * components of the system when writing to the file.
 */
public class FileManager {
    private final String filename = "data.txt";
    private final ArrayList<Module> modules = new ArrayList<>();

    /**
     * constructor
     */
    public FileManager (){}

    /**
     * responsible for the reading of the text file and assigning
     */
    public ArrayList<Module> load() throws IOException {
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)){
            infile.useDelimiter("\r?\n|\r");

            //loading starts
            Module currentModule = null;
            QuestionBank currentBank = null;
            int lang = 0;
            while(infile.hasNext()) {
                switch (infile.nextLine()){
                    case "m" -> currentModule = loadModule(infile);
                    case "b" -> currentBank = loadBank(infile, currentModule);
                    case "l" -> lang = infile.nextInt();
                    case "q" -> loadQuestion(infile, currentBank, lang);
                }
            }
        }
        return modules;
    }

    /**
     * method to load a module with
     * @return the module which has been loaded
     */
    private Module loadModule(Scanner infile){
        Module m = new Module(infile.nextLine());
        modules.add(m);
        return m;
    }

    /**
     * method for loading a question bank
     * @param module the module the bank is associated with
     * @return the question bank which has been loaded
     */
    private QuestionBank loadBank(Scanner infile, Module module){
        QuestionBank b = new QuestionBank(infile.nextLine());
        module.addBank(b);
        return b;
    }

    /**
     * method for loading a question
     * @param bank the bank the question is a part of
     */
    private void loadQuestion(Scanner infile, QuestionBank bank, int language){
        Question q;
        switch (infile.nextLine()){ //get questions
            case "mc" -> q = new MultipleChoice(); //for multiple choice
            case "ftb"-> q = new FillTheBlanks(); //for fill the blanks
            default -> {
                System.err.println("when reading file - not a valid question type");
                return;
            }
        }
        q.load(infile);
        bank.addQuestion(q, language);
    }

    /**
     * writes the information back to the file
     */
    public void save(Object[] modules) throws IOException{
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {
            for (Object mod : modules) { //cycle modules
                Module m = (Module) mod;
                outfile.println("m\n"+m.getId());
                for (Object bank : m.getBanks()) { //cycle banks
                    QuestionBank b = (QuestionBank) bank;
                    outfile.println("b\n"+b.getId());
                    for (int l=0; l<2; l++) { //cycles through the languages
                        outfile.println("l\n"+l);
                        for (Object question : b.getQuestions()[l]) { //cycle questions
                            Question q = (Question) question;
                            outfile.println("q\n"+q.getSaveData());
                        }
                    }
                }
            }
        }
    }
}
