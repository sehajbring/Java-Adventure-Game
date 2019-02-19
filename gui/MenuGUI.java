// import java libraries and declare package
package gui;
import logic.*;
import connect.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/** 
 * Generate MenuGUI class which will handle and display the GUI for
 * the game menu.
 * @author Grace Lan, Leia Astorga, Sehajveer Bring
 */
public class MenuGUI {
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and initialize instance variables
	private MenuMain menuMain = new MenuMain(this);
	private MenuLogic menuLogic = menuMain.getMenuLogic();
	
	// generate panels for menu
	private JPanel menuPanel = new JPanel(new GridLayout(5,1));
	private JLayeredPane popContainer = new JLayeredPane();
	private JPanel popPanel = new JPanel();
	private JPanel labelPanel = new JPanel(new BorderLayout());
	
	private ArrayList<JLabel> menuImages = new ArrayList<JLabel>();
	private ArrayList<JLabel> popImages = new ArrayList<JLabel>();
	private JLabel messageWindow = new JLabel();
	private ImageIcon messageIcon = Icon.createImageIcon("/images/UI/popLabel.png");
	private ImageIcon popIcon = Icon.createImageIcon("/images/UI/eventsWindow.png");
	private ImageIcon pauseIcon = Icon.createImageIcon("/images/UI/pauseButton.png");
	private ImageIcon closeButton = Icon.createImageIcon("/images/UI/closeButton.png");
	private JLabel pauseButton = new JLabel(pauseIcon);
	private JLabel closeEventButton = new JLabel(closeButton);
	
	
	/**
	 * This method adds the different menu panels to the main menu container
	 */
	public MenuGUI() {
		// call methods which generate each menu space's GUI
		createImageFiles();
		createMenuSpace();
		createPopSpace();
		createMessageWindow();
		
		getMenuPanel().setOpaque(false);
		getPopPanel().setOpaque(false);
		getPopContainer().setOpaque(false);
		
		getPopImages().get(3).setBounds(420, 25, getPopIcon().getIconWidth(), getPopIcon().getIconHeight());
		
		popPanel.setBounds(420, 25, getPopIcon().getIconWidth(), getPopIcon().getIconHeight());
		labelPanel.setBounds(600, 550, getMessageIcon().getIconWidth(), getMessageIcon().getIconHeight());
		
		closeEventButton.addMouseListener(menuMain);
        closeEventButton.setBounds(962, 29, closeButton.getIconWidth(), closeButton.getIconHeight());
		
		popContainer.add(getPopImages().get(3), new Integer(0));
		popContainer.add(popPanel, new Integer(1));
		popContainer.add(labelPanel, new Integer(1));
		popContainer.add(closeEventButton, new Integer(2));
	}
	
	
	/**
	 * This method gets the image files via a helper method, and sets icons
	 * in their labels depending on the game variable settings.
	 */
	private void createImageFiles() {
		JLabel prybarMenu = new JLabel(Icon.createImageIcon("/images/items/prybarIcon.png"));
		JLabel oilMenu = new JLabel(Icon.createImageIcon("/images/items/oilIcon.png"));
		JLabel paperMenu = new JLabel(Icon.createImageIcon("/images/items/paperIcon.png"));
		
		JLabel prybarPop = new JLabel(Icon.createImageIcon("/images/items/prybar.png"));
		JLabel oilPop = new JLabel(Icon.createImageIcon("/images/items/oil.png"));
		JLabel paperPop = new JLabel(Icon.createImageIcon("/images/items/paper.png"));
		
		JLabel popBG = new JLabel(getPopIcon());
		
		// add labels to the label array
		getMenuImages().add(prybarMenu);
		getMenuImages().add(oilMenu);
		getMenuImages().add(paperMenu);
		
		getPopImages().add(prybarPop);
		getPopImages().add(oilPop);
		getPopImages().add(paperPop);
		getPopImages().add(popBG);
		
		// display the default label visibility at the start of the game
		getMenuImages().get(0).setVisible(false);
		getMenuImages().get(1).setVisible(false);
		getMenuImages().get(2).setVisible(false);
		
		getPopImages().get(0).setVisible(false);
		getPopImages().get(1).setVisible(false);
		getPopImages().get(2).setVisible(false);
	}
	
	
	/**
	 * This method creates the menu panel, which contains the items the player
	 * has acquired, and a pause button for stopping the game.
	 */
	public void createMenuSpace() {
		// add listeners to each jLabel
		getMenuImages().get(0).addMouseListener(menuMain);
		getMenuImages().get(1).addMouseListener(menuMain);
		getMenuImages().get(2).addMouseListener(menuMain);
		getPauseButton().addMouseListener(menuMain);
		
		JLabel menuTitle = new JLabel("<html><font color = white><br><font size = 5><font style = bold>Items<br><br></font></html>");
		
		// add components to their containers
		getMenuPanel().add(menuTitle);
		getMenuPanel().add(getMenuImages().get(0));
		getMenuPanel().add(getMenuImages().get(1));
		getMenuPanel().add(getMenuImages().get(2));
		getMenuPanel().add(getPauseButton());
	}
	
	
	/**
	 * This method creates the pop panel, which displays items that the player
	 * has clicked on in their inventory.
	 */
	private void createPopSpace() {
		getPopPanel().add(getPopImages().get(0));
		getPopPanel().add(getPopImages().get(1));
		getPopPanel().add(getPopImages().get(2));

		// add listeners to each JLabel
		getPopImages().get(0).addMouseListener(menuMain);
		getPopImages().get(1).addMouseListener(menuMain);
		getPopImages().get(2).addMouseListener(menuMain);
	}
	
	
	/**
	 * This method generates the message window which will display information for the user
	 * when they click on an item in their inventory.
	 */
	public void createMessageWindow() {
		messageWindow = new JLabel("...");
		messageWindow.setFont(new Font("Courier", Font.PLAIN, 20));
		messageWindow.setIcon(messageIcon);
		messageWindow.setIconTextGap(-messageIcon.getIconWidth()+10);
		messageWindow.setHorizontalTextPosition(0);
		messageWindow.setOpaque(false);
				
		labelPanel.add(messageWindow, BorderLayout.CENTER);
		labelPanel.setOpaque(false);
		
	}
	

	/**
	 * @return the menuLogic
	 */
	public MenuLogic getMenuLogic() {
		return menuLogic;
	}

	/**
	 * @return the menuPanel
	 */
	public JPanel getMenuPanel() {
		return menuPanel;
	}

	/**
	 * @return the popContainer
	 */
	public JLayeredPane getPopContainer() {
		return popContainer;
	}

	/**
	 * @return the popPanel
	 */
	public JPanel getPopPanel() {
		return popPanel;
	}

	/**
	 * @return the menuImages
	 */
	public ArrayList<JLabel> getMenuImages() {
		return menuImages;
	}

	/**
	 * @return the popImages
	 */
	public ArrayList<JLabel> getPopImages() {
		return popImages;
	}

	/**
	 * @return the pauseButton
	 */
	public JLabel getPauseButton() {
		return pauseButton;
	}

	/**
	 * @return the messageWindow
	 */
	public JLabel getMessageWindow() {
		return messageWindow;
	}

	/**
	 * @return the messageIcon
	 */
	public ImageIcon getMessageIcon() {
		return messageIcon;
	}

	/**
	 * @return the popIcon
	 */
	public ImageIcon getPopIcon() {
		return popIcon;
	}

	/**
	 * @return the closeEventButton
	 */
	public JLabel getCloseEventButton() {
		return closeEventButton;
	}

	
}