/*
 * Emma Shu, 4/9/19: Class Road is where the road will be drawn and where
 * the call to the other paintMe methods are located. Class road includes 
 * methods such as step and collide that are used in the main class.
*/

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Road extends JPanel{

    final int LANE_HEIGHT = 150;
    final int ROAD_WIDTH = 800;
    int carCount = 0;
    ArrayList<Vehicle> cars = new ArrayList<Vehicle>();

    public Road() {
            super();
    }

    public void addCar(Vehicle v) {
            cars.add(v);
    }

    public void paintComponent(Graphics g) { // Draws road and calls to method that draws cars.
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.WHITE);
            for (int a = LANE_HEIGHT; a < LANE_HEIGHT * 4; a += LANE_HEIGHT) {
                    for (int b = 0; b < getWidth(); b += 40) {
                            g.fillRect(b, a, 30, 5);
                    }
            }
            for (int a = 0; a < cars.size(); a++) {
                    cars.get(a).paintMe(g);
            }
    }
    
    public void step() { // Each iteration of a run.
        for (int a = 0; a < cars.size(); a++) {
            Vehicle v = cars.get(a);
            if (!collide(v.getX() + v.getSpeed(), v.getY(), v)) {
                v.setX(v.getX() + v.getSpeed());
                if (v.getX() > ROAD_WIDTH) {
                    if (!collide(0, v.getY(), v)) {
                        v.setX(0);
                        carCount++;
                    }
                }
            } 
            else {
                if ((v.getY() - LANE_HEIGHT > 40) && (!collide(v.getX(), v.getY() - LANE_HEIGHT, v))) {
                    v.setY(v.getY() - LANE_HEIGHT);
                } 
                else if ((v.getY() + LANE_HEIGHT < 40 + 3 * LANE_HEIGHT) && (!collide(v.getX(), v.getY() + LANE_HEIGHT, v))) {
                    v.setY(v.getY() + LANE_HEIGHT);
                }
            }
        }
    }
    
    public boolean collide(int x, int y, Vehicle v) { // Detect collisions.
        for (int a = 0; a < cars.size(); a++) {
            Vehicle u = cars.get(a);
            if (y == u.getY()) {
                if (!u.equals(v)) {
                    if (x < u.getX() + u.getWidth() && 
                            u.getX() < x + v.getWidth()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public int getCarCount() {
        return carCount;
    }

    public void resetCarCount() {
        carCount = 0;
    }
}
