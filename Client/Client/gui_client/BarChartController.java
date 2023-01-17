package gui_client;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import logic.Order;
import logic.Subscriber;
import logic.UsersReports;


/**
 * This class shows users report.
 *
 */
public class BarChartController implements Initializable{
	
	@FXML
	private BarChart<String, Integer> barChart;
	
	@FXML
	private PieChart pieChart;
	
	ObservableList<PieChart.Data> pieChartData;

	Message messageToServer = new Message(null,null);
	
	UserReportPageController usr;
	
	private int cnt0 = 0;
	private int cnt1 = 0;
	private int cnt2 = 0;
	private int cnt3 = 0;
	private int cnt4 = 0;
	private int cnt5 = 0;
	
	
	/**
	 * This method loads the pie chart data.
	 * @param location
	 * @param rb
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		
		messageToServer.setCommand(Command.ReadUserReports);
		messageToServer.setContent(0);
		ClientUI.chat.accept(messageToServer);
		
		int ordersCount = 0;
		String[] counters = null;
		XYChart.Series data0 = new XYChart.Series<>();
		XYChart.Series data1 = new XYChart.Series<>();
		XYChart.Series data2 = new XYChart.Series<>();
		XYChart.Series data3 = new XYChart.Series<>();
		XYChart.Series data4 = new XYChart.Series<>();
		XYChart.Series data5 = new XYChart.Series<>();
		data0.setName("0");
		data1.setName("1-5");
		data2.setName("6-10");
		data3.setName("11-15");
		data4.setName("16-20");
		data5.setName("21-25");
		
		for (UsersReports userReport : ChatClient.usersReport) {
			
			// add if condition to find the requested user report
			// might need to add another window
			if ((userReport.getMonth().equals(usr.month)) && (userReport.getYear().equals(usr.year)))
			{
				counters = userReport.getData().split(",");
			
				cnt0 = Integer.parseInt(counters[0]);
				cnt1 = Integer.parseInt(counters[1]);
				cnt2 = Integer.parseInt(counters[2]);
				cnt3 = Integer.parseInt(counters[3]);
				cnt4 = Integer.parseInt(counters[4]);
				cnt5 = Integer.parseInt(counters[5]);
			}
			
		}
		
		data0.getData().add(new XYChart.Data("", cnt0));
		data1.getData().add(new XYChart.Data("", cnt1));
		data2.getData().add(new XYChart.Data("", cnt2));
		data3.getData().add(new XYChart.Data("", cnt3));
		data4.getData().add(new XYChart.Data("", cnt4));
		data5.getData().add(new XYChart.Data("", cnt5));
		barChart.getData().addAll(data0,data1,data2,data3,data4,data5);
		
		ArrayList<PieChart.Data> data = new ArrayList<>();
		data.add(new PieChart.Data("not active users", cnt0));
		data.add(new PieChart.Data("active users", cnt1+cnt2+cnt3+cnt4+cnt5));
		pieChartData = FXCollections.observableArrayList(data);
		pieChart.getData().addAll(pieChartData);
	}
	
	/**
	 * This method hides the open window and shows a new one.
	 * 
	 * @param event
	 * @param path
	 * @param title
	 * @throws Exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/UserReportPage.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("User Report Type");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
	
}
