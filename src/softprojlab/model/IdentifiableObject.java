package softprojlab.model;

// Java imports


// Project imports
import softprojlab.cli.YAMLExportable;
import softprojlab.main.LogHandler;

/**
 * Abstract ancestor class for objects which need to be uniquely identified. Each instance has a UID.
 * @author pfemeter.marton
 *
 */
public abstract class IdentifiableObject implements YAMLExportable {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "IdentifiableObject";
	
	// Private Attributes
	
	/**
	 * The unique identifier of this instance.
	 */
	private Integer uid;

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 * this.uid must be set separately.
	 */
	public IdentifiableObject() {
		LogHandler.logFunctionCall(IdentifiableObject.logName, "Constructor");
		this.uid = null;
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	/**
	 * Get the UID of this instance.
	 * @return this.uid, or -1 if this.uid is null
	 */
	public int getUid() {
		LogHandler.logFunctionCall(IdentifiableObject.logName, "getUid");
		LogHandler.decrementIndentation();
		if (this.uid == null) {
			return -1;
		}
		return this.uid;
	}
	
	/**
	 * Sets the value of this.uid.
	 * @param value The new value of this.uid.
	 */
	public void setUid(int value) {
		LogHandler.logFunctionCall(IdentifiableObject.logName, "setUid");
		this.uid = value;
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Returns the displayable name of this object
	 * @return
	 */
	public abstract String getDisplayableName();
}
