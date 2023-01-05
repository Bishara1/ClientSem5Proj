// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import logic.Item;
import logic.Location;
import logic.Machine;
import logic.OrdersReports;
import logic.Subscriber;


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
  public static boolean awaitResponse = false;
  public static ArrayList<Subscriber> subscribers;//+users
  public static ArrayList<Machine> machines;
  public static ArrayList<Item> items;
  public static ArrayList<OrdersReports> orderReport;
  private boolean FirstCart = false;
  public static ArrayList<Item> cart;
  public static int ID;
  public static ArrayList<Location> locations;
  public static String password;
  public static String role;
  public static String Fname;
  public static boolean isSubscriber = false;
  public static int machineToLoad = -1;
  public static String locationName = "North"; //?????
  public static String supplyMethod = "Immediate pickup"; //?????
  

  
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
	  		  String[] passRoleFname = (String[])(((Message)msg).getContent());
	  		  password = passRoleFname[0];
	  		  role = passRoleFname[1];
	  		  Fname = passRoleFname[2];
	  		  
	  		  break;
	  		  
	  	case ReadLocations:
	  		locations = (ArrayList<Location>) (responseFromServer.getContent());
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
