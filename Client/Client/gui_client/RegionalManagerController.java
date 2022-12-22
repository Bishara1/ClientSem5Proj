package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegionalManagerController {

	@FXML
	private Button logOutBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button registrationBtn;
	
	
	
	public void LogoutBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Ekrut");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	
}
