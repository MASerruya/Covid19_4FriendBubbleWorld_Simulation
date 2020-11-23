import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class SpreadEnv extends Environment {
    
	// Model of the grid
	SpreadModel model;
	SpreadView view;
	
	public static final Literal yab = Literal.parseLiteral("at(young,bar)"); 
	public static final Literal yaj = Literal.parseLiteral("at(young,job)"); 
	public static final Literal yahos = Literal.parseLiteral("at(young,hospital)");
	public static final Literal yahom = Literal.parseLiteral("at(young,home)");   
	
	@Override
    public void init(String[] args) {
        model = new SpreadModel();

        if (args.length == 1 && args[0].equals("gui")) {
            view  = new SpreadView(model);
            model.setView(view);
        }

		updatePercepts();
    }
	
	    void updatePercepts() {
			// clear the percepts of the agents    
			clearPercepts("young");
	
			// get the robot location
			for(int i = 0; i < 2; i++){
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
		}
	
	@Override
    public boolean executeAction(String ag, Structure action) {
		                                                                                                                                  
        System.out.println("["+ag+"] doing: "+action); 
		String sid =  ag.substring(ag.length() - 1);   
		int iid =  Integer.parseInt(sid)-1;
		
		
        boolean result = false;
        if (action.getFunctor().equals("move_towards")) {
            String l = action.getTerm(0).toString();
            Location dest = null;
            if (l.equals("bar")) {
                dest = model.lBar;
            }
            try {
                result = model.moveTowards(dest,iid);
	
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            //logger.info("Failed to execute action "+action);
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
