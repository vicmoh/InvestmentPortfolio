/*Name: Vicky Mohamamd
 */
package stock;
import java.text.DecimalFormat;

/**
 * class for mutual fund
 * @author Vicky Mohammad
 */
public class MutualFund extends Investment{
    //decalre variables
    private double bookValue = 0;
    private double gain = 0;
    public static final double MUTUAL_FEE = 45;
    public DecimalFormat twoDecimal = new DecimalFormat();
    
    /**
     * constructor to create a new mutual 
     * @param symbol the symbol of the object
     * @param name the name of object
     * @param quantity the amount
     * @param price of the object
     * @throws stock.Investment.customException of the investment
     */
    public MutualFund(String symbol, String name, int quantity, double price) throws customException{
        //create the object
        super(symbol, name, quantity, price);
        this.bookValue = quantity * price;
        super.numberOfMutualFund = super.numberOfMutualFund + 1;
    }//end func
    
    /**
     * a constructor for reading the file
     * @param symbol of the investment
     * @param name of the investment
     * @param quantity of the investment
     * @param price of the investment
     * @param bookValue of the investment
     * @throws stock.Investment.customException of the investment
     */
    public MutualFund(String symbol, String name, int quantity, double price, double bookValue) throws customException{
        //create the object
        super(symbol, name, quantity, price);
        this.bookValue = bookValue;
        super.numberOfMutualFund = super.numberOfMutualFund + 1;
    }//end func
 
    /**
     * get book value of mutual fund
     * @return book value of the object
     */
    public double getBookValue(){
        return bookValue;
    }//end func
    
    /**
     * change the price the new price
     * @param newPrice of the object
     */
    @Override public void priceChanged(double newPrice) throws customException{
        if(newPrice <=0){
            throw new customException("Price must be a number greater than 0.\n");
        }//end if
        //set the new value when price is changed
        setPrice(newPrice);
        gain = (getQuantity() * newPrice - MUTUAL_FEE)- bookValue;
    }//end func
    
    /**
     * get the gain of mutual fund
     * @return the gain of the object
     */
    @Override public double getGain(){
        try{
            priceChanged(getPrice());
        }catch(customException e){
            e.getMessage();
        }//end if
        return gain;
    }//end func
    
    /**
     * calculate the when buying 
     * @param price of the mutual
     * @param quantity of the mutual
     */
    @Override public String buy(double price, int quantity )throws customException{
        //declare var
        String message = "";
        //throw exception
        if(quantity <= 0){
            throw new customException("Quantity must be a whole number greater than 0.\n");
        }else if(price <= 0){
            throw new customException("Price must be a number greater than 0.\n");
        }//end if
        //declare variables
        int totalPreQuantity;
        //calculate when buying
        totalPreQuantity = getQuantity();
        setQuantity(getQuantity() + quantity);
        priceChanged(price);
        //count, and add the number of mutualFund
        this.bookValue = bookValue * ((totalPreQuantity + (double)quantity)/totalPreQuantity);
        super.numberOfMutualFund = super.numberOfMutualFund + 1;
        //get the massage and return
        message = message + "Buying more " + quantity + " '" + getSymbol() + "' mutual fund at $" + price + ".\n";
        message = message + "You now have have " + getQuantity() + " '" + getSymbol() +"' mutual fund in your portfolio.\n";
        return message;
    }//end if
    
    /**
     * calculate the quantity to sell
     * @param price of the investment
     * @param quantityToSell the amount 
     */
    @Override public String sell(int quantityToSell, double price)throws customException{
        //throw exception
        if(quantityToSell <= 0){
            throw new customException("Quantity must be a whole number greater than 0.\n");
        }else if(price <= 0){
            throw new customException("Price must be a number greater than 0.\n");
        }//end if
        //decalre var
        String message = "";
        boolean printSell = false;
        double paymentRecieved = 0;
        twoDecimal.setMaximumFractionDigits(2);
        priceChanged(price);
        //check if the the user is selling more than the quantity
        if(this.getQuantity() >= quantityToSell){
            //remove the number of mutualFund
            if(getQuantity() == quantityToSell){
                printSell = true;
                numberOfMutualFund = numberOfMutualFund - 1;
            }//end if
            double totalPreQuantity = getQuantity();
            setQuantity(getQuantity() - quantityToSell);
            this.bookValue = this.bookValue * ((totalPreQuantity - (double)quantityToSell)/totalPreQuantity);
            //payment recieved after sell
            paymentRecieved = (quantityToSell * getPrice()) - MUTUAL_FEE;
            //symbol is found, print the info
            message = message + "Symbol found...\n";
            message = message + ("Payment received $" + twoDecimal.format(paymentRecieved) + ".\n");
            
            //print if it is sold
            if(printSell == true){
                message = message + ("Selling all what you have...\n");
            }else{
                message = message + "Selling " + quantityToSell + " '" + getSymbol() + "' mutual fund at $" + getPrice() + ".\n";
                message = message + "You now have have " + getQuantity() + " '" + getSymbol() +"' mutual fund in your portfolio.\n";
            }//end if
        }else{
            //remove the number of stock
            numberOfMutualFund = numberOfMutualFund - 1;
            //payment recieved after sell
            paymentRecieved = (getQuantity() * getPrice()) - MUTUAL_FEE;
            message = message + ("QUANTITY OUT OF BOUND.\n");
            message = message + ("Selling all what you have..\n");
            message = message + ("Payment received " + twoDecimal.format(paymentRecieved) + ".\n");
        }//end if
        //return the message string
        return message;
    }//end func
    
     /**
     * the data string that will be saved to a file
     * @return the string data to be saved to a file
     */
    @Override public String data(){
        return ("m" + "@" + getSymbol() + "@" + getName() + "@" + getQuantity() + "@" + getPrice() + "@" + bookValue);
    }//end 
    
    /**
     * check if the are the same object
     * @param otherObject that is to be check
     * @return true if its the same
     */
    @Override public boolean equals(Object otherObject){
        if (otherObject == null){
            return false;     
        }else if (getClass() != otherObject.getClass()){  
            return false;     
        }else{
            return true;
        }//end if
    }//end func
    
    /**
     * a function to print out the mutual fund
     * @return return the string of the object
     */
    @Override public String toString(){
       //set object of 2 decimal format
       twoDecimal.setMaximumFractionDigits(2);
       //display
       System.out.println("*************************************");
       System.out.println("Type: Mutual Fund");
       System.out.println("Symbol: " + getSymbol());
       System.out.println("Name: " + getName());
       System.out.println("Quantity: " + getQuantity());
       System.out.println("Price: $" + twoDecimal.format(getPrice()));
       System.out.println("Book Value: $" + twoDecimal.format(bookValue));
       System.out.println("*************************************");
       return String.format("*************************************" +
                            "\nType: Mutual Fund" + 
                            "\nSymbol: " + getSymbol() +
                            "\nName: " + getName() +
                            "\nQuantity: " + getQuantity() +
                            "\nPrice: $" + getPrice() +
                            "\nBook value: $" + twoDecimal.format(bookValue) +
                            "\n*************************************\n");
    }//end func
}//end class
