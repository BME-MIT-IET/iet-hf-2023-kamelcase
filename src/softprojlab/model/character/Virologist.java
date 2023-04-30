package softprojlab.model.character;

// Java imports
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import softprojlab.cli.YAMLExportable;
// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.Game;
import softprojlab.model.IdentifiableObject;
import softprojlab.model.field.Field;
import softprojlab.model.item.Wearable;
import softprojlab.model.item.agent.Agent;
import softprojlab.model.item.equipment.Equipment;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Material;
import softprojlab.model.item.material.Nucleotide;

/**
 * Principal controllable characters in-game. The players control it.
 * Can pick up Wearable and Material items, up to a limit.
 * Can interact with other instances of its class. Must always be on a Field (if not, then that instance is considered dead).
 * @author pfemeter.marton
 *
 */
public class Virologist extends IdentifiableObject implements UpdateableCharacter {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Virologist";
	
	// Private Attributes
	
	/**
	 * Aminoacids the character has. Maximum capacity: this.materialcap.
	 */
	private ArrayList<Aminoacid> aminoacidStorage;
	
	/**
	 * Agents that are currently in effect on this instance.
	 */
	private ArrayList<Agent> appliedAgents;
	
	/**
	 * Equipments that are on the character. Maximum capacity: 3.
	 */
	private ArrayList<Equipment> wornEquipments;
	
	/**
	 * Agents that the character knows, and can craft.
	 */
	private LinkedHashSet<Agent> learnedAgents;
	
	/**
	 * The Field the character is on.
	 */
	private Field location;
	
	/**
	 * The maximum number of items this character can have from a type of Material at a given time.
	 * Default value: 6.
	 */
	private int materialCap;
	
	/**
	 * Nucleotides the character has. Maximum capacity: this.materialcap.
	 */
	private ArrayList<Nucleotide> nucleotideStorage;
	
	/**
	 * Agents that have already been crafted, and can be applied.
	 */
	private ArrayList<Agent> synthesisedAgents;

	// Public Attributes
	
	/**
	 * The number of actionTokens this character currently has. Characters can do actions by spending these tokens.
	 * Characters get actionTokens at the start of each round. See exact cost of actions in the method descriptions of this class.
	 */
	public int actionTokens;
	
	// Constructors
	
	/**
	 * Default constructor.
	 * @param startLocation The location to place this Virologist.
	 */
	public Virologist(Field startLocation) {
		LogHandler.logFunctionCall(Virologist.logName, "Constructor");
		
		this.aminoacidStorage = new ArrayList<Aminoacid>();
		this.appliedAgents = new ArrayList<Agent>();
		this.wornEquipments = new ArrayList<Equipment>();
		this.learnedAgents = new LinkedHashSet<Agent>();
		this.location = startLocation;
		if (startLocation != null) {
			startLocation.acceptVirologist(this);
		}
		this.materialCap = 6;
		this.nucleotideStorage = new ArrayList<Nucleotide>();
		this.synthesisedAgents = new ArrayList<Agent>();
		this.actionTokens = 0;
		
		LogHandler.decrementIndentation();
	}
	
	// Private Methods

	/**
	 * Adds a Material item to storage.
	 * @param <T> The type of Material to add. Must be descendant of Material.
	 * @param item The instance to add to storage.
	 * @param storage The storage to add item to.
	 * @return Whether the operation was successful (storage had enough capacity, and no parameters were null).
	 */
	private <T extends Material> boolean addMaterial(T item, ArrayList<T> storage) {
		if (item == null || storage == null) {
			return false;
		}

		// Iterating through handlers
		int totalCap = this.materialCap;
		for (Wearable capacityIterator: this.wornEquipments) {
			totalCap = capacityIterator.handleBagCapacity(totalCap);
		}
		for (Wearable capacityIterator: this.appliedAgents) {
			totalCap = capacityIterator.handleBagCapacity(totalCap);
		}


		// Calculating free capacity for material
		int freeCap = totalCap - storage.size();
		if (freeCap >= 1) {
			storage.add(item);
			return true;
		}

		return false;
	}

	/**
	 * Calls toYAML method on each object in itemList, then indents each row with the specified number of spaces.
	 * @param itemList The list of objects generating the YAML documentation to indent.
	 * @param indentation The number of spaces to indent each row by.
	 * @return A concatenated, indented version of the YAML documentation of each object in itemList.
	 */
	private <T extends YAMLExportable, C extends Collection<T>> String createIndentedListYaml(C itemList, int indentation) {
		String result = "";

		// iterating through each item
		for (YAMLExportable iterator: itemList) {
			String[] rowList = iterator.toYAML().split("\n");

			// Indenting each row
			for (String row: rowList) {
				String localResult = "";
				for (int i = 0; i < indentation; ++i) {
					localResult += ' ';
				}

				localResult += row + '\n';

				result += localResult;
			}
		}

		return result;
	}

	/**
	 * Checks whether the parameterized Virologist is on the same field as this Virologist is.
	 * @param query The Virologist that needs to be validated.
	 * @return Whether query is on the same field as this Virologist.
	 */
	private boolean validateOtherVirologist(Virologist query) {
		if (query == null) {
			return false;
		}

		// Checking if this.location knows about the queried Virologist
		return this.location.getPlayers().contains(query);
	}

	// Public Methods
	
	/**
	 * Add Aminoacid to this.aminoacidStorage.
	 * @param material The Aminoacid to add.
	 * @return Whether the operation was successful. True if capacity was not yet reached.
	 */
	public Boolean addAminoacid(Aminoacid material) {
		LogHandler.logFunctionCall(Virologist.logName, "addAminoacid");
		LogHandler.decrementIndentation();

		return this.addMaterial(material, this.aminoacidStorage);
	}
	
	/**
	 * Add Equipment to this.wornEquipments.
	 * If at capacity, prompts the user if they want to replace an old item with the new one.
	 * @param equipment The Equipment to add.
	 * @return Whether the operation was successful. True if capacity was not yet reached.
	 */
	public Boolean addEquipment(Equipment equipment) {
		LogHandler.logFunctionCall(Virologist.logName, "addEquipment");
		LogHandler.decrementIndentation();

		if (this.wornEquipments.size() >= 3) {

			// If already at capacity, asking user if they want to drop something from this.wornEquipments
			String question = "Would you like to drop an equipment item to pick up the new one?";
			ArrayList<String> optionList = new ArrayList<String>();

			for (Equipment previousIterator: this.wornEquipments) {
				String localOption = "Type: " + previousIterator.getClass().getSimpleName();
				optionList.add(localOption);
			}

			int response = -1;
			try {
				response = Game.askForUserInput(question, optionList, false);
			} catch (IndexOutOfBoundsException error) {
				System.out.println("Error parsing response as integer in range. Aborting picking up new item.");
				return false;
			}

			if (response < 0 || response >= this.wornEquipments.size()) {
				return false;
			}
			this.wornEquipments.remove(response);
			this.wornEquipments.add(response, equipment);
			equipment.setTarget(this);
			return true;
		} else {
			// If not at capacity, adding item automatically
			this.wornEquipments.add(equipment);
			equipment.setTarget(this);
			return true;
		}
	}
	
	/**
	 * Add Nucleotide to this.nucleotideStorage.
	 * @param material The Nucleotide to add.
	 * @return Whether the operation was successful. True if capacity was not yet reached.
	 */
	public Boolean addNucleotide(Nucleotide material) {
		LogHandler.logFunctionCall(Virologist.logName, "addNucleotide");
		LogHandler.decrementIndentation();

		return this.addMaterial(material, this.nucleotideStorage);
	}
	
	/**
	 * Handles applying the specified Agent to this instance.
	 * Iterates through every Wearable already applied on this instance (this.wornEquipments, this.appliedAgents).
	 * If any of them handle the event, the agent does not get applied. Otherwise, it does get applied by default. 
	 * @param agent The Agent to apply.
	 * @param attacker The initiator of this method call.
	 */
	public void addToAppliedAgents(Agent agent, Virologist attacker) {
		LogHandler.logFunctionCall(Virologist.logName, "addToAppliedAgents");
		
		if (agent == null) {
			return;
		}

		// Iterating through handlers
		boolean isHandled = false;
		for (Wearable spreadIterator: this.wornEquipments) {
			if (spreadIterator.handleAgentSpread(agent, attacker)) {
				isHandled = true;
				break;
			}
		}
		if (!isHandled) {
			for (Wearable spreadIterator: this.appliedAgents) {
				if (spreadIterator.handleAgentSpread(agent, attacker)) {
					isHandled = true;
					break;
				}
			}

			// If spreading was not handled, then the default implementation is applying the agent
			if (!isHandled) {
				this.appliedAgents.add(agent);
				agent.setTarget(this);
			}
		}
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Tries applying the specified Agent to the victim.
	 * Cost: 3 actionTokens.
	 * @param victim The target of the operation.
	 */
	public void applyAgentTo(Virologist victim) {
		LogHandler.logFunctionCall(Virologist.logName, "applyAgentTo");
	
		if (!this.validateOtherVirologist(victim) || this.synthesisedAgents.isEmpty()) {
			return;
		}

		int actionCost = 3;
		if (this.actionTokens >= actionCost) {
			Agent agent = this.synthesisedAgents.remove(0);
			agent.setOwner(null);
			this.actionTokens -= actionCost;
			victim.addToAppliedAgents(agent, this);
		}
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Removes this instance from the Field it is standing on. This instance cannot be interacted with afterwards.
	 * Also clears every attribute in this instance, removing itself from Wearables' target and/or owner attributes.
	 */
	public void die() {
		LogHandler.logFunctionCall(Virologist.logName, "die");

		this.actionTokens = 0;
		this.materialCap = 0;
		
		this.aminoacidStorage.clear();
		this.nucleotideStorage.clear();
		
		for (Agent iterator: this.appliedAgents) {
			iterator.setTarget(null);
		}
		this.appliedAgents.clear();
		
		for (Agent iterator: this.synthesisedAgents) {
			iterator.setOwner(null);
		}
		this.synthesisedAgents.clear();
		
		for (Agent iterator: this.learnedAgents) {
			iterator.setOwner(null);
		}
		this.learnedAgents.clear();
		
		for (Equipment iterator: this.wornEquipments) {
			iterator.setTarget(null);
		}
		this.wornEquipments.clear();
		
		this.location.removeVirologist(this);
		this.location = null;

		LogHandler.decrementIndentation();
	}
	
	/**
	 * Clears this.learnedAgents.
	 */
	public void forgetLearnedAgents() {
		LogHandler.logFunctionCall(Virologist.logName, "forgetLearnedAgents");
		
		this.learnedAgents.clear();
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Tries killing the victim. Only successful, if attacker (this) has an Axe.
	 * @param victim The target of the operation.
	 * @return Whether the operation was successful.
	 */
	public Boolean kill(Virologist victim) {
		LogHandler.logFunctionCall(Virologist.logName, "kill");
		
		int actionCost = 5;
		if (this.actionTokens < actionCost) {
			return false;
		}
		
		if (victim == null || !this.validateOtherVirologist(victim)) {
			return false;
		}

		// Iterating through handlers
		boolean isHandled = false;
		for (Wearable spreadIterator: this.wornEquipments) {
			if (spreadIterator.handleKillAttempt(victim)) {
				isHandled = true;
				break;
			}
		}
		if (!isHandled) {
			for (Wearable spreadIterator: this.appliedAgents) {
				if (spreadIterator.handleKillAttempt(victim)) {
					isHandled = true;
					break;
				}
			}
		}

		if (isHandled) {
			this.actionTokens -= actionCost;
		}

		LogHandler.decrementIndentation();

		// If kill attempt was not handled, then the default implementation is not killing the victim
		return isHandled;
	}

	/**
	 * Adds a new Agent to this.learnedAgents, if it is not already there.
	 * @param agent The Agent to learn.
	 */
	public void learnAgent(Agent agent) {
		LogHandler.logFunctionCall(Virologist.logName, "learnAgent");
		
		if (!this.learnedAgents.contains(agent)) {
			this.learnedAgents.add(agent);
		}
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Moves the character to the next Field in the specified direction.
	 * Cost: 1 actionToken.
	 * @param direction The direction to move in.
	 * @return Whether the operation was successful.
	 */
	public Boolean move(int direction) {
		LogHandler.logFunctionCall(Virologist.logName, "move");
		
		int actionPrice = 1;
		Boolean canAffordIt = (this.actionTokens >= actionPrice);
		if (canAffordIt) {
			ArrayList<Field> destinationList = this.location.getNeighbors();
			if (direction < 0) {
				LogHandler.decrementIndentation();
				return false;
			}
			
			if (destinationList.size() > 0) {
				direction = direction % destinationList.size();
				Field destination = destinationList.get(direction);
				this.setLocation(destination);
				this.actionTokens -= actionPrice;
				LogHandler.decrementIndentation();
				return true;
			} else {
				// Ensure correct behaviour for BearAgent and DanceAgent
				this.actionTokens -= actionPrice;
				LogHandler.decrementIndentation();
				return false;
			}
		}
		
		LogHandler.decrementIndentation();
		return false;
	}
	
	/**
	 * Removes an Agent from this.appliedAgents, or this.synthesisedAgent.
	 * @param agent The Agent to remove.
	 */
	public void removeAgent(Agent agent) {
		LogHandler.logFunctionCall(Virologist.logName, "removeAgent");

		if (!this.appliedAgents.remove(agent)) {
			this.synthesisedAgents.remove(agent);
		}
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Removes an Equipment from the character.
	 * @param which The Equipment to remove.
	 * @return The queried Equipment, or null.
	 */
	public Equipment removeEquipment(Equipment which) {
		LogHandler.logFunctionCall(Virologist.logName, "removeEquipment");
		
		Equipment result = null;
		if ( !this.wornEquipments.isEmpty() ) {
			if (which == null) {
				result = this.wornEquipments.remove(0);
			} else {
				int relevantIndex = this.wornEquipments.indexOf(which);
				if (relevantIndex >= 0) {
					result = this.wornEquipments.remove(relevantIndex);
				}
			}
		}
		
		LogHandler.decrementIndentation();
		return result;
	}
	
	/**
	 * Handles steal attempts on a Virologist.
	 * @param attacker The Virologist initiating the attack.
	 * @return Whether the operation was successful.
	 */
	public boolean beingRobbed(Virologist attacker) {

		boolean isHandled = false;
		for (Wearable stealIterator: this.wornEquipments) {
			if (stealIterator.handleSteal(attacker, this)) {
				isHandled = true;
				break;
			}
		}
		if (!isHandled) {
			for (Wearable stealIterator: this.appliedAgents) {
				if (stealIterator.handleSteal(attacker, this)) {
					isHandled = true;
					break;
				}
			}
		}

		return isHandled;
	}

	/**
	 * Steals an Equipment from the victim. Only possible, if the victim is paralyzed.
	 * Cost: 3 actionTokens.
	 * @param victim The target of the operation.
	 */
	public void stealFrom(Virologist victim) {
		LogHandler.logFunctionCall(Virologist.logName, "stealFrom");
		
		if (!this.validateOtherVirologist(victim)) {
			return;
		}

		int cost = 3;
		if (this.actionTokens >= cost) {
			this.actionTokens -= cost;
			victim.beingRobbed(this);
		}
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Crafts the specified Agent (must be from this.learnedAgents), using this.aminoacidStorage and this.nucleotideStorage, and adds it to this.synthesisedAgents if successful.
	 * Cost: 1 actionTokens.
	 * Enough material must be available for crafting. See material costs at the documentation of the specific Agent classes.
	 * @param agent The Agent to craft from this.learnedAgents.
	 * @return Whether the operation was successful.
	 */
	public Boolean synthetiseAgent(Agent agent) {
		LogHandler.logFunctionCall(Virologist.logName, "synthetiseAgent");

		int actionCost = 1;
		if (this.actionTokens < actionCost || agent == null || !this.learnedAgents.contains(agent)) {
			return false;
		}

		LogHandler.decrementIndentation();
		if (this.craftAgent(agent)) {
			this.actionTokens -= actionCost;
			return true;
		}
		return false;
	}

	/**
	 * Crafts a new Agent from the specified aminoacid and nucleotide.
	 * @param agent The agent to craft.
	 * @return True if the operation was successful.
	 */
	public boolean craftAgent(Agent agent) {

		Agent weapon = agent.craft(this.aminoacidStorage, this.nucleotideStorage);
		if (weapon == null) {
			return false;
		}

		this.addCreatedAgent(weapon);

		return true;
	}
	
	/**
	 * Adds a new agent to the Virologists created agents array.
	 * @param a The created Agent.
	 */
	public void addCreatedAgent(Agent a) {
		this.synthesisedAgents.add(a);
		a.setOwner(this);
	}

	/**
	 * Tries looting from this.location.
	 * Cost: 2 actionTokens.
	 * @return Whether the operation was successful.
	 */
	public Boolean tryLooting() {
		LogHandler.logFunctionCall(Virologist.logName, "tryLooting");
		
		int actionCost = 2;
		if (this.actionTokens < actionCost || this.location == null) {
			return false;
		}
		
		LogHandler.decrementIndentation();

		this.actionTokens -= actionCost;
		return this.location.getLoot(this);
	}
	
	// See UpdateableCharacter for documentation.
	public int update() {
		LogHandler.logFunctionCall(Virologist.logName, "update");
		
		int index = 0;
		while (index < this.appliedAgents.size()) {
			if (this.appliedAgents.get(index).decay()) {
				++index;
			}
		}
		
		index = 0;
		while (index < this.synthesisedAgents.size()) {
			if (this.synthesisedAgents.get(index).decay()) {
				++index;
			}
		}

		LogHandler.decrementIndentation();
		
		return this.learnedAgents.size();
	}

	@Override
	public String toYAML() {
		String result = "kind: Virologist\n";
		result += "id: " + this.getUid() + '\n';
		result += "position: " + ( this.location == null ? null : this.location.getUid() ) + '\n';
		result += "agents:\n";

		result += "    applied:\n";
		result += this.createIndentedListYaml(this.appliedAgents, 6);

		result += "    learned:\n";
		result += this.createIndentedListYaml(this.learnedAgents, 6);

		result += "    crafted:\n";
		result += this.createIndentedListYaml(this.synthesisedAgents, 6);

		result += "equipments:\n";
		result += this.createIndentedListYaml(this.wornEquipments, 2);

		result += "materials:\n";
		result += "    aminoacids: " + this.aminoacidStorage.size() + '\n';
		result += "    nucleotides: " + this.nucleotideStorage.size() + '\n';
		result += "actionTokens: " + this.actionTokens + '\n';


		return result;
	}

	/**
	 * Returns the Field where this Virologist is located.
	 * @return The Field.
	 */
	public Field getLocation() {
		return this.location;
	}
	
	/**
	 * Sets the new location of the Virologist. Necessary for CLI interaction
	 * @param newLocation the new location where the virologist will be
	 */
	public void setLocation(Field newLocation) {
		if (this.location != null) {
			this.location.removeVirologist(this);
		}
		newLocation.acceptVirologist(this);
		this.location = newLocation;
	}
	
	/**
	 * Returns this.learnedAgents
	 * @return A reference to this.learnedAgents.
	 */
	public LinkedHashSet<Agent> getLearnedAgents() {
		return this.learnedAgents;
	}

	/**
	 * Returns this.appliedAgents
	 * @return A reference to this.appliedAgents.
	 */
	public ArrayList<Agent> getAppliedAgents() {
		return this.appliedAgents;
	}
	
	public String getDisplayableName() {
		return "Virologist";
	}
	
	public int getAminoacidCount() {
		return aminoacidStorage.size();
	}
	
	public int getNucleotideCount() {
		return nucleotideStorage.size();
	}
	
	public List<Agent> getDeepSynthesised() {
		return Collections.unmodifiableList(synthesisedAgents);
	}
	
	public Set<Agent> getDeepLearned() {
		return Collections.unmodifiableSet(learnedAgents);
	}
	
	public List<Agent> getDeepApplied() {
		return Collections.unmodifiableList(appliedAgents);
	}
	
	public List<Equipment> getDeepEquipment() {
		return Collections.unmodifiableList(wornEquipments);
	}
}
