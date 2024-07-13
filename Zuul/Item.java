/*
 * In the class item, there are getters for the item name and the message they display. The class does not
 * set the items as that is done inside the class Game.
 */
public class Item {

	String name;
	String message;

	public Item(String newName, String newMessage) { // Constructor for the items that are set in the game class.
		name = newName;
		message = newMessage;
	}
	
	public String getName() { // Getter for the string name.
		return name;
	}
	
	public String getMessage() { // Getter for the string message.
		return message;
	}
	
}
