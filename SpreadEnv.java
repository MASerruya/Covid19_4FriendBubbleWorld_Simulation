import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class SpreadEnv extends Environment {


    SpreadModel model; // the model of the grid

	@Override
    public void init(String[] args) {
        model = new SpreadModel();

        if (args.length == 1 && args[0].equals("gui")) {
            SpreadView view  = new SpreadView(model);
            model.setView(view);
        }
    }
}
