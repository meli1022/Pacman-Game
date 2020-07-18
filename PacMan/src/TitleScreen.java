import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


@SuppressWarnings("serial")
public class TitleScreen extends JFrame implements ActionListener{

	// creates the panel for the GUI

	JButton startButton = new JButton("Start");
	JLabel titleLabel = new JLabel("Click start button to begin PacMan Game");
	JLabel titleNameLabel = new JLabel("PACMAN");
	Font font = new Font("Courier", Font.BOLD,60);
	Font font2 = new Font("Courier", Font.BOLD,16);
	Font font3 = new Font("Arial", Font.PLAIN,12);

	String test = "The game involves a player controlling a "
			+ "‘chomping’ character, Pacman, around a maze filled with food (pellets). "
			+ "The player moves Pacman around the maze (4 arrow directions, up, down, left and right)"
			+ " and attempts to ‘eat’ the food (move over - and the food disappears)  "
			+ " However, there are ghosts (computer controlled) characters that are attempting to ‘catch’ "
			+ "(collide into) Pacman, in which case Pacman dies.  "
			+ "The game ends when Pacman eats all the food, or dies " ;

	JLabel howToLabel = new JLabel("<html>"+test+"</html>");

	// adds the elements to the GUI
	public TitleScreen() {

		titleScreenSetup();
		titleScreenButtonSetup();
		titleScreenLabel();
		titleScreenNameLabel();
		titleScreenHowToLabel();
	}

// sets properties for the how to play the game label
	private void titleScreenHowToLabel() {

		howToLabel.setFont(font3);
		howToLabel.setBounds(50, 300, 300, 200);
		howToLabel.setVisible(true);
		add(howToLabel);

	}


	//sets properties for the title name label
	private void titleScreenNameLabel() {



		titleNameLabel.setFont(font);
		titleNameLabel.setBounds(275, 50, 300, 200);
		titleNameLabel.setVisible(true);
		add(titleNameLabel);

	}
	//sets properties for the title label
	private void titleScreenLabel() {

		titleLabel.setFont(font2);
		titleLabel.setBounds(200, 100, 400, 200);
		titleLabel.setVisible(true);
		add(titleLabel);

	}

	// sets up the title screen
	private void titleScreenSetup() {

		setSize(800, 600);
		setBackground(Color.PINK);
		setTitle("PacMan Game");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	//sets properties for the start button
	private void titleScreenButtonSetup() {

		startButton.setBounds(290, 375, 200, 100);
		startButton.setBackground(Color.YELLOW);
		add(startButton);
		startButton.addActionListener(this);
		startButton.setVisible(true);

	}

	//Opens game when button is clicked
	public void actionPerformed(ActionEvent event) {

		if(event.getSource() == startButton) {


			this.dispose();
			new PacManGUI();


		}

	}

}

