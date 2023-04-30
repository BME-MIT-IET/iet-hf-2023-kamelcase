package softprojlab.view.customViews;

// Java Imports
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.util.function.BiConsumer;

// Project Imports


/**
 * Standardized dark mode JButton
 * @author marton.pfemeter
 */
public class DarkButton extends JButton {

	// Static Attributes
	
	/**
	 * Unique ID for Serialization
	 */
	private static final long serialVersionUID = 694647369975639540L;
	
	// Private Attributes
	
	/**
	 * Callback function called when the button is pressed.
	 * Its first parameter is the name written on this button, and the second is the index it was given.
	 */
	private BiConsumer<String, Integer> callback;
	
	/**
	 * Index of this button. Will be returned in this.callback when this button is pressed.
	 */
	private Integer index;
	
	/**
	 * Name of this button. Will be returned in this.callback when this button is pressed.
	 */
	private String name;
	
	// Constructors
	
	/**
	 * Constructor for native JButton behavior
	 * @param name Name of the button
	 */
	public DarkButton(String name) {
		this(name, null);
	}
	
	/**
	 * Constructor for native JButton behavior with an index
	 * @param name Name of the button
	 * @param index The index of the button that it will return in this.callback when it is pressed
	 */
	public DarkButton(String name, Integer index) {
		this(name, index, true);
	}
	
	/**
	 * Constructor for specifying whether the button should be enabled.
	 * @param name Name of the button
	 * @param index The index of the button that it will return in this.callback when it is pressed
	 * @param isEnabled Whether the button is enabled
	 */
	public DarkButton(String name, Integer index, boolean isEnabled) {
		super();
		this.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(new DarkLabel(name));
		
		this.callback = null;
		this.index = index;
		this.name = name;
		
		this.addActionListener((e) -> {
			if (this.callback != null) {
				this.callback.accept(this.name, this.index);
			}
		});
		this.setEnabled(isEnabled);
		
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
	}
	
	// Public methods
	
	/**
	 * Sets this.callback to the parameter.
	 * @param query The new value of this.callback.
	 * @return True if this.callback was not null before this operation, false if it was null.
	 */
	public boolean setCallback(BiConsumer<String, Integer> query) {
		boolean result = (this.callback == null);
		
		this.callback = query;
		
		return result;
	}

}
