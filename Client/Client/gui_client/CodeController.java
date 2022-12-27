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

public class CodeController {

	@FXML
	private TextField codetxt;
	@FXML
	private Button backBtn;
	@FXML
	private Button getBtn;
	
	public void GetBtn(ActionEvent event) throws Exception {
		String code = codetxt.getText();
		
		if (code.equals("")) {
			Alert alert = new Alert(AlertType.ERROR,"Empty Field !",ButtonType.OK);
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR,"What the fuck happens now ?",ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
//		((Node)event.getSource()).getScene().getWindow().hide();
//		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/?.fxml"));
//		Stage primaryStage = new Stage();
//		Scene scene = new Scene(root);
//		primaryStage.setTitle("?");
//		primaryStage.setScene(scene);		
//		primaryStage.show();	
		Alert alert = new Alert(AlertType.ERROR,"BACK TO WHERE ?!?!?!!",ButtonType.OK);
		alert.showAndWait();
	}
}
