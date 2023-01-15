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
	
	private int flag = 0;
	
	Message ReportmessageToServer = new Message(null, null);
	Message messageToServer = new Message(null, null);
	Message orderToServer = new Message(null, null);
	
	private static String location;
	public static String year;
	public static String month;
	public static String requestedReport;
	
	private ArrayList<String> newItems = new ArrayList<>();

	ObservableList<String> yearList;
	ObservableList<String> MonthList;
	ObservableList<String> LocationList;
	
	
	/* Functionality : get the selected option in "location" combo box
	 * Input : event
	 * Result : save combo box selected value to location
	 * */
	@FXML
	public void Select(ActionEvent event) {
		location = cmbLocation.getSelectionModel().getSelectedItem().toString();
	}
	
	/* Functionality : get the selected option in "year" combo box
	 * Input : event
	 * Result : save combo box selected value to year
	 * */
	@FXML
	public void SelectYear(ActionEvent event) {
		year = cmbYear.getSelectionModel().getSelectedItem().toString();
	}
	
	/* Functionality : get the selected option in "month" combo box
	 * Input : event
	 * Result : save combo box selected value to month
	 * */
	@FXML
	public void SelectMonth(ActionEvent event) {
		month = cmbMonth.getSelectionModel().getSelectedItem().toString();
	}
	
	/* Functionality : call functions to initialize all combo boxes and read machines from data base
	 * Input : URL url , ResourceBundle rb
	 * Result : start combo box values and read machines from data base
	 * */
	public void initialize(URL url, ResourceBundle rb) {
		setYearComboBox();
		setMonthComboBox();
		setLocationComboBox();
		// read machines now
		messageToServer.setCommand(Command.ReadMachines);
		messageToServer.setContent(0);	
		ClientUI.chat.accept(messageToServer); 
	}
	
	/* Functionality : sets year combo box
	 * Input : yearList , cmbYear
	 * Result : start combo box values
	 * */
    public void setYearComboBox() {	
    	ArrayList<String> year = new ArrayList<String>(Arrays.asList("2015","2016","2017","2018","2019","2020","2021","2022","2023"));
    	
		yearList = FXCollections.observableArrayList(year);
		cmbYear.getItems().clear();
		cmbYear.setItems(yearList);
	}
	
    /* Functionality : sets month combo box
	 * Input : monthList , cmbMonth
	 * Result : start combo box values
	 * */
	public void setMonthComboBox() {
		ArrayList<String> month = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
	
		MonthList = FXCollections.observableArrayList(month);
		cmbMonth.getItems().clear();
		cmbMonth.setItems(MonthList);
	}
	
	/* Functionality : sets location combo box
	 * Input : locationList , cmbLocation
	 * Result : start combo box values
	 * */
	public void setLocationComboBox() {
		ArrayList<String> type = new ArrayList<String>(Arrays.asList("North", "South", "UAE"));
				
		LocationList = FXCollections.observableArrayList(type);
		cmbLocation.getItems().clear();
		cmbLocation.setItems(LocationList);
	}
	
	
	/* Functionality : checks selected values of all combo boxes , calls function to find requested report
	 * Input : cmbYear , cmbMonth , cmbLocation , event
	 * Result : alert when combo box is empty, else call for function OrderReportSearch
	 * */
	public void ShowReportBtn(ActionEvent event) throws Exception{
		// Checking if one or more fields are empty
		if ((cmbYear.getValue() == null) || (cmbMonth.getValue() == null) || (cmbLocation.getValue() == null))
		{
			Alert alert = new Alert(AlertType.ERROR,"One or more feilds is Empty!",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			OrderReportSearch(event);
		}
	}
	
	/* Functionality : checks the current date of today , then searches or builds the requested report
	 * Input : event
	 * Result : builds the report , or alerts that no data was found
	 * */
	public void OrderReportSearch(ActionEvent event) throws Exception
	{
		boolean currentDate = checkCurrentDate();
		OrdersReports currentReport; 
		// if dataFlag = true recreate this month's report
		// else find the bloody report in data base, if you find it show it, else make it damn it
	
		Message msgToUpdate = new Message(null, null);
		if (currentDate) 
		{
			currentReport = findCurrentReport();
			createNewOrderReport();
			if (flag==0) {System.out.println("no orders in the selected location");}
			// there is no such report in data base (meaning requested year, month and machine id)
			else if(currentReport == null)
			{	// create new report
				// call a bloody function to get all machines id to put them inn a bloody string
				ArrayList<String> report = new ArrayList<String>(Arrays.asList("",location,fromArrayToString(newItems),month,year));
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
	
	// function that checks if the combo boxes values are today's date
		public boolean checkCurrentDate() {	// MONTHVALUE IS PROBLEMATIC HERE ! IS IT 01 OR 1?!?!?!
			if ((year.equals(String.valueOf(LocalDate.now().getYear()))))
				if ((month.equals(String.valueOf(LocalDate.now().getMonthValue()))))
					return true;
			return false;
		}	//return true if we still are in the requested date
		
	
		
	public OrdersReports findCurrentReport() {
		// reads data base
		ReportmessageToServer.setCommand(Command.ReadOrdersReports);
		ReportmessageToServer.setContent(0);	
		ClientUI.chat.accept(ReportmessageToServer);
		try {
			if(ChatClient.orderReport.get(0)==(null)) return null;
			
			for (OrdersReports orderReport : ChatClient.orderReport)
			{
				// if combo box selected values exist in the orders report
				if (orderReport.getLocation().equals(location.toString()) &&
						orderReport.getYear().equals(year.toString()) &&
							orderReport.getMonth().equals(month.toString())) 
				{
				return orderReport;
				}
			}
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR,"Couldn't find report",ButtonType.OK);
			alert.showAndWait();
		}
		return null;
	}
		
	public void createNewOrderReport() {
		
		orderToServer.setCommand(Command.ReadOrders);
		orderToServer.setContent(0);
		ClientUI.chat.accept(orderToServer);
		String[] dateSplit = null;
		for(Order o : ChatClient.orders)
		{
			dateSplit = o.getOrder_created().toString().split("-");
			if(dateSplit[0].equals(year) && Integer.parseInt(dateSplit[1])==(Integer.parseInt(month)) && location.equals(o.getLocation()))
			{
				// call another function 
				// to add data
				scanOrder(o.getItems_in_order());
				flag++;
			}
		}
	}
	
	
	
	public void scanOrder(String data) {
		String[] temp = data.split("\\.");
		
		for (String s : temp) {
			checkInReport(s);
		}
		
	}
	
	// adds items o new report
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
	
	
	public void FindRequestedOrderReport(ActionEvent event) throws Exception{
		
		ReportmessageToServer.setCommand(Command.ReadOrdersReports);
		ReportmessageToServer.setContent(0);	
		ClientUI.chat.accept(ReportmessageToServer);
		boolean flag = false;
		// find the requested order
		try {
			if (ChatClient.orderReport.get(0)!=null)
			{
	
				for (int j = 0; j < ChatClient.orderReport.size(); j++)
			{	if (ChatClient.orderReport.get(j).getLocation().equals(location.toString()) &&
					ChatClient.orderReport.get(j).getYear().equals(year.toString()) &&
						ChatClient.orderReport.get(j).getMonth().equals(month.toString())) {
				
							requestedReport = ChatClient.orderReport.get(j).getData();
							flag = true;
							break;
					}
			}
				
			
		if (flag) {
			nextWindow(event,"/gui_client_windows/ReportsCEO.fxml","Pie Chart (ReportsCEO)");
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
					if ( order.getLocation().equals(location)
							&& dateSplit[0].equals(year)
							&& Integer.parseInt(dateSplit[1]) == Integer.parseInt(month))
					{
						things += order.getItems_in_order();
						
						alertFlag = true;
					}
					
				}
				if (alertFlag)
				{
					scanOrder(things);
					
					ArrayList<String> NewReport = new ArrayList<String>(Arrays.asList("",location,fromArrayToString(newItems),month,year));
					orderToServer.setCommand(Command.InsertOrderReport);
					orderToServer.setContent(NewReport);
					ClientUI.chat.accept(orderToServer);
					FindRequestedOrderReport(event);
				}
				if (!alertFlag)
				{
					Alert alert = new Alert(AlertType.ERROR,"No order reports in the requested timeline",ButtonType.OK);
					alert.showAndWait();
					}
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR,"No reports available!",ButtonType.OK);
				alert.showAndWait();
			}
	}catch(Exception e) {
		Alert alert = new Alert(AlertType.ERROR,"Couldn't find report",ButtonType.OK);
		alert.showAndWait();
		

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
