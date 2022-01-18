import java.awt.Color;
import acm.graphics.GOval;

/**
 * The bBall class simulates a single ball drop, and runs simultaneously to the bSim class (thread)
 */

public class bBall extends Thread {
	
	private static final double Pi = 3.141592654;		// pi is used to convert degrees to radians
	private static final double g = 9.8;		// gravitational constant: 9.8 m/s^2
	private static final double TICK = 0.1;		// clock tick duration (s)
	private static final double ETHR = 0.01;		// E threshold - when Voy < ETHR * Vo, stops
	private static final int HEIGHT = 600;		// distance from top of screen to ground plane
	private static final int SCALE = HEIGHT/100; 	// ratio of pixels/meter
	public GOval myBall; 	// ball - named myBall
	boolean isRunning = true;		// stores whether the balls are moving (bSim is running)
	
	/**
	 * The constructor specifies the parameters for simulation. They are
	 *
	 * @param Xi double initial X position of the center of the ball
	 * @param Yi double initial Y position of the center of the ball
	 * @param Vo double initial velocity of the ball at launch
	 * @param theta double launch angle (with respect to the horizon)
	 * @param bSize double radius of the ball in simulation units
	 * @param bColor Color color of the ball
	 * @param bLoss double fraction (b/w 0 and 1) of the energy lost on each bounce
	 * 
	 */

	double Xi;
	double Yi;
	double Vo;
	double theta;
	double bSize;
	Color bColor; 
	double bLoss;
	
	public bBall(double Xi, double Yi, double Vo, double theta, double bSize, Color bColor, double bLoss) { // creates a ball using the parameters listed above
		super();		// this refers to the object being called
		this.Xi = Xi;
		this.Yi = Yi;
		this.Vo = Vo;
		this.theta = theta;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		
		myBall = new GOval(Xi*SCALE, Yi*SCALE, SCALE*bSize*2, SCALE*bSize*2);		// creating a GOval using the parameters
		myBall.setFilled(true);		// filling the ball
		myBall.setColor(bColor);		// setting the ball color
	}
	
	/**
	 * The run method implements the simulation loop from Assignment 1.
	 * Once the start method is called on the bBall instance, the
	 * code in the run method is executed concurrently with the main
	 * program.
	 * @param void
	 * @return void
	 */
	
	public void run() {
		isRunning = true;
		double X;
		double time_g = 0;
		double Y;
		double time_l = 0;
		double Vy;
		double ScrX;
		double ScrY; 
		double Vox=Vo*Math.cos(theta*Pi/180);		// initializing Vo's horizontal and vertical components, and converting the angle to radians
		double Voy=Vo*Math.sin(theta*Pi/180);
		
		// simulation from assignment 1

		while(isRunning) {
			X = Xi + Vox * time_g;
			Y = Yi + Voy * time_l - 0.5 * g * time_l * time_l;
			Vy = Voy - g * time_l;
			time_g = time_g + TICK;
			time_l = time_l + TICK;
			
			if(Vy < 0 && Y <= bSize) 
			{
				Voy = Voy * Math.sqrt(1-bLoss);
				System.out.println(Voy);
				time_l = 0;
				Y = bSize;
			}
			
			System.out.println("X = "+X+" Y = "+Y);		// printing x and y positions in the console
			ScrX = (int) ((X-bSize) * SCALE);		// defining x and y position of the ball in screen coordinates
			ScrY = (int) (HEIGHT-(Y+bSize) * SCALE);
			myBall.setLocation(ScrX,ScrY);		// setting location of the ball
			
			if(Voy < Vo*ETHR)
			{
				break;
			}

			try {
				Thread.sleep(20);		// pauses
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				}
			}
		isRunning = false;
		}

	}