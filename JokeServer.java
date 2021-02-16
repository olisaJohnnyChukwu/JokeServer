import java.io.*;
import java.net.*;
import java.util.*;
//ss
/*Name: Olisa johnny-Chukwu
* file:Joke Server
* 
* jokes gotten from https://www.rd.com/jokes/computer/
*/

public class JokeServer {
    static String mode="proverb";
    
    public static void main(String[] args) throws IOException {
    Socket client;
    ServerSocket serverSocket;
    


        AdminLooper AL = new AdminLooper();
        Thread t = new Thread(AL);
        t.start();

        serverSocket= new ServerSocket(4545,6);

        while(true){
            client=serverSocket.accept();
            
            new Worker(client).start();
           // serverSocket.close();

        }
        
    }
    
}
class Worker extends Thread{
    Socket s;
   

    Worker(Socket s) {
        this.s = s;
    }

    public void run() {
        BufferedReader in_ = null;
        PrintStream out_ = null;
        String name ;
        String choice ;
        int i=0;
        

        try {
            /**
             * instantize Input output objects to send to client
             * 
             */
            out_= new PrintStream(s.getOutputStream());
            in_ = new BufferedReader(new InputStreamReader(s.getInputStream()));
            name=in_.readLine();//recieve name
            System.out.println("In joke mode");//print default mode for the first time the server is ru
            do {
                choice = in_.readLine();//if not equals to quit continue
                out_.println(JokeServer.mode);//send state to client
                i=in_.read();//recieve index of untold joke or proverb 

                /**
                 * if else conditionals to determine which statement to send to
                 * client
                 */
                if(JokeServer.mode.equals("proverb")){
                    returnjoke(i, name, out_);
                }else{
                    returnproverb(i, name, out_);
                }



            } while (choice.indexOf("quit") < 0);

            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static void returnjoke(int i,String n,PrintStream out){
        /**
         * method returns statement using the unsed index and sends to client
         */
        Vector<String> joke=new Vector<>();
        joke.add("JA "+n+" Don't use beef stew as a computer password. It's not stroganoff.");
        joke.add("JB "+n+" What happens to a frog's car when it breaks down? It gets toad away.");
        joke.add("JC "+n+" Q: What did the duck say when he bought lipstick?A: Put it on my bill.");
        joke.add("JD "+n+" Why are iPhone chargers not called Apple Juice?!");

        out.println(joke.get(i));
    }

    static void returnproverb(int i,String n,PrintStream out){
        /**
         * method returns statement using the unsed index and sends to client
         */
        Vector<String> proverb=new Vector<>();
        proverb.add("PA " +n+" A barking dog never bites");
        proverb.add("PB " +n+" A chain is only as strong as its weakest link");
        proverb.add("PC " +n+" The child cannot see what the older sees sqautibg");
        proverb.add("PD " +n+" A word is enough for the wise");


        out.println(proverb.get(i));
    }

}

class AdminLooper implements Runnable {
    public static boolean adminControlSwitch = true;

    public void run() {
        System.out.println("In the admin looper thread");

        int q_len = 6;
        int port = 5050;
        Socket sock;

        try {
            ServerSocket servsock = new ServerSocket(port, q_len);
            while (adminControlSwitch) {

                sock = servsock.accept();
                new AdminWorker(sock).start();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}

/**
 * admin worker  thread class to manage the mode of the joke server
 */
class AdminWorker extends Thread {
    Socket sock;

    AdminWorker(Socket s) {
        sock = s;
    }

    public void run() {
        BufferedReader in_ = null;
        PrintStream out_=null;
        

        try {
            out_=new PrintStream(sock.getOutputStream());
            in_ = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            // ask the admin client for the mode it wants
            out_.println(JokeServer.mode);//send server state to client
            System.out.println("In "+JokeServer.mode+"mode");
            JokeServer.mode=in_.readLine();//accept the new mode
            
        sock.close();//close sock
    }catch(IOException e){
        e.printStackTrace();
    }
    }
   

    
    

}