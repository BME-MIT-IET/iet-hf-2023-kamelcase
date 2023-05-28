package softprojlab.view;

// Java Imports
import java.awt.*;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

// Project Imports
import softprojlab.cli.CLI;
import softprojlab.view.customViews.BoxContainer;
import softprojlab.view.customViews.DarkButton;

/**
 * 
 */
public class MapLoadView extends ListItemManager {
   
	// Static Attributes

	
	/**
	 * Unique ID for Serialization 
	 */
	private static final long serialVersionUID = -1531772406484554089L;
	
	// Private Attributes

	/**
	 * 
	 */
	private DarkButton loadGame;
    
    /**
     * Reference to the single MainWindow instance.
     */
    private MainWindow mainWindow;

    // Protected Attributes

    protected MapLoader ml;

    // Constructors
    
    /**
     * Default constructor.
     * @param root Reference to the single MainWindow instance.
     */
    public MapLoadView(MainWindow root){
        super(500, 500, true);
        this.loadGame = new DarkButton("START GAME");
        this.ml = new MapLoader();

        this.mainWindow = root;

        ArrayList<String> mapList = this.ml.availableMaps == null ? new ArrayList<String>() : new ArrayList<>(Arrays.asList(this.ml.availableMaps));

        this.init(mapList);
    }

    // Private Methods
    
    /**
     * Initializes the view.
     *
     * @param mapList ArrayList of the possible maps.
     */
    private void init(ArrayList<String> mapList){

        this.setOptions("", mapList);

        this.loadGame.addActionListener(this::loadGame);

        this.loadGame.setMaximumSize(new Dimension(100,50));
        this.loadGame.setHorizontalAlignment(SwingConstants.CENTER);

		this.scrollContainer.add(this.backgroundPanel);
		this.backgroundPanel.add(this.loadGame);

        this.setBackground(Color.BLACK);

        this.validate();
    }

    /**
     * Loads the map from the selected file.
     */
    private void loadGame(ActionEvent e){
        this.ml.load(this.selectedIndex);
        this.mainWindow.game = this.ml.getGame();
        this.mainWindow.changeViewState();
    }

    /**
     * Handles the list item change
     */
    @Override
    public void onListItemClicked() {
        this.loadGame(new ActionEvent(this, 0, ""));
    }
}
