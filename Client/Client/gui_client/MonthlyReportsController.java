package gui_client;


import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MonthlyReportsController {
	
	@FXML
	private Button showReportBtn;
	@FXML
	private Button backBtn;
	
	@FXML
	private ComboBox cmbYear;
	@FXML
	private ComboBox cmbMonth;
	@FXML
	private ComboBox cmbLocation;
	@FXML
	private ComboBox cmbMachineId;
	@FXML
	private ComboBox cmbType;
	
	ObservableList<Integer> yearList;
	ObservableList<Integer> MonthList;
	ObservableList<String> TypeList;

	private void setYearComboBox() {
		ArrayList<Integer> year = new ArrayList<Integer>();
		
		for(int i=2015;i<=2022;i++)
			year.add(i);
		
		yearList = FXCollections.observableArrayList(year);
		cmbYear.setItems(yearList);
	}
	
	private void setMonthComboBox() {
		ArrayList<Integer> month = new ArrayList<Integer>();
		
		for(int i=1;i<=12;i++)
			month.add(i);
		
		MonthList = FXCollections.observableArrayList(month);
		cmbMonth.setItems(MonthList);
	}
	
	private void setTypeComboBox() {
		ArrayList<String> type = new ArrayList<String>();
		
		type.add("Inventory");
		type.add("Orders");
		
		TypeList = FXCollections.observableArrayList(type);
		cmbType.setItems(TypeList);
	}
	
	
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/CEOReports.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
	//	scene.getStylesheets().add(getClass().getResource("/gui/CEOReports.css").toExternalForm());
		primaryStage.setTitle("CEO Reports");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	

}
