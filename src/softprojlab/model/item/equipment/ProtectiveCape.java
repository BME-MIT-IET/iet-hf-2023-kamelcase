package softprojlab.model.item.equipment;

import java.util.ArrayList;
import java.util.List;

// Java imports


// Project imports

import softprojlab.main.LogHandler;
import softprojlab.model.Game;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.agent.Agent;

/**
 * Equipment that protects the wearer from agent spreadings.
 * @author pfemeter.marton
 *
 */
public class ProtectiveCape extends Equipment {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "ProtectiveCape";
	
	// Private Attributes
	
	/**
	 * The probability of the cape protecting the wearer from Agent spread. Must be between 0 and 1. 
	 */
	private int effectiveness;

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor
	 */
	public ProtectiveCape() {
		super();
		LogHandler.logFunctionCall(ProtectiveCape.logName, "Constructor");
		this.effectiveness = 82;
		LogHandler.decrementIndentation();
	}
	
	// Private Methods


	// Public Methods
	
	// See ancestor for documentation.
	public Boolean handleAgentSpread(Agent query, Virologist source) {
		LogHandler.logFunctionCall(ProtectiveCape.logName, "handleAgentSpread");
		LogHandler.decrementIndentation();

		int random_int;
    	if (Game.randomness)
    		random_int = (int) ((Math.random() * (100)) + 0);
    	else
    		random_int = Game.askYesNo("Should the cape protect the Virologist?") ? 100 : 0;

		if(random_int > 100 - effectiveness)
			return true;
		else
			// failed spreading
			return false;
	}

	@Override
	public String toYAML() {
		return "cap";
	}
	
	public String getDisplayableName() {
		return "Cape";
	}
}
