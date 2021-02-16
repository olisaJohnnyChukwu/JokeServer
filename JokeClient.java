import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Name: Olisa johnny-Chukwu
 * file:Joke Client
 * 
 * jokes gotten from https://www.rd.com/jokes/computer/
 */
public class JokeClient {
    static String server_name;
    public static void main(String[] args) {
       
		if(args.length<1) server_name= "localhost";
        else server_name = args[0];
        
        Socket s=null;
        PrintStream out_=null;
        BufferedReader in_=null;
        String name;
        String choice;
        Scanner scan=new Scanner(System.in);
        System.out.println("Please enter your name !");
        name=scan.nextLine();// accept user name input from client

        try{
            s=new Socket(server_name,4545);
            out_=new PrintStream(s.getOutputStream());
            in_=new BufferedReader(new InputStreamReader(s.getInputStream()));
            out_.println(name);//send name to client server
            String state;
            client a=new client(name);//create user object

            /**
             * do while loop to allow the client choose to continue or quit
             * 
             */

            do{
                System.out.println("Press enter or type quit!");
                choice=scan.nextLine();//accept input from user to stay in loop or quit
                out_.println(choice);//if the client wants to continue send to server
                state=in_.readLine();//recieve state from client

                
                
                /**
                 * if else conditionals ,to manage state of conversation.
                 * if the number of jokes or proverbs is == 4
                 * set the number of jokes or proverbs told to zero
                 * and empty the hashset
                 */

                if(state.equals("joke")){
                    if(a.proverbs_told==4){
                        
                        a.proverbs_told=0;//reset the client obj proverb index to 0
                        a.proverbsIndexes.clear();//clear hashset
                        System.out.println("Proverb cycle Finished");//if cycle is finished notify client
                        out_.write(a.proverb());//send unsed index to server
                        System.out.println(in_.readLine());//recieve untold proverb from server
                    }else{
                       
                        //if the cycle not finished send the index of an untold joke
                        out_.write(a.proverb());
                        System.out.println(in_.readLine());//print proverb from server
                    }
                }else{
                    if(a.Jokes_told==4){
                        
                        a.Jokes_told=0;//reset the client obj joke index to 0
                        a.jokeIndexes.clear();//clear hashset
                        System.out.println("Joke cycle Finished");//if cycle is finished notify client
                        out_.write(a.joke());//send unsed index to server
                        System.out.println(in_.readLine());//recieve untold joke from server
                    }else{
                        
                        out_.write(a.joke());//send unused joke index to server
                        System.out.println(in_.readLine());//print untold joke to server
                    }
                }




            }while(choice.indexOf("quit")<0);
            System.out.println("Ended by user request :(");

        }catch(IOException e){
            e.printStackTrace();
        }


       
        
    }
    
           

        

    
    
    
    
}
class client {
    /**
     *client object to store and manage user state
     */
    
    
    
    int Jokes_told=0;// number of jokes told set 0 at beginning
    int proverbs_told=0;// number of proverb told set 0 at beginning
    HashSet<Integer> jokeIndexes =new HashSet<>();//hashset to store index of jokes
    HashSet<Integer> proverbsIndexes =new HashSet<>();//hashset to store index of told proverbs
    String username;
    

    public client(String username){
        this.username=username;
        
    }

   


    public int joke(){
        /**
         * joke() method returns index of unused index of joke
         */
        Random rand=new Random();
        int i=0;
        int rand_int=rand.nextInt(4);
        do{

            rand_int= rand.nextInt(4);// set to random number between 0-3
            if(!jokeIndexes.contains(rand_int)){
                i=rand_int;//if not in set add abd break loop
                jokeIndexes.add(i);
                break;
            }
        }while(jokeIndexes.contains(rand_int));
        Jokes_told+=1;//increase the jokes told by 1
        return i;
    }

    public int proverb(){
        /**
         * proverb() method returns index of unused index of joke
         */
        Random rand=new Random();
        int i=0;
        int rand_int=rand.nextInt(4);
        do{

            rand_int= rand.nextInt(4);// set to random number between 0-3
            if(!proverbsIndexes.contains(rand_int)){
                i=rand_int;
                proverbsIndexes.add(i);//if not in set add abd break loop
                break;
            }
        }while(proverbsIndexes.contains(rand_int));
        proverbs_told+=1;
        return i;//increase the jokes told by 1
    }

    

   
}

