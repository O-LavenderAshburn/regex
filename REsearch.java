import java.io.*;
import java.util.ArrayList;
import java.util.*;


public class REsearch{

    // Deque to keep track of possible current and next states
    private static Deque deque;

    public static void main(String[] args){

        //Pointers and marks 
        int mark =0;
        int lineNum =1;
        Boolean succces;
        String currentLine;

        //print usage and exit
        if(args.length < 1){

            //error
            System.out.println("Usage: ");
            return;
        }

        //File to search
        String filename = args[0];

        try{

            //read the file 
            FileReader fr = new FileReader(filename);
            BufferedReader lineReader = new BufferedReader(fr); 

            //Read the first line
            String line = lineReader.readLine();


            //while we have lines to process
            while(line != null){

                //while we still have  a sub-string to search
                while(mark < line.length()){
                
                    //shorten the current line to search
                    currentLine = line.substring(mark);

                    //seaerch the current line
                    succces = search(currentLine);

                    if(succces){

                        //exit out of while loop 
                        System.out.println("Pattern found on line: "+ lineNum);
                        break;
                    }
                    //increment mark
                    mark++;
                }

                //get the next line to process and increment lineNumber and reset mark
                line = lineReader.readLine();
                lineNum++;
                mark = 0;

            }

        } catch(Exception e){
            //return
            System.err.println(e);
            return;
        }


    }


    public static Boolean search(String string){
        //<IMPLEMENT SEARCH LOGIC HERE>

        return false;
    }


    

}