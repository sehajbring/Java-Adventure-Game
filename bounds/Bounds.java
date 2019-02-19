// declare package and import java libraries
package bounds;
import gui.*;
import java.util.*;
import javax.swing.*;

/**
 * Generate Bounds class, which handles movement collisions and triggers events
 * or items depending on the user's movement around the game map.
 * @author Anindya Mandal, Grace Lan, Sehajveer Bring
 */
public class Bounds {
	
	public static final long serialVersionUID = 0412171700;
	
	// Booleans to invoke the correct child class
	private boolean itemSpace = false;
	private boolean validSpace = false;
	private boolean eventSpace = false;
	private boolean doorSpace = false;
	private boolean playerDead = false;

	// Player X, Y position to be used by child classes
	private int playerX;
	private int playerY;

	// Instance ArrayLists contain X and Y pixel values for all the walls in the world
	 private ArrayList<Integer> upperX = new ArrayList<Integer>(Arrays.asList(720, 720, 23 , 720, 240, 240, 310, 310, 310, 527, 600, 600, 478, 457, 720, 527, 720, 72, 73 , 93 , 720));
	 private ArrayList<Integer> lowerX = new ArrayList<Integer>(Arrays.asList(672, 0  , 0  , 0  , 195, 195, 0  , 265, 265, 480, 552, 483, 409, 409, 480, 480, 480, 0 , 0  , 50 , 649));
	 private ArrayList<Integer> upperY = new ArrayList<Integer>(Arrays.asList(360, 360, 360, 23 , 118, 146, 190, 238, 360, 167, 265, 265, 265, 360, 167, 45 , 72 , 96, 264, 240, 240));
	 private ArrayList<Integer> lowerY = new ArrayList<Integer>(Arrays.asList(0  , 313, 0  , 0  , 0  , 122, 145, 145, 242, 50 , 120, 216, 216, 216, 120, 0  , 43,  0 , 182, 194, 120));
	
	// Pixel coordinates for all event spaces in the world
	private ArrayList<Integer> eventUpperX = new ArrayList<Integer>(Arrays.asList(96 , 45 , 502));
	private ArrayList<Integer> eventLowerX = new ArrayList<Integer>(Arrays.asList(48 , 20 , 455));
	private ArrayList<Integer> eventUpperY = new ArrayList<Integer>(Arrays.asList(120, 286, 211));
	private ArrayList<Integer> eventLowerY = new ArrayList<Integer>(Arrays.asList(71 , 242, 172));
	
	
	// Integers to keep track of the event or item triggered
	private int eventNum = 0;
	private int itemNum = 0;

	
	// Pixel coordinates for all the item spaces in the world
	private ArrayList<Integer> upperItemX = new ArrayList<Integer>(Arrays.asList(645, 168));
	private ArrayList<Integer> lowerItemX = new ArrayList<Integer>(Arrays.asList(605, 119));
	private ArrayList<Integer> upperItemY = new ArrayList<Integer>(Arrays.asList(217, 310));
	private ArrayList<Integer> lowerItemY = new ArrayList<Integer>(Arrays.asList(170, 266));
	

	
	/**
	 * This method moderates the types of collisions the player may encounter as they move around
	 * the game map, and updates the game values if walls, items, or events are encountered.
	 * @param playerX - Player's X coordinate in pixels
	 * @param playerY - Player's Y coordinate in pixels
	 * @return true - if player is not running into walls, to make it easier for Movement to implement bounds
	 */
	public boolean checkBounds(int playerX, int playerY) {
		setPlayerX(playerX);
		setPlayerY(playerY);

		for (int i = 0; i < upperX.size(); i++) {
			if (!((playerX > lowerX.get(i) && playerX < upperX.get(i)) && (playerY > lowerY.get(i) && playerY < upperY.get(i)))) {
				validSpace = true; // wall collision checking
			} else {
				validSpace = false;
				break;
			}
		}
		
		for (int i = 0; i < eventUpperX.size(); i++) {
			if ((playerX > eventLowerX.get(i) && playerX < eventUpperX.get(i)) && (playerY > eventLowerY.get(i) && playerY < eventUpperY.get(i))) {
				eventSpace = true; // event collision checking
				eventNum = i + 1;
				break;
			} else {
				eventSpace = false;
			}
		}
		
		for (int i = 0; i < upperItemX.size(); i++) {
			if ((playerX > lowerItemX.get(i) && playerX < upperItemX.get(i)) && (playerY > lowerItemY.get(i) && playerY < upperItemY.get(i))) {
				itemSpace = true; // item collision checking
				itemNum = i + 1;
				break;
			} else {
				itemSpace = false;
			}
		}
		
		if ((playerX > 520) && (playerY > 43) && (playerY < 72)) {
			doorSpace = true; // door collision checking
		} else {
			doorSpace = false;
		}
		
		if (validSpace) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * This method passes the appropriate parameters to the child class depending on the type of collision
	 * the player has encountered.
	 * @param labels - an array list of labels which bounds will modify depending on the player's position
	 * @param eventContainer - the panel in the game map which displays game events
	 * @param finishPanel - the panel that displays the end message once the game is completed
	 * @param eventsGUI - an instance of the game events that is altered depending on the player's location
	 * @param movementGUI - an instance of the player's movement which is altered depending on what bounds they are colliding with
	 * @param gameWon - boolean that determines whether the player has completed all tasks required to complete the game
	 */
	public void checkAction(ArrayList<JLabel> labels, JLayeredPane eventContainer, JLayeredPane finishPanel, EventsGUI eventsGUI, MovementGUI movementGUI, boolean gameWon) {
		JLabel oilLabel = labels.get(0);
		JLabel prybarLabel = labels.get(1);
		JLabel doorLabel = labels.get(2);
		Bounds Action;
		
		if(eventSpace) {
			Action = new EventSpace();
			if (getEventNum() == 1) {
				Action.callAction(eventContainer, eventsGUI, "BUTTON");
			} else if (getEventNum() == 2) {
				Action.callAction(eventContainer, eventsGUI, "LEVER");
			} else if (getEventNum() == 3) {
				Action.callAction(eventContainer, eventsGUI, "SWITCH");
			}
			
			// Set the velocity to zero so player stops moving after an event is triggered
			movementGUI.setDX(0);
			movementGUI.setDY(0);
			
		} else if (itemSpace) {
			Action = new ItemSpace();
			if (getItemNum() == 1) {
				Action.callAction(oilLabel);
			} else if (getItemNum() == 2) {
				Action.callAction(prybarLabel);
			}
		} else if (doorSpace) {
			// Set the velocity to zero so player stops moving after the door is entered
			movementGUI.setDX(0);
			movementGUI.setDY(0);
			finishPanel.setVisible(true);
		}
		
		if (gameWon && !playerDead) {
			removeDoorBounds();
			Action = new ItemSpace();
			Action.callAction(doorLabel);
		} else if (!gameWon && !playerDead){
			livePlayer();
			doorLabel.setVisible(true);
		}
	}
	
	
	/**
	 * This method is overridden by one of the child classes depending on the collision type.
	 * @param eventContainer - the panel in the game map which displays game events
	 * @param eventsGUI - an instance of the game events that is altered depending on the player's location
	 * @param eventName - used to identify which event is being triggered by the player's movement
	 */
	public void callAction(JLayeredPane eventcontainer, EventsGUI eventsGUI, String eventName) {}
	
	/**
	 * This method is overridden by one of the child classes depending on the collision type
	 * @param itemToHide - the JLabel which needs to be hidden from the game map
	 */
	public void callAction(JLabel itemToHide) {}

	
	/**
	 * This method removes wall bounds when the player becomes a ghost
	 */
	public void deadPlayer() {
		upperX = new ArrayList<Integer>(Arrays.asList(720, 720, 23 , 720));
		lowerX = new ArrayList<Integer>(Arrays.asList(672, 0  , 0  , 0  ));
		upperY = new ArrayList<Integer>(Arrays.asList(360, 360, 360, 23 ));
		lowerY = new ArrayList<Integer>(Arrays.asList(0  , 313, 0  , 0  ));
		playerDead = true;
	}
	
	
	/**
	 * This method restores wall bounds when the player becomes a ghost
	 */
	public void livePlayer() {
		upperX = new ArrayList<Integer>(Arrays.asList(720, 720, 23 , 720, 240, 240, 310, 310, 310, 527, 600, 600, 478, 457, 720, 527, 720, 72, 73 , 93 , 720));
		lowerX = new ArrayList<Integer>(Arrays.asList(672, 0  , 0  , 0  , 195, 195, 0  , 265, 265, 480, 552, 483, 409, 409, 480, 480, 480, 0 , 0  , 50 , 649));
		upperY = new ArrayList<Integer>(Arrays.asList(360, 360, 360, 23 , 118, 146, 190, 238, 360, 167, 265, 265, 265, 360, 167, 45 , 72 , 96, 264, 240, 240));
		lowerY = new ArrayList<Integer>(Arrays.asList(0  , 313, 0  , 0  , 0  , 122, 145, 145, 242, 50 , 120, 216, 216, 216, 120, 0  , 43,  0 , 182, 194, 120));
		playerDead = false;
	}
	
	
	/**
	 * Removes the bound check on the ending door location
	 */
	public void removeDoorBounds() {
		upperX = new ArrayList<Integer>(Arrays.asList(720, 720, 23 , 720, 240, 240, 310, 310, 310, 527, 600, 600, 478, 457, 720, 527, 72, 73 , 93 , 720));
		lowerX = new ArrayList<Integer>(Arrays.asList(672, 0  , 0  , 0  , 195, 195, 0  , 265, 265, 480, 552, 483, 409, 409, 480, 480, 0 , 0  , 50 , 649));
		upperY = new ArrayList<Integer>(Arrays.asList(360, 360, 360, 23 , 118, 146, 190, 238, 360, 167, 265, 265, 265, 360, 167, 45 , 96, 264, 240, 240));
		lowerY = new ArrayList<Integer>(Arrays.asList(0  , 313, 0  , 0  , 0  , 122, 145, 145, 242, 50 , 120, 216, 216, 216, 120, 0  , 0 , 182, 194, 120));
	}
	
	
	/**
	 * 
	 * @return itemSpace - True if player is on an item, otherwise false
	 */
	public boolean getItemSpace() {
		return itemSpace;
	}
	
	
	/**
	 * @return playerX -  The player's X value
	 */
	public int getPlayerX() {
		return playerX;
	}
	
	/**
	 * @return playerY - The player's Y value
	 */
	public int getPlayerY() {
		return playerY;
	}
	
	/**
	 * @param num - The number that playerX gets set to
	 */
	public void setPlayerX(int num) {
		playerX = num;
	}
	
	/**
	 * @param num - The number that playerY gets set to
	 */
	public void setPlayerY(int num) {
		playerY = num;
	}
	
	/**
	 * @return eventNum - To identify which event was triggered
	 */
	public int getEventNum() {
		return eventNum;
	}
	
	/**
	 * @return itemNum - To identify which item was picked up
	 */
	public int getItemNum() {
		return itemNum;
	}

	/**
	 * @return the playerDead
	 */
	public boolean getPlayerDead() {
		return playerDead;
	}


}
