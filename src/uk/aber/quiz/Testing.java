package uk.aber.quiz;
import java.util.*;

public class Testing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //If the next is a int, print found and the int
            if (scanner.hasNextInt()) {
                System.out.println("Found Int Value: "+scanner.nextInt());
            }else {
                //If no int is found, print "Not Found:" and the token
                System.out.println("Not Found Int value: " + scanner.next());
                System.out.println("remove" + scanner.next());
            }
        }
    }
}
