import javax.swing.ImageIcon;

/**
 * This class s used to create Ghost objects.  
 * It includes a constant ImageIcon array to hold the various ghost pictures
 * and a constructor method that sets the Ghosts' images
 */

public class Ghost extends Mover{

	//	/Creates an array of ImageIcons representing various Ghosts 
	public static final ImageIcon[] IMAGE = {

			new ImageIcon("images/Ghost1.bmp"),
			new ImageIcon("images/Ghost2.bmp"),
			new ImageIcon("images/Ghost3.bmp")
	};

	//Ghost constructor
	public Ghost(int gNum) {
		this.setIcon(IMAGE[gNum]);
	}

}

