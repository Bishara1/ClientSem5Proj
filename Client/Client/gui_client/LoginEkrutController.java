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

/**
 * This class lets the user decide sign in method
 */
public class LoginEkrutController {
	
	@FXML
	private Button accountlogin;
	@FXML
	private Button ektBtn;
	
	/**
	 * This method starts the "LoginEkut" fxml window
	 * @param primaryStage 
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/LoginEkrut.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Ekrut");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	/**
	 * This method calls function nextWindow to go to requested path
	 * @param event
	 * @throws Exception
	 */
	public void AccountLoginBtn(ActionEvent event) throws Exception { 
		nextWindow(event, "/gui_client_windows/UserLogin.fxml", "User Login");
	}
	
	/**
	 * This method calls function nextWindow to go to requested path
	 * @param event
	 * @throws Exception
	 */
	public void EKTBtn(ActionEvent event) throws Exception{
		nextWindow(event, "/gui_client_windows/EKTLogin.fxml", "EKT Login");
	}
	
	/**
	 * This method calls function nextWindow to go to requested path
	 * @param event
	 * @throws Exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client_windows/OLOKPage.fxml", "OL OK Page");
	}
	
	/**
	 * This method hides the currently open window and shows the desired window
	 * @param event
	 * @param path
	 * @param title
	 * @throws Exception
	 */
	private void nextWindow(ActionEvent event, String path, String title) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource(path));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
}