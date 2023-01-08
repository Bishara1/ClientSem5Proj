package gui_client;

import java.util.ArrayList;
import java.util.Arrays;

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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Machine;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Command;
import common.Message;

public class MonthlyReportsController implements Initializable {
	
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
	Message ReportmessageToServer = new Message(null, null);
	private String location;
	public static String year;
	public static String month;
	public static String machineId;
	public String reportType;
	public static String requestedReport;
	
	ObservableList<String> yearList;
	ObservableList<String> MonthList;
	ObservableList<String> TypeList;
	ObservableList<String> LocationList;
	ObservableList<String> MachineIdList;
	
	@FXML
	public void Select(ActionEvent event)
	{
		location = cmbLocation.getSelectionModel().getSelectedItem().toString();
		setMachineIdComboBox();
	}
	
	@FXML
	public void SelectYear(ActionEvent event)
	{
		year = cmbYear.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectMonth(ActionEvent event)
	{
		month = cmbMonth.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectMachineId(ActionEvent event)
	{
		machineId = cmbMachineId.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectReportType(ActionEvent event)
	{
		reportType = cmbType.getSelectionModel().getSelectedItem().toString();
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
		year.add("2023");
		
		yearList = FXCollections.observableArrayList(year);
		cmbYear.getItems().clear();
		cmbYear.setItems(yearList);
	}
	
		public void setMonthComboBox() {
		ArrayList<String> month = new ArrayList<String>(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
	
		MonthList = FXCollections.observableArrayList(month);
		cmbMonth.getItems().clear();
		cmbMonth.setItems(MonthList);
	}
	
	public void setTypeComboBox() {
		ArrayList<String> type = new ArrayList<String>();
		
		type.add("Inventory");
		type.add("Orders");
		type.add("Users");
		
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
			if(location.equals(i.getLocation()))
			{
				typeMachine.add(String.valueOf(i.getMachine_id()));
			}
		}
		
		MachineIdList = FXCollections.observableArrayList(typeMachine);
		cmbMachineId.getItems().clear();
		cmbMachineId.setItems(MachineIdList);
	}

	public void ShowReportBtn(ActionEvent event) throws Exception{
		// Checking if one or more fields are empty
		if ((cmbYear.getValue() == null )||(cmbMonth.getValue() == null )
				||(cmbType.getValue() == null ) ||(cmbLocation.getValue() == null )
					||(cmbMachineId.getValue() == null)) {
			Alert alert = new Alert(AlertType.ERROR,"One or more feilds is Empty!",ButtonType.OK);
			alert.showAndWait();
			}
		else {
			
			switch (reportType) {
			case "Orders":
				OrderReportSearch(event);
				break;
			case "Inventory":
				System.out.println("Inventory");
				break;
			case "Users":
				System.out.println("Users");
				break;

			default:
				System.out.println("need to fkn complete");
				break;
			}
		}
	}
	
	public void OrderReportSearch(ActionEvent event) throws Exception {
	
		String.valueOf(LocalDate.now().getMonthValue());
		String.valueOf(LocalDate.now().getYear());
		
		//get combobox value
		//check if month and year comboboxes values are the current month and year
		//if they are the same, recreate this months report
		//else try to find them in database, if they already exist get them
		//if they dont exist, make them
		
	
	// FindRequestedorderReport(event);
	}
	
	public void FindRequestedOrderReport(ActionEvent event) throws Exception{
		
		ReportmessageToServer.setCommand(Command.ReadOrdersReports);
		ReportmessageToServer.setContent(0);	
		ClientUI.chat.accept(ReportmessageToServer);
		boolean flag = false;
		// find the requested order
		if (ChatClient.orderReport.get(0)!=null)
		{

			for (int j = 0; j < ChatClient.orderReport.size(); j++)
		{	if (ChatClient.orderReport.get(j).getMachine_id().equals(machineId.toString()) &&
				ChatClient.orderReport.get(j).getYear().equals(year.toString()) &&
					ChatClient.orderReport.get(j).getMonth().equals(month.toString())) {
						requestedReport = ChatClient.orderReport.get(j).getData();
						flag = true;
						break;
				}
		}
			
		if (flag) {
			((Node)event.getSource()).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ReportsCEO.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Pie Chart (ReportsCEO)");
			primaryStage.setScene(scene);		
			primaryStage.show();	
		}

		else
			{
				Alert alert = new Alert(AlertType.ERROR,"No order reports in the requested timeline",ButtonType.OK);
				alert.showAndWait();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR,"No reports available!",ButtonType.OK);
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
