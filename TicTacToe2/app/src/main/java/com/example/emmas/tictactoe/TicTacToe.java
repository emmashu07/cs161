// Emma Shu: Android TicTacToe is an Android App that allows the user to play TicTacToe against and AI that should actively try to win the game.

package com.example.emmas.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class TicTacToe extends AppCompatActivity implements View.OnClickListener {

    Button[][] grid = new Button[3][3];
    int[][] board = new int[3][3];
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        grid[0][0] = (Button)this.findViewById(R.id.button1);
        grid[0][1] = (Button)this.findViewById(R.id.button2);
        grid[0][2] = (Button)this.findViewById(R.id.button3);
        grid[1][0] = (Button)this.findViewById(R.id.button4);
        grid[1][1] = (Button)this.findViewById(R.id.button5);
        grid[1][2] = (Button)this.findViewById(R.id.button6);
        grid[2][0] = (Button)this.findViewById(R.id.button7);
        grid[2][1] = (Button)this.findViewById(R.id.button8);
        grid[2][2] = (Button)this.findViewById(R.id.button9);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                grid[x][y].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                // Player selects a place and the AI moves to counter it.
                if(b.equals(grid[x][y])) {
                    if (board[x][y] == BLANK) {
                        b.setText("X");
                        b.setEnabled(false);
                        board[x][y] = X_MOVE;
                        if(checkWin(X_MOVE)) {
                            Toast.makeText(this, "X Wins!", Toast.LENGTH_SHORT).show();
                            clearBoard();
                        }
                        else if (checkTie()) {
                            clearBoard();
                        }
                        aiMove();
                        if (checkWin(O_MOVE)) {
                            Toast.makeText(this, "O Wins!", Toast.LENGTH_SHORT).show();
                            clearBoard();
                        }
                        else if (checkTie()) {
                            clearBoard();
                        }
                    }
                }
            }
        }
    }

    public boolean checkTie() {
        // Check for tie.
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == BLANK) { // Checks if all places in the board are filled up. If so, and there is no win, then the result is a tie.
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin(int player) {
        // Check for wins, for either X or O.
        if (board[1][1] == player && board[0][0] == player && board[2][2] == player) {
            return true;
        } else if (board[1][1] == player && board[2][0] == player && board[0][2] == player) {
            return true;
        } else if (board[1][1] == player && board[0][1] == player && board[2][1] == player) {
            return true;
        } else if (board[1][1] == player && board[1][0] == player && board[1][2] == player) {
            return true;
        } else if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
            return true;
        } else if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
            return true;
        } else if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
            return true;
        } else if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
            return true;
        }
        return false;
    }

    public void clearBoard() {
        for (int a = 0; a < board.length; a++) {
            for (int b = 0; b < board[0].length; b++) {
                board[a][b] = BLANK;
                grid[a][b].setText("");
                grid[a][b].setEnabled(true); // Re-enables disabled buttons after a win or tie.
            }
        }
    }

    public void aiMove() {
        // Check for possible O moves that would win the game.
        if (checkSingleBlank(O_MOVE)) {
            return;
        }
        // Check to block X moves that might win the game.
        if (checkSingleBlank(X_MOVE)) {
            return;
        }
        // Pick randomly.
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == BLANK) {
                    list.add(x*10 + y);
                }
            }
        }
        int choice = (int)(Math.random() * list.size());
        board[list.get(choice) / 10][list.get(choice) % 10] = O_MOVE;
        grid[list.get(choice) / 10][list.get(choice) % 10].setText("O");
        grid[list.get(choice) / 10][list.get(choice) % 10].setEnabled(false);
    }

    public boolean checkSingleBlank(int player) {
        // Method to check for either X moves to block or O moves to win.
        int blankCount = 0;
        int oCount = 0;
        int blankX = 0;
        int blankY = 0;
        // Block columns.
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player) {
                    oCount++;
                }
                if (oCount == 2 && blankCount == 1) {
                    board[blankX][blankY] = O_MOVE;
                    grid[blankX][blankY].setText("O");
                    grid[blankX][blankY].setEnabled(false);
                    return true;
                }
            }
        }
        // Block rows.
        for (int y = 0; y < 3; y++) {
            blankCount = 0;
            oCount = 0;
            blankX = 0;
            blankY = 0;
            for (int x = 0; x < 3; x++) {
                if (board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player) {
                    oCount++;
                }
                if (oCount == 2 && blankCount == 1) {
                    board[blankX][blankY] = O_MOVE;
                    grid[blankX][blankY].setText("O");
                    grid[blankX][blankY].setEnabled(false);
                    return true;
                }
            }
        }
        // Block diagonal from top left to bottom right.
        blankCount = 0;
        oCount = 0;
        blankX = 0;
        blankY = 0;
        if(board[0][0] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0;
        }
        else if (board[0][0] == player) {
            oCount++;
        }
        if(board[1][1] == BLANK) {
            blankCount++;
            blankX = 1;
            blankY = 1;
        }
        else if (board[1][1] == player) {
            oCount++;
        }
        if(board[2][2] == BLANK) {
            blankCount++;
            blankX = 2;
            blankY = 2;
        }
        else if (board[2][2] == player) {
            oCount++;
        }
        if (oCount == 2 && blankCount == 1) {
            board[blankX][blankY] = O_MOVE;
            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
            return true;
        }
        // Block diagonal from bottom left to top right.
        blankCount = 0;
        oCount = 0;
        blankX = 0;
        blankY = 0;
        if(board[2][0] == BLANK) {
            blankCount++;
            blankX = 2;
            blankY = 0;
        }
        else if (board[2][0] == player) {
            oCount++;
        }
        if(board[1][1] == BLANK) {
            blankCount++;
            blankX = 1;
            blankY = 1;
        }
        else if (board[1][1] == player) {
            oCount++;
        }
        if(board[0][2] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 2;
        }
        else if (board[0][2] == player) {
            oCount++;
        }
        if (oCount == 2 && blankCount == 1) {
            board[blankX][blankY] = O_MOVE;
            grid[blankX][blankY].setText("O");
            grid[blankX][blankY].setEnabled(false);
            return true;
        }
        return false;
    }
}
