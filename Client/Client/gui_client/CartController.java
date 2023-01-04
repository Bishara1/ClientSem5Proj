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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Item;
import logic.Subscriber;


public class CartController implements Initializable{

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
	private TableView<Item> table;
	@FXML
	private TableColumn<Item,String> productidcol;
	@FXML
	private TableColumn<Item,String> amountcol;
	@FXML
	private TableColumn<Item,String> pricecol;

	private ArrayList<Item> cart;
	
	private ObservableList<Item> obs;
	
	public void LoadAndSetTable() {
		cart = ChatClient.cart;
		System.out.println(cart);
		productidcol.setCellValueFactory(new PropertyValueFactory<>("productID"));
		amountcol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));	
		obs = FXCollections.observableArrayList(cart);  // insert database details to obs
		System.out.println(obs);
		table.setItems(obs);  // load database colummns into table and display them
	}
	public void ProceedPayment() {
	}
	public void Back(ActionEvent event) throws Exception {
		//ChatClient.cart = this.cart;
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ekrutOrder.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	public void RemoveItem() {
	}

    public void RemoveAll() {
    }
    public void TotalPrice() {
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		LoadAndSetTable();
	}
}

