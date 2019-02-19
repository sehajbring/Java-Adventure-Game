// import java libraries and declare package
package logic;

/** 
 * Generate MenuLogic class which stores the main menu variables
 * and the getter/setter methods for the event.
 * @author Grace Lan, Leia Astorga, Sehajveer Bring
 */
public class MenuLogic {
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and encapsulate instance variables
	private boolean gotPrybar = false;
	private boolean gotOil = false;
	private boolean paperFound = false;
	private boolean pauseGame = false;
	private String itemSelected = "";
	
	/**
	 * Default Constructor
	 */
	public MenuLogic() {
	}

	/**
	 * @return the gotPrybar
	 */
	public boolean getGotPrybar() {
		return gotPrybar;
	}
	
	/**
	 * @param gotPrybar the gotPrybar to set
	 */
	public void setGotPrybar(boolean gotPrybar) {
		this.gotPrybar = gotPrybar;
	}
	
	/**
	 * @return the gotOil
	 */
	public boolean getGotOil() {
		return gotOil;
	}

	/**
	 * @param gotOil the gotOil to set
	 */
	public void setGotOil(boolean gotOil) {
		this.gotOil = gotOil;
	}
	
	/**
	 * @return the paperFound
	 */
	public boolean getPaperFound() {
		return paperFound;
	}
	
	/**
	 * @param paperFound the paperFound to set
	 */
	public void setPaperFound(boolean paperFound) {
		this.paperFound = paperFound;
	}

	/**
	 * @return the pauseGame
	 */
	public boolean getPauseGame() {
		return pauseGame;
	}

	/**
	 * @param pauseGame the pauseGame to set
	 */
	public void setPauseGame(boolean pauseGame) {
		this.pauseGame = pauseGame;
	}

	/**
	 * @return the itemSelected
	 */
	public String getItemSelected() {
		return itemSelected;
	}

	/**
	 * @param itemSelected the itemSelected to set
	 */
	public void setItemSelected(String itemSelected) {
		this.itemSelected = itemSelected;
	}
	
}
