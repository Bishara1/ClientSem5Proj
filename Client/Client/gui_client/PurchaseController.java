package gui_client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Item;

public class PurchaseController implements Initializable{

	@FXML
	private TextField creditCardtxt;
	@FXML
	private Button backBtn;
	@FXML
	private Button purchaseBtn;
	@FXML
	private TextField priceLbl;
	
	private int Threshold = 0;
	
	private int MachineNumber = -1;
	
	
	
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client_windows/Cart.fxml", "Cart");	
	}
	
	
	public void Purchase(ActionEvent event) throws Exception {
		if(creditCardtxt.getText().length() != 16)
		{
			if(creditCardtxt.getText().isEmpty())
			{
				Message msg = new Message(null,null);
				msg.setContent(ChatClient.ID);
				msg.setCommand(Command.ReadUserVisa);
				ClientUI.chat.accept(msg);
				InsertOrder(event);
				nextWindow(event, "/gui_client_windows/Receipt.fxml", "Receipt");
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR,"Please enter valid credit card details, alternatively leave the field blank if you wish to use the credit card you signed up with",ButtonType.OK);
				alert.showAndWait();
			}
		}
		else
		{
			try {
				Integer.parseInt(creditCardtxt.getText());
				ChatClient.creditcard = creditCardtxt.getText();
				InsertOrder(event);
				nextWindow(event, "/gui_client_windows/Receipt.fxml", "Receipt");
			}catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR,"Please enter valid credit card details, alternatively leave the field blank if you wish to use the credit card you signed up with",ButtonType.OK);
				alert.showAndWait();
			}
			
		}
	}

	 public void updateTotalPrice()
	 {
	    	int sum = 0;
	    	for(Item item : ChatClient.cart)
	    	{
	    		sum += item.getPrice() * Integer.parseInt(item.getAmount());
	    	}
	    	priceLbl.setText(String.valueOf(sum));
	    	return;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FindMachineNumber(ChatClient.machineToLoad);
		updateTotalPrice();
		
	}
	
	private void nextWindow(ActionEvent event, String window_location, String title) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource(window_location));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	public void InsertOrder(ActionEvent event) throws IOException
	{
		Alert alert = new Alert(AlertType.WARNING,"Are you sure you want to continue?",ButtonType.NO,ButtonType.YES);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.YES)
		{
			int size = ChatClient.availableItems.size();
			ArrayList<Integer> itemAmount = ChatClient.available;
			String itemsToFill = "";
			for(int i =0;i<size;i++)
			{
				if(itemAmount.get(i) <= Threshold)
				{
					if(i!= 0)
						itemsToFill += ",";
					itemsToFill += ChatClient.availableItems.get(i);
				}
			}
			if(!itemsToFill.isEmpty()) //send request to fill machine by threshold
			{
				ClientUI.chat.accept(new Message(ChatClient.machineToLoad,Command.ReadStockRequests));
				if(ChatClient.stockRequests.isEmpty())
				{
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(ChatClient.machineToLoad));
					ClientUI.chat.accept(new Message(data,Command.InsertStockRequest));
				}
				
			}
			((Node)event.getSource()).getScene().getWindow().hide();
			Alert alert1 = new Alert(AlertType.CONFIRMATION,"Order added!",ButtonType.OK);
			alert1.showAndWait();
			ArrayList<String> data = new ArrayList<String>();
			data.add(String.valueOf(ChatClient.ID));
			data.add(ChatClient.locationName);
			String items = "";
			int price = 0;
			for(int i =0;i<ChatClient.cart.size();i++)
			{
				items += ChatClient.cart.get(i).getProductID();
				items += ",";
				items += ChatClient.cart.get(i).getAmount();
				items += ".";
				price += ChatClient.cart.get(i).getPrice()* Integer.parseInt(ChatClient.cart.get(i).getAmount());
			}
			data.add(items);
			data.add(ChatClient.creditcard);
			data.add(String.valueOf(price));
			data.add(ChatClient.supplyMethod);
			data.add(String.valueOf(ChatClient.machineToLoad));
			String newStock = CreateNewStock(ChatClient.available,ChatClient.availableItems);
			data.add(newStock);
			int totalInventory = CreateNewInventory(newStock);
			data.add(String.valueOf(totalInventory));
			Message msg = new Message(data,Command.InsertOrder);
			ClientUI.chat.accept(msg);
			if(ChatClient.supplyMethod == "Delivery")
			{
				data = new ArrayList<String>();
				ClientUI.chat.accept(new Message(0,Command.ReadOrders));
				size = ChatClient.orders.size();
				data.add(String.valueOf(ChatClient.orders.get(size-1).getOrder_num()));
				data.add(String.valueOf(ChatClient.ID));
				data.add(String.valueOf(calculateTotalPrice()));
				data.add(ChatClient.deliveryLocation);
				data.add(ChatClient.address);
				ClientUI.chat.accept(new Message(data,Command.InsertDelivery));
			}
			if(ChatClient.FirstSubscriberOrder == true)
			{
				msg = new Message(null,null);
				ArrayList<String> param = new ArrayList<String>();
				param.add("usersFirstCart");
				param.add(String.valueOf(ChatClient.ID));
				param.add("0");
				msg.setCommand(Command.UpdateUserFirstCart);
				msg.setContent(param);
				ClientUI.chat.accept(msg);
				ChatClient.FirstSubscriberOrder = false;
			}
			//Disconnect
		}
	}
	
	public int calculateTotalPrice()
	 {
	    	int sum = 0;
	    	for(Item item : ChatClient.cart)
	    	{
	    		sum += item.getPrice() * Integer.parseInt(item.getAmount());
	    	}
	    	return sum;
	 }
	
	public String CreateNewStock(ArrayList<Integer> available,ArrayList<String> availableItems)
	{
		
		int i,j =0;
		int size = ChatClient.machines.get(MachineNumber).getItems().size();
		String newStock = "";
		for(i=0;i<size;i++)
		{
			if(ChatClient.machines.get(MachineNumber).getItems().get(i).equals(availableItems.get(j)))
			{
				newStock += String.valueOf(available.get(j));
				if(i != size-1)
					newStock += ",";
				j++;
			}
			else
			{
				newStock += "0";
				if(i != size-1)
					newStock += ",";
			}
		}
		return newStock;
	}
	
	public int CreateNewInventory(String newStock)
	{
		String[] items = newStock.split(",");
		int inv = 0;
		for(String item : items)
		{
			inv += Integer.parseInt(item);
		}
		return inv;
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
	
}
