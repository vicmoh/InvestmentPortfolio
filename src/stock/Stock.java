/*Name: Vicky Mohamamd
 *ID: 0895381
 */
package stock;
import java.text.DecimalFormat;

/**
 * class for stock
 * @author Vicky Mohammad
 */
public class Stock extends Investment {
    //decalre variables
    private double bookValue = 0;
    private double gain = 0;
    public static final double STOCK_FEE = 9.99;
    public DecimalFormat twoDecimal = new DecimalFormat();
    
    /**
     * a constructor to create a stock
     * @param symbol to initialize for the stock
     * @param name to initialize for the stock
     * @param quantity to initialize for the stock
     * @param price to initialize for the stock
     * @throws stock.Investment.customException of the investment
     */
    public Stock(String symbol, String name, int quantity, double price) throws customException{
        //add books
        super(symbol, name, quantity, price);
        this.bookValue = quantity * price + STOCK_FEE;
        super.numberOfStock = super.numberOfStock + 1;
    }//end func
    
    /**
     * a constructor for reading the file
     * @param symbol symbol of the investment
     * @param name symbol of the investment
     * @param quantity symbol of the investment
     * @param price symbol of the investment
     * @param bookValue symbol of the investment
     * @throws stock.Investment.customException of the investment
     */
    public Stock(String symbol, String name, int quantity, double price, double bookValue) throws customException{
        //add books
        super(symbol, name, quantity, price);
        this.bookValue = bookValue;
        super.numberOfStock = super.numberOfStock + 1;
    }//end func
    
    /**
     * get the book value
     * @return  return the book value of the stock
     */
    public double getBookValue(){
        return bookValue;
    }//end func
    
    /**
     * set new price
     * @param newPrice set the new price 
     */
    @Override public void priceChanged(double newPrice)throws customException{
        //throw exception
        if(newPrice <= 0){
            throw new customException("Price must be a number greater than 0.\n");
        }//end if
        //set the new value when price is changed
        setPrice(newPrice);
        gain = (getQuantity() * newPrice - STOCK_FEE)- bookValue;
    }//end func
    
    /**
     * get the price
     * @return return the gain
     */
    @Override public double getGain(){
        //update the new price
        try{
            priceChanged(getPrice());
        }catch(customException e){
            e.getMessage();
        }//end if
        return gain;
    }//end func
    
    /**
     * calculate the stock when buying
     * @param price for calculating
     * @param quantity for calculation
     */
    @Override public String buy(double price, int quantity ) throws customException{
        //declare var
        String message = "";
        //throw exception
        if(quantity <= 0){
            throw new customException("Quantity must be a whole number greater than 0.\n");
        }else if(price <= 0){
            throw new customException("Price must be a number greater than 0.\n");
        }//end if
        //decalre var
        int totalPreQuantity;
        //calculate the buy
        totalPreQuantity = getQuantity();
        setQuantity(getQuantity() + quantity);
        priceChanged(price);
        //count, and add the number of stock
        this.bookValue = bookValue * ((totalPreQuantity + (double)quantity)/totalPreQuantity);
        super.numberOfStock = super.numberOfStock + 1;
        //get the message and return
        message = message + "Buying more " + quantity + " '" + getSymbol() + "' stock at $" + price + ".\n";
        message = message + "You now have have " + getQuantity() + " '" + getSymbol() +"' stock in your portfolio.\n";
        return message;
    }//end if
    
    /**
     * calculate for selling the stock
     * @param price of the investment
     * @param quantityToSell the amount to sell
     */
    @Override public String sell(int quantityToSell, double price) throws customException{
        //throw exception
        if(quantityToSell <= 0){
            throw new customException("Quantity must be a whole number greater than 0.\n");
        }else if(price <= 0){
            throw new customException("Price must be a number greater than 0.\n");
        }//end if
        //declare var
        String message = "";
        boolean printSell = false;
        double paymentRecieved = 0;
        priceChanged(price);
        twoDecimal.setMaximumFractionDigits(2);
        //check if the user is selling more then the quantity
        if(getQuantity() >= quantityToSell){
            //subtract the number of stock
            if(getQuantity() == quantityToSell){
                printSell = true;       
                numberOfStock = numberOfStock - 1;
            }//end if
            double totalPreQuantity;
            totalPreQuantity = getQuantity();
            setQuantity(getQuantity() - quantityToSell);
            bookValue = bookValue * ((totalPreQuantity - (double)quantityToSell)/totalPreQuantity);
            //payment recieved after sell
            paymentRecieved = (quantityToSell * getPrice()) - STOCK_FEE;
            //symbol is found, print the info
            message = message + "Symbol found...\n";
            message = message + ("Payment received $"  + twoDecimal.format(paymentRecieved) + ".\n");
            
            //print if it is sold
            if(printSell == true){
                message = message + "Selling all what you have...\n";
            }else{
                message = message + "Selling " + quantityToSell + " '" + getSymbol() + "' stock at $" + getPrice() + ".\n";
                message = message + "You now have have " + getQuantity() + " '" + getSymbol() +"' stock in your portfolio.\n";
            }
        }else{
            //remove the number of stock
            numberOfStock = numberOfStock - 1;
            //calculate and print
            paymentRecieved = (getQuantity() * getPrice()) - STOCK_FEE;
            message = message + ("QUANTITY OUT OF BOUND.\n");
            message = message + ("Selling what you have...\n");
            message = message + ("Payment received "  + twoDecimal.format(paymentRecieved) + ".\n");
        }//end if
        //return the message string
        return message;
    }//end func
    
    /**
     * the data string that will be saved to a file
     * @return the string data to be saved to a file
     */
    @Override public String data(){
        return ("s" + "@" + getSymbol() + "@" + getName() + "@" + getQuantity() + "@" + getPrice() + "@" + bookValue);
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
     * print the output of the stock
     * @return return the string of the stock
     */
    @Override public String toString(){
        twoDecimal.setMaximumFractionDigits(2);
        //display
        System.out.println("*************************************");
        System.out.println("Type: Stock");
        System.out.println("Symbol: " + getSymbol());
        System.out.println("Name: " + getName());
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Price: $" + twoDecimal.format(getPrice()));
        System.out.println("Book Value: $" + twoDecimal.format(bookValue));
        System.out.println("*************************************");
        return String.format("*************************************" +
                             "\nType: Stock" +
                             "\nSymbol: " + getSymbol() +
                             "\nName: " + getName() +
                             "\nQuanity: " + getQuantity() +
                             "\nPrice: $" + twoDecimal.format(getPrice()) +
                             "\nStock value: $" + twoDecimal.format(bookValue) +
                             "\n*************************************\n");
    }//end func
}//end class
