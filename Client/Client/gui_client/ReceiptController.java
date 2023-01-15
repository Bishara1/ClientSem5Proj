package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import gui_client.CartController.ItemTable;
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
import javafx.stage.Stage;
import logic.*;

public class ReceiptController implements Initializable {

	public class ItemTable {
		private String label;
		private Integer amount;
		private Integer priceAll;

		public ItemTable(String label, Integer amount, Integer price) {
			super();
			this.label = label;
			this.amount = amount;
			int NewPrice = amount * price;
			this.priceAll = NewPrice;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public Integer getPriceAll() {
			return priceAll;
		}

		public void setPriceAll(Integer priceAll) {
			this.priceAll = priceAll;
		}

	}

	@FXML
	private TableView<ItemTable> receiptTable;
	@FXML
	private TableColumn<ItemTable, String> productIdCol;
	@FXML
	private TableColumn<ItemTable, String> amountCol;
	@FXML
	private TableColumn<ItemTable, String> priceCol;
	@FXML
	private Label ordercodelbl;

	private ArrayList<ItemTable> tableCart = new ArrayList<ItemTable>();

	private ObservableList<ItemTable> obs;

	private int MachineNumber = -1;

	private int Threshold = 0;

	@FXML
	private Button okBtn;
	@FXML
	private Button backBtn;

	public void LoadAndSetTable() {
		productIdCol.setCellValueFactory(new PropertyValueFactory<>("Label"));
		amountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("PriceAll"));
		FindMachineNumber(ChatClient.machineToLoad);
		createItemTableCart();
		if(ChatClient.orderId != -1)
		{
			ordercodelbl.setText(ordercodelbl.getText() + " " + ChatClient.orderId);
		}
		else
		{
			ordercodelbl.setVisible(false);
		}
		Threshold = ChatClient.machines.get(MachineNumber).getThreshold();
		obs = FXCollections.observableArrayList(tableCart); // insert database details to obs
		receiptTable.setItems(obs); // load database colummns into table and display them
	}

	public void OKBtn(ActionEvent event) throws Exception {
		//Disconnect
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/LoginEkrut.fxml")); Stage
		primaryStage = new Stage(); Scene scene = new Scene(root);
		primaryStage.setTitle("Login"); primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	public void BackBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Purchase.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Cart");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoadAndSetTable();

	}

	public void createItemTableCart() {
		for (Item item : ChatClient.cart) {
			tableCart.add(new ItemTable(item.getProductID(), Integer.parseInt(item.getAmount()), item.getPrice()));
		}
	}

	public String CreateNewStock(ArrayList<Integer> available, ArrayList<String> availableItems) {

		int i, j = 0;
		int size = ChatClient.machines.get(MachineNumber).getItems().size();
		String newStock = "";
		for (i = 0; i < size; i++) {
			if (ChatClient.machines.get(MachineNumber).getItems().get(i).equals(availableItems.get(j))) {
				newStock += String.valueOf(available.get(j));
				if (i != size - 1)
					newStock += ",";
				j++;
			} else {
				newStock += "0";
				if (i != size - 1)
					newStock += ",";
			}
		}
		return newStock;
	}

	public int CreateNewInventory(String newStock) {
		String[] items = newStock.split(",");
		int inv = 0;
		for (String item : items) {
			inv += Integer.parseInt(item);
		}
		return inv;
	}

	public void FindMachineNumber(int id) {
		int size = ChatClient.machines.size();
		for (int i = 0; i < size; i++) {
			if (ChatClient.machines.get(i).getMachine_id() == id)
				MachineNumber = i;
		}
	}

	public int findItemIndex(String name) {
		int size = ChatClient.machines.get(MachineNumber).getItems().size();
		for (int i = 0; i < size; i++) {
			if (ChatClient.machines.get(MachineNumber).getItems().get(i).equals(name))
				return i;
		}
		return -1;
	}

	public int getPrice(String name) {
		for (Item item : ChatClient.items) {
			if (item.getProductID().equals(name))
				return item.getPrice();
		}
		return -1;
	}

	public int calculateTotalPrice() {
		int sum = 0;
		for (Item item : ChatClient.cart) {
			sum += item.getPrice() * Integer.parseInt(item.getAmount());
		}
		return sum;
	}

}
