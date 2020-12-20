import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.time.LocalDateTime;
import java.util.Random;

/** Class that implements the Model of Covid City application */
public class SpreadModel extends GridWorldModel {

	// Constants for the grid objects
	public static final int JOB = 16;
	public static final int BAR = 32;
	public static final int HOSPITAL = 64;
	public static final int SPORTS = 128;
	public static final int SCHOOL = 256;
	public static final int PARK = 512;
	public static final int NHOMES = 6;
	public static int[] HOMES = new int[NHOMES];

	// A day in the system is turned into 30 seconds.
	public static final int DAY = 30;

	// Amount of youngs and adults
	public static final int NUMBER_OF_YOUNG = 2;
	public static final int NUMBER_OF_ADULT = 2;

	// Grid size
	public static int GSize = 50;

	// Object location
	Location lJob = new Location(4, 0);
	Location lBar = new Location(22, 40);
	Location lHospital = new Location(1, GSize - 2);
	Location lSports = new Location(5, 29);
	Location lSchool = new Location(14, 22);
	Location lPark = new Location(35, 12);
	Location[] lHomes = new Location[NHOMES];

	/**
	 * Class constructor
	 */
	public SpreadModel() {

		// Create a GSize grid with n mobile agents
		super(GSize, GSize, NUMBER_OF_YOUNG + NUMBER_OF_ADULT);

		int power_of_two = PARK;
		// Calculate parameters involving the homes locations.
		for (int i = 0; i < NHOMES; i++) {
			power_of_two *= 2;
			HOMES[i] = power_of_two;
		}

		int it = 0;
		Random rand = new Random();

		// Calculate the actual homes.
		while (it < NHOMES) {
			// Generate two random numbers avoiding the borders so the names are easily read
			// once in the grid.
			int x = rand.nextInt(48) + 1;
			int y = rand.nextInt(48) + 1;

			Location provisional_loc = new Location(x, y);

			// If the position was already taken by one of the static elements, abort.
			if (provisional_loc.equals(lJob) || provisional_loc.equals(lBar) || provisional_loc.equals(lHospital)
					|| provisional_loc.equals(lSports) || provisional_loc.equals(lPark)
					|| provisional_loc.equals(lSchool)) {

				continue;
			}

			int iit;
			// If the position was already taken by one of the dynamic elements, abort.
			for (iit = 0; iit < it; iit++) {
				if (provisional_loc.equals(lHomes[iit])) {
					iit = -1;
					break;
				}

			}
			if (iit == -1)
				continue;

			// If the location was free, positionate the new home.
			lHomes[it] = provisional_loc;

			it++;
		}

		// Agents location by order
		setAgPos(0, 13, 10);
		setAgPos(1, 3, 3);
		setAgPos(2, 12, 10);
		setAgPos(3, 11, 10);

		// Initial location of the objects
		add(JOB, lJob);
		add(BAR, lBar);
		add(HOSPITAL, lHospital);
		add(SPORTS, lSports);
		add(SCHOOL, lSchool);
		add(PARK, lPark);
		for (int i = 0; i < NHOMES; i++)
			add(HOMES[i], lHomes[i]);
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
			for (int i = 0; i < NHOMES; i++)
				view.update(lHomes[i].x, lHomes[i].y);
			view.update(lSports.x, lSports.y);
			view.update(lSchool.x, lSchool.y);
			view.update(lPark.x, lPark.y);
		}
		return true;
	}

}
