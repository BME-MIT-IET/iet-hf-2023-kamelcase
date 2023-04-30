package softprojlab.model.field;

// Java imports
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.material.*;

/**
 * Field with Material on it.
 * @author pfemeter.marton
 *
 */
public class Storage extends Field {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Storage";
	
	// Private Attributes
	
	/**
	 * Materials to get by characters.
	 */
	private ArrayList<Material> loot;

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 * Initializes loot with random amount of materials.
	 * @param aminoNumber The number of Aminoacids this Storage should contain.
	 * @param nucleotideNumber The number of Nucleotides this Storage should contain.
	 */
	public Storage(int aminoNumber, int nucleotideNumber) {
		super();
		LogHandler.logFunctionCall(Storage.logName, "Constructor");
		this.loot = new ArrayList<Material>();
		for (int i = 0; i < aminoNumber; ++i) {
			this.loot.add(new Aminoacid());
		}
		for (int i = 0; i < nucleotideNumber; ++i) {
			this.loot.add(new Nucleotide());
		}
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	/**
	 * Implements ancestor method.
	 * Adds Materials to the target.
	 * If the filed is not empty, the character will get the loot.
	 * @param target The recipient of the loot.
	 * @return Whether the operation was successful.
	 */
	public Boolean getLoot(Virologist target) {
		LogHandler.logFunctionCall(Storage.logName, "getLoot");
		
		if (this.loot.isEmpty())
			return false;
		
		while( !this.loot.isEmpty() ) {
			if (this.loot.get(0).addToTarget(target))
				this.loot.remove(0);
			else
				break;
		}
		
		LogHandler.decrementIndentation();
		return true;
	}

	/**
	 * Add the given material to the loot.
	 * The material being added to the end of the loot.
	 * @param material The material to add.
	 */
	public void addMaterial(Material material) {
		this.loot.add(material);
	}

	@Override
	public String toYAML() {
		StringBuilder yaml = new StringBuilder(super.toYAML());
		int aminoNumber = 0;
		int nucleotideNumber = 0;
		for (Material material : this.loot)
			if (material instanceof Aminoacid)
				aminoNumber++;
			else
				nucleotideNumber++;

		yaml.append("materials: ").append("\n");
		yaml.append("    aminoacids: ").append(aminoNumber).append("\n");
		yaml.append("    nucleotides: ").append(nucleotideNumber).append("\n");

		return yaml.toString();
	}
	
	public String getDisplayableName() {
		return "Storage";
	}
}
