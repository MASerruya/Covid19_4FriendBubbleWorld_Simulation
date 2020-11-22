import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/** class that implements the View of Covid City application */
public class SpreadView extends GridWorldView {

    SpreadModel sModel;

    public SpreadView(SpreadModel model) {
        super(model, "Covid City", 1000);
        sModel = model;
        defaultFont = new Font("Arial", Font.BOLD, 14);
        setVisible(true);
        repaint();
    }
	
	
	/** draw application objects */
    @Override
    public void draw(Graphics g, int x, int y, int object) {
        //Location lYoung = sModel.getAgPos(0);
        super.drawAgent(g, x, y, Color.lightGray, -1);
		switch (object) {
			case SpreadModel.JOB:
				//if (lRobot.equals(hmodel.lFridge)) {
				//    super.drawAgent(g, x, y, Color.yellow, -1);
				//}
				//super.drawAgent(g, x, y, Color.blue, -1);
				g.setColor(Color.blue);
				drawString(g, x, y, defaultFont, "JOB");
				break;
			case SpreadModel.BAR:
				//if (lRobot.equals(hmodel.lOwner)) {
				//    super.drawAgent(g, x, y, Color.yellow, -1);
				//}
				//super.drawAgent(g, x, y, Color.orange, -1);
				g.setColor(Color.orange);
				drawString(g, x, y, defaultFont, "BAR");
				break;
			case SpreadModel.HOSPITAL:
				//if (lRobot.equals(hmodel.lOwner)) {
				//    super.drawAgent(g, x, y, Color.yellow, -1);
				//}
				//super.drawAgent(g, x, y, Color.red, -1);
				g.setColor(Color.red);
				drawString(g, x, y, defaultFont, "HOSPITAL");
				break;
			case SpreadModel.HOME:
				//if (lRobot.equals(hmodel.lOwner)) {
				//    super.drawAgent(g, x, y, Color.yellow, -1);
				//}
				//super.drawAgent(g, x, y, Color.green, -1);
				g.setColor(Color.green);
				drawString(g, x, y, defaultFont, "HOME");
				break;
			}		
			//repaint();
    }
	
	@Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        Location lAgent = sModel.getAgPos(id);
        //Location lAdult = sModel.getAgPos(1);	
		/*
		if(lAgent.equals(sModel.lJob)){				
			c = Color.yellow;
		} else if (lAgent.equals(sModel.lJob)){
			c = Color.blue;
		} else if (lAgent.equals(sModel.lBar)){
			c = Color.green;
		} else if (lAgent.equals(sModel.lHospital)){
			c = Color.green;
		} else if (lAgent.equals(sModel.lHome)){
			c = Color.red;
		} else {
			c = Color.pink;
		}*/
		/*super.drawAgent(g, x, y, c, -1);
		g.setColor(Color.black);*/
		
		if(id<5){ // Caso young
			if(lAgent.equals(sModel.lJob)){				
				c = Color.yellow;
			} else if (lAgent.equals(sModel.lJob)){
				c = Color.blue;
			} else if (lAgent.equals(sModel.lBar)){
				c = Color.green;
			} else if (lAgent.equals(sModel.lHospital)){
				c = Color.green;
			} else if (lAgent.equals(sModel.lHome)){
				c = Color.red;
			} else {
				c = Color.magenta;
			}
			super.drawAgent(g, x, y, c, -1);
			g.setColor(Color.black);
			super.drawString(g, x, y, defaultFont, "Young");
		
		} else { // Caso adult
			if(lAgent.equals(sModel.lJob)){				
				c = Color.yellow;
			} else if (lAgent.equals(sModel.lJob)){
				c = Color.blue;
			} else if (lAgent.equals(sModel.lBar)){
				c = Color.green;
			} else if (lAgent.equals(sModel.lHospital)){
				c = Color.green;
			} else if (lAgent.equals(sModel.lHome)){
				c = Color.red;
			} else {
				c = Color.pink;
			}
			super.drawAgent(g, x, y, c, -1);
			g.setColor(Color.black);
			super.drawString(g, x, y, defaultFont, "Adult");
		}
		
    }
	
}
