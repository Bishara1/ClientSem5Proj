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

public class LoginController {
//	public static ClientController chat; //only one instance
	ChatClient client;
	
	@FXML
	private TextField hostIptxt;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Page");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	public void ExitBtn() {
		System.exit(0);	
	}
	
	public void LoginBtn(ActionEvent event) throws Exception {
		/*
		 * ConnectNewClient(); Message msg = new Message("", Command.Connect);
		 * //connects client to server ClientUI.chat.accept(msg);
		 */
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ConnectNewClient(); Message msg = new Message("", Command.Connect);
		
//		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/LoginEkrut.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/OLOKPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("OL OK Page");
		primaryStage.setScene(scene);
		
		primaryStage.show();	
	}
	
	public void ConnectNewClient() { //added this method to LoginSubscriber
		// the server ip is hardcoded
		ClientUI.chat = new ClientController(hostIptxt.getText(), 5555);  // new client connected
		///ClientUI.chat.accept("login"); // send to server that a client is connected
	}
}