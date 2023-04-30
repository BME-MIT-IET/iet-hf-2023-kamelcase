package softprojlab.view;

// Java Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;

// Project Imports
import softprojlab.model.Game;
import softprojlab.view.customViews.EndGameView;
import softprojlab.view.customViews.GameView;

/**
 * Main view extending JFrame, handling loading in saves and switching between menus
 * @author marton.pfemeter
 */
public class MainWindow extends JFrame {
	
	// Static Attributes
	
	/**
	 * Unique ID for Serialization
	 */
	private static final long serialVersionUID = 536772569099559229L;
	
	// Private Attributes
	
    /**
     * View handling interaction in-game
     */
    private ActionSidebar actionBar;
    
    /**
     * Panel showing that someone won the game.
     */
    private EndGameView endView;
    
	/**
	 * View showing in-game content
	 */
	private GameView gameView;
	
    /**
     * View showing the current state of the active Virologist
     */
    private InventorySidebar inventoryBar;
    
	/**
	 * Reference to the loading screen
	 */
	private MapLoadView loadView;

	/**
	 * View showing our current position on the map
	 */
    private MapView map;
    
    /**
     * Root component for presenting this.popup as a popup dialog
     */
    private JLayeredPane root;
	
	/**
	 * The state indicating which view is currently active
	 * this.loadView if true, and this.gameView if false
	 */
	private boolean viewState;
    
    // Public Attributes
    
    /**
     * Reference to the model behind the GUI
     */
    public Game game;
    
    /**
     * Component handling asking questions from the user.
     */
    public QuestionDialog popup;
    
    // Constructors

	/**
	 * Constructor loading in the main menu and previous saves, adding valid view state changes, and setting up correct exit protocol 
	 */
	public MainWindow() {
		super("Blind Virologist Simulator");
		
		this.actionBar = new ActionSidebar(this);
		this.inventoryBar = new InventorySidebar(this);
		this.loadView = new MapLoadView(this);
		this.map = new MapView(this);
		this.root = new JLayeredPane();
		this.popup = new QuestionDialog(this);
		this.viewState = true;
		
		this.game = new Game();
		Game.endGameCallback = this::endGame;
		Game.questionCallback = this.popup::askQuestion;
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		int minWidth = 1600;
		int minHeight = 900;
		this.setMinimumSize(new Dimension(minWidth, minHeight));
		
		BorderLayout frameLayout = new BorderLayout();
		this.setLayout(frameLayout);
		this.setBackground(Color.BLACK);
		
		this.gameView = new GameView(this.actionBar, this.inventoryBar, this.map);
		
		this.endView = new EndGameView(this);
		
		this.root.add(this.popup, 0, -1);
		this.root.add(this.loadView, 0, -1);
		this.root.add(this.gameView, 0, -1);
		this.root.add(this.endView, 0, -1);
		
		this.add(this.root, BorderLayout.CENTER);
		
		this.setVisible(true);

		this.invalidate();
	}
	
	// Private Methods
	
	/**
	 * Updates layer boundaries, so that resizing of the main window is handled properly.
	 */
	private void setLayerPosition() {
		this.loadView.setBounds(0, 0, this.getContentPane().getWidth(), this.getContentPane().getHeight());
		this.gameView.setBounds(0, 0, this.getContentPane().getWidth(), this.getContentPane().getHeight());
		
		int endOriginX = (this.getContentPane().getWidth() - (int) (0.5 * this.popup.getDefaultWidth()) ) / 2;
		int endOriginY = (this.getContentPane().getHeight() - (int) (0.5 * this.popup.getDefaultHeight()) ) / 2;
		this.endView.setBounds(endOriginX, endOriginY, (int) (0.5 * this.popup.getDefaultWidth()), (int) (0.5 * this.popup.getDefaultHeight()) );
		
		int originX = (this.getContentPane().getWidth() - this.popup.getDefaultWidth()) / 2;
		int originY = (this.getContentPane().getHeight() - this.popup.getDefaultHeight()) / 2;
		this.popup.setBounds(originX, originY, this.popup.getDefaultWidth(), this.popup.getDefaultHeight());
	}
	
	// Public Methods
	
	/**
	 * Toggles this.viewState, then calls this.invalidate()
	 */
	public void changeViewState() {
		this.viewState = !this.viewState;
		this.invalidate();
	}
	
	/**
	 * Callback function for Game to signal to the GUI that the game has ended.
	 * @param winnerID The UID of the winner.
	 */
	public void endGame(int winnerID) {
		this.endView.setText(winnerID);
	}
	
	/**
	 * Redraws the component according to this.currentView, and calls super.invalidate()
	 * If this.popup.isVisible, displays this.popup as well.
	 */
	public void invalidate() {
		super.invalidate();
		
		this.actionBar.invalidate();
		this.inventoryBar.invalidate();
		this.map.invalidate();
		this.popup.invalidate();
		this.endView.invalidate();
		this.loadView.invalidate();
		
		this.setLayerPosition();
		
		if (this.viewState) {
			this.gameView.setVisible(false);
			this.loadView.setVisible(true);
			this.root.moveToFront(this.loadView);
		} else {
			this.loadView.setVisible(false);
			this.gameView.setVisible(true);
			this.root.moveToFront(this.gameView);
		}
		
		if (this.popup.isVisible) {
			this.popup.setVisible(true);
			this.root.moveToFront(this.popup);
		} else {
			this.popup.setVisible(false);
			this.root.moveToBack(this.popup);
		}
		
		if (this.endView.isVisible) {
			this.endView.setVisible(true);
			this.root.moveToFront(this.endView);
		} else {
			this.endView.setVisible(false);
			this.root.moveToBack(this.endView);
		}
		
		this.validate();
	}
}
