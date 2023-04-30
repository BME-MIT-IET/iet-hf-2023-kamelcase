package softprojlab.model.item.agent;

//Java imports
import java.util.ArrayList;

//Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Nucleotide;

/**
 * Agent that protects the victim.
 * @author pfemeter.marton
 *
 */
public class ProtectiveAgent extends Agent {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "ProtectiveAgent";
	
	// Private Attributes
	
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 */
		public ProtectiveAgent() {
			super();
			
			this.agentId = 5;
			
			LogHandler.logFunctionCall(ProtectiveAgent.logName, "Constructor");
			// Aminoacids in price
			this.price.add(2);

			// Nucleotides in price
			this.price.add(2);
			LogHandler.decrementIndentation();
		}
	
	// Private Methods

	
	
	// Public Methods
	
	// See ancestor for documentation.
	public Agent craft(ArrayList<Aminoacid> aminoacids, ArrayList<Nucleotide> nucleotides) {
		LogHandler.logFunctionCall(ProtectiveAgent.logName, "craft");
		ProtectiveAgent copy = null;
		if (aminoacids.size() >= super.price.get(0) && nucleotides.size() >= super.price.get(1)) {
			for (int i = 0; i < super.price.get(0); i++)
				aminoacids.remove(0);
			for (int i = 0; i < super.price.get(1); i++)
				nucleotides.remove(0);
			copy = new ProtectiveAgent();
			copy.timeToLive = this.timeToLive;
		}
		LogHandler.decrementIndentation();
		return null;
	}

	public boolean decay() {
		LogHandler.logFunctionCall(ProtectiveAgent.logName, "decay");
		LogHandler.decrementIndentation();
		return super.decay();
	}
	
	// See ancestor for documentation.
	public Boolean handleAgentSpread(Agent query, Virologist source) {
		LogHandler.logFunctionCall(ProtectiveAgent.logName, "handleAgentSpread");
		LogHandler.decrementIndentation();
		return true;
	}
	
	public String getDisplayableName() {
		return "Protective agent";
	}
}
