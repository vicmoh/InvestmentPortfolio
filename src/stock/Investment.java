/*Name: Vicky Mohamamd
 *ID: 0895381
 */
package stock;
import java.text.DecimalFormat;

/**
 * a super class for the investment
 * @author vicky mohammad
 */
public abstract class Investment {
    //decalre variable
    private String symbol, name;
    private int quantity = 0;
    private double price = 0;
    protected static int numberOfStock = 0;
    protected static int numberOfMutualFund = 0;
    //set object of 2 decimal format
    public DecimalFormat twoDecimal = new DecimalFormat();
    
    /**
     * a constructor to create the super class
     * @param symbol to initialize for the stock
     * @param name to initialize for the stock
     * @param quantity to initialize for the stock
     * @param price to initialize for the stock
     * @throws stock.Investment.customException for the investment
     */
    public Investment(String symbol, String name, int quantity, double price) throws customException{
        //create an exception
        if(name.equals("")){
            throw new customException("Cannot leave name blank.\n");
        }else if(quantity <= 0){
            throw new customException("Quantity must be a whole number greater than 0.\n");
        }else if(price <= 0){
            throw new customException("Price must be greater than 0.\n");
        }else if(symbol.equals("")){
            throw new customException("Cannot leave symbol blank.\n");
        }//end if
        //add books
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }//end func
    
    /**
     * set the new price of the investment
     * @param newPrice of the investment
     * @throws stock.Investment.customException for the investment
     */
    public abstract void priceChanged(double newPrice)throws customException;
    
    /**
     * get the gain for the investment
     * @return the gain of the investment
     */
    public abstract double getGain();
    
    /**
     * the abstract method for buying
     * @param price of the investment
     * @param quantity of the investment
     * @return the string for the message output
     * @throws stock.Investment.customException for the investment
     */
    public abstract String buy(double price, int quantity) throws customException;
    
    /**
     * the abstract method for selling investment
     * @param quantityToSell of the investment
     * @param price of the investment
     * @return the string of the message output
     * @throws stock.Investment.customException for the investment
     */
    public abstract String sell(int quantityToSell, double price) throws customException;
    
    /**
     * get the number of stocks 
     * @return the number of stocks
     */
    public int getNumberOfStock(){
        return numberOfStock;
    }//end func
    
    /**
     * get the number of mutual fund
     * @return get the number of mutual fund
     */
    public int getNumberOfMutualFund(){
        return numberOfMutualFund;
    }//end func
    
    /**
     * function to get symbol of the object
     * @return symbol of the object
     */
    public String getSymbol(){
        return symbol;
    }//end func
    
    /**
     * function to get the name
     * @return return the name of the stock
     */
    public String getName(){
        return name;
    }//end func
    
    /**
     * get the quantity of the class
     * @return the quantity of the stock
     */
    public int getQuantity(){
        return quantity;
    }//end func
    
    /**
     * function to get price
     * @return the price of the stock
     */
    public double getPrice(){
        return price;
    }//end func
    
    /**
     * set the price
     * @param price the price to be set
     */
    protected void setPrice(double price){
        this.price = price;
    }//end func
    
    /**
     * set the quantity of the investment
     * @param quantity that is to be set
     */
    protected void setQuantity(int quantity){
        this.quantity = quantity;
    }//end func
    
    /**
     * set the symbol of the investment
     * @param symbol that is to be set
     */
    protected void setSymbol(String symbol){
        this.symbol = symbol;
    }//end func
    
    /**
     * set the name of the investment
     * @param name that is to be set
     */
    protected void setName(String name){
        this.name = name;
    }//end func
    
    /**
     * and abstract for the data to be safe and load
     * @return the string of the info data to be written and load
     */
    public abstract String data();
    
    /**
     * check if the symbol is equal
     * @param otherObject that is being compared
     * @return the symbol
     */
    @Override public boolean equals(Object otherObject){
        if (otherObject == null){       
            return false;     
        }else if (getClass() != otherObject.getClass()){         
            return false;     
        }else{
            Investment otherInvestment = (Investment)otherObject;         
            return (symbol.equals(otherInvestment.symbol));//return true 
        }//end if
    }//end func
    
    /**
     * print the output
     * @return return the string of the stock
     */
    @Override public abstract String toString();
    
    public class customException extends Exception{
        public customException(String message){
            super(message);
        }//end func
    }//end class
}//end class
