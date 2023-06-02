package softprojlab.model.item.agent;

import softprojlab.main.LogHandler;
import softprojlab.model.Game;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.Wearable;

import java.util.ArrayList;
import java.util.List;

public class BearAgent extends DanceAgent{

    // Static attributes

    /**
     * Name for logging.
     */
    private final static String logName = "BearAgent";

    // Private Attributes



    // Public Attributes



    // Constructors

    /**
     * Default constructor
     */
    public BearAgent() {
        super();
        
        this.agentId = 1;
        
		LogHandler.logFunctionCall(BearAgent.logName, "Constructor");
		LogHandler.decrementIndentation();
		this.timeToLive = Integer.MAX_VALUE;
    }


    // Private Methods



    // Public Methods



    // See ancestor for documentation.
    public boolean decay() {
        LogHandler.logFunctionCall(logName, "decay");
        
        if (this.target != null)
	        while (target.getActionTokens() > 0) {
	        	int randomNumber;
	        	if (Game.randomness)
	        		randomNumber = (int) Math.floor(Math.random() * 10);
	        	else
	        		randomNumber = Game.askForUserInput("Please choose which direction to move: ", new ArrayList<String>(List.of("0-9")), true);
		        this.target.move(randomNumber);
		        int old_tokens = target.getActionTokens();
		        this.target.setActionTokens(2);
		        this.target.tryLooting();
		        this.target.setActionTokens(old_tokens);
	        }

        LogHandler.decrementIndentation();
        return super.decay(); // Action first, then decrease ttl
    }

    // See ancestor for documentation.
    public int handleBagCapacity(int original) {
        LogHandler.logFunctionCall(logName, "handleBagCapacity");
        LogHandler.decrementIndentation();
        return 100_000; // Use a reasonably big number
    }

    // See ancestor for documentation.
    public Boolean handleAgentSpread(Agent query, Virologist source) {
        LogHandler.logFunctionCall(logName, "handleAgentSpread");
        if (source == null) {
            LogHandler.decrementIndentation();
        	return true;
        }
        LogHandler.decrementIndentation();
        return false;
    }
	
	public String getDisplayableName() {
		return "Bear agent";
	}
    

}
