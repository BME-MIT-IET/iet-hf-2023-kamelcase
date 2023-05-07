package softprojlab.model.item.material;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;

/**
 * A kind of Material.
 * @author pfemeter.marton
 *
 */
public class Aminoacid extends Material {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Aminoacid";
	
	// Private Attributes
	
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 */
	public Aminoacid() {
		super("amino");
		LogHandler.logFunctionCall(Aminoacid.logName, "Constructor");
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	// See ancestor for documentation
	public Boolean addToTarget(Virologist target) {
		LogHandler.logFunctionCall(Aminoacid.logName, "addToTarget");
		

		LogHandler.decrementIndentation();
		return target.addAminoacid(this);
	}
}
