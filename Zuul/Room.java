import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Room 
{
    private String description;
    private HashMap exits;        // stores exits of this room.
    ArrayList<Item> items = new ArrayList<Item>();
    Game game;
    
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     */
    public Room(String description, Game mygame) 
    {
        this.description = description;
        exits = new HashMap();
        this.game = mygame;
    }

    /**
     * Define an exit from this room.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return "You are " + description + "\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
    	if (!game.checkInventory("torch") && game.getCurrentRoom() == game.cave1) { // Unless player has torch, they cannot "see" anything.
    		return "";
    	}
    	else if(!game.checkInventory("silverkey") && game.getCurrentRoom() == game.hut) { // Cannot display items inside the hut if not inside the hut.
	    	String returnString = "Exits:\n";
	        Set keys = exits.keySet();
	        for(Iterator iter = keys.iterator(); iter.hasNext(); )
	            returnString += iter.next() + " " + "\n";
	        
	        return returnString;
    	}
    	else {
	    	String returnString = "Exits:\n";
	        Set keys = exits.keySet();
	        for(Iterator iter = keys.iterator(); iter.hasNext(); )
	            returnString += iter.next() + " " + "\n";
	        returnString += "\nItems in the room:\n";
	        returnString += getRoomItems() + "\n";
	        System.out.println();
	        
	        return returnString;
    	}
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room getExit(String direction) 
    {
        return (Room)exits.get(direction);
    }
    
    /*
     * Get items in room.
     */
    public Item getItem (int index) {
    	return items.get(index);
    }
    
    
    public Item getItem (String itemName) {
    	Item noreturn = null;
    	for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(itemName)) {
				return items.get(i);
			}
		}
    	return null;
    }
    
    public void removeItem (String itemName) {
    	Item noreturn = null;
    	for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(itemName)) {
				items.remove(i);
			}
		}
    }
    
    /*
     * Set a particular item in the room.
     */
    public void setItem(Item newitem) {
    	items.add(newitem);
    }
    
    public String getRoomItems() { // Display the items inside the room.
	    	String output = "";
			for (int i = 0; i < items.size(); i++) {
				output += items.get(i).getName() + " ";
			}
			return output;
    }
}

