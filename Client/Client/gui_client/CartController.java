package gui_client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
	
	private ArrayList<ItemTable> tableCart;
	
	private ObservableList<ItemTable> obs;
	
	private int Threshold = 0;
	
	private int MachineNumber = -1;
	
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
		
		if(!cart.isEmpty())
		{
			Parent root = null;
			if(ChatClient.isSubscriber == true)
			{
				Message msg = new Message(null,null);
				msg.setContent(ChatClient.ID);
				msg.setCommand(Command.ReadUserVisa);
				ClientUI.chat.accept(msg);
				Optional<ButtonType> result = InsertOrder(event);
				if(result.get() == ButtonType.YES) {
					((Node)event.getSource()).getScene().getWindow().hide();
					root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Receipt.fxml"));
					Stage primaryStage = new Stage();
					Scene scene = new Scene(root);
					primaryStage.setTitle("EKRUT");
					primaryStage.setScene(scene);		
					primaryStage.show();
				}
			}
			else
			{
				((Node)event.getSource()).getScene().getWindow().hide();
				root = FXMLLoader.load(getClass().getResource("/gui_client_windows/Purchase.fxml"));
				Stage primaryStage = new Stage();
				Scene scene = new Scene(root);
				primaryStage.setTitle("EKRUT");
				primaryStage.setScene(scene);		
				primaryStage.show();
			}
			
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR,"Must have items in cart to purchase",ButtonType.OK);
			alert.showAndWait();
		}
	}
	public void Back(ActionEvent event) throws Exception {
		ChatClient.cart = this.cart;
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/ekrutOrder.fxml"));
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
		FindMachineNumber(ChatClient.machineToLoad);
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
		 tableCart = new ArrayList<ItemTable>();
		 for(Item item : cart)
		 {
			 tableCart.add(new ItemTable(item.getProductID(),Integer.parseInt(item.getAmount()),item.getPrice()));
		 }
	 }
	 
	 public Optional<ButtonType> InsertOrder(ActionEvent event) throws IOException
		{
			Alert alert = new Alert(AlertType.WARNING,"Are you sure you want to continue?",ButtonType.NO,ButtonType.YES);
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.YES)
			{
				int size = ChatClient.availableItems.size();
				ArrayList<Integer> itemAmount = ChatClient.available;
				String itemsToFill = "";
				for(int i =0;i<size;i++)
				{
					if(itemAmount.get(i) <= Threshold)
					{
						if(i!= 0)
							itemsToFill += ",";
						itemsToFill += ChatClient.availableItems.get(i);
					}
				}
				if(!itemsToFill.isEmpty()) //send request to fill machine by threshold
				{
					ClientUI.chat.accept(new Message(ChatClient.machineToLoad,Command.ReadStockRequests));
					if(ChatClient.stockRequests.isEmpty())
					{
						ArrayList<String> data = new ArrayList<String>();
						data.add(String.valueOf(ChatClient.machineToLoad));
						ClientUI.chat.accept(new Message(data,Command.InsertStockRequest));
					}
					
				}
				((Node)event.getSource()).getScene().getWindow().hide();
				Alert alert1 = new Alert(AlertType.CONFIRMATION,"Order added!",ButtonType.OK);
				alert1.showAndWait();
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
				data.add(ChatClient.creditcard);
				data.add(String.valueOf(price));
				data.add(ChatClient.supplyMethod);
				data.add(String.valueOf(ChatClient.machineToLoad));
				String newStock = CreateNewStock(ChatClient.available,ChatClient.availableItems);
				data.add(newStock);
				int totalInventory = CreateNewInventory(newStock);
				data.add(String.valueOf(totalInventory));
				Message msg = new Message(data,Command.InsertOrder);
				ClientUI.chat.accept(msg);
				if(ChatClient.supplyMethod == "Delivery")
				{
					data = new ArrayList<String>();
					ClientUI.chat.accept(new Message(0,Command.ReadOrders));
					size = ChatClient.orders.size();
					data.add(String.valueOf(ChatClient.orders.get(size-1).getOrder_num()));
					data.add(String.valueOf(ChatClient.ID));
					data.add(String.valueOf(calculateTotalPrice()));
					data.add(ChatClient.deliveryLocation);
					data.add(ChatClient.address);
					ClientUI.chat.accept(new Message(data,Command.InsertDelivery));
				}
				if(ChatClient.FirstSubscriberOrder == true)
				{
					msg = new Message(null,null);
					ArrayList<String> param = new ArrayList<String>();
					param.add("usersFirstCart");
					param.add(String.valueOf(ChatClient.ID));
					param.add("0");
					msg.setCommand(Command.UpdateUserFirstCart);
					msg.setContent(param);
					ClientUI.chat.accept(msg);
					ChatClient.FirstSubscriberOrder = false;
				}
				//Disconnect
			}
			return result;
		}
		
		public int calculateTotalPrice()
		 {
		    	int sum = 0;
		    	for(Item item : ChatClient.cart)
		    	{
		    		sum += item.getPrice() * Integer.parseInt(item.getAmount());
		    	}
		    	return sum;
		 }
		
		public String CreateNewStock(ArrayList<Integer> available,ArrayList<String> availableItems)
		{
			
			int i,j =0;
			int size = ChatClient.machines.get(MachineNumber).getItems().size();
			String newStock = "";
			for(i=0;i<size;i++)
			{
				if(ChatClient.machines.get(MachineNumber).getItems().get(i).equals(availableItems.get(j)))
				{
					newStock += String.valueOf(available.get(j));
					if(i != size-1)
						newStock += ",";
					j++;
				}
				else
				{
					newStock += "0";
					if(i != size-1)
						newStock += ",";
				}
			}
			return newStock;
		}
		
		public int CreateNewInventory(String newStock)
		{
			String[] items = newStock.split(",");
			int inv = 0;
			for(String item : items)
			{
				inv += Integer.parseInt(item);
			}
			return inv;
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

