// import java libraries and declare package
package connect;
import gui.*;
import logic.*;
import bounds.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


/** 
 * Generate WorldMain class which moderates between the logic and
 * the GUI classes for the main game world.
 * @author Leia Astorga, Sehajveer Bring, Grace Lan, Anindya Mandal
 */
public class WorldMain implements ActionListener, ComponentListener, KeyListener, FocusListener, MouseListener {

	public static final long serialVersionUID = 0412171700;
	
	// declare and encapsulate instance variables
	private WorldGUI worldGUI;
	private WorldLogic worldLogic;
	private Timer timer = new Timer (10, this);
	private Bounds checkBounds;
	private boolean[] userKeyPressed = new boolean [4];
	private int playThrough = 0;

	
	/**
	 * Constructor
	 * @param worldGUIInstance - an instance of WorldGUI
	 */
	public WorldMain(WorldGUI worldGUIInstance) {
		userKeyPressed [0] = false;
		userKeyPressed [1] = false;
		userKeyPressed [2] = false;
		userKeyPressed [3] = false;
			
		worldGUI = worldGUIInstance;
		worldLogic = new WorldLogic(worldGUI);
		checkBounds = new Bounds();
	}
	

	/**
	 * This method responds to the action listener applied to the button in the overlay panel.
	 * If the button is pressed, game play is enabled.
	 * @param actionEvent - the action listener
	 */
	public void actionPerformed(ActionEvent actionEvent) {	
		updateGameValues();
		
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		labels.add(worldGUI.getOilIconLabel());
		labels.add(worldGUI.getPrybarIconLabel());
		labels.add(worldGUI.getDoorLabel());
		JLayeredPane eventContainer = worldGUI.getEventPanel();
		JLayeredPane finishPanel = worldGUI.getFinishPanel();
		EventsGUI eventsGUI = worldGUI.getEventsGUI();
		MovementGUI movementGUI = worldGUI.getMovementGUI();
		boolean gameWon = getWorldLogic().getGameWon();
		
		int tempPlayerX = worldGUI.getMovementGUI().getX() + worldGUI.getMovementGUI().getDX();
		int tempPlayerY = worldGUI.getMovementGUI().getY() + worldGUI.getMovementGUI().getDY();
		if(checkBounds.checkBounds(tempPlayerX, tempPlayerY)) {
			checkBounds.checkAction(labels, eventContainer, finishPanel, eventsGUI, movementGUI, gameWon);
			int futurePlayerX = worldGUI.getMovementGUI().getX() + worldGUI.getMovementGUI().getDX();
			int futurePlayerY = worldGUI.getMovementGUI().getY() + worldGUI.getMovementGUI().getDY();
			worldGUI.getMovementGUI().setX(futurePlayerX); 
			worldGUI.getMovementGUI().setY(futurePlayerY);
			worldGUI.repaint();
		}
		
	}
	
	
	/**
	 * This method respond to mouse clicks. If the user clicks on one of the components,
	 * and the correct game conditions are met, the image is modified in response to the click.
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		JLabel whichLabel = (JLabel)mouseEvent.getSource();
		
		if (whichLabel == worldGUI.getPlayButton()) {
			worldGUI.getStartPausePanel().setVisible(false);
			worldGUI.getMenuPanel().setVisible(true);
			worldGUI.getMenuGUI().getMenuLogic().setPauseGame(false);
			timer.start();
		
		} else if (whichLabel == worldGUI.getPlayAgain()) {
			playAgain();
		
		} else if (whichLabel == worldGUI.getExitGame()) {
			System.exit(0);
		}
	}
	
	/**
	 * This helper methods initiate a game restart if the player chooses to play again.
	 */
	public void playAgain() {
		MovementGUI movementGUI = worldGUI.getMovementGUI();
		worldGUI.getFinishPanel().setVisible(false);
		worldGUI.getStartPausePanel().setVisible(true);
		
		ArrayList<String> restartArray = new ArrayList<String>();
		restartArray.add("72");
		restartArray.add("30");
		restartArray.add(Integer.toString(playThrough));
		for(int index = 3; index <= 11; index++) {
			restartArray.add("false");
		}
		worldGUI.getLoadSaveGUI().setLoadArray(restartArray);
		worldGUI.getLoadSaveGUI().setGameProgress();
		
		if (playThrough >= 1 && (movementGUI.getPlayerIcon() != movementGUI.getGhostImage())) {
			checkBounds.deadPlayer();
			movementGUI.setPlayerIcon(movementGUI.getGhostImage());
			worldGUI.getGameInstructions().setText("<html><center><font color = white><font style = bold>Escape Adventures of the Grumpy Face:" +
												   "<br><br>You waited and waited for the sequel.<br>Eventually, you died and became a ghost." + 
												   "<br>You can float through walls...<br>But you're still trapped!" +
												   "<br><br>Game Controls:<br><br>'w' = move up&nbsp&nbsp&nbsp<br>'s' = move down&nbsp<br>'a' = move left&nbsp<br>'d' = move right" + 
												   "<br><br>Click on Items in the menu for a closer look.<br>The most recently clicked item will remain active<br>and can be used, even after you close it. </font></center></html>");
		}
		
		playThrough++;
	}
	
	
	/**
	 * This method is a helper method for the action listener, which updates all
	 * game values based on how often the timer triggers the action listener.
	 */
	public void updateGameValues() {	
		worldLogic.updateEvents();
		worldLogic.updateWorld();
		worldLogic.updateMenu();
		worldGUI.revalidate();
	}
	
	
	/**
	 * This method listens for when movement keys w, a, s, or d are
	 * pressed and updates the direction of velocity to 1 or -1.
	 * This changes the where the players new coordinates will lie.
	 * @param keyEvent - the key listener
	 */
	public void keyPressed(KeyEvent keyEvent) {
		if (!worldGUI.getStartPausePanel().isVisible()
				&& !worldGUI.getEventPanel().isVisible()
				&& !worldGUI.getPopPanel().isVisible()
				&& !worldGUI.getMessagePanel().isVisible()
				&& !worldGUI.getFinishPanel().isVisible()
				&& !worldGUI.getSavedGamesPanel().isVisible()) {

			int key = keyEvent.getKeyCode();
			if (key == KeyEvent.VK_A) {
				userKeyPressed [0] = true;
				worldGUI.getMovementGUI().setDX(-1);
			}
			if (key == KeyEvent.VK_D) {
				userKeyPressed [1] = true;
				worldGUI.getMovementGUI().setDX(1);
			}
			if (key == KeyEvent.VK_W) {
				userKeyPressed [2] = true;
				worldGUI.getMovementGUI().setDY(-1);
			}
			if (key == KeyEvent.VK_S) {
				userKeyPressed [3] = true;
				worldGUI.getMovementGUI().setDY(1);
			}
		}
	}
	
	
	/**
	 * This method listens for when movement keys w, a, s, or d are
	 * released and resets the direction of velocity to 0.
	 * @param keyEvent - the key listener
	 */
	public void keyReleased(KeyEvent keyEvent) {
		if (!worldGUI.getStartPausePanel().isVisible()
				&& !worldGUI.getEventPanel().isVisible()
				&& !worldGUI.getPopPanel().isVisible()
				&& !worldGUI.getMessagePanel().isVisible()
				&& !worldGUI.getFinishPanel().isVisible()
				&& !worldGUI.getSavedGamesPanel().isVisible()) {
			int key = keyEvent.getKeyCode();
			if (key == KeyEvent.VK_A){
				userKeyPressed [0] = false;
			}
			if (key == KeyEvent.VK_D){
				userKeyPressed [1] = false;
			}
			if (key == KeyEvent.VK_W){
				userKeyPressed [2] = false;
			}
			if (key == KeyEvent.VK_S) {
				userKeyPressed [3] = false;
			}
			if (!userKeyPressed [2] || !userKeyPressed [3]){
				worldGUI.getMovementGUI().setDY(0);
			}
			if (!userKeyPressed [0] || !userKeyPressed [1]){
				worldGUI.getMovementGUI().setDX(0);
			}
		}
	}


	/**
	 * This method listens for when the window has lost focus and returns the focus
	 * back to the window if the event panel is not currently visible
	 * @param focusEvent - the focus listener
	 */
    public void focusLost(FocusEvent focusEvent) {
    	if (!worldGUI.getEventPanel().isVisible()) {
    		worldGUI.requestFocusInWindow();
    	}
    }
    
    
    /**
     * This method listens for when a component with a component listener
     * added has been made visible
     * @param componentEvent - the componentListener
     */
    public void componentShown(ComponentEvent componentEvent) {
        worldGUI.removeFocusListener(this);
        worldGUI.removeKeyListener(this);
        
    }
    
    
    /**
     * This method listens for when a component with a component listener
     * added has been hidden from visibility
     * @param componentEvent - the componentListener
     */
    public void componentHidden(ComponentEvent componentEvent) {
    	worldGUI.addFocusListener(this);
    	worldGUI.addKeyListener(this);
  
    }
    
    
    /**
	 * These unused methods are required for listeners to function
	 */
    public void keyTyped(KeyEvent keyEvent) {}
	public void focusGained(FocusEvent focusEvent) {}
	public void componentMoved(ComponentEvent componentEvent) {}
	public void componentResized(ComponentEvent componentEvent) {}
	public void mouseReleased(MouseEvent mouseEvent) {}
	public void mouseEntered(MouseEvent mouseEvent) {}
	public void mouseExited(MouseEvent mouseEvent) {}
	public void mousePressed(MouseEvent mouseEvent) {}
	
	
	/**
	 * @return the worldLogic
	 */
	public WorldLogic getWorldLogic() {
		return worldLogic;
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @return the checkBounds
	 */
	public Bounds getCheckBounds() {
		return checkBounds;
	}

	/**
	 * @return the playThrough
	 */
	public int getPlayThrough() {
		return playThrough;
	}

	/**
	 * @param playThrough the playThrough to set
	 */
	public void setPlayThrough(int playThrough) {
		this.playThrough = playThrough;
	}
	
	
}
