package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Request;

public class WorkerUIController implements Initializable{
	
	@FXML
	private Label titletxt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.titletxt.setText("Welcome " + ChatClient.Fname);
	}
	
	public void BackBtn(ActionEvent event) throws Exception
	{
    	ClientUI.chat.accept(new Message(ChatClient.ID, Command.Disconnect));
		nextWindow(event, "/gui_client_windows/LoginEkrut.fxml", "Login Ekrut"); 
	}
	
	public void WorkWindowBtn(ActionEvent event) throws Exception
	{
		//check if password is correct/ if client exists and then proceed;
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Parent root = null;
		
		System.out.println(ChatClient.role);
		switch(ChatClient.role) {
		
		case "ceo":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/CEOReports.fxml"));
			break;
		
		case "rgn":
			ChatClient.locationName = "North";
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/RegionalManager.fxml"));
			break;
			
		case "rgs":
			ChatClient.locationName = "South";
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/RegionalManager.fxml"));
			break;
			
		case "rgu":
			ChatClient.locationName = "UAE";
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/RegionalManager.fxml"));
			break;
		
		case "stm":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/UpdateStock.fxml"));
			break;
			
		case "mkm":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/DiscountLocation.fxml"));
			break;
			
		case "stw":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Login.fxml"));
			break;
			
		case "dlw":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/ViewOrdersDeliveryOperator.fxml"));
			break;
			
		case "dlo":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/ViewOrdersDeliveryOperator.fxml"));
			break;
			
		case "inm":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Login.fxml"));
			break;
			
		case "customer":
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/UserUI.fxml"));
			break;
			
		case "csw": //costumer service worker
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/CustomerServiceWorker.fxml"));
			break;
			
		case "svw": //costumer service worker
			root = FXMLLoader.load(getClass().getResource("/gui_client_windows/SubscriberRequest.fxml"));
			break;
		
		default:
			break;
		}
		
		Scene scene = new Scene(root);
		//Parent root2 = FXMLLoader.load(getClass().getResource("/gui_client_windows/StartOrder.fxml"));
		//scene.getStylesheets().add(getClass().getResource("/gui/.css").toExternalForm());
		primaryStage.setTitle(ChatClient.Fname + " Page");
		primaryStage.setScene(scene);
		
		primaryStage.show();	
	}
	
	public void CreateRemoteOrderBtn(ActionEvent event) throws Exception
	{
		nextWindow(event, "/gui_client_windows/ShipmentMethod.fxml", "Shipment Method");
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
