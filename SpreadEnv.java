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
	public static final int WEEK 	= 7;
	public static final int WEEKEND = 8;
	// A day in the system is turned into 30 seconds.
	public static final int DAY	= 30;
	 
	public static final Literal yab = Literal.parseLiteral("at(young,bar)"); 
	public static final Literal yaj = Literal.parseLiteral("at(young,job)"); 
	public static final Literal yahos = Literal.parseLiteral("at(young,hospital)");        
	public static final Literal yahom = Literal.parseLiteral("at(young,home)");        
	
	public static final Literal aab = Literal.parseLiteral("at(adult,bar)"); 
	public static final Literal aaj = Literal.parseLiteral("at(adult,job)"); 
	public static final Literal aahos = Literal.parseLiteral("at(adult,hospital)");     
	public static final Literal aahom = Literal.parseLiteral("at(adult,home)"); 
	
	public static final Literal dweek = Literal.parseLiteral("is_day(WEEK)");  
	public static final Literal dweekend = Literal.parseLiteral("is_day(WEEKEND)"); 
	public static final Literal newday = Literal.parseLiteral("new_day"); 

	private static MAS2JProject project;


	/****************** ENV. METHODS ********************/
	
	/* Task to execute each day elapsed. Adds the newday perception */
    //TODO: Create and manage newday belief for all agents.
    private Runnable dayelapsed = () -> {

    	//Iterate all intial project agents adding newday belief
    	for (AgentParameters agent : project.getAgents()) addPercept(agent.name, newday);

    	//Debug log code, decide if remove in final version.
    	System.out.println("[DEBUG]: Newday belief added at -> " + new java.util.Date());
    };
	
	@Override
    public void init(String[] args) {
        model = new SpreadModel();

        if (args.length == 2 && args[0].equals("gui")) {
            view  = new SpreadView(model);
            model.setView(view);

            //Store initial parsed projects (to get a list of all initial agents)
            try{
            	jason.mas2j.parser.mas2j parser = new jason.mas2j.parser.mas2j(new FileInputStream(args[1]));
            	project = parser.mas();
            }
            catch (Exception e){
            	//Catch exceptions in project initialization
            	e.printStackTrace();
            }
            

        }

		updatePercepts();

		/* Creating a executor sevice to schedule a task that adds the newday belief each 'DAY' seconds elapsed */

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		//Schedule, dayelapsed->task to run, delay 0 to first execution, schedule each DAY seconds
        executorService.scheduleWithFixedDelay(dayelapsed, 0, DAY, TimeUnit.SECONDS);
    }

	
	
	public void updatePercepts() {
		// clear the percepts of the agents    
		clearPercepts("young");
		clearPercepts("adult");

		// get the Young location
		for(int i = 0; i < model.NUMBER_OF_YOUNG; i++){
			String sid = Integer.toString(i+1);      
			Location lyoung = model.getAgPos(i);
						   
			// add agent location to its percepts
			if (lyoung.equals(model.lBar)) {
				addPercept("young" + sid, yab);                          
			}
			else if (lyoung.equals(model.lJob)) {
				addPercept("young" + sid, yaj);
			}                                                    
			else if (lyoung.equals(model.lHospital)) {
				addPercept("young" + sid, yahos);
			}                  
			else if (lyoung.equals(model.lHome)) {
				addPercept("young" + sid, yahom);
			}    
					   
		}  
			                
		// get the Adult location
		for(int i = 0; i < model.NUMBER_OF_ADULT; i++){
			String sid = Integer.toString(i+1);      
			Location ladult = model.getAgPos(i+model.NUMBER_OF_YOUNG);  

			// add agent location to its percepts
			if (ladult.equals(model.lBar)) {
				addPercept("adult" + sid, aab);              
			}
			else if (ladult.equals(model.lJob)) {
				addPercept("adult" + sid, aaj);
			}                                                
			else if (ladult.equals(model.lHospital)) {
				addPercept("adult" + sid, aahos);
			}                  
			else if (ladult.equals(model.lHome)) {
				addPercept("adult" + sid, aahom);
			}                                                        
		}
		boolean week = this.isWeekDay();
		if (week){ 
			addPercept(dweek);
		}   else{
			addPercept(dweekend);
		}
	}
	
	public void calendars(){                 
		
	}
	                         
	public boolean isWeekDay()
	{
		LocalDateTime now = LocalDateTime.now();
		//Get the current number of seconds consumed of the current hour.
		int seconds = now.getMinute() * 60 + now.getSecond();
		//Get the actual date within the system.
		int current_day = (seconds / DAY) % 7;

		//Check if the current day is a week day.
		if (current_day == WEEK)
		{
			return true;
		}
		//Check if the current day is a weekend day.
		else
		{
			return false;
		}
	}
	
	@Override
    public boolean executeAction(String ag, Structure action) {
		   
		boolean result = false;
        System.out.println("["+ag+"] doing: "+action); 
		
		// Variables for getting agent id
		//String sid =  ag.substring(ag.length() - 1);    
		String sid;  
		int iid;  
		if(ag.startsWith("young")){   //Si es young, si ag es young1 el iid es 0
			sid = ag.substring(5, ag.length()); 
			iid =  Integer.parseInt(sid)-1;    
		}else{                     //Si es adult, si ag es adult2 el iid es NUMBER_OF_YOUNG + 1
		    sid = ag.substring(5, ag.length()); 
			iid =  Integer.parseInt(sid)-1 + model.NUMBER_OF_YOUNG;  
		}
		
        if (action.getFunctor().equals("move_towards")) {                                                                                 
            String l = action.getTerm(0).toString();
            Location dest = null;
            if (l.equals("bar")) {
                dest = model.lBar;
            }
		else if (l.equals("job")) {
               dest = model.lJob;
           }  
		   else if (l.equals("hospital")) {
               dest = model.lHospital;
           }         
		   else if (l.equals("home")) {
               dest = model.lHome;
           }                                        
            try {
                result = model.moveTowards(dest,iid);
	
            } catch (Exception e) {
                e.printStackTrace();
            }
                                                                                                                                     
        } 
	else {
		//What to do if action is not defined

	}

        if (result) {
            updatePercepts();
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
        return result;
    }
	
	
}
