package softprojlab.view;

// Java Imports
import java.awt.*;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

// Project Imports
import softprojlab.model.character.Virologist;
import softprojlab.model.field.Field;
import softprojlab.view.MainWindow;

/**
 *
 */
public class MapView extends JPanel {

    // Static Attributes

    /**
     * Unique ID for Serialization
     */
    private static final long serialVersionUID = -1563588920413189429L;

    // Private Attributes

    /**
     * ez lesz a jövöhét zenéje
     */
    private JPanel map;

    /**
     * Reference to the single MainWindow instance.
     */
    private MainWindow mainWindow;

    // Constructors

    /**
     * Default constructor.
     * @param root Reference to the single MainWindow instance.
     */
    public MapView(MainWindow root) {
        this.map = new JPanel();

        this.mainWindow = root;

        this.init();
    }

    // Private Attributes


    private void init(){
        this.map.setSize(500,500);
        this.setBackground(Color.WHITE);
        Border border = BorderFactory.createTitledBorder("Map");
        this.map.setBorder(border);
    }

    public void invalidate(){
        repaint();
    }

    /**
     *
     * @param c the cordinates of the current drawing position
     * @param pivot pivot point of the rotation
     * @param angle angle of rotation
     * @return c the next position for the drawing
     */
    private Point rotate(Point c, Point pivot, double angle){
    	Point temp = new Point(c.x - pivot.x, c.y - pivot.y);
    	c.x = (int) (Math.cos(angle) * temp.x + Math.sin(angle) * temp.y);
    	c.y = (int) (-Math.sin(angle) * temp.x + Math.cos(angle) * temp.y);
    	c.x += pivot.x;
    	c.y += pivot.y;
    	return c;
    }


    /**
     * draws out the current field and the neighbours
     * @param g
     */
    public void paint(Graphics g){
        //current active player and their datas
        Virologist currentVirologist = null;
        Field currentField = null;
        ArrayList<Field> neighbors =null;

        boolean initailized = false;

        if(mainWindow.game.getCurrentVirologist()!=null) {
            currentVirologist = mainWindow.game.getCurrentVirologist();

            if(currentVirologist.getLocation()!=null){
                currentField = currentVirologist.getLocation();

                if(currentField.getNeighbors()!=null) {
                    neighbors = currentField.getNeighbors();

                    initailized =true;
                }
            }
        }


        if(initailized) {
            //starting cordinate for drawing circles
            Point coordinates = new Point(200, 100);

            for (int i = 0; i < neighbors.size(); i++) {

                //the line to the circle
                g.setColor(Color.GRAY);
                g.drawLine(250, 250, coordinates.x + 5, coordinates.y + 5);
                g.fillOval(coordinates.x, coordinates.y, 40, 40);
                g.setColor(Color.WHITE);
                if(neighbors.get(i)!=null)
                    g.drawString(neighbors.get(i).getDisplayableName(), coordinates.x + 5, coordinates.y + 20);

                rotate(coordinates, new Point(250, 250), Math.PI / 6);
            }

            g.setColor(Color.BLACK);
            g.fillRect(200, 200, 100, 100);
            g.setColor(Color.WHITE);
            g.fillRect(201, 201, 98, 98);
            g.setColor(Color.black);

            if(currentField.getUid()!=0)
                g.drawString("uid:" + currentField.getUid(), 205, 212);


            g.drawString("Kind:" + currentField.getDisplayableName(), 205, 224);

            //check there is other players
            if(currentField.getPlayers().size()!=0) {
                if (currentField.getPlayers().size() == 1) {
                    g.drawString("Others:", 205, 238);
                } else {// if there is
                    String temp = "";
                    //collect the uid-s
                    for (int i = 0; i < currentField.getPlayers().size(); i++) {
                        //exept their own
                        if (currentVirologist.getUid() != currentField.getPlayers().get(i).getUid() && currentField.getPlayers().get(i).getUid() != 0) {

                            temp += currentField.getPlayers().get(i).getUid() + ", ";
                        }
                    }
                    g.drawString("Others:" + temp, 205, 238);
                }
            }
        }
    }



}
