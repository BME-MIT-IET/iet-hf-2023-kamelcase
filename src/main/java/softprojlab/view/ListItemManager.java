package softprojlab.view;

// Java imports
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// Project imports
import softprojlab.view.customViews.BoxContainer;
import softprojlab.view.customViews.DarkLabel;
import softprojlab.view.customViews.DropMenu;
import softprojlab.view.viewHelpers.ListClickManager;

/**
 * Abstract class for handling single selection multiple choice question displaying to the user.
 * @author pfemeter.marton
 */
public abstract class ListItemManager extends JPanel {
	
	// Static Attributes
	
	/**
	 * Unique ID for Serialization
	 */
	private static final long serialVersionUID = -1360325305211368417L;
	
	// Private Attributes
	
	/**
	 * Component handling list displaying, and selection.
	 */
	private DropMenu<String> listHandler;
	
	/**
	 * True, if the first item in the list should be selected by default.
	 */
	private boolean selectFirst;
	
	/**
	 * Optional text to display over the list being displayed.
	 */
	private DarkLabel topText;
	
	// Protected Attributes

	/**
	 * Background Image
	 */
	protected BufferedImage backgroundImage;

	/**
	 * Background Panel
	 */

	protected JPanel backgroundPanel = new JPanel()
	{
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			if(backgroundImage != null){
				int width = backgroundPanel.getWidth();
				int height = backgroundPanel.getHeight();

				g.drawImage(backgroundImage, 0, 0, width, height, backgroundPanel);
			}
		}
	};

	/**
	 * Container inside this.scrollHandler
	 */
	protected BoxContainer scrollContainer;
	
	/**
	 * Scrollview for this.listHandler.
	 */
	protected JScrollPane scrollHandler;
	
	/**
	 * The last index selected by the user.
	 */
	protected int selectedIndex;
	
	// Public Attributes
	
	/**
	 * Flag for signaling whether listSelectionListener was just called.
	 * Needed for mouseListener to work properly.
	 */
	public volatile boolean wasJustSelected;
	
	// Constructors
	
	/**
	 * Convenience constructor that doesn't set a preferred, maximumal, or a minimal size for this component.
	 * @param selectFirst True, if the first item in the list should be selected by default.
	 */
	public ListItemManager(boolean selectFirst) {
		this(null, null, selectFirst);
	}
	
	/**
	 * Default constructor. Sets listSelectionListener to this.onListItemClicked, and mouseListener to a new ListClickManager.
	 * @param width The width of the component. If null, preferred, maximum, and minimum size are not set.
	 * @param height The height of the component. If not all options can be presented at once, a scrollbar is added. If null, preferred, maximum and minimum size are not set.
	 * @param selectFirst True, if the first item in the list should be selected by default.
	 */
	public ListItemManager(Integer width, Integer height, boolean selectFirst) {
		super(new BorderLayout());
		
		if (width != null && height != null) {
			this.setMinimumSize(new Dimension(width, height));
			this.setPreferredSize(new Dimension(width, height));
			this.setMaximumSize(new Dimension(width, height));
		}
		this.setBackground(Color.BLACK);
		
		this.listHandler = new DropMenu<String> (new String[0], false);
		this.selectFirst = selectFirst;
		
		this.topText = new DarkLabel("");
		this.add(this.topText, BorderLayout.NORTH);

		try{
			this.backgroundImage = ImageIO.read(new File("src/main/resources/wizardstartwallpaper.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		
		this.selectedIndex = -1;
		if (this.selectFirst) {
			this.selectedIndex = 0;
		}
		
		this.wasJustSelected = false;
		
		this.scrollContainer = new BoxContainer(true);
		
		this.scrollContainer.add(this.listHandler);
		
		this.scrollHandler = new JScrollPane(this.scrollContainer);
		
		this.scrollHandler.setBackground(Color.BLACK);
		this.add(this.scrollHandler, BorderLayout.CENTER);
		
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
	}
	
	// Protected Methods
	
	/**
	 * Creates a new instance for this.listHandler from the parameterized options.
	 * @param topLabel Optional text to display over the list being displayed. If null, it is not displayed.
	 * @param optionList The list of the names of the options in this list.
	 */
	protected void setOptions(String topLabel, ArrayList<String> optionList) {
		this.setOptions(topLabel, optionList.toArray(new String[0]));
	}
	
	/**
	 * Creates a new instance for this.listHandler from the parameterized options.
	 * @param topLabel Optional text to display over the list being displayed. If null, it is not displayed.
	 * @param optionList The list of the names of the options in this list.
	 */
	protected void setOptions(String topLabel, String[] optionList) {
		this.scrollContainer.remove(this.listHandler);
		
		this.listHandler = new DropMenu<String> (optionList, this.selectFirst);
		
		this.listHandler.addListSelectionListener((event) -> {
			this.wasJustSelected = true;
			this.onListItemSelected(this.listHandler.getSelectedIndex());
		});
		
		this.listHandler.addMouseListener(new ListClickManager(this, this.listHandler));
		
		this.topText.setText(topLabel);
		
		this.scrollContainer.add(this.listHandler);
		
		this.validate();
	}
	
	// Public Methods

	/**
	 * Called when the item at this.selectedIndex was clicked (i.e.: clicked at least once after being selected).
	 */
	public abstract void onListItemClicked();
	
	/**
	 * Updates this.selectedIndex on new selection in the list.
	 * @param index The new value of this.selectedIndex (the item selected in the GUI)
	 */
	public void onListItemSelected(int index) {
		this.selectedIndex = index;
	}

}
