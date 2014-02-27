import java.io.*;   
import java.net.*;  
import java.util.Scanner;

/**
   @author Snilledata
*/
public class ATMClient {
	private static String[] phrase = new String[13];
	/**
	private static String phrase[1] = "Welcome!";
	private static String phrase[2] = "Enter username: ";
	private static String phrase[3] = "Enter password: ";
	private static String phrase[4] = "Enter security code: ";
	private static String phrase[5] = "1) Balance 2) Withdraw 3) Deposit 4) Language 5) Exit";
	private static String phrase[6] = "Your balance is: ";
	private static String phrase[7] = "Enter amount: ";
	private static String phrase[8] = "Available languages: 1) English";
	private static String phrase[9] = "Error!";
	private static String phrase[10] = "Farewell my good sir!";
	private static String phrase[11] = "You don't have enough money to do that.";
	private static String phrase[12] = "Incorrect security code.";
	*/
	
	
    private static int connectionPort = 8989;
    
    public static void main(String[] args) throws IOException {
    	
    	phrase[0] = "English";
    	phrase[1] = "Welcome!";
    	phrase[2] = "Enter username: ";
    	phrase[3] = "Enter password: ";
    	phrase[4] = "Enter security code: ";
    	phrase[5] = "1) Balance 2) Withdraw 3) Deposit 4) Language 5) Exit";
    	phrase[6] = "Your balance is: ";
    	phrase[7] = "Enter amount: ";
    	phrase[8] = "Available languages: ";
    	phrase[9] = "Error!";
    	phrase[10] = "Farewell my good sir!";
    	phrase[11] = "You don't have enough money to do that.";
    	phrase[12] = "Incorrect security code.";
        
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
	    for(;;){
	        for (;;) {
	    		System.out.println(phrase[2]);
	    		out.println(scanner.nextInt());
	    		System.out.println(phrase[3]);
	    		out.println(scanner.nextInt());
	    		int successful = Integer.parseInt(in.readLine());
	    		if (successful == 1) {
	    			break;
	    		}
	    		System.out.println(phrase[9]);
	        }
	    	System.out.println(in.readLine());
	    	System.out.println(phrase[5]);	// Prints the menu.
	    	System.out.print("> ");			// Prints "> ".
	    	
	        int menuOption = scanner.nextInt();	// Waiting for input.
	        out.println(menuOption);  
	        int serverOption = Integer.parseInt(in.readLine());	// Reads from server.
	        while (serverOption != 7) {							// Loop unless choise is to exit.
		        switch (serverOption) {
		        case 0:	// Prints menu and waits for input.
		        	System.out.println(phrase[5]);
	                System.out.print("> ");
	                menuOption = scanner.nextInt();
	                out.println(menuOption);  
	                serverOption = Integer.parseInt(in.readLine());
		        	break;
		        case 1:	// Asks for username.
		    		System.out.println(phrase[2]);
	                menuOption = scanner.nextInt();
	                out.println(menuOption);              
	                serverOption = Integer.parseInt(in.readLine());
		    		break;
		        case 2:	// Asks for password.
		        	System.out.println(phrase[3]);
	                menuOption = scanner.nextInt();
	                out.println(menuOption);         
	                serverOption = Integer.parseInt(in.readLine());
		        	break;
		        case 3:	// Asks for security code.
		        	System.out.println(phrase[4]);
	                menuOption = scanner.nextInt();
	                out.println(menuOption);    
	                serverOption = Integer.parseInt(in.readLine());     
		        	break;
		        case 4:	// Prints balance
		        	System.out.println(phrase[6] + Integer.parseInt(in.readLine()));
		        	System.out.println(phrase[5]);
	                System.out.print("> ");
	                menuOption = scanner.nextInt();
	                out.println(menuOption);   
	                serverOption = Integer.parseInt(in.readLine());      
		        	break;
		        case 5: // Asks for amount.
		        	System.out.println(phrase[7]);
	                menuOption = scanner.nextInt();
	                out.println(menuOption);        
	                serverOption = Integer.parseInt(in.readLine()); 
		        	break;
		        case 6: // Prints language list.
		        	System.out.println(phrase[8] + in.readLine());
	                System.out.print("> ");
	                menuOption = scanner.nextInt();
	                out.println(menuOption);     
	                transferLanguage(in);
	                System.out.println(phrase[5]);
	                System.out.print("> ");
	                menuOption = scanner.nextInt();
	                out.println(menuOption);  
	                serverOption = Integer.parseInt(in.readLine());    
		        	break;
		        case 7:	// Exits.
		        	break;
		        case 8:	// Prints error message.
		        	System.out.println(phrase[9]);
		        	System.out.println(phrase[5]);
	                System.out.print("> ");
	                menuOption = scanner.nextInt();
	                out.println(menuOption);   
	                serverOption = Integer.parseInt(in.readLine());      
		        	break;
		        case 9: // Prints low message.
		        	System.out.println(phrase[11]);
		        	System.out.println(phrase[6] + Integer.parseInt(in.readLine()));
		        	System.out.println(phrase[5]);
	                System.out.print("> ");
	                menuOption = scanner.nextInt();
	                out.println(menuOption);   
	                serverOption = Integer.parseInt(in.readLine());   
	                break;
		        case 10: // Prints wrong code message.
		        	System.out.println(phrase[12]);
		        	System.out.println(phrase[5]);
	                System.out.print("> ");
	                menuOption = scanner.nextInt();
	                out.println(menuOption);   
	                serverOption = Integer.parseInt(in.readLine());  
	                break;
		        default:
		        	break;
		        }
	        }
	        System.out.println(phrase[10]);
        }
	    /**
        out.close();
        in.close();
        scanner.close();
        ATMSocket.close();
        */
	}
    
    private static void transferLanguage(BufferedReader in) throws IOException {
    	for(int i = 0; i < 13 ; i++) {
    		phrase[i] = in.readLine();
    	}
    }
}   
