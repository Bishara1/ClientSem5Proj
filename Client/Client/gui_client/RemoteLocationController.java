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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import logic.Machine;
import javafx.stage.Stage;

public class RemoteLocationController implements Initializable {
	private String selected;
	private Message messageToServer = new Message(null, null);
	private ObservableList<String> MachineIdList;
	
	@FXML
	private ComboBox<String> cmbLocation;
	@FXML
	private ComboBox<String> cmbMachine;
	@FXML
	private Button backbtn;
	@FXML
	private Button startorderbtn;
	
	@FXML
	public void Select(ActionEvent event) {
		selected = cmbLocation.getSelectionModel().getSelectedItem().toString();
		setMachineIdComboBox();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setLocationComboBox();
		
	}
	
	public void setMachineIdComboBox() {
		ArrayList<String> typeMachine = new ArrayList<String>();
		messageToServer.setCommand(Command.ReadMachines);
		messageToServer.setContent(0);	
		ClientUI.chat.accept(messageToServer); 
		
		for(Machine machine : ChatClient.machines) {
			if(selected.equals(machine.getLocation())) {
				typeMachine.add(String.valueOf(machine.getMachine_id()));
			}
		}
		
		MachineIdList = FXCollections.observableArrayList(typeMachine);
		cmbMachine.getItems().clear();
		cmbMachine.setItems(MachineIdList);
	}
	
	public void setLocationComboBox() {
		ArrayList<String> Locations = new ArrayList<String>(Arrays.asList("North", "South", "UAE"));
		
	}
	
	public void BackBtn(ActionEvent event) throws Exception  { //fix this apparently its null
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UserUI.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
    public void StartOrderBtn() {
		
	}
}
