package softprojlab.model.item.agent;

// Java imports
import java.util.ArrayList;
import java.util.List;

// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.Game;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Nucleotide;

/**
 * Agent that makes the victim move randomly, on the neighbouring fields.
 * @author pfemeter.marton
 *
 */
public class DanceAgent extends Agent {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "DanceAgent";
	
	// Private Attributes
	
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor
	 */
	public DanceAgent() {
		super();
		
		this.agentId = 2;
		
		LogHandler.logFunctionCall(DanceAgent.logName, "Constructor");
		// Aminoacids in price
		this.price.add(3);

		// Nucleotides in price
		this.price.add(3);
		LogHandler.decrementIndentation();
	}

	
	// Private Methods

	
	
	// Public Methods
	
	// See ancestor for documentation.
	public Agent craft(ArrayList<Aminoacid> aminoacids, ArrayList<Nucleotide> nucleotides) {
		LogHandler.logFunctionCall(DanceAgent.logName, "craft");
		DanceAgent copy = null;
		if (aminoacids.size() >= super.price.get(0) && nucleotides.size() >= super.price.get(1)) {
			for (int i = 0; i < super.price.get(0); i++)
				aminoacids.remove(0);
			for (int i = 0; i < super.price.get(1); i++)
				nucleotides.remove(0);
			copy = new DanceAgent();
			copy.timeToLive = this.timeToLive;
		}
		LogHandler.decrementIndentation();
		return copy;
	}

	// See ancestor for documentation.
	public boolean decay() {
		LogHandler.logFunctionCall(DanceAgent.logName, "decay");
		if (this.target != null)
			while (this.target.getActionTokens() > 0 ) {
				int randomNumber;
	        	if (Game.randomness)
	        		randomNumber = (int) Math.floor(Math.random() * 10);
	        	else
	        		randomNumber = Game.askForUserInput("Please choose which direction to move: ", new ArrayList<String>(List.of("0-9")), true);
				this.target.move(randomNumber);
			}
		LogHandler.decrementIndentation();
		return super.decay();
	}
	
	public String getDisplayableName() {
		return "Dance agent";
	}

}
