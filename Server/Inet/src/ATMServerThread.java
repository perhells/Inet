import java.io.*;
import java.net.*;

/**
   @author Viebrapadata
   
   Sys
   1,2,3,6  
   */
public class ATMServerThread extends Thread {
	private int[] userNames = new int[]{1,2};
	private int[] userPasswords = new int[]{1,2};
	private int[] userFunds = new int[]{1000,1000};
	
    private Socket socket = null;
    private BufferedReader in;
    PrintWriter out;
    public ATMServerThread(Socket socket) {
        super("ATMServerThread");
        this.socket = socket;
    }

    private String readLine() throws IOException {
        String str = in.readLine();
        System.out.println(""  + socket + " : " + str);
        return str;
    }

    private int validateUser() throws IOException {
    	for(;;){
	    	int userName = Integer.parseInt(readLine());
	    	System.out.println("User name is: " + userName);
	    	int userPass = Integer.parseInt(readLine());
	    	System.out.println("User pass is: " + userPass);
	    	for(int i = 0; i < userNames.length; i++){
	    		if (userName == userNames[i] && userPass == userPasswords[i]) {
	    			System.out.println("Success!");
	    			out.println(1);
	    			return userFunds[i];
	    		}
	    	}
			System.out.println("Fail!");
			out.println(0);
    	}
    }
    
    private boolean validateWithdraw() throws IOException {
    		out.println(3);
    		int code = Integer.parseInt(readLine());
    		if(code % 2 == 1 && code > 0 && code < 100){
    			return true;
    		}
    	return false;
    }

    public void run(){
         
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
	
            String inputLine;
	
            int value;
            int balance = validateUser();
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
                    	if (deposit < 0){
                    		if (value <= balance){
	                    		if(validateWithdraw()){
	                        		balance += deposit * value;
	                        		out.println(4);
	                                out.println(balance);
	                                inputLine=readLine();
	                                choise = Integer.parseInt(inputLine);
	                                break;
	                    		}else{
	                    			out.println(10);
	                    			inputLine=readLine();
	                    			choise = Integer.parseInt(inputLine);
	                        		break;
	                    		}
                    		}else{
                    			out.println(9);
                    			out.println(balance);
                    			inputLine=readLine();
                    			choise = Integer.parseInt(inputLine);
                        		break;
                    		}
                    	}else{
                    		balance += deposit * value;
                    		out.println(4);
                            out.println(balance);
                			inputLine=readLine();
                			choise = Integer.parseInt(inputLine);
                    		break;
                    	}
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
    /*
/**
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
    */

    /*
     * Compose the clients menu
   
/**
    private static void composeMenu() {
    	menu = "--[ " + banner + " ]--\n(1)"; 
    }
     */ 
 
}