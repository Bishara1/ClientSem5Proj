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
	private Button Accountlogin;
	@FXML
	private Button ektBtn;
	
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());  css
		primaryStage.setTitle("Login Ekrut");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
	
	public void AccountLoginBtn(ActionEvent event) throws Exception { 
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UserLogin.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Login Subscriber");
		primaryStage.setScene(scene);
		
		primaryStage.show();	
	}
	
	public void EKTBtn(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
//		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/EKTLogin.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/StockTable.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("EKT Login");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public void ExitBtn() {
		System.exit(0);
	}
}
