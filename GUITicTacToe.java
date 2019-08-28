/* Emma Shu, 10/5/18: This program allows a player to use the Graphical User Interface, GUI, to play TicTacToe. The program opens a window 
 * with two different containers, North and Center. Center container has a grid layout and is where the players will execute the game. 
 * After every move, the button is disabled to prevent others from entering the same spot. After a win or tie, the board is cleared and the 
 * buttons are enabled again. The North container has a border layout and displays the number of wins each player has and the number of ties.
 * There is a text field and button that allows players to change their player's name.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUITicTacToe implements ActionListener {
	
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[3][3];
	Container center = new Container();
	Container north = new Container();
	JLabel xLabel = new JLabel("X wins: 0");
	JLabel oLabel = new JLabel ("O wins: 0");
	JLabel tieLabel = new JLabel ("Ties: 0");
	JButton xChangeName = new JButton ("Change X's Name");
	JButton oChangeName = new JButton ("Change O's Name");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	int[][] board = new int[3][3];
	int turn = X_TURN;
	String xPlayerName = "X";
	String oPlayerName = "O";
	int xWins = 0;
	int oWins = 0;
	int ties = 0;
	
	
	public GUITicTacToe() {
		frame.setSize(400, 400);
		
		frame.setLayout(new BorderLayout());
		// Center grid container.
		center.setLayout(new GridLayout(3,3));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[i][j] = new JButton("");
				center.add(button[i][j]);
				button[i][j].addActionListener(this); // ActionListener "listens" for actions. If a certain action is performed, then a method will be performed.
			}
		}
		frame.add(center, BorderLayout.CENTER);
		
		// North grid container.
		north.setLayout(new GridLayout(4,4));
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		north.add(oChangeName);
		oChangeName.addActionListener(this);
		north.add(xChangeField);
		xChangeField.addActionListener(this);
		north.add(oChangeField);
		north.add(xLabel);
		north.add(oLabel);
		north.add(tieLabel);
		oChangeField.addActionListener(this);
		frame.add(north, BorderLayout.NORTH);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the program after exiting window.
		frame.setVisible(true); // Displays the frames.
	}

	public static void main(String[] args) {
		
		new GUITicTacToe();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean gridButton = false;
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				if (event.getSource().equals(button[i][j])) {
					gridButton = true;
					current = button[i][j];
					if (board[i][j] == BLANK) {
						if (turn == X_TURN) {
							current.setText("X");
							current.setEnabled(false);
							board[i][j] = X_MOVE;
							turn = O_TURN;
						}
						else {
							current.setText("O");
							current.setEnabled(false);
							board[i][j] = O_MOVE;
							turn = X_TURN;
						}
						if (checkWin(X_MOVE)) {
							xWins++;
							xLabel.setText(xPlayerName + " wins: " + xWins);
							clearBoard();
						}
						else if (checkWin(O_MOVE)) {
							oWins++;
							oLabel.setText(oPlayerName + " wins: " + oWins);
							clearBoard();
						}
						else if (checkTie()) {
							ties++;
							tieLabel.setText("Ties: " + ties);
							clearBoard();
						}
					}
				}
			}
		}
		if (gridButton == false) {
			if (event.getSource().equals(xChangeName)) {
				if (xChangeField.getText().length() != 0) {
					xPlayerName = xChangeField.getText();
					xLabel.setText(xPlayerName + " wins: " + xWins);
				}
			}
			else if (event.getSource().equals(oChangeName)) {
				if (oChangeField.getText().length() != 0) {
					oPlayerName = oChangeField.getText();
					oLabel.setText(oPlayerName + " wins: " + oWins);
				}
			}
		}
		
	}
	
	
	
	public boolean checkWin(int player) { 
		if (board[1][1] == player && board[0][0] == player && board[2][2] == player) {
			return true;
		}
		else if (board[1][1] == player && board[2][0] == player && board[0][2] == player) {
			return true;
		}
		else if (board[1][1] == player && board[0][1] == player && board[2][1] == player) {
			return true;
		}
		else if (board[1][1] == player && board[1][0] == player && board[1][2] == player) {
			return true;
		}
		else if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
			return true;
		}
		else if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
			return true;
		}
		else if (board[2][0] == player && board[2][1] == player && board [2][2] == player) {
			return true;
		}
		else if (board[0][2] == player && board[1][2] == player && board [2][2] == player) {
			return true;
		}
		return false;
	}
	
	public boolean checkTie() {
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[0].length; column++) {
				if (board[row][column] == BLANK) { 
					return false;
				}
			}
		}
		return true;
	}
	
	public void clearBoard() {
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board[0].length; b++) {
				board[a][b] = BLANK;
				button[a][b].setText("");
				button[a][b].setEnabled(true); // Re-enables disabled buttons after a win or tie.
			}
		}
		turn = X_TURN;
	}

}
