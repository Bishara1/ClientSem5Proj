package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import client.ChatClient;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Location;

public class ShipmentAddressController implements Initializable{
	
	@FXML
	private ComboBox RegionBox;
	@FXML
	private TextField citytxt;
	@FXML
	private TextField streettxt;
	@FXML
	private Button backBtn;
	@FXML
	private Button sendreqBtn;
	
	private ObservableList<String> obs;
	
	private String region = "";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept(new Message(0,Command.ReadLocations));
		setLocationBox();
	}
	
	public void SendYourRequest(ActionEvent event) throws Exception{
		if(region.isEmpty() || citytxt.getText().isEmpty() || streettxt.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR,"One or more fields is Empty!",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			ChatClient.address += citytxt.getText();
			ChatClient.address += ",";
			ChatClient.address += streettxt.getText();
			System.out.println(ChatClient.address);
			ChatClient.deliveryLocation = region;
			((Node)event.getSource()).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/RemoteLocation.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Login EKRUT");
			primaryStage.setScene(scene);		
			primaryStage.show();	
		}
	}

	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/ShipmentMethod.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	 public void setLocationBox() {	
	    	ArrayList<String> Locations = new ArrayList<String>();
	    	for(Location loc : ChatClient.locations)
	    	{
	    		Locations.add(loc.getLocation());
	    	}
	    	obs = FXCollections.observableArrayList(Locations);
			RegionBox.getItems().clear();
			RegionBox.setItems(obs);
		}
	 
	 @FXML
		public void SelectLocation(ActionEvent event) {
		 try {
			region = RegionBox.getSelectionModel().getSelectedItem().toString();
		 }catch(Exception e) {e.printStackTrace();}
		}

}
