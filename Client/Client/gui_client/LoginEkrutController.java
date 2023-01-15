package gui_client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginEkrutController {
	
	@FXML
	private Button accountlogin;
	@FXML
	private Button ektBtn;
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/LoginEkrut.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Ekrut");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	public void AccountLoginBtn(ActionEvent event) throws Exception { 
		nextWindow(event, "/gui_client_windows/UserLogin.fxml", "User Login");
	}
	
	public void EKTBtn(ActionEvent event) throws Exception{
		nextWindow(event, "/gui_client_windows/EKTLogin.fxml", "EKT Login");
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client_windows/OLOKPage.fxml", "OL OK Page");
	}
	
	private void nextWindow(ActionEvent event, String window_location, String title) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource(window_location));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
}