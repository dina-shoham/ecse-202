import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import acm.gui.TableLayout;

public class sliderBox extends JPanel{
	
	JLabel name_lab;		// name label
	JLabel min_lab;		// minimum label
	JLabel max_lab;		// maximum label
	JLabel def_lab;		// default label
	JSlider slider;		// slider
	
	/**
	 * the sliderBox method creates the slider boxes, accepting ints for min, max, and def
	 * @param name
	 * @param min
	 * @param max
	 * @param def
	 */
	public sliderBox(String name, int min, int max, int def)
	{
		
		this.setLayout(new TableLayout(1, 5));		// setting table layout
		
		// creating labels
		name_lab  = new JLabel(name);
		min_lab = new JLabel(min+"");
		max_lab = new JLabel(max+"");
		def_lab = new JLabel(def+"");
		def_lab.setForeground(Color.blue);		// making default label blue
		slider = new JSlider(min, max, def);		// creating new JSlider w min, max, and def labels
		
		this.add(name_lab,"width = 100");
		this.add(min_lab,"width = 25");
		this.add(max_lab,"width = 100");
		this.add(def_lab,"width = 80");
		this.add(slider, "width = 100");
					
	}
	
	/**
	 * this sliderBox method accepts doubles instead of ints for max, min, and def
	 * @param name
	 * @param min
	 * @param max
	 * @param def
	 */
	public sliderBox(String name, double min, double max, double def)
	{
		
		this.setLayout(new TableLayout(1, 5));		// setting table layout
		
		// creating labels
		name_lab  = new JLabel(name);
		min_lab = new JLabel(min+"");
		max_lab = new JLabel(max+"");
		def_lab = new JLabel(def+"");
		def_lab.setForeground(Color.blue);		// making def label blue
		slider = new JSlider((int)(min*10), (int)(max*10), (int)(def*10));		// multiplying by 10 bc min, max, and def are doubles
		
		// adding labels
		this.add(name_lab,"width = 100");
		this.add(min_lab,"width = 25");
		this.add(max_lab,"width = 100");
		this.add(def_lab,"width = 80");
		this.add(slider, "width = 100");
	
	}
	

}