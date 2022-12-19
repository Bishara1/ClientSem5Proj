package gui_client;

import java.awt.TextField;
import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

			System.out.println("You must enter an id number");	// Alert ?
		}
		else
		{
			// AcademicFrameController methods java file
		}
	}
	
	public void BackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui_client/loginsubscriber.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
		primaryStage.setTitle("Teeest 3");
		primaryStage.setScene(scene);		
		primaryStage.show();		
	}
	

}
