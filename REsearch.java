import java.io.*;
import java.util.ArrayList;
import java.util.*;


public class REsearch{

    // Deque to keep track of possible current and next states
    private static Deque deque;
    private static ArrayList<State> states = new ArrayList<State>();

    public static void main(String[] args){

        //Pointers and marks 
        int mark =0;
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
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(isr);
    
            //read in and create states
            String stateInput = reader.readLine();

            // while we have states to read in 
            while(stateInput != null){

                String[] stateInfo = stateInput.split(",");
                char symbol = stateInfo[0].charAt(0);
                int n1 =Integer.parseInt(stateInfo[1]);
                int n2 =Integer.parseInt(stateInfo[2]);

                //create state and add to list of states
                State newState = new State(symbol, n1 , n2);
                states.add(newState);

                stateInput = reader.readLine();

            }
            reader.close();

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
                        System.out.println(line);
                        break;
                    }
                    //increment mark
                    mark++;
                }

                //get the next line to process and increment lineNumber and reset mark
                line = lineReader.readLine();
                mark = 0;

            }

            lineReader.close();

        } catch(Exception e){
            //return
            System.err.println(e);
            return;
        }


    }


    public static Boolean search(String string){
        //looking at
        int point = 0;
        char[] chars = string.toCharArray();

        while(true){
        //set the current pointer 
        char current = chars[point];

        //<IMPLEMENT SEARCH LOGIC HERE>

            break;
        }



        return false;
    }


    

}