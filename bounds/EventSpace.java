// declare package and import java libraries
package bounds;
import gui.*;
import java.awt.*;
import javax.swing.*;

/**
 * Generate EventSpace class, which is a child class of Bounds which handles the user colliding
 * with an event space on the game map.
 * @author Anindya Mandal, Leia Astorga, Sehajveer Bring
 */
public class EventSpace extends Bounds{

	public static final long serialVersionUID = 0412171700;
	
	/**
	 * This method triggers the appropriate event based on the event name of the user's location on the map.
	 * @param eventContainer - the panel in the game map which displays game events
	 * @param eventsGUI - an instance of the game events that is altered depending on the player's location
	 * @param eventName - used to identify which event is being triggered by the player's movement
	 */
	public void callAction(JLayeredPane eventContainer, EventsGUI eventsGUI, String eventName) {	

			JLabel messageWindow = eventsGUI.getMessageWindow();
			JPanel eventPanel = eventsGUI.getEventPanel();
			CardLayout cardLayout = (CardLayout) eventPanel.getLayout();

			
			if (eventName == "BUTTON") {
				cardLayout.show(eventPanel, eventsGUI.getBUTTON());
				messageWindow.setText("<html><font color = white>You find a digital screen on the floor with rows of colourful buttons.</font></html>");	
			
			} else if (eventName == "LEVER") {
				cardLayout.show(eventPanel, eventsGUI.getLEVER());	
				if (!eventsGUI.getCleanedLever()) {
					messageWindow.setText("<html><font color = white>You find some sort of red breaker panel with a lever on it.<br>The lever is so rusted that it can't be moved.</font></html>");
				} else {
					messageWindow.setText("<html><font color = white>You're at the red breaker panel with the lever.<br>The rust is gone, but the lever is still to hard to move on its own.</font></html>");
				}
			
			} else if (eventName == "SWITCH") {
				cardLayout.show(eventPanel, eventsGUI.getSWITCH());
				if (!eventsGUI.getBoxOpen()) {
					messageWindow.setText("<html><font color = white>On the floor, you find a red box with a lid and a switch.<br>The lid is stuck shut.</font></html>");
				} else {
					messageWindow.setText("<html><font color = white>On the floor, you find the red box with an open lid.</font></html>");
				}
			}
		
		eventsGUI.getMessagePanel().setVisible(true);
		eventContainer.setVisible(true);
			
	}
	
}
