package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerUIController {
	@FXML
	private Button subreqbtn;
	@FXML
	private Button createorderbtn;
	@FXML
	private Button createremotebtn;
	@FXML
	private Button backbtn;
	
	
	
	public void SubReqBtn() {
		
	}
	
	public void CreateOrderBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ekrutOrder.fxml"));
		Scene scene = new Scene(root);
		
		//scene.getStylesheets().add(getClass().getResource("/gui/.css").toExternalForm());
		primaryStage.setTitle("Subscribers Viewer");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	public void CreateRemoteBtn() {
		
	}
    public void BackBtn() {
		
	}
}
