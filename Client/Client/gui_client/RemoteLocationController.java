package gui_client;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import logic.Machine;

public class RemoteLocationController {
	private String selected;
	private Message messageToServer = new Message(null, null);
	private ObservableList<String> MachineIdList;
	
	@FXML
	private ComboBox<String> cmbLocation;
	@FXML
	private ComboBox<String> cmbMachine;
	
	@FXML
	public void Select(ActionEvent event) {
		selected = cmbLocation.getSelectionModel().getSelectedItem().toString();
		setMachineIdComboBox();
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
	
	public void LocationCombo() {
		
	}
	public void BackBtn() {
		
	}
    public void StartOrderBtn() {
		
	}
}
