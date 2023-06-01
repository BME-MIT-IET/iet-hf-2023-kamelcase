package softprojlab.cli;

// Java imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

// Project imports
import softprojlab.model.Game;
import softprojlab.model.IdentifiableObject;
import softprojlab.model.character.Virologist;
import softprojlab.model.field.*;
import softprojlab.model.item.agent.*;
import softprojlab.model.item.equipment.*;
import softprojlab.model.item.material.Aminoacid;
import softprojlab.model.item.material.Nucleotide;

import static java.lang.Integer.parseInt;

/**
 * Represents the command line interface of the application.
 * @author kristofhetenyi
 */
public class CLI {

    /**
     * TopLvl commands
     */
    private final HashMap<String, Function<String, Boolean>> topLvlCommands = new HashMap<>();

    /**
     * CreateLvl commands
     */
    private final HashMap<String, Function<String[], Boolean>> createLvlCommands = new HashMap<>();

     /**
     *SetLvl commands
     */
    private final HashMap<String, HashMap<String, Function<String[], Boolean>>> setLvlCommands = new HashMap<>();

    /**
     * GameLvl commands
     */
    private final HashMap<String, Function<String[], Boolean>> gameLvlCommands = new HashMap<>();

    /**
     * VirologistLvl commands
     */
    private final HashMap<String, Function<String[], Boolean>> virologistLvlCommands = new HashMap<>();

    /**
     * Equipments by strings.
     * All time initializes a new one.
     */
    private final HashMap<String, Supplier<Equipment>> equipmentsByStr = new HashMap<>(
            Map.of(
                    "bag", Bag::new,
                    "axe", Axe::new,
                    "glo", ThrowbackGloves::new,
                    "cap", ProtectiveCape::new
                    )
    );

    /**
     * Agents by strings.
     * All time initializes a new one.
     */
    private final HashMap<String, Supplier<Agent>> agentsByStr = new HashMap<>(
            Map.of(
                    "dan", DanceAgent::new,
                    "pro", ProtectiveAgent::new,
                    "par", ParalyzerAgent::new,
                    "bear", BearAgent::new,
                    "dem", DementiaAgent::new
                    )
    );

    /**
     * History of commands.
     * Log only valid commands.
     */
    private final ArrayList<String> history = new ArrayList<>();

    /**
     * The game that is being played.
     * Initialize null
     */
    private Game game = null;

    /**
     * Close the application if true with exit command.
     */
    public boolean doExit = true;
    
    /**
     * Scanner instance used to read input from the command line
     */
    private Scanner scanner;

    /**
     * Initialize a new command line output interface.
     */
    public CLI() {
       this.initNewGame();

        // Init commands
        this.initTopLvlCommands();
        this.initCreateLvlCommands();
        this.initSetLvlCommands();
        this.initGameLvlCommands();
        this.initVirologistLvlCommands();

    }

    // Public methods

    /**
     * Clears the STDIN and start w8ing for user input.
     * Commands can be found in the documentation.
     */
    public void startListeningForInput() {
        scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            //System.out.println("COMMAND: " + line);

            if (!handleTopLvlCommand(line)) {
                System.err.println("Command failed:\n" + line );
            } else {
                history.add(line);
                this.saveHistory("auto-save.txt");
            }
        }

    }


    /**
     * Prints a help message to the STDOUT.
     */
    public boolean printHelp(String input) {
        System.out.println("For available commands see documentation.");
        return true;
    }


    /**
     * Prints an object YAML format to the STDOUT.
     * Object must implement YAMLExportable interface.
     * @param obj Object to be printed.
     */
    public void printYAML(YAMLExportable obj) {
        System.out.println(obj.toYAML());
    }

    /**
     * Close the application.
     */
    public boolean exit(String input) {
        if (this.doExit)
            System.exit(0);
        return true;
    }

    // Private methods

    /**
     * Initializes the top lvl commands.
     */
    private void initTopLvlCommands() {
        this.topLvlCommands.put("create", this::handleCreateCommand);
        this.topLvlCommands.put("exit", this::exit);
        this.topLvlCommands.put("help", this::printHelp);
        this.topLvlCommands.put("set", this::handleSetCommand);
        this.topLvlCommands.put("game", this::handleGameCommand);
        this.topLvlCommands.put("generate", this::handleGenerateCommand);
        this.topLvlCommands.put("show", this::handleShowCommand);
        this.topLvlCommands.put("virologist", this::handleVirologistCommand);
        this.topLvlCommands.put("y", this::handleYNCommand);
        this.topLvlCommands.put("n", this::handleYNCommand);
        this.topLvlCommands.put("#", (String s) -> true);
        this.topLvlCommands.put("", (String s) -> true);
    }

    /**
     *  Initialize CreateLvl commands.
     */
    private void initCreateLvlCommands() {
        this.createLvlCommands.put("b", (String[] s) -> {
            Field f = new Bunker(new ProtectiveCape());
            this.game.addNewUniqueObject(f);
            this.game.addField(f);
            return true;
        } );
        this.createLvlCommands.put("i", (String[] s) -> {
            Field f = new InfectedLaboratory(new BearAgent());
            this.game.addNewUniqueObject(f);
            this.game.addField(f);
            return true;
        } );
        this.createLvlCommands.put("l", (String[] s) -> {
            Field f = new Laboratory(new DanceAgent());
            this.game.addNewUniqueObject(f);
            this.game.addField(f);
            return true;
        } );
        this.createLvlCommands.put("p", (String[] s) -> {
            Field f = new PlainField();
            this.game.addNewUniqueObject(f);
            this.game.addField(f);
            return true;
        } );
        this.createLvlCommands.put("s", (String[] s) -> {
            Field f = new Storage(0, 0);
            this.game.addNewUniqueObject(f);
            this.game.addField(f);
            return true;
        } );
        this.createLvlCommands.put("v", (String[] s) -> {
            Virologist v = new Virologist(new PlainField());
            this.game.addNewUniqueObject(v);
            return true;
        } );
    }

    /**
     *  Initialize SetLvl commands.
     */
    private void initSetLvlCommands() {
        this.setLvlCommands.put("b", new HashMap<>(
                Map.of(
                "player", this::setPlayerOnField,
                "neighbor", this::setFieldNeighbor,
                "equipment", (String[] s) -> {
                            Bunker b = (Bunker) this.game.findUniqueObject(parseInt(s[2]));
                            if (b != null) {
                                b.setEquipment(this.equipmentsByStr.get(s[4]).get());
                                return true;
                            }
                        return false;
                    }
        )));
        this.setLvlCommands.put("i", new HashMap<>(
                Map.of(
                        "player", this::setPlayerOnField,
                        "neighbor", this::setFieldNeighbor,
                        "agent", (String[] s) -> {
                            InfectedLaboratory i = (InfectedLaboratory) this.game.findUniqueObject(parseInt(s[2]));
                            if (i != null) {
                                i.setAgent(this.agentsByStr.get(s[4]).get());
                                return true;
                            }
                            return false;
                        }
                )));
        this.setLvlCommands.put("l", new HashMap<>(
                Map.of(
                        "player", this::setPlayerOnField,
                        "neighbor", this::setFieldNeighbor,
                        "agent", (String[] s) -> {
                            Laboratory l = (Laboratory) this.game.findUniqueObject(parseInt(s[2]));
                            if (l != null) {
                                l.setAgent(this.agentsByStr.get(s[4]).get());
                                return true;
                            }
                            return false;
                        }
                )));
        this.setLvlCommands.put("p",  new HashMap<>(
                Map.of(
                        "player", this::setPlayerOnField,
                        "neighbor", this::setFieldNeighbor
                )));
        this.setLvlCommands.put("s",  new HashMap<>(
                Map.of(
                        "player", this::setPlayerOnField,
                        "neighbor", this::setFieldNeighbor,
                        "nuc", (String[] s) -> {
                            Storage storage = (Storage) this.game.findUniqueObject(parseInt(s[2]));
                            if (storage != null) {
                            	int items = parseInt(s[4]);
                            	for (int i = 0; i < items; i++)
                            		storage.addMaterial(new Nucleotide());
                                return true;
                            }
                            return false;
                        },
                        "ami", (String[] s) -> {
                            Storage storage = (Storage) this.game.findUniqueObject(parseInt(s[2]));
                            if (storage != null) {
                            	int items = parseInt(s[4]);
                            	for (int i = 0; i < items; i++)
                            		storage.addMaterial(new Aminoacid());
                                return true;
                            }
                            return false;
                        },
                        "mat", (String[] s) -> {
                            Storage storage = (Storage) this.game.findUniqueObject(parseInt(s[2]));
                            if (storage != null) {
                            	int items = parseInt(s[4]);
                            	for (int i = 0; i < items; i++)
                            		storage.addMaterial(new Random().nextInt() % 2 == 1 ? new Aminoacid() : new Nucleotide());
                                return true;
                            }
                            return false;
                        }
                )));
        this.setLvlCommands.put("v",  new HashMap<>(
                Map.of(
                        "equipment", (String[] s) -> {
                            Virologist v = (Virologist) this.game.findUniqueObject(parseInt(s[2]));
                            if (v != null) {
                                v.addEquipment(this.equipmentsByStr.get(s[4]).get());
                                return true;
                            }
                            return false;
                        },
                        "applied", (String[] s) -> {
                            Virologist v = (Virologist) this.game.findUniqueObject(parseInt(s[2]));
                            if (v != null) {
                                v.addToAppliedAgents(this.agentsByStr.get(s[4]).get(), this.game.getCurrentVirologist());
                                return true;
                            }

                            return false;
                        },
                        "learned", (String[] s) -> {
                            Virologist v = (Virologist) this.game.findUniqueObject(parseInt(s[2]));
                            if (v != null) {
                                v.learnAgent(this.agentsByStr.get(s[4]).get());
                                return true;
                            }
                            return false;
                        },
                        "created", (String[] s) -> {
                            Virologist v = (Virologist) this.game.findUniqueObject(parseInt(s[2]));
                            if (v != null) {
                                v.addCreatedAgent(this.agentsByStr.get(s[4]).get());
                                return true;
                            }
                            return false;
                        },
                        "nuc", (String[] s) -> {
                            Virologist v = (Virologist) this.game.findUniqueObject(parseInt(s[2]));
                            if (v != null) {
                            	int items = parseInt(s[4]);
                            	for (int i = 0; i < items; i++)
                            		v.addNucleotide(new Nucleotide());
                                return true;
                            }
                            return false;
                        },
                        "ami", (String[] s) -> {
                            Virologist v = (Virologist) this.game.findUniqueObject(parseInt(s[2]));
                            if (v != null) {
                            	int items = parseInt(s[4]);
                            	for (int i = 0; i < items; i++)
                            		v.addAminoacid(new Aminoacid());
                                return true;
                            }
                            return false;
                        },
                        "action", (String[] s) -> {
                            Virologist v = (Virologist) this.game.findUniqueObject(parseInt(s[2]));
                            if (v != null) {
                                v.actionTokens = parseInt(s[4]);
                                return true;
                            }
                            return false;
                        }
                )));
    }


    /**
     * Initializes the GameLvl commands.
     */
    private void initGameLvlCommands() {
        this.gameLvlCommands.put("save", (String[] s) -> {
            return this.saveHistory(s[2]);
        });
        this.gameLvlCommands.put("load", (String[] s) -> {
            return this.loadHistory(s[2]);
        });
        this.gameLvlCommands.put("start", (String[] s) -> {
        	try {
        		this.game.startGame();
                return true;
        	} catch (Exception error) {
        		System.out.println("Error - " + error.getMessage());
                return false;
        	}
        });
        this.gameLvlCommands.put("new", (String[] s) -> {
            initNewGame();
            return true;
        });
        this.gameLvlCommands.put("update", (String[] s) -> {
            this.game.update();
            return true;
        });
        this.gameLvlCommands.put("random", (String[] s) -> {
            this.game.randomness = parseInt(s[2]) != 1;
            return true;
        });
    }

    /**
     * Initializes a new Game object.
     */
    private void initNewGame() {
        this.game = new Game();
        this.history.clear();
        this.game.questionCallback = this::askQuestion;
        this.game.endGameCallback = this::handleGameEnd;
    }


    /**
     * Initializes the virologistLvl commands.
     */
    private void initVirologistLvlCommands() {
        StringBuilder sb = new StringBuilder();
        this.virologistLvlCommands.put("move", (String[] s) -> {
            Virologist v = (Virologist) this.game.getCurrentVirologist();
            if (v == null) {
            	System.err.println("Command failed:\nNo virologist is selected as current");
            	return false;
            }
            Field f = (Field) this.game.findUniqueObject(parseInt(s[2]));
            return moveVirologist(v, f);
        });
        this.virologistLvlCommands.put("steal", (String[] s) -> {
           Virologist v = (Virologist) this.game.getCurrentVirologist();
           if (v == null) {
           	System.err.println("Command failed:\nNo virologist is selected as current");
           	return false;
           }
           Virologist target = (Virologist) this.game.findUniqueObject(parseInt(s[2]));

           if (v != null && target != null) {
               v.stealFrom(target);
               return true;
           }

            return false;
        });
        this.virologistLvlCommands.put("spread", (String[] s) -> {
           Virologist v = (Virologist) this.game.getCurrentVirologist();
           if (v == null) {
           	System.err.println("Command failed:\nNo virologist is selected as current");
           	return false;
           }
           Virologist target = (Virologist) this.game.findUniqueObject(parseInt(s[3]));

           if (v != null && target != null) {
               v.applyAgentTo(target);
               return true;
           }
           return false;
        });
        this.virologistLvlCommands.put("kill", (String[] s) -> {
            Virologist v = (Virologist) this.game.getCurrentVirologist();
            if (v == null) {
            	System.err.println("Command failed:\nNo virologist is selected as current");
            	return false;
            }
            Virologist target = (Virologist) this.game.findUniqueObject(parseInt(s[2]));

            if (v != null && target != null) {
                return v.kill(target);
            }
            return false;
        });
        this.virologistLvlCommands.put("skip", (String[] s) -> {
            this.game.nextVirologist();
            return true;
        });
        this.virologistLvlCommands.put("create", (String[] s) -> {
            Virologist v = (Virologist) this.game.getCurrentVirologist();
            if (v == null) {
            	System.err.println("Command failed:\nNo virologist is selected as current");
            	return false;
            }
            if (v != null) {
                return v.synthetiseAgent(this.agentsByStr.get(s[2]).get());
            }
            return false;
        });
        this.virologistLvlCommands.put("loot", (String[] s) -> {
            Virologist v = (Virologist) this.game.getCurrentVirologist();
            if (v == null) {
            	System.err.println("Command failed:\nNo virologist is selected as current");
            	return false;
            }
            if (v != null) {
                return v.tryLooting();
            }
            return false;
        });
    }

    /**
     * Moves the virologist to the given field.
     * @param v The virologist to move.
     * @param f The field to move to.
     * @return True if the virologist was moved, false otherwise.
     */
    private Boolean moveVirologist(Virologist v, Field f) {
    	// System.out.println("moving virologist " + v.getUid() + " - to field: " + f.getUid());
        if (v != null && f != null) {
            int dir = -1;
            Field virologistField = v.getLocation();

            for (int i = 0; i < virologistField.getNeighbors().size(); i++) {
                if (virologistField.getNeighbors().get(i).getUid() == f.getUid() )
                    dir = i;
            }

            if (dir >= 0)
                return v.move(dir);
        }
        return false;
    }


    /**
     * Handles the user input look for top lvl commands.
     * Possible commands are: help, exit, create, set, generate, game,
     * show, virologist, y/n.
     * @param input User input.
     * @return True if the input is a valid command.
     */
    private boolean handleTopLvlCommand(String input) {
    	try {
    		return this.topLvlCommands.getOrDefault(splitInput(input)[0], (String s) -> false).apply(input);
    	} catch (ClassCastException classCastException) {
    		System.err.println("Invalid UID in command:\n" + input);
    		return false;
    	} catch (NumberFormatException numberFormatException) {
    		System.err.println("Invalid number in command:\n" + input);
    		return false;
    	}
    }

    /**
     * Handles the create top lvl command.
     * For possible commands see documentation.
     * @param input The user input with the top lvl command.
     * @return True if the input is a valid command.
     */
    private boolean handleCreateCommand(String input) {
        return this.createLvlCommands.getOrDefault(splitInput(input)[1], (String[] s) -> false).apply(splitInput(input));
    }

    /**
     * Handles the set top lvl command.
     * For possible commands see documentation.
     * @param input The user input with the top lvl command.
     * @return True if the input is a valid command.
     */
    private boolean handleSetCommand(String input) {
        return this.setLvlCommands.getOrDefault(splitInput(input)[1], new HashMap<>()).getOrDefault(splitInput(input)[3], (String[] s) -> false).apply(splitInput(input));
    }


    /**
     * Handles the generate top lvl command.
     * @param input The user input with the top lvl command.
     * @return True if the input is a valid command.
     */
    private boolean handleGenerateCommand(String input) {
        if (this.splitInput(input).length == 3) {
            this.game.seed = parseInt(this.splitInput(input)[2]);
        } else {
            this.game.seed = new Random().nextInt();
        }

        this.game.generate(parseInt(this.splitInput(input)[1]));
        return true;
    }


    /**
     * Handles the game top lvl command.
     * @param input The user input with the top lvl command.
     * @return True if the input is a valid command.
     */
    private boolean handleGameCommand(String input) {
        return this.gameLvlCommands.getOrDefault(splitInput(input)[1], (String[] s) -> false).apply(splitInput(input));
    }

    // see handleShowCommand below
    private boolean handleShowCommand(String input) {
    	String output = this.handleShowCommandLocal(input);
    	
    	System.out.println(output);
    	
    	return (output != null);
    }

    /**
     * Handles the show top lvl command.
     * @param input The user input with the top lvl command.
     * @return True if the input is a valid command.
     */
    public String handleShowCommandLocal(String input) {
        String[] splitInput = splitInput(input);
        StringBuilder op = new StringBuilder();
        if (splitInput.length > 1 && splitInput[1].equals("game")) {
            for (int i = 0; i < this.game.getAllObjects().size(); i++) {
                op.append(this.game.getAllObjects().get(i).toYAML()).append(i < this.game.getAllObjects().size() -1 ? "---\n" : "");
            }

            if (splitInput.length >= 3) {
                if (saveShowLog(splitInput, op)) return null;
            }
        } else {
            if (splitInput[1].equals("field") || splitInput[1].equals("virologist")) {
                IdentifiableObject o = this.game.findUniqueObject(parseInt(splitInput[2]));
                if (o != null) {
                    op.append(o.toYAML());
                }

                if (splitInput.length > 3) {
                    if (saveShowLog(splitInput, op)) return null;
                }
            }
        }
        return op.toString();
    }

    private boolean saveShowLog(String[] splitInput, StringBuilder op) {
        try {
            FileWriter myWriter = new FileWriter(splitInput.length == 3 ? splitInput[2] : splitInput[3]);
            myWriter.write(op.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * Handles the virologist top lvl command.
     * @param input The user input with the top lvl command.
     * @return True if the input is a valid command.
     */
    private boolean handleVirologistCommand(String input) {
        return this.virologistLvlCommands.getOrDefault(splitInput(input)[1], (String[] s) -> false).apply(splitInput(input));
    }

    /**
     * Handles the y/n top lvl command.
     * @param input The user input with the top lvl command.
     * @return True if the input is y.
     */
    private boolean handleYNCommand(String input) {
        return false;
    }

    /**
     * Splits the input by spaces.
     * @param input String to be split.
     * @return The split input.
     */
    private String[] splitInput(String input) {
        return input.split(" ");
    }


    // Export functions (for readability)

    /**
     * Sets a virologist on field.
     * @param input The input string.
     * @return True if successful.
     */
    private boolean setPlayerOnField(String[] input) {
        Field f = (Field) this.game.findUniqueObject(parseInt(input[2]));
        Virologist v = (Virologist) this.game.findUniqueObject(parseInt(input[4]));
        if (f != null && v != null) {
            v.setLocation(f);
            return true;
        }

        return false;
    }

    /**
     * Sets a fields neighbor.
     * @param input The input string.
     * @return True if successful.
     */
    private boolean setFieldNeighbor(String[] input) {
        Field field = (Field) this.game.findUniqueObject(parseInt(input[2]));
        Field fieldTarget = (Field) this.game.findUniqueObject(parseInt(input[4]));
        if (field != null && fieldTarget != null) {
            field.addNeighbor(fieldTarget);
            return true;
        }

        return false;
    }

    /**
     * Writes the commands of the game to a file.
     * @param path The file path.
     * @return True if successful.
     */
    private boolean saveHistory(String path) {
        try {
            FileWriter writer = new FileWriter(path);
            for (String s:
                 this.history) {
                writer.write(s + "\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred during writing save file.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads the commands of the game from a file.
     * @param path The file path.
     * @return True if successful.
     */
    public boolean loadHistory(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            this.initNewGame();
            
            while ((line = reader.readLine()) != null) {
                this.history.add(line);
            }

            reader.close();

            for (String entry : this.history) {
                this.handleTopLvlCommand(entry);
            }
           // this.history.forEach(this::handleTopLvlCommand);
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred during reading save file.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Asks a question to the user.
     * @param question The question to be asked.
     * @return The answer of the user. (int)
     */
    private int askQuestion(String question) {
        System.out.println(question);
        if (scanner != null)
        	return parseInt(scanner.nextLine());
        return 0;
    }

    /**
     * Handles the game end event.
     * @param winner The winner of the game.
     */
    private void handleGameEnd(Integer winner) {
        System.out.println("Game ended.");
        System.out.println("The winner is: " + winner);
    }

    /*
    * Return the current Game object;
    * @return The current Game object.
    * */
    public Game getGame() {
        return this.game;
    }
}
