package gui_client;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Command;
import common.Message;

public class MonthlyReportsController implements Initializable{
	
	@FXML
	private Button showReportBtn;
	@FXML
	private Button backBtn;
	
	@FXML
	public ComboBox<String> cmbYear;
	@FXML
	private ComboBox<String> cmbMonth;
	@FXML
	private ComboBox<String> cmbLocation;
	@FXML
	private ComboBox<String> cmbMachineId;
	@FXML
	private ComboBox<String> cmbType;
	
	Message messageToServer = new Message(null, null);
	private String selected;
	
	ObservableList<String> yearList;
	ObservableList<String> MonthList;
	ObservableList<String> TypeList;
	ObservableList<String> LocationList;
	ObservableList<String> MachineIdList;
	
	@FXML
	public void Select(ActionEvent event)
	{
		
		selected = cmbLocation.getSelectionModel().getSelectedItem().toString();
		System.out.println(selected);
		setMachineIdComboBox();
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		setYearComboBox();
		setMonthComboBox();
		setTypeComboBox();
		setLocationComboBox();
	}
	
    public void setYearComboBox() {	
	ArrayList<String> year = new ArrayList<String>();
		
		year.add("2015");
		year.add("2016");
		year.add("2017");
		year.add("2018");
		year.add("2019");
		year.add("2020");
		year.add("2021");
		year.add("2022");
		
		yearList = FXCollections.observableArrayList(year);
		cmbYear.getItems().clear();
		cmbYear.setItems(yearList);
	}
	
		public void setMonthComboBox() {
		ArrayList<String> month = new ArrayList<String>();
		
		month.add("1");
		month.add("2");
		month.add("3");
		month.add("4");
		month.add("5");
		month.add("6");
		month.add("7");
		month.add("8");
		month.add("9");
		month.add("10");
		month.add("11");
		month.add("12");
	
		MonthList = FXCollections.observableArrayList(month);
		cmbMonth.getItems().clear();
		cmbMonth.setItems(MonthList);
	}
	
	public void setTypeComboBox() {
		ArrayList<String> type = new ArrayList<String>();
		
		type.add("Inventory");
		type.add("Orders");
		
		TypeList = FXCollections.observableArrayList(type);
		cmbType.getItems().clear();
		cmbType.setItems(TypeList);
	}
	
	public void setLocationComboBox() {
		ArrayList<String> type = new ArrayList<String>();
		
		type.add("North");
		type.add("South");
		type.add("UAE");
		
		LocationList = FXCollections.observableArrayList(type);
		cmbLocation.getItems().clear();
		cmbLocation.setItems(LocationList);
	}
	
	public void setMachineIdComboBox() {
		ArrayList<String> typeMachine = new ArrayList<String>();
//		ConnectNewClient();
		messageToServer.setCommand(Command.ReadMachines);
		messageToServer.setContent(0);	
		ClientUI.chat.accept(messageToServer); 
		
		for(Machine i : ChatClient.machines)
		{
			
			if(selected.equals(i.getLocation()))
			{
				typeMachine.add(String.valueOf(i.getMachine_id()));
			}
		}
		
		MachineIdList = FXCollections.observableArrayList(typeMachine);
		cmbMachineId.getItems().clear();
		cmbMachineId.setItems(MachineIdList);
	}
	
//	public void ConnectNewClient() {
//		ClientUI.chat = new ClientController("localhost", 5555);  // new client connected
//		///ClientUI.chat.accept("login"); // send to server that a client is connected
//	}
	
	public void ShowReportBtn(ActionEvent event) throws Exception{
		// Checking if one or more fields are empty
		if ((cmbYear.getValue() == null )||(cmbMonth.getValue() == null )
				||(cmbType.getValue() == null ) ||(cmbLocation.getValue() == null )
					||(cmbMachineId.getValue() == null)) {
			Alert alert = new Alert(AlertType.ERROR,"One or more feilds is Empty!",ButtonType.OK);
			alert.showAndWait();
			}
		else {
			Alert alert = new Alert(AlertType.ERROR,"NOT COMPLETE",ButtonType.OK);
			alert.showAndWait();
		}

	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/CEOReports.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("CEO Reports");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	

}
