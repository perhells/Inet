import java.io.*;   
import java.net.*;  
import java.util.Scanner;

/**
   @author Snilledata
*/
public class ATMClient {
	private static String msgWelcome = "Welcome!";
	private static String msgUser = "Enter username: ";
	private static String msgPass = "Enter password: ";
	private static String msgCode = "Enter security code: ";
	private static String msgMenu = "1) Balance 2) Withdraw 3) Deposit 4) Language 5) Exit";
	private static String msgBalance = "Your balance is: ";
	private static String msgAmount = "Enter amount: ";
	private static String msgLanguageList = "Available languages: 1) English";
	private static String msgError = "Error!";
	private static String msgExit = "Farewell my good sir!";
	private static String msgLow = "You don't have enough money to do that.";
	private static String msgWrong = "Incorrect security code.";
	
	
	
    private static int connectionPort = 8989;
    
    public static void main(String[] args) throws IOException {
        
        Socket ATMSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String adress = "";

        try {		
            adress = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Missing argument ip-adress");
            System.exit(1);
        }
        try {
            ATMSocket = new Socket(adress, connectionPort); 
            out = new PrintWriter(ATMSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader
                                    (ATMSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " +adress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't open connection to " + adress);
            System.exit(1);
        }

        System.out.println("Contacting bank ... ");
        Scanner scanner = new Scanner(System.in);
        for (;;) {
    		System.out.println(msgUser);
    		out.println(scanner.nextInt());
    		System.out.println(msgPass);
    		out.println(scanner.nextInt());
    		System.out.println(msgCode);
    		out.println(scanner.nextInt());
    		int successful = Integer.parseInt(in.readLine());
    		if (successful == 1) {
    			break;
    		}
    		System.out.println(msgError);
        }
    	System.out.println(msgWelcome);	// Prints the welcome message.
    	System.out.println(msgMenu);	// Prints the menu.
    	System.out.print("> ");			// Prints "> ".
    	
        int menuOption = scanner.nextInt();	// Waiting for input.
        out.println(menuOption);  
        int serverOption = Integer.parseInt(in.readLine());	// Reads from server.
        while (serverOption != 7) {							// Loop unless choise is to exit.
	        switch (serverOption) {
	        case 0:	// Prints menu and waits for input.
	        	System.out.println(msgMenu);
                System.out.print("> ");
                menuOption = scanner.nextInt();
                out.println(menuOption);  
                serverOption = Integer.parseInt(in.readLine());
	        	break;
	        case 1:	// Asks for username.
	    		System.out.println(msgUser);
                menuOption = scanner.nextInt();
                out.println(menuOption);              
                serverOption = Integer.parseInt(in.readLine());
	    		break;
	        case 2:	// Asks for password.
	        	System.out.println(msgPass);
                menuOption = scanner.nextInt();
                out.println(menuOption);         
                serverOption = Integer.parseInt(in.readLine());
	        	break;
	        case 3:	// Asks for security code.
	        	System.out.println(msgCode);
                menuOption = scanner.nextInt();
                out.println(menuOption);    
                serverOption = Integer.parseInt(in.readLine());     
	        	break;
	        case 4:	// Prints balance
	        	System.out.println(msgBalance + Integer.parseInt(in.readLine()));
	        	System.out.println(msgMenu);
                System.out.print("> ");
                menuOption = scanner.nextInt();
                out.println(menuOption);   
                serverOption = Integer.parseInt(in.readLine());      
	        	break;
	        case 5: // Asks for amount.
	        	System.out.println(msgAmount);
                menuOption = scanner.nextInt();
                out.println(menuOption);        
                serverOption = Integer.parseInt(in.readLine()); 
	        	break;
	        case 6: // Prints language list.
	        	System.out.println(msgLanguageList);
                System.out.print("> ");
                menuOption = scanner.nextInt();
                out.println(menuOption);     
                serverOption = Integer.parseInt(in.readLine());    
	        	break;
	        case 7:	// Exits.
	        	break;
	        case 8:	// Prints error message.
	        	System.out.println(msgError);
	        	System.out.println(msgMenu);
                System.out.print("> ");
                menuOption = scanner.nextInt();
                out.println(menuOption);   
                serverOption = Integer.parseInt(in.readLine());      
	        	break;
	        case 9: // Prints low message.
	        	System.out.println(msgLow);
	        	System.out.println(msgBalance + Integer.parseInt(in.readLine()));
	        	System.out.println(msgMenu);
                System.out.print("> ");
                menuOption = scanner.nextInt();
                out.println(menuOption);   
                serverOption = Integer.parseInt(in.readLine());   
                break;
	        case 10: // Prints wrong code message.
	        	System.out.println(msgWrong);
	        	System.out.println(msgMenu);
                System.out.print("> ");
                menuOption = scanner.nextInt();
                out.println(menuOption);   
                serverOption = Integer.parseInt(in.readLine());  
                break;
	        default:
	        	break;
	        }
        }
        System.out.println(msgExit);
        out.close();
        in.close();
        scanner.close();
        ATMSocket.close();
	}
}   
