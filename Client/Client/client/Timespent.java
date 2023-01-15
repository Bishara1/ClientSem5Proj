package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui_client.UserLoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.control.Alert.AlertType;

public class Timespent extends Application implements Runnable{

	private int time;
	
	public Timespent() {
		time = 0;
	}

	public void reset()
	{
		time = 0;
	}

	@Override
	public void run() {
		try {
			
			Thread.sleep(10000);
			
		Platform.runLater(new Runnable() {
			@Override
		    public void run() {
		    	//hide all windows
		    	//disconnect current user
		    	ObservableList<Window> windows12;
		    	Iterator<Window> it = Stage.impl_getWindows();
		    	ArrayList<Window> windows = new ArrayList<Window>();
		    	while(it.hasNext())
		    	{
		    		windows.add(it.next());
		    	}
		    	windows12 = FXCollections.observableArrayList(windows);
		    	for(Window w : windows12)
		    	{
		    		w.hide();
		    	}
				Parent root = null;
				try {
					root = FXMLLoader.load(getClass().getResource("/gui_client_windows/LoginEkrut.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ChatClient.cart = new ArrayList<>();
				Stage primaryStage = new Stage();
				Scene scene = new Scene(root);
				//scene.getStylesheets().add(getClass().getResource("/gui/loginsubscriber.css").toExternalForm());
				primaryStage.setTitle("EKRUT");
				primaryStage.setScene(scene);		
				primaryStage.show();
		    }
		});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		return;
	}

}
