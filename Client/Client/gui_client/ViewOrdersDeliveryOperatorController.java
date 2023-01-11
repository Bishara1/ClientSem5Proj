package gui_client;

import java.net.URL;
import java.util.ArrayList;
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
import logic.Order;

public class ViewOrdersDeliveryOperatorController implements Initializable {
	private ObservableList<Order> allOrders;
	@FXML
	private TableView<Order> OrdersTable;
	@FXML
	private TableColumn<Order,String> orderNumberCol;
	@FXML
	private TableColumn<Order,String> customerIDCol;
	@FXML
	private TableColumn<Order,String> totalPriceCol;	
	@FXML
	private TableColumn<Order,String> locationCol;
	@FXML
	private TableColumn<Order,String> statusCol;
	@FXML
	private Button backBtn;
	@FXML
	private Label titlelbl;
	private ArrayList<Order> changedOrders = new ArrayList<Order>();;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.titlelbl.setText("Welcome " + ChatClient.Fname);
		ImportOrders();
		
		//allow status column to be editable
		statusCol.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	public void ImportOrders() {
		changedOrders.clear();
		GetRemoteOrders();
		DisplayOrders();
	}
	
	private void GetRemoteOrders() {
		ClientUI.chat.accept(new Message(0, Command.ReadOrders));
		allOrders = FXCollections.observableArrayList(ChatClient.orders);
	}
	
	public void DisplayOrders() {
		orderNumberCol.setCellValueFactory(new PropertyValueFactory<>("order_num"));
		customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("order_status"));
		
		OrdersTable.setItems(allOrders);
	}
	
	public void backButton(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ViewDelivery.fxml"));
		Scene scene = new Scene(root);
		
		String title = (ChatClient.role.equals("dlw")) ? "Deliverer" : "Delivery Operator";
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	@FXML
	public void ChangeStatus(TableColumn.CellEditEvent<Order, String> productStringCellEditEvent) {
		Order selectedOrder = OrdersTable.getSelectionModel().getSelectedItem();
		int index = OrdersTable.getSelectionModel().getSelectedIndex();
		
		if (ChatClient.role.equals("dlo")) {
			if (!productStringCellEditEvent.getOldValue().equals("Pending"))
				RaiseAlertConfirmation("Can only modify pending orders!");
			
			
			else if (productStringCellEditEvent.getNewValue().equals("Delivering") ||
					productStringCellEditEvent.getNewValue().equals("delivering")) {
				
				selectedOrder.setOrder_status("Delivering");
				allOrders.set(index, selectedOrder);
				
				if (!changedOrders.contains(selectedOrder))
					changedOrders.add(selectedOrder);
			}
			
			else 
				RaiseAlertConfirmation("Invalid Status! Modified status should be \"Delivering\".");
			
		}
		
		else if (ChatClient.role.equals("dlw")) {
			if (!productStringCellEditEvent.getOldValue().equals("Delivering"))
				RaiseAlertConfirmation("Can only modify orders in transit!");
			
			
			else if (productStringCellEditEvent.getNewValue().equals("Delivered") ||
					productStringCellEditEvent.getNewValue().equals("delivered")) {
				
				selectedOrder.setOrder_status("delivered");
				allOrders.set(index, selectedOrder);
				
				if (!changedOrders.contains(selectedOrder))
					changedOrders.add(selectedOrder);
			}
			
			else 
				RaiseAlertConfirmation("Invalid Status! Modified status should be \"Delivered\".");
		}
		
		OrdersTable.refresh();
	}
	
	public void UpdateOrders() {
		ClientUI.chat.accept(new Message(changedOrders, Command.UpdateOrders));
	}
	
	private void RaiseAlertConfirmation(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION, message,ButtonType.OK);
		alert.showAndWait();
	}
	
//	private ArrayList<Order> ConvertToArrayList() {
//		List<Order> updatedOrdersList = allOrders.stream().collect(Collectors.toList());
//		ArrayList<Order> updatedOrders = new ArrayList<>(updatedOrdersList);
//		
//		System.out.println(updatedOrders.get(0));
//		return updatedOrders;
//	}
}
