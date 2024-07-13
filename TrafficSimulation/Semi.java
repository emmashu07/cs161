/*
 * Emma Shu, 4/9/19: Class Semi, overrides paintMe in Class Vehicle, 
 * initializes/draws semi truck.
*/

import java.awt.Color;
import java.awt.Graphics;

public class Semi extends Vehicle{
	
	public Semi(int x, int y) {
		super(x, y);
		width = 120;
		height = 40;
		speed = 5;
	}
	
	public void paintMe(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
}
