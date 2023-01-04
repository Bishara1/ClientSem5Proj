package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import logic.*;

public class ReceiptController {
	@FXML
	private TableView<Item> receiptTable;
	@FXML
	private TableColumn<Item,String> productIdCol;
	@FXML
	private TableColumn<Item,String> amountCol;
	@FXML
	private TableColumn<Item,String> priceCol;

	@FXML
	private Button okBtn;
	@FXML
	private Button backBtn;
	
	
	public void OKBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Purchase.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Cart");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
}
