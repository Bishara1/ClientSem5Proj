package gui_client;



import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Command;
import common.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ekrutOrderController implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button AddtoCartBtn;
	@FXML
	private Button ProcceedToCartBtn;
	@FXML
	private TextField ProductIdlbl;
	@FXML
	private TextField amountlbl;
	@FXML
	private TextField TotalPricelbl;
	
	public void ProceedCartBtn() {
		
	}
	public void BackBtn() {
		
	}
    public void AddToCartBtn() {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept(new Message(1,Command.ReadMachines));
		
	}
}
