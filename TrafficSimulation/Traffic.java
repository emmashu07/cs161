/*
 * Emma Shu, 4/9/19: Class Traffic is the Traffic Simulation main class where the 
 * layout and buttons are set up, and where the project would be run. The 
 * class implements ActionListener and Runnable interfaces.
*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Traffic implements ActionListener, Runnable{

	JFrame frame = new JFrame("Traffic Simulation");
	Road road = new Road();
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	Container south = new Container();
	JButton addCompact = new JButton("Add Sports Car");
	JButton addSUV = new JButton("Add SUV");
	JButton addSemi = new JButton("Add Semi");
	Container west = new Container();
        JLabel throughput = new JLabel("Throughput: 0");
        int carCount = 0;
        long startTime = 0;

        
        boolean running = false;
	
	
	public Traffic() {
		frame.setSize(1000, 600);
		frame.setLayout(new BorderLayout());
		frame.add(road, BorderLayout.CENTER);
		
		south.setLayout(new GridLayout(1,2));
		south.add(start);
                start.addActionListener(this);
                south.add(stop);
                south.add(throughput);
                stop.addActionListener(this);
                frame.add(south, BorderLayout.SOUTH);
                
                west.setLayout(new GridLayout(3,1));
                west.add(addSemi);
                addSemi.addActionListener(this);
                west.add(addSUV);
                addSUV.addActionListener(this);
                west.add(addCompact);
                addCompact.addActionListener(this);
                frame.add(west, BorderLayout.WEST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.repaint();
	}
	
	public static void main (String args[]) {
            new Traffic();
	}

    @Override
    public void actionPerformed(ActionEvent event) { // Performs based on which button pressed.
        if (event.getSource().equals(start)) {
            if (running == false) {
                running = true;
                road.resetCarCount();
                startTime = System.currentTimeMillis();
                Thread t = new Thread(this);
                t.start();
            }
        }
        if (event.getSource().equals(stop)) {
            running = false;
        }
        if (event.getSource().equals(addSemi)) {
            Semi semi = new Semi (0,30);
            road.addCar(semi);
            for (int x = 0; x < road.ROAD_WIDTH; x += 20) {
                for (int y = 60; y < 600; y += 150) {
                        semi.setX(x);
                        semi.setY(y);
                    if(!road.collide(x, y, semi)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
        if (event.getSource().equals(addSUV)) {
            SUV suv = new SUV (0,30);
            road.addCar(suv);
            for (int x = 0; x < road.ROAD_WIDTH; x += 20) {
                for (int y = 60; y < 600; y += 150) {
                        suv.setX(x);
                        suv.setY(y);
                    if(!road.collide(x, y, suv)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
        if (event.getSource().equals(addCompact)) {
            Compact compact = new Compact (0,30);
            road.addCar(compact);
            for (int x = 0; x < road.ROAD_WIDTH; x += 20) {
                for (int y = 60; y < 600; y += 150) {
                        compact.setX(x);
                        compact.setY(y);
                    if(!road.collide(x, y, compact)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void run() { // From Runnable interface, runs program.
        while (running) {
            road.step();
            carCount = road.getCarCount();
            double throughputCalc = carCount / 
                    (1000 * (double) (System.currentTimeMillis() - startTime));
            throughput.setText("Throughput: " + throughputCalc);
            frame.repaint();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
