package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PurchaseController {

	@FXML
	private TextField creditCardtxt;
	@FXML
	private TextField idtxt;
	
	@FXML
	private Button backBtn;
	@FXML
	private Button purchaseBtn;
	
	
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Cart.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Cart");
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
}
