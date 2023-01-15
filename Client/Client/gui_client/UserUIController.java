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

public class UserUIController implements Initializable {
	@FXML
	private Button subreqbtn;
	@FXML
	private Button createorderbtn;
	@FXML
	private Button createremotebtn;
	@FXML
	private Button backbtn;
	@FXML
	private Button remotecodebtn;
	@FXML
	private Label titletxt;
	
	public OLOKPageController p;
	
	public void SubReqBtn(ActionEvent event) throws Exception {
		ArrayList<String> request;
		int flag =0;
		//Add insert to database that a customer wants to become a subscriber
		Message messageToServer = new Message(null, null);
		messageToServer.setCommand(Command.ReadRequests);
		messageToServer.setContent(0);
		ClientUI.chat.accept(messageToServer);
		
		if(ChatClient.userRequest.contains(null))
		{
			 request = new ArrayList<String>(Arrays.asList("",String.valueOf(ChatClient.ID),"subscriberRequest","Pending"));
			messageToServer.setCommand(Command.InsertRequest);
			messageToServer.setContent(request);
			ClientUI.chat.accept(messageToServer);
		}
		else
		{
		    for(Request r : ChatClient.userRequest)
		    {
		           
		        	if(r.getCustomer_id()==ChatClient.ID)
		        	{
		        		flag=1;
		        	}
		    }
		    if(flag==0)
		    {
		    	request = new ArrayList<String>(Arrays.asList("",String.valueOf(ChatClient.ID),"subscriberRequest","Pending"));
    			messageToServer.setCommand(Command.InsertRequest);
    			messageToServer.setContent(request);
    			ClientUI.chat.accept(messageToServer);
    			Alert alert = new Alert(AlertType.CONFIRMATION,"Your request has been inserted",ButtonType.OK);
				alert.showAndWait();
		    }
		    else
		    {
		    	Alert alert = new Alert(AlertType.ERROR,"You already have a request",ButtonType.OK);
				alert.showAndWait();
		    }
		}
	}
	
	public void CreateOrderBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client_windows/ekrutOrder.fxml", "ekrut Order");
		
	}
	
	public void CreateRemoteOrderBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client_windows/ShipmentMethod.fxml", "Shipment Method");
	}
	
    public void BackBtn(ActionEvent event) throws Exception  {
		nextWindow(event, "/gui_client_windows/LoginEkrut.fxml", "Login Ekrut");
	}
    
    public void RemoteCode(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client_windows/RemoteCode.fxml", "Remote Code");
    }
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		this.titletxt.setText("Welcome " + ChatClient.Fname);
		if(p.type.equals("OK"))
		{
			remotecodebtn.setLayoutX(28);
			remotecodebtn.setLayoutY(117);
			createremotebtn.setVisible(false);
		}
		if(p.type.equals("OL")) {
			createorderbtn.setVisible(false);
			createremotebtn.setLayoutX(28);
			createremotebtn.setLayoutY(37);
			subreqbtn.setLayoutX(28);
			subreqbtn.setLayoutY(117);
			remotecodebtn.setVisible(false);
		}
		if(ChatClient.isSubscriber == true) {
			subreqbtn.setVisible(false);
		}
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