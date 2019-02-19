// declare package and import java libraries
package bounds;
import javax.swing.*;

/**
 * Generate ItemSpace class, which is a child class of Bounds which handles the user colliding
 * with an item space on the game map.
 * @author Anindya Mandal
 */
public class ItemSpace extends Bounds{
	
	public static final long serialVersionUID = 0412171700;

	/**
	 * This method allows the player to collect items by making them disappear from the game map.
	 * @param itemToHide - the JLabel which needs to be hidden from the game map
	 */
	public void callAction(JLabel itemToHide) {
		itemToHide.setVisible(false);
	}


}
