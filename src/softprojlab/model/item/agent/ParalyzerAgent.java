package softprojlab.model.item.agent;

//Java imports
import java.util.ArrayList;

//Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.equipment.Equipment;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Nucleotide;

/**
 * Agent that blocks the victim from doing any action (i.e.: paralyzes them).
 * @author pfemeter.marton
 *
 */
public class ParalyzerAgent extends Agent {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "ParalyzerAgent";
	
	// Private Attributes
	
	

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor
	 */
	public ParalyzerAgent() {
		super();
		
		this.agentId = 4;
		
		LogHandler.logFunctionCall(ParalyzerAgent.logName, "Constructor");
		// Aminoacids in price
		this.price.add(4);

		// Nucleotides in price
		this.price.add(4);
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	// See ancestor for documentation.
	public Agent craft(ArrayList<Aminoacid> aminoacids, ArrayList<Nucleotide> nucleotides) {
		LogHandler.logFunctionCall(ParalyzerAgent.logName, "craft");
		ParalyzerAgent copy = null;
		if (aminoacids.size() >= super.price.get(0) && nucleotides.size() >= super.price.get(1)) {
			for (int i = 0; i < super.price.get(0); i++)
				aminoacids.remove(0);
			for (int i = 0; i < super.price.get(1); i++)
				nucleotides.remove(0);
			copy = new ParalyzerAgent();
			copy.timeToLive = this.timeToLive;
		}
		LogHandler.decrementIndentation();
		return copy;
	}

	// See ancestor for documentation.
	public boolean decay() {
		LogHandler.logFunctionCall(ParalyzerAgent.logName, "decay");
		if (this.target != null)
			this.target.actionTokens = 0;
		LogHandler.decrementIndentation();
		return super.decay();
	}
	
	// See ancestor for documentation.
	public Boolean handleSteal(Virologist source, Virologist target) {
		LogHandler.logFunctionCall(ParalyzerAgent.logName, "handleSteal");
		Equipment loot = target.removeEquipment(null);
		if (loot != null) {
			source.addEquipment(loot);
		}
		LogHandler.decrementIndentation();
		return (loot != null);
	}
	
	public String getDisplayableName() {
		return "Paralyzer agent";
	}
}
