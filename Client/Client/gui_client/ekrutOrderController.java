package gui_client;

import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ekrutOrderController implements Initializable{

	@FXML
	private Label codeLbl1;
	@FXML
	private Label codeLbl2;
	@FXML
	private Label codeLbl3;
	@FXML
	private Label codeLbl4;
	@FXML
	private Label priceLbl1;
	@FXML
	private Label priceLbl2;
	@FXML
	private Label priceLbl3;
	@FXML
	private Label priceLbl4;
	@FXML
	private Label amountLbl1;
	@FXML
	private Label amountLbl2;
	@FXML
	private Label amountLbl3;
	@FXML
	private Label amountLbl4;
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
	
	private int MachineNumber = 1; //placeholder for the actual machine number
	
	private int rotation = 0;
	//ArrayList<Machine> machines are saved in ChatClient
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept(new Message(MachineNumber, Command.ReadMachines));
		codeLbl1.setText(ChatClient.machines.get(MachineNumber-1).getItem(rotation));
		codeLbl2.setText(ChatClient.machines.get(MachineNumber-1).getItem(rotation+1));
		codeLbl3.setText(ChatClient.machines.get(MachineNumber-1).getItem(rotation+2));
		codeLbl4.setText(ChatClient.machines.get(MachineNumber-1).getItem(rotation+3));
		
	}
	
	public void ProceedCartBtn() {
		
	}
	public void BackBtn() {
		
	}
    public void AddToCartBtn() {
		
	}
}
