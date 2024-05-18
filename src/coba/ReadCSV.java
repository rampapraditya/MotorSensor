package coba;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author RAMPA
 */
public class ReadCSV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("MAXMINX.csv"));
        sc.useDelimiter(",");   //sets the delimiter pattern  
        while (sc.hasNext()) //returns a boolean value  
        {
            System.out.print(sc.next());  //find and returns the next complete token from this scanner  
        }
        sc.close();  //closes the scanner  
    }


}
