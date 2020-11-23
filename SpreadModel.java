import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
                        
/** class that implements the Model of Covid City application */
public class SpreadModel extends GridWorldModel {

	// Constants for the grid objects
	public static final int JOB = 16;
	public static final int BAR = 32;
	public static final int HOSPITAL = 64;
	public static final int HOME = 128; 
	
	public static final int NUMBER_OF_YOUNG = 2;
	public static final int NUMBER_OF_ADULT = 2;
	
	// Grid size
	public static int GSize = 30;
	
	// Object location
	Location lJob = new Location(4,0);
	Location lBar = new Location(28,7);
	Location lHospital = new Location(22,18);
    Location lHome  = new Location(GSize-1,GSize-1);
	
	
	public SpreadModel() {
        // create a GSize grid with n mobile agents
        super(GSize, GSize, 6);
		                
		// Agents location
		setAgPos(0, 13, 10);
		
		setAgPos(1, 3, 3);
		
		setAgPos(2, 12, 10);
		setAgPos(3, 11, 10); 
		/*
		setAgPos(4, 1, 1);
		setAgPos(5, 0, 0);
		*/
	
		// initial location of the objects
        add(JOB, lJob);
        add(BAR, lBar);
        add(HOSPITAL, lHospital);
        add(HOME, lHome);
    }
	
	
	boolean moveTowards(Location dest, int id) {
        Location lAgent = getAgPos(id);
        
		// X coord
		if (lAgent.x < dest.x){
			lAgent.x++;
		}else if (lAgent.x > dest.x){
			lAgent.x--;
		}
		
		// Y coord
        if (lAgent.y < dest.y){
			lAgent.y++;
        }else if (lAgent.y > dest.y){   
			lAgent.y--;
		}
		
        setAgPos(id, lAgent); // move the person in the grid
		
		//UpdateGrid()
		
                                              

                     
	
		
		   
		// repaint the locations
        if (view != null) {                             
            view.update(lJob.x,lJob.y);
            view.update(lBar.x,lBar.y);
			view.update(lHospital.x,lHospital.y);
			view.update(lHome.x,lHome.y);
        }
        return true;
    }


}
