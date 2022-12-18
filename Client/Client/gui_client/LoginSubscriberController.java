package gui_client;

import java.awt.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginSubscriberController {

	@FXML
	private TextField Usernametxt;
	@FXML
	private TextField Passwordtxt;	
	@FXML
	private Label Usernamelbl;
	@FXML
	private Label Passwordlbl;
	@FXML
	private Button Loginbtn;
	@FXML
	private Button Backbtn;
	
	
	
	public void loginBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/StartOrder.fxml"));
		
		Scene scene = new Scene(root);
		//Parent root2 = FXMLLoader.load(getClass().getResource("/gui_client/StartOrder.fxml"));
		//scene.getStylesheets().add(getClass().getResource("/gui/.css").toExternalForm());
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);
		
		primaryStage.show();	
	}
}
