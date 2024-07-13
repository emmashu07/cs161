import java.util.ArrayList;

/**
 * Emma Shu: Zuul is a text-based game that was built off of Michael Kolling's and David J. Barnes's
 * "World of Zuul" application. In the game, you can travel around an island in hopes of finding the
 * treasure that the old man asks you to find. To start the game, you go north to find some more
 * information. There will be clues along the way to direct you to the right rooms and find the right
 * items to unlock the treasure. Other times, however, you will have to test your luck on which rooms to
 * pick. Beware, some of the rooms are instant death.
 */

class Game {
	private Parser parser;
	private Room currentRoom;
	
	Room palms, trees, swamp, pond, ocean, tunnel, cave1, cave2, hut, field, hills, wall, forest, tower, deadend;
	ArrayList<Item> inventory = new ArrayList<Item>();

	/**
	 * Create the game and initialize its internal map.
	 */
	public Game() {
		createRooms();
		parser = new Parser();
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public static void main(String[] args) { // Make instance of the game.
		Game mygame = new Game();
		mygame.play();
	}
	

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {
		// create the rooms and add this because of a new constructor so methods in other classes can use methods in other classes.
		palms = new Room("by a cluster of palm trees.\n", this); 
		trees = new Room ("underneath the trees.\n", this);
		swamp = new Room("at a swamp.\n", this );
		pond = new Room("at a small pond.\n", this);
		ocean = new Room("on the shore of the ocean.\n", this);
		tunnel = new Room("at the mouth of a tunnel.\n", this);
		cave1 = new Room("in a cave.\n", this);
		cave2 = new Room("in a cave.\n", this);
		hut = new Room("in a small hut.\n", this);
		field = new Room("at a grassy field.\n", this);
		hills = new Room("on some hills.\n", this);
		wall = new Room("at a wall.\n", this);
		forest = new Room("in a forest.\n", this);
		tower = new Room("at a tower.\n", this);
		deadend = new Room("at an empty clearing.\n", this);
		

		// initialize room exits
		palms.setExit("east", swamp);
		palms.setExit("north", trees);
		palms.setExit("south", pond);
		
		trees.setExit("south", palms);

		pond.setExit("north", palms);
		pond.setExit("south", ocean);
		pond.setExit("west", tunnel);

		tunnel.setExit("east", pond);
		tunnel.setExit("north", cave1);
		tunnel.setExit("south", cave2);
		
		cave1.setExit("east", trees);
		cave1.setExit("west", hut);
		cave1.setExit("south", tunnel);
		
		hut.setExit("east", cave1);
		hut.setExit("north", field);
		hut.setExit("south", hills);
		hut.setExit("west", wall);
		
		hills.setExit("north", hut);
		
		field.setExit("south", hut);
		
		wall.setExit("east", hut);
		
		forest.setExit("east", wall);
		forest.setExit("south", tower);
		forest.setExit("west", deadend);
		
		deadend.setExit("east", forest);
		
		tower.setExit("north", forest);
		
		currentRoom = palms; // start game by the palms.
		
		// Set items and their messages.
		trees.setItem(new Item("rock", "Go towards the direction you came from."));
		trees.setItem(new Item("walnut", ""));
		pond.setItem(new Item("bottle", "Follow the sign that contains the name of Spongebob's pet snail's name."));
		tunnel.setItem(new Item("torch", ""));
		tunnel.setItem(new Item("torch", ""));
		tunnel.setItem(new Item("silverkey", ""));
		tunnel.setItem(new Item("paper", ":)"));
		hut.setItem(new Item("wood", "Soon\nto have\n fun\nin the\n    hut."));
		hut.setItem(new Item("book", "Spongebob: Squarepants\nPatrick: Starfish\nSquidward: ?"));
		field.setItem(new Item("flower", ""));
		hills.setItem(new Item("squidkey", ""));
		hills.setItem(new Item("tentaclekey", ""));
		hills.setItem(new Item("tentacleskey", ""));
		deadend.setItem(new Item("leaves", "You pick up the leaves to see a single gold key."));
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Adventure!");
		System.out.println("Adventure is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println(currentRoom.getLongDescription());
	}

	/**
	 * Given a command, process (that is: execute) the command. If this command ends
	 * the game, true is returned, otherwise false is returned.
	 */
	private boolean processCommand(Command command) {
		boolean wantToQuit = false;

		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} 
		else if (commandWord.equals("go")) {
			wantToQuit = goRoom(command);
		} 
		else if (commandWord.equals("quit")) {
			wantToQuit = quit(command);
		}
		else if (commandWord.equals("inventory")) { // Display items in your inventory.
			printInventory();
		}
		else if (commandWord.equals("get")) { // Get items inside the room.
			getItem(command);
		}
		else if (commandWord.equals("drop")) { // Drop items from your inventory.
			dropItem(command);
		}
		else if(commandWord.equals("room")) { // Get room description again.
			System.out.println(currentRoom.getLongDescription());
		}
		return wantToQuit;
	}

	private void getItem(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know what to pick up...
			System.out.println("Get what?");
			return;
		}

		String item = command.getSecondWord();

		// Try to get item.
		Item newItem = currentRoom.getItem(item);

		if (newItem == null)
			System.out.println("The item does not exist!");
		else {
			if (inventory.size() < 2) { // MAX inventory size is 2 spaces.
				inventory.add(newItem);
				currentRoom.removeItem(item);
				System.out.println("Picked up: " + item);
				System.out.println(newItem.getMessage());
				System.out.println();
				if (checkInventory("leaves")) { // Only set the item gold key unless leaves are picked up.
					deadend.setItem(new Item("goldkey", ""));
				}
			}
			else {
				System.out.println("You have already filled up your 2 spaces in your inventory!");
			}
		}
	}

	private void dropItem(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know what to drop...
			System.out.println("Drop what?");
			return;
		}

		String item = command.getSecondWord();

		// Try to drop item.
		Item newItem = null;
		int index = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getName().equals(item)) {
				newItem = inventory.get(i);
				index = i;
			}
		}
		if (newItem == null)
			System.out.println("The item is not in your inventory!");
		else {
			inventory.remove(index);
			currentRoom.setItem(new Item(newItem.getName(), newItem.getMessage()));
			System.out.println("Dropped: " + item);
			System.out.println();
		}
	}
	
	private void printInventory() {
		String output = "";
		for (int i = 0; i < inventory.size(); i++) {
			output += inventory.get(i).getName() + " ";
		}
		System.out.println("You are carrying: ");
		System.out.println(output);
		System.out.println();
		
	}
	
	public boolean checkInventory(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// implementations of user commands:

	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("You were just shipwrecked on a small island.");
		System.out.println("Is that an old man sitting on the trees you see?");
		System.out.println("Go north to get some more information.");
		System.out.println();
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private boolean goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
			return false;
		}

		String direction = command.getSecondWord();

		// Try to leave current room.
		Room nextRoom = currentRoom.getExit(direction);

		if (nextRoom == null)
			System.out.println("There is no door!");
		else {
			Room previousRoom = currentRoom;
			currentRoom = nextRoom;
			System.out.println(currentRoom.getLongDescription());
			/*
			 * A long(er) description for all the rooms containing the storyline of the game. Some 
			 * information about items and exits can be found in here.
			 */
			if (nextRoom == trees) {
				if (previousRoom == palms) {
					System.out.println("An old man sits on the palm trees, throwing");
					System.out.println("walnuts at you.");
					System.out.println("\"Here's a thought, since you have nothing");
					System.out.println("better to do, why don't you find the treasure");
					System.out.println("that's buried on this island? I even have something");
					System.out.println("that may help you.\" The man drops a rock wrapped in");
					System.out.println("paper.");
					System.out.println();
				}
				else if (previousRoom == cave1) {
					System.out.println("The old man waves at you. \"Back so soon?\"");
				}
					
			}
			else if (nextRoom == swamp) {
				System.out.println("The muddy water shifts a bit. Before you know it,");
				System.out.println("a swamp monster swallows you whole. You lose.");
				return true;
			}
			else if (nextRoom == pond) {
				System.out.println("You look around for a while. You spot a half-buried");
				System.out.println("bottle in the sand. There looks like there's a piece of");
				System.out.println("paper in there. By the bottle, there are three signs.");
				System.out.println("One points west and says \"Gary\". Another points south");
				System.out.println("and says \"Barry\"");
				System.out.println();
			}
			else if (nextRoom == ocean) {
				System.out.println("You stand at the shore of an ocean. Suddenly, a deafening");
				System.out.println("shriek of a yellow sponge echoes shakes the island, sending.");
				System.out.println("you flying into the ocean. Barry? Really? You lose.");
				return true;
			}
			else if (nextRoom == tunnel) {
				System.out.println("You follow the sign to the entrance of a tunnel. Two");
				System.out.println("torches sit side by side. Through the light, you manage");
				System.out.println("to find a silver key on top of a piece of paper. Ahead,");
				System.out.println("the tunnel splits in two directions.");
				System.out.println();
			}
			else if (nextRoom == cave1) {
				if (checkInventory("torch")) {
					System.out.println("The torch lights up the dark and musty room. To the ");
					System.out.println("east, you find a hidden pathway covered in plants. ");
					System.out.println("To the west, the tunnel opens up into an open field");
					System.out.println("where a small hut sits.");
				}
				else {
					System.out.println("It is too dark for you to see anything.");
				}
			}
			else if (nextRoom == cave2) {
				System.out.println("The dark room shakes for a moment. The ceiling rumbles");
				System.out.println("and collapses on top of you. You lose.");
				return true;
			}
			else if (nextRoom == hut) {
				if (checkInventory("silverkey")) {
					System.out.println("You enter a hut with fully stocked shelves. Most of the");
					System.out.println("books you open are illegible; however, you find that one of");
					System.out.println("the books has a couple sentences you can read. On another");
					System.out.println("shelf, there lies a piece of wood.");
				}
				else {
					System.out.println("You pound on the door of the hut. It doesn't budge. Maybe a");
					System.out.println("key would work?");
				}
			}
			else if (nextRoom == field) {
				System.out.println("You see some pretty flowers.");
			}
			else if (nextRoom == hills) {
				System.out.println("You arrive at the base of a hill. There lies three keys");
				System.out.println("with small labels on each one.");
			}
			else if (nextRoom == wall) {
				if (checkInventory("tentaclekey") && checkInventory("squidkey")) {
					System.out.println("You can only have one key! You must drop one and come back.");
				}
				else if (checkInventory("tentacleskey") && checkInventory("squidkey")) {
					System.out.println("You can only have one key! You must drop one and come back.");
				}
				else if (checkInventory("tentacleskey") && checkInventory("tentaclekey")) {
					System.out.println("You can only have one key! You must drop one and come back.");
				}
				else if (checkInventory("tentaclekey") || checkInventory("squidkey")) {
					System.out.println("You stick your key into the doors until one flies open.");
					System.out.println("An octopus arm drags you into the room. You lose.");
					return true;
				}
				else if (checkInventory("tentacleskey")) {
					System.out.println("You stick your key into the center door and it opens");
					System.out.println("up to reveal a sign pointing you towards the west.");
					wall.setExit("west", forest);
				}
				else {
					System.out.println("You approach the three doors at a large wall. You knock");
					System.out.println("on all of the doors. None of them open. Maybe try a key?");
				}
			}
			else if (nextRoom == forest) {
				System.out.println("You enter a forest to see another sign pointing you south.");
				System.out.println("When you look more closely at the sign, it looks as if it has");
				System.out.println("twisted in the ground it has been planted in. Another pathway");
				System.out.println("leads west.");
			}
			else if (nextRoom == deadend) {
				System.out.println("You look around, nothing. Signs never lie.");
			}
			else if (nextRoom == tower) {
				if (checkInventory("goldkey")) {
					System.out.println("You push the door of the tower open. In the center of the");
					System.out.println("room sits a tightly locked chest. You twist your gold key");
					System.out.println("into the chest. The lid opens and sucks you into a blinding");
					System.out.println("white portal. You wake up to find yourself sprawled on the ");
					System.out.println("couch in front of a 24/7 live rerun of Spongebob. You win.");
					return true;
				}
				else {
					System.out.println("You push the door of the tower open. In the center of the");
					System.out.println("room sits a tightly locked chest. You pick it up and shake it");
					System.out.println("but nothing happens.");
				}
			}
			
		}
		return false;
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we really
	 * quit the game. Return true, if this command quits the game, false otherwise.
	 */
	private boolean quit(Command command) {
		if (command.hasSecondWord()) {
			System.out.println("Quit what?");
			return false;
		} 
		else
			return true; // signal that we want to quit
	}
}
