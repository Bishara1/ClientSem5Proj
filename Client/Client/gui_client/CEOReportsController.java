package gui_client;

import java.net.URL;
import java.util.ResourceBundle;
import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class CEOReportsController implements Initializable {
	
	@FXML
	private Label titleLbl;
	@FXML
	private Button viewMachinesBtn;
	@FXML
	private Button monthlyReportBtn;
	@FXML
	private Button inventoryReportBtn;
	@FXML
	private Button viewSubscribersBtn;
	@FXML
	private Button logOutBtn;
	@FXML
	private Button viewdiscountBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.titleLbl.setText("Welcome CEO " + ChatClient.Fname);
	}
	
	public void ViewMachines(ActionEvent event) throws Exception{
		nextWindow(event,"/gui_client_windows/StockTable.fxml","Stock Table");
	}

	public void ViewSubscribers(ActionEvent event) throws Exception  {
		nextWindow(event,"/gui_client_windows/SubscribersViewer.fxml","Subscriber Viewer");
	}
	
	public void viewDiscount(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/DiscountLocation.fxml","Discount Location");
	}
	
	public void ViewReports(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/ChooseReportType.fxml","Choose Report Type");
	}

	public void LogOut(ActionEvent event) throws Exception {
		nextWindow(event,"/gui_client_windows/LoginEkrut.fxml","Login Ekrut");
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
