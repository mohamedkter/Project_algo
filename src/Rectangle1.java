import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * The class containing the main method, the entry point of the application. It
 * will take a command line file argument which include the commands to be read
 * and creates the appropriate SkipList object and outputs the correct results
 * to the console as specified in the file.
 *
 * @author (Your name here)
 * 
 * @version (Date here)
 */
public class Rectangle1 {

    /**
     * The entry point of the application.
     *
     * @param args
     *            The name of the command file passed in as a command line
     *            argument.
     */
    public static void main(String[] args) {
        // the file object
        

        

        
        try {
        	
            CommandProcessor cmdProc = new CommandProcessor();
            
            try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\GAMMA\\.eclipse\\org.eclipse.tips.state\\Project_Starter\\Data\\P1test1.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                   
                    if (!line.trim().isEmpty()) {
                    	cmdProc.processor(line.trim())	;
                    }
                   
                    }
                   
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    
}
    
}
