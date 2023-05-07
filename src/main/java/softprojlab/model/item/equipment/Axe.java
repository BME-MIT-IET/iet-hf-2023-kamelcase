package softprojlab.model.item.equipment;

import softprojlab.model.character.Virologist;

public class Axe extends Equipment{
    // Static attributes


    // Private Attributes



    // Public Attributes
    /**
     * It shows if it can be useable or not.(ture it is, false it isn't)
     */
    boolean working;

    // Constructors

    /**
     * Default constructor.
     * Set it useable.
     */
    public Axe() {
        super();
        working = true;
    }

    // Private Methods



    // Public Methods

    // See ancestor for documentation.
    @Override
    public boolean handleKillAttempt(Virologist victim){
        if (working) {
            victim.die();
            working = false;

            return true;
        } else
            return false;

    }

	@Override
	public String toYAML() {
		return working ? "axe" : "bluntaxe";
	}
	
	public String getDisplayableName() {
		return working ? "Axe" : "Blunt axe";
	}
	
	@Override
	public int getTTL() {
		return working ? 0 : 1;
	}
}