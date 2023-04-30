package softprojlab.model.item;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.IdentifiableObject;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.agent.Agent;

/**
 * Abstract class representing wearable items by the characters.
 * @author pfemeter.marton
 *
 */
public abstract class Wearable extends IdentifiableObject {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Wearable";
	
	// Private Attributes
	
	/**
	 * The character this item is applied on.
	 */
	protected Virologist target;
	
	/**
	 * The number of rounds left before this object expires.
	 */
	protected int timeToLive;

	// Public Attributes
	
	
	
	// Constructors
	
	public Wearable() {
		super();
		LogHandler.logFunctionCall(Wearable.logName, "Constructor");
		this.target = null;
		this.timeToLive = 2;
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	/**
	 * Decreases this.timeToLive. 
	 * @return false if no longer present on wearer i.e decayed
	 */
	public abstract boolean decay();
	
	/**
	 * Adds this item to the target, and sets this.target.
	 * @param target The target of the operation.
	 */
	public abstract void setTarget(Virologist target);
	
	/**
	 * Default implementation for handling Agent spreading.
	 * @param query The Agent being applied.
	 * @param source The attacker, the character who is spreading the Agent.
	 * @return Whether the action was handled.
	 */
	public Boolean handleAgentSpread(Agent query, Virologist source) {
		LogHandler.logFunctionCall(Wearable.logName, "handleAgentSpread");
		LogHandler.decrementIndentation();
		return false;
	}
	
	/**
	 * Default implementation for handling stealing.
	 * @param source The recipient of the stolen item.
	 * @param target The victim, the caracter who was targeted for stealing.
	 * @return Whether the action was handled.
	 */
	public Boolean handleSteal(Virologist source, Virologist target) {
		LogHandler.logFunctionCall(Wearable.logName, "handleSteal");
		LogHandler.decrementIndentation();
		return false;
	}
	
	/**
	 * Default implementation for handling Material capacity increasing.
	 * @param original The original capacity for materials.
	 * @return The new capacity amount.
	 */
	public int handleBagCapacity(int original) {
		LogHandler.logFunctionCall(Wearable.logName, "handleBagCapacity");
		LogHandler.decrementIndentation();
		return original;
	}

	/**
	 *  Default implementation for handling kill atempt.
	 * @param victim the target, the caracter who was targeted for kill atempt.
	 * @return The atempt out come (true if it was successfull and false if it wasn't)
	 */
	public boolean handleKillAttempt(Virologist victim){
		return false;
	}
	
	/**
	 * Returns the remaining time to live for a wearable
	 * @return
	 */
	public abstract int getTTL();
}
