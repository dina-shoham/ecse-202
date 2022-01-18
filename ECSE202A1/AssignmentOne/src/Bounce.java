import java.awt.Color;

import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class Bounce extends GraphicsProgram {

	// establishing constants (for the entire class):
	private static final int WIDTH = 600;			// defines the width of the screen in pixels
	private static final int HEIGHT = 600;			// distance from top of screen to ground plane
	private static final int OFFSET = 200;			// distance from bottom of screen to ground plane
	private static final int PD = 1;				// diameter of plot point
	private static final int SCALE = HEIGHT/100; 	// ratio of pixels/meter
	private static final double g = 9.8;			// MKS gravitational constant 9.8 m/s^2
	private static final double Pi = 3.141592654;	// To convert degrees to radians
	private static final double bSize = 2.5;		// Physical ball radius (meters)
	private static final double Xinit = 5.0;		// Initial ball location (X)
	private static final double Yinit = bSize;		// Initial ball location (Y)
	private static final double TICK = 0.1;			// Clock tick duration (sec)
	private static final double ETHR = 0.01;		// Whey Voy < ETHR * Vo, STOP

	
	// class is public: accessible outside this class
	// return type: void
	// function name: run (input is empty)
	public void run() {
		
		
		// reading inputs here
		double Vo = readDouble ("Enter the initial velocity of the ball in m/s, from 0 to 100: ");
		double angle = readDouble ("Enter the launch angle in degrees, from 0 to 90: ");
		double loss = readDouble ("Enter energy loss factor: ");
		println("V0 = "+Vo+", theta = "+angle+", and loss = "+loss+".");
	
		this.resize(WIDTH, HEIGHT+OFFSET);
	

		// draw a horizon line
		GLine horizon = new GLine(0, HEIGHT, WIDTH, HEIGHT);
		add(horizon);
		
		
		//create a ball
		GOval myBall = new GOval(Xinit*SCALE, Yinit*SCALE, 2*bSize*SCALE, 2*bSize*SCALE);
		myBall.setFilled(true);
		myBall.setFillColor(Color.red);
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
		double Vox=Vo*Math.cos(angle*Pi/180);
		double Voy=Vo*Math.sin(angle*Pi/180);
		
		
		while(true) {
			X = Xinit + Vox + time_g;
			Y = Yinit + Voy + time_l - 0.5 * g * time_l*time_l;
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
			
			
			GOval plot_trace = new GOval(ScrX+bSize*SCALE, ScrY+bSize*SCALE, PD, PD);
			plot_trace.setFillColor(Color.black);
			plot_trace.setFilled(true);
			add(plot_trace);
			}
		}
		
	}

