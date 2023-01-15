package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OLOKPageController {

	@FXML
	private Button olBtn;
	
	@FXML
	private Button okBtn;
	
	@FXML
	private Button exitBtn;
	
	public static String type;
	
	
	public void OLbtn(ActionEvent event) throws Exception {
		
		type = "OL";
		nextWindow(event,"/gui_client_windows/LoginEkrut.fxml","EKT Login");
	}
	
	public void OKbtn(ActionEvent event) throws Exception {
		type = "OK";
		nextWindow(event,"/gui_client_windows/LoginEkrut.fxml","EKT Login");
	}
	
	public void ExitBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	private void nextWindow(ActionEvent event, String window_location, String title) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource(window_location));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
}