/*Name: Vicky Mohamamd
 *ID: 0895381
 */
package stock;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import stock.Investment.customException;

/**
 * class for the main menu
 * @author vicky mohammad
 */
public class Menu {
    //declare final for type
    private static final int STOCK = 1;
    private static final int MUTUAL_FUND = 2;
    //declare var
    private static String symbol = " ", name = " ";
    private static double price = 0;
    private static int quantity = 0;
    private static String searchRange = " ";
    private static boolean found = false;
    //declare objects
    private static Scanner scan = new Scanner(System.in);
    public static Portfolio portfolio = new Portfolio();
    private static ArrayList<Investment> hashIndex = new ArrayList<Investment>();
    private static HashMap<String, ArrayList<Integer>> hash = new HashMap <String, ArrayList<Integer>>();
    private static UserInterface GUI = new UserInterface();
    private double totalGain = 0;
    //declare variable for decimale format
    public DecimalFormat twoDecimal = new DecimalFormat();
    //declare variable for creating geting the symbol and name for the price change
    private static String currentSymbol = "";
    private static String currentName = "";
    private static String currentPrice = "";
    //create var for file name
    public static String fileName;
    
    /**
     * main function
     * @param args for the command line input
     */
    public static void main(String[] args) {
    	try{
	        //load the array 
	        fileName = args[0];
	        System.out.println("The filename (args[0]) is: " + args[0]);
	        loadPortfolio(fileName);
	        removeUpdateHash();
	        //set the window
	        GUI.setWindow();
        }catch(Exception e){
        	System.out.println("Please input the name of your file in the argument.");
        	System.exit(0);
        }//end catch
    }//end main
    
    /**
     * get the current symbol
     * @return the current symbol
     */
    public String getCurrentSymbol(){
        return currentSymbol;
    }//end func
    
    /**
     * get the current name
     * @return the current name
     */
    public String getCurrentName(){
        return currentName;
    }//end func
    
    /**
     * get the current price
     * @return the current price
     */
    public String getCurrentPrice(){
        return currentPrice;
    }//end func
    
    /**
     * get the total gain of the investment
     * @return the total gain
     */
    public double getTotalGain(){
        return totalGain;
    }//end func
    
    /**
     * print all the portfolio
     * debug
     * @return the string of the message
     */
    public String printPorfolio(){
        return portfolio.toStringList();
    }//end func

    /**
     * this is function is to buy a stock
     * @param type of the investment
     * @param symbol of the investment
     * @param name of the investment
     * @param quantity of the investment
     * @param price of the investment
     * @return the string for of the output for the message box
     */
    public String option1(int type, String symbol, String name, int quantity, double price){
        
        //declare var
        boolean symbolFound = false;
        String message = "";
        try{
            //check if there is existing symbol
            for(int x=0; x<portfolio.list.size(); x++){
                if (portfolio.list.get(x).getSymbol().equalsIgnoreCase(symbol)){
                    symbolFound = true;
                    //buying more investment
                    message = message + portfolio.list.get(x).buy(price, quantity);
                    //print the message
                    message = message + "Found existing symbol.\n";
                    message = message + "Buying more existing investment...\n";
                    //if symbol found exit
                    break;
                }//end if
            }//end for
        }catch(customException e){
            message = message + e.getMessage();
        }//end catch
        
        //if the it didn't find the existing symbol buy for stock
        if(symbolFound == false && type == STOCK){
            //add the stock list
            message = message + portfolio.addStock(symbol, name, quantity, price); 
        }//end if
        
        //if the it didn't find the existing symbol buy for mutual fund
        if(symbolFound == false && type == MUTUAL_FUND){
            //add the mutual list
            message = message + portfolio.addMutual(symbol, name, quantity, price); 
        }//end if
        
        //return
        return message;
    }//end func
    
    /**
     * a function for selling an investment
     * @param symbol of the investment
     * @param quantity of the investment
     * @param price of the investment
     * @return the string for the message
     */
    public String option2(String symbol, int quantity, double price){      
        //declare a string for return
        String message = "";
        
        //declare var
        int found = 0;
        //search for the stock
        for(int x = 0; x < portfolio.list.size();x++){
            if(portfolio.list.get(x).getSymbol().equalsIgnoreCase(symbol)){
                
                //sell the stock
                try{
                    found = 1;
                    portfolio.list.get(x).priceChanged(price);
                    if(quantity >= portfolio.list.get(x).getQuantity()){
                        message = message + portfolio.list.get(x).sell(quantity, price);
                        portfolio.list.remove(x);
                    }else{
                        message = message + portfolio.list.get(x).sell(quantity, price);
                    }//end if
                }catch(customException e){
                    message = message + e.getMessage();
                }//end try

            }//end if
        }//end list

        //if it could not find the symbol create a message
        if(found != 1){
            message = message + "COULD NOT FIND SYMBOL.\n";
        }//end if
            
        //return the string
        return message;
    }//end func
       
    /**
     * a function for updating the price of the investment
     * @param price of the investment
     * @param x for the index of the investment list
     * @return the string for the message output
     */
    public String option3(double price, int x){
        //decalare string that is being returned
        String message = "";
        //set boundry of the size 
        if(x < 0){
            x = 0;
        }//end if 
        if(x > portfolio.list.size()-1){
            x = portfolio.list.size()-1;
        }//end if 
        //check the if there is any investment in the list
        if(portfolio.list.size() != 0){
            //get the info at the index
            currentSymbol = portfolio.list.get(x).getSymbol();
            currentName = portfolio.list.get(x).getName();
            currentPrice = Double.toString(portfolio.list.get(x).getPrice());
        }else{
            message = message + "No investment available.\n";
        }//end if
        return message;
    }//end func
            
    /**
     * option 4
     * @return the string of the output
     */
    public String option4(){
        String message = "";
        DecimalFormat decimalGain = new DecimalFormat();
        decimalGain.setMaximumFractionDigits(2);
        totalGain = 0;
        //add all the gain in the list
        if(portfolio.list.size() != 0){
            if(portfolio.list.get(0).getNumberOfStock() != 0){
                for(int x=0; x<portfolio.list.size();x++){
                    //get the indivisual gain and the total gain
                    totalGain = totalGain + portfolio.list.get(x).getGain();           
                    message = message + portfolio.list.get(x).getSymbol() + ": " + decimalGain.format(portfolio.list.get(x).getGain()) + ".\n";
                }//end for
            }//end if
        }//end if
        //message = message + "Your total gain " + decimalGain.format(totalGain) + "\n";
        //return the massage
        return message;
    }//end func
    
    /**
     * option 5 for search
     * @param symbol of the investment
     * @param name of the investment
     * @param lowPrice of investment
     * @param highPrice of the investment
     * @return string of the output
     */
    public String option5(String symbol, String name, String lowPrice, String highPrice){
        //decalre var
        String message = "";
        double inputPrice;
        found = false;
        boolean rangePriceIsNotEmpty = false;
        
        //check if the price is entered
        if(lowPrice.equals("") && highPrice.equals("")){
            rangePriceIsNotEmpty = false;
        }else{
            rangePriceIsNotEmpty = true;
        }//end if
        
        //check if there is an investment in the list
        if(portfolio.list.size() != 0){
            //declare var
            int match = 0;
            //check for stock the list
            
            for(int x = 0; x < portfolio.list.size();x++){
                //check for the are a match
                String[] splitName = name.toLowerCase().split(" +");

                //get the index of the hash and print if match
                try{
                   for(int y=0; y < splitName.length; y++) {
                       if(hash.get(splitName[y]).contains(x+1)){
                           match++;
                       }//end if
                   }//end if
                }catch(Exception e){
                    //getting a hash key that doesnt exist, so no match
                    match = 0;
                }//end try

                //for debuging
                //System.out.println("S: " + symbol + "N: " + name + "IP: " + inputPrice);

                //check print the name if it match
                if(symbol.equals("") == true && name.equals("") == true && rangePriceIsNotEmpty == false){
                    for(int y = 0; y<portfolio.list.size(); y++){
                        message = message + portfolio.list.get(y).toString();
                        found = true;
                    }//end if
                    break;
                }else if(match >= splitName.length && rangePriceIsNotEmpty == false && symbol.equals("") == true){
                    message = message + portfolio.list.get(x).toString();
                    found = true;
                }else if(symbol.equalsIgnoreCase(portfolio.list.get(x).getSymbol()) && rangePriceIsNotEmpty == false && name.equals("") == true){
                    message = message + portfolio.list.get(x).toString();
                    found = true;
                }else if(symbol.equals("") == true && name.equals("") == true && rangePriceIsNotEmpty == true){
                    message = message + checkRangeValue(x, lowPrice, highPrice);
                }else if(match >= splitName.length && searchRange.equals("") != true && symbol.equals("") == true){ 
                    message = message + checkRangeValue(x, lowPrice, highPrice);
                }else if(symbol.equalsIgnoreCase(portfolio.list.get(x).getSymbol()) && rangePriceIsNotEmpty == true && name.equals("") == true){
                    message = message + checkRangeValue(x, lowPrice, highPrice);
                }else if(symbol.equalsIgnoreCase(portfolio.list.get(x).getSymbol()) && rangePriceIsNotEmpty == false && match >= splitName.length){
                    message = message + portfolio.list.get(x).toString();
                    found = true;
                }else if(symbol.equalsIgnoreCase(portfolio.list.get(x).getSymbol()) && rangePriceIsNotEmpty == true && match >= splitName.length){
                    message = message + checkRangeValue(x, lowPrice, highPrice);
                }//end if
                //reset match
                match = 0;
            }//end for
            
            //if nothing found then print to the user
            if(found == false){
                message = message + "Cannot find what you are looking for.\n";
            }//end if
        }else{
            message = message + "No investment in the portfolio...\n";
        }//end if
        //return the message
        return message;
    }//end func
    
    /**
     * save all the data to the the fileName
     * @param fileName of the data
     */
    public void savePortfolio(String fileName){
        // Write to a file
        BufferedWriter writer;
        try{
            System.out.println("***************************************");
            System.out.println("Saving...");
            writer = new BufferedWriter(new FileWriter(fileName));
            for(int x = 0; x < portfolio.list.size(); x++){
                
                //write the data
                writer.write(portfolio.list.get(x).data());
                //print what is saving for debug
                System.out.println(portfolio.list.get(x).data());
              
                writer.newLine();
            }//end if
            System.out.println("Saved.");
            System.out.println("***************************************");
            writer.close();
        } catch(IOException e){
            System.out.println("Failed to write to the file.");
            System.out.println("Please follow the instruction carefully.");
        }//end try
    }//end func
    
    /**
     * load the file to the fileName
     * @param fileName of the data
     */
    public static void loadPortfolio(String fileName){
        // Read the file
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            String symbol = "";
            String name = "";
            int quantity = 0;
            int index = 0;
            double price = 0;
            double bookValue = 0;
            //load the while until the end of the lsit
            System.out.println("***************************************");
            System.out.println("Loading...");
            while (line != null){
                String[] data = line.split("@");
                
                //for debug
                System.out.println(line);
                /*or(int x=0; x < data.length; x++){
                    System.out.println("Split: " + data[x]);
                }*/
                
                //convert string to int or double
                symbol = data[1];
                name = data[2];
                quantity = Integer.parseInt(data[3]);
                price = Double.parseDouble(data[4]);
                bookValue = Double.parseDouble(data[5]);
                
                //load data for stock
                if(data[0].equals("s")){
                    try{
                        portfolio.list.add(new Stock(symbol, name, quantity, price, bookValue));
                        addHash(name, portfolio.list.size());
                    }catch(customException e){
                        e.getMessage();
                    }//end try
                }//en if 
                //load data for mutual
                if(data[0].equals("m")){
                    try{
                        portfolio.list.add(new MutualFund(symbol, name, quantity, price, bookValue));
                        addHash(name, portfolio.list.size());
                    }catch(customException e){
                        e.getMessage();
                    }//end try
                }//end if
                index++;
                line = reader.readLine();
            }//end while
            System.out.println("Loaded.");
            System.out.println("***************************************");
            reader.close();
        } catch(IOException e){
            System.out.println("No file detected.");
        }//end try
    }//end if
  
    /**
     * add a hash
     * @param key the string of the sentence
     * @param index the index that is that word exist on the array
     */
    public static void addHash(String key, int index){
        //split the search in each words
        String split[] = key.toLowerCase().split(" ");
        //add all the index to all to hash
        for(int x = 0; x < split.length; x++){
            //set a new array list
            ArrayList<Integer> arrayIndex = new ArrayList<Integer>();
            try{
                //copy the array
                for(int y = 0; y < hash.get(split[x]).size(); y++){
                    arrayIndex.add(hash.get(split[x]).get(y));
                }//end for
            }catch(Exception E){
                //skip the the copy and move on    
            }//end try
            //add the new index to array
            arrayIndex.add(index);
            hash.put(split[x], arrayIndex);
        }//end if
    }//end method
    
    /**
     * method to refresh the hash
     */
    public static void removeUpdateHash(){
        //remove the hash
        hash.clear();
        //restore the hash
        for(int x=0; x < portfolio.list.size();x++){
            String stringBuffer = "";
            stringBuffer = portfolio.list.get(x).getName();
            addHash(stringBuffer, x+1);
        }//end for
    }//end if

    /**
     * check the the range value
     * @param x the index of the investment
     * @param lowPrice of the lowest number to be search
     * @param highPrice of the highest number to be search
     * @return the string of the output
     */
    public String checkRangeValue(int x, String lowPrice, String highPrice){
        //declare variable
        String message = "";
        //check the rang
        try{
            //if it is in the price range then print
            if(lowPrice.equals("") == false && highPrice.equals("") == false){
                //declare var
                double num1 = Double.parseDouble(lowPrice);
                double num2 = Double.parseDouble(highPrice);
                //check the range
                if(num1 <= portfolio.list.get(x).getPrice() && portfolio.list.get(x).getPrice() <= num2){
                    message = message + portfolio.list.get(x).toString();
                    found = true;
                }//end if
            }else if(lowPrice.equals("") == true || highPrice.equals("") == false){
                //declare var
                double num2 = Double.parseDouble(highPrice);
                //check the range
                if(portfolio.list.get(x).getPrice() <= num2){
                    message = message + portfolio.list.get(x).toString();
                    found = true;
                }//end if
            }else if(lowPrice.equals("") == false || highPrice.equals("") == true){
                //decalre var
                double num1 = Double.parseDouble(lowPrice);
                //check the range
                if(portfolio.list.get(x).getPrice() >= num1){
                    message = message + portfolio.list.get(x).toString();
                    found = true;
                }//end if
            }//end if
        }catch(Exception e){
            System.out.println("WRONG FORMAT FOR RANGE");
        }//end if
        //return the massage
        return message;
    }//end func5
    
}//end class