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

public class StockTableController {

	@FXML
	private Button showStockBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button backBtn;
	
	@FXML
	private TextField machineCode;
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UpdateStock.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Update Stock");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
}
