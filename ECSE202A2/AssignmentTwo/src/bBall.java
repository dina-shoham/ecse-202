import java.awt.Color;

import acm.graphics.GLine;
import acm.graphics.GOval;

public class bBall extends Thread {
	/**
	 * The constructor specifies the parameters for simulation. They are
	 *
	 * @param Xi double The initial X position of the center of the ball
	 * @param Yi double The initial Y position of the center of the ball
	 * @param Vo double The initial velocity of the ball at launch
	 * @param theta double Launch angle (with the horizontal plane)
	 * @param bSize double The radius of the ball in simulation units
	 * @param bColor Color The initial color of the ball
	 * @param bLoss double Fraction [0,1] of the energy lost on each bounce
	 */
	
	double Xi;
	double Yi;
	double Vo;
	double theta;
	double bSize;
	Color bColor;
	double bLoss;
	private static final double TICK = 0.1;			// Clock tick duration (sec)
	private static final double ETHR = 0.01;		// Whey Voy < ETHR * Vo, STOP
	
	
	public bBall(double Xi, double Yi, double Vo, double theta,
			double bSize, Color bColor, double bLoss) {
		this.Xi = Xi; // Get simulation parameters
		this.Yi = Yi;
		this.Vo = Vo;
		this.theta = theta;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		}

	
	private static final int WIDTH = 600;			// defines the width of the screen in pixels
	private static final int HEIGHT = 600;			// distance from top of screen to ground plane
	private static final int SCALE = HEIGHT/100; 	// ratio of pixels/meter
	private static final double g = 9.8;			// MKS gravitational constant 9.8 m/s^2
	private static final double Pi = 3.141592654;	// To convert degrees to radians

	
		/**
		 * The run method implements the simulation loop from Assignment 1.
		 * Once the start method is called on the bBall instance, the
		 * code in the run method is executed concurrently with the main
		 * program.
		 * @param void
		 * @return void
		 */
		
		public void run() {
			GLine horizon = new GLine(0, HEIGHT, WIDTH, HEIGHT);
			add(horizon);
					
	
			//create a ball
			public GOval getBall() {
				 return myBall;
				}
			GOval myBall = new GOval(Xi*SCALE, Yi*SCALE, 2*bSize*SCALE, 2*bSize*SCALE);
			myBall.setFilled(true);
			myBall.setFillColor(bColor);
			add(myBall);
			
			
			//initializing more variables
			double X;
			double time_g = 0;
			double Y;
			double time_l = 0;
			double Vy;
			double ScrX;
			double ScrY;
			// initializing Vo's horizontal and vertical components, and converting the angle to radians 
			double Vox=Vo*Math.cos(theta*Pi/180);
			double Voy=Vo*Math.sin(theta*Pi/180);
			
			
			while(true) {
				X = Xi + Vox + time_g;
				Y = Yi + Voy + time_l - 0.5 * g * time_l*time_l;
				Vy = Voy - g * time_l;
				time_g = time_g + TICK;
				time_l = time_l + TICK;
				
				if(Vy < 0 && Y <= bSize) 
				{
					Voy = Voy*Math.sqrt(1-loss);
					time_l = 0;
					Y = bSize;
				}
				
				if(Voy < Vo*ETHR)
				{
					break;
				}
				
				
				ScrX = (int) ((X-bSize)*SCALE);
				ScrY = (int) (HEIGHT-(Y+bSize)*SCALE);
				myBall.setLocation(ScrX,ScrY);
				pause(100);
				
		}
	}


		private void add(GOval myBall) {
			// TODO Auto-generated method stub
			
		}


		private void add(GLine horizon) {
			// TODO Auto-generated method stub
			
		}