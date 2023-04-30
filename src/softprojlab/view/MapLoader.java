package softprojlab.view;

import softprojlab.cli.CLI;
import softprojlab.model.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MapLoader {
    public String[] availableMaps;

    private CLI cli;


    public MapLoader() {
        this.cli = new CLI();
        this.availableMaps = loadMapList();
    }

    /**
     * Loads the possible options for the map.
     */
    private String[] loadMapList(){
        File fs = getMapsByOs();

        System.out.println(fs.getAbsolutePath());

        return fs.list();
    }


    /*
     * Returns the maps' location based of the current OS.
     * @return File object of the maps' location.
     * */
    private File getMapsByOs() {
        File fs;
        if(System.getProperty("os.name").contains("Windows"))
            fs = new File("C:\\maps");
        else
            fs = new File("/tmp/maps");
        return fs;
    }

    /**
     * Loads the map.
     * @param choice The index of the map in the available maps list.
     */
    public void load(int choice) {
        if(this.availableMaps != null)
            this.cli.loadHistory(this.getMapsByOs().getAbsolutePath() + "/" + this.availableMaps[choice]);
    }


    /**
     * Return the loaded game.
     * @return Game object.
     */
    public Game getGame() {
        return this.cli.getGame();
    }

}
