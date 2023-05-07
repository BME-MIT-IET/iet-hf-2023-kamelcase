package softprojlab.model.item.agent;

// Java imports
import java.util.ArrayList;

// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.item.Wearable;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Nucleotide;

/**
 * Abstract class representing agents, which can be applied to characters.
 * @author pfemeter.marton
 *
 */
public abstract class Agent extends Wearable {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Agent";
	
	/**
	 * For identifying the different type of Agents (so that a Virologist can't learn the same Agent twice).
	 */
	protected Integer agentId;
		
	/**
	 * The price of crafting this Agent in Aminoacids and Nucleotides.
	 */
	protected ArrayList<Integer> price;
	
	protected Virologist owner;

	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 */
	public Agent() {
		super();
		LogHandler.logFunctionCall(Agent.logName, "Constructor");
		this.target = null;
		this.owner = null;
		this.timeToLive = 3;
		
		this.price = new ArrayList<Integer>();
		
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	
	
	// Public Methods
	
	public boolean equals(Object obj) {
		if (obj != null) {
			try {
				Agent query = (Agent) obj;
				if (this.agentId != null && query.agentId != null) {
					return (this.agentId == query.agentId);
				}
			} catch (Exception error) {
				// Error while casting, so they are not equal
			}
		}
		return false;
	}
	
	public int hashCode() {
		return this.agentId;
	}
	
	/**
	 * Crafts an instance of this Agent. If the crafting is successful, the required materials are going to get subtracted from the specified arrays.
	 * @param aminoacids The amount of Aminoacids we have.
	 * @param nucleotides The amount of nucleotides we have.
	 * @return An instance of this Agent if successful, or null if not.
	 */
	public abstract Agent craft(ArrayList<Aminoacid> aminoacids, ArrayList<Nucleotide> nucleotides);
	
	// See ancestor for documentation.
	public boolean decay() {
		--this.timeToLive;
		if (this.timeToLive <= 0) {
			if (this.target != null)
				this.target.removeAgent(this);
			else
				this.owner.removeAgent(this);
			this.target = null;
			return false;
		}
		return true;
	}
	
	// See ancestor for documentation.
	public void setTarget(Virologist target) {
		LogHandler.logFunctionCall(Agent.logName, "setTarget");
		
		this.target = target;
		
		LogHandler.decrementIndentation();
	}
	public void setOwner(Virologist newvirologist){owner = newvirologist;}
	public String toYAML() {
		String yaml = "- kind: " + 
				this.getClass().getSimpleName() + 
				"\n  ttl: " + this.timeToLive;
		if (this.target != null)
			yaml += "\n  target: " + this.target.getUid();
		else if (this.owner != null)
			yaml += "\n  owner: " + this.owner.getUid();
		else
			return this.getClass().getSimpleName();
		return yaml;
	}
	
	public int getTTL() {
		return timeToLive;
	}

}