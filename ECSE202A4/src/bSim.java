import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.graphics.GRect;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/* The bSim class is the main class in the program.
 * It runs the actual simulation, as well as randomly generates size, speed, etc. of each ball.
 * It also creates the GLabel that prompts the user to click, and the label after that.
 */

public class bSim extends GraphicsProgram implements ChangeListener, ItemListener {
	
	// initializing variables
	private static final int WIDTH = 1800;		// width of the window
	private static final int HEIGHT = 600;		// height of the window
	private static final int OFFSET = 200;		// height of the horizon line (where we want the bottom of the simulation to be)
	private static final double SCALE = HEIGHT/100;		// scale of the coordinate system (pixels/meter)
	
	private RandomGenerator rgen = new RandomGenerator(); 	// declares the instance variable RandomGenerator
	
	bBall[] allBalls;
	bTree myBTree = new bTree();		// declaring the binary tree
	
	// creating new slider boxes for number of balls and max and min values for size, loss, velocity, and launch angle
	sliderBox num_box = new sliderBox("number of balls:", 1, 255, 100);
	sliderBox smin_box = new sliderBox("min size:", 1, 25, 4);
	sliderBox smax_box = new sliderBox("max size:", 1, 25, 10);
	sliderBox lmin_box = new sliderBox("min loss:", 0.0, 1.0, 0.4);
	sliderBox lmax_box = new sliderBox("max loss:", 0.0, 1.0, 0.4);
	sliderBox vmin_box = new sliderBox("min velocity:", 1, 200, 25);
	sliderBox vmax_box = new sliderBox("max velocity:", 1, 200, 45);
	sliderBox tmin_box = new sliderBox("theta min:", 1, 100, 80);
	sliderBox tmax_box = new sliderBox("theta max:", 1, 100, 100);
	
	// creating combo boxes for bSim, file, edit, and help
	JComboBox<String> bSimCB;
	JComboBox<String> FileCB;
	JComboBox<String> EditCB;
	JComboBox<String> HelpCB;
	
	/**
	 * the init method essentially adds all of the graphic elements of the program
	 * thi sincludes the horizon, the panel, the sliders, and the combo boxes
	 */
	public void init() {
		
		this.resize(WIDTH, HEIGHT+OFFSET);	// resizing the dimensions of the applet window
		
		GRect horizon = new GRect(0, HEIGHT, WIDTH, 3);		// adding a horizon line
		horizon.setFilled(true);
		horizon.setFillColor(Color.black);
		add(horizon);
		
		JLabel gsp = new JLabel("general simulation parameters:");		// creating gsp label
		
		JPanel panel = new JPanel();		// initializing panel
		panel.setLayout(new TableLayout(10,1));		// setting the panel's layout
		panel.add(gsp);		// adding label to panel
		panel.add(num_box);		// adding slider boxes to panel
		panel.add(smin_box);
		panel.add(smax_box);
		panel.add(lmin_box);
		panel.add(lmax_box);
		panel.add(vmin_box);
		panel.add(vmax_box);
		panel.add(tmin_box);
		panel.add(tmax_box);
		add(panel,EAST);		// placing panel on right (east) side of applet window
				
		
		num_box.slider.addChangeListener(this);		// adding listeners on the sliders
		smin_box.slider.addChangeListener(this);
		smax_box.slider.addChangeListener(this);
		lmin_box.slider.addChangeListener(this);
		lmax_box.slider.addChangeListener(this);
		vmin_box.slider.addChangeListener(this);
		vmax_box.slider.addChangeListener(this);
		tmin_box.slider.addChangeListener(this);
		tmax_box.slider.addChangeListener(this);
	
		
		bSimCB = new JComboBox<String>();		// adding combo boxes for bSim, file, edit, and help
		FileCB = new JComboBox<String>();
		EditCB = new JComboBox<String>();
		HelpCB = new JComboBox<String>();
		bSimCB.addItem("bSim");		// bSim, run, clear, stop, hist, and quit options to bSim combo box
		bSimCB.addItem("Run");
		bSimCB.addItem("Clear");
		bSimCB.addItem("Stop");
		bSimCB.addItem("Hist");
		bSimCB.addItem("Quit");
		FileCB.addItem("File");		// adding file option to file combo box
		EditCB.addItem("Edit");		// ^^ same for edit
		HelpCB.addItem("Help");		// ^^ same for help
		
		JPanel top = new JPanel();		// creating JPanel called top
		top.setLayout(new TableLayout(1,4));		// setting layout for top
		top.add(bSimCB);		// adding bSim, file, edit, and help combo boxes to top
		top.add(FileCB);
		top.add(EditCB);
		top.add(HelpCB);
		add(top,NORTH);		// placing top at the top (north) of applet window
		bSimCB.addItemListener(this);		// adding listener to bSim combo box (so bSim, run, clear, etc. options options can be selected)
	}

	
	public void run()	// entry point for the program - indicates where to start running
	{
	}
	
	/**
	 * the generateBalls class generates the balls according to the following randomly generated parameters:
	 * @param NUMBALLS
	 * @param MINSIZE
	 * @param MAXSIZE
	 * @param EMIN
	 * @param EMAX
	 * @param ViMIN
	 * @param ViMAX
	 * @param ThetaMIN
	 * @param ThetaMAX
	 * it calls on the bBall class in order to simulate projectile motion
	 */
	public void generateBalls(int NUMBALLS, int MINSIZE, int MAXSIZE, double EMIN, double EMAX, int ViMIN, int ViMAX, int ThetaMIN, int ThetaMAX)
	{
		allBalls = new bBall[NUMBALLS];
		RandomGenerator rgen = new RandomGenerator();
		myBTree = new bTree();

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
	}
	
		/** 
		 * the stateChanged method listens to the user input on the sliders and changes values accordingly
		 * it does so for all 9 slider boxes
		 */
		public void stateChanged(ChangeEvent ce) {
			if(ce.getSource() == num_box.slider)
			{
				num_box.def_lab.setText(num_box.slider.getValue()+"");
			}
			if(ce.getSource() == smin_box.slider)
			{
				smin_box.def_lab.setText(smin_box.slider.getValue()+"");
			}
			if(ce.getSource() == smax_box.slider)
			{
				smax_box.def_lab.setText(smax_box.slider.getValue()+"");
			}
			if(ce.getSource() == lmin_box.slider)
			{
				lmin_box.def_lab.setText(lmin_box.slider.getValue()/10.0+"");
			}
			if(ce.getSource() == lmax_box.slider)
			{
				lmax_box.def_lab.setText(lmax_box.slider.getValue()/10.0+"");
			}
			if(ce.getSource() == vmin_box.slider)
			{
				vmin_box.def_lab.setText(vmin_box.slider.getValue()+"");
			}
			if(ce.getSource() == vmax_box.slider)
			{
				vmax_box.def_lab.setText(vmax_box.slider.getValue()+"");
			}
			if(ce.getSource() == tmin_box.slider)
			{
				tmin_box.def_lab.setText(tmin_box.slider.getValue()+"");
			}
			if(ce.getSource() == tmax_box.slider)
			{
				tmax_box.def_lab.setText(tmax_box.slider.getValue()+"");
			}
		}
		
		/**
		 * the itemStateChanged method performs all of the functions within the bSim combo box
		 */
		public void itemStateChanged(ItemEvent ie) {
			if(ie.getSource() == bSimCB)		// if bSim is clicked, run the following if loops:
			{
				System.out.println("bSim combox was selected.");
				if(bSimCB.getSelectedIndex() == 1 &&ie.getStateChange() == 1)		// if run is selected (run has an index = 1)
				{
					System.out.println("run is selected.");
					generateBalls(num_box.slider.getValue(), smin_box.slider.getValue(), smax_box.slider.getValue(), lmin_box.slider.getValue()/10.0, lmax_box.slider.getValue()/10.0, vmin_box.slider.getValue(), vmax_box.slider.getValue(), tmin_box.slider.getValue(), tmax_box.slider.getValue());
					// call on generateBalls method -> runs simulation using values from slider boxes
					// have to divide lmin and lmax by 10.0 to convert them back to doubles
				}
				if(bSimCB.getSelectedIndex() == 2 && ie.getStateChange() == 1)		// if clear is selected
				{
					System.out.println("clear is selected");
					this.removeAll();		// remove everything in simulation
					GRect horizon = new GRect(0, HEIGHT, WIDTH, 3);		// re-adding horizon line
					horizon.setFilled(true);
					horizon.setFillColor(Color.black);
					add(horizon);
				}
				if(bSimCB.getSelectedIndex() == 3 && ie.getStateChange() == 1)		// if stop is selected
				{
					System.out.println("stop is selected.");
					myBTree.stopAll();		// calls on stopAll method (in bTree class)
				}
				if(bSimCB.getSelectedIndex() == 4 && ie.getStateChange() == 1)		// if hist is selected
				{
					System.out.println("hist is selected.");
					myBTree.moveSort();		// calls on moveSort method (in bTree class)
				}
				if(bSimCB.getSelectedIndex() == 5 && ie.getStateChange() == 1)		// if quit is selected
				{
					System.out.println("quit is selected.");
					System.exit(0);  // quits program
				}

				
			}
		}
}