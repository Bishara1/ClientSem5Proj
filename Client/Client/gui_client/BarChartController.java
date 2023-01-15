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
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import logic.Order;
import logic.Subscriber;

public class BarChartController implements Initializable{
	
	@FXML
	private BarChart<String, Integer> barChart;

	Message messageToServer = new Message(null,null);
	private int cnt1 = 0;
	private int cnt2 = 0;
	private int cnt3 = 0;
	private int cnt4 = 0;
	private int cnt5 = 0;
	
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
		XYChart.Series data1 = new XYChart.Series<>();
		XYChart.Series data2 = new XYChart.Series<>();
		XYChart.Series data3 = new XYChart.Series<>();
		XYChart.Series data4 = new XYChart.Series<>();
		data.setName("0-5");
		data1.setName("6-10");
		data2.setName("11-15");
		data3.setName("16-20");
		data4.setName("21-25");
		
		// loop to go over subscribers/users and gather the data
		for (Subscriber user : ChatClient.subscribers) {
			
			if(user.getRole().equals("customer"))
			{
				ordersCount = findUserOrdersCount(user.getId());
				
				if ((ordersCount >= 0) && (ordersCount <= 5))
					cnt1++;
				if ((ordersCount > 5) && (ordersCount <= 10))
					cnt2++;
				if ((ordersCount > 10) && (ordersCount <= 15))
					cnt3++;
				if ((ordersCount > 15) && (ordersCount <= 20))
					cnt4++;
				if ((ordersCount > 20) && (ordersCount <= 25))
					cnt5++;
			}
		}
		
		data.getData().add(new XYChart.Data("", cnt1));
		data1.getData().add(new XYChart.Data("", cnt2));
		data2.getData().add(new XYChart.Data("", cnt3));
		data3.getData().add(new XYChart.Data("", cnt4));
		data4.getData().add(new XYChart.Data("", cnt5));
		barChart.getData().addAll(data,data1,data2,data3,data4);
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
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/ChooseReportType.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Choose Report Type");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	
}
