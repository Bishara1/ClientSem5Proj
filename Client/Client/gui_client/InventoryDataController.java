package gui_client;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InventoryDataController implements Initializable{
	
	private ObservableList<ViewItem> obs;
	Message messageToServer = new Message(null, null);
	
	@FXML
	private PieChart pieChart;
	
	@FXML
	private TableView<ViewItem> tableSub;
	@FXML
	private TableColumn<ViewItem,String> namecol;
	@FXML
	private TableColumn<ViewItem,String> amountcol;
	
	ArrayList<ViewItem> items = new ArrayList<>();
	ArrayList<PieChart.Data> data = new ArrayList<>();
	
	ObservableList<PieChart.Data> pieChartData;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadAndSetData();
	}
	
	public void loadAndSetData() {
		
		splitItems(InventoryReportController.str);
		
		namecol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		amountcol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
		
		tableSub.setItems(obs);
	}
	
	public void splitItems(String string)
	 {
		 String[] dotSplit = string.split("\\.");
		 String[] finalSplit = null;
		 
		 for (String s : dotSplit) {
			finalSplit = s.split(",");
			
			items.add(new ViewItem(finalSplit[0], finalSplit[1]));
			data.add(new PieChart.Data(finalSplit[0], Integer.parseInt(finalSplit[1])));
			
		}
		 
		pieChartData = FXCollections.observableArrayList(data);
		pieChart.getData().addAll(pieChartData);
		obs = FXCollections.observableArrayList(items);
		
	 }
	
	public class ViewItem
	{
		private String Name;
		private String Amount;
		
		public String getName() {
			return Name;
		}
		
		public String getAmount() {
			return Amount;
		}
		
		public ViewItem(String name,String amount) {
			this.Name = name;
			this.Amount = amount;
		}
	}

	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/InventoryReport.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Inventory Report");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	

}
