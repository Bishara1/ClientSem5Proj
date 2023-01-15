package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChooseReportTypeController {
	
	@FXML
	private Button backBtn;
	@FXML
	private Button monthlyReportBtn;
	@FXML
	private Button inventoryReportBtn;
	@FXML
	private Button userReportBtn;
	
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/CEOReports.fxml","CEO Reports");
	}

	public void MonthlyReportsBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/MonthlyReports.fxml","Monthly Reports");
	}
	
	public void InventoryReportsBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/InventoryReport.fxml","Inventory Report");
	}
	
	public void UsersReportsBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/BarChart.fxml","Monthly Reports");
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
