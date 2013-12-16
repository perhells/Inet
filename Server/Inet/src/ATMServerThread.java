import java.io.*;
import java.net.*;

/**
   @author Viebrapadata
*/
public class ATMServerThread extends Thread {
    private Socket socket = null;
    private DataInputStream in;
    DataOutputStream out;
    public ATMServerThread(Socket socket) {
        super("ATMServerThread");
        this.socket = socket;
    }

    private String readLine() throws IOException {
        String str = in.readUTF();
        //System.out.println("  + socket + " : " + str);
        return str;
    }

    private boolean validateUser() {
        return true;
    }

    public void run(){
         
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
	
            String inputLine, outputLine;
	
            int balance = 1000;
            int value;
            validateUser();
            out.writeChars("Welcome to Bank! (1)Balance, (2)Withdrawal, (3)Deposit, (4)Exit"); 
            inputLine = readLine();
            int choise = Integer.parseInt(inputLine);
            while (choise != 4) {
                int deposit = 1;
                switch (choise) {
                case 2:
                    deposit = -1;
                case 3:
                    out.writeChars("Enter amount: ");	
                    inputLine= readLine();
                    value = Integer.parseInt(inputLine);
                    balance += deposit * value;
                case 1:
                    out.writeChars("Current balance is " + balance + " dollars");
                    out.writeChars("(1)Balance, (2)Withdrawal, (3)Deposit, (4)Exit");
                    inputLine=readLine();
                    choise = Integer.parseInt(inputLine);
                    break;
                case 4:
                    break;
                default: 
                    break;
                }
            }
            out.writeChars("Good Bye");
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    
    }
}