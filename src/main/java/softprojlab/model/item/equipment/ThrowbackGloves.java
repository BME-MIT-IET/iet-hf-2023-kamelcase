package softprojlab.model.item.equipment;

// Java imports


// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.Game;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.agent.Agent;

/**
 * Equipment that can return an Agent to the source of the Agent.
 * @author pfemeter.marton
 *
 */
public class ThrowbackGloves extends Equipment {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "ThrowbackGloves";
	
	// Private Attributes
	/**
	 * The counter how many usage left before it is going to be unsusable.
	 */
	private int remainingThrows;
	
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 */
	public ThrowbackGloves() {
		super();
		remainingThrows = 3;
		LogHandler.logFunctionCall(ThrowbackGloves.logName, "Constructor");
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods

	/**
	 * Default implementation for handling Agent spreading.
	 * Lowering the remainingThrows
	 * @param query The Agent being applied.
	 * @param source The attacker, the character who is spreading the Agent.
	 * @return Whether the action was handled.
	 */
	public Boolean handleAgentSpread(Agent query, Virologist source) {
		LogHandler.logFunctionCall(ThrowbackGloves.logName, "handleAgentSpread");
		LogHandler.decrementIndentation();

		// A source null, ha InfectedLaboratory dob BearAgent-et
		if( remainingThrows > 0 && source != null) {
			if (Game.askYesNo("Should the virologist throw back the Agent?")) {
				remainingThrows--;
				System.out.println(remainingThrows);
				source.addToAppliedAgents(query, this.target);
			}
			if (remainingThrows <= 0)
				this.target.removeEquipment(this);
			return true;
		} else
			return false;
	}

	@Override
	public String toYAML() {
		return "glo";
	}
	
	public String getDisplayableName() {
		return "Gloves";
	}
	
	@Override
	public int getTTL() {
		return remainingThrows;
	}
}
