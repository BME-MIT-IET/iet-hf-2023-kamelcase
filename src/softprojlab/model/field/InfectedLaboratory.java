package softprojlab.model.field;

import softprojlab.model.character.Virologist;
import softprojlab.model.item.agent.Agent;
import softprojlab.model.item.agent.BearAgent;

public class InfectedLaboratory extends Laboratory {

    /**
     * Default constructor
     * Initializes the loot(Agent) to the given Agent.
     *
     * @param a Agent the agent we want to be able to find at the LAB.
     */
    public InfectedLaboratory(Agent a) {
        super(a);
    }
    
    @Override
    public void acceptVirologist(Virologist virologist) {
		super.acceptVirologist(virologist);
		virologist.addToAppliedAgents(new BearAgent(), null);
	}
	
	public String getDisplayableName() {
		return "Infected laboratory";
	}
}
