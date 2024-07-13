/*
 * Nonogram, 6/5/19: Bobogram contains a series of three picture logic puzzles in which the user navigates through. 
 * Also called “Pain by Numbers”,  cells in a grid must be either filled in or left blank in accordance to the numbers 
 * located in the columns to the left and the rows on top to reveal a pixelated image. The puzzles will become more 
 * and more complicated in size with each board; the first board is a 5 x 5 cell, followed by a 10 x 10, 
 * and finally a 15 x 15.
 * User Manual Link: https://docs.google.com/document/d/1pMxMcLZ8CViKI_E2QWx1YNjoBza2pjeH5B-Q9j2pxSM/edit?usp=sharing
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Nonogram implements MouseListener, ActionListener {

	final int BLANK = 0;
	final int X = 2;
	final int FILLED = 1;
	int width = 5;
	public int[][] board = new int[width][width];
	JFrame frame = new JFrame();
	JButton next = new JButton("Next Puzzle");
	JButton check = new JButton("Check Answer");
	JButton change = new JButton("Change Mode");
	JButton clear = new JButton("Clear Board");
	Container south = new Container();
	Container north = new Container();
	Container west = new Container();
	JLabel[] labels5N = new JLabel[5];
	JLabel[] labels5W = new JLabel[5];
	JLabel[] labels10N = new JLabel[10];
	JLabel[] labels10W = new JLabel[10];
	JLabel[] labels15N = new JLabel[15];
	JLabel[] labels15W = new JLabel[15];
	boolean isClicked = false;
	Nonogrid nonogrid = new Nonogrid(board);

	public Nonogram() {
		frame.setSize(1950, 1050);
		frame.setLayout(new BorderLayout());
		frame.add(nonogrid, BorderLayout.CENTER);
		nonogrid.addMouseListener(this);

		south.setLayout(new GridLayout(1, 3));
		south.add(next);
		next.addActionListener(this);
		south.add(check);
		check.addActionListener(this);
		south.add(change);
		change.addActionListener(this);
		south.add(clear);
		clear.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH);

		north.setLayout(new GridLayout(1, width));
		JLabel labelsS = new JLabel("     ");
		north.add(labelsS);
		labels5N[0] = new JLabel("<html>2<br/></html>");
		labels5N[1] = new JLabel("<html>1<br/>1</html>");
		labels5N[2] = new JLabel("<html>1<br/>1</html>");
		labels5N[3] = new JLabel("<html>1<br/>1</html>");
		labels5N[4] = new JLabel("<html>2<br/></html>");
		for(int i = 0; i < labels5N.length; i++) {
			north.add(labels5N[i]);
		}
		frame.add(north, BorderLayout.NORTH);

		west.setLayout((new GridLayout(width, 1)));
		labels5W[0] = new JLabel("1        1");
		labels5W[1] = new JLabel("1        1        1");
		labels5W[2] = new JLabel("1        1");
		labels5W[3] = new JLabel("1        1");
		labels5W[4] = new JLabel("1");
		for(int i = 0; i < labels5W.length; i++) {
			west.add(labels5W[i]);
		}
		frame.add(west, BorderLayout.WEST);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new Nonogram();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) { //Modified Conway's Life, Life class code.
		double width = (double) nonogrid.getHeight() / board[0].length; //Width of a single square.
		double height = (double) nonogrid.getWidth() / board.length; //Height of a single square.
		int column = Math.min(board[0].length, (int) (e.getX() / height)); //Either the length of board or the event where the mouse was from divided by vertical squares, the column number.
		int row = Math.min(board.length, (int) (e.getY() / width));  //Either the length of board or the event where the mouse was from divided by horizontal squares, the row number.
		if (!isClicked) { //If the mode is set to fill option.
			if (board[row][column] == BLANK || board[row][column] == X) { //Fill.
				board[row][column] = FILLED;
			} else if (board[row][column] == FILLED) { //Blank.
				board[row][column] = BLANK;
			}
		} else if (isClicked) { //If the mode is set to X option.
			if (board[row][column] == BLANK || board[row][column] == FILLED) { //X.
				board[row][column] = X;
			} else if (board[row][column] == X) { //Blank.
				board[row][column] = BLANK;
			}
		}
		frame.repaint(); //The different integer values will be inserted into the 2D integer array that is put into the Nonogrid class to be painted on screen.
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(change)) { //Change mode from fill to X.
			isClicked = !isClicked;
		}
		if (e.getSource().equals(check)) { //Answer keys.
			int[][] temp = new int[width][width];
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j < temp[0].length; j++) {
					temp[i][j] = board[i][j];
					if (temp[i][j] == X) {
						temp[i][j] = BLANK; //Create temporary array to check the answers in order to avoid all X's transforming to blanks after answer is checked.
					}
				}
			}
			boolean go = true;
			if (width == 5) {
				int[][] answerKey5 = { { 0, 1, 0, 1, 0 },
						{ 1, 0, 1, 0, 1 }, 
						{ 1, 0, 0, 0, 1 }, 
						{ 0, 1, 0, 1, 0 },
						{ 0, 0, 1, 0, 0 } };
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						go = go && (answerKey5[i][j] == temp[i][j]); //If all values are true then continue.
					}
				}
			}
			if (width == 10) {
				int[][] answerKey10 = { { 0, 1, 0, 1, 1, 1, 1, 0, 0, 0 }, 
						{ 0, 1, 1, 1, 0, 0, 1, 1, 0, 0 },
						{ 0, 1, 1, 0, 0, 0, 0, 1, 1, 0 }, 
						{ 1, 1, 0, 0, 1, 1, 0, 0, 1, 1 },
						{ 0, 1, 0, 0, 0, 0, 0, 0, 1, 0 }, 
						{ 0, 1, 0, 0, 0, 0, 0, 0, 1, 0 }, 
						{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0 },
						{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0 }, 
						{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0 },
						{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						go = go && (answerKey10[i][j] == temp[i][j]); //If all values are true then continue.
					}
				}
			}
			if (width == 15) {
				int[][] answerKey15 =  { { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
						{ 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0 },
						{ 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0 },
						{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
						{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
						{ 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1 },
						{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 },
						{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
						{ 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0 },
						{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1 },
						{ 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1 },
						{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
						{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
						{ 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0 },
						{ 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0 } };
				for (int i = 0; i < 15; i++) {
					for (int j = 0; j < 15; j++) {
						go = go && (answerKey15[i][j] == temp[i][j]); //If all values are true then continue.
					} 
				}
			}
			if (go) {
				JOptionPane.showMessageDialog(frame, "Congratulations!"); //If all were true then display "Congratulations" statement.
			} else {
				JOptionPane.showMessageDialog(frame, "Not Finished."); //If became false then display "Not Finished" statement.
				
			}
		}
		if (e.getSource().equals(next)) {
			frame.remove(nonogrid);
			if (width < 15) { //Change the grid width according to current width.
				width += 5;
			} else {
				width = 5;
			}
			board = new int[width][width]; //Create new integer array to store the new values.
			nonogrid = new Nonogrid(board); //Create new Nonogrid, will change size with the board variable.
			frame.add(nonogrid, BorderLayout.CENTER);
			nonogrid.addMouseListener(this);
			switchLabels(width); //Change the labels according to width.
			frame.setVisible(true);
			frame.repaint();
		}
		if (e.getSource().equals(clear)) {
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < width; j++) {
					board[i][j] = BLANK; //Set all squares to blank.
				}
			}
			frame.repaint();
		}

	}
	public void switchLabels(int width) { //JLabels for each width.
		frame.remove(north);
		frame.remove(west);
		north = new Container();
		west = new Container();
		north.setLayout(new GridLayout(1, width));
		west.setLayout((new GridLayout(width, 1)));
		if (width == 5) {
			labels5N[0] = new JLabel("<html>2<br/></html>"); //Cannot use String tabs and returns in JLabels, use HTML instead.
			labels5N[1] = new JLabel("<html>1<br/>1</html>");
			labels5N[2] = new JLabel("<html>1<br/>1</html>");
			labels5N[3] = new JLabel("<html>1<br/>1</html>");
			labels5N[4] = new JLabel("<html>2<br/></html>");
			north.add(new JLabel("    "));
			for(int i = 0; i < labels5N.length; i++) {
				north.add(labels5N[i]);
			}
			labels5W[0] = new JLabel("1        1");
			labels5W[1] = new JLabel("1        1        1  ");
			labels5W[2] = new JLabel("1        1");
			labels5W[3] = new JLabel("1        1");
			labels5W[4] = new JLabel("1");
			for(int i = 0; i < labels5W.length; i++) {
				west.add(labels5W[i]);
			}
		}
		else if (width == 10) {
			labels10N[0] = new JLabel("<html>1<br>1<br></html>");
			labels10N[1] = new JLabel("<html>10<br></html>");
			labels10N[2] = new JLabel("<html>2<br>1<br></html>");
			labels10N[3] = new JLabel("<html>2<br>1<br></html>");
			labels10N[4] = new JLabel("<html>1<br>1<br>4<br></html>");
			labels10N[5] = new JLabel("<html>1<br>1<br>4<br></html>");
			labels10N[6] = new JLabel("<html>2<br>1<br></html>");
			labels10N[7] = new JLabel("<html>2<br>1<br></html>");
			labels10N[8] = new JLabel("<html>10<br></html>");
			labels10N[9] = new JLabel("<html>1<br>1<br></html>");
			north.add(new JLabel("    "));
			for(int i = 0; i < labels10N.length; i++) {
				north.add(labels10N[i]);
			}
			labels10W[0] = new JLabel("1        4");
			labels10W[1] = new JLabel("3        2");
			labels10W[2] = new JLabel("2        2");
			labels10W[3] = new JLabel("2        2        2  ");
			labels10W[4] = new JLabel("1        1");
			labels10W[5] = new JLabel("1        1");
			labels10W[6] = new JLabel("1        2        1  ");
			labels10W[7] = new JLabel("1        2        1  ");
			labels10W[8] = new JLabel("1        2        1  ");
			labels10W[9] = new JLabel("10");
			for(int i = 0; i < labels10W.length; i++) {
				west.add(labels10W[i]);
			}
		}
		else {
			labels15N[0] = new JLabel("<html>1<br>3<br>3<br></html>");
			labels15N[1] = new JLabel("<html>1<br>1<br>4<br>1<br></html>");
			labels15N[2] = new JLabel("<html>1<br>1<br>1<br></html>");
			labels15N[3] = new JLabel("<html>2<br>1<br></html>");
			labels15N[4] = new JLabel("<html>1<br>2<br>1<br></html>");
			labels15N[5] = new JLabel("<html>1<br>1<br>1<br></html>");
			labels15N[6] = new JLabel("<html>1<br>2<br>1<br></html>");
			labels15N[7] = new JLabel("<html>1<br>1<br>1<br>1<br>1<br></html>");
			labels15N[8] = new JLabel("<html>1<br>2<br>1<br></html>");
			labels15N[9] = new JLabel("<html>1<br>1<br>1<br></html>");
			labels15N[10] = new JLabel("<html>1<br>2<br>1<br></html>");
			labels15N[11] = new JLabel("<html>2<br>1<br></html>");
			labels15N[12] = new JLabel("<html>1<br>1<br>1<br></html>");
			labels15N[13] = new JLabel("<html>1<br>1<br>4<br>1<br></html>");
			labels15N[14] = new JLabel("<html>1<br>3<br>3<br></html>");
			north.add(new JLabel("    "));
			for(int i = 0; i < labels15N.length; i++) {
				north.add(labels15N[i]);
			}
			labels15W[0] = new JLabel("3        3 ");
			labels15W[1] = new JLabel("1        6        1 ");
			labels15W[2] = new JLabel("1        2        2        1");
			labels15W[3] = new JLabel("1        1        1        1");
			labels15W[4] = new JLabel("1        1");
			labels15W[5] = new JLabel("2        1        1        2");
			labels15W[6] = new JLabel("1        1        1        1        1");
			labels15W[7] = new JLabel("1        1");
			labels15W[8] = new JLabel("1        1        1        1        1");
			labels15W[9] = new JLabel("1        1        1        1");
			labels15W[10] = new JLabel("1         3        1");
			labels15W[11] = new JLabel("1        1");
			labels15W[12] = new JLabel("1        1");
			labels15W[13] = new JLabel("1        1        1        1");
			labels15W[14] = new JLabel("1        5        1");
			for (int i = 0; i < labels15W.length; i++) {
				west.add(labels15W[i]);
			}
			
		}
		frame.add(north, BorderLayout.NORTH);
		frame.add(west, BorderLayout.WEST);
	}

}
