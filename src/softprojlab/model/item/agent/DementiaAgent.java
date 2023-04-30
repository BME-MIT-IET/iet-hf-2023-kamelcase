package softprojlab.model.item.agent;

// Java imports
import java.util.ArrayList;

// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Nucleotide;

/**
 * Agent that makes the victim forget all other Agents.
 * @author pfemeter.marton
 *
 */
public class DementiaAgent extends Agent {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "DementiaAgent";
	
	// Private Attributes
	
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor
	 */
	public DementiaAgent() {
		super();
		
		this.agentId = 3;
		
		LogHandler.logFunctionCall(DementiaAgent.logName, "Constructor");
		// Aminoacids in price
		this.price.add(5);

		// Nucleotides in price
		this.price.add(5);
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	// Public Methods
	
	// See ancestor for documentation.
	public Agent craft(ArrayList<Aminoacid> aminoacids, ArrayList<Nucleotide> nucleotides) {
		LogHandler.logFunctionCall(DementiaAgent.logName, "craft");
		DementiaAgent copy = null;
		if (aminoacids.size() >= super.price.get(0) && nucleotides.size() >= super.price.get(1)) {
			for (int i = 0; i < super.price.get(0); i++)
				aminoacids.remove(0);
			for (int i = 0; i < super.price.get(1); i++)
				nucleotides.remove(0);
			copy = new DementiaAgent();
			copy.timeToLive = this.timeToLive;
		}
		LogHandler.decrementIndentation();
		return copy;
	}

	// See ancestor for documentation.
	public boolean decay() {
		LogHandler.logFunctionCall(DementiaAgent.logName, "decay");
		if (this.target != null) {
			target.forgetLearnedAgents();
			this.timeToLive = 1;
		}
		LogHandler.decrementIndentation();
		return super.decay();
	}
	
	public String getDisplayableName() {
		return "Dementia agent";
	}


}
