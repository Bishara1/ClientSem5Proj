package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Item;
import logic.Subscriber;


public class CartController implements Initializable{

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
	private Button removebtn;
	@FXML
	private Button paymentbtn;
	@FXML
	private Button removeallbtn;
	@FXML
	private Button backbtn;
	@FXML
	private TextField removeidtxt;
	@FXML
	private TextField totalpricetxt;
	@FXML
	private TableView<ItemTable> table;
	@FXML
	private TableColumn<ItemTable,String> productidcol;
	@FXML
	private TableColumn<ItemTable,String> amountcol;
	@FXML
	private TableColumn<ItemTable,String> pricecol;

	private ArrayList<Item> cart;
	
	private ArrayList<ItemTable> tableCart = new ArrayList<ItemTable>();
	
	private ObservableList<ItemTable> obs;
	
	public void LoadAndSetTable() {
		cart = ChatClient.cart;
		productidcol.setCellValueFactory(new PropertyValueFactory<>("Label"));
		amountcol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
		pricecol.setCellValueFactory(new PropertyValueFactory<>("PriceAll"));	
		createItemTableCart();
		obs = FXCollections.observableArrayList(tableCart);  // insert database details to obs
		table.setItems(obs);  // load database colummns into table and display them
	}
	
	public void ProceedPayment(ActionEvent event) throws Exception {
		
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = null;
		if(ChatClient.isSubscriber == true)
		{
			root = FXMLLoader.load(getClass().getResource("/gui_client/Receipt.fxml"));
		}
		else
		{
			root = FXMLLoader.load(getClass().getResource("/gui_client/Purchase.fxml"));
		}
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	public void Back(ActionEvent event) throws Exception {
		ChatClient.cart = this.cart;
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ekrutOrder.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	public void RemoveItem() {
		
		try {
			ItemTable remove = table.getSelectionModel().getSelectedItem();
			int index = getItemIndex(remove.getLabel());
			if(index != -1) {
				cart.remove(index);
				tableCart.remove(index);
			}
			updateTotalPrice();
			LoadAndSetTable();
			Alert alert = new Alert(AlertType.CONFIRMATION,"Item removed successfully!",ButtonType.OK);
			alert.showAndWait();
				
		}catch(NullPointerException e) {
			
			Alert alert = new Alert(AlertType.ERROR,"Select an item to remove!",ButtonType.OK);
			alert.showAndWait();
		}
	}

    public void RemoveAll() {
    	
    	cart.removeAll(cart);
    	tableCart.removeAll(tableCart);
    	updateTotalPrice();
    	LoadAndSetTable();
    	Alert alert = new Alert(AlertType.CONFIRMATION,"Remove all items!",ButtonType.OK);
		alert.showAndWait(); //**************************************************************** 
		//add an alert that asks the customer if hes sure he wants to remove all items
    	
    }
    public void TotalPrice() {
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		LoadAndSetTable();
		updateTotalPrice();
	}
	
	 public void updateTotalPrice()
	 {
	    	int sum = 0;
	    	for(Item item : cart)
	    	{
	    		sum += item.getPrice() * Integer.parseInt(item.getAmount());
	    	}
	    	totalpricetxt.setText(String.valueOf(sum));
	    	return;
	 }
	 
	 public int getPrice(String name)
	 {
	    	for(Item item : ChatClient.items)
	    	{
	    		if(item.getProductID().equals(name))
	    			return item.getPrice();
	    	}
	    	return -1; 
	 }
	 
	 public int getItemIndex(String name)
	 {
		 int size = cart.size();
		 for(int i =0;i<size;i++)
		 {
			 if(cart.get(i).getProductID().equals(name))
				 return i;
		 }
		 return -1;
	 }
	 
	 public void createItemTableCart()
	 {
		 for(Item item : cart)
		 {
			 tableCart.add(new ItemTable(item.getProductID(),Integer.parseInt(item.getAmount()),item.getPrice()));
		 }
	 }
}

