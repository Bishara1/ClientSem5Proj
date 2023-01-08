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
import javafx.scene.control.Label;
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
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Cart.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Cart");
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	
	public void PurchaseBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Receipt.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Receipt");
		primaryStage.setScene(scene);		
		primaryStage.show();	
}
	public void Purchase(ActionEvent event) throws Exception {
		if(creditCardtxt.getText().isEmpty() || idtxt.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR,"Please enter credit card details and ID",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			((Node)event.getSource()).getScene().getWindow().hide();
			ChatClient.ID = Integer.parseInt(this.idtxt.getText());
			Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Receipt.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			primaryStage.setTitle("EKRUT");
			primaryStage.setScene(scene);		
			primaryStage.show();
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
	
}
