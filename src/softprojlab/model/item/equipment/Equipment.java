package softprojlab.model.item.equipment;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.Wearable;

/**
 * Abstract class representing persistent items that can be worn by characters.
 * @author pfemeter.marton
 *
 */
public abstract class Equipment extends Wearable {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Equipment";
	
	// Private Attributes
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor
	 */
	public Equipment() {
		super();
		LogHandler.logFunctionCall(Equipment.logName, "Constructor");
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	// See ancestor for documentation.
	public boolean decay() {
		LogHandler.logFunctionCall(Equipment.logName, "decay");
		LogHandler.decrementIndentation();
		return true;
	}
	
	// See ancestor for documentation
	public void setTarget(Virologist target) {
		LogHandler.logFunctionCall(Equipment.logName, "setTarget");
		
		this.target = target;
		
		LogHandler.decrementIndentation();
	}
	
	public int getTTL() {
		return Integer.MAX_VALUE;
	}
}
