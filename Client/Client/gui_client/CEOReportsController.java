package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class CEOReportsController {
	@FXML
	private Button viewMachinesBtn;
	@FXML
	private Button monthlyReportBtn;
	@FXML
	private Button viewSubscribersBtn;
	@FXML
	private Button logOutBtn;
	@FXML
	private Button viewdiscountBtn;
	
	
	public void ViewMachines(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/StockTable.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
		primaryStage.setTitle("Stock Table");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	
	public void ViewSubscribers(ActionEvent event) throws Exception  {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/SubscribersViewer.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Subscriber Viewer");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	public void viewDiscount(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/DiscountLocation.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Discount Location");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	public void MonthlyReports(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/MonthlyReports.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Monthly Reports");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	
	public void LogOut(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
}
