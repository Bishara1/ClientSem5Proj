package gui_client;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.InventoryReports;
import logic.Item;

public class InventoryDataController implements Initializable{
	
	private ObservableList<ViewItem> obs;
	Message messageToServer = new Message(null, null);
	
	@FXML
	private PieChart pieChart;
	
	@FXML
	private Label dateLbl;
	@FXML
	private Label locationLbl;
	@FXML
	private Label monthYearLbl;
	@FXML
	private Label stockLbl;
	@FXML
	private Label machineIdLbl;
	
	@FXML
	private TableView<ViewItem> tableSub;
	@FXML
	private TableColumn<ViewItem,String> nameCol;
	@FXML
	private TableColumn<ViewItem,String> unitPriceCol;
	@FXML
	private TableColumn<ViewItem,String> inventoryCol;
	@FXML
	private TableColumn<ViewItem,String> stockCol;
	@FXML
	private TableColumn<ViewItem,String> valueCol;
	
	ArrayList<ViewItem> items = new ArrayList<>();
	ArrayList<PieChart.Data> data = new ArrayList<>();
	
	ObservableList<PieChart.Data> pieChartData;
	
	InventoryReports report = InventoryReportController.requestedInventoryReport;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.dateLbl.setText(String.valueOf(LocalDate.now()));
		this.locationLbl.setText(report.getLocation());
		this.monthYearLbl.setText(report.getMonth()+"/"+report.getYear());
		this.machineIdLbl.setText("machine id : "+report.getMachine_id());
		loadAndSetData();
	}
	
	public void loadAndSetData() {
		
		messageToServer.setCommand(Command.ReadItems);
		messageToServer.setContent(0);	
		ClientUI.chat.accept(messageToServer); 
		splitItems(report.getStock());
		
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		inventoryCol.setCellValueFactory(new PropertyValueFactory<>("inventory"));
		stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
		valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
		
		tableSub.setItems(obs);
	}
	
	
	// calculates the current stock and initializes the pie chart
	public void splitItems(String string)
	 {
		 String[] dotSplit = string.split("\\.");
		 String[] finalSplit = null;
		 int price;
		 int val;
		 for (String s : dotSplit) {
			finalSplit = s.split(",");
			price = getUnitPriceColumn(finalSplit[0]);
			val = price*1;
			items.add(new ViewItem(finalSplit[0], String.valueOf(price), "safwan", finalSplit[1] , String.valueOf(val)));
			data.add(new PieChart.Data(finalSplit[0], Integer.parseInt(finalSplit[1])));
			
		}
		 
		pieChartData = FXCollections.observableArrayList(data);
		pieChart.getData().addAll(pieChartData);
		obs = FXCollections.observableArrayList(items);
		
	 }
	
	public int getUnitPriceColumn(String name) {
		
		for (Item item : ChatClient.items)
			if (name.equals(item.getProductID()))
				return item.getPrice();
		return 0;
	}
	
	public class ViewItem
	{
		private String name;
		private String unitPrice;
		private String inventory;
		private String stock;
		private String value;
		
		public String getName() {
			return name;
		}
		
		public String getUnitPrice() {
			return unitPrice;
		}
		
		public String getInventory() {
			return inventory;
		}
		
		public String getStock() {
			return stock;
		}
		
		public String getValue() {
			return value;
		}
		
		public ViewItem(String name,String unitPrice,String inventory,String stock,String value) {
			this.name = name;
			this.unitPrice = unitPrice;
			this.inventory = inventory;
			this.stock = stock;
			this.value = value;
		}
	}

	public void BackBtn(ActionEvent event) throws Exception
	{
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/InventoryReport.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Inventory Report");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	

}
