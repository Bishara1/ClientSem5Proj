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
import logic.InventoryReports;
import logic.Machine;

public class InventoryReportController implements Initializable{
	
	Message messageToServer = new Message(null,null);
	private static String location;
	private  static String machineId;
	private static String month;
	private  static String year;
	
	public static InventoryReports requestedInventoryReport = new InventoryReports();
	public static boolean existFlag = false;
	
	@FXML
	private Button showReportBtn;
	@FXML
	private Button backBtn;
	@FXML
	private ComboBox<String> cmbLocation;
	@FXML
	private ComboBox<String> cmbMachineId;
	@FXML
	private ComboBox<String> cmbYear;
	@FXML
	private ComboBox<String> cmbMonth;
	
	ObservableList<String> MachineIdList;
	ObservableList<String> LocationList;
	ObservableList<String> YearList;
	ObservableList<String> MonthList;
	
	@FXML
	public void Select(ActionEvent event) {
		location = cmbLocation.getSelectionModel().getSelectedItem().toString();
		setMachineIdComboBox();
	}
	
	@FXML
	public void SelectMachineId(ActionEvent event) {
	    machineId = cmbMachineId.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectMonth(ActionEvent event) {
		month = cmbMonth.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectYear(ActionEvent event) {
		year = cmbYear.getSelectionModel().getSelectedItem().toString();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setAllComboBoxes();
		
	}
	
	public void setAllComboBoxes() {
		ArrayList<String> locationStr = new ArrayList<String>(Arrays.asList("North", "South", "UAE"));
		LocationList = FXCollections.observableArrayList(locationStr);
		cmbLocation.getItems().clear();
		cmbLocation.setItems(LocationList);

		ArrayList<String> yearStr = new ArrayList<String>(Arrays.asList("2022","2023"));
		YearList = FXCollections.observableArrayList(yearStr);
		cmbYear.getItems().clear();
		cmbYear.setItems(YearList);
		
		ArrayList<String> monthStr = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
		MonthList = FXCollections.observableArrayList(monthStr);
		cmbMonth.getItems().clear();
		cmbMonth.setItems(MonthList);
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
		
		if ((cmbLocation.getValue() == null )||(cmbMachineId.getValue() == null ) || (cmbYear.getValue() == null ) || (cmbYear.getValue() == null )) {
			Alert alert = new Alert(AlertType.ERROR,"One or more fields is empty!",ButtonType.OK);
			alert.showAndWait();
			}
		
		else	
			{
			//read inventory report from data base
			messageToServer.setCommand(Command.ReadInventoryReports);
			messageToServer.setContent(0);	
			ClientUI.chat.accept(messageToServer); 
			// now find the report -> no u
			
			for (InventoryReports ir : ChatClient.InventoryReport)
				if ((ir.getLocation().equals(location))&&(ir.getMonth().equals(month))&&ir.getYear().equals(year))
				{
					requestedInventoryReport = ir;
					existFlag = true;
				}
			if(existFlag == true) {
				nextWindow(event,"/gui_client_windows/InventoryData.fxml","Inventory Data");
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR,"Couldn't find report",ButtonType.OK);
				alert.showAndWait();
			}
			}
			
	}

	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/ChooseReportType.fxml","Choose Report Type");
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
