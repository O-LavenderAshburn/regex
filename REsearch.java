import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class REsearch{
    //symbo
    public static final char BRANCH = 'γ';
    public static final char WILDCARD = 'α';

    // Deque to keep track of possible current and next states
    private static Deque deque;
    private static ArrayList<State> states = new ArrayList<State>();
    
    

    public static void main(String[] args){

        //Pointers and marks 
        int mark =0;
        Boolean succces;
        String currentLine;

        //print usage and exit
        // if(args.length < 1){

        //     //error
        //     System.out.println("Usage: ");
        //     return;
        // }

        //File to search
        //String filename = args[0];

        try{

            //read in states 
            // InputStreamReader isr = new InputStreamReader(System.in);
            FileReader fr1= new FileReader("./testFSMs/one.txt");

            BufferedReader reader = new BufferedReader(fr1);
    
            //read in and create states
            String stateInput = reader.readLine();

            // while we have states to read in 
            while(stateInput != null){
                //split the state information
                String[] stateInfo = stateInput.split(",");                
                char[] symbol = stateInfo[0].toCharArray();
                int n1 = Integer.parseInt(stateInfo[1]);
                int n2 = Integer.parseInt(stateInfo[2]);

                //create state and add to list of states
                State newState = new State(symbol, n1 , n2);
                states.add(newState);

                stateInput = reader.readLine();
            }
            reader.close();

            //read the file 
            FileReader fr = new FileReader("./testFSMs/testText.txt");
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

                    if(succces == true){

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
        int pointer =0;
        char[] chars = string.toCharArray();

        //get position 0
        int currentStatePos =0;
        State currenState;

        //get the start state of the machine
        currenState = states.get(currentStatePos);


        while(true){

            //--If we have consumed all our input without getting to the final state--\\
            if(pointer > chars.length){
                break;
            }

            //set the current pointer 
            char currentChar = chars[pointer];

            char[] symbol = currenState.getType();
            
            while(true){

                if(currenState.getType()[0]== 'γ'){

                    deque.push(currenState.next1(),currenState.next2());

                    try{

                        if(deque.emptyStack()){
                            //deque next states onto the stack and reset visited 
                            deque.deque();
                            deque.resetVisited();

                            //if empty stack after deque
                            if(deque.emptyStack()){
                                return false;
                            }
                            
                            break;
                        }

                    currentStatePos = deque.pop();
                    currenState = states.get(currentStatePos);

                    } catch (Exception e){
                        System.err.println(e);
                    }

                }   

                else{
                    try{

                    //pop of the stack
                        if(deque.emptyStack()){
                            //deque next states onto the stack and reset visited 
                            deque.deque();
                            deque.resetVisited();

                            //if empty stack after deque
                            if(deque.emptyStack()){
                                return false;
                            }
                            
                            break;
                        }
                            
                        currentStatePos = deque.pop();
                        currenState = states.get(currentStatePos);

                    } catch (Exception e) {

                    }

                }
                if(!(currenState.getType()[0] == BRANCH)){

                    checkMatching(currenState,currentChar);

                }

                //--If we get back to state 0--\\
                if(currenState.next1() ==  0){
                    return true;
                }


            }
            
            pointer++;

        }

        return false;
    }


    /**
     * Check if we are matching a character
     * @param state the current state
     * @param pointer the character we are currently pointing at
     */
    public static void checkMatching(State state, char current){
        char[] symbol =  state.getType();

        if(symbol[0] == WILDCARD){
            deque.queue(state.next1(),state.next2());
        }
        // else if(symbol.length == 2){
        //     if(symbol[1] != pointer){
        //         deque.queue(state.next1(),state.next2());
        //     }
        // }
        else{
            if(symbol[0] == current){
                deque.queue(state.next1(),state.next2());
            }
        }
    }


    

}