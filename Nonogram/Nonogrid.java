/*Bobogrid paints the X and FILLED state of the class "Bobogram", as well as draws its grid lines. The size of the board
is determined primarily by the width. The following checks the state of the button that each square is assigned to, and either
fills it in, or X's it in. BLANK does not need to be set because every time the frame repaints, the default is blank.*/

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Nonogrid extends JPanel{
	
	//declare and initialize variables
	double width;
	double height;
	int[][] squares;
	final int X = 2;
	final int FILLED = 1;
	
	public Nonogrid(int[][] grid) {
		squares = grid;//make 2D array grid equal squares 
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width = (double)this.getWidth()/ squares.length; //width and height will be the same thing, since it's a square grid board. Type double
		height = (double)this.getHeight()/ squares.length;
		
		for (int row = 0; row < squares.length; row++) {//check through array. Modified Life Panel from Conway's Life
			for (int column = 0; column < squares[0].length; column++) {
				if (squares[row][column] == FILLED) {//if the state of the button is filled, fill square into pink
					g.setColor(Color.PINK);
					g.fillRect((int)Math.round(column*width),(int)Math.round(row*height),//must cast all double types over to integer
							(int)width + 1,(int)height + 1);				
				}
				if (squares[row][column] == X) {//if the state of the button is "X", draw a red X within the square
					g.setColor(Color.RED);
					//diagonal from top left to bottom right
					g.drawLine((int)Math.round(column * width), (int)Math.round(row * height), (int)(column * width + width), (int)(row * height + height));
					//diagonal from top right to bottom left
					g.drawLine((int)Math.round(column * width), (int)(row * height + height), (int)(column * width + width), (int)Math.round(row * height));
				}
			}
		}
		
		g.setColor(Color.BLACK); //draw grid lines depending on how large the width/height is. Modified Life Panel from Conway's Life
		for (int x = 0; x < squares[0].length + 1; x++) {
			g.drawLine((int)(Math.round(x*width)), 0, (int)(Math.round(x*width)), this.getHeight());
		}
		for (int y = 0; y < squares.length + 1; y++) {
			g.drawLine(0, (int)(Math.round(y*height)), this.getWidth(), (int)(Math.round(y*height)));
			
		
		}
	}
	
}