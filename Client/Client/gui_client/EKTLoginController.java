package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EKTLoginController implements Initializable {
	
	public static String u = "";
	
	public OLOKPageController p;
	
	@FXML
	private ComboBox<String> cmbUser;
	
	@FXML
	private Button backBtn;
	
	@FXML
	private Button loginBtn;
	
	ObservableList<String> users;
	
	Message messageToServer = new Message(null, null);
	@FXML
	public void Selectuser(ActionEvent event) {
		u = cmbUser.getSelectionModel().getSelectedItem().toString();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		u = "";
		setUserComboBox();
	}
	
	
	public void setUserComboBox() {	
    	ArrayList<String> u = new ArrayList<String>(Arrays.asList("128763524","123456789","314887546"));
		users = FXCollections.observableArrayList(u);
		cmbUser.getItems().clear();
		cmbUser.setItems(users);
		
	}
	
	public void login(ActionEvent event) throws Exception
	{
		if(u.isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR,"You must enter an ID",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			Message msg = new Message(u,Command.EKTConnect);
			ConnectNewClient();
			ClientUI.chat.accept(msg);
		
			if(p.type.equals("OK"))
			{
				if(ChatClient.ID != -1 && ChatClient.isSubscriber == true) {
					nextWindow(event, "/gui_client_windows/UserUI.fxml", "USER UI");
				}
				else
				{
					Alert alert = new Alert(AlertType.ERROR,"The ID you entered can't use EKT",ButtonType.OK);
					alert.showAndWait();
				}
			}
			else
			{
				if(ChatClient.role.equals("customer"))
				{
					if(ChatClient.isSubscriber == true) {
						nextWindow(event,"/gui_client_windows/UserUI.fxml","USER UI");
					    return;
					}
					else {
						Alert alert = new Alert(AlertType.ERROR,"The ID you entered can't use EKT",ButtonType.OK);
						alert.showAndWait();
					}
				}
				else
				{
					nextWindow(event, "/gui_client_windows/WorkerUI.fxml", "WORKER UI");
				}
			}
		}
	}
	public void ConnectNewClient() { //added this method to LoginSubscriber
		// the server ip is hardcoded
		//ClientUI.chat = new ClientController("localhost", 5555);  // new client connected
		///ClientUI.chat.accept("login"); // send to server that a client is connected
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/LoginEkrut.fxml","Login EKRUT");
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