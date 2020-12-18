import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.time.LocalDateTime;

/** Class that implements the Model of Covid City application */
public class SpreadModel extends GridWorldModel {

	// Constants for the grid objects
	public static final int JOB = 16;
	public static final int BAR = 32;
	public static final int HOSPITAL = 64;
	public static final int SPORTS = 128;
	public static final int SCHOOL = 256;
	public static final int PARK = 512;
	public static final int HOME1 = 1024;
	public static final int HOME2 = 2048;
	public static final int HOME3 = 4096;
	public static final int HOME4 = 8192;
	public static final int HOME5 = 16384;

	// Week days
	/*
	 * public static final int L = 0; public static final int M = 1; public static
	 * final int X = 2; public static final int J = 3; public static final int V =
	 * 4; public static final int S = 5; public static final int D = 6; // Cases to
	 * check if it is someday between L & V or between S & D. public static final
	 * int WEEK = 7; public static final int WEEKEND = 8;
	 */

	// A day in the system is turned into 30 seconds.
	public static final int DAY = 30;

	public static final int NUMBER_OF_YOUNG = 2;
	public static final int NUMBER_OF_ADULT = 2;

	// Grid size
	public static int GSize = 50;

	// Object location
	Location lJob 		= new Location(4, 0);
	Location lBar 		= new Location(22, 7);
	Location lHospital 	= new Location(1, GSize - 2);
	Location lHome1 	= new Location(GSize - 3, GSize - 3);
	Location lHome2 	= new Location(10, 1);
	Location lHome3 	= new Location(3, 38);
	Location lHome4 	= new Location(47, 20);
	Location lHome5 	= new Location(24, 45);
	Location lSports 	= new Location(5, 19);
	Location lSchool 	= new Location(3, 7);
	Location lPark 		= new Location(15, 12);

	/**
	 * Class constructor
	 */
	public SpreadModel() {

		// Create a GSize grid with n mobile agents
		super(GSize, GSize, NUMBER_OF_YOUNG + NUMBER_OF_ADULT);

		// Agents location by order
		setAgPos(0, 13, 10);
		setAgPos(1, 3, 3);
		setAgPos(2, 12, 10);
		setAgPos(3, 11, 10);
		// setAgPos(4, 1, 1);
		// setAgPos(5, 0, 0);

		// Initial location of the objects
		add(JOB, lJob);
		add(BAR, lBar);
		add(HOSPITAL, lHospital);
		add(HOME1, lHome1);
		add(HOME2, lHome2);
		add(HOME3, lHome3);
		add(HOME4, lHome4);
		add(HOME5, lHome5);
		add(SPORTS, lSports);
		add(SCHOOL, lSchool);
		add(PARK, lPark);
	}

	/**
	 * Moves the agent to a destination
	 * 
	 * @param dest destination
	 * @param id   agent to be moved
	 * @return boolean when the movement has finished
	 */
	boolean moveTowards(Location dest, int id) {

		Location lAgent = getAgPos(id);

		// Just moves in X coord
		if (lAgent.x < dest.x && lAgent.y == dest.y) {
			lAgent.x++;
		} else if (lAgent.x > dest.x && lAgent.y == dest.y) {
			lAgent.x--;
		}
		// Just moves in Y coord
		else if (lAgent.y < dest.y && lAgent.x == dest.x) {
			lAgent.y++;
		} else if (lAgent.y > dest.y && lAgent.x == dest.x) {
			lAgent.y--;
		} else {
			int randomNum = (int) ((Math.random() * 3 + id)) % 3;
			if (randomNum == 0 || randomNum == 2) {
				if (lAgent.y < dest.y) {
					lAgent.y++;
				} else {
					lAgent.y--;
				}
			}
			if (randomNum == 1 || randomNum == 2) {
				if (lAgent.x < dest.x) {
					lAgent.x++;
				} else {
					lAgent.x--;
				}
			}
		}

		// Move the agent in the grid
		setAgPos(id, lAgent);
		// UpdateGrid()

		// Repaint the locations
		if (view != null) {
			view.update(lJob.x, lJob.y);
			view.update(lBar.x, lBar.y);
			view.update(lHospital.x, lHospital.y);
			view.update(lHome1.x, lHome1.y);
			view.update(lHome2.x, lHome2.y);
			view.update(lHome3.x, lHome3.y);
			view.update(lHome4.x, lHome4.y);
			view.update(lHome5.x, lHome5.y);
			view.update(lSports.x, lSports.y);
			view.update(lSchool.x, lSchool.y);
			view.update(lPark.x, lPark.y);
		}
		return true;
	}

}
