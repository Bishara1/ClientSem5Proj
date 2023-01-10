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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Machine;

public class InventoryReportController implements Initializable{
	
	Message messageToServer = new Message(null,null);
	private static String location;
	private  static String machineId;
	public static String str = "";
	
	@FXML
	private Button showReportBtn;
	@FXML
	private Button backBtn;
	@FXML
	private ComboBox<String> cmbLocation;
	@FXML
	private ComboBox<String> cmbMachineId;
	
	ObservableList<String> MachineIdList;
	ObservableList<String> LocationList;
	
	@FXML
	public void Select(ActionEvent event) {
		location = cmbLocation.getSelectionModel().getSelectedItem().toString();
		setMachineIdComboBox();
	}
	@FXML
	public void SelectMachineId(ActionEvent event) {
	    machineId = cmbMachineId.getSelectionModel().getSelectedItem().toString();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> type = new ArrayList<String>(Arrays.asList("North", "South", "UAE"));
		
		LocationList = FXCollections.observableArrayList(type);
		cmbLocation.getItems().clear();
		cmbLocation.setItems(LocationList);
		
	}
	
	public void setMachineIdComboBox() {
		
		ArrayList<String> typeMachine = new ArrayList<String>();
		messageToServer.setCommand(Command.ReadMachines);
		messageToServer.setContent(0);	
		ClientUI.chat.accept(messageToServer); 
	
		for(Machine i : ChatClient.machines)
		{
			if(location.equals(i.getLocation()))
			{
				typeMachine.add(String.valueOf(i.getMachine_id()));
			}
		}
		
		MachineIdList = FXCollections.observableArrayList(typeMachine);
		cmbMachineId.getItems().clear();
		cmbMachineId.setItems(MachineIdList);
	}
	

	public  void ShowReport(ActionEvent event)throws Exception{
		boolean flag = false;
		
		int total = 0;
		String[] item = null;
		String[] amount = null;
		
		if ((cmbLocation.getValue() == null )||(cmbMachineId.getValue() == null )) {
			Alert alert = new Alert(AlertType.ERROR,"One or more feilds is Empty!",ButtonType.OK);
			alert.showAndWait();
			}
		else
			{
				for(Machine m : ChatClient.machines)
			
			{
				if(location.equals(m.getLocation()) && Integer.parseInt(machineId)==(m.getMachine_id()))
				{
					item = m.getAllItems().split(",");
					amount = m.getAmount_per_item().split(",");
					for (int i = 0 ; i < item.length ; i++) {
						str += item[i] + "," + amount[i] + ".";
					}
					total=m.getTotal_inventory();	
					flag = true;
					break;
				}
			}
			
			if (flag)
			{
				ArrayList<String> NewReport = new ArrayList<String>(Arrays.asList(machineId,str,String.valueOf(total),location));
				messageToServer.setCommand(Command.InsertInventoryReport);
				messageToServer.setContent(NewReport);
				ClientUI.chat.accept(messageToServer);
				
				nextWindow(event,"/gui_client/InventoryData.fxml","Inventory Data");
			}
			else {
				Alert alert = new Alert(AlertType.ERROR,"No report found",ButtonType.OK);
				alert.showAndWait();
				}
			}
	}

	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client/ChooseReportType.fxml","Choose Report Type");
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
