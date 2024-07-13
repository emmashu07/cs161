/*
 * Emma Shu, 4/9/19: Class SUV, overrides paintMe in Class Vehicle,
 * initializes/draws SUV.
*/

import java.awt.Color;
import java.awt.Graphics;

public class SUV extends Vehicle{
	
	public SUV(int x, int y) {
		super(x,y);
		width = 60;
		height = 30;
		speed = 8;
	}
	
	public void paintMe(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}
	
}
