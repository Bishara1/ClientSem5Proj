package gui_client;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
		primaryStage.setTitle("EKRUT");
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
	
}
