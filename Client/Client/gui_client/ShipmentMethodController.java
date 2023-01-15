package gui_client;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ShipmentMethodController {
	
	@FXML
	private Button backBtn;
	@FXML
	private Button immediatePickupBtn;
	@FXML
	private Button deliveryBtn;
	
	
	public void ImmediatePickupBtn(ActionEvent event) throws Exception {
		ChatClient.supplyMethod = "Self pickup";
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/RemoteLocation.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Receipt");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	public void DeliveryBtn(ActionEvent event) throws Exception {
		ChatClient.supplyMethod = "Delivery";
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/ShipmentAddress.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Insert your address for shipment request");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Cart.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Cart");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}

}
