package gui_client;

import java.util.ArrayList;
import java.util.Arrays;

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
import logic.Order;
import logic.OrdersReports;

import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import client.ChatClient;
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
	Message orderToServer = new Message(null, null);
	Message ReportmessageToServer = new Message(null, null);
	private String location;
	public String reportType;
	public static String year;
	public static String month;
	public static String machineId;
	public static String requestedReport;
	public static String requestedReport1;
	
	private ArrayList<String> newItems = new ArrayList<>();

	ObservableList<String> yearList;
	ObservableList<String> MonthList;
	ObservableList<String> TypeList;
	ObservableList<String> LocationList;
	ObservableList<String> MachineIdList;
	
	@FXML
	public void Select(ActionEvent event) {
		location = cmbLocation.getSelectionModel().getSelectedItem().toString();
		setMachineIdComboBox();
	}
	
	@FXML
	public void SelectYear(ActionEvent event) {
		year = cmbYear.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectMonth(ActionEvent event) {
		month = cmbMonth.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectMachineId(ActionEvent event) {
		machineId = cmbMachineId.getSelectionModel().getSelectedItem().toString();
	}
	
	@FXML
	public void SelectReportType(ActionEvent event) {
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
		ArrayList<String> month = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
	
		MonthList = FXCollections.observableArrayList(month);
		cmbMonth.getItems().clear();
		cmbMonth.setItems(MonthList);
	}
	
	public void setTypeComboBox() {
		ArrayList<String> type = new ArrayList<String>(Arrays.asList("Inventory", "Orders", "Users"));
		
		TypeList = FXCollections.observableArrayList(type);
		cmbType.getItems().clear();
		cmbType.setItems(TypeList);
	}
	
	public void setLocationComboBox() {
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
	
	public void OrderReportSearch(ActionEvent event) throws Exception
	{
		boolean currentDate = checkCurrentDate();
		OrdersReports currentReport; 
		// if dataFlag = true recreate this month's report
		// else find the bloody report in data base, if you find it show it, else make it damn it
	
		Message msgToUpdate = new Message(null, null);
		if (currentDate) {
			currentReport = findCurrentReport();
			createNewOrderReport();
			
			// there is no such report in db (meaning requested year, month and machine id)
			if(currentReport == null)
			{	// create new report
				ArrayList<String> report = new ArrayList<String>(Arrays.asList("",machineId,location,fromArrayToString(newItems),month,year));
				ReportmessageToServer.setCommand(Command.InsertOrderReport);
				ReportmessageToServer.setContent(report);
				ClientUI.chat.accept(ReportmessageToServer);
			}
			else
			{
				//update the current report, insert newItems instead of the old ones //update data
				ArrayList<String> WhatToUpdate = new ArrayList<>(Arrays.asList("ordersreport",currentReport.getReport_id(),fromArrayToString(newItems)));
				msgToUpdate.setCommand(Command.DatabaseUpdate);
				msgToUpdate.setContent(WhatToUpdate);
				ClientUI.chat.accept(msgToUpdate);
			}
			FindRequestedOrderReport(event);
		}
		else {	// if currentDate is false , 
				// meaning the order report is already in the data base
				FindRequestedOrderReport(event);
			 }
	}
	
	public void createNewOrderReport() {
		
		orderToServer.setCommand(Command.ReadOrders);
		orderToServer.setContent(0);
		ClientUI.chat.accept(orderToServer);
		String[] dateSplit = null;
		for(Order o : ChatClient.orders)
		{
			dateSplit = o.getOrder_created().toString().split("-");
			if(dateSplit[0].equals(year) && Integer.parseInt(dateSplit[1])==(Integer.parseInt(month)) && (Integer.parseInt(machineId))==(o.getMachine_id()))
			{
				// call another function 
				// to add data
				scanOrder(o.getItems_in_order());
			}
		}
	}
	
	public OrdersReports findCurrentReport() {
		// reads data base
		ReportmessageToServer.setCommand(Command.ReadOrdersReports);
		ReportmessageToServer.setContent(0);	
		ClientUI.chat.accept(ReportmessageToServer);
		
		if(ChatClient.orderReport.get(0)==(null)) return null;
		
		for (OrdersReports orderReport : ChatClient.orderReport)
		{
			// if combo box selected values exist in the orders report
			if (orderReport.getMachine_id().equals(machineId.toString()) &&
					orderReport.getYear().equals(year.toString()) &&
						orderReport.getMonth().equals(month.toString())) 
			{
			return orderReport;
			}
		}
		return null;
	}
	
	public void scanOrder(String data) {
		String[] temp = data.split("\\.");
		
		for (String s : temp) {
			checkInReport(s);
		}
		
	}
	
	// adds items o new report
	// if 
	public void checkInReport(String data) {
		String[] nameAmount = data.split(",");
		String[] old = null;
		int newAmount;
		int index = checkContains(newItems,nameAmount[0]);
		if(index == -1) //checkContains
		{
			newItems.add(data);
		}
		else
		{
			old = newItems.get(index).split(",");
			newAmount = Integer.parseInt(nameAmount[1]) + Integer.parseInt(old[1]); //
			newItems.set(index, nameAmount[0] + "," + String.valueOf(newAmount));
		}
	}
	
	public int checkContains(ArrayList<String> arr, String s)
	{
		int size = arr.size();
		String[] split = null;
		for(int i = 0;i<size;i++)
		{
			split = arr.get(i).split(",");
			if(split[0].equals(s))
				return i;
		}
		return -1;
	}
	
	public String fromArrayToString(ArrayList<String> arr)
	{
		int size = arr.size();
		String str = "";
		for(int i = 0;i<size;i++)
		{
			str += arr.get(i);
			str += ".";
		}
		return str;
	}
	
	// function that checks if the combo boxes values are today's date
	public boolean checkCurrentDate() {	// MONTHVALUE IS PROBLEMATIC HERE ! IS IT 01 OR 1?!?!?!
		if ((year.equals(String.valueOf(LocalDate.now().getYear()))))
			if ((month.equals(String.valueOf(LocalDate.now().getMonthValue()))))
				return true;
		return false;
	}	//return true if we still are in the requested date
	
	public void FindRequestedOrderReport(ActionEvent event) throws Exception{
		
		ReportmessageToServer.setCommand(Command.ReadOrdersReports);
		ReportmessageToServer.setContent(0);	
		ClientUI.chat.accept(ReportmessageToServer);
		boolean flag = false;
		// find the requested order
//<<<<<<< HEAD
//		if (ChatClient.orderReport.get(0)!=null) {
//			for (int j = 0; j < ChatClient.orderReport.size(); j++)	{	
//				if (ChatClient.orderReport.get(j).getMachine_id().equals(machineId.toString()) &&
//						ChatClient.orderReport.get(j).getYear().equals(year.toString()) &&
//							ChatClient.orderReport.get(j).getMonth().equals(month.toString())) {
//					
//					requestedReport = ChatClient.orderReport.get(j).getData();
//					flag = true;
//					break;
//=======
		if (ChatClient.orderReport.get(0)!=null)
		{

			for (int j = 0; j < ChatClient.orderReport.size(); j++)
		{	if (ChatClient.orderReport.get(j).getMachine_id().equals(machineId.toString()) &&
				ChatClient.orderReport.get(j).getYear().equals(year.toString()) &&
					ChatClient.orderReport.get(j).getMonth().equals(month.toString())) {
						requestedReport = ChatClient.orderReport.get(j).getData();
						flag = true;
						break;
//>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
				}
//<<<<<<< HEAD
//			}
//				
//			if (flag) {
//				((Node)event.getSource()).getScene().getWindow().hide();
//				Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ReportsCEO.fxml"));
//				Stage primaryStage = new Stage();
//				Scene scene = new Scene(root);
//				primaryStage.setTitle("Pie Chart (ReportsCEO)");
//				primaryStage.setScene(scene);		
//				primaryStage.show();	
//			}
//	
//			else {
//					Alert alert = new Alert(AlertType.ERROR,"No order reports in the requested timeline",ButtonType.OK);
//					alert.showAndWait();
//=======
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
				orderToServer.setCommand(Command.ReadOrders);
				orderToServer.setContent(0);
				ClientUI.chat.accept(orderToServer);
				String[] dateSplit= null;
				String things = "";
				boolean alertFlag = false;
				for (Order order : ChatClient.orders)
				{
					dateSplit = order.getOrder_created().toString().split("-");
					// if combo box selected values exist in the orders report
					if ( order.getMachine_id() == Integer.parseInt(machineId)
							&& dateSplit[0].equals(year)
							&& Integer.parseInt(dateSplit[1]) == Integer.parseInt(month))
					{
						things += order.getItems_in_order();
						
						alertFlag = true;
					}
					
				}
				if (alertFlag)
				{
					ArrayList<String> NewReport = new ArrayList<String>(Arrays.asList("",machineId,location,things,month,year));
					orderToServer.setCommand(Command.InsertOrderReport);
					orderToServer.setContent(NewReport);
					ClientUI.chat.accept(orderToServer);
					FindRequestedOrderReport(event);
				}
				if (!alertFlag)
				{
					Alert alert = new Alert(AlertType.ERROR,"No order reports in the requested timeline",ButtonType.OK);
					alert.showAndWait();}
				}
//>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
			}
//<<<<<<< HEAD
//		}
//=======
//>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
		
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
