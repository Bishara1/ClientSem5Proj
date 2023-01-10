package gui_client;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import logic.Order;
import logic.Subscriber;

public class BarChartController implements Initializable{
	
	@FXML
	private BarChart<String, Integer> barChart;

	Message messageToServer = new Message(null,null);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		
		messageToServer.setCommand(Command.ReadUsers);
		messageToServer.setContent(0);	
		ClientUI.chat.accept(messageToServer); 
		
		messageToServer.setCommand(Command.ReadOrders);
		messageToServer.setContent(0);	
		ClientUI.chat.accept(messageToServer); 
		
		int ordersCount = 0;
		
		XYChart.Series data = new XYChart.Series<>();
		data.setName("Orders made");
		for (Subscriber user : ChatClient.subscribers) {
			
			if(user.getRole().equals("customer"))
			{
				ordersCount = findUserOrdersCount(user.getId());
				data.getData().add(new XYChart.Data(user.getFname()+ " " + user.getLName() + "\n" + user.getId(), ordersCount));
			}
		}
		barChart.getData().addAll(data);
	}
	
	public int findUserOrdersCount(int id) {
		
		int cnt = 0;
		for (Order i : ChatClient.orders) {
			if (i.getCustomer_id() == id)
				cnt++;
		}
		return cnt;
	}

	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ChooseReportType.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Choose Report Type");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	
}
