package gui_client;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Item;

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
	private Button backBtn;
	@FXML
	private Button AddtoCartBtn;
	@FXML
	private Button ProcceedToCartBtn;
	@FXML
	private TextField ProductIdlbl;
	@FXML
	private TextField amountlbl;
	@FXML
	private TextField TotalPricelbl;
	@FXML
	private Label amountBtnLbl; //NEW **************************************
	
	private ArrayList<Item> cart;
	private boolean previousCart = false;
	
	private int amountByBtn = 0; //NEW *************************************
	
	private int MachineNumber = 1; //placeholder for the actual machine number
	
	private int rotation;
	//ArrayList<Machine> machines are saved in ChatClient
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept(new Message(MachineNumber, Command.ReadMachines));
		ClientUI.chat.accept(new Message(0,Command.ReadItems));
		if(ChatClient.cart.equals(null))
			cart = new ArrayList<Item>();
		else {
			cart = new ArrayList<Item>();
			cart = ChatClient.cart;
			updateTotalPrice();
			previousCart = true;
		}
		rotation = 0;
		LoadItems();
		
	}
	
	public void ProceedCartBtn(ActionEvent event) throws Exception {
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
//<<<<<<< HEAD
//		((Node)event.getSource()).getScene().getWindow().hide();
//		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UserUI.fxml"));
//		Stage primaryStage = new Stage();
//		Scene scene = new Scene(root);
//		//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
//		primaryStage.setTitle("User UI");
//		primaryStage.setScene(scene);		
//		primaryStage.show();
//=======
//		
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
		
//>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
	}
	
	public void NextItems()
	{
		if((rotation+1)*4 >= ChatClient.machines.get(MachineNumber-1).getItems().size())
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
			rotation -= 1;
		}
		LoadItems();
	}
	
	public void LessItem() //NEW *******************************
	{
		if(!ProductIdlbl.getText().equals(""))
		{
			if(amountByBtn > 0)
			{
				amountByBtn--;
				amountBtnLbl.setText(String.valueOf(amountByBtn));
			}
		}
	}
	
	public void MoreItem() //NEW *******************************
	{
		if(!ProductIdlbl.getText().equals(""))
		{
			amountByBtn++;
			amountBtnLbl.setText(String.valueOf(amountByBtn));
		}
	}
	
    public void AddToCartBtn() {
		if(this.ProductIdlbl.getText().equals("") || amountByBtn == 0) //this.amountlbl.getText().equals("") ***********
		{
			Alert alert = new Alert(AlertType.ERROR,"Must enter product name and amount!",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			if(ChatClient.machines.get(MachineNumber-1).existItem(ProductIdlbl.getText()))
			{
					
				addItemFromMachineToCart(ProductIdlbl.getText(),String.valueOf(amountByBtn) ); //amountlbl.getText()
				updateTotalPrice();
				Alert alert = new Alert(AlertType.CONFIRMATION,"Item has been added to cart!",ButtonType.OK);
				alert.showAndWait();
				amountByBtn = 0; //new 
				ProductIdlbl.setText("");
				amountBtnLbl.setText("0");
			} 
			else
			{
				Alert alert = new Alert(AlertType.ERROR,"The item you entered doesn't exist in the machine!",ButtonType.OK);
				alert.showAndWait();
			}
		}
	}
    
    public void LoadItems()
    {
    	CheckAndLoadItem(rotation*4,codeLbl1,"Item");
    	CheckAndLoadItem(rotation*4+1,codeLbl2,"Item");
    	CheckAndLoadItem(rotation*4+2,codeLbl3,"Item");
    	CheckAndLoadItem(rotation*4+3,codeLbl4,"Item");
    	CheckAndLoadItem(rotation*4,priceLbl1,"Price");
    	CheckAndLoadItem(rotation*4+1,priceLbl2,"Price");
    	CheckAndLoadItem(rotation*4+2,priceLbl3,"Price");
    	CheckAndLoadItem(rotation*4+3,priceLbl4,"Price");
    	CheckAndLoadItem(rotation*4,amountLbl1,"Amount");
    	CheckAndLoadItem(rotation*4+1,amountLbl2,"Amount");
    	CheckAndLoadItem(rotation*4+2,amountLbl3,"Amount");
    	CheckAndLoadItem(rotation*4+3,amountLbl4,"Amount");
    }
    
    public void CheckAndLoadItem(int num,Label lbl,String str) //num = rotation*4 + i 
    {
    	if(num > ChatClient.machines.get(MachineNumber-1).getItems().size()-1)
    		lbl.setText(" ");
    	else
    	{
    		switch(str) {
    			case "Item":
    				lbl.setText(ChatClient.machines.get(MachineNumber-1).getItem(num));
    				break;
    			case "Price":
    				lbl.setText(this.getPrice(ChatClient.machines.get(MachineNumber-1).getItem(num)) + " NIS");
    				break;
    			case "Amount":
    				lbl.setText(String.valueOf(ChatClient.machines.get(MachineNumber-1).getAmount(num)));
    				break;
    		}
    	}
    	
    }
    
    public int getPrice(String name)
    {
    	for(Item item : ChatClient.items)
    	{
    		if(item.getProductID().equals(name))
    			return item.getPrice();
    	}
    	return -1; 
    }
    
    public int findMax()
    {
    	int size = ChatClient.machines.get(MachineNumber-1).getItems().size();
    	int temp = 0;
    	while(temp*4+3<size)
    		temp++;
    	return temp;
    }
    
    public void addItemFromMachineToCart(String name,String amount)
    {
    	if(!ChatClient.machines.get(MachineNumber-1).existItem(name))
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
}


