import java.io.*;
import java.net.*;

/**
   @author Viebrapadata
   
   Sys
   1,2,3,6  
   */
public class ATMServerThread extends Thread {
	int userName;
	int balance;
	int phraseCount = 13;
	int languageCount = 0;
	String banner;
	private int[] userNames  = new int[0];
	private int[] userPasswords = new int[0];
	private int[] userFunds = new int[0];
	private String[][] languages = new String[0][phraseCount];
	//private ArrayList<String[]> languages = new ArrayList<String[]>(); 
	// 
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
	    	userName = Integer.parseInt(readLine());
	    	System.out.println("User name is: " + userName);
	    	int userPass = Integer.parseInt(readLine());
	    	System.out.println("User pass is: " + userPass);
    		loadFromFile();
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
    	loadFromFile();
    	loadLanguages();
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
	
            String inputLine;
            
            
            int value;
            for(;;){
	            balance = validateUser();
	            banner = loadBanner();
	            out.println(banner);
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
	                    updateBalance();
	                    if (value > 0) {
	                    	if (deposit < 0){
	                    		if (value <= balance){
		                    		if(validateWithdraw()){
		                        		balance += deposit * value;
		                        		writeBalance();
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
	                    		writeBalance();
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
	                	updateBalance();
	                    out.println(4);
	                    out.println(balance);
	                    inputLine=readLine();
	                    choise = Integer.parseInt(inputLine);
	                    break;
	                case 4: // Language
	                    out.println(6);
	                    loadLanguages();
	                    out.println(languageList());
	                    inputLine=readLine();
	                    choise = Integer.parseInt(inputLine);
	                    transferLanguage(choise);
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
            }
            /**
            out.close();
            in.close();
            socket.close();
            */
        }catch (IOException e){
            e.printStackTrace();
        }
    
    }
    
    private void writeToFile() {
    	try(PrintWriter out = new PrintWriter("users.txt")){
	    	for(int i = 0;i<userNames.length;i++){
	    		out.println(userNames[i] + " " + userPasswords[i] + " " + userFunds[i]);
	    	}
    	} catch(IOException e) {
			e.printStackTrace();
    	}
    }
    
    private void loadFromFile() {
    	try(BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
    		System.out.println("Started reading from file...");
            String line = br.readLine();
            int count = 0;
            while (line != null) {
            	System.out.println(count);
            	count ++;
            	line = br.readLine();
            }
            userNames = new int[count];
            userPasswords = new int[count];
            userFunds = new int[count];
            try(BufferedReader br2 = new BufferedReader(new FileReader("users.txt"))) {
                String line2 = br2.readLine();
	            System.out.println("read: " + line2);
	            int index = 0;
	            while (line2 != null) {
	            	String[] temp = new String[3];
	            	temp = line2.split("\\s+");
	            	System.out.println(temp);
	            	userNames[index] = Integer.parseInt(temp[0]);
	            	userPasswords[index] = Integer.parseInt(temp[1]);
	            	userFunds[index] = Integer.parseInt(temp[2]);
	            	line2 = br2.readLine();
	            	index++;
	            }
            } catch (IOException e) {
    			e.printStackTrace();
    		}
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void updateBalance() {
    	loadFromFile();
    	for(int i = 0; i<userNames.length; i++) {
    		if(userName == userNames[i]) {
    			balance = userFunds[i];
    		}
    	}
    }
    
    private void writeBalance() {
    	for(int i = 0; i<userNames.length; i++) {
    		if(userName == userNames[i]) {
    			userFunds[i] = balance;
    		}
    	}
    	writeToFile();
    }
    
    private void loadLanguages() {
    	try(BufferedReader br = new BufferedReader(new FileReader("languages.txt"))) {
            String line = br.readLine();
            int count = 0;
            while(line!=null){
            	count++;
            	line = br.readLine();
            }
            languageCount = count/phraseCount;
            languages = new String[languageCount][phraseCount];
            try(BufferedReader br2 = new BufferedReader(new FileReader("languages.txt"))) {
	            int wordIndex = 0;
	            int langIndex = 0;
	            String line2 = br2.readLine();
	            while (line2 != null) {
	            	if(wordIndex == phraseCount){
	            		wordIndex = 0;
	            		langIndex++;
	            	}
	            	languages[langIndex][wordIndex] = line2;
	                wordIndex++;
	                line2 = br2.readLine();
	            }
            } catch (IOException e) {
    			e.printStackTrace();
    		}  
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

	private void transferLanguage(int langIndex) {
		for(int i = 0; i < phraseCount; i++){
			out.println(languages[langIndex][i]);
		}
    }
	
	private String languageList() {
		String language;
		String returnLanguage = languages[0][0]; 
		int index = 1;
		while(index < languageCount) {
			language = languages[index][0];
			returnLanguage = returnLanguage + ", " + language;
			index++;
		}
		return returnLanguage;
	}
	
	private String loadBanner() throws IOException {
		String banner = "";
		try(BufferedReader br = new BufferedReader(new FileReader("banner.txt"))) {
			banner = br.readLine();
        } catch (IOException e) {
			e.printStackTrace();
		}
		return banner;
	}
}