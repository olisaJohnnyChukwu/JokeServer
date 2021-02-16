import java.io.*;
import java.net.*;
import java.util.*;
/*Name: Olisa johnny-Chukwu
* file:Joke ClientAdmin
* 
* jokes gotten from https://www.rd.com/jokes/computer/
*/


public class JokeClientAdmin {
    static String server_name;

    public static void main(String[] args) {
       
        if(args.length<1) server_name= "localhost";
        else server_name= args[0];
        String choice;
        //String mode=null;
        Scanner scan=new Scanner(System.in);// use scanner class to 
        String mode="joke";//set first default server to joke
        System.out.println("Default mode: joke");
       /**
        * do while loop to choose if to continue  or quit client admin
        */
            do{
                System.out.println( " enter to switch  mode or quit to exit... "); 
                choice=scan.nextLine();
                switch_mode(mode);// if user presses enters switch current 
                
                
                //output.println(choice);
                
            }while(choice.indexOf("quit")<0);


        
     
        }
        static void switch_mode(String m){
            /** */
            Socket s=null;
            PrintStream out_=null;
            BufferedReader inp_=null;
            
             
        try{
            /**
             * create socket and instantize the input and  output 
             */
            s=new Socket(server_name,5050);
            out_=new PrintStream(s.getOutputStream());
            inp_=new BufferedReader(new InputStreamReader(s.getInputStream()));
            m=inp_.readLine();//set string m to the current server mode

            if(m.equals("joke")){
                out_.println("proverb");//if mode is joke send proverb to server
                System.out.println("Switched to joke mode");
            }
            else{
                out_.println("joke");//if mode is proverb send joke to server
                System.out.println("Switched to proverb mode");
            }

        }catch(IOException e){e.printStackTrace();}
        }

    

   
        
    
}
