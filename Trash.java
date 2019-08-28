package es;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Trash - a robot by Emma Shu: This code programs a team robot that will spin in large circles, sometimes in the
 * same direction, other times in circles that are adjacent to each other. This will make it hard to track.
 */
public class Trash extends TeamRobot 
{
	/**
	 * run: Trash's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		setColors(new Color (255,15,192),new Color (255,15,192),new Color (255,15,192)); // body, gun, radar

		// Robot main loop
		while(true) {
			// SpinBot: Mathew A. Nelson (original), Flemming N. Larsen (contributor)
			setTurnRight(100000); //Turn right for a lot of degrees continuously so the robot will spin forever.
			ahead(100000); 
			/**
			 * Keep on moving forwards continuously as well so the robot will turn in large circles 
			 * rather than spin around itself. 
			 */

		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if(!isTeammate(e.getName())) {
			fire(3); //Do not shoot the teammates, always shoot large and powerful bullets.
		}
	}
	/**
	 * onHitRobot: What to do when you hit another robot
	 */
	public void onHitRobot(HitRobotEvent e) {
		turnRight(90); //Once you hit another robot, you must turn to be able to continue going in circles.
	}
}
