import jason.environment.grid.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** Class that implements the View of Covid City application */
public class SpreadView extends GridWorldView {

	SpreadModel sModel;

	// Variables for agent drawing management
	public String[] allAgents = null;
	public boolean[] allAgentsInfectionStatus = null;

	/**
	 * Class constructor
	 */
	public SpreadView(SpreadModel model) {
		super(model, "Covid City", 1000);
		sModel = model;
		defaultFont = new Font("Arial", Font.BOLD, 14);
		setVisible(true);
		repaint();
	}

	/**
	 * Draw applcation objects
	 * 
	 * @param g
	 * @param x      coord of the object
	 * @param y      coord of the object
	 * @param object id object to be drawn
	 */
	@Override
	public void draw(Graphics g, int x, int y, int object) {

		super.drawAgent(g, x, y, Color.lightGray, -1);

		// Painting each object
		switch (object) {

		//Set the color and the corresponding string to the JOB cell.
		case SpreadModel.JOB:
			g.setColor(Color.orange);
			drawString(g, x, y, defaultFont, "JOB");
			break;

		//Set the color and the corresponding string to the BAR cell.
		case SpreadModel.BAR:
			g.setColor(Color.orange);
			drawString(g, x, y, defaultFont, "BAR");
			break;

		//Set the color and the corresponding string to the HOSPITAL cell.
		case SpreadModel.HOSPITAL:
			g.setColor(Color.red);
			drawString(g, x, y, defaultFont, "HOSPITAL");
			break;

		//Set the color and the corresponding string to the SPORTS cell.
		case SpreadModel.SPORTS:
			g.setColor(Color.orange);
			drawString(g, x, y, defaultFont, "SPORTS");
			break;

		//Set the color and the corresponding string to the SCHOOL cell.
		case SpreadModel.SCHOOL:
			g.setColor(Color.orange);
			drawString(g, x, y, defaultFont, "SCHOOL");
			break;

		//Set the color and the corresponding string to the PARK cell.
		case SpreadModel.PARK:
			g.setColor(Color.orange);
			drawString(g, x, y, defaultFont, "PARK");
			break;

		default:

			//Set the color and the corresponding string to every HOME cell within the grid.
			for (int i = 0; i < sModel.NHOMES; i++) {
				if (object == SpreadModel.HOMES[i]) {
					g.setColor(Color.blue);
					drawString(g, x, y, defaultFont, "HOME_" + (i + 1));
					break;
				}
			}

			break;
		}
		repaint();
	}

	/**
	 * Draw agent
	 * 
	 * @param g
	 * @param x      coord of the agent
	 * @param y      coord of the agent
	 * @param object id agent to be drawn
	 */
	@Override
	public void drawAgent(Graphics g, int x, int y, Color c, int id) {

		if (id < 0) return;

		Location lAgent = sModel.getAgPos(id);

		//Paint the agent of red if it is infected.
		if (isInfected(id) == true) {
			c = Color.red;
		//Paint the agent of green if it is not infected.
		} else if (isInfected(id) == false) {
			c = Color.green;
		}

		super.drawAgent(g, x, y, c, -1);
		g.setColor(Color.black);

		if (id < sModel.NUMBER_OF_YOUNG) {

			super.drawString(g, x, y, defaultFont, "Y"); // Y for young agents.
		} else {
			super.drawString(g, x, y, defaultFont, "A"); // A for adult agents.

		}

	}

	/**
	 * Fill "allAgents" class variable with allAgents of SpreadEnv
	 * 
	 * @param arrayAgents "allAgents" array from SpreadEnv
	 */
	public void setAllAgents(String[] arrayAgents) {
		allAgents = arrayAgents;
	}

	/**
	 * Fill "allAgentsInfectionStatus" class variable with allAgentsInfectionStatus
	 * of SpreadEnv
	 * 
	 * @param arrayInfected "allAgentsInfectionStatus" array from SpreadEnv
	 */
	public void setInformationOfInfections(boolean[] arrayInfected) {
		allAgentsInfectionStatus = arrayInfected;
	}

	/**
	 * Comparing allAgents and allAgentsInfectionStatus arrays determine if an agent
	 * is infected or not for drawing with certain colors
	 * 
	 * @param id of the agent to be verified
	 */
	public boolean isInfected(int id) {

		if(allAgents == null || allAgentsInfectionStatus == null) return false;

		String sid = allAgents[id];

		//Check if the agent is infected.
		if (allAgentsInfectionStatus[id]) {
			return true;
		}
		return false;
	}

}
