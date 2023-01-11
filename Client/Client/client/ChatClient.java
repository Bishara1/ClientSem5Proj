// "Object Oriented Software Engineering" and is issued under the open-source
// This file contains material supporting section 3.7 of the textbook:
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import gui_client.UserLoginController;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import logic.Item;
import logic.Location;
import logic.Machine;
import logic.OrdersReports;
import logic.Subscriber;
import logic.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


//Boom badapam mr.WorldWide
/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  public static ArrayList<Subscriber> subscribers;//+users
  public static ArrayList<Machine> machines;
  public static ArrayList<Item> items;
  public static ArrayList<Order> orders;  
  public static ArrayList<OrdersReports> orderReport;
  public static ArrayList<Location> locations;
  public static ArrayList<Item> cart;
  public static ArrayList<Integer> available;
  public static String password;
  public static String role;
  public static String Fname;
  public static String locationName = "North"; //?????
  public static String supplyMethod = "Immediate pickup"; //?????
  public static boolean awaitResponse = false;
  public static boolean isSubscriber = false;
  public static boolean FirstSubscriberOrder = false;
  private boolean FirstCart = false;
  public static int ID;
  public static int machineToLoad = -1;
  public static Thread timer = new Thread(new Timespent());
  //public static Stage primaryStage; //-> fixes the issue of windows popping up AND solves the timing thread
  

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
   // timer.start(); //SAFWAN CREATE A NEW THREAD EVERYTIME YOU WANT TO RESTART THE TIMER OR JUST OVERRIDE THE CURRENT THREAD, THANKS IN ADVANCE.
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  @SuppressWarnings("unchecked")
  public void handleMessageFromServer(Object msg) 
  {	  
	  Message responseFromServer = (Message) msg;
	  
	  switch(responseFromServer.getCommand()) 
	  {
	 
	  	  case ReadUsers:
	  		  subscribers = (ArrayList<Subscriber>) responseFromServer.getContent();
	  		  System.out.println(subscribers);
	  		  break;
	  		  
	  	 case ReadMachines:
	  		  machines = (ArrayList<Machine>) responseFromServer.getContent();
	  		  break;
	  	
	  	 case ReadItems:
	  		  if(FirstCart == false) {
	  			  cart = new ArrayList<Item>();
	  			  FirstCart = true;
	  		  }
	  		  items = (ArrayList<Item>) responseFromServer.getContent();
	  		  break;
	  		  
	  	  case Disconnect:
	  		  ClientUI.chat.display("Disconnected");
	  		  break;
	  	  
	  	  case Connect:
	  		  String[] passRoleFnameSubNumFirstCart = (String[])(((Message)msg).getContent());
	  		  password = passRoleFnameSubNumFirstCart[0];
	  		  role = passRoleFnameSubNumFirstCart[1];
	  		  Fname = passRoleFnameSubNumFirstCart[2];
	  		  if(Integer.parseInt(passRoleFnameSubNumFirstCart[3]) != -1)
	  			  isSubscriber = true;
	  		  else
	  			  isSubscriber = false;
	  		  if(Integer.parseInt(passRoleFnameSubNumFirstCart[4]) == 1)
	  			  FirstCart = true;
	  		  else
	  			  FirstCart = false;
	  		  break;
	  		  
	  	case ReadLocations:
	  		locations = (ArrayList<Location>) (responseFromServer.getContent());
	  		break; 
	  		
	  	case ReadOrders:
	  		orders = (ArrayList<Order>)(responseFromServer.getContent());
	  		break;
	  		
	  	case ReadOrdersReports:
	  		orderReport = (ArrayList<OrdersReports>) (responseFromServer.getContent());
	  		break;
	  		
	  default:
		  System.out.println("ChatClient got response but didn't deal with it");
		  break;
	  	
	  }
	  awaitResponse = false;
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(Object message)  
  {
	  try
	  {
		  openConnection(); //in order to send more than one message
    	  awaitResponse = true; 
    	  sendToServer(message);
    	
    	  while (awaitResponse) {
			  try {
				  Thread.sleep(100);
			  }   catch (InterruptedException e) {
				  e.printStackTrace();
			  }
		  }
      }
      catch(IOException e)
      {
          clientUI.display("Could not send message to server.  Terminating client.");
          quit();
      }
    }
  
  public void Logout() throws Exception
  { 
	  
  }
  
  
  /**
   * This method terminates the client.
   */
  public void quit() {
    try {
    	closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
}
//End of ChatClient class
