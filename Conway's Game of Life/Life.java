/*10/23/18 Emma Shu: In Conway's Life, the program is to paint cells that are alive and remove cells
 *that have died based on the number of neighbors a cell has. The program contains three buttons that
 *either "Step" the generation up, "Start" the continuously running program, or "Stop" the program.
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Life implements MouseListener, ActionListener, Runnable {
	
	boolean [][] cells = new boolean [25][25];
	JFrame frame = new JFrame("Life Simulation");
	LifePanel panel = new LifePanel(cells);
	Container south = new Container();
	JButton step = new JButton("Step");
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	boolean running = false;
	
	public Life() {
		frame.setSize(600,600);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		panel.addMouseListener(this);
		
		// Sets buttons.
		south.setLayout(new GridLayout(1,3));
		south.add(step);
		step.addActionListener(this);
		south.add(start);
		start.addActionListener(this);
		south.add(stop);
		stop.addActionListener(this);
		frame.add(south, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Life();

	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		double width = (double)panel.getHeight()/ cells[0].length;
		double height = (double)panel.getWidth()/ cells.length;
		int column = Math.min(cells[0].length, (int)(event.getX()/ height));
		int row = Math.min(cells.length, (int)(event.getY() / width));
		cells[row][column] = !cells[row][column];
		frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent event) {	
	}

	@Override
	public void mouseExited(MouseEvent event) {	
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// Action performed for all three buttons.
		if (event.getSource().equals(step)) {
			step();
		}
		if (event.getSource().equals(start)) {
			if (running == false) {
				running = true;
				Thread t = new Thread(this);
				t.start();
			}
		}
		if (event.getSource().equals(stop)) {
			running = false;
		}
	}
	
	@Override
	public void run() {
		while (running == true) {
			step();
			try {
				Thread.sleep(500);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void step() { // Paints new cells based on neighbor count.
		boolean[][] nextCells = new boolean[cells.length][cells[0].length];
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				int cellCount = 0;
				if (row > 0 && column > 0 && cells[row-1][column-1] == true) {
					cellCount++;
				}
				if (row > 0 && cells[row-1][column] == true) {
					cellCount++;
				}
				if (row > 0 && column < cells[0].length-1 && cells[row-1][column+1] == true) {
					cellCount++;
				}
				if (column > 0 && cells[row][column-1] == true) {
					cellCount++;
				}
				if (column < cells[0].length-1 && cells[row][column+1] == true) {
					cellCount++;
				}
				if (row < cells.length-1 && column > 0 && cells[row+1][column-1] == true) {
					cellCount++;
				}
				if (row < cells.length-1 && cells[row+1][column] == true) {
					cellCount++;
				}
				if (row < cells.length-1 && column < cells.length-1 && cells[row+1][column+1] == true) {
					cellCount++;
				}
				if (cells[row][column] == true) {
					if (cellCount == 2 || cellCount == 3) {
						nextCells[row][column] = true;
					}
					else {
						nextCells[row][column] = false;
					}
				}
				else {
					if (cellCount == 3) {
						nextCells[row][column] = true;
					}
					else {
						nextCells[row][column] = false;
					}
				}
			}
		}
		cells = nextCells;
		panel.setCells(nextCells);
		frame.repaint();
	}
	
}

