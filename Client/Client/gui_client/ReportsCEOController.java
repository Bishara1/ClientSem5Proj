package gui_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Command;
import common.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ReportsCEOController implements Initializable{
	
	@FXML
	private Button backBtn;
	
	@FXML
	private PieChart pieChart;
	
	ObservableList<PieChart.Data> pieChartData;
	MonthlyReportsController mpc;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setPieChart();
	}
	
	public void setPieChart() {
		String s = null;

		for (int j = 0; j < ChatClient.orderReport.size(); j++)
		{	if (ChatClient.orderReport.get(j).getYear().equals(mpc.year.toString()) &&
					ChatClient.orderReport.get(j).getMonth().equals(mpc.month.toString()) &&
						ChatClient.orderReport.get(j).getMachine_id().equals(mpc.machineId.toString()))
							{
								s = ChatClient.orderReport.get(j).getData();
							}
		}
			ArrayList<PieChart.Data> data = new ArrayList<>();
			String[] d = s.split("\\.");
			String[] splitFinal = null;
			
			for (int i = 0; i < d.length; i++) {
				splitFinal = d[i].split(",");
				data.add(new PieChart.Data(splitFinal[0], Integer.parseInt(splitFinal[1])));
			}
			pieChartData = FXCollections.observableArrayList(data);
			pieChart.getData().addAll(pieChartData);
		
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/MonthlyReports.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Monthly Reports");
		primaryStage.setScene(scene);		
		primaryStage.show();	
		
//		pieChart = null;
	}




}
