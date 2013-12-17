import java.io.*;
import java.net.*;

/**
   @author Viebrapadata
*/
public class ATMServerThread extends Thread {
    private Socket socket = null;
    private BufferedReader in;
    PrintWriter out;
    public ATMServerThread(Socket socket) {
        super("ATMServerThread");
        this.socket = socket;
    }

    private String readLine() throws IOException {
        String str = in.readLine();
        //System.out.println(""  + socket + " : " + str);
        return str;
    }

    private boolean validateUser() {
        return true;
    }

    public void run(){
         
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
	
            String inputLine, outputLine;
	
            int balance = 1000;
            int value;
            validateUser();
            inputLine = readLine();
            int choise = Integer.parseInt(inputLine);
            while (choise != 5) {
            	System.out.println("Choise is: " + choise);
                int deposit = 1;
                switch (choise) {
                case 2:	// Withdraw
                    deposit = -1;
                case 3: // Deposit
                    out.println(5);	
                    inputLine= readLine();
                    value = Integer.parseInt(inputLine);
                    if (value > 0) {
                    	if (deposit < 0 && value > balance){
                    		out.println(9);
                            out.println(balance);
                            inputLine=readLine();
                            choise = Integer.parseInt(inputLine);
                            break;
                    	}
                    	balance += deposit * value;
                    } else {
                    	out.println(8);
                        inputLine = readLine();
                        choise = Integer.parseInt(inputLine);
                        break;
                    }
                case 1: // Balance
                    out.println(4);
                    out.println(balance);
                    inputLine=readLine();
                    choise = Integer.parseInt(inputLine);
                    break;
                case 4: // Language
                    out.println(6);
                    inputLine=readLine();
                    choise = Integer.parseInt(inputLine);
                	break;
                case 5: // Exit
                    break;
                default: 
                    out.println(8);
                    inputLine = readLine();
                    choise = Integer.parseInt(inputLine);
                    break;
                }
            }
            out.println(7);
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    
    }
    
    
    @SuppressWarnings("unused")
	private static void banner(BufferedReader in) {
    	String newBanner;
		try {
			newBanner = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(banner.length() < 1) { //at least one sign
    		banner = newBanner;
    		composeMenu();
    	}
    }
    
    /*
     * Compose the clients menu
     */
	private static void composeMenu() {
    	menu = "--[ " + banner + " ]--\n(1)"; 
    }
    
    
}