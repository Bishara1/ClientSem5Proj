package gui_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class lets the user decide which configuration he would like to use.
 */
public class OLOKPageController {

	@FXML
	private Button olBtn;
	
	@FXML
	private Button okBtn;
	
	@FXML
	private Button backBtn;
	
	public static String type;	// static string for configuration type
	
	
	/**
	 * This method sets type = "OL" and calls nextWindow function.
	 * @param event
	 * @throws Exception
	 */
	public void OLbtn(ActionEvent event) throws Exception {
		type = "OL";
		nextWindow(event,"/gui_client_windows/LoginEkrut.fxml","Login Ekrut");
	}
	
	/**
	 *  This method sets type = "OK" and calls nextWindow function.
	 * @param event
	 * @throws Exception
	 */
	public void OKbtn(ActionEvent event) throws Exception {
		type = "OK";
		nextWindow(event,"/gui_client_windows/LoginEkrut.fxml","Login Ekrut");
	}
	
	/**
	 * This method closes all window and exists system.
	 * @param event
	 * @throws Exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		nextWindow(event, "/gui_client_windows/Login.fxml", "Login");
	}
	
	/**
	 * This method hides the currently open window and shows the desired window
	 * @param event
	 * @param path
	 * @param title
	 * @throws Exception
	 */
	private void nextWindow(ActionEvent event, String path, String title) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource(path));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);		
		primaryStage.show();	
	}
	
}