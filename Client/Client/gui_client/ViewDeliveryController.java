package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewDeliveryController {
	@FXML
	private Button viewOrdersBtn;
	@FXML
	private Button logoutBtn;
	
	public void ViewOrdersBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ViewOrdersDeliveryOperator.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Orders");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public void LogoutBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Login.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Delivery Operator");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
}
