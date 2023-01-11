package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Item;
import logic.Location;
import logic.Machine;

public class ekrutOrderController implements Initializable{
	

	@FXML
	private Label codeLbl1;
	@FXML
	private Label codeLbl2;
	@FXML
	private Label codeLbl3;
	@FXML
	private Label codeLbl4;
	@FXML
	private Label priceLbl1;
	@FXML
	private Label priceLbl2;
	@FXML
	private Label priceLbl3;
	@FXML
	private Label priceLbl4;
	@FXML
	private Label amountLbl1;
	@FXML
	private Label amountLbl2;
	@FXML
	private Label amountLbl3;
	@FXML
	private Label amountLbl4;
	@FXML
	private Label amountCart1;
	@FXML
	private Label amountCart2;
	@FXML
	private Label amountCart3;
	@FXML
	private Label amountCart4;
	@FXML
	private Button backBtn;
	@FXML
	private Button ProcceedToCartBtn;
	@FXML
	private Button less1;
	@FXML
	private Button more1;
	@FXML
	private Button less2;
	@FXML
	private Button more2;
	@FXML
	private Button less3;
	@FXML
	private Button more3;
	@FXML
	private Button less4;
	@FXML
	private Button more4;
	@FXML
	private TextField ProductIdlbl;
	@FXML
	private TextField amountlbl;
	@FXML
	private TextField TotalPricelbl;
	@FXML
	private Label amountBtnLbl;
	@FXML
	private ImageView image1;
	@FXML
	private ImageView image2;
	@FXML
	private ImageView image3;
	@FXML
	private ImageView image4;
	
	private String itemsString = "";
	
	private ArrayList<Item> cart;
	
	private ArrayList<Integer> amount;
	
	private ArrayList<Integer> available;
	
	private int MachineNumber = -1; 
	
	private int rotation;
	
	private int sale = 100; //add sale to the price calculation
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept(new Message(0, Command.ReadMachines));
		ClientUI.chat.accept(new Message(0,Command.ReadItems));
		if(ChatClient.machineToLoad != -1)
		{
			FindMachineNumber(ChatClient.machineToLoad);
		}
		if(ChatClient.FirstSubscriberOrder)
			this.sale = 80;
		else
			this.sale = updateSale();
		if(ChatClient.cart.isEmpty())
		{
			cart = new ArrayList<Item>();
			amount = new ArrayList<Integer>();
			available = new ArrayList<Integer>();
			int price;
			for(String i : ChatClient.machines.get(MachineNumber).getItems())
			{
				cart.add(new Item(i,"0",getPrice(i)));
				int index = getItemIndex(i);
				available.add(ChatClient.machines.get(MachineNumber).getAmount(index));
				amount.add(0);
			}
		}
		else {
			cart = new ArrayList<Item>();
			amount = new ArrayList<Integer>();
			available = new ArrayList<Integer>();
			int cartAmount;
			for(String i : ChatClient.machines.get(MachineNumber).getItems())
			{
				int indexItem = getItemIndex(i);
				int indexCart = getItemIndexCart(i);
				if(indexCart != -1)
				{
					int boughtAmount = Integer.parseInt(ChatClient.cart.get(indexCart).getAmount());
					amount.add(boughtAmount);
					available.add(ChatClient.machines.get(MachineNumber).getAmount(indexItem)-boughtAmount);
					cart.add(new Item(i,ChatClient.cart.get(indexCart).getAmount(),getPrice(i)));
				}
				else
				{
					amount.add(0);
					available.add(ChatClient.machines.get(MachineNumber).getAmount(indexItem));
					cart.add(new Item(i,"0",getPrice(i)));
				}
			}
			updateTotalPrice();
		}		
		rotation = 0;
		System.out.println(ChatClient.FirstSubscriberOrder);
		System.out.println("Sale : " + sale);
		System.out.println("Available : " + available); //PRINTING
		System.out.println("Amount : " + amount); //PRINTING
		System.out.println("Local Cart : " + this.cart);
		System.out.println("Static Items : " + ChatClient.items);
		LoadItems();
		
	}
	
	public void ProceedCartBtn(ActionEvent event) throws Exception {
		//deal with threshold, send a message -> change the value of a static field in chatClient
		ChatClient.available = available;
		ArrayList<Item> removeThese = new ArrayList<Item>();
		for(Item item : cart)
		{
			if(item.getAmount().equals("0"))
				removeThese.add(item);
		}
		cart.removeAll(removeThese);
		ChatClient.cart = cart;
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Cart.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Cart");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	public void BackBtn(ActionEvent event) throws Exception {
		
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = null;
		switch(ChatClient.role) {
		
		case "ceo":
			root = FXMLLoader.load(getClass().getResource("/gui_client/CEOReports.fxml"));
			break;
		
		case "rgm":
			root = FXMLLoader.load(getClass().getResource("/gui_client/Login.fxml"));
			break;
			
		case "rgw":
			root = FXMLLoader.load(getClass().getResource("/gui_client/Login.fxml"));
			break;
		
		case "stm":
			root = FXMLLoader.load(getClass().getResource("/gui_client/Login.fxml"));
			break;
			
		case "stw":
			root = FXMLLoader.load(getClass().getResource("/gui_client/Login.fxml"));
			break;
			
		case "dlw":
			root = FXMLLoader.load(getClass().getResource("/gui_client/Login.fxml"));
			break;
			
		case "inm":
			root = FXMLLoader.load(getClass().getResource("/gui_client/Login.fxml"));
			break;
			
		case "customer":
			root = FXMLLoader.load(getClass().getResource("/gui_client/UserUI.fxml"));
			break;
			
		default:
			break;
		}
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();	
		
	}
	
	public void NextItems()
	{
		if((rotation+1)*4 >= ChatClient.machines.get(MachineNumber).getItems().size())
			rotation=0;
		else
			rotation += 1;
		LoadItems(); //deal with only showing 2 
	
	}
	
	public void PrevItems()
	{
		if(rotation == 0)
			rotation = findMax();
		else
		{
			if(ChatClient.machines.get(MachineNumber).getItems().size() > 4)
				rotation -= 1;
		}
		LoadItems();
	}
	
	public void LessItem1() //NEW *******************************
	{
		if(amount.get(rotation*4)>0)
		{
			int newAmount = amount.get(rotation*4) - 1; 
			amount.set(rotation*4, newAmount);
			int newAvailable = available.get(rotation*4) + 1;
			available.set(rotation*4,newAvailable);
			amountCart1.setText(String.valueOf(amount.get(rotation*4)));
			System.out.println("Available : " + available); //PRINTING
			System.out.println("Amount : " + amount); //PRINTING
			Item addedToCart = new Item(cart.get(rotation*4).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4).getPrice()));
			cart.set(rotation*4,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
		
	}
	
	public void MoreItem1() //NEW *******************************
	{
		if(available.get(rotation*4)>0)
		{
			int newAmount = amount.get(rotation*4) + 1; 
			amount.set(rotation*4, newAmount);
			int newAvailable = available.get(rotation*4) - 1;
			available.set(rotation*4,newAvailable);
			amountCart1.setText(String.valueOf(amount.get(rotation*4)));
			System.out.println("Available : " + available); //PRINTING
			System.out.println("Amount : " + amount); //PRINTING
			Item addedToCart = new Item(cart.get(rotation*4).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4).getPrice()));
			cart.set(rotation*4,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
	}
	
	public void LessItem2() //NEW *******************************
	{
		if(amount.get(rotation*4+1)>0)
		{
			int newAmount = amount.get(rotation*4+1) - 1; 
			amount.set(rotation*4+1, newAmount);
			int newAvailable = available.get(rotation*4+1) + 1;
			available.set(rotation*4+1,newAvailable);
			amountCart2.setText(String.valueOf(amount.get(rotation*4+1)));
			Item addedToCart = new Item(cart.get(rotation*4+1).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4+1).getPrice()));
			cart.set(rotation*4+1,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
	}
	
	public void MoreItem2() //NEW *******************************
	{
		if(available.get(rotation*4+1)>0)
		{
			int newAmount = amount.get(rotation*4+1) + 1; 
			amount.set(rotation*4+1, newAmount);
			int newAvailable = available.get(rotation*4+1) - 1;
			available.set(rotation*4+1,newAvailable);
			amountCart2.setText(String.valueOf(amount.get(rotation*4+1)));
			Item addedToCart = new Item(cart.get(rotation*4+1).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4+1).getPrice()));
			cart.set(rotation*4+1,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
	}
	
	public void LessItem3() //NEW *******************************
	{
		if(amount.get(rotation*4+2)>0)
		{
			int newAmount = amount.get(rotation*4+2) - 1; //Removed 1 from amount, added 1 to stock, update stock to have +1
			amount.set(rotation*4+2, newAmount);
			int newAvailable = available.get(rotation*4+2) + 1;
			available.set(rotation*4+2,newAvailable);
			amountCart3.setText(String.valueOf(amount.get(rotation*4+2)));
			Item addedToCart = new Item(cart.get(rotation*4+2).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4+2).getPrice()));
			cart.set(rotation*4+2,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
	}
	
	public void MoreItem3() //NEW *******************************
	{
		if(available.get(rotation*4+2)>0)
		{
			int newAmount = amount.get(rotation*4+2) + 1; //Removed 1 from amount, added 1 to stock, update stock to have +1
			amount.set(rotation*4+2, newAmount);
			int newAvailable = available.get(rotation*4+2) - 1;
			available.set(rotation*4+2,newAvailable);
			amountCart3.setText(String.valueOf(amount.get(rotation*4+2)));
			Item addedToCart = new Item(cart.get(rotation*4+2).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4+2).getPrice()));
			cart.set(rotation*4+2,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
	}
	
	public void LessItem4() //NEW *******************************
	{
		if(amount.get(rotation*4+3)>0)
		{
			int newAmount = amount.get(rotation*4+3) - 1; //Removed 1 from amount, added 1 to stock, update stock to have +1
			amount.set(rotation*4+3, newAmount);
			int newAvailable = available.get(rotation*4+3) + 1;
			available.set(rotation*4+3,newAvailable);
			amountCart4.setText(String.valueOf(amount.get(rotation*4+3)));
			Item addedToCart = new Item(cart.get(rotation*4+3).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4+3).getPrice()));
			cart.set(rotation*4+3,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
	}
	
	public void MoreItem4() //NEW *******************************
	{
		if(available.get(rotation*4+3)>0)
		{
			int newAmount = amount.get(rotation*4+3) + 1; //Removed 1 from amount, added 1 to stock, update stock to have +1
			amount.set(rotation*4+3, newAmount);
			int newAvailable = available.get(rotation*4+3) - 1;
			available.set(rotation*4+3,newAvailable);
			amountCart4.setText(String.valueOf(amount.get(rotation*4+3)));
			Item addedToCart = new Item(cart.get(rotation*4+3).getProductID(),String.valueOf(newAmount),(cart.get(rotation*4+3).getPrice()));
			cart.set(rotation*4+3,addedToCart);
			updateTotalPrice();
			LoadItems();
		}
	}
    
    public void LoadItems()
    {
    	CheckAndLoadItem(rotation*4,codeLbl1,"Item",1);
    	CheckAndLoadItem(rotation*4+1,codeLbl2,"Item",2);
    	CheckAndLoadItem(rotation*4+2,codeLbl3,"Item",3);
    	CheckAndLoadItem(rotation*4+3,codeLbl4,"Item",4);
    	CheckAndLoadItem(rotation*4,priceLbl1,"Price",0);
    	CheckAndLoadItem(rotation*4+1,priceLbl2,"Price",0);
    	CheckAndLoadItem(rotation*4+2,priceLbl3,"Price",0);
    	CheckAndLoadItem(rotation*4+3,priceLbl4,"Price",0);
    	CheckAndLoadItem(rotation*4,amountLbl1,"Available",0);
    	CheckAndLoadItem(rotation*4+1,amountLbl2,"Available",0);
    	CheckAndLoadItem(rotation*4+2,amountLbl3,"Available",0);
    	CheckAndLoadItem(rotation*4+3,amountLbl4,"Available",0);
    	CheckAndLoadItem(rotation*4,amountCart1,"Amount",0);
    	CheckAndLoadItem(rotation*4+1,amountCart2,"Amount",0);
    	CheckAndLoadItem(rotation*4+2,amountCart3,"Amount",0);
    	CheckAndLoadItem(rotation*4+3,amountCart4,"Amount",0);
    }
    
    public void CheckAndLoadItem(int num,Label lbl,String str,int index) //num = rotation*4 + i 
    {
    	if(num > ChatClient.machines.get(MachineNumber).getItems().size()-1)
    	{
    		lbl.visibleProperty().set(false);
    		switch(index) {
    		
    		case 1:
    			less1.visibleProperty().set(false);
    			more1.visibleProperty().set(false);
        		image1.visibleProperty().set(false);
    			break;
    		case 2:
    			less2.visibleProperty().set(false);
    			more2.visibleProperty().set(false);
    			image2.visibleProperty().set(false);
    			break;
    		case 3:
    			less3.visibleProperty().set(false);
    			more3.visibleProperty().set(false);
    			image3.visibleProperty().set(false);
    			break;
    		case 4:
    			less4.visibleProperty().set(false);
    			more4.visibleProperty().set(false);
    			image4.visibleProperty().set(false);
    			break;
    		
    		}
    		
    	}
    	else
    	{
    		lbl.visibleProperty().set(true);
    		switch(index) {
    	case 1:
			less1.visibleProperty().set(true);
			more1.visibleProperty().set(true);
    		image1.visibleProperty().set(true);
			break;
		case 2:
			less2.visibleProperty().set(true);
			more2.visibleProperty().set(true);
			image2.visibleProperty().set(true);
			break;
		case 3:
			less3.visibleProperty().set(true);
			more3.visibleProperty().set(true);
			image3.visibleProperty().set(true);
			break;
		case 4:
			less4.visibleProperty().set(true);
			more4.visibleProperty().set(true);
			image4.visibleProperty().set(true);
			break;
    		}
    		switch(str) {
    			case "Item":
    				lbl.setText(ChatClient.machines.get(MachineNumber).getItem(num));
    				break;
    			case "Price":
    				lbl.setText(this.getPrice(ChatClient.machines.get(MachineNumber).getItem(num)) + " NIS");
    				break;
    			case "Available":
    				lbl.setText(String.valueOf(available.get(num))); //set the amount from available
    				break;
    			case "Amount":
    				lbl.setText(String.valueOf(amount.get(num))); //set the amount from available
    				break;
    		}
    	}
    	
    }
    
    public int getPrice(String name)
    {
    	for(Item item : ChatClient.items)
    	{
    		if(item.getProductID().equals(name))
    			return calculatePrice(item.getPrice());
    	}
    	return -1; 
    }
    
    public int findMax()
    {
    	int size = ChatClient.machines.get(MachineNumber).getItems().size();
    	int temp = 0;
    	if(size == 4)
    		return temp;
    	while(temp*4+3<size)
    		temp++;
    	return temp;
    }
    
    public void addItemsFromMachineToCart(String name,String amount) //PLEASE FIX
    {
    	if(!ChatClient.machines.get(MachineNumber).existItem(name))
    		return;
    	else
    	{
    		
    		int size = cart.size();
    			for(int i = 0;i<size;i++)
    			{
    				if(cart.get(i).getProductID().equals(name)) //item already in cart, add new amount to amountCart
    				{
    					int newAmount = Integer.parseInt(cart.get(i).getAmount()) + Integer.parseInt(amount);
    					cart.get(i).setAmount(String.valueOf(newAmount));
    					return;
    				}
    			
    			}
    			cart.add(new Item(name,amount,this.getPrice(name)));
    		
    		
    		
    	}
    }
    
    public void updateTotalPrice() 
    {
    	int sum = 0;
    	for(Item item : cart)
    	{
    		sum += item.getPrice() * Integer.parseInt(item.getAmount());
    	}
    	TotalPricelbl.setText(String.valueOf(sum));
    	return;
    }
    
    public void FindMachineNumber(int id)
    {
    	int size = ChatClient.machines.size();
		for(int i = 0;i<size;i++)
		{
			if(ChatClient.machines.get(i).getMachine_id() == id)
				MachineNumber = i;
		}
    }
    
    public int getItemIndex(String name)
    {
    	int size = ChatClient.machines.get(MachineNumber).getItems().size();
    	for(int i =0;i<size;i++)
    	{
    		if(ChatClient.machines.get(MachineNumber).getItems().get(i).equals(name))
    			return i;
    	}
    	return -1;
    }
    
    public int getItemIndexCart(String name)
    {
    	int size = ChatClient.cart.size();
    	for(int i =0;i<size;i++)
    	{
    		if(ChatClient.cart.get(i).getProductID().equals(name))
    			return i;
    	}
    	return -1;
    }
    
    public int updateSale() 
    {
    	ClientUI.chat.accept(new Message(0,Command.ReadLocations));
    	for(Location location : ChatClient.locations)
    	{
    		if(location.getLocation().equals(ChatClient.locationName))
    				return 100 - location.getSale_value();
    	}
    	return 100;
    }
    
    public int calculatePrice(int normalPrice)
    {
    	double sale = this.sale;
    	sale = sale/100;
    	double normalPriceDouble = normalPrice;
    	double newPrice = normalPriceDouble * sale;
    	int newPriceInt = (int)(newPrice);
    	return newPriceInt;
    }
}


