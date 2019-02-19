// import java libraries and declare package
package logic;
import gui.*;
import bounds.*;
import connect.*;
import java.util.*;
import javax.swing.*;


/** 
 * Generate WorldLogic class which stores the main world variables
 * and the getter/setter methods for the event.
 * @author Leia Astorga, Sehajveer Bring, Grace Lan
 */
public class WorldLogic {
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and encapsulate instance variables
	private WorldGUI worldGUI;
	private boolean gameWon = false;

	
	/**
	 * Constructor
	 * A copy instance of both world logic and world gui are made because 
	 * the this class needs to be able to alter these classes directly.
	 * @param worldGUIInstance - an instance of WorldGUI
	 * @param worldMainInstance - an instance of WorldMain
	 */
	public WorldLogic(WorldGUI worldGUIInstance) {
		worldGUI = worldGUIInstance;
	}
	
	
	/**
	 * This method changes values in the menu and world based on
	 * the user's actions in the events classes
	 */
	public void updateEvents() {
		
		// update event booleans based on menu selection
		EventsLogic eventsLogic = worldGUI.getEventsGUI().getEventsMain().getEventsLogic();
		String itemSelected = worldGUI.getMenuGUI().getMenuLogic().getItemSelected();
		eventsLogic.setItemSelected(itemSelected);
			
	}
	
	
	/**
	 * This method changes values in the menu and movement objects based on
	 * the user's actions in the world classes, which is why a privacy leak
	 * of the references is allowed instead of copy references being made.
	 */
	public void updateWorld() {
		// respond to player closing the events panel
		EventsLogic eventsLogic = worldGUI.getEventsGUI().getEventsMain().getEventsLogic();
		LoadSaveMain loadSaveMain = worldGUI.getLoadSaveGUI().getLoadSaveMain();
		MenuLogic menuLogic = worldGUI.getMenuGUI().getMenuLogic();
		Bounds checkBounds = worldGUI.getWorldMain().getCheckBounds();
		
		if (eventsLogic.getCloseEvents()) {
			worldGUI.getEventPanel().setVisible(false);
			worldGUI.getMessagePanel().setVisible(false);
			eventsLogic.setCloseEvents(false);
		}
		
		// update world booleans
		if (eventsLogic.getLeverSetting() &&
			eventsLogic.getButtonSetting() &&
			eventsLogic.getSwitchSetting()) {
			setGameWon(true);
		} else {
			setGameWon(false);
		}
		
		// respond to player clicking pause
		
		if (menuLogic.getPauseGame() &&
			!loadSaveMain.getTempHidePause() &&
			!worldGUI.getStartPausePanel().isVisible()) {
				worldGUI.getStartPausePanel().setVisible(true);
				worldGUI.getMenuPanel().setVisible(false);
		}
			
		if (!checkBounds.getPlayerDead()) {
			worldGUI.getGameInstructions().setText(worldGUI.getTextInstructions());
		}
	}
	
	
	/**
	 * This method changes values in the events based on the user's actions in the menu. We must
	 * use direct references inståead of copies because this method needs to directly change what
	 * is displayed on the screen.
	 */
	public void updateMenu() {
		MenuLogic menuLogic = worldGUI.getMenuGUI().getMenuLogic();
		EventsLogic eventsLogic = worldGUI.getEventsGUI().getEventsMain().getEventsLogic();
		ArrayList<JLabel> menuImages = worldGUI.getMenuGUI().getMenuImages();
		
		menuLogic.setPaperFound(eventsLogic.getPaperFound());
		menuLogic.setGotOil(!worldGUI.getOilIconLabel().isVisible());
		menuLogic.setGotPrybar(!worldGUI.getPrybarIconLabel().isVisible());
		
		menuImages.get(0).setVisible(menuLogic.getGotPrybar());
		menuImages.get(1).setVisible(menuLogic.getGotOil());
		menuImages.get(2).setVisible(menuLogic.getPaperFound());	
	}

	
	/**
	 * @return the gameWon
	 */
	public boolean getGameWon() {
		return gameWon;
	}

	/**
	 * @param gameWon the gameWon to set
	 */
	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}

}
