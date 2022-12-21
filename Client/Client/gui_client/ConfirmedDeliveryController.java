package gui_client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logic.Delivery;

public class ConfirmedDeliveryController {

	
	@FXML
	private Button confirmBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TableView<Delivery> tableDelivery;
	@FXML
	private TableColumn<Delivery,String> orderIdcol;
	@FXML
	private TableColumn<Delivery,String> shippingDatecol;
	@FXML
	private TableColumn<Delivery,String> estimatedcol;
	@FXML
	private TableColumn<Delivery,String> statuscol;
	@FXML
	private TableColumn<Delivery,String> deliveryIdcol;
	
	
	public void Confirm() {
	}

    public void Back() {
    }
	
}
