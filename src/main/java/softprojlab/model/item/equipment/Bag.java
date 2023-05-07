package softprojlab.model.item.equipment;

// Java imports


// Project imports
import softprojlab.main.LogHandler;

/**
 * Equipment that increases the wearer's Material carrying capacity.
 * @author pfemeter.marton
 *
 */
public class Bag extends Equipment {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Bag";
	
	// Private Attributes
	
	/**
	 * The amount this item increases the wearer's carrying capacity.
	 */
	private int bonusCapacity;

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 */
	public Bag() {
		super();
		LogHandler.logFunctionCall(Bag.logName, "Constructor");
		this.bonusCapacity = 5;
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	// See ancestor for documentation.
	public int handleBagCapacity(int original) {
		LogHandler.logFunctionCall(Bag.logName, "handleBagCapacity");
		LogHandler.decrementIndentation();

		return original + this.bonusCapacity;
	}

	@Override
	public String toYAML() {
		return "bag";
	}
	
	public String getDisplayableName() {
		return "Bag";
	}
}
