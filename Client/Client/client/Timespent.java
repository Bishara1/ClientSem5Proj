package client;

import gui_client.UserLoginController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Timespent implements Runnable{

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
			while(time < 120) {
				Thread.sleep(1000);
				time++;
			}
		System.exit(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
