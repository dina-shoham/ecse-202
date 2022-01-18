import acm.graphics.GLine;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram {
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;			// n.b. screen coordinates
	private static final double SCALE = HEIGHT/100;	// pixels per meter
	private static final int NUMBALLS = 1000;		// # balls to simulate
	private static final double MINSIZE = 1.5;		// Minimum ball radius (meters)
	private static final double MAXSIZE = 10.0;		// Maximum ball radius (meters)
	private static final double EMIN = 0.4;			// Minimum loss coefficient
	private static final double EMAX = 0.6;			// Maximum loss coefficient
	private static final double ViMIN = 25.0;		// Minimum velocity (meters/sec)
	private static final double ViMAX = 45.0;		// Maximum velocity (meters/sec)
	private static final double ThetaMIN = 80.0;	// Minimum launch angle (degrees)
	private static final double ThetaMAX = 100.0;	// Maximum launch angle (degrees)
	
	

public void run()
{
	this.resize(WIDTH, HEIGHT+OFFSET);
	
	RandomGenerator rgen = new RandomGenerator();
	
	GLine horizon = new GLine(0, HEIGHT, WIDTH, HEIGHT);
	add(horizon);
	
	//adding balls
	for(int i=0 ; i < NUMBALLS ; i++)
	{
		double bLoss = rgen.nextDouble(EMIN, EMAX);
		double bSize = rgen.nextDouble(MINSIZE, MAXSIZE);
		double theta = rgen.nextDouble(ThetaMIN, ThetaMAX);
		double Vi = rgen.nextDouble(ViMIN, ViMAX);
		Color bColor = rgen.nextColor();
		double Yi = bSize;
		double Xi = WIDTH/(2*SCALE);
		bBall cur_ball = newbBall(Xi, Yi, Vi, theta, bSize, bColor, bLoss);
		add(cur_ball.Start);
	}
}

