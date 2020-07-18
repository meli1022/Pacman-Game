

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import javax.swing.JPanel;

public class Score extends JPanel {


	int Score = 0;
	int highScore = 0;


	public Score() {

		setscore();
		screenSetup();



		//		if (Board.pellets >= 1) 
		//			score==pellets;



	}

	private void screenSetup() {

		setBackground(Color.PINK);
		setVisible(true);
	}

	private void setscore() {
		JLabel scoreLabel = new JLabel("Score:", Score);
		JLabel hScoreLabel = new JLabel("High Score:");

		scoreLabel.setBounds(10, 10, 10, 10);
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 12));

		hScoreLabel.setBounds(10, 10, 10, 10);
		hScoreLabel.setFont(new Font("Serif", Font.BOLD, 12));

		add(hScoreLabel);
	}


}





