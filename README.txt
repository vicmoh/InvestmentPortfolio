Student Information
####################
Name: Vicky Mohammad
ID: 0895381
Version: 3.0

PROGRAM:
    An individual may have a portfolio that holds different 
    kinds of investments and allows you to buy or sell investments, 
    search for some investments, update prices, and compute the total 
    gain of the portfolio.

How to run the program:
    ----------------------------------------------------------------------------
    Running through NetBean:
	- this program was created through netbean
	- assuming that this runs on netbeans
	- first open netbeans
	- go to "run" and then click "set configuration" then "customize" on navbar
	- set the "command argument" with the file name such as "dataName.txt"
    ----------------------------------------------------------------------------
    Running through command line:
    - go to the package directory "stock"
    - type "javac *.java" on bash to compile
    - exit bash and run type "cd .." to package folder parent of "stock"
    - type: "java stock.Menu data.txt" to run the program
    ----------------------------------------------------------------------------


Assumptions:
    - THIS PROGRAM WAS CREATED IN A VERY HIGH RESOLUTION, PLASE NOTE THAT THE WINDOW FONT AND OTHER OBJECTS MIGHT BE BIG ON YOUR SCREEN!!!!
	- fei song said that we can use any data format as long as it work
    - when user input sell quantity more than the quantity it has
      it will sell all the remaining of that investment stated
    - the data type format is "type@nsymbol@name@quantity@price@bookValue" per line per array element
      this data format is NOT MEANT TO BE READABLE TO USER
    - all textfield will reset when opening to a new panel for buy, sell, and search
    - the GUI interface is based on the format but not exact
    - it loads the data when program runs and saves when exit or quit
    - the current price is shown on the update panel for better user experience
    - when pressing the reset button it SHOULD ONLY RESET THE TEXTFIELD
    - wheen closing the program and get gain is calucaluted and restarted the program, the get gain is the same as it should be
    - each massage box is independent for each panel
    - when buying an existing investment, IT ONLY CHECK SYMBOL IF IT MATCHES
    - commandline is outputed for debug use to keep track of user input is allowed 

Limitation:
    - there must be an comand argument with a file name or the program will not run
    - the data file is parse with split @ delimiter FORMAT 
      and may not work with other data type or format
    - do not input any string that has the "@" sign or it will ruin the program
    - data file MUST be .txt
    - spaces in search or when buy or selling can cause unwanted output SO MAKE SURE THAT WHAT YOU ARE SEARCHING FOR IS EXACT INPUT
    - case sensitive might not work for the searched