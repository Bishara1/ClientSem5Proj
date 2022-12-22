package gui_client;

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

public class UpdateThresholdWindowController {
	
	@FXML
	private Button backBtn;
	@FXML
	private Button updateBtn;
	
	@FXML
	private TextField machineCode;
	@FXML
	private TextField thresholdValue;
	
	public void UpdateBtn(ActionEvent event) throws Exception{
		Alert alert = new Alert(AlertType.ERROR,"Bishara Fix Me !!!!!",ButtonType.OK);
		alert.showAndWait();
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/RegionalManager.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Regional Manager");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}

}
