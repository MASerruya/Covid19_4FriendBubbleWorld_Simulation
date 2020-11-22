import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class SpreadEnv extends Environment {
    
	// Model of the grid
	SpreadModel model;
	SpreadView view;
	
	public static final Literal yab = Literal.parseLiteral("at(young,bar)");
	
	@Override
    public void init(String[] args) {
        model = new SpreadModel();

        if (args.length == 1 && args[0].equals("gui")) {
            view  = new SpreadView(model);
            model.setView(view);
			//view.update();
        }
	
    }
	
	    void updatePercepts() {
        // clear the percepts of the agents
        clearPercepts("young");

        // get the robot location
        Location lyoung = model.getAgPos(0);

        // add agent location to its percepts
        if (lyoung.equals(model.lBar)) {
            addPercept("young", yab);
        }
    }
	
	@Override
    public boolean executeAction(String ag, Structure action) {
        System.out.println("["+ag+"] doing: "+action);
        boolean result = false;
        if (action.getFunctor().equals("move_towards")) {
            String l = action.getTerm(0).toString();
            Location dest = null;
            if (l.equals("bar")) {
                dest = model.lBar;
            }
            try {
                result = model.moveTowards(dest,0);
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
