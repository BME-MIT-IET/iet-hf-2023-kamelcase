package softprojlab.model.character;

/**
 * Interface for the Game class to access characters.
 * @author pfemeter.marton
 *
 */
public interface UpdateableCharacter {
	/**
	 * Function that tells the character that it is their turn.
	 * @return The number of Agents known by the character. Used to determine the winner of the game.
	 */
	public int update();
}
