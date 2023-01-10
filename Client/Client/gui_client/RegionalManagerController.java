package gui_client;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RegionalManagerController implements Initializable {

	@FXML
	private Button logOutBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button registrationBtn;
    @FXML
    private Label welcomelbl;
	
	public void LogoutBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Ekrut");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		welcomelbl.setText("Weclome " + ChatClient.Fname);
	}
	
}
