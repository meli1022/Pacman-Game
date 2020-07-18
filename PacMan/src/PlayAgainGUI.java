import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class PlayAgainGUI extends JFrame implements ActionListener{

	// creates the panel for the GUI

	JButton playAgainButton = new JButton("Play Again");
	JLabel Label1 = new JLabel("Click button to play again");
	JLabel NameLabel = new JLabel("PACMAN");
	Font font = new Font("Courier", Font.BOLD,60);
	Font font2 = new Font("Courier", Font.BOLD,12);

	// adds the elements to the GUI
	public PlayAgainGUI() {

		titleScreenSetup();
		titleScreenButtonSetup();
		titleScreenLabel();
		titleScreenNameLabel();
	}


	//sets properties for the title name label
	private void titleScreenNameLabel() {


		NameLabel.setFont(font);
		NameLabel.setBounds(275, 50, 300, 200);
		NameLabel.setVisible(true);
		add(NameLabel);

	}
	//sets properties for the title label
	private void titleScreenLabel() {

		Label1.setFont(font2);
		Label1.setBounds(250, 100, 300, 200);
		Label1.setVisible(true);
		add(Label1);

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

		playAgainButton.setBounds(290, 275, 200, 100);
		playAgainButton.setBackground(Color.YELLOW);
		add(playAgainButton);
		playAgainButton.addActionListener(this);
		playAgainButton.setVisible(true);

	}

	//Opens game when button is clicked
	public void actionPerformed(ActionEvent event) {

		if(event.getSource() == playAgainButton) {


			this.dispose();
			new PacManGUI();


		}

	}

}






