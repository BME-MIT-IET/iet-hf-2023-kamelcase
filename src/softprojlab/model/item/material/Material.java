package softprojlab.model.item.material;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;

/**
 * Abstract class representing the price of crafting Agents.
 * @author pfemeter.marton
 *
 */
public abstract class Material {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Material";
	
	// Private Attributes
	

	// Public Attributes
	
	/**
	 * UID for each Material instance.
	 * Read-only. Serves cosmetic purposes only.
	 * Different behavior and use from IdentifiableObject UID.
	 */
	public final String chemicalFormula;	
	
	// Constructors
	
	/**
	 * Default constructor
	 * @param formula this.chemicalFormula attribute.
	 */
	public Material(String formula) {
		LogHandler.logFunctionCall(Material.logName, "Constructor");
		this.chemicalFormula = formula;
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	/**
	 * Adds this object to the specified target.
	 * @param target The recipient of this object.
	 * @return Whether the operation was successful.
	 */
	public abstract Boolean addToTarget(Virologist target);
	
	/**
	 * Gets this.chemicalFormula.
	 * @return this.chemicalFormula
	 */
	public String getFormula() {
		LogHandler.logFunctionCall(Material.logName, "getFormula");
		LogHandler.decrementIndentation();
		return this.chemicalFormula;
	}
}
