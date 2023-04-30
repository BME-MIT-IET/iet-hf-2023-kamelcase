package softprojlab.model.field;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;

/**
 * Basic field, without anything special on it.
 * @author pfemeter.marton
 *
 */
public class PlainField extends Field {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "PlainField";
	
	// Private Attributes

	// Public Attributes
	
	
	
	// Constructors

	/**
	 * Initializes the field.
	 */
	public PlainField() {
		super();
		LogHandler.logFunctionCall(PlainField.logName, "Constructor");
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	/**
	 * Implements ancestor method.
	 * Doesn't do anything.
	 * @param target The recipient of the loot.
	 * @return Always true.
	 */
	public Boolean getLoot(Virologist target) {
		LogHandler.logFunctionCall(PlainField.logName, "getLoot");
		LogHandler.decrementIndentation();
		return true;
	}
	
	public String getDisplayableName() {
		return "Plain field";
	}
}
