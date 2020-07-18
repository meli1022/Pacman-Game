/**
 * This class represents the game board and includes methods to handle keyboard events and game actions
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class Board extends JPanel implements KeyListener, ActionListener {

	/**
	 * Timer for game movement
	 */
	private Timer gameTimer = new Timer(250, this);

	/**
	 * Timer for PacMan animation
	 */
	private Timer animateTimer = new Timer(50, this);

	/**
	 * ImageIcon constant for wall
	 */
	private static final ImageIcon WALL = new ImageIcon("images/StdWall.bmp");

	/**
	 * ImageIcon constant for food
	 */
	private static final ImageIcon FOOD = new ImageIcon("images/StdFood.bmp");

	/**
	 * ImageIcon constant for blank
	 */
	private static final ImageIcon BLANK = new ImageIcon("images/Black.bmp");

	/**
	 * ImageIcon constant for door
	 */
	private static final ImageIcon DOOR = new ImageIcon("images/Black.bmp");

	/**
	 * ImageIcon constant for skull
	 */
	private static final ImageIcon SKULL = new ImageIcon("images/Skull.bmp");

	/**
	 * array to hold the game board characters from the text file
	 */
	private char[][] maze = new char[25][27];

	/**
	 * array to hold the game board images
	 */
	private JLabel[][] cell = new JLabel[25][27];

	/**
	 * PacMan object
	 */
	private PacMan pacMan;


	/**
	 * Array of Ghost objects
	 */
	private Ghost[] ghost = new Ghost[3];

	/**
	 * track amount of food on board
	 */
	private int pellets=0;

	/**
	 * track game score (1pt per food item eaten)
	 */
	private int score=0;

	/**
	 * steps for animating Pacman's chomp
	 */
	private int pStep;	


	//Construct the game board including the layout, background, PacMan and ghosts and calls the loadBoard method
	public Board() {

		//1. Set layout and make the grid
		setLayout(new GridLayout(25,27));
		setBackground(Color.BLACK);
		setBounds( 0, 0, 22*25, 27*22);

		//2. Create pacman and the ghosts
		pacMan = new PacMan ();

		ghost[0]= new Ghost (0);
		ghost[1]= new Ghost (1);
		ghost[2]= new Ghost (2);

		//3. Load the maze
		loadBoard();

	}
	// Loads maze on the screen using a text file
	private void loadBoard() {



		//keeps track of the row number
		int r = 0;

		//1. open the maze text file for input
		Scanner input;

		try {

			input = new Scanner (new File("maze.txt"));

			//2. cycle through all the rows in the maze file reading one row
			while (input.hasNext()) {

				//2.1 read the next line 
				maze [r] = input.nextLine().toCharArray();

				//2.2 for each row cycle through all the columns
				for (int c = 0; c < maze [r].length; c++) {

					cell [r][c] = new JLabel(); //create a new picture for each 

					/*Depending on the symbol in the maze file assign the appropriate picture, 
					 *this is only done for squares that aren't black since background is already black
					 */

					//2.2.1. if the symbol is a wall then assign a wall picture
					if (maze [r][c] =='W')

						cell [r][c].setIcon(WALL);

					//2.2.2. Otherwise if the symbol is a food item then assign the foot picture

					else if (maze [r][c] == 'F') {

						cell [r][c].setIcon(FOOD);
						setPellets(getPellets() + 1); //keep track of the amount of food
					}

					//2.2.3. Otherwise if the symbol is PacMan then assign closed left image and set PacMan's row, columns and direction (left)

					else if (maze [r][c] == 'P') {

						cell [r][c].setIcon(pacMan.getIcon());
						pacMan.setRow(r);
						pacMan.setColumn(c);
						pacMan.setDirection(0);
					}
					//2.2.4. 
					else if 	(maze [r][c] == '0' || maze [r][c] =='1' || maze[r][c] == '2')  {

						int gNum = (int) (maze [r][c])-48; 

						cell[r][c].setIcon(ghost [gNum].getIcon()); 
						ghost[gNum].setRow(r);
						ghost[gNum].setColumn(c);

					}
					//2.2.5.
					else if (maze [r][c] == 'D') {

						cell [r][c].setIcon((DOOR));

					}

					//2.2.6.

					add (cell[r][c]);

				}
				//2.3. Increment the row
				r++;
			}

			//3. close the maze text file 
			input.close();
		} catch (FileNotFoundException e) {


			System.out.println("File not found");

		}

	}

	//Determines the source of the action as either the game timer or the animation timer and then
	//performs the corresponding actions
	@Override
	public void actionPerformed(ActionEvent e) {
		//1. If the action is the game timer
		if (e.getSource()==gameTimer) {

			//1.1. Then move the PacMan and the ghosts
			performMove(pacMan);
			moveGhosts();

			//2. Otherwise, if the action is the animation timer
		} else if (e.getSource()==animateTimer) {

			//2.1. Animate PacMan through the current step
			animatePacMan();

			//2.2. Increment the step number
			pStep++;

			//2.3. If the step is the last step then reset the step to 0
			if (pStep==3)
				pStep=0;

		}

	}



	//Allows an object to move and updates both on the maze and screen based on:
	//the object, direction, and change in row and column
	private void performMove(Mover mover) {
		// TODO Auto-generated method stub
		//1. If a mover is at a door then teleport to other side
		if (mover.getColumn()==1){
			mover.setColumn(24);
			cell[12][1].setIcon(DOOR);
		} else if (mover.getColumn()==25){
			mover.setColumn(2);
			cell[12][25].setIcon(DOOR);
		}

		//2. If there is no wall in the direction that
		// the Mover object wants to go then
		if (maze[mover.getNextRow()][mover.getNextColumn()] != 'W') {

			//2.1. If the Mover object is PacMan then animate a 'chomp'
			if (mover==pacMan)
				animateTimer.start();

			//2.2 Otherwise the Mover is a ghost
			else {

				//2.2.1 If the cell where the Ghost is has food then reset the food
				if (maze[mover.getRow()][mover.getColumn()]=='F')
					cell[mover.getRow()][mover.getColumn()].setIcon(FOOD);

				//2.2.2 Otherwise reset the cell to blank
				else
					cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);

				//2.2.3 Move the ghost's position
				mover.move();

				//2.2.4 If a collision has occurred then death occurs
				if (collided())
					death();

				//2.2.5 Otherwise update the picture on the screen
				else
					cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());
			}
		}
	}
	//Stop the game when PacMan and a ghost 'collide'
	private void death() {
		// TODO Auto-generated method stub
		//1. Set pacMan dead
		pacMan.setDead(true);

		//2. Stop the game
		stopGame();

		//3. Determine the current location of PacMan on the screen
		// and assign a picture of a skull
		cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);

		new PlayAgainGUI();
	}
	//Stops the game timer
	private void stopGame() {
		// TODO Auto-generated method stub
		//1. if PacMan is dead or all the food is eaten then
		// stop the timers
		if (pacMan.isDead() || score==getPellets()) {
			animateTimer.stop();
			gameTimer.stop();
		}
	}

	//Determines if PacMan has collided with a Ghost
	private boolean collided() {
		// TODO Auto-generated method stub
		//1. Cycle through all the ghosts to see if anyone has caught PacMan
		for (Ghost g: ghost) {

			//1.1. If the ghost is in the same location then return that a collision occurred
			if (g.getRow()==pacMan.getRow() && g.getColumn()==pacMan.getColumn())
				return true;
		}

		//2. If no ghosts were in the same location then return that no collision occurred
		return false;
	}

	//Animates PacMan in 3 steps: open mouth, draw black square, move and close mouth
	private void animatePacMan() {
		// TODO Auto-generated method stub
		//1. If it is step 0 of animation
		if (pStep == 0) {

			//1.1 Open mouth in current cell
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon
			(PacMan.IMAGE[pacMan.getDirection()][1]);

			//1.2 Delay the animation timer
			animateTimer.setDelay(100);

		}

		//2. Otherwise if it is step 1 of animation
		else if (pStep==1)

			//2.1. Blank the current cell
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);

		//3. Otherwise if it is step 2 of animation
		else if (pStep ==2) {

			//3.1. Move pacMan
			pacMan.move();

			//3.2. If there is any food in the new square on the maze and the Mover is PacMan then
			if (maze[pacMan.getRow()][pacMan.getColumn()]=='F'){

				//3.2.1. Increment the score
				score++;
				//System.out.println(score);

				//3.2.2. Mark the maze at the new position to 'eaten'
				maze[pacMan.getRow()][pacMan.getColumn()]='E';

			}
			//3.3. Stop the animation timer
			animateTimer.stop();

			//3.4. If pacMan is dead then show a skull
			if (pacMan.isDead())

				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);

			//3.5 Otherwise show the appropriate closed pacMan
			// based on its direction
			else
				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][0]);

		}
	}

	//Moves the ghosts in a random pattern
	private void moveGhosts() {
		// TODO Auto-generated method stub
		//1. Cycle through all the ghosts
		for (Ghost g: ghost) {

			int dir=0;

			//1.1. Keep selecting random directions to avoid
			// 'back-tracking'
			do {
				dir = (int)(Math.random()*4);
			} while (Math.abs(g.getDirection() - dir) == 2);

			//1.2. Set the ghosts direction
			g.setDirection(dir);

			//1.3 Move the ghost
			performMove(g);
		}
	}
	//Mandatory method to implement KeyListener interface
	@Override
	public void keyTyped(KeyEvent key) {
		//not used
	}

	//Handles keyboard entries - to start the game and control PacMan's movements
	@Override
	public void keyPressed(KeyEvent key) {
		//1. If the game isn't running and pacMan is alive then
		// start the game timer
		if (gameTimer.isRunning()==false && pacMan.isDead()==false)
			gameTimer.start();

		//2. If PacMan is still alive and the game is not over then
		if (pacMan.isDead()==false && score!=getPellets()) {

			//2.1 Track direction based on the key pressed 37 since ASCII codes 
			//for cursor keys start at 37:
			int direction = key.getKeyCode()-37;

			//2.2. Change direction of PacMan;
			// 37-left,38-up,39-right,40-down
			if (direction==0 && maze[pacMan.getRow()][pacMan.getColumn()-1] != 'W')
				pacMan.setDirection(0);
			else if (direction==1 && maze[pacMan.getRow()-1][pacMan.getColumn()] != 'W')
				pacMan.setDirection(1);
			else if (direction==2 && maze[pacMan.getRow()][pacMan.getColumn()+1] != 'W')
				pacMan.setDirection(2);
			else if (direction==3 && maze[pacMan.getRow()+1][pacMan.getColumn()] != 'W')
				pacMan.setDirection(3);

		}
	}

	//Mandatory method to implement KeyListener interface
	@Override
	public void keyReleased(KeyEvent key) {
		//not used

	}
	public int getPellets() {
		return pellets;
	}
	public void setPellets(int pellets) {
		this.pellets = pellets;
	}
}

