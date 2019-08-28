/* Emma Shu, 10/3/2018: This TicTacToe program allows two players, x and o, to use the console input to play TicTacToe. The console displays a
 * two dimensional array with columns labeled 1 through 3 and rows labeled a through c so that users will be able to enter in the space they 
 * would like to move to. The program would display the moves until each blank is filled in or one of the players win. After each round, the
 * program would ask the player if they would like to continue playing. If so, the program redraws the board and records the outcome of the 
 * previous round.
*/
import java.util.Scanner;

public class TicTacToe {
	
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	
	int[][] board = new int[3][3];
	int turn = X_TURN;
	Scanner scanner;
	String input = "";
	String next = "";
	int xWinCounter = 0;
	int oWinCounter = 0;
	int tieCounter = 0;

	public TicTacToe() {
		scanner = new Scanner(System.in);
		boolean stillPlaying = true;
		while (stillPlaying) {
			while (!checkWin(X_MOVE) && !checkWin(O_MOVE) && !checkTie()) {
				printBoard();
				System.out.println("Please enter a letter followed by a number (ex. a2): ");
				input = scanner.nextLine();
				if (input.length() != 2) {
					System.out.println("Please enter a letter followed by a number (ex. a2): ");
				} 
				else if (input.charAt(0) != 'a' && input.charAt(0) != 'b' && input.charAt(0) != 'c') {
					System.out.println("Row must be a, b, or c. Please try again (ex. a2): ");
				} 
				else if (input.charAt(1) != '1' && input.charAt(1) != '2' && input.charAt(1) != '3') {
					System.out.println("Column must be 1, 2, or 3. Please try again (ex. a2): ");
				}
				else {
					int row = input.charAt(0) - 'a'; // Translates into an integer. If subtracted by 'a' on the ASCII, the 'a' would go to 0, 'b' would go to 1, and 'c' would go to 2 which can be read by the program.
					int column = input.charAt(1) - '1'; // Translates into a more computer-readable integer starting at base index 0.
					if (board[row][column] == BLANK) { // Checks if the place is taken or not.
						if (turn == X_TURN) {
							board[row][column] = X_MOVE;
							turn = O_TURN;
						}
						else {
							board[row][column] = O_MOVE;
							turn = X_TURN;
						}
					}
					else {
						System.out.println("This place is already taken. Please try again (ex. a2): ");
					}
				}
			}
			if (checkWin(X_MOVE)) {
				System.out.println("X wins!");
				xWinCounter++;
			}
			else if (checkWin(O_MOVE)) {
				System.out.println("O wins!");
				oWinCounter++;
			}
			else if (checkTie()) {
				System.out.println("It's a tie!");
				tieCounter++;
			}
			System.out.println("X has won " + xWinCounter + " time(s).");
			System.out.println("O has won " + oWinCounter + " time(s).");
			System.out.println("There has been " + tieCounter + " tie(s).");
			System.out.println("Continue playing? (y or n): ");
			next = scanner.nextLine();
			if (next.equals("n")) {
				stillPlaying = false;
			}
			else {
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[0].length; j++) {
						board[i][j] = BLANK;
					}
				}
				turn = X_TURN;
			}
		}
	}

	public static void main(String[] args) {
		new TicTacToe();
	}

	public void printBoard() {
		System.out.println(" \t1\t2\t3"); // \t for tab.
		for (int row = 0; row < board.length; row++) {
			String output = (char) ('a' + row) + "\t"; // Must cast into character because would originally display as corresponding numbers on the ASCII chart. Add 1 to get one further down ASCII chart to get 'b' and 'c'.
			for (int column = 0; column < board[0].length; column++) {
				if (board[row][column] == BLANK) {
					output += " \t";
				} 
				else if (board[row][column] == X_MOVE) {
					output += "X\t";
				} 
				else if (board[row][column] == O_MOVE) {
					output += "O\t";
				}
			}
			System.out.println(output);
		}
	}
	
	public boolean checkWin(int player) { // Variable player that can be replaced by either X_MOVE or O_MOVE without creating two methods.
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
				if (board[row][column] == BLANK) { // Checks if all places in the board are filled up. If so, and there is no win, then the result is a tie.
					return false;
				}
			}
		}
		return true;	
	}
}