package gui_client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ShipmentAddressController implements Initializable{
	
	@FXML
	private TextField countrytxt;
	@FXML
	private TextField cityytxt;
	@FXML
	private TextField streettxt;
	@FXML
	private Button backBtn;
	@FXML
	private Button sendreqBtn;
	
	public void SendYourRequest(ActionEvent event) throws Exception{
		Alert alert = new Alert(AlertType.CONFIRMATION,"Your address has been sent to our delivery man:)",ButtonType.OK);
		alert.showAndWait();
	}
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ShipmentMethod.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
