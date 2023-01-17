package gui_client;


import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class is the controller for the first window, the main window.
 */
public class LoginController {
	ChatClient client;
	
	@FXML
	private TextField hostIptxt;

	/**
	 * This method starts the fxml file "Login"
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	/**
	 * This method closes all windows and exists system.
	 */
	public void ExitBtn() {
		System.exit(0);	
	}
	
	/**
	 * This method hides "Login" fxml window, connects to data base and shows "OLOKPage" fxml window.
	 * @param event
	 * @throws Exception
	 */
	public void LoginBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ConnectNewClient(); Message msg = new Message("", Command.Connect);
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/OLOKPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("OL OK Page");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
	
	/**
	 * This method connects new client.
	 */
	public void ConnectNewClient() {
		ClientUI.chat = new ClientController(hostIptxt.getText(), 5555);  // new client connected
	}
}