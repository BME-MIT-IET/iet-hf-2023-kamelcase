package softprojlab.view.customViews;

// Java Imports
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

// Project Imports

/**
 * Standardized dark mode JLabel
 * @author marton.pfemeter
 */
public class DarkLabel extends JLabel {

	// Static Attributes
	
	/**
	 * Unique ID for Serialization
	 */
	private static final long serialVersionUID = 2540284956007210232L;

	// Constructors
	
	/**
	 * Constructor with standard font size 14
	 * @param text The text to display
	 */
	public DarkLabel(String text) {
		this(text, 14);
	}
	
	/**
	 * Constructor specifying font size
	 * @param text The text to display
	 * @param size Font size
	 */
	public DarkLabel(String text, int size) {
		this(text, new Font("futura", Font.PLAIN, size));
	}
	
	/**
	 * Fully customizable text constructor
	 * @param text The text to display
	 * @param parameters The Font to use on the text
	 */
	public DarkLabel(String text, Font parameters) {
		super(text, SwingConstants.CENTER);
		this.setFont(parameters);
		this.setForeground(Color.WHITE);
		this.setBackground(Color.BLACK);
	}
}
