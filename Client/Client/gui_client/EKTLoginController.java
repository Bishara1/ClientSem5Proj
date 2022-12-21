package gui_client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EKTLoginController {
	
	@FXML
	private TextField idtxt;
	
	@FXML
	private Button backBtn;
	@FXML
	private Button loginBtn;
	
	private String getID() {
		return idtxt.getText();
	}
	
	public void login(ActionEvent event) throws Exception {
	
		String id;
		//FXMLLoader loader = new FXMLLoader();
		
		id=getID();
		if(id.trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.ERROR,"You must enter an ID!",ButtonType.OK);
			alert.showAndWait();
		}
		else
		{
			// AcademicFrameController methods java file
		}
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
		primaryStage.setTitle("Login EKRUT");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}//
	

}
