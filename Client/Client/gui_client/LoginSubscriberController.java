package gui_client;



import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
		String username = Usernametxt.getText();
		String password = Passwordtxt.getText();
		
		if(username.equals("") || password.equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR,"Must enter username and password",ButtonType.OK);
			alert.showAndWait();
		}
		else {
			ConnectNewClient();
		Message msg = new Message(username, Command.Connect); //connects client to server
		ClientUI.chat.accept(msg);
		
		//check if password is correct/ if client exists and then proceed;
		if(ChatClient.password.equals(password))
		{
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui_client/SubscribersViewer.fxml"));
			
			Scene scene = new Scene(root);
			//Parent root2 = FXMLLoader.load(getClass().getResource("/gui_client/StartOrder.fxml"));
			//scene.getStylesheets().add(getClass().getResource("/gui/.css").toExternalForm());
			primaryStage.setTitle("Subscriber Viewer");
			primaryStage.setScene(scene);
			
			primaryStage.show();	
		}
		else
		{
			if(ChatClient.password.equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR,"Username isn't in db",ButtonType.OK);
				alert.showAndWait();
			}
			else 
				if(!ChatClient.password.equals(password))
			{
				Alert alert = new Alert(AlertType.ERROR,"Incorrect password",ButtonType.OK);
				alert.showAndWait();
			}
		}
		}
		
			
	}
	
	public void ConnectNewClient() { //MUST ADD A DYNAMIC IP GETTER
		// the server ip is hardcoded
		ClientUI.chat = new ClientController("localhost", 5555);  // new client connected
		///ClientUI.chat.accept("login"); // send to server that a client is connected
	}
	
	public void backBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
	//	scene.getStylesheets().add(getClass().getResource("/gui/LoginEkrut.css").toExternalForm());
		primaryStage.setTitle("Login Ekrut");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
}
