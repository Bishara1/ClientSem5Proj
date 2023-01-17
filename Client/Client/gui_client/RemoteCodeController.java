package gui_client;



import java.io.IOException;
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
import javafx.scene.control.TextField;
import logic.Item;
import logic.Order;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RemoteCodeController implements Initializable{

	@FXML
	private Button backbtn;
	@FXML
	private Button OrderSummaryBtn;
	@FXML
	private TextField Codetxt;
	
	private boolean flag = true;
	
	public void ProceedSummary(ActionEvent event) {
		flag = true;
		if(Codetxt.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR,"Code field can't be empty",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			try {
				Integer.parseInt(Codetxt.getText());
				Message msg = new Message(null,null);
				msg.setCommand(Command.ReadOrders);
				msg.setContent(Integer.parseInt(Codetxt.getText()));
				ClientUI.chat.accept(msg);
			}catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR,"Code must be a number",ButtonType.OK);
				alert.showAndWait();
				flag = false;
			}
			
			if(flag == true)
			{
				try {
					Order order = ChatClient.orders.get(0);
					if(ChatClient.ID == order.getCustomer_id() && (order.getSupply_method().equals("Self pickup") && !(order.getOrder_status().equals("Picked up") && (order.getMachine_id() == ChatClient.machineToLoad))))
					{
						AddItemsToCart(order.getItems_in_order());
						Message supplyMethod = new Message(null,null);
						ArrayList<String> arr = new ArrayList<String>();
						arr.add("ordersupplymethod");
						arr.add(Codetxt.getText());
						arr.add("Picked up");
						supplyMethod.setCommand(Command.UpdateOrderSupplyMethod);
						supplyMethod.setContent(arr);
						ClientUI.chat.accept(supplyMethod);
						((Node) event.getSource()).getScene().getWindow().hide();
						Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Receipt.fxml"));
						Stage primaryStage = new Stage();
						Scene scene = new Scene(root);
						primaryStage.setTitle("Receipt");
						primaryStage.setScene(scene);
						primaryStage.show();
						
					}
					else
					{
						Alert alert = new Alert(AlertType.ERROR,"Can't retrieve order",ButtonType.OK);
						alert.showAndWait();
					}
					//cart = order.items_in_order
					//Add tests to check if the ID in the order is the same ID of the current user, and if the Supply Method is not "Picked up"
					//Update Supply Method to "Picked Up"
					//and go to receipt
				}catch(Exception e) {
					Alert alert = new Alert(AlertType.ERROR,"Order doesn't exist",ButtonType.OK);
					alert.showAndWait();
				}
			}
		}
	}
	
	public void AddItemsToCart(String items)
	{
		ChatClient.cart = new ArrayList<Item>();
		String[] itemsArr = items.split("[.]");
		String[] nameAmount;
		for(String item : itemsArr)
		{
			nameAmount = item.split(",");
			ChatClient.cart.add(new Item(nameAmount[0],nameAmount[1],GetPrice(nameAmount[0])));
		}
	}
	
	public void BackBtn(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/UserUI.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Message msg = new Message(null,null);
		msg.setCommand(Command.ReadItems);
		msg.setContent(0);
		ClientUI.chat.accept(msg);
		msg = new Message(null,null);
		msg.setCommand(Command.ReadMachines);
		msg.setContent(0);
		ClientUI.chat.accept(msg);
	}
	
	public int GetPrice(String name)
	{
		for(Item item : ChatClient.items)
		{
			if(item.getProductID().equals(name))
				return item.getPrice();
		}
		return -1;
	}
}
