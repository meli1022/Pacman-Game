import javax.swing.JFrame;

/**
 * This class creates a PacMan GUI that extends the JFrame class.  It has a Board (JPanel) and 
 * includes a constructor method that sets up the frame and adds a key listener to the board.
 */

@SuppressWarnings("serial")
public class PacManGUI extends JFrame {
	
	// Board panel
	private Board board = new Board();
	private Score score = new Score();
	
	//PacMan GUI constructor
	public PacManGUI() {
		
		//set up GUI
		setSize(800,600);
		setTitle("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		addKeyListener(board);
		add(board);
		
		add(score);
		
		setVisible(true);
		
		
		
	}
	
}

