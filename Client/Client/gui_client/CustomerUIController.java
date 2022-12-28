package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.control.Alert;
=======
>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
import javafx.scene.control.Button;
<<<<<<< HEAD
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
=======
>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
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
	
<<<<<<< HEAD
	
	
	public void SubReqBtn(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.ERROR,"Your request has been recieved :)",ButtonType.OK);
		alert.showAndWait();
=======
	public void SubReqBtn() {
		
>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
	}
	
	public void CreateOrderBtn(ActionEvent event) throws Exception {
<<<<<<< HEAD
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ekrutOrder.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
=======
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/ekrutOrder.fxml"));
		Scene scene = new Scene(root);
		
		//scene.getStylesheets().add(getClass().getResource("/gui/.css").toExternalForm());
		primaryStage.setTitle("Ekrut Order");
		primaryStage.setScene(scene);
		
>>>>>>> branch 'master' of https://github.com/Bishara1/ClientSem5Proj
		primaryStage.show();
		
	}
	public void CreateRemoteOrderBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/RemoteLocation.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
    public void BackBtn(ActionEvent event) throws Exception  {
    	((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UserLogin.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle("EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
}
