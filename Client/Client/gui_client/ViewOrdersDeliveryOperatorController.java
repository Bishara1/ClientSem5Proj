package gui_client;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import logic.Delivery;

public class ViewOrdersDeliveryOperatorController implements Initializable {
	private ObservableList<Delivery> allDeliveries;
	private ArrayList<Delivery> changedDeliveries = new ArrayList<>();
	@FXML
	private TableView<Delivery> DeliveriesTable;
	@FXML
	private TableColumn<Delivery,String> orderNumberCol;
	@FXML
	private TableColumn<Delivery,String> customerIDCol;
	@FXML
	private TableColumn<Delivery,String> totalPriceCol;	
	@FXML
	private TableColumn<Delivery,String> locationCol;
	@FXML
	private TableColumn<Delivery,String> statusCol;
	@FXML
	private Button logOutBtn;
	@FXML
	private Label titlelbl;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.titlelbl.setText("Welcome " + ChatClient.Fname);
		ImportDeliveries();
		
		//allow status column to be editable
		statusCol.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public void ImportDeliveries() {
		changedDeliveries.clear();
		GetRemoteDeliveries();
		DisplayDeliveries();
		DeliveriesTable.refresh();
	}
	
	private void GetRemoteDeliveries() {
		ClientUI.chat.accept(new Message(0, Command.ReadDeliveries));
		System.out.println(ChatClient.deliveries);
		allDeliveries = FXCollections.observableArrayList(ChatClient.deliveries);
	}
	
	public void DisplayDeliveries() {
		orderNumberCol.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("total_price"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		DeliveriesTable.setItems(allDeliveries);
	}
	
	public void logOutButton(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Scene scene = new Scene(root);
		//disconnect
		
		String title = (ChatClient.role.equals("dlw")) ? "Deliverer" : "Delivery Operator";
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	@FXML
	public void ChangeStatus(TableColumn.CellEditEvent<Delivery, String> productStringCellEditEvent) {
		Delivery selectedDelivery = DeliveriesTable.getSelectionModel().getSelectedItem();
		int index = DeliveriesTable.getSelectionModel().getSelectedIndex();
		String oldVal = productStringCellEditEvent.getOldValue();
		String newVal= productStringCellEditEvent.getNewValue();
		
		if (ChatClient.role.equals("dlo")) {
			if (!oldVal.equals("Pending") && !oldVal.equals("Delivered"))
				RaiseAlertConfirmation("Can only modify delivered or pending deliveries!");
			
			else if (newVal.equals("Delivering") || newVal.equals("delivering")) {
				if (oldVal.equals("Pending"))
					addToChangedList(selectedDelivery, index, "Delivering");
			}
			
			else if (newVal.equals("Completed") || newVal.equals("completed")) {
				if (oldVal.equals("Delivered")) 
					addToChangedList(selectedDelivery, index, "Completed");
			}
			
			else 
				RaiseAlertConfirmation("Invalid Status! Modified status should be \"Delivering\" or \"Completed\".");
		}
		
		else if (ChatClient.role.equals("dlw")) {			
			if (!oldVal.equals("Delivering"))
				RaiseAlertConfirmation("Can only modify deliveries in transit!");
			
			else if (newVal.equals("Delivered") || newVal.equals("delivered"))
				addToChangedList(selectedDelivery, index, "Delivered");
				
			else 
				RaiseAlertConfirmation("Invalid Status! Modified status should be \"Delivered\".");
		}
		
		DeliveriesTable.refresh();
	}
	
	private void addToChangedList(Delivery selectedDelivery, int index, String status) {
		selectedDelivery.setStatus(status);
		allDeliveries.set(index, selectedDelivery);
		
		if (!changedDeliveries.contains(selectedDelivery))
			changedDeliveries.add(selectedDelivery);
	}
	
	public void UpdateDeliveries() {
		String  estimatedDelivery;
		int size = changedDeliveries.size();
		String[] dataForUpdate = new String[size+1];
		
		dataForUpdate[0] = "delivery";
		for (int i = 1; i < dataForUpdate.length; i++) {
			if (ChatClient.role.equals("dlo") && changedDeliveries.get(i-1).getStatus().equals("Delivering")) {
				estimatedDelivery = addDaysToDate(changedDeliveries.get(i-1).getShipping_date().toString());
			}
			
			else {
				estimatedDelivery = changedDeliveries.get(i-1).getShipping_date().toString();
			}
			
			dataForUpdate[i] = changedDeliveries.get(i-1).getOrder_id() + " " + changedDeliveries.get(i-1).getStatus() + " " + estimatedDelivery;
			System.out.println(dataForUpdate[i]);
		}
		ClientUI.chat.accept(new Message(dataForUpdate, Command.UpdateDeliveries));
		
		dataForUpdate[0] = "orders";
		for (int i = 1; i < dataForUpdate.length; i++) {
			dataForUpdate[i] = changedDeliveries.get(i-1).getOrder_id() + " " + changedDeliveries.get(i-1).getStatus();
			System.out.println(dataForUpdate[i]);
		}
		ClientUI.chat.accept(new Message(dataForUpdate, Command.UpdateOrders));
	}
	
	private String addDaysToDate(String date) {
		String dateAfter;
		SimpleDateFormat dateBefore = new SimpleDateFormat("YYYY-MM-DD");
		Calendar cal = Calendar.getInstance();
	    try{  
	           cal.setTime(dateBefore.parse(date));  
	    } catch(ParseException e) { e.printStackTrace(); }  
	             
	    cal.add(Calendar.DAY_OF_MONTH, 14);  
	    dateAfter = dateBefore.format(cal.getTime());
	    return dateAfter;
	}

	private void RaiseAlertConfirmation(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.OK);
		alert.showAndWait();
	}	
}
