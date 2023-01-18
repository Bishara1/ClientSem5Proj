package gui_client;

import java.io.File;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class StyleSheetManager {
	
	public static Image GetImage(Class<?> cls, String imageName) {
		String actual = "/images/" + imageName;
		String path = cls.getClass().getResource(actual).toExternalForm();
		Image img = new Image(path,true);
		
		return img;
	}

}
