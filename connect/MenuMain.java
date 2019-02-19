// import java libraries and declare package
package connect;
import gui.*;
import logic.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/** 
 * Generate MenuMain class which moderates between the logic and
 * the GUI classes for the game menu.
 * @author Grace Lan, Leia Astorga
 */
public class MenuMain implements MouseListener {  
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and encapsulate instance variables
	private MenuGUI menuGUI;
	private MenuLogic menuLogic;
	
	/**
	 * Constructor
	 * @param menuGUIInstance - an instance of MenuGUI
	 * @param menuLogicInstance - an instance of MenuLogic
	 */
	public MenuMain(MenuGUI menuGUIInstance) {
		menuGUI = menuGUIInstance;
		menuLogic = new MenuLogic();
	}
	
	
	/**
	 * This method respond to mouse clicks. If the user clicks on one of the
	 * components, an image is opened or closed in response to the click. Game
	 * booleans may also be flipped via MenuLogic if certain items are clicked.
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		JLabel whichLabel = (JLabel)mouseEvent.getSource();
		ArrayList<JLabel> popImages = menuGUI.getPopImages();
		ArrayList<JLabel> menuImages = menuGUI.getMenuImages();
		
		if (whichLabel.equals(menuImages.get(0)) && menuLogic.getGotPrybar()) {
			popImages.get(0).setVisible(true);
			popImages.get(1).setVisible(false);
			popImages.get(2).setVisible(false);
			menuLogic.setItemSelected("PRYBAR");
			menuGUI.getMessageWindow().setText("<html><font color = white>A Heavy Prybar</font></html>");
			menuGUI.getPopContainer().setVisible(true);
		
		} else if (whichLabel.equals(menuImages.get(1)) && menuLogic.getGotOil()) {
			popImages.get(0).setVisible(false);
			popImages.get(1).setVisible(true);
			popImages.get(2).setVisible(false);
			menuLogic.setItemSelected("OIL");
			menuGUI.getMessageWindow().setText("<html><font color = white>An Oil Container</font></html>");
			menuGUI.getPopContainer().setVisible(true);
		
		} else if (whichLabel.equals(menuImages.get(2)) && menuLogic.getPaperFound()) {
			popImages.get(0).setVisible(false);
			popImages.get(1).setVisible(false);
			popImages.get(2).setVisible(true);
			menuLogic.setItemSelected("PAPER");
			menuGUI.getMessageWindow().setText("<html><font color = white>Some Scrap Paper</font></html>");
			menuGUI.getPopContainer().setVisible(true);
		
		} else if (whichLabel.equals(menuGUI.getPauseButton())) {
			menuLogic.setPauseGame(true);
			
		} else if (whichLabel.equals(menuGUI.getCloseEventButton())){
			popImages.get(0).setVisible(false);
			popImages.get(1).setVisible(false);
			popImages.get(2).setVisible(false);
			menuGUI.getPopContainer().setVisible(false);
		}
	}

	
	/**
	 * These unused methods are required for MouseListener to function
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseReleased(MouseEvent mouseEvent) {}
	public void mouseEntered(MouseEvent mouseEvent) {}
	public void mouseExited(MouseEvent mouseEvent) {}
	public void mouseMoved(MouseEvent mouseEvent) {}
	public void mousePressed(MouseEvent mouseEvent) {}
	
	/**
	 * @return the menuLogic
	 */
	public MenuLogic getMenuLogic() {
		return menuLogic;
	}


}