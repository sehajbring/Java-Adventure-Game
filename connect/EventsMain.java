// import java libraries and declare package
package connect;
import gui.*;
import logic.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** 
 * Generate EventsMain class which moderates between the logic and
 * the GUI classes for the game events.
 * @author Leia Astorga, Grace Lan
 */
public class EventsMain implements ActionListener, MouseMotionListener, MouseListener, ItemListener {  
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and encapsulate instance variables
	private EventsGUI eventsGUI;
	private EventsLogic eventsLogic = new EventsLogic();

	
	/**
	 * Constructor
	 * @param eventsGUIInstance - an instance of EventsGUI
	 */
	public EventsMain(EventsGUI eventsGUIInstance) {
		eventsGUI = eventsGUIInstance;
	}
	
	/**
	 * This method responds to the item listener applied to the temporary
	 * event menu in EventsGUI. This is for being able to test all events at once
	 * for debugging purposes
	 * @param itemEvent - the item listener
	 */
	public void itemStateChanged(ItemEvent itemEvent) {
		CardLayout cardLayout = (CardLayout) eventsGUI.getEventPanel().getLayout();
		cardLayout.show(eventsGUI.getEventPanel(), (String)itemEvent.getItem());
	}
	
	
	/**
	 * This method responds to the action listeners applied to each button in the grid.
	 * If a button is pressed, and the player has the paper item selected in their
	 * inventory, a method is called responding to the action.
	 * @param actionEvent - the action listener
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		if (eventsLogic.getItemSelected() == "PAPER") {
			JButton clickedButton = (JButton)actionEvent.getSource();
			buttonReact(actionEvent, clickedButton);
		} else {
			eventsGUI.getMessageWindow().setText("<html><font color = white>Pressing that button didn't seem to do anything...</font></html>");
		}
	}
	
	
	/**
	 * This method checks whether buttons in the grid are being pressed in the correct
	 * sequence and, if so, changes the button's label to an 'x'.
	 * @param actionEvent - the actionListener
	 * @param clickedButton - the button that was clicked by the user
	 */
	private void buttonReact(ActionEvent actionEvent, JButton clickedButton) {
		JButton whichButton = (JButton)actionEvent.getSource();
		JButton[] buttonArray = eventsGUI.getButtonArray();
		
		// checks whether button is a secret code button pressed in the right sequence
		if (!eventsLogic.getButtonSetting()) {
			// 3 7 14
			if	(whichButton == buttonArray[3]  && !eventsLogic.getButtonSetting() ||
				 whichButton == buttonArray[7]  && !eventsLogic.getButtonSetting() && !eventsLogic.getButtonSetting() ||
				 whichButton == buttonArray[14] && !eventsLogic.getButtonSetting() && !eventsLogic.getButtonSetting()) {
					clickedButton.setIcon(eventsGUI.getBtnBright());
					eventsGUI.setButtonSequence(eventsGUI.getButtonSequence() + 1);
					eventsGUI.getMessageWindow().setText("<html><font color = white>That button seems to do something.<br>The button you pressed flashed green!</font></html>");
			
			} else if (whichButton == buttonArray[20] && eventsGUI.getButtonSequence() == 3) {
					completeButtons();
					eventsGUI.getMessageWindow().setText("<html><font color = white>You hear something large and mechanical shutting down in the room.</font></html>");
					eventsLogic.setButtonSetting(true);
			} else {
				eventsGUI.getMessageWindow().setText("<html><font color = white>Pressing that button didn't seem to do anything...</font></html>");
			}
		}
	}
	
	
	/**
	 * This method sets the button grid to the completed state (all green, with
	 * a message that security has been deactivated.
	 */
	public void completeButtons() {
		for (int counter = 1; counter <= 20; counter++) {
			JButton[] tempButtons = eventsGUI.getButtonArray();
			tempButtons[counter].setText("<html><font color = white>!</font></html>");
			tempButtons[counter].setIcon(eventsGUI.getBtnBright());
			eventsGUI.setButtonArray(tempButtons);
		}
		
		JButton[] tempButtons = eventsGUI.getButtonArray();
		tempButtons[8].setText("<html><font color = white>SECURITY</font></html>");
		tempButtons[13].setText("<html><font color = white>DISABLED</font></html>");
		eventsGUI.setButtonArray(tempButtons);
	}
	
	
	/**
	 * This method resets the button grid to the default starting state.
	 */
	public void resetButtons() {
		eventsGUI.setButtonSequence(0);
		eventsGUI.getButtonPanel().removeAll();
		JButton[] tempButtons = eventsGUI.getButtonArray();
		
		for (int counter = 1; counter <= 20; counter++) {
			tempButtons[counter] = new JButton();
			tempButtons[counter].setOpaque(false);
			tempButtons[counter].setVisible(true);
			tempButtons[counter].setBorderPainted(false);
			tempButtons[counter].setHorizontalTextPosition(JButton.CENTER);
			tempButtons[counter].setVerticalTextPosition(SwingConstants.CENTER);
			tempButtons[counter].setFont(new Font("Courier", Font.PLAIN, 15));
			tempButtons[counter].setText("<html><font color = white>" + Integer.toString(counter) + "</font></html>");
			// add action listener to each button
			tempButtons[counter].addActionListener(this);
			// add each button to the container
			eventsGUI.getButtonPanel().add(tempButtons[counter]);
			
		}
		
		// set colours around each row of buttons
		for (int counter = 1; counter <= 20; counter++) {
			if (counter >= 1 && counter < 6) {
				tempButtons[counter].setIcon(eventsGUI.getBtnBlue());
			} else if (counter > 5 && counter < 11) {
				tempButtons[counter].setIcon(eventsGUI.getBtnGreen());
			} else if (counter > 10 && counter < 16) {
				tempButtons[counter].setIcon(eventsGUI.getBtnRed());
			} else if (counter > 15 && counter < 21) {
				tempButtons[counter].setIcon(eventsGUI.getBtnGray());
			}
		}
		
		eventsGUI.setButtonArray(tempButtons);
	}
	
	
	/**
	 * This method respond to mouse clicks. If the user clicks on one of the components,
	 * and the correct game conditions are met, the image is modified in response to the click.
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		JLabel whichLabel = (JLabel)mouseEvent.getSource();
		switchEventClick(whichLabel);
		leverEventClick(whichLabel);
		closeEventClick(whichLabel);
	}
	
	
	/**
	 * This method responds to the user's clicks in the switch event
	 * and updates the displayed images accordingly.
	 * @param whichLabel - the label that was clicked on during a game event
	 */
	public void switchEventClick(JLabel whichLabel) {
		// if user clicks within the switch image coordinates, it's setting is toggled on and off
		if (whichLabel.equals(eventsGUI.getSwitchImage())) {			
			if (eventsLogic.getSwitchSetting()) {
				eventsGUI.getSwitchImage().setIcon(eventsGUI.getSwitchIconOff());
				eventsGUI.getMessageWindow().setText("<html><font color = white>You turned the switch setting back to off.</font></html>");
				eventsLogic.setSwitchSetting(false);
			} else {
				eventsGUI.getSwitchImage().setIcon(eventsGUI.getSwitchIconOn());
				eventsGUI.getMessageWindow().setText("<html><font color = white>You turned the switch setting back to on.</font></html>");
				eventsLogic.setSwitchSetting(true);
			}	
		}
		
		// if user clicks within the box image coordinates, it is opened and/or emptied based on the user's past behaviour
		if (whichLabel.equals(eventsGUI.getBoxImage()) && eventsGUI.getBoxOpen()) {
			if (eventsLogic.getPaperFound()) {
				eventsGUI.getBoxImage().setIcon(eventsGUI.getSwitchIconClosed());
				eventsGUI.getMessageWindow().setText("<html><font color = white>You close the box..</font></html>");
				eventsGUI.setBoxOpen(false);
			} else {
				eventsGUI.getBoxImage().setIcon(eventsGUI.getSwitchIconEmpty());
				eventsGUI.getMessageWindow().setText("<html><font color = white>You pick up the paper in the box..</font></html>");
				
				eventsLogic.setPaperFound(true);
			}
		}
	}
	
	
	/**
	 * This method responds to the user's clicks in the lever event
	 * and updates the displayed images accordingly.
	 * @param whichLabel - the label that was clicked on during a game event
	 */
	public void leverEventClick(JLabel whichLabel) {
		// if user clicks after lever is oiled and prybar is selected, it's setting is toggled on and off
		if (whichLabel.equals(eventsGUI.getLeverImage())) {
			
			if (eventsGUI.getCleanedLever() && (eventsLogic.getItemSelected() == "PRYBAR")) {
				
				if (!eventsLogic.getLeverSetting()) {
					eventsGUI.getLeverImage().setIcon(eventsGUI.getLeverIconOn());
					eventsLogic.setLeverSetting(true);
					eventsGUI.getMessageWindow().setText("<html><font color = white>You use the prybar to change the lever's setting.<br>Nothing seems to happen.</font></html>");
				} else {
					eventsGUI.getLeverImage().setIcon(eventsGUI.getLeverIconClean());
					eventsLogic.setLeverSetting(false);
					eventsGUI.getMessageWindow().setText("<html><font color = white>You use the prybar to change the lever's setting.<br>Nothing seems to happen.</font></html>");
				}
			}
		}
	}
	
	
	/**
	 * This method generates the exit button for the event window, which hides the event panel when clicked.
	 * @param whichLabel - the label that was clicked on during a game event
	 */
	public void closeEventClick(JLabel whichLabel) {
		if (whichLabel.equals(eventsGUI.getCloseEventButton())) { // check if player clicks the close button
			eventsLogic.setCloseEvents(true);
		}
	}
	
	
	/**
	 * This method responds to the mouse motion listener applied to the Switch Event. If the 
	 * user has selected the prybar in their inventory, and the user drags a cursor over
	 * the box, the box opens to reveal an item.
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseDragged(MouseEvent mouseEvent) {
		JLabel whichLabel = (JLabel)mouseEvent.getSource();
		
		// checks if user is in switch event, has selected prybar in inventory, and box is closed
		if (whichLabel.equals(eventsGUI.getBoxImage()) && (eventsLogic.getItemSelected() == "PRYBAR") && !eventsGUI.getBoxOpen()) {
			// change imageIcon used for the box space label
			if (eventsLogic.getPaperFound() ) {
				eventsGUI.getBoxImage().setIcon(eventsGUI.getSwitchIconEmpty());
			} else {
				eventsGUI.getBoxImage().setIcon(eventsGUI.getSwitchIconOpen());
			}
			
			if (getEventsLogic().getPaperFound()) {
				eventsGUI.getMessageWindow().setText("<html><font color = white>You pry open the box using the prybar.<br>Looks like there's nothing inside...</font></html>");
			} else {
				eventsGUI.getMessageWindow().setText("<html><font color = white>You pry open the box using the prybar.<br>Looks like there's something inside...</font></html>");
			}
			
			eventsGUI.setBoxOpen(true);
			
			// checks if user is in lever event and has selected oil in inventory
		} else if (whichLabel.equals(eventsGUI.getLeverImage()) && (eventsLogic.getItemSelected() == "OIL") && !eventsGUI.getCleanedLever()) {
			// change imageIcon used for the box space label
			eventsGUI.getLeverImage().setIcon(eventsGUI.getLeverIconClean());
			eventsGUI.setCleanedLever(true);
			eventsGUI.getMessageWindow().setText("<html><font color = white>You used the oil to remove the rust from the lever.<br>The lever still won't budge on its own.</font></html>");
		}
	}

	
	/**
	 * These unused methods are required for MouseListener and MouseMotionListener to function
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseReleased(MouseEvent mouseEvent) {}
	public void mouseEntered(MouseEvent mouseEvent) {}
	public void mouseExited(MouseEvent mouseEvent) {}
	public void mouseMoved(MouseEvent mouseEvent) {}
	public void mousePressed(MouseEvent mouseEvent) {}

	
	/**
	 * @return the eventsLogic
	 */
	public EventsLogic getEventsLogic() {
		return eventsLogic;
	}
	
}