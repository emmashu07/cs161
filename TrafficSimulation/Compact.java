/*
 * Emma Shu, 4/9/19: Class Compact, overrides paintMe in Class Vehicle, 
 * initializes/draws compact/sports car.
*/

import java.awt.Color;
import java.awt.Graphics;

public class Compact extends Vehicle{

	public Compact(int x, int y) {
		super(x,y);
		width = 40;
		height = 20;
		speed = 12;
	}
	
	public void paintMe(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}
}
