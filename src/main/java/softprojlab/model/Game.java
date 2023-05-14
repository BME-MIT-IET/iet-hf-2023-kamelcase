package softprojlab.model;

// Java imports
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

// Project imports
import softprojlab.main.LogHandler;
import softprojlab.model.character.Virologist;
import softprojlab.model.field.*;
import softprojlab.model.item.agent.*;
import softprojlab.model.item.equipment.*;

/**
 * Singleton class managing the application.
 * @author pfemeter.marton
 *
 */
public class Game {

	// Static attributes
	
	/**
	 * Name for logging.
	 */
	private final static String logName = "Game";

    // public static Attributes
	/**
	 * If true then the game events are randomly happening.
	 */
    public static boolean randomness = false;

	/**
	 * The seed for generating random objects.
	 */
	public int seed = 0;
	
	/**
	 * Callback for presenting the user with (multiple choice) questions.
	 * Gets the question and the response options in its parameter (only needs to be presented, it is already formatted).
	 * Should return the number typed by the user as a response.
	 *
	 */
	public static Function<String, Integer> questionCallback;
	
	/**
	 * Called by Game.endGame when someone won the game.
	 * Indicates the uid of the winner in its parameter.
	 *
	 */
	public static Consumer<Integer> endGameCallback;


	// private static Attributes
	
	/**
	 * The number of different Agents available.
	 */
	private int maxAgentNumber;
	
	/**
	 * The Field objects that are in the current Game.
	 */
	private ArrayList<Field> playingMap;

	/**
	 * The list of every uniquely identifiable object in the game.
	 */
	private ArrayList<IdentifiableObject> objects;
	
	/**
	 * A list of every player in the current game session. Initialized after a call to Game.startGame.
	 */
	private ArrayList<Virologist> playerList;
	
	/**
	 * Index of the currently active player in Game.playerList.
	 */
	private int activePlayer;
	
	/**
	 * The number of the turn the game is currently on. Used to determine number of actiontokens at the beginning of each round.
	 */
	private int roundCount;

	/**
	 * The uid counter, for IdentifiableObject instances.
	 * Shows the next uid that will be assigned to an object.
	 */
	private int uidCounter = 100;
	
	/**
	 * Random number generator
	 */
	private Random generator;

	// Constructors
	
	/**
	 * Default constructor.
	 */
	public Game() {
		LogHandler.logFunctionCall(Game.logName, "Constructor");
		maxAgentNumber = 5;
		playingMap = new ArrayList<Field>();
		generator = new Random(seed);
		playerList = new ArrayList<Virologist>();
		objects = new ArrayList<IdentifiableObject>();
		
		// System.out.println("Game constructor, starting uid: " + this.uidCounter);

		LogHandler.decrementIndentation();
	}
	
	// private static Methods
	
	/**
	 * Generates a new Agent for map generation.
	 * @param index The index of the name of the Agent in alphabetical order, starting from 0.
	 * @return The Agent generated, or null if the index was not valid.
	 */
	private Agent generateAgent(int index) {
		switch (index) {
		case 0: {
			return new BearAgent();
		}
		case 1: {
			return new DanceAgent();
		}
		case 2: {
			return new DementiaAgent();
		}
		case 3: {
			return new ParalyzerAgent();
		}
		case 4: {
			return new ProtectiveAgent();
		}
		default:
			return null;
		}
	}
	
	/**
	 * Generates a new Field for map generation, with random loot.
	 * @param index The index of the name of the Field in alphabetical order, starting from 0.
	 * @return The Field generated, or null if the index was not valid.
	 */
	private Field generateField(int index) {
		// Random number for agent and equipment generation
		int randomAgentOrEquipmentNumber = generator.nextInt() % 4;
		
		switch (index) {
		case 0: {
			return new Bunker(this.generateEquipment(randomAgentOrEquipmentNumber));
		}
		case 1: {
			return new InfectedLaboratory(new BearAgent());
		}
		case 2: {
			return new Laboratory(this.generateAgent( (randomAgentOrEquipmentNumber + 1) ));
		}
		case 3: {
			return new PlainField();
		}
		case 4: {
			// Random number of Material, 0-5 range for each
			int randomAmino = generator.nextInt() % 6;
			int randomNucleotide = generator.nextInt() % 6;
			return new Storage(randomAmino, randomNucleotide);
		}
		default:
			return null;
		}
	}
	
	/**
	 * Generates a new Equipment for map generation.
	 * @param index The index of the name of the Equipment in alphabetical order, starting from 0.
	 * @return The Equipment generated, or null if the index was not valid.
	 */
	private Equipment generateEquipment(int index) {		
		switch (index) {
		case 0: {
			return new Axe();
		}
		case 1: {
			return new Bag();
		}
		case 2: {
			return new ProtectiveCape();
		}
		case 3: {
			return new ThrowbackGloves();
		}
		default:
			return null;
		}
	}
	
	// public static Methods
	
	/**
	 * Presents the user with a multiple choice question using questionCallback. 
	 * @param message The question to ask.
	 * @param options The response options.
	 * @param ignoreOptionsSize Do not compare the upper bound of the result with the options size
	 * @return The index of the option the user selected. (0 is false, 1 is true if only 2 options were given)
	 * @throws IndexOutOfBoundsException Throws an exception if the user responded with an index greater than or equal to options.size(), or if it is less than zero.
	 */
	public static int askForUserInput(String message, ArrayList<String> options, boolean ignoreOptionsSize) throws IndexOutOfBoundsException {
		String question = message + '\n';
		question += "Type the number before the options below to respond:\n";
		
		int index = 0;
		for (String option: options) {
			question += index + ") " + option + '\n';
			++index;
		}
		
		int result = questionCallback.apply(question);
		if (result < 0 || (result >= options.size() && !ignoreOptionsSize)) {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}
	
	/**
	 * Convenience method for using askForUserInput with question that can only be answered with yes or no.
	 * @param message The yes/no question to ask.
	 * @return False if the response was 0, 1 if it was true.
	 * @throws IndexOutOfBoundsException Throws an exception if the user responded with an index greater than or equal to options.size(), or if it is less than zero.
	 */
	public static boolean askYesNo(String message) throws IndexOutOfBoundsException {
		ArrayList<String> optionList = new ArrayList<String>();
		optionList.add("No (false)");
		optionList.add("Yes (true)");
		
		int result = askForUserInput(message, optionList, false);
		
		return (result != 0);
	}

	/**
	 * Add an object to the unique objects list.
	 * @param obj Object to be added. Must implement IdentifiableObject interface.
	 * @return The object's uid.
	 */
	public int addNewUniqueObject(IdentifiableObject obj) {
		LogHandler.logFunctionCall(Game.logName, "addNewUniqueObject");
		obj.setUid((this.uidCounter)++);
		objects.add(obj);
		// System.out.println("creating uid: " + obj.getUid() + " which is a: " + obj);
		LogHandler.decrementIndentation();
		return obj.getUid();
	}
	
	/**
	 * Adds the Field in its parameter to playingMap.
	 * @param query The Field to add to playingMap.
	 */
	public void addField(Field query) {
		this.playingMap.add(query);
	}

	/**
	 * Get the object with the given uid.
	 * If no object with the given uid exists, null is returned.
	 * @param uid The object's uid.
	 * @return The object with the given uid, or null if it does not exists.
	 */
	public IdentifiableObject findUniqueObject(int uid) {
		LogHandler.logFunctionCall(Game.logName, "findUniqueObject");
		for (IdentifiableObject obj : objects) {
			if (obj.getUid() == uid) {
				LogHandler.decrementIndentation();
				return obj;
			}
		}
		LogHandler.decrementIndentation();
		return null;
	}

	/**
	 * Gets all the IdentifiableObjects in the game.
	 * @return The list of all IdentifiableObjects.
	 */
	public ArrayList<IdentifiableObject> getAllObjects() {
		return new ArrayList<IdentifiableObject>(objects);
	}

	/**
	 * Starts a new round, giving each player new actiontokens. Starts a new round by calling Game.nextVirologist.
	 */
	public void update() {
		LogHandler.logFunctionCall(Game.logName, "update");
		
		// Incrementing round counter
		++roundCount;
		
		// Setting actiontokens to 1 at the beginning, then +1 per round, maxed out at 5
		for (Virologist player: playerList) {
			if (player.getLocation() == null)
				continue;
			player.actionTokens = (roundCount > 5 ? 5 : roundCount);
		}
		
		// Setting to -1 so that when we "pass it over to the next player", the starting index will be 0
		activePlayer = -1;
		// Starting a new round
		this.nextVirologist();
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Function starting a game.
	 * The game can only be started if the following conditions are met:
	 * - playingMap must be contiguous.
	 * - There must be at least one character on playingMap.
	 * - There must be at least one Agent that can be learned on playingMap.
	 * @throws Exception Throws an exception if the above criteria are not met. The string inside the Exception describes the problem.
	 */
	public void startGame() throws Exception {
		LogHandler.logFunctionCall(Game.logName, "startGame");
		
		if (playingMap.isEmpty()) {
			throw new Exception("The map is empty.");
		} else if (maxAgentNumber <= 0) {
			throw new Exception("No agents to learn.");
		}
		
		// making a copy of playingMap for BFS
		ArrayList<Field> copyList = new ArrayList<Field>();
		for (Field field : playingMap) {
			copyList.add(field);
		}
		
		Field start = copyList.get(0);
		ArrayList<Field> activeNodes = new ArrayList<Field>();
		activeNodes.add(start);
		
		if (copyList.size() != 1) {
			while (!activeNodes.isEmpty()) {
				Field localStart = activeNodes.get(0);
				
				// Adding players on this tile to playerList
				ArrayList<Virologist> localPlayers = localStart.getPlayers();
				for (Virologist playerIterator: localPlayers) {
					boolean alreadyCounted = false;
					for (Virologist oldIterator: this.playerList) {
						if (oldIterator.getUid() == playerIterator.getUid()) {
							alreadyCounted = true;
							break;
						}
					}
					if (!alreadyCounted) {
						playerList.add(playerIterator);
					}
				}
				
				for (Field fieldIterator : localStart.getNeighbors()) {
					int iteratorIndex = copyList.indexOf(fieldIterator);
					if (iteratorIndex != -1) {
						
						// check whether this neighboring field was ever in playingMap
						int globalIndex = playingMap.indexOf(fieldIterator);
						if (globalIndex == -1) {
							throw new Exception("Some fields in the map were not correctly generated.");
						}
						activeNodes.add(fieldIterator);
						copyList.remove(iteratorIndex);
					}
				}
				activeNodes.remove(0);
			}
		} else {
			 for (Virologist iterator: copyList.get(0).getPlayers()) {
				 playerList.add(iterator);
			 }
			 copyList.clear();
		}
		
		if (!copyList.isEmpty()) {
			throw new Exception("The map was not contiguous.");
		} else if (playerList.isEmpty()) {
			throw new Exception("No characters found on map.");
		}
		
		// Sorting players by uid
		this.playerList.sort((Virologist lhs, Virologist rhs) -> {
			if (lhs.getUid() < rhs.getUid()) {
				return -1;
			} else if (lhs.getUid() == rhs.getUid()) {
				return 0;
			} else {
				return 1;
			}
		});
		
		// Starting game if everything went correctly
		roundCount = 0;
		
		LogHandler.decrementIndentation();
	}
	
	/**
	 * Function ending a game.
	 * @param winnerId The UID of the winning player.
	 */
	public void endGame(int winnerId) {
		LogHandler.logFunctionCall(Game.logName, "endGame");
		
		endGameCallback.accept(winnerId);
		
		LogHandler.decrementIndentation();
	}

	/**
	 * Returns the current Virologist who can do something.
	 * @return The current Virologist.
	 */
	public Virologist getCurrentVirologist() {
		// System.out.println("current player index: " + this.activePlayer + " - uid: " + this.playerList.get(this.activePlayer).getUid());
		if (activePlayer >= 0 && activePlayer < playerList.size()) {
			return playerList.get(activePlayer);
		}
		return null;
	}

	/**
	 * Pass the turn to the next Virologist.
	 */
	public void nextVirologist() {
		if (playerList.size() == 0) {
			return;
		}
		
		++activePlayer;

		// Deprecated
		// Start new round after last player
		if (activePlayer >= playerList.size()) {
			// update();
		} else {
			// Update Agents on current player
			Virologist activeVirologist = playerList.get(activePlayer);
			// This Virologist has gone to heaven, thus no location is provided
			// Let's not disturb him
			if (activeVirologist.getLocation() == null) {
				nextVirologist();
				return;
			}
			int knownAgents = playerList.get(activePlayer).update();
			
			// End game if current player learned every Agent
			if (maxAgentNumber <= knownAgents) {
				endGame(playerList.get(activePlayer).getUid());
			}
		}
	}
	
	/**
     * Generates the required objects to make the game playable, then starts the game.
     * @param numberOfPlayers The number of players to add to the game.
     */
    public void generate(int numberOfPlayers) {
        this.generate(numberOfPlayers, true);
    }
	
	/**
	 * Generates the required objects to make the game playable.
	 * @param numberOfPlayers The number of players to add to the game.
	 * @param startAfterGeneration Whether to call this.startGame after generation.
	 */
	public void generate(int numberOfPlayers, boolean startAfterGeneration) {
		randomness = true;

		// Number of fields to generate (between 20 and 100)
		int numberOfFields = (generator.nextInt() % 80) + 20;
		
		int numberOfAgents = 5;
		int numberOfEquipment = 4;
		
		// Making sure every type of Agent is available
		
		// BearAgent needs different kind of field
		playingMap.add(new InfectedLaboratory(generateAgent(0)));
		for (int i = 1; i < numberOfAgents; ++i) {
			playingMap.add(new Laboratory(generateAgent(i)));
		}
		
		// Making sure every type of Equipment is available
		for (int i = 0; i < numberOfEquipment; ++i) {
			playingMap.add(new Bunker(generateEquipment(i)));
		}
		
		// Making sure at least one Storage is available
		playingMap.add(new Storage(3, 2));
		
		// Placing players
		for (int i = 0; i < numberOfPlayers; ++i) {
			PlainField startLocation = new PlainField();
			Virologist player = new Virologist(startLocation);
			playingMap.add(startLocation);
		}
		
		// Generating rest of the Fields
		while (playingMap.size() < numberOfFields) {
			int randomFieldIndex = generator.nextInt() % 5;
			playingMap.add(generateField(randomFieldIndex));
		}
		
		for (Field fieldIterator : playingMap) {
			
			// Generating 4 random neighbors for each Field			
			for (int i = 0; i < 4; ++i) {
				int randomIndex = generator.nextInt() % playingMap.size();
				Field candidate = playingMap.get(randomIndex);
				
				// A random generation counts towards the 4 neighbors, even if they were already neighbors
				boolean alreadyNeighbors = false;
				
				// Checking if they were already neighbors
				for (Field existingNeighbor : fieldIterator.getNeighbors()) {
					if (candidate == existingNeighbor) {
						alreadyNeighbors = true;
						break;
					}
				}
				
				// If not neighbors, set them as neighbors
				if (!alreadyNeighbors) {
					fieldIterator.addNeighbor(candidate);
					candidate.addNeighbor(fieldIterator);
				}
			}
		}
		
		if (startAfterGeneration) {
    		// Start the game after generation
    		try {
    			this.startGame();
    		} catch (Exception error) {
    			// Something is very wrong with the implementation of Game.generate() or Game.startGame if exception occurs here...
    			System.out.println("Error - Something went wrong generating the map. - " + error.getMessage());
    		}
		}
	}
}
