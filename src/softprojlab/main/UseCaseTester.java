package softprojlab.main;

// Java imports
import java.lang.reflect.Array;
import java.util.*;

// Project imports
import softprojlab.model.character.Virologist;
import softprojlab.model.field.*;
import softprojlab.model.Game;
import softprojlab.model.item.agent.*;
import softprojlab.model.item.equipment.*;

public class UseCaseTester {
	// Private Attributes
	
//	private static final String[] menuOptions = {
//			"DecayAgents",
//			"LearnGeneticCode",
//			"LootBunker",
//			"LootStorage",
//			"Move",
//			"SpreadDanceAgent",
//			"SpreadDementiaAgent",
//			"SpreadParalyzeAgent",
//			"SpreadProtectiveAgent",
//			"Steal",
//			"SynthetiseDanceAgent",
//			"SynthetiseDementiaAgent",
//			"SynthetiseParalyzeAgent",
//			"SynthetiseProtectiveAgent",
//			"UpdateVirologist"
//	};
//			
//
//	// Public Attributes
//	
//	
//	
//	// Private Methods
//	
//	/**
//	 * Presents the tester with a binary choice.
//	 * @param message The question to answer.
//	 * @return The answer to the question.
//	 */
//	private static Boolean binaryChoice(String message) {
//		System.out.println(message);
//		System.out.println("Enter 0 to disagree, 1 to agree:");
//		
//		int result = UseCaseTester.parseInteger();
//		
//		Boolean binaryResult = (result != 0 && result != -1);
//		if (binaryResult) {
//			System.out.println("You agreed");
//		} else {
//			System.out.println("You disagreed");
//		}
//		
//		return binaryResult;
//	}
//
//	/**
//	 * Presents the tester with a multiple choice
//	 *
//	 * @param message The question to answer
//	 * @param options The possible answers
//	 * @return The int of the choice (from 0)
//	 */
//	private static int multipleChoice(String message, ArrayList<String> options) {
//		System.out.println(message);
//		System.out.println("Enter the number of your choice: ");
//		int res = UseCaseTester.parseInteger();
//
//		for (int i = 0; i < options.size(); i++) {
//			System.out.println(i + ": " + options.get(i));
//		}
//
//		try	{
//			System.out.println("Your choice: " + options.get(res));
//		} catch (IndexOutOfBoundsException e) {
//			System.out.println("Choice does not exists!");
//			return -1;
//		}
//
//		return res;
//	}
//
//	/**
//	 * For synthetiseAgent use-case testing.
//	 * @param query The Agent to test.
//	 */
//	private static void craftAgent(Agent query) {
//		// Setup
//		
//		Agent knowledge = query;
//		Virologist testDummy = new Virologist(null, null);
//		testDummy.learnAgent(knowledge);
//		
//		// Action to test
//		
//		String firstChoice = "Should source Virologist have enough actionTokens?";
//		if ( UseCaseTester.binaryChoice(firstChoice) ) {
//			
//			String secondChoice = "Should source Virologist have enough material?";
//			if ( UseCaseTester.binaryChoice(secondChoice) ) {				
//				testDummy.synthetiseAgent(knowledge);
//				return;
//			}
//		}
//	}
//	
//	private static void lootField(Field location) {
//		// Setup
//		
//		Virologist testDummy = new Virologist(location, null);
//		
//		// Action to test
//		
//		testDummy.tryLooting();
//	}
//	
//	/**
//	 * Parses an integer form the standard input.
//	 * @return The parsed integer, or -1 if the operation failed.
//	 */
//	private static int parseInteger() {
//		Scanner input = new Scanner(System.in);
//		
//		int result = -1;
//		try {
//			result = Integer.parseInt(input.nextLine());
//		} catch (Exception exception) {
//			System.err.println(exception);
//			System.err.println("Error - Invalid input, could not parse integer.");
//		}
//		
//		return result;
//	}
//	
//	/**
//	 * Prints the possible option to the standard output.
//	 */
//	private static void printMenu() {
//		String startMessage = "\nSelect one of the following by typing the number next to it:\n";
//		String firstOption = "0 - Exit\n";
//		System.out.print(startMessage + firstOption);
//		
//		int counter = 1;
//		for (String option: UseCaseTester.menuOptions) {
//			String output = counter + " - " + option;
//			System.out.println(output);
//			++counter;
//		}
//	}
//	
//	/**
//	 * For spreadAgent use-case testing.
//	 * @param query The Agent to test.
//	 */
//	private static void spreadAgent(Agent query) {
//		// Setup
//		
//		Virologist testDummy = new Virologist(null, null);
//		Virologist victim = new Virologist(null, null);
//		
//		// Action to test
//		
//		String firstChoice = "Should the Virologist have enough actionTokens?";
//		if ( UseCaseTester.binaryChoice(firstChoice) ) {
//			String secondChoice = "Should the other Virologist successfully defend against the attack?";
//			if ( !UseCaseTester.binaryChoice(secondChoice) ) {
//				testDummy.applyAgentTo(query, victim);
//				query.decay();
//				return;
//			}
//
//			String equipmentChoice = "Choose an equipment to wear";
//			ArrayList<String> options = new ArrayList<>();
//			options.add("None");
//			options.add("Gloves");
//			options.add("Cape");
//			options.add("Gloves and Cape");
//
//			int answer = UseCaseTester.multipleChoice(equipmentChoice, options);
//
//			switch (answer) {
//				case 0:
//					testDummy.applyAgentTo(query, victim);
//					query.decay();
//					break;
//				case 1:
//					victim.applyAgentTo(query, testDummy);
//					query.decay();
//					break;
//				case 2:
//					if (new Random().nextInt(100) <= 82) {
//						testDummy.applyAgentTo(query, victim);
//						query.decay();
//					}
//					break;
//				case 3:
//					if (new Random().nextInt(100) <= 82) {
//						victim.applyAgentTo(query, testDummy);
//						query.decay();
//					}
//					break;
//			}
//
//		}
//		
//		testDummy.applyAgentTo(null, victim);
//	}
//	
//	// Use-cases
//	
//	/**
//	 * Use-case:
//	 */
//	private static void decayAgents() {
//		// Setup
//		
//		DanceAgent testItem = new DanceAgent();
//		ParalyzerAgent testItemOther = new ParalyzerAgent();
//		Virologist testDummy = new Virologist(null, null);
//		testDummy.applyAgentTo(testItem, testDummy);
//		testDummy.applyAgentTo(testItemOther, testDummy);
//		
//		// Action to test
//		
//		testDummy.update();
//	}
//	
//	/**
//	 * Use-case: Test leaning Agent from Laboratory.
//	 */
//	private static void learnGeneticCode() {
//		String question = "Which agent do you want to learn ?";
//		ArrayList<String> options = new ArrayList<>();
//		options.add("DanceAgent");
//		options.add("DementiaAgent");
//		options.add("ParalyzeAgent");
//		options.add("ProtectiveAgent");
//
//		int choice = UseCaseTester.multipleChoice(question, options);
//
//		Laboratory laboratory = null;
//		switch (choice) {
//			case 0: {
//				new Laboratory(new DanceAgent());
//				break;
//			}
//			case 1: {
//				new Laboratory(new DementiaAgent());
//				break;
//			}
//			case 2: {
//				new Laboratory(new ParalyzerAgent());
//				break;
//			}
//			case 3: {
//				new Laboratory(new ProtectiveAgent());
//				break;
//			}
//			default: {
//				new Laboratory(new DanceAgent());
//				break;
//			}
//		};
//
//		UseCaseTester.lootField(laboratory);
//	}
//	
//	/**
//	 * Use-case: Test looting Bunker.
//	 */
//	private static void LootBunker() {
//		Bunker location = new Bunker(new ProtectiveCape());
//		UseCaseTester.lootField(location);
//	}
//	
//	/**
//	 * Use-case: Test looting Storage.
//	 */
//	private static void lootStorage() {
//		Storage location = new Storage(20, 20);
//		UseCaseTester.lootField(location);
//	}
//	
//	/**
//	 * Use-case: Moving Virologist.
//	 */
//	private static void move() {
//		// Setup
//		
//		Field origin = new PlainField();
//		Field destination = new PlainField();
//		origin.addNeighbor(destination);
//		Virologist testDummy = new Virologist(origin, null);
//		
//		// Action to test
//		
//		testDummy.move(0);
//	}
//	
//	/**
//	 * Use-case: Spreading DanceAgent.
//	 */
//	private static void spreadDanceAgent() {
//		Agent test = new DanceAgent();
//		UseCaseTester.spreadAgent(test);
//	}
//	
//	/**
//	 * Use-case: Spreading DementiaAgent.
//	 */
//	private static void spreadDementiaAgent() {
//		Agent test = new DementiaAgent();
//		UseCaseTester.spreadAgent(test);
//	}
//	
//	/**
//	 * Use-case: Spreading ParalyzerAgent.
//	 */
//	private static void spreadParalyzerAgent() {
//		Agent test = new ParalyzerAgent();
//		UseCaseTester.spreadAgent(test);
//	}
//	
//	/**
//	 * Use-case: Spreading ProtectiveAgent.
//	 */
//	private static void spreadProtectiveAgent() {
//		Agent test = new ProtectiveAgent();
//		UseCaseTester.spreadAgent(test);
//	}
//	
//	/**
//	 * Use-case:
//	 */
//	private static void steal() {
//		// Setup
//		PlainField f = new PlainField();
//
//		Virologist testDummy = new Virologist(null, null);
//		Virologist victim = new Virologist(null, null);
//
//		f.acceptVirologist(testDummy);
//		// Action to test
//		String fieldChoice = "Are the 2 virologists on the same field ?";
//		if( UseCaseTester.binaryChoice(fieldChoice)) {
//			f.acceptVirologist(victim);
//			String firstChoice = "Should the victim be paralyzed?";
//			if ( UseCaseTester.binaryChoice(firstChoice) ) {
//				String secondChoice = "Should the victim have any equipment?";
//				if ( UseCaseTester.binaryChoice(secondChoice) ) {
//					ProtectiveCape testItem = new ProtectiveCape();
//					victim.addEquipment(testItem);
//				}
//				testDummy.stealFrom(victim);
//			}
//		}
//	}
//	
//	/**
//	 * Use-case: Crafting DanceAgent.
//	 */
//	private static void synthetiseDanceAgent() {
//		Agent test = new DanceAgent();
//		UseCaseTester.craftAgent(test);
//	}
//	
//	/**
//	 * Use-case: Crafting DementiaAgent.
//	 */
//	private static void synthetiseDementiaAgent() {
//		Agent test = new DementiaAgent();
//		UseCaseTester.craftAgent(test);
//	}
//	
//	/**
//	 * Use-case: Crafting ParalyzerAgent.
//	 */
//	private static void synthetiseParalyzerAgent() {
//		Agent test = new ParalyzerAgent();
//		UseCaseTester.craftAgent(test);
//	}
//	
//	/**
//	 * Use-case: Crafting ProtectiveAgent.
//	 */
//	private static void synthetiseProtectiveAgent() {
//		Agent test = new ProtectiveAgent();
//		UseCaseTester.craftAgent(test);
//	}
//	
//	/**
//	 * Use-case:
//	 */
//	private static void updateVirologist() {
//		// Setup
//		
//		Game testUnit = new Game();
//		
//		// Action to test
//		
//		testUnit.update();
//	}
//	
//	// Public Methods
//	
//	public static void runTesting() {
//		int commandCode = -1;
//		while (commandCode != 0) {
//			UseCaseTester.printMenu();
//			commandCode = UseCaseTester.parseInteger();
//			
//			System.out.println("Selected " + commandCode);
//			
//			switch (commandCode) {
//			case 0: {
//				// Exit
//				break;
//			}
//			case 1: {
//				UseCaseTester.decayAgents();
//				break;
//			}
//			case 2: {
//				UseCaseTester.learnGeneticCode();
//				break;
//			}
//			case 3: {
//				UseCaseTester.LootBunker();
//				break;
//			}
//			case 4: {
//				UseCaseTester.lootStorage();
//				break;
//			}
//			case 5: {
//				UseCaseTester.move();
//				break;
//			}
//			case 6: {
//				UseCaseTester.spreadDanceAgent();
//				break;
//			}
//			case 7: {
//				UseCaseTester.spreadDementiaAgent();
//				break;
//			}
//			case 8: {
//				UseCaseTester.spreadParalyzerAgent();
//				break;
//			}
//			case 9: {
//				UseCaseTester.spreadProtectiveAgent();
//				break;
//			}
//			case 10: {
//				UseCaseTester.steal();
//				break;
//			}
//			case 11: {
//				UseCaseTester.synthetiseDanceAgent();
//				break;
//			}
//			case 12: {
//				UseCaseTester.synthetiseDementiaAgent();
//				break;
//			}
//			case 13: {
//				UseCaseTester.synthetiseParalyzerAgent();
//				break;
//			}
//			case 14: {
//				UseCaseTester.synthetiseProtectiveAgent();
//				break;
//			}
//			case 15: {
//				UseCaseTester.updateVirologist();
//				break;
//			}
//			default:
//				System.err.println("Error - Unknown command");
//				break;
//			}
//		}
//		
//		System.out.println("Exiting...");
//	}
}
