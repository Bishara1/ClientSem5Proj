package gui_client;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import logic.Items;

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
	private TextField removeidlbl;
	
	@FXML
	private TableView<Items> tableSub;
	@FXML
	private TableColumn<Items,String> productidcol;
	@FXML
	private TableColumn<Items,String> amountcol;
	@FXML
	private TableColumn<Items,String> pricecol;
	
}
