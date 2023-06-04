import java.io.*;
import java.util.ArrayList;
import java.util.*;


public class REsearch{


    public static void main(String[] args){

        //Pointers and marks 
        int mark =0;
        int pointer =0;
        int lineNum =1;

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

        } catch (Exception e){
            //return
            System.err.println(e);
            return;
        }


    }

    

}