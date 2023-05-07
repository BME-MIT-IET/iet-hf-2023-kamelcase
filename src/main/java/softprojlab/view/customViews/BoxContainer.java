package softprojlab.view.customViews;

// Java Imports
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Project Imports


/**
 * JPanel with black background and BoxLayout.
 * @author pfemeter.marton
 */
public class BoxContainer extends JPanel {

	// Private Static Attributes
	
	/**
	 * Unique ID for Serialization
	 */
	private static final long serialVersionUID = 7823414402173167483L;

	// Constructors
	
	/**
	 * Default constructor.
	 * @param isVertical True, if the layout's axis should be vertical, false if it should be horizontal.
	 */
	public BoxContainer(boolean isVertical) {
		super();
		BoxLayout resultBox = new BoxLayout(this, (isVertical ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS));
		this.setAlignmentX(SwingConstants.CENTER);
		this.setLayout(resultBox);
		this.setBackground(Color.BLACK);
	}
}
