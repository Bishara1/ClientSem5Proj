package gui_client;

import client.ChatClient;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Subscriber;

public class CustomerServiceWorkerController {

	@FXML
	private TextField FirstNametxt;
	@FXML
	private TextField LastNametxt;
	@FXML
	private TextField Idtxt;
	@FXML
	private TextField PhoneNumbertxt;
	@FXML
	private TextField Emailtxt;
	@FXML
	private TextField CreditCardtxt;
	@FXML
	private TextField Usernametxt;
	@FXML
	private TextField Passwordtxt;
	@FXML
	private TextField Roletxt;

	@FXML
	private Button addBtn;
	@FXML
	private Button logoutBtn;

	Message messageToServer = new Message(null, null);
	Message ReadUsersMessage = new Message(null, null);

	public void Add(ActionEvent event) throws Exception
	{
		boolean flag = true;
		String newUser = FirstNametxt.getText() + " " + LastNametxt.getText() + " " + Idtxt.getText() + " " 
				+ PhoneNumbertxt.getText() + " " + Emailtxt.getText() + " " + CreditCardtxt.getText() + " -1 " + 
				Usernametxt.getText() + " " + Passwordtxt.getText() + " " + Roletxt.getText();
		ReadUsersMessage.setCommand(Command.ReadUsers);
		ReadUsersMessage.setContent(0);
		ClientUI.chat.accept(ReadUsersMessage);
		
		for(Subscriber s : ChatClient.subscribers)
		{
			if ((s.getId()== Integer.parseInt(Idtxt.getText()) || s.getUserName().equals(Usernametxt.getText()) ||
					s.getPhoneNum().equals(PhoneNumbertxt.getText())))
			{
				Alert alert = new Alert(AlertType.ERROR,"User already exists!",ButtonType.OK);
				alert.showAndWait();
				flag = false;
			}
		}
		if (flag)
		{
			messageToServer.setCommand(Command.InsertUser);
			messageToServer.setContent(newUser);
			ClientUI.chat.accept(messageToServer);
			Alert alert = new Alert(AlertType.CONFIRMATION,"User successfully added",ButtonType.OK);
			alert.showAndWait();}
	}

	public void Logout(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UserLogin.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("User Login");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
