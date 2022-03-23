package uk.aber.application;

import uk.aber.quiz.FileManager;
import uk.aber.users.Student;
import uk.aber.users.Teacher;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class is responsible for starting execution of the code, and for letting the system determine
 * whether the user is a Student or a Teacher, and thus garner the capabilities to their need.
 * It is also responsible for initiating the loading of the data from the file.
 */
public class Application {
    private final Scanner input = new Scanner(System.in);
    private final FileManager man;
    private final Teacher teacher;
    private final Student student;

    public Application() throws IOException {
        man = new FileManager();
        teacher = new Teacher(man.load());
        student = new Student(teacher);
    }

    /**
     * main function for initiating the program
     */
    public static void main(String[] args) throws IOException {
        Application app = new Application();
        app.run();
    }

    /**
     * method for finding whether the user is a Student or a Teacher
     * @throws IOException if the file is not found in runTeacher
     */
    private void run() throws IOException {
        boolean chosen;
        System.out.println("Are you a student or a teacher?: (S/T)");
        do {
            chosen = true;
            switch(input.nextLine()) {
                case "T" -> runTeacher();
                case "S" -> runStudent();
                default -> {
                    System.err.println("Invalid input - please try again");
                    chosen=false;
                }
            }
        } while (!chosen);
    }

    /**
     * responsible for initiating behaviour involving a teacher user
     * @throws IOException if the file to write to is not found
     */
    private void runTeacher() throws IOException {
        man.save(teacher.main());
    }

    /**
     * responsible for initiating behaviour involving a student user
     */
    private void runStudent(){
        student.main();
    }
}
