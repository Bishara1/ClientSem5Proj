package gui_client;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import gui_client.StockTableController.ViewItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;
import logic.Order;
import logic.Subscriber;

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
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GetRemoteOrders();
		DisplayOrders();
	}
	
	private void GetRemoteOrders() {
		ClientUI.chat.accept(new Message(0, Command.ReadOrders));
		allOrders = FXCollections.observableArrayList(ChatClient.orders);
	}
	
	private void DisplayOrders() {
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
		
		primaryStage.setTitle("Delivery Operator");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
}
