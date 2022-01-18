import java.awt.Color;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/* The bSim class is the main class in the program.
 * It runs the actual simulation, as well as randomly generates size, speed, etc. of each ball.
 * It also creates the GLabel that prompts the user to click, and the label after that.
 */

public class bSim extends GraphicsProgram {

	private static final int WIDTH = 1200;		// width of the window
	private static final int HEIGHT = 600;		// height of the window
	private static final int OFFSET = 200;		// height of the horizon line (where we want the bottom of the simulation to be)
	private static final int NUMBALLS = 100;		// number of balls to generate
	private static final double SCALE = HEIGHT/100;		// scale of the coordinate system (pixels/meter)
	private static final double MINSIZE = 1;		// min ball radius (meters)
	private static final double MAXSIZE = 7;		// max ball radius (meters)
	private static final double EMIN = 0.7;		// min energy loss
	private static final double EMAX = 0.9;		// max energy loss 
	private static final double ViMIN = 25.0;		// min velocity (m/s)
	private static final double ViMAX = 45.0;		// max velocity (m/s)
	private static final double ThetaMIN = 80.0;		// min launch angle (degrees)
	private static final double ThetaMAX = 100.0;		// max launch angle (degrees)
	
	bTree myBTree = new bTree(); 
	
	private RandomGenerator rgen = new RandomGenerator(); 	// declares the instance variable RandomGenerator
		
	public void run() {		// beginning the run method
		
		this.resize(WIDTH, HEIGHT+OFFSET);	// resizing the dimensions of the applet window
		
		GRect horizon = new GRect(0, HEIGHT, WIDTH, 3);		// adding a white horizon line
		horizon.setFilled(true);
		horizon.setFillColor(Color.white);
		add(horizon);

		for(int i = 0 ; i < NUMBALLS ; i++)		// starting at 0 balls, then increasing by 1 until i reaches the max number of balls
		{
			// generating random energy loss, ball size, launch angle, initial speed, and colour 
			double bLoss = rgen.nextDouble(EMIN, EMAX);		
			double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
			double theta = rgen.nextDouble(ThetaMIN, ThetaMAX);
			double Vi = rgen.nextDouble(ViMIN, ViMAX);
			Color bColor = rgen.nextColor();
			double Yi = bSize;		// setting initial y position to be the radius of the ball
			double Xi = WIDTH/(2*SCALE);		// setting initial x position to be half of the width of the applet window
			
			bBall iBall = new bBall(Xi, Yi, Vi, theta, bSize, bColor, bLoss);		// creates a new ball using the (pseudo-) randomized properties			
			add(iBall.myBall);
			iBall.start();
			myBTree.addNode(iBall);
		}
		
		while(myBTree.isRunning());		// while balls are still moving, do nothing
		
		GLabel myLabel = new GLabel("Click mouse to continue.", 1000, 550);		// creates label
		add(myLabel); 	// adds label
		myLabel.setColor(Color.red);		// sets color
		
		waitForClick();	 // waits for user to click
		
		myBTree.moveSort();	 // places balls in order of size
		myLabel.setLabel("All sorted!");		// changes label
	}
	
}
