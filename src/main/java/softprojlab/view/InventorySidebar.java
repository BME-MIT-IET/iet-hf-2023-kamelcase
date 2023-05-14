package softprojlab.view;

// Java Imports
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.lang.reflect.Array;
import java.util.ArrayList;

// Project Imports
import softprojlab.model.item.agent.Agent;
import softprojlab.model.item.equipment.Equipment;
import softprojlab.view.customViews.DarkLabel;

/**
 *
 */
public class InventorySidebar extends JPanel {

    // Static Attributes

    /**
     * Unique ID for Serialization
     */
    private static final long serialVersionUID = -2860629412078381129L;

    // Private Attributes

    /**
     *
     */
    private JPanel inventory;

    /**
     *
     */
    private ArrayList<DarkLabel> sidebar;

    /**
     * Reference to the single MainWindow instance.
     */
    private MainWindow mainWindow;
    private ArrayList<String> sidebardatas = new ArrayList<String>();
    // Constructors

    /**
     * Default constructor.
     * @param rootsResference to the single MainWindow instance.
     */
    public InventorySidebar(MainWindow root){
        this.inventory = new JPanel();
        this.inventory.setLayout(new GridLayout(16,1));
        this.sidebar = new ArrayList<DarkLabel>();

        this.mainWindow = root;


        sidebardatas.add("Current Virologist: ");
        sidebardatas.add("ActionTokens: ");
        sidebardatas.add("Nucleotide: ");
        sidebardatas.add("Aminoacid: ");
        sidebardatas.add("Equipments: ");
        sidebardatas.add("Learned Agents: ");
        sidebardatas.add("Synthetised Agents: ");
        sidebardatas.add("Applied Agents: ");
        init(sidebardatas);

    }

    // Private Methods

    /**
     *
     * @param sidebardatas az adatok amiket megjelenit majd a sidebar
     */
    private void init(ArrayList<String> sidebardatas){
        inventory.removeAll();

        //feltölti a sidebart az adatokkal
        for(int i = 0; i < sidebardatas.size(); i++) {
            this.sidebar.add(new DarkLabel(sidebardatas.get(i)));
        }

        //hozzáadja a panelhez a JLabeleket majd mindegyik utan egy szeparatort
        for(int i = 0; i < this.sidebar.size(); i++){
            this.inventory.add(this.sidebar.get(i));
            this.inventory.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        this.inventory.setBackground(Color.BLACK);
        this.setBackground(Color.BLACK);

        this.add(this.inventory);
    }

    /**
     * Called if someone uses any action which will change the value of any parameter on Virologist
     */
    public void invalidate(){
    	super.invalidate();
    	
        sidebar.clear();
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> wholeString = new ArrayList<String>();
        if(mainWindow.game.getCurrentVirologist() != null) {
        	wholeString.add(Integer.toString(mainWindow.game.getCurrentVirologist().getUid()));
            wholeString.add(Integer.toString(mainWindow.game.getCurrentVirologist().getActionTokens()));
            wholeString.add(Integer.toString(mainWindow.game.getCurrentVirologist().getNucleotideCount()));
            wholeString.add(Integer.toString(mainWindow.game.getCurrentVirologist().getAminoacidCount()));

            ArrayList<Equipment> a = new ArrayList<>(mainWindow.game.getCurrentVirologist().getDeepEquipment());
            String temp0 = getStringFromArrayList2(a);
            wholeString.add(temp0);

            ArrayList<Agent> b = new ArrayList<>(mainWindow.game.getCurrentVirologist().getDeepLearned());
            String temp = getStringFromArrayList(b);
            wholeString.add(temp);

            ArrayList<Agent> c = new ArrayList<>(mainWindow.game.getCurrentVirologist().getDeepSynthesised());
            String temp2 = getStringFromArrayList(c);
            wholeString.add(temp2);


            ArrayList<Agent> d = new ArrayList<>(mainWindow.game.getCurrentVirologist().getDeepApplied());
            String temp3 = getStringFromArrayList(d);
            wholeString.add(temp3);

            for(int i = 0; i < wholeString.size(); i++) {
                output.add(sidebardatas.get(i) + " " + wholeString.get(i));
            }
            init(output);

        }

    }

    /**
     *
     * @param a ArrayList from we want to get a string
     * @return the transformed String
     */
    private String getStringFromArrayList(ArrayList<Agent> a){
        String temp = "";
        for(int i = 0; i < a.size(); ++i){
            temp +=  " " + a.get(i).getDisplayableName();
        }
        return temp;
    }

    private String getStringFromArrayList2(ArrayList<Equipment> a){
        String temp = "";
        for(int i = 0; i < a.size(); ++i){
            temp +=  " " + a.get(i).getDisplayableName();
        }
        return temp;
    }
}
