package softprojlab.view.viewHelpers;

// Java Imports
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

// Project Imports
import softprojlab.view.ListItemManager;
import softprojlab.view.customViews.DropMenu;

/**
 * Helper class handling clicking after selection on a ListClickManager instance.
 * @author pfemeter.marton
 */
public class ListClickManager extends MouseAdapter {

	// Private Attributes
	
	/**
	 * The JList in this.listManager.
	 */
	private DropMenu<String> dataSource;
	
	/**
	 * The instance that is going to be notified through its onListItemClicked method when one of its items are clicked.
	 */
	private ListItemManager listManager;
	
	// Constructors
	
	/**
	 * Default constructor.
	 * @param target The instance to notify when it is clicked.
	 * @param data The JList in target.
	 */
	public ListClickManager(ListItemManager target, DropMenu<String> data) {
		super();
		this.listManager = target;
		this.dataSource = data;
	}
	
	// Public Methods
	
	/**
	 * Handles clicking of an item in this.list
	 * If the item clicked was already selected, then calls this.list.onListItemClicked method.
	 */
	public void mouseClicked(MouseEvent event) {
		if (this.listManager != null && this.dataSource != null) {
			
			// Checking that the click was on the correct component
			if (this.dataSource == event.getComponent()) {
				
				// Checking that the click was on a visible list item
				Rectangle listBoundary = this.dataSource.getCellBounds(0, this.dataSource.getLastVisibleIndex());
				if (listBoundary != null && listBoundary.contains(event.getPoint())) {
				
					int clickedIndex = this.dataSource.locationToIndex(event.getPoint());
					
					if (clickedIndex == this.dataSource.getSelectedIndex() && !this.listManager.wasJustSelected) {
						this.listManager.wasJustSelected = false;
						this.listManager.onListItemClicked();
					} else {
						this.listManager.wasJustSelected = false;
						this.listManager.onListItemSelected(clickedIndex);
						
						if (event.getClickCount() >= 2) {
							this.listManager.onListItemClicked();
						}
						
					}
				}
			}
		}
	}

}
