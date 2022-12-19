package gui_client;

import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ReportsCEOController {
	
	@FXML
	private Button backBtn;
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/MonthlyReports.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/MonthlyReports.css").toExternalForm());
		primaryStage.setTitle("Test 2");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}

}
