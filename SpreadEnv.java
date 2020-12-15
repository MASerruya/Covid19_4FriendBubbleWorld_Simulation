import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import jason.mas2j.*;
import java.util.logging.Logger;
import java.time.LocalDateTime;

/*	Imports for periodical task schedulling */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;
//DEBUG
import java.util.*;

public class SpreadEnv extends Environment {

	/****************** CONSTANTS ********************/

	// Model of the grid
	SpreadModel model;
	SpreadView view;

	// Week days
	public static final int L = 0;
	public static final int M = 1;
	public static final int X = 2;
	public static final int J = 3;
	public static final int V = 4;
	public static final int S = 5;
	public static final int D = 6;
	// Cases to check if it is someday between L & V or between S & D.
	public static final int WEEK = 7;
	public static final int WEEKEND = 8;
	// A day in the system is turned into 30 seconds.
	public static final int DAY = 30;

	/******** LITERALS ************************/

	// Location young
	public static final Literal yab = Literal.parseLiteral("at(young,bar)");
	public static final Literal yaj = Literal.parseLiteral("at(young,job)");
	public static final Literal yahos = Literal.parseLiteral("at(young,hospital)");
	public static final Literal yahom = Literal.parseLiteral("at(young,home)");
	public static final Literal yasp = Literal.parseLiteral("at(young,sports)");
	public static final Literal yasch = Literal.parseLiteral("at(young,school)");
	public static final Literal yapk = Literal.parseLiteral("at(young,park)");
	// Location adult
	public static final Literal aab = Literal.parseLiteral("at(adult,bar)");
	public static final Literal aaj = Literal.parseLiteral("at(adult,job)");
	public static final Literal aahos = Literal.parseLiteral("at(adult,hospital)");
	public static final Literal aahom = Literal.parseLiteral("at(adult,home)");
	public static final Literal aasp = Literal.parseLiteral("at(adult,sports)");
	public static final Literal aasch = Literal.parseLiteral("at(adult,school)");
	public static final Literal aapk = Literal.parseLiteral("at(adult,park)");

	// Weekdays and time
	public static final Literal dweek = Literal.parseLiteral("is_week");
	public static final Literal dweekend = Literal.parseLiteral("is_weekend");
	public static final Literal ynewday = Literal.parseLiteral("new_day(young)");
	public static final Literal anewday = Literal.parseLiteral("new_day(adult)");

	public static final Literal lu = Literal.parseLiteral("is_monday");
	public static final Literal ma = Literal.parseLiteral("is_tuesday");
	public static final Literal mi = Literal.parseLiteral("is_wednesday");
	public static final Literal ju = Literal.parseLiteral("is_thursday");
	public static final Literal vi = Literal.parseLiteral("is_friday");
	public static final Literal sa = Literal.parseLiteral("is_saturday");
	public static final Literal dom = Literal.parseLiteral("is_sunday");

	// Infection
	public static final Literal yinf = Literal.parseLiteral("is_infected(young)");
	public static final Literal ainf = Literal.parseLiteral("is_infected(adult)");  
	
	//Recovered
	public static final Literal yrec = Literal.parseLiteral("recovered(young)");
	public static final Literal arec = Literal.parseLiteral("recovered(adult)"); 	

	// Responsability
	public static final Literal resp1 = Literal.parseLiteral("is_low_responsible");
	public static final Literal resp2 = Literal.parseLiteral("is_medium_responsible");
	public static final Literal resp3 = Literal.parseLiteral("is_high_responsible");
	public static final Literal[] respArray = { resp1, resp2, resp3 };

	private static MAS2JProject project;

	// Internal variable to keep track of the current day
	private int curr_day;

	/****************** ENV. METHODS ********************/

	/* Task to execute each day elapsed. Adds the newday perception */
	private Runnable dayelapsed = () -> {

		//Add newday perception for all agents
		clearAllPercepts();
		// Add newday perception for all agents

		curr_day = (curr_day + 1) % 7;
		updatePercepts();

		/* has to be done for all agents */
		addPercept("young1", ynewday);
		addPercept("young2", ynewday);
		addPercept("adult1", anewday);
		addPercept("adult2", anewday);

		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}

		// Debug log code, decide if remove in final version.
		System.out.println("[DEBUG]: Newday belief added at -> " + new java.util.Date());

		// DEBUG -- Print all percepts at this time
		Collection<Literal> allperc = consultPercepts("young1");
		System.out.println("PERCEPS FROM YOUNG1 " + " " + allperc);

	};

	/**
	 * Initiate the program
	 * 
	 * @param args
	 */
	@Override
	public void init(String[] args) {

		// Instance of SpreadModel class
		model = new SpreadModel();

		if (args.length == 2 && args[0].equals("gui")) {
			view = new SpreadView(model);
			model.setView(view);

			// Store initial parsed projects (to get a list of all initial agents)
			try {
				jason.mas2j.parser.mas2j parser = new jason.mas2j.parser.mas2j(new FileInputStream(args[1]));
				project = parser.mas();
			} catch (Exception e) {
				// Catch exceptions in project initialization
				e.printStackTrace();
			}
		}

		// Set initial day to monday
		curr_day = V;
		// addPercept(vi);
		// addPercept(dweek);

		// Update the percepts to all the agents
		updatePercepts();

		// Set the responsability degree to agents
		// TODO: get agentlist dinamically
		String[] allAgents = { "young1", "young2", "adult1", "adult2" };
		setResponsability(allAgents);

		// Infect the patients 0
		// addPercept("young1", yinf);
		String[] infectedAtBegining = { "young1" };
		infectAtBegining(infectedAtBegining);

		// Creating a executor sevice to schedule a task that adds the newday belief
		// each 'DAY' seconds elapsed
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		// Schedule, dayelapsed->task to run, delay DAY to first execution, schedule
		// each DAY seconds
		executorService.scheduleWithFixedDelay(dayelapsed, DAY, DAY, TimeUnit.SECONDS);
	}

	/**
	 * Update the percepts of the agent
	 * 
	 * @param ag  name of the agent which percepts will be cleared
	 * @param sid
	 * @param iid
	 */
	public void updatePercepts(String ag, String sid, int iid) {

		// Clear the percepts of the agent ag
		clearPercepts(ag);
		// clearPercepts("adult");
		// DEBUG
		System.out.println("Removed percepts: " + ag);

		addPercept(get_weeklit(curr_day));

		Location lagent = model.getAgPos(iid);

		if (ag.startsWith("young")) {
			// add agent location to its percepts
			if (lagent.equals(model.lBar)) {
				addPercept("young" + sid, yab);
				System.out.println("Added lbar percept!");
			} else if (lagent.equals(model.lJob)) {
				addPercept("young" + sid, yaj);
				System.out.println("Added ljob percept!");
			} else if (lagent.equals(model.lHospital)) {
				addPercept("young" + sid, yahos);
				System.out.println("Added lhosp percept!");
			} else if (lagent.equals(model.lHome)) {
				addPercept("young" + sid, yahom);
				System.out.println("Added lhome percept!");
			} else if (lagent.equals(model.lSports)) {
				addPercept("young" + sid, yasp);
				System.out.println("Added lSports percept!");
			} else if (lagent.equals(model.lSchool)) {
				addPercept("young" + sid, yasch);
				System.out.println("Added lSchool percept!");
			} else if (lagent.equals(model.lPark)) {
				addPercept("young" + sid, yapk);
				System.out.println("Added lPark percept!");
			}

		} else if (ag.startsWith("adult")) {
			// add agent location to its percepts
			if (lagent.equals(model.lBar)) {
				addPercept("adult" + sid, aab);
				System.out.println("[adult" + sid + "] Added lbar percept!");
			} else if (lagent.equals(model.lJob)) {
				addPercept("adult" + sid, aaj);
				System.out.println("[adult" + sid + "] Added ljob percept!");
			} else if (lagent.equals(model.lHospital)) {
				addPercept("adult" + sid, aahos);
				System.out.println("[adult" + sid + "] Added lhospital percept!");
			} else if (lagent.equals(model.lHome)) {
				addPercept("adult" + sid, aahom);
				System.out.println("[adult" + sid + "] Added lhome percept!");
			} else if (lagent.equals(model.lSports)) {
				addPercept("adult" + sid, aasp);
				System.out.println("[adult" + sid + "] Added lSports percept!");
			} else if (lagent.equals(model.lSchool)) {
				addPercept("adult" + sid, aasch);
				System.out.println("[adult" + sid + "] Added lSchool percept!");
			} else if (lagent.equals(model.lPark)) {
				addPercept("adult" + sid, aapk);
				System.out.println("[adult" + sid + "] Added lPark percept!");
			}
		}
	}

	/**
	 * Update the percepts for all the agents
	 */
	public void updatePercepts() {

		// clear the percepts of the agents
		clearAllPercepts();
		// clearPercepts("adult");
		// DEBUG
		System.out.println("Removed all percepts!");

		// Add the percept of the current day
		addPercept(get_weeklit(curr_day));

		// Add the percept of week/weekend
		if (curr_day < S) {
			addPercept(dweek);
		} else {
			System.out.println("WEEKEEEEEEEEEEEEEEEEEEEND");
			addPercept(dweekend);
		}
		// DEBUG -- Print all percepts at this time
		// Collection<Literal> allperc = consultPercepts("adult1");
		// System.out.println("************* PERCEPS FROM ADULT1: " + allperc);

		// Add location percept
		// get the Young location
		for (int i = 0; i < model.NUMBER_OF_YOUNG; i++) {
			String sid = Integer.toString(i + 1);
			Location lyoung = model.getAgPos(i);
			/*
			 * if (i == 0) { addPercept("young" + sid, yinf); }
			 */

			// add agent location to its percepts
			if (lyoung.equals(model.lBar)) {
				addPercept("young" + sid, yab);
				System.out.println("Added lbar percept!");
			} else if (lyoung.equals(model.lJob)) {
				addPercept("young" + sid, yaj);
				System.out.println("Added ljob percept!");
			} else if (lyoung.equals(model.lHospital)) {
				addPercept("young" + sid, yahos);
				System.out.println("Added lhosp percept!");
			} else if (lyoung.equals(model.lHome)) {
				addPercept("young" + sid, yahom);
				System.out.println("Added lhome percept!");
			} else if (lyoung.equals(model.lSports)) {
				addPercept("young" + sid, yasp);
				System.out.println("Added lSports percept!");
			} else if (lyoung.equals(model.lSchool)) {
				addPercept("young" + sid, yasch);
				System.out.println("Added lSchool percept!");
			} else if (lyoung.equals(model.lPark)) {
				addPercept("young" + sid, yapk);
				System.out.println("Added lSchool percept!");
			}
		}

		// get the Adult location
		for (int i = 0; i < model.NUMBER_OF_ADULT; i++) {
			String sid = Integer.toString(i + 1);
			Location ladult = model.getAgPos(i + model.NUMBER_OF_YOUNG);

			// add agent location to its perceps
			if (ladult.equals(model.lBar)) {
				addPercept("adult" + sid, aab);
				System.out.println("[adult" + sid + "] Added lbar percept!");
			} else if (ladult.equals(model.lJob)) {
				addPercept("adult" + sid, aaj);
				System.out.println("[adult" + sid + "] Added ljob percept!");
			} else if (ladult.equals(model.lHospital)) {
				addPercept("adult" + sid, aahos);
				System.out.println("[adult" + sid + "] Added lhospital percept!");
			} else if (ladult.equals(model.lHome)) {
				addPercept("adult" + sid, aahom);
				System.out.println("[adult" + sid + "] Added lhome percept!");
			} else if (ladult.equals(model.lSports)) {
				addPercept("adult" + sid, aasp);
				System.out.println("[adult" + sid + "] Added lSports percept!");
			} else if (ladult.equals(model.lSchool)) {
				addPercept("adult" + sid, aasch);
				System.out.println("[adult" + sid + "] Added lSchool percept!");
			} else if (ladult.equals(model.lPark)) {
				addPercept("adult" + sid, aapk);
				System.out.println("[adult" + sid + "] Added lPark percept!");
			}
		}
	}

	public void updatePosition(String ag, String sid, int iid) {

		Location lagent = model.getAgPos(iid);

		if (ag.startsWith("young")) {
			// add agent location to its percepts
			if (lagent.equals(model.lBar)) {
				addPercept("young" + sid, yab);
				System.out.println("Added lbar percept!");
			} else if (lagent.equals(model.lJob)) {
				addPercept("young" + sid, yaj);
				System.out.println("Added ljob percept!");
			} else if (lagent.equals(model.lHospital)) {
				addPercept("young" + sid, yahos);
				System.out.println("Added lhosp percept!");
			} else if (lagent.equals(model.lHome)) {
				addPercept("young" + sid, yahom);
				System.out.println("Added lhome percept!");
			} else if (lagent.equals(model.lSports)) {
				addPercept("young" + sid, yasp);
				System.out.println("Added lSports percept!");
			} else if (lagent.equals(model.lSchool)) {
				addPercept("young" + sid, yasch);
				System.out.println("Added lSchool percept!");
			} else if (lagent.equals(model.lPark)) {
				addPercept("young" + sid, yapk);
				System.out.println("Added lPark percept!");
			}

		} else if (ag.startsWith("adult")) {
			// add agent location to its percepts
			if (lagent.equals(model.lBar)) {
				addPercept("adult" + sid, aab);
				System.out.println("[adult" + sid + "] Added lbar percept!");
			} else if (lagent.equals(model.lJob)) {
				addPercept("adult" + sid, aaj);
				System.out.println("[adult" + sid + "] Added ljob percept!");
			} else if (lagent.equals(model.lHospital)) {
				addPercept("adult" + sid, aahos);
				System.out.println("[adult" + sid + "] Added lhospital percept!");
			} else if (lagent.equals(model.lHome)) {
				addPercept("adult" + sid, aahom);
				System.out.println("[adult" + sid + "] Added lhome percept!");
			} else if (lagent.equals(model.lSports)) {
				addPercept("adult" + sid, aasp);
				System.out.println("[adult" + sid + "] Added lSports percept!");
			} else if (lagent.equals(model.lSchool)) {
				addPercept("adult" + sid, aasch);
				System.out.println("[adult" + sid + "] Added lSchool percept!");
			} else if (lagent.equals(model.lPark)) {
				addPercept("adult" + sid, aapk);
				System.out.println("[adult" + sid + "] Added lPark percept!");
			}
		}
	}

	/**
	 * Excecution of the action
	 * 
	 * @param ag
	 * @param action
	 */
	@Override
	public boolean executeAction(String ag, Structure action) {

		// DEBUG -- Prior to action, percepts
		Collection<Literal> allpercb = consultPercepts(ag);
		System.out.println("PERCEPS FROM BEFORE " + ag + " " + allpercb);

		boolean result = false;
		System.out.println("[" + ag + "] doing: " + action);

		// Variables for getting agent id
		// String sid = ag.substring(ag.length() - 1);                                                                                
		String sid;
		int iid;
		if (ag.startsWith("young")) { // Si es young, si ag es young1 el iid es 0
			sid = ag.substring(5, ag.length());
			iid = Integer.parseInt(sid) - 1;
		} else { // Si es adult, si ag es adult2 el iid es NUMBER_OF_YOUNG + 1
			sid = ag.substring(5, ag.length());
			iid = Integer.parseInt(sid) - 1 + model.NUMBER_OF_YOUNG;
		}
		
		
		if (action.getFunctor().equals("do_things")) {  
			
			try
			{
				Thread.sleep(300);
			}                                                                                                                    
			catch (Exception e) {
			      
			}   
			
			String l = action.getTerm(0).toString();
			boolean infected = false;                    
			double randomNum = Math.random();
			
			if (l.equals("bar")) {
				if (randomNum < 0.3){ 
					infected = true;    
				}                            
			} else if (l.equals("job")) {
				if (randomNum < 0.05){ 
					infected = true;    
				}                            
			} else if (l.equals("sports")) {
				if (randomNum < 0.1){ 
					infected = true;    
				}                            
			} else if (l.equals("school")) {
				if (randomNum < 0.1){ 
					infected = true;    
				}                            
			} else if (l.equals("park")) {
				if (randomNum < 0.1){ 
					infected = true;    
				}                            
			} 
			
			if (infected){
				if (ag.startsWith("young")){  
					addPercept("young"+sid, yinf);
				}else{
					addPercept("adult"+sid, ainf);
				}
			}             
			
			result = true;
			                                                                                     
		}
		else if (action.getFunctor().equals("quarentine")){   
			removePercept("young"+sid, yaj);    
			addPercept("young"+sid, yrec);  
			                
		}           
		else if (action.getFunctor().equals("move_towards")) {
			String l = action.getTerm(0).toString();
			Location dest = null;
			if (l.equals("bar")) {
				dest = model.lBar;
			} else if (l.equals("job")) {
				dest = model.lJob;
			} else if (l.equals("hospital")) {
				dest = model.lHospital;
			} else if (l.equals("home")) {
				dest = model.lHome;
			} else if (l.equals("sports")) {
				dest = model.lSports;
			} else if (l.equals("school")) {
				dest = model.lSchool;
			} else if (l.equals("park")) {
				dest = model.lPark;
			}
			try {
				result = model.moveTowards(dest, iid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			//Remove the literal determining the starting position.
			if (ag.startsWith("young"))
			{
				if (containsPercept(ag, yab)) {
					removePercept(ag, yab);
				} else if (containsPercept(ag, yaj)) {
					removePercept(ag, yaj);
				} else if (containsPercept(ag, yahos)) {
					removePercept(ag, yahos);
				} else if (containsPercept(ag, yahom)) {
					removePercept(ag, yahom);
				} else if (containsPercept(ag, yasp)) {
					removePercept(ag, yasp);
				} else if (containsPercept(ag, yasch)) {
					removePercept(ag, yasch);
				} else if (containsPercept(ag, yapk)) {
					removePercept(ag, yapk);
				}
			}
			else {
				if (containsPercept(ag, aab)) {
					removePercept(ag, aab);
				} else if (containsPercept(ag, aaj)) {
					removePercept(ag, aaj);
				} else if (containsPercept(ag, aahos)) {
					removePercept(ag, aahos);
				} else if (containsPercept(ag, aahom)) {
					removePercept(ag, aahom);
				} else if (containsPercept(ag, aasp)) {
					removePercept(ag, aasp);
				} else if (containsPercept(ag, aasch)) {
					removePercept(ag, aasch);
				} else if (containsPercept(ag, aapk)) {
					removePercept(ag, aapk);
				}
			}

			try
			{
				Thread.sleep(100);
			}
			catch (Exception e) {}     

			//Add the literal determining the new position.
			updatePosition(ag, sid, iid);

		} else {
			// What to do if action is not defined
		}

//		if (result) {
//			updatePercepts(ag, sid, iid);
//			try {
//				Thread.sleep(100);
//			} catch (Exception e) {
//			}
//		}

		// DEBUG -- After action, percepts
		allpercb = consultPercepts(ag);
		System.out.println("PERCEPS AFTER FROM " + ag + " " + allpercb);

		return result;
	}

	/**
	 * Auxiliar function to translate numbers into weeday literals
	 * 
	 * @param ag wday
	 * @return result of the int to literal convertion
	 */
	private Literal get_weeklit(int wday) {

		// Translate number to literal
		switch (wday) {
		case L:
			return lu;
		case M:
			return ma;
		case X:
			return mi;
		case J:
			return ju;
		case V:
			return vi;
		case S:
			return sa;
		case D:
			return dom;
		}

		// For other number error
		System.out.println("[ERROR] Invalid weeday internal record.");
		System.exit(-1);

		return Literal.parseLiteral("");
	}

	/**
	 * Method to define which agents are infected at the begining
	 * 
	 * @param ag agents to be infected
	 */
	private void infectAtBegining(String[] ags) {

		// Verify not empty array
		if (ags.length > 0) {

			for (int k = 0; k < ags.length; k++) {

				if (ags[k].startsWith("young")) { // Young case
					addPercept(ags[k], yinf);

				} else if (ags[k].startsWith("adult")) { // Adult case
					addPercept(ags[k], ainf);
				}
			}
		}
	}

	/**
	 * Method that defines randomly how responsible an agent is
	 * 
	 * @param ag agent to be set
	 */
	private void setResponsability(String[] ags) {

		// Verify not empty array
		if (ags.length > 0) {

			Random rand = new Random();
			int value = 0;

			for (int k = 0; k < ags.length; k++) {
				// TODO: Asignar probabilidades a que sea High, Medium o Low según cada tipo de
				// agente
				if (ags[k].startsWith("young")) { // Young case: 20%High, 40%Medium, 40%Low
					value = rand.nextInt(2);
					addPercept(ags[k], respArray[value]);

				} else if (ags[k].startsWith("adult")) { // Adult case: 40%High, 35%Medium, 25%Low
					value = rand.nextInt(2);
					addPercept(ags[k], respArray[value]);
				}
			}
		}
	}

}                                                                                                                                       
