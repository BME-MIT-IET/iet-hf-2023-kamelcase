package softprojlab.model.field;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.equipment.Equipment;
import softprojlab.model.item.equipment.ProtectiveCape;

/**
 * Field with Equipment on it, that can be looted by characters.
 * @author pfemeter.marton
 *
 */
public class Bunker extends Field {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Bunker";
	
	// Private Attributes
	
	/**
	 * Item that can be found on this field.
	 */
	private Equipment loot;

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 * Initialize the field with a given loot.
	 * @param loot The Equipment to place on this Bunker.
	 */
	public Bunker(Equipment loot) {
		super();
		this.loot = loot;
	}
	
	// Private Methods

	
	
	// Public Methods
	
	/**
	 * Implements ancestor method.
	 * Adds the loot to the character's inventory.
	 * @param target The recipient of the loot.
	 * @return Whether the operation was successful.
	 */
	public Boolean getLoot(Virologist target) {
		if (this.loot != null) {
			if (target.addEquipment(this.loot)) {
				this.loot = null;
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Implements ancestor method.
	 * Sets the loot to the given value.
	 * @param loot The new loot.
	 */
	public void setEquipment(Equipment loot) {
		this.loot = loot;
	}

	@Override
	public String toYAML() {
		StringBuilder yaml = new StringBuilder();
		yaml.append(super.toYAML());
		yaml.append("equipment: ").append(this.loot != null ? this.loot.toYAML() : "").append("\n");

		return yaml.toString();
	}
	
	public String getDisplayableName() {
		return "Bunker";
	}
}
