package softprojlab.view.customViews;

// Java Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Project Imports
import softprojlab.view.MainWindow;

/**
 * A view for displaying at the end of the game.
 * @author pfemeter.marton
 */
public class EndGameView extends JPanel {

	// Static Attributes
	
	/**
	 * Unique ID for Serialization 
	 */
	private static final long serialVersionUID = -1564952048477988973L;
	
	// Private Attributes
	
	
	/**
	 * Text displaying the UID of the winner.
	 */
	private DarkLabel bottomText;
	
    /**
     * Reference to the single MainWindow instance.
     */
	private MainWindow mainWindow;
	
	/**
	 * The root view.
	 */
	private BoxContainer root;
	
	// Public Attributes
	
	/**
	 * Whether this component is visible.
	 */
	public boolean isVisible;
	
	// Constructors
	
	/**
	 * Default constructor.
	 * @param root = Reference to the single MainWindow instance.
	 */
	public EndGameView(MainWindow root) {
        super(new BorderLayout());
        
        this.mainWindow  = root;
        this.isVisible = false;
		
		this.root = new BoxContainer(true);
		
		String text = "GAME OVER";
		DarkLabel displayText = new DarkLabel(text, new Font("futura", Font.BOLD, 20));
		displayText.setAlignmentX(SwingConstants.CENTER);
		this.root.add(displayText);
		
		this.bottomText = new DarkLabel("");
		this.root.add(this.bottomText);
		
		DarkButton confirmButton = new DarkButton("Go to main menu");
		confirmButton.addActionListener((event) -> {
			this.isVisible = false;
			this.mainWindow.changeViewState();
		});
		this.root.add(confirmButton);
		
		this.add(this.root, BorderLayout.CENTER);
        
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
	}
	
	// Public Methods
	
	/**
	 * Sets this.bottomText with the parameterized value, sets this.isVisible to true, then calls this.mainWindow.invalidate().
	 * @param winnerId The UID of the Virologist that won.
	 */
	public void setText(int winnerId) {
		this.bottomText.setText("The winner is: " + winnerId);
		this.isVisible = true;
		this.mainWindow.invalidate();
	}
}
