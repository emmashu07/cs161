/*
 * Emma Shu, 4/9/19: Class Vehicle is a parent class to all the types of 
 * vehicles, initializing method paintMe that will be overridden in the other
 * vehicle classes.
*/

import java.awt.Graphics;

public class Vehicle {

	int x; 
	int width;
	int y;
	int height;
	int speed;
	
	public Vehicle(int x, int y) {
            this.x = x;
            this.y = y;	
	}

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public void paintMe(Graphics g) { // Overriden in child classes.
        }
	
}
