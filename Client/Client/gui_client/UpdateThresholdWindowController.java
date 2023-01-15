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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Machine;

public class UpdateThresholdWindowController implements Initializable {
	
	@FXML
	private Button backBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Label locationlbl;
	
	@FXML
	private ComboBox<String> locationName;
	@FXML
	private ComboBox<String> machineCode;
	@FXML
	private TextField thresholdValue;
	
	ObservableList<String> MachineIdList;
	ObservableList<String> LocationList;
	
	String selectedLocation = "";
	String machineId = "";
	
	@FXML
	public void UpdateBtn(ActionEvent event) throws Exception{
		String newValue = "";
		if(thresholdValue.getText().isEmpty() || selectedLocation.isEmpty() || machineId.isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR,"One or more fields is missing",ButtonType.OK);
			alert.showAndWait();
		}
		else {
			
			newValue = thresholdValue.getText();
			Message msg = new Message(null,null);
			ArrayList<String> data = new ArrayList<String>();
			data.add("machinesThreshold");
			data.add(machineId);	
			try {
					Integer.parseInt(newValue);
					if(Integer.parseInt(thresholdValue.getText()) < 0)
					{
						Alert alert = new Alert(AlertType.ERROR,"Threshold value must be at least 0",ButtonType.OK);
						alert.showAndWait();
					}
					else {
						data.add(newValue);
						msg.setContent(data);
						msg.setCommand(Command.UpdateMachineThreshold);
						ClientUI.chat.accept(msg);
						Alert alert = new Alert(AlertType.INFORMATION,"Value updated successfully!",ButtonType.OK);
						alert.showAndWait();
					}
				}catch(NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR,"Must enter a NUMERIC value",ButtonType.OK);
					alert.showAndWait();
					data.add("0");
				}
			
		}
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/RegionalManager.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Regional Manager");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedLocation = ChatClient.locationName;
		locationlbl.setText(locationlbl.getText() + selectedLocation);
		setMachineBox();
	
	}
	
	public void setMachineBox()
	{
		ArrayList<String> typeMachine = new ArrayList<String>();
		ClientUI.chat.accept(new Message(0,Command.ReadMachines)); 
		
		for(Machine i : ChatClient.machines)
		{
			if(selectedLocation.equals(i.getLocation()))
			{
				typeMachine.add(String.valueOf(i.getMachine_id()));
			}
		}
		
		MachineIdList= FXCollections.observableArrayList(typeMachine);
		machineCode.getItems().clear();
		machineCode.setItems(MachineIdList);
	}
	
	@FXML
	public void SelectMachineId(ActionEvent event) {
		try {
		machineId = machineCode.getSelectionModel().getSelectedItem().toString();
		}catch(Exception e) { 
			Alert alert = new Alert(AlertType.ERROR,"Must choose a machine ID",ButtonType.OK);
			alert.showAndWait();
		}
	}

}
