/*Name: Vicky Mohamamd
 *ID: 0895381
 */
package stock;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
//swing gui lib
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import stock.Menu;
import static stock.Menu.portfolio;

/**
 * a class for the user interface using swing
 * @author vicky mohammad
 */
public class UserInterface {
    
    //decclare var for decimal format
    private static DecimalFormat numberToString = new DecimalFormat("#.00");
    //create an object for the back end
    private Menu controller = new Menu();
    //delcare vaiables
    private static JFrame mainWindow;
    private static JPanel subWindow;
    //declare final for type
    private static final int STOCK = 1;
    private static final int MUTUAL_FUND = 2;
    //declare final for type
    private static final int BUY = 1;
    private static final int SELL = 2;
    private static final int UPDATE = 3;
    private static final int GETGAIN = 4;
    private static final int SEARCH = 5;
    private static final int FONT_SIZE = 35; 
    private static final String FONT_TYPE = "Serif";
    //declare string for the text field
    private String textFieldString;
    public String symbol;
    public String name;
    public int quantity;
    public double price;
    public String lowPrice;
    public String highPrice;
    private String buyMessage = "";
    private String sellMessage = "";
    private String updateMessage = "";
    private String getGainMessage = "";
    private String searchMessage = "";
    //create the textfield
    private JTextField symbolField = new JTextField("");
    private JTextField nameField = new JTextField("");
    private JTextField quantityField = new JTextField("");
    private JTextField priceField = new JTextField("");
    private JTextField lowPriceField = new JTextField("");
    private JTextField highPriceField = new JTextField("");
    private JTextArea messageArea = new JTextArea();
    private JTextField gainArea = new JTextField("");
    //declares buttons
    private JButton resetButton = new JButton("Reset");
    private JButton buyButton = new JButton("Buy");
    private JButton sellButton = new JButton("Sell");
    private JButton nextButton = new JButton("Next");
    private JButton prevButton = new JButton("Prev");
    private JButton saveButton = new JButton("Save");
    private JButton searchButton = new JButton("Search");
    //declare var for updating panel
    private int currentInvestmentPosition = 0;
    //combox 
    private String[] investmentTypes = { "Stock", "MutualFund"};
    public JComboBox<String> investmentBox = new JComboBox<String>(investmentTypes);
    private int comboSelectedType = 1;
    
    /**
     * get the text from in the field;
     * @return the the text in the text field
     */
    public String getTextField(){
        return textFieldString; 
    }//end if
    
        /**
     * a function to catch wrong format for integer 
     * @param toBeConverted from the input to integer
     * @return -1 if wrong format
     */
    public int tryCatchInt(String toBeConverted){
        int number = 0;
        try{
            number = Integer.parseInt(toBeConverted);
        }catch(NumberFormatException E){
            System.out.println("WRONG FORMAT");
            return number = -1;
        }//end catch
        return number;
    }//end  func
    
    /**
     * a function to catch wrong format for integer
     * @param toBeConverted from input to integer
     * @return -1 if wrong format
     */
    public double tryCatchDouble(String toBeConverted){
        double number = 0;
        try{
            number = Double.parseDouble(toBeConverted);
        }catch(NumberFormatException E){
            System.out.println("WRONG FORMAT");
            return number = -1;
        }//end catch
        return number;
    }//end  func
    
    /**
     * set the the universal font of all user interface
     * @param f the font that being changed
     */
    public void setUIFont (javax.swing.plaf.FontUIResource f){
        //set the default frame of the whole font
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource){
                UIManager.put (key, f);
            }//end if
        }//end while
    }//end func
    
    /**
     * set a new window
     */
    public void setWindow(){
        //set the default font for all the windows
        setUIFont (new javax.swing.plaf.FontUIResource(FONT_TYPE,Font.BOLD,FONT_SIZE));
        symbolField.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        nameField.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        quantityField.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        priceField.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        lowPriceField.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        highPriceField.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        messageArea.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        gainArea.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        investmentBox.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        resetButton.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        buyButton.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        sellButton.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        nextButton.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        prevButton.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        saveButton.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        searchButton.setFont(new Font(FONT_TYPE, Font.BOLD, FONT_SIZE));
        //set up the subwindow
        subWindow = new JPanel(new GridLayout(1,1));
        subWindow.add(startUpPanel());
        //set up the window
        mainWindow = new JFrame("Investment Portfolio");
        mainWindow.setSize(1280, 720);
        //exit the window when user clicked the close button
        mainWindow.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent windowEvent){
                //save the array list and exit the program
                controller.savePortfolio(controller.fileName);
                System.exit(0);
            }//end window
        });//end listiner
        
        mainWindow.setLocationRelativeTo(null);
        
        //container
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 1));
        
        //menu or comand line objects
        JTextField commandLine = new JTextField("Write your commands here...");
        JTabbedPane commandTab = new JTabbedPane();
        
        //create the menu bar for the command
        final JMenuBar menuBar = new JMenuBar();
        JMenu command = new JMenu("Command");
        //create the child menu
        final JMenuItem buyMenu = new JMenuItem("Buy");
        final JMenuItem sellMenu = new JMenuItem("Sell");
        final JMenuItem updateMenu = new JMenuItem("Update");
        final JMenuItem getGainMenu = new JMenuItem("GetGain");
        final JMenuItem searchMenu = new JMenuItem("Search");
        final JMenuItem quitMenu = new JMenuItem("Quit");
        
        //set action the menu items to the
        buyMenu.setActionCommand("Buy");
        sellMenu.setActionCommand("Sell");
        updateMenu.setActionCommand("Update");
        getGainMenu.setActionCommand("GetGain");
        searchMenu.setActionCommand("Search");
        quitMenu.setActionCommand("Quit");
        
        //set the action
        buyMenu.addActionListener(new BuyPanelListener());
        sellMenu.addActionListener(new SellPanelListener());
        updateMenu.addActionListener(new UpdatePanelListener());
        getGainMenu.addActionListener(new GetGainPanelListener());
        searchMenu.addActionListener(new SearchPanelListener());
        quitMenu.addActionListener(new QuitPanelListener());
       
        //add to the command
        command.add(buyMenu);
        command.add(sellMenu);
        command.add(updateMenu);
        command.add(getGainMenu);
        command.add(searchMenu);
        command.add(quitMenu);
        //add to the menu bar
        menuBar.add(command);
        //add to the main window
        mainWindow.add(menuBar, BorderLayout.NORTH);
        mainWindow.add(subWindow);
        //main window setup
        mainWindow.setMinimumSize(new Dimension(1280, 720));
        mainWindow.setVisible(true);
    }//end method
    
    /**
     * create start up panel
     * @return returns the content of start up panel
     */
    public JPanel startUpPanel(){
        //create the panel
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1,1));
        
        //create the text
        JTextArea welcomeText = new JTextArea("\n\n          Welcome to Investment Portfolio\n\n"+
            "          Choose a command from the “Commands” menu to buy or sell\n" +
            "          an investment, update prices for all investments, get gain for\n" +
            "          the portfolio, search for relevant investments, or quit the\n" +
            "          program.");
        //set the charateristic
        welcomeText.setBackground(Color.white);
        welcomeText.setEditable(false);
        
        //add the text in the panels
        content.add(welcomeText);
        return content;
    }//end if
    
    /**
     * a content panel that display the main content
     * @param type of the panel
     * @return of the panel
     */
    public JPanel contentPanel(int type){
        //create the panel
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(2,1));
        JLabel headerLabel = new JLabel("Header");
        
        //bottom content
        JPanel bottomContent = new JPanel(new GridBagLayout());
        GridBagConstraints bottomConstraints = new GridBagConstraints();
        //header label
        JLabel bottomHeaderLabel = new JLabel("Messages", SwingConstants.LEFT);
        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 0;
        bottomConstraints.weightx = 1;
        if(type == GETGAIN){
            //center the bottom header if its get gain 
            bottomConstraints.anchor = GridBagConstraints.CENTER;
        }else{
            //else make the bottom header to the left
            bottomConstraints.anchor = GridBagConstraints.WEST;
        }//end if
        bottomContent.add(bottomHeaderLabel, bottomConstraints);
        //the message box
        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 1;
        bottomConstraints.weightx = 1;
        bottomConstraints.weighty = 1;
        bottomConstraints.fill = GridBagConstraints.BOTH;
        //add to message panel      
        bottomConstraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
        bottomContent.add(messagePanel(), bottomConstraints);
        
        //create the top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,2));
        
        //add the top panel for left and right right
        if(type == GETGAIN){
            topPanel.add(getGainPanel());
            //content.setLayout(new GridLayout(1,1));
        }else{
            topPanel.add(inputInfoPanel(type));
            topPanel.add(buttonPanel(type));
        }//end if
        
        //add and return the panel
        content.add(topPanel);
        content.add(bottomContent);
        
        //add a header
        JPanel contentWithHeader = new JPanel(new BorderLayout());
        contentWithHeader.add(headerLabel, BorderLayout.NORTH);
        
        //create t he string of the header
        String headerString = "";
        String bottomHeaderString = "";
        if(type == BUY){
            headerString = "Buying an Investment";
            bottomHeaderString = "Messages";
        }else if(type == SELL){
            headerString = "Selling an Investment";
            bottomHeaderString = "Messages";
        }else if(type == UPDATE){
            headerString = "Updating Investments";
            bottomHeaderString = "Messages";
        }else if(type == GETGAIN){
            headerString = "Getting Total Gain";
            bottomHeaderString = "Individual Gains";
        }else if(type == SEARCH){
            headerString = "Searching Investments";
            bottomHeaderString = "Messages";
        }//end if
        headerLabel.setText(headerString);
        bottomHeaderLabel.setText(bottomHeaderString);
        
        //return the panel
        contentWithHeader.add(content);
        return contentWithHeader;
    }//end method
        
    /**
     * create the user inputs panel
     * @param type of the panel
     * @return the panel of for the inputs
     */
    public JPanel inputInfoPanel(int type){
        //create objects
        JPanel centerContent = new JPanel(new BorderLayout());
        JPanel content = new JPanel(new GridLayout(8,1));
        
        //add the textfield depending of the type
        if(type == BUY){
            content.setLayout(new GridLayout(5,1));
        }else if(type == SELL){
            content.setLayout(new GridLayout(3,1));
        }else if(type == UPDATE){
            content.setLayout(new GridLayout(3,1));
        }else if(type == SEARCH){
            content.setLayout(new GridLayout(4,1));
        }//end if
        
        //for choosing the type of investment
        if(type == BUY){
            JPanel investmentComboBoxWithPanel = new JPanel(new GridLayout(1 , 2));
            JLabel typeLabel = new JLabel("Type");
            investmentComboBoxWithPanel.add(typeLabel);
            investmentComboBoxWithPanel.add(investmentBox);
            investmentBox.setVisible(true);
            content.add(investmentComboBoxWithPanel);
            //create action for combo box
            investmentBox.setSelectedIndex(0);
            investmentBox.addActionListener(new comboBoxListener());
        }//end if
        //for symbol
        if(type == BUY || type == SELL || type == UPDATE || type == SEARCH){
            content.add(inputPanel("Symbol", symbolField));
            symbol = getTextField();
        }//end if
        //for name
        if(type == BUY || type == UPDATE || type == SEARCH){
            content.add(inputPanel("Name", nameField));
            name = getTextField();
        }//end if
        //for quantity
        if(type == BUY || type == SELL){
            content.add(inputPanel("Quantity", quantityField));
            quantity = tryCatchInt(getTextField());
        }//end if
        //for price
        if(type == BUY || type == SELL || type == UPDATE){
            content.add(inputPanel("Price", priceField));
            price = tryCatchDouble(getTextField());
        }//end if
        //for low price
        if(type == SEARCH){
            content.add(inputPanel("Low Price", lowPriceField));
            lowPrice = getTextField();
        }//end if
        //for high price
        if(type == SEARCH){
            content.add(inputPanel("High Price", highPriceField));
            highPrice = getTextField();
        }//end if
        
        //add to the center
        centerContent.add(content);
        return centerContent;
    }//end method
    
    /**
     * a method for creating a text field with a label 
     * @param textField the text field that you want to create
     * @param label for assigning the string of the label
     * @return the JPanel of the content
     */
    public JPanel inputPanel(String label, JTextField textField){
        //top content
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1,2));
        GridBagConstraints constraints = new GridBagConstraints();
        //info of total gain label
        JLabel totalGainLabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add(totalGainLabel, constraints);
        //show the total gain in text area
        //textField = new JTextField("");
        textField.setPreferredSize(new Dimension(800, 60));//set the size
        constraints.insets = new Insets(20, 20, 20, 20);//inverse padding
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(textField, constraints);
        textFieldString = textField.getText();
        return content;
    }//end method
    
    /**
     * a method for creating the button
     * @param type of the panel
     * @return the panel of the button layout
     */
    public JPanel buttonPanel(int type){
        //declares buttons
        resetButton = new JButton("Reset");
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        nextButton = new JButton("Next");
        prevButton = new JButton("Prev");
        saveButton = new JButton("Save");
        searchButton = new JButton("Search");
        //set up whats going to be in the content
        JPanel content = new JPanel();
        /*change the layout*/
        JPanel centerReset = new JPanel();
        JPanel centerBuy = new JPanel();
        JPanel centerSell = new JPanel();
        JPanel centerNext = new JPanel();
        JPanel centerPrev = new JPanel();
        JPanel centerSave = new JPanel();
        JPanel centerSearch = new JPanel();
        //sett the default size of all button
        final int yLength = 80;
        final int xLength = 200;
        resetButton.setPreferredSize(new Dimension(xLength,yLength));
        buyButton.setPreferredSize(new Dimension(xLength,yLength));
        sellButton.setPreferredSize(new Dimension(xLength,yLength));
        nextButton.setPreferredSize(new Dimension(xLength,yLength));
        prevButton.setPreferredSize(new Dimension(xLength,yLength));
        saveButton.setPreferredSize(new Dimension(xLength,yLength));
        searchButton.setPreferredSize(new Dimension(xLength,yLength));
        
        //set the grid layout depending of the menu
        if(type == BUY || type == SELL || type == SEARCH){
            content.setLayout(new GridLayout(2, 1));
        }//end if
        if(type == UPDATE){
            content.setLayout(new GridLayout(3, 1));
        }//end if
        
        if(type == UPDATE){
            symbolField.setEditable(false);
            nameField.setEditable(false);
        }else{
            symbolField.setEditable(true);
            nameField.setEditable(true);
        }//end if
        
        //for reset button
        if(type == BUY || type == SELL || type == SEARCH){
            centerReset.add(resetButton);
            content.add(centerReset);
        }//end if
        //for the buy button
        if(type == BUY){
            centerBuy.add(buyButton);
            content.add(centerBuy);
        }//end if
        //for sell button
        if(type == SELL){
            centerSell.add(sellButton);
            content.add(centerSell);
        }//end if
        //for next and prev button
        if(type == UPDATE){
            centerNext.add(nextButton);
            centerPrev.add(prevButton);
            centerSave.add(saveButton);
            content.add(centerNext);
            content.add(centerPrev);
            content.add(centerSave);
        }//end
        //for the save button
        if(type == SEARCH){
            centerSearch.add(searchButton);
            content.add(centerSearch);
        }//end if
        
        //action listiner for the button
        resetButton.addActionListener(new PerformResetListener());
        buyButton.addActionListener(new PerformBuyListener());
        sellButton.addActionListener(new PerformSellListener());
        nextButton.addActionListener(new PerformNextListener());
        prevButton.addActionListener(new PerformPrevListener());
        saveButton.addActionListener(new PerformSaveListener());
        searchButton.addActionListener(new PerformSearchListener());
        
        //return 
        return content;
    }//end method
    
    /**
     * create get gain panel
     * @return the content of gain gain panel
     */
    public JPanel getGainPanel(){
        //create the panel
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1,1));
        
        //top content
        JPanel topContent = new JPanel(new GridBagLayout());
        GridBagConstraints topConstraints = new GridBagConstraints();
        //info of total gain label
        JLabel totalGainLabel = new JLabel("Total gain");
        topConstraints.gridx = 0;
        topConstraints.gridy = 0;
        topContent.add(totalGainLabel, topConstraints);
        //show the total gain in text area
        gainArea.setPreferredSize(new Dimension(800, 60));//set the size
        gainArea.setEnabled(false);
        topConstraints.insets = new Insets(20, 20, 20, 20);//inverse padding
        topConstraints.gridx = 1;
        topConstraints.gridy = 0;
        topConstraints.gridwidth = 1;
        topConstraints.fill = GridBagConstraints.HORIZONTAL;
        topContent.add(gainArea, topConstraints);
        
        //add to the content and return the content
        content.add(topContent);
        return content;
    }//end if
   
    /**
     * create the message panel to show the message
     * @return the scroll panel of the message panel
     */
    public JScrollPane messagePanel(){
        //create the panel
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1,1));
        //create the text
        JScrollPane scrollMessage = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //set the charateristic
        messageArea.setEditable(false);
        messageArea.setBackground(Color.white);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        //add the text in the panels
        content.add(scrollMessage);
        return scrollMessage;
    }//end method 
    
    /**
     * a pop-up window that display any text
     * @param text that you want to display
     */
    public static void popup(String text){
        //create window
        JFrame frame = new JFrame();
        frame.setSize(800, 400);
        frame.setLayout(new GridLayout(2, 1));  
        //create objects 
        JLabel info = new JLabel(text, SwingConstants.CENTER);
        JButton button = new JButton("Okay");
        
        //set it to the middle
        frame.setLocationRelativeTo(null);
        
        //center the components
        JPanel centerInfo = new JPanel(new BorderLayout());
        JPanel centerButton = new JPanel();
        centerInfo.add(info, BorderLayout.CENTER);
        centerButton.add(button, BorderLayout.CENTER);
        
        //add to the frame
        frame.add(centerInfo);
        frame.add(centerButton);
        frame.setVisible(true);
        //when butto is clicked
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }//end action    
        });
    }//end func
    
    /**
     * clear the text field when invoked
     */
    public void clearTextField(){
        //reset the text in for the textfield
        symbolField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        lowPriceField.setText("");
        highPriceField.setText("");
    }//end func
    
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * an action listener when opening the buy panel
     */
    class BuyPanelListener implements ActionListener {
        /**
         * the action event
         * @param e  the action event
         */
        public void actionPerformed(ActionEvent e) {
            //remove the content and show the buy panel
            subWindow.removeAll();
            subWindow.add(contentPanel(BUY));
            clearTextField();
            messageArea.setText(buyMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when opening the sell panel
     */
    class SellPanelListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //remove the content and show sell panel
            subWindow.removeAll();
            subWindow.add(contentPanel(SELL));
            clearTextField();
            messageArea.setText(sellMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when opening the update panel
     */
    class UpdatePanelListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //remove the content and show the update the panel 
            subWindow.removeAll();
            subWindow.add(contentPanel(UPDATE));
            //set the textfield and position of the investment
            currentInvestmentPosition = 0;
            updateMessage = updateMessage + controller.option3(price, currentInvestmentPosition);
            symbolField.setText(controller.getCurrentSymbol());
            nameField.setText(controller.getCurrentName());
            priceField.setText(controller.getCurrentPrice());
            
            //clear textfield if nothing in there
            if(controller.portfolio.list.size() <= 0){
                clearTextField();
            }//end if
            
            //set the button enabled or disabled
            if(controller.portfolio.list.size() <= 1){
                nextButton.setEnabled(false);
                prevButton.setEnabled(false);
            }else if(currentInvestmentPosition == 0 && portfolio.list.size() > 1){
                nextButton.setEnabled(true);
                prevButton.setEnabled(false);
            }//end if
            
            //set it visible
            messageArea.setText(updateMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when opening the get gain panel
     */
    class GetGainPanelListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //remove the the content and show ge gain panel
            subWindow.removeAll();
            subWindow.add(contentPanel(GETGAIN));
            //calculate and set the get gain area
            getGainMessage = "";
            getGainMessage = getGainMessage + controller.option4();
            gainArea.setText(numberToString.format(controller.getTotalGain()));
            //update the frame
            messageArea.setText(getGainMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when opening the search panel
     */
    class SearchPanelListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //remove the content and showo the search panel
            subWindow.removeAll();
            subWindow.add(contentPanel(SEARCH));
            clearTextField();
            messageArea.setText(searchMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when opening the quit panel
     */
    class QuitPanelListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //exit when click quit on command and save the array
            controller.savePortfolio(controller.fileName);
            System.exit(0);
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the reset button
     */
    class PerformResetListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //reset the text in for the textfield
            symbolField.setText("");
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
            lowPriceField.setText("");
            highPriceField.setText("");
            //refresh just in case
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the buy button
     */
    class PerformBuyListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //get the string at the textfield
            symbol = symbolField.getText();
            name = nameField.getText();
            //check if quantity and price is correct format
            quantity = tryCatchInt(quantityField.getText());
            price = tryCatchDouble(priceField.getText());
            //add the stock and set the total gain
            buyMessage = buyMessage + controller.option1(comboSelectedType, symbol, name, quantity, price);
            //display the message
            controller.printPorfolio();
            messageArea.setText(buyMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the sell button
     */
    class PerformSellListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //get the string at the textfield
            symbol = symbolField.getText();
            //check if quantity and price is correct format
            quantity = tryCatchInt(quantityField.getText());
            price = tryCatchDouble(priceField.getText());
            //sell the stock and set the total gain
            sellMessage = sellMessage + controller.option2(symbol, quantity, price);
            //display the message
            controller.printPorfolio();//for debug
            messageArea.setText(sellMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the next button
     */
    class PerformNextListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //move position to the next
            currentInvestmentPosition++;
            
            //set boundry of the size 
            if(currentInvestmentPosition <= 0){
                currentInvestmentPosition = 0;
                nextButton.setEnabled(true);
                prevButton.setEnabled(false);
            }else if(currentInvestmentPosition >= controller.portfolio.list.size()-1){
                currentInvestmentPosition = controller.portfolio.list.size()-1;
                nextButton.setEnabled(false);
                prevButton.setEnabled(true);
            }else{
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
            }//end 
            
            //print the invesment
            controller.option3(price, currentInvestmentPosition);
            symbolField.setText(controller.getCurrentSymbol());
            nameField.setText(controller.getCurrentName());
            priceField.setText(controller.getCurrentPrice());
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the previous button
     */
    class PerformPrevListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //move position to the prev
            currentInvestmentPosition--;
            
            //set boundry of the size 
            if(currentInvestmentPosition <= 0){
                currentInvestmentPosition = 0;
                nextButton.setEnabled(true);
                prevButton.setEnabled(false);
            }else if(currentInvestmentPosition >= controller.portfolio.list.size()-1){
                currentInvestmentPosition = controller.portfolio.list.size()-1;
                nextButton.setEnabled(false);
                prevButton.setEnabled(true);
            }else{
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
            }//end if
            
            //print the invesment
            controller.option3(price, currentInvestmentPosition);
            symbolField.setText(controller.getCurrentSymbol());
            nameField.setText(controller.getCurrentName());
            priceField.setText(controller.getCurrentPrice());
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the save button
     */
    class PerformSaveListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //get the price from the textfield
            price = tryCatchDouble(priceField.getText());
            //save the price for the current invesment
            if(portfolio.list.size() != 0){
                //set the new price when save button is clicked
                try{
                    portfolio.list.get(currentInvestmentPosition).priceChanged(price);
                    //print
                    updateMessage = updateMessage + "Price for '" + portfolio.list.get(currentInvestmentPosition).getName() + "' has been saved.\n";
                }catch(Investment.customException ce){
                    updateMessage = updateMessage + ce.getMessage();
                }//end try
                messageArea.setText(updateMessage);
                mainWindow.setVisible(true);
            }//end if
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the search button
     */
    class PerformSearchListener implements ActionListener {
        /**
         * the action event
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) { 
            //set the string to default value
            symbol = "";
            name = "";
            lowPrice = "";
            highPrice = "";
            
            //assign the textfield string to the variables
            symbol = symbolField.getText();
            name = nameField.getText();
            lowPrice = lowPriceField.getText();
            highPrice = highPriceField.getText();
            //search the invesment in the list
            searchMessage = "";
            searchMessage = searchMessage + controller.option5(symbol, name, lowPrice, highPrice);
            //print all the invesment that is being search
            messageArea.setText(searchMessage);
            mainWindow.setVisible(true);
        }//end func 
    }//end class
    
    /**
     * an action listener when performing the combo box selection
     */
    class comboBoxListener implements ActionListener{
        /**
         * the action event
         * @param e  the action event
         */
        @Override public void actionPerformed(ActionEvent e){
            System.out.println("Combo listener is selected");
            JComboBox cb = (JComboBox)e.getSource();
            String selected = (String)cb.getSelectedItem();
            if(selected.equalsIgnoreCase("Stock")){
                investmentBox.setSelectedIndex(0);
                comboSelectedType = STOCK;
                System.out.println("Stock selected");
            }else if(selected.equalsIgnoreCase("MutualFund")){
                investmentBox.setSelectedIndex(1);
                comboSelectedType = MUTUAL_FUND;
                System.out.println("Mutual Fund selected");
            }//end if
        }//end func
    }//end class

}//end class