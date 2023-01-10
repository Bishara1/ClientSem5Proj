package gui_client;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.*;

public class ReceiptController implements Initializable{
	
	public class ItemTable{
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
	private TableColumn<ItemTable,String> productIdCol;
	@FXML
	private TableColumn<ItemTable,String> amountCol;
	@FXML
	private TableColumn<ItemTable,String> priceCol;
	
	private ArrayList<ItemTable> tableCart = new ArrayList<ItemTable>();

	private ObservableList<ItemTable> obs;
	
	private int MachineNumber = -1;

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
		obs = FXCollections.observableArrayList(tableCart);  // insert database details to obs
		receiptTable.setItems(obs);  // load database colummns into table and display them
	}
	
	public void OKBtn(ActionEvent event) throws Exception {
		//check if theres an item that went under the specified threshold and send a request to deal with it -> stock manager
		int size = ChatClient.machines.get(MachineNumber).getAmountItems().size();
		ArrayList<Integer> itemAmount = ChatClient.machines.get(MachineNumber).getAmountItems();
		String itemsToFill = "";
		for(int i =0;i<size;i++)
		{
			if(itemAmount.get(i) == 0)
			{
				itemsToFill += ChatClient.machines.get(MachineNumber).getItem(i);
				if(i != size-1)
					itemsToFill += ",";
			}
		}
		if(!itemsToFill.isEmpty()) //send request to fill machine by threshold
		{
			//send machine_id and itemsToFill to stockrequest?
		}
		((Node)event.getSource()).getScene().getWindow().hide();
		Alert alert = new Alert(AlertType.CONFIRMATION,"Order added!",ButtonType.OK);
		alert.showAndWait();
		ArrayList<String> data = new ArrayList<String>();
		data.add(String.valueOf(ChatClient.ID));
		data.add(ChatClient.locationName);
		String items = "";
		int price = 0;
		for(int i =0;i<ChatClient.cart.size();i++)
		{
			items += ChatClient.cart.get(i).getProductID();
			items += ",";
			items += ChatClient.cart.get(i).getAmount();
			items += ".";
			price += ChatClient.cart.get(i).getPrice()* Integer.parseInt(ChatClient.cart.get(i).getAmount());
		}
		data.add(items);
		data.add(String.valueOf(price));
		data.add(ChatClient.supplyMethod);
		data.add(String.valueOf(ChatClient.machineToLoad));
		String newStock = CreateNewStock(ChatClient.available);
		data.add(newStock);
		Message msg = new Message(data,Command.InsertOrder);
		ClientUI.chat.accept(msg);
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/Purchase.fxml"));
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
	
	public void createItemTableCart()
	 {
		 for(Item item : ChatClient.cart)
		 {
			 tableCart.add(new ItemTable(item.getProductID(),Integer.parseInt(item.getAmount()),item.getPrice()));
		 }
	 }
	
	public String CreateNewStock(ArrayList<Integer> arr)
	{
		int i;
		int size = arr.size();
		String newStock = "";
		for(i=0;i<size;i++)
		{
			newStock += String.valueOf(arr.get(i));
			if(i != size-1)
				newStock += ",";
		}
		return newStock;
	}

	 public void FindMachineNumber(int id)
	  {
	    	int size = ChatClient.machines.size();
			for(int i = 0;i<size;i++)
			{
				if(ChatClient.machines.get(i).getMachine_id() == id)
					MachineNumber = i;
			}
	  }
	    
}
