package softprojlab.model.field;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.agent.Agent;

/**
 * Field with Agent on it.
 * @author pfemeter.marton
 *
 */
public class Laboratory extends Field {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Laboratory";
	
	// Private Attributes
	
	/**
	 * Agent on the field.
	 * Can be looted by Virologist(s).
	 */
	private Agent loot;

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor
	 * Initializes the loot(Agent) to the given Agent.
	 * @param a Agent the agent we want to be able to find at the LAB.
	 */
	public Laboratory(Agent a) {
		super();
		LogHandler.logFunctionCall(Laboratory.logName, "Constructor");
		this.loot = a;
		LogHandler.decrementIndentation();
	}
	
	// Private Methods
	
	
	// Public Methods
	
	/**
	 * Implements ancestor method.
	 * Adds the Agent to the target's knowledge.
	 * @param target The recipient of the loot.
	 * @return Whether the operation was successful.
	 */
	public Boolean getLoot(Virologist target) {

		if (this.loot != null) {
			target.learnAgent(this.loot);
			return true;
		}
		
		return false;
	}

	/**
	 * Implements ancestor method.
	 * Sets the Agent on the field.
	 * @param a The Agent we want to set on the field.
	 */
	public void setAgent(Agent a) {
		this.loot = a;
	}

	@Override
	public String toYAML() {
		StringBuilder yaml = new StringBuilder();
		yaml.append(super.toYAML());
		yaml.append("agent: ").append(this.loot.getClass().getSimpleName()).append("\n");
		return yaml.toString();
	}
	
	public String getDisplayableName() {
		return "Laboratory";
	}
}
