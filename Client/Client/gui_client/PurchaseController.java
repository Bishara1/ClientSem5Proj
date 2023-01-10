package gui_client;

import java.net.URL;

import java.util.ResourceBundle;

import client.ChatClient;
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
	private TextField idtxt;
	
	@FXML
	private Button backBtn;
	@FXML
	private Button purchaseBtn;
	@FXML
	private TextField priceLbl;
	
	
	
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client/Cart.fxml", "Cart");	
	}
	
	public void PurchaseBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client/Receipt.fxml", "Receipt");
	}
	
	public void Purchase(ActionEvent event) throws Exception {
		if(creditCardtxt.getText().isEmpty() || idtxt.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR,"Please enter credit card details and ID",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			nextWindow(event, "/gui_client/Receipt.fxml", "Receipt");
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
	
}
