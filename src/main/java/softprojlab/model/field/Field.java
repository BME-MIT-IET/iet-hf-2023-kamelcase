package softprojlab.model.field;

// Java imports
import java.util.ArrayList;

// Project imports
import softprojlab.cli.OutputArray;
import softprojlab.model.IdentifiableObject;
import softprojlab.model.character.Virologist;

/**
 * Abstract class representing a place for the characters to stand.
 * The map is built from this kind of fields.
 * @author pfemeter.marton
 *
 */
public abstract class Field extends IdentifiableObject {
	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Field";
	
	// Private Attributes
	
	/**
	 * The neighboring Fields.
	 */
	protected ArrayList<Field> neighbors;
	
	/**
	 * The characters on this Field.
	 */
	protected ArrayList<Virologist> players;
	
	// Public Attributes
	
	
	
	// Constructors
	
	/**
	 * Default constructor.
	 * Initializes the neighbors and players attributes (empty).
	 */
	public Field() {
		this.neighbors = new ArrayList<Field>();
		this.players = new ArrayList<Virologist>();
	}
	
	// Private Methods
	
	
	
	// Public Methods
	
	/**
	 * Adds a specified character to the players(this.players) of this Field.
	 * @param virologist The character to add.
	 */
	public void acceptVirologist(Virologist virologist) {
		this.players.add(virologist);
	}
	
	/**
	 * Appends the specified Field to this Field's neighbors(this.neighbors).
	 * @param query The new neighbor to append.
	 */
	public void addNeighbor(Field query) {
		this.neighbors.add(query);
	}
	
	/**
	 * Adds the loot that can be found on this Field to the specified target.
	 * @param target The character that will receive the loot found on this Field.
	 * @return Whether the operation was successful.
	 */
	public abstract Boolean getLoot(Virologist target);
	
	/**
	 * Gets the neighbors of this Field.
	 * @return The neighbors of this Field.
	 */
	public ArrayList<Field> getNeighbors() {
		return this.neighbors;
	}
	
	/**
	 * Gets the characters standing on this Field.
	 * @return The characters standing on this Field.
	 */
	public ArrayList<Virologist> getPlayers() {
		return this.players;
	}
	
	/**
	 * Removes the specified character from this.players.
	 * @param virologist The character to remove.
	 */
	public void removeVirologist(Virologist virologist) {
		this.players.remove(virologist);
	}


	@Override
	public String toYAML() {
		StringBuilder builder = new StringBuilder();
		/*
		kind: PlainField
		id: <uid>
		neighbors: [<uid of neighbor fields>]
		virologists: [<uid of virologists>]
		* */

		ArrayList<Integer> neighborIds = new OutputArray<>();
		ArrayList<Integer> playerIds = new OutputArray<>();

		//this.neighbors.forEach(field -> neighborIds.add(field.getUid()));
		for (Field field : this.neighbors) {
			neighborIds.add(field.getUid());
		}
		//this.players.forEach(virologist -> playerIds.add(virologist.getUid()));
		for (Virologist virologist : this.players) {
			playerIds.add(virologist.getUid());
		}
		neighborIds.sort(null);
		playerIds.sort(null);

		builder.append("kind: ").append(this.getClass().getSimpleName()).append("\n");
		builder.append("id: ").append(this.getUid()).append("\n");
		builder.append("neighbors: ").append(this.neighbors.size() > 0 ? neighborIds.toString() : "").append("\n");
		builder.append("virologists:").append(this.players.size() > 0 ? (' ' + playerIds.toString()) : "").append("\n");
		return builder.toString();
	}

}
