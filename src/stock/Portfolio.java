/*Name: Vicky Mohamamd
 *ID: 0895381
 */
package stock;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static stock.Menu.addHash;
import static stock.Menu.portfolio;

/**
 * a class for array list of investment
 * to make thing easier 
 * @author vicky mohammad
 */
public class Portfolio {
    //declare var
    ArrayList<Investment> list = new ArrayList<Investment>();
    
    /**
     * function to be able to get the methods of stock in array
     * by casting the array of the object
     * @param x the element of the list
     * @return the stock of the list
     */
    public Stock getStock(int x){
        return (Stock)list.get(x);
    }//end func
    
    /**
     * function to be able to get the methods of mutual in array
     * by casting the array of the object
     * @param x the element of the list
     * @return the mutual fund
     */
    public MutualFund getMutualFund(int x){
        return (MutualFund)list.get(x);
    }//end func
    
    /**
     * add a new stock to the portfolio
     * @param symbol of the object
     * @param name of the object
     * @param quantity of the object
     * @param price of the object
     * @return the string of the output
     */
    public String addStock(String symbol, String name, int quantity, double price){
        //declare var
        String message = "";
        //go through my custom exception
        try{
            list.add(new Stock(symbol, name, quantity, price));
            addHash(name, portfolio.list.size());
            message = message + "Buying " + quantity + " '" + name + "' stock at $" + price + ".\n";
        }catch(Investment.customException e){
            message = message + e.getMessage();
        }//end if 
        return message;
    }//end func
    
    /**
     * add a mutual fund to the portfolio
     * @param symbol of the object
     * @param name of the object
     * @param quantity of the object
     * @param price of the object
     * @return the string of the output
     */
    public String addMutual(String symbol, String name, int quantity, double price){
        //declare var
        String message = "";
        //go through the custom exception
        try{
            list.add(new MutualFund(symbol, name, quantity, price));
            addHash(name, portfolio.list.size());
            message = message + "Buying " + quantity + " '" + name + "' mutual fund at $" + price + ".\n";
        }catch(Investment.customException e){
            message = message + e.getMessage();
        }//end if
        return message;
    }//end func
    
    /**
     * print all the list
     * @return the string of the output
     */
    public String toStringList(){
        //declare var
        String message = "";
        //set object of 2 decimal format
        DecimalFormat twoDecimal = new DecimalFormat();
        twoDecimal.setMaximumFractionDigits(2);
        //display book 
        for(int x = 0; x < list.size();x++){
            if(list.get(x) instanceof Stock){
                Stock stock = (Stock)list.get(x);
                message = message + stock.toString();
            }else{
                MutualFund mutual= (MutualFund)list.get(x);
                message = message + mutual.toString();
            }//end if
        }//end for
        //return the output
        return message;
    }//end func
}//end class
