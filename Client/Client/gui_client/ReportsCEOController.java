package gui_client;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
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
		String s = mpc.requestedReport;

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
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client_windows/MonthlyReports.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Monthly Reports");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}




}
