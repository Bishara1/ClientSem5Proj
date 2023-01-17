package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DiscountLocationController implements Initializable {

	@FXML
	private Button backbtn;
	@FXML
	private Button confirmbtn;	
	@FXML
	private ComboBox cmbLocation;
	
	
	@FXML
	private TextField discount;
	
	private String location;
	private ObservableList<String> LocationList;
	Message messageToServer = new Message(null, null);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setLocationComboBox();
		
	}
	
	@FXML
	public void Select(ActionEvent event) {
		location = cmbLocation.getSelectionModel().getSelectedItem().toString();
	}
	
	public void setLocationComboBox() {
		ArrayList<String> type = new ArrayList<String>(Arrays.asList("North", "South", "UAE"));
				
		LocationList = FXCollections.observableArrayList(type);
		cmbLocation.getItems().clear();
		cmbLocation.setItems(LocationList);
	}
	
    public void ConfirmBtn() {
		String val = discount.getText();
		// NEED TO ADD A TEST IF VAL IS ONLY DIGITS !!!!!!
		String[] dis = new String[3];
		try {
			
			Integer.parseInt(val);
			
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR,"must enter number",ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
	
		 if((Integer.parseInt(val)>0 && Integer.parseInt(val)<=100))
		{
			switch (location) {
			case "North":
				dis[0]="location";
				dis[1]="North";
				dis[2]=val;
				messageToServer.setCommand(Command.UpdateDiscount);
				messageToServer.setContent(dis);
				ClientUI.chat.accept(messageToServer);
				break;

			case "South":
				dis[0]="location";
				dis[1]="South";
				dis[2]=val;
				messageToServer.setCommand(Command.UpdateDiscount);
				messageToServer.setContent(dis);
				ClientUI.chat.accept(messageToServer);
				break;
				
			case "UAE":
				dis[0]="location";
				dis[1]="UAE";
				dis[2]=val;
				messageToServer.setCommand(Command.UpdateDiscount);
				messageToServer.setContent(dis);
				ClientUI.chat.accept(messageToServer);
				break;
				
			default:
				break;
			}
			Alert alert = new Alert(AlertType.CONFIRMATION,"Discount applied successfully.",ButtonType.OK);
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR,"Discount not in range\nRange must a number be between 0 and 100.",ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	
    public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/WorkerUI.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("WORKER UI");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}

}
