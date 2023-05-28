package softprojlab.view;

// Java Imports
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Project Imports
import softprojlab.model.character.Virologist;
import softprojlab.model.field.Field;
import softprojlab.model.item.Wearable;
import softprojlab.model.item.agent.*;
import softprojlab.view.customViews.DarkButton;

/**
 * 
 */
public class ActionSidebar extends ListItemManager {

	// Static Attributes
	
	/**
	 * Unique ID for Serialization 
	 */
    private static final long serialVersionUID = -2738353269065007394L;

    // Private Attributes
    
    /**
     * Reference to the single MainWindow instance.
     */
    private MainWindow mainWindow;

    /**
     * Agent generate success
     */
    private boolean successGeneratedAgent;

    /**
     * Kill success
     */
    private boolean successKilled;

    /**
     * Default constructor.
     * @param root Reference to the single MainWindow instance.
     */
    public ActionSidebar(MainWindow root){
        super(false);

        ArrayList<String> nameOfButtons = new ArrayList<>();
        nameOfButtons.add("Move");
        nameOfButtons.add("Synthetize");
        nameOfButtons.add("Kill");
        nameOfButtons.add("Spread");
        nameOfButtons.add("Steal");
        nameOfButtons.add("Skip");
        this.setOptions("Gombok", nameOfButtons);

        this.mainWindow = root;
    }


    private void handleUserResponse(Integer index) {
        if(mainWindow.game.getCurrentVirologist() != null) {
            if(this.selectedIndex == 0) mainWindow.game.getCurrentVirologist().move(index);
            else if (this.selectedIndex == 1){
                successGeneratedAgent = mainWindow.game.getCurrentVirologist().synthetiseAgent(generateAgent(index));
            }
            else if (this.selectedIndex == 2){
                successKilled = mainWindow.game.getCurrentVirologist().kill(mainWindow.game.getCurrentVirologist().getLocation().getPlayers().get(index));
            }
            else if (this.selectedIndex == 3) mainWindow.game.getCurrentVirologist().applyAgentTo(mainWindow.game.getCurrentVirologist().getLocation().getPlayers().get(index));
            else if (this.selectedIndex == 4) mainWindow.game.getCurrentVirologist().stealFrom(mainWindow.game.getCurrentVirologist().getLocation().getPlayers().get(index));
        }
        this.mainWindow.invalidate();
    }
    
    @Override
    public void onListItemClicked() {
        if (this.selectedIndex == 0) {
            if(mainWindow.game.getCurrentVirologist().actionTokens != 0){
                String temp = StringFromFields();
                if (temp != null && this.mainWindow.game.getCurrentVirologist().getLocation().getNeighbors().size() > 0) {
                    this.mainWindow.popup.askQuestion(temp, this::handleUserResponse);
                }
            }
            else{
                this.mainWindow.popup.askQuestion("Not enough ActionTokens to Move\n PLEASE PRESS SKIP",  this::handleUserResponse);
            }

        } else if (this.selectedIndex == 1) {
            String temp = StringFromAgent();
            if (temp != null) {
                this.mainWindow.popup.askQuestion(temp, this::handleUserResponse);
            }
            if (!successGeneratedAgent) {
                this.mainWindow.popup.askQuestion("You can't generate this Agent\n OKAY", this::handleUserResponse);
            }
        } else if (this.selectedIndex == 2) {
            String temp = StringFromVirologist();
            if (temp != null) {
                this.mainWindow.popup.askQuestion(temp, this::handleUserResponse);
            }
            if (!successKilled) {
                this.mainWindow.popup.askQuestion("You can't kill him! :(\n OKAY", this::handleUserResponse);
            }

        } else if (this.selectedIndex == 3) {
            if (mainWindow.game.getCurrentVirologist().getSynthesisedAgentsCount() > 0) {
                String temp = StringFromVirologist();
                if (temp != null) {
                    this.mainWindow.popup.askQuestion(temp, this::handleUserResponse);
                }
            }
            else{
                this.mainWindow.popup.askQuestion("You don't have any Agents to use\n OKAY",  this::handleUserResponse);
            }
        } else if (this.selectedIndex == 4) {
            if(mainWindow.game.getCurrentVirologist().actionTokens >= 3) {
                String temp = StringFromVirologist();
                if (temp != null) {
                    this.mainWindow.popup.askQuestion(temp, this::handleUserResponse);
                }
            }
            else{
                this.mainWindow.popup.askQuestion("You don't have enough ActionTokens to Steal\n OKAY",  this::handleUserResponse);
            }
        } else if (this.selectedIndex == 5) {
            this.mainWindow.popup.askQuestion("NextPlayer is coming\n OKAY",  this::handleUserResponse);
        	mainWindow.game.nextVirologist();
        	if (mainWindow.game.getCurrentVirologist() == null)
        		mainWindow.game.update();
        }
        mainWindow.invalidate();
    }



    private String StringFromFields(){
        String temp = "Select a Field\n";
        if(mainWindow.game.getCurrentVirologist() != null) {
            for (int i = 0; i < mainWindow.game.getCurrentVirologist().getLocation().getNeighbors().size(); ++i)
                temp += mainWindow.game.getCurrentVirologist().getLocation().getNeighbors().get(i).getDisplayableName() + "\n";
            return temp;
        }
        return null;
    }

    private String StringFromVirologist(){
        String temp = "Select a Virologist\n";
        if(mainWindow.game.getCurrentVirologist() != null) {
            for (int i = 0; i < mainWindow.game.getCurrentVirologist().getLocation().getPlayers().size(); ++i) {
            	Virologist iThVirologist = mainWindow.game.getCurrentVirologist().getLocation().getPlayers().get(i);
            	temp += iThVirologist.getDisplayableName() + " " + iThVirologist.getUid() + " " + iThVirologist.getLocation().getDisplayableName() + "\n";
            }
            return temp;
        }
        return null;

    }

    private String StringFromAgent(){
        String temp = "Select an Agent\n";
        ArrayList<String> agents= new ArrayList<>();
        agents.add("BearAgent");
        agents.add("DanceAgent");
        agents.add("DementiaAgent");
        agents.add("ParalyzerAgent");
        agents.add("ProtectiveAgent");
        for (int i = 0; i < agents.size(); ++i)
            temp += agents.get(i) + "\n";
        return temp;
    }

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
}
