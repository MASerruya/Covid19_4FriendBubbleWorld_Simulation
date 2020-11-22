import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class SpreadEnv extends Environment {
    
	// Model of the grid
	SpreadModel model;
	SpreadView view;
	
	@Override
    public void init(String[] args) {
        model = new SpreadModel();

        if (args.length == 1 && args[0].equals("gui")) {
            view  = new SpreadView(model);
            model.setView(view);
			//view.update();
        }
		
        // Agent to location
		// get the agent location
        Location lAgent = model.getAgPos(0);
        while (!lAgent.equals(model.lBar)) {
			model.moveTowards(model.lBar, 5);
			try {
                Thread.sleep(400);
            } catch (Exception e) {}
        }
    }
}
