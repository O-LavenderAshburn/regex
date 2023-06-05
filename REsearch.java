import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class REsearch{
    public static final char BRANCH = 'γ';
    public static final char WILDCARD = 'α';

    // Deque to keep track of possible current and next states
    private static Deque deque;
    private static ArrayList<State> states = new ArrayList<State>();
    //α wildcard symbol
    
    

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

            //read in states 
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(isr);
    
            //read in and create states
            String stateInput = reader.readLine();

            // while we have states to read in 
            while(stateInput != null){

                //split the state information
                String[] stateInfo = stateInput.split(",");
                char[] symbol = stateInfo[0].toCharArray();
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

        //create new deque
        deque = new Deque(states.size());

        //set pointer 
        int pointerPos =0;
        int currentStatePos =0;
        State currenState;
        currenState = states.get(currentStatePos);

        char[] symbol = currenState.getType();
        char[] chars = string.toCharArray();



        while(true){

            //set the current pointer 
            char pointer = chars[pointerPos];
            //<IMPLEMENT SEARCH LOGIC HERE>
            while(true){
                if(symbol[0] == 'γ'){
                    deque.push(currenState.next1(),currenState.next2());
                }   
                else{
                    try{

                    //pop of the stack
                    currentStatePos = deque.pop();
                    currenState = states.get(currentStatePos);
                    symbol = currenState.getType();

                    } catch (Exception e) {
                        deque.setPossibleCurrentStates();
                    }

                }

                if(symbol[0] == WILDCARD|| symbol[0] != BRANCH || symbol.length !=2){
                    checkMatching(currenState,pointer);
                }
            }
            
            break;

        }


        return false;
    }

    public static void checkMatching(State state, char pointer){
        char[] symbol =  state.getType();
        if(symbol[0] == WILDCARD){
            deque.queue(state.next1(),state.next2());
        }
        else if(symbol.length == 2){
            if(symbol[1] != pointer){
                deque.queue(state.next1(),state.next2());
            }
        }
        else{
            if(symbol[0] == pointer){
                deque.queue(state.next1(),state.next2());
            }
        }
    }


    

}