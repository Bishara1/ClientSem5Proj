package gui_client;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoteLocationController{
		
		
		@FXML
		private TextField machineidtxt ;
		
		public void LocationCombo() {
			
		}
		public void BackBtn(ActionEvent event) throws Exception  { //fix this apparently its null
			((Node)event.getSource()).getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("/gui_client/UserUI.fxml"));
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
			primaryStage.setTitle("EKRUT");
			primaryStage.setScene(scene);		
			primaryStage.show();
		}
	    public void StartOrderBtn() {
			
		}
	}
