package gui_client;

import java.net.URL;
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
	
	@FXML
	private Button showStockBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button backBtn;
	
	@FXML
	private TextField machineCodetxt;
	
	private ObservableList<Machine> obs;
	@FXML
	private TableView<Machine> machineTable;
	@FXML
	private TableColumn<Machine,String> itemsCol;
	@FXML
	private TableColumn<Machine,String> amountCol;
	
	public void ShowStockBtn(ActionEvent event) throws Exception{
		
		String machineCode = machineCodetxt.getText();
		
		if(machineCode.equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR,"Must enter machine id !",ButtonType.OK);
			alert.showAndWait();
		}
		else {
			Message msg = new Message(machineCode, Command.ReadMachines); //connects client to server
			ClientUI.chat.accept(msg);
			
			//Check if machine id exists in DB
			// if it does exist : load machine
			LoadMachine();
			
		}
	}
	
	public void LoadMachine() {	//probably very extremly wrong code but wth
		// GETTERS AND SETTERS ARE MISSING FOR ITEMS AND AMOUNT
		// ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
		
		itemsCol.setCellValueFactory(new PropertyValueFactory<>("total_inventory"));
		amountCol.setCellValueFactory(new PropertyValueFactory<>("machine_code"));
//		itemsCol.setCellValueFactory(new PropertyValueFactory<>("items"));
//		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
		machineTable.setItems(obs);
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

//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		messageToServer.setCommand(Command.DatabaseRead);
//		messageToServer.setContent(null);
//		ClientUI.chat.accept(messageToServer);  // read from database
//		obs = FXCollections.observableArrayList();  // insert database details to obs
//		LoadMachine(); // load database colummns into table and display them
//		
//	}
}
