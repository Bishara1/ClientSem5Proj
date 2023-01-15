package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
			while(time < 10) {
				Thread.sleep(1000);
				time++;
			}
			Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	
		    	//disconnect current user
				Parent root = null;
				try {
					root = FXMLLoader.load(getClass().getResource("/gui_client/LoginEkrut.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
