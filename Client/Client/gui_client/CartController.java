package gui_client;




import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import logic.Item;


public class CartController {

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
	private TableView<Item> tableSub;
	@FXML
	private TableColumn<Item,String> productidcol;
	@FXML
	private TableColumn<Item,String> amountcol;
	@FXML
	private TableColumn<Item,String> pricecol;
	
	
	public void LoadAndSetTable() {
		productidcol.setCellValueFactory(new PropertyValueFactory<>("productID"));
		amountcol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));	
	}
	public void ProceedPayment() {
	}
	public void Back() {
	}
	
	public void RemoveItem() {
	}

    public void RemoveAll() {
    }
	}

