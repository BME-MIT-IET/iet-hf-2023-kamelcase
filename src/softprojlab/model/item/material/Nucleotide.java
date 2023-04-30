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
public class Nucleotide extends Material {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Nucleotide";
	
	// Private Attributes
	
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 */
	public Nucleotide() {
		super("nucleotide");
		LogHandler.logFunctionCall(Nucleotide.logName, "Constructor");
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	// See ancestor for documentation
	public Boolean addToTarget(Virologist target) {
		LogHandler.logFunctionCall(Nucleotide.logName, "addToTarget");

		LogHandler.decrementIndentation();
		return target.addNucleotide(this);
		
		//LogHandler.decrementIndentation();
		//return true;
	}
}
