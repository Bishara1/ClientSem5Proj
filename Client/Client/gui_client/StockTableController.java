package gui_client;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;

import common.Command;
import common.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Item;
import logic.Machine;
import logic.Subscriber;

public class StockTableController {

	Message messageToServer = new Message(null, null);
	private static String machineCode;
	
	@FXML
	private Button showStockBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button backBtn;
	
	@FXML
	private TextField machineCodetxt;
	
	private ObservableList<ViewItem> obs;
	@FXML
	private TableView<ViewItem> machineTable;
	@FXML
	private TableColumn<ViewItem,String> itemsCol;
	@FXML
	private TableColumn<ViewItem,String> amountCol;
	
	
	public void ShowStockBtn(ActionEvent event) throws Exception{
		
		machineCode = machineCodetxt.getText();
		
		if(machineCode.equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR,"Must enter machine id !",ButtonType.OK);
			alert.showAndWait();
		}
		// ASK AVI IF MACHINE ID CAN CONTAIN LETTERS
//		else if(machineCode.)
//		{
//			Alert alert = new Alert(AlertType.ERROR,"Must enter machine id !",ButtonType.OK);
//			alert.showAndWait();
//		}
		else {
			ConnectNewClient();
			LoadMachine();
		}
	}
	
	public void ConnectNewClient() {
		ClientUI.chat = new ClientController("localhost", 5555);  // new client connected
		///ClientUI.chat.accept("login"); // send to server that a client is connected
	}
	
	public void LoadMachine() {
				
		messageToServer.setCommand(Command.ReadMachines);
		messageToServer.setContent(Integer.parseInt(machineCode));	// machine id
		ClientUI.chat.accept(messageToServer); 
		itemsCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		amountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
		ArrayList<ViewItem> items = new ArrayList<>();
<<<<<<< HEAD
=======

>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj

		if (ChatClient.machines.contains(null))
		{	
			Alert alert = new Alert(AlertType.ERROR,"Machine Id does not exist !",ButtonType.OK);
<<<<<<< HEAD
			alert.showAndWait();
=======
			alert.showAndWait();
>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
		}
		else {
			Machine temp = ChatClient.machines.get(Integer.parseInt(machineCode)-1); //must check that 
			int size = temp.getItems().size();
			for(int i = 0;i<size;i++)
			{
				items.add(new ViewItem(temp.getItems().get(i),temp.getAmountItems().get(i).toString()));
			}
			obs = FXCollections.observableArrayList(items);
			machineTable.setItems(obs);
		}
	}
	
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UpdateStock.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Update Stock");
		primaryStage.setScene(scene);		
		primaryStage.show();		
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
}
