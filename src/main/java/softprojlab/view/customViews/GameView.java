package softprojlab.view.customViews;

// Java Imports
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

// Project Imports
import softprojlab.view.ActionSidebar;
import softprojlab.view.InventorySidebar;
import softprojlab.view.MapView;

/**
 * The view the user is going to see in-game.
 * @author pfemeter.marton
 */
public class GameView extends JPanel {

	// Static Attributes
	
	/**
	 * Unique ID for Serialization 
	 */
	private static final long serialVersionUID = 4907610844645434179L;
	
	
	// Constructors
	
	/**
	 * Default constructor. Throws an exception if any of its parameters are null.
	 * @param actionBar The ActionSidebar instance we want to use in-game.
	 * @param inventoryBar The InventorySidebar instance we want to use in-game.
	 * @param map The MapView instance we want to use in-game.
	 * @throws NullPointerException If any of the above parameters are null, this call throws, and the resulting JPanel may cause unexpected behavior.
	 */
	public GameView(ActionSidebar actionBar, InventorySidebar inventoryBar, MapView map) throws NullPointerException {
		super(new BorderLayout());

		if (inventoryBar == null || actionBar == null || map == null) {
			throw new NullPointerException();
		}
		
        this.add(map, BorderLayout.CENTER);
		
		JPanel sidebar = new BoxContainer(true);
		sidebar.add(inventoryBar);
		sidebar.add(actionBar);
		
		this.add(sidebar, BorderLayout.EAST);
        
        this.setBackground(Color.BLACK);
	}

}
