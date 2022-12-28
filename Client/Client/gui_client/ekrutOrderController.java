package gui_client;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.Item;

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
	
	private int rotation;
	//ArrayList<Machine> machines are saved in ChatClient
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientUI.chat.accept(new Message(MachineNumber, Command.ReadMachines));
		ClientUI.chat.accept(new Message(0,Command.ReadItems));
		rotation = 0;
		LoadItems();
		
	}
	
	public void NextItems()
	{
		if((rotation+1)*4 >= ChatClient.machines.get(MachineNumber-1).getItems().size())
			rotation=0;
		else
			rotation += 1;
		System.out.println(rotation + "rotation");
		LoadItems(); //deal with only showing 2 
	}
	
	public void ProceedCartBtn() {
		
	}
	public void BackBtn() {
		
	}
    public void AddToCartBtn() {
		
	}
    
    public void LoadItems()
    {
    	CheckAndLoadItem(rotation*4,codeLbl1,"Item");
    	CheckAndLoadItem(rotation*4+1,codeLbl2,"Item");
    	CheckAndLoadItem(rotation*4+2,codeLbl3,"Item");
    	CheckAndLoadItem(rotation*4+3,codeLbl4,"Item");
    	CheckAndLoadItem(rotation*4,priceLbl1,"Price");
    	CheckAndLoadItem(rotation*4+1,priceLbl2,"Price");
    	CheckAndLoadItem(rotation*4+2,priceLbl3,"Price");
    	CheckAndLoadItem(rotation*4+3,priceLbl4,"Price");
    	CheckAndLoadItem(rotation*4,amountLbl1,"Amount");
    	CheckAndLoadItem(rotation*4+1,amountLbl2,"Amount");
    	CheckAndLoadItem(rotation*4+2,amountLbl3,"Amount");
    	CheckAndLoadItem(rotation*4+3,amountLbl4,"Amount");
    }
    
    public void CheckAndLoadItem(int num,Label lbl,String str) //num = rotation*4 + i
    {
    	if(num > ChatClient.machines.get(MachineNumber-1).getItems().size()-1)
    		lbl.setText(" ");
    	else
    	{
    		switch(str) {
    			case "Item":
    				lbl.setText(ChatClient.machines.get(MachineNumber-1).getItem(num));
    				break;
    			case "Price":
    				lbl.setText(this.getPrice(ChatClient.machines.get(MachineNumber-1).getItem(num)) + " NIS");
    				break;
    			case "Amount":
    				lbl.setText(String.valueOf(ChatClient.machines.get(MachineNumber-1).getAmount(num)));
    				break;
    		}
    	}
    	
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
}
