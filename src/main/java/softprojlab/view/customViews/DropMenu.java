package softprojlab.view.customViews;

// Java Imports
import java.awt.Color;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

// Project Imports


/**
 * Standardized dark mode JList
 * @param <T> Type specification for JList
 * @author marton.pfemeter
 */
public class DropMenu<T> extends JList<T> {
	
	// Static Attributes
	
	/**
	 * Unique ID for Serialization 
	 */
	private static final long serialVersionUID = 4576091883414189193L;

	// Constructors
	
	/**
	 * Constructor
	 * @param data The array of objects to choose from
	 */
	public DropMenu(T[] data) {
		this(data, false);
	}
	
	/**
	 * Constructor, selecting the first element in the given array by default
	 * @param data The array of objects to choose from
	 * @param selectFirst If the first item is going to be selected by default (nothing is selected by default if false)
	 */
	public DropMenu(T[] data, boolean selectFirst) {
		super(data);
		
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setFont(new Font("futura", Font.PLAIN, 14));
		
		if (selectFirst) {
			this.setSelectedIndex(0);
		}
	}
}
