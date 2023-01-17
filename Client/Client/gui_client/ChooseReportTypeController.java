package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Class responsible for ceo window that displays 3 report types:
 * Monthly reports, inventory reports, user reports. The ceo can choose
 * what report to display.
 */
public class ChooseReportTypeController {
	
	// Buttons in window
	@FXML
	private Button backBtn;
	@FXML
	private Button monthlyReportBtn;
	@FXML
	private Button inventoryReportBtn;
	@FXML
	private Button userReportBtn;
	
	/**
	 * back to ceo reports page
	 * @param event Type of action that occurred in the window by the user (when pressing a button in this scenario)
	 * @throws Exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/CEOReports.fxml","CEO Reports");
	}
	
	/**
	 * displays monthly reports window when pressing monthlyReports window
	 * @param event Type of action that occurred in the window by the user (when pressing a button in this scenario)
	 * @throws Exception
	 */
	public void MonthlyReportsBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/MonthlyReports.fxml","Monthly Reports");
	}
	
	/**
	 * display inventory reports window when pressing inventoryReports window
	 * @param event Type of action that occurred in the window by the user (when pressing a button in this scenario)
	 * @throws Exception
	 */
	public void InventoryReportsBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/InventoryReport.fxml","Inventory Report");
	}
	
	/**
	 * display user reports window when pressing userReports button
	 * @param event Type of action that occurred in the window by the user (when pressing a button in this scenario)
	 * @throws Exception
	 */
	public void UsersReportsBtn(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/UserReportPage.fxml","User Reports");
	}
	
	/**
	 * Loads desired window with the desired title
	 * @param event Type of action that occurred in the window by the user (when pressing a button in this scenario)
	 * @throws Exception
	 */
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
