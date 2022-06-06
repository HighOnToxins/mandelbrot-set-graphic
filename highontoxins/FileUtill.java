package highontoxins;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class FileUtill {

	public static void exportImage(BufferedImage image, String filePath) {
		try {
			//file
			System.out.println("Exporting file..."); //printing
			
			//writing
			File imageFile = new File(filePath + ".png");
            ImageIO.write(image, "png", imageFile);

			System.out.println("Done."); //printing
			
        } catch (Exception e) {
              System.out.println("Error" + e.getMessage()); //printing
        }
		
	}
	
}
