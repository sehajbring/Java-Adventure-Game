// import java libraries and declare package
package logic;
import javax.swing.*;

/** 
 * Generate EventsLogic class which stores the main event variables
 * and the getter/setter methods for the event.
 * @author Leia Astorga, Grace Lan, Sehajveer Bring
 */
public class EventsLogic extends JPanel {
    
	public static final long serialVersionUID = 0412171700;
	
	// declare and initialize game variables
	private boolean switchSetting = false;
	private boolean buttonSetting = false;
	private boolean leverSetting = false;
	private boolean paperFound = false;
	private String itemSelected = "";
	private boolean closeEvents = false;
	
	
	/**
	 * @return the switchSetting
	 */
	public boolean getSwitchSetting() {
		return switchSetting;
	}

	/**
	 * @param switchSetting the switchSetting to set
	 */
	public void setSwitchSetting(boolean switchSetting) {
		this.switchSetting = switchSetting;
	}

	/**
	 * @return the buttonSetting
	 */
	public boolean getButtonSetting() {
		return buttonSetting;
	}

	/**
	 * @param buttonSetting the buttonSetting to set
	 */
	public void setButtonSetting(boolean buttonSetting) {
		this.buttonSetting = buttonSetting;
	}

	/**
	 * @return the leverSetting
	 */
	public boolean getLeverSetting() {
		return leverSetting;
	}

	/**
	 * @param leverSetting the leverSetting to set
	 */
	public void setLeverSetting(boolean leverSetting) {
		this.leverSetting = leverSetting;
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

	/**
	 * @return the closeEvents
	 */
	public boolean getCloseEvents() {
		return closeEvents;
	}

	/**
	 * @param closeEvents the closeEvents to set
	 */
	public void setCloseEvents(boolean closeEvents) {
		this.closeEvents = closeEvents;
	}
	
	
}