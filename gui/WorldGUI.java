// import java libraries and declare package
package gui;
import logic.*;
import connect.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/** 
 * Generate WorldGUI class which will handle and display the GUI for
 * the game events
 * @author Leia Astorga, Grace Lan, Sehajveer Bring
 */
public class WorldGUI extends JFrame {
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and initialize constants
	private static final int SCREEN_WIDTH = 1440;
	private static final int SCREEN_HEIGHT = 720;
	private static final int BGWORLDX = 0;
	private static final int BGWORLDY = 0;
	
	private static final int BG_LAYER = 0;
	private static final int ICON_LAYER = 1;
	private static final int PLAYER_LAYER = 2;
	private static final int UI_LAYER = 3;
	private static final int EVENTS_LAYER = 4;
	private static final int MENU_LAYER = 5;
	private static final int OVER_LAYER = 6;
	private static final int TOP_LAYER = 7;
	
	private static final int MESSAGEWINDOWX = 220;
	private static final int MESSAGEWINDOWY = 550;
	private static final int EVENTSWINDOWX = 420;
	private static final int EVENTSWINDOWY = 25;
	private static final int MENUWINDOWX = 1350;
	private static final int MENUWINDOWY = 25;
	
	private static Location buttonLocation = new Location(3, 4);
	private static Location leverLocation = new Location(1, 11);
	private static Location switchLocation = new Location(20, 8);
	
	private static Location prybarLocation = new Location(6, 12);
	private static Location oilLocation = new Location(26, 8);
	private static Location doorLocation = new Location(21, 2);

	// declare and initialize instance variables
	private WorldMain worldMain = new WorldMain(this);
	private LoadSaveGUI loadSaveGUI = new LoadSaveGUI(this);
	private EventsGUI eventsGUI = new EventsGUI();
	private MenuGUI menuGUI = new MenuGUI();
	private MovementGUI movementGUI = new MovementGUI();
	
	private JLayeredPane startPausePanel = new JLayeredPane();
	private JLayeredPane savedGamesPanel = loadSaveGUI.getSavedGamesPanel();
	private JLayeredPane layeredPane = new JLayeredPane();
	private JLayeredPane eventPanel = eventsGUI.getEventContainer();
	private JLayeredPane finishPanel = new JLayeredPane();
	private JPanel messagePanel = eventsGUI.getMessagePanel();
	private JPanel menuPanel = menuGUI.getMenuPanel();
	private JLayeredPane popPanel = menuGUI.getPopContainer();
	private JPanel movementPanel = movementGUI;
	private JPanel loadSavePanel;

	private JLabel playButton;
	private JLabel playAgain;
	private JLabel exitGame;
	
	private ImageIcon pauseBGIcon = Icon.createImageIcon("/images/UI/loadBG.png");
	private JLabel pauseBG = new JLabel(pauseBGIcon);
	private JLabel gameInstructions;
	private JLabel finalMessage;
	private JLabel bgWorldLabel;
	private JLabel switchIconLabel;
	private JLabel buttonIconLabel;
	private JLabel leverIconLabel;
	private JLabel prybarIconLabel;
	private JLabel oilIconLabel;
	private JLabel menuWindowLabel;
	private JLabel doorLabel;
	private String textInstructions;
	
	
	/**
	 * This default constructor initializes the other classes and calls on helper
	 * methods to start the game
	 */
	public WorldGUI() {
		super("Escape Adventures of the Grumpy Face");
		
		setFocusable(true);
		setResizable(false);
		setContentPane(getContentPane());
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		layeredPane.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		loadSaveGUI.gameSlots();
		generateStartPausePanel();
		generateFinishPanel();
		ArrayList<ImageIcon> imageIcons = generateImageIcons();
		generateLabels(imageIcons);
		setBounds(imageIcons);
		addComponents();
		
		// add listeners
		addKeyListener(worldMain);
		addFocusListener(worldMain);
		startPausePanel.addComponentListener(worldMain);
		eventPanel.addComponentListener(worldMain);
		messagePanel.addComponentListener(worldMain);
		menuPanel.addComponentListener(worldMain);
		popPanel.addComponentListener(worldMain);
		savedGamesPanel.addComponentListener(worldMain);
		finishPanel.addComponentListener(worldMain);
		setVisible(true);
		pack();
	
	}
	
	
	/**
	 * This method generates a panel that overlays the rest of the game. Pressing the play
	 * button on this panel allows the user to return to the game and reactivate key presses.
	 */
	private void generateStartPausePanel() {
		
		loadSaveGUI.generateLoadSavePanel();
		loadSavePanel = loadSaveGUI.getLoadSavePanel();
		
		textInstructions = "<html><center><font color = white><font style = bold>Escape Adventures of the Grumpy Face:" +
				  "<br><br>You wake up alone,<br>trapped in an empty house<br>with one locked door..." + 
				  "<br><br>Game Controls:<br><br>'w' = move up&nbsp&nbsp&nbsp<br>'s' = move down&nbsp<br>'a' = move left&nbsp<br>'d' = move right" + 
				  "<br><br>Click on Items in the menu for a closer look.<br>The most recently clicked item will remain active<br>and can be used, even after you close it. </font></center></html>";
		
		ImageIcon instructionBG = Icon.createImageIcon("/images/UI/eventsWindow.png");
		gameInstructions = new JLabel(textInstructions);
		gameInstructions.setIcon(instructionBG);
		gameInstructions.setIconTextGap(-instructionBG.getIconWidth()+30);
		gameInstructions.setHorizontalAlignment(SwingConstants.CENTER);
		gameInstructions.setFont(new Font("Courier", Font.PLAIN, 18));
		gameInstructions.setOpaque(false);
		
		playButton = new JLabel(Icon.createImageIcon("/images/UI/messageWindow.png"));
		playButton.setText("<html><font color = white>Click to Play</font></html>");
		playButton.setFont(new Font("Courier", Font.PLAIN, 30));
		playButton.setHorizontalTextPosition(JButton.CENTER);
		playButton.setVerticalTextPosition(JButton.CENTER);
		playButton.addMouseListener(worldMain);
		
		ImageIcon eventsBG = eventsGUI.getEventsBG();
		ImageIcon messageBG = eventsGUI.getMessageBG();
		
		pauseBG.setBounds(0, 0, pauseBGIcon.getIconWidth(), pauseBGIcon.getIconHeight());
		gameInstructions.setBounds(EVENTSWINDOWX, EVENTSWINDOWY, eventsBG.getIconWidth(), eventsBG.getIconHeight());
		loadSavePanel.setBounds(590, 460, 300, 50);
		playButton.setBounds(MESSAGEWINDOWX, MESSAGEWINDOWY, messageBG.getIconWidth(), messageBG.getIconHeight());	
		
		startPausePanel.add(pauseBG, new Integer(BG_LAYER));
		startPausePanel.add(gameInstructions, new Integer(ICON_LAYER));
		startPausePanel.add(loadSavePanel, new Integer(ICON_LAYER));
		startPausePanel.add(playButton, new Integer(ICON_LAYER));
	}

	
	/**
	 * This method generates the panel that displays after the user wins the game.
	 */
	private void generateFinishPanel() {

		finalMessage = new JLabel("<html><center><font color = white><br><br><br><br><br><br><br><br><font size = 40><font style = bold> Success!</font></font>"
				+ "<font color = white><br><br>You have passed the trials and now the door to the void is open.<br><br><br>To be continued...<br><br></font></center></html>");
		finalMessage.setHorizontalAlignment(SwingConstants.CENTER);
		finalMessage.setFont(new Font("Courier", Font.PLAIN, 20));
		finalMessage.setOpaque(false);
		
		JPanel finalPrompt = generateFinalPrompt();
		
		ImageIcon endIcon = Icon.createImageIcon("/images/UI/loadBG.png");
		JLabel endBG = new JLabel(endIcon);
		endBG.setBounds(0, 0, endIcon.getIconWidth(), endIcon.getIconHeight());
		
		JPanel finishContainer = new JPanel(new BorderLayout());
		finishContainer.add(finalMessage, BorderLayout.NORTH);
		finishContainer.add(finalPrompt, BorderLayout.CENTER);
		finishContainer.setOpaque(false);
		finishContainer.setBounds(0, 0, endIcon.getIconWidth(), endIcon.getIconHeight());
		
		finishPanel.add(endBG, new Integer(0));
		finishPanel.add(finishContainer, new Integer(1));
	}
	
	
	/**
	 * This method generates the panel containing the final prompt for the player on whether to
	 * play the game again or close the game window.
	 * @return finalPrompt - a JPanel containing two buttons: one plays the game again and the other closes the window
	 */
	public JPanel generateFinalPrompt() {
		
		JPanel finalPrompt = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		ImageIcon playIcon = Icon.createImageIcon("/images/imagesForMap/face.png");
		ImageIcon closeIcon = Icon.createImageIcon("/images/UI/closeButton.png");
		
		playAgain = new JLabel("<html><font color = white>Play Again?</font></html>");
		playAgain.setIcon(playIcon);
		playAgain.setFont(new Font("Courier", Font.PLAIN, 20));
		playAgain.setHorizontalTextPosition(JButton.CENTER);
		playAgain.setVerticalTextPosition(SwingConstants.BOTTOM);
		playAgain.addMouseListener(worldMain);
		playAgain.setVisible(true);
		
		exitGame = new JLabel("<html><font color = white>Exit Game?</font></html>");
		exitGame.setIcon(closeIcon);
		exitGame.setFont(new Font("Courier", Font.PLAIN, 20));
		exitGame.setHorizontalTextPosition(JButton.CENTER);
		exitGame.setVerticalTextPosition(SwingConstants.BOTTOM);
		exitGame.addMouseListener(worldMain);
		exitGame.setVisible(true);
		
		constraints.gridx = 0;
		constraints.weightx = 2;
		finalPrompt.add(playAgain, constraints);
		
		constraints.gridx = 2;
		finalPrompt.add(exitGame, constraints);
		finalPrompt.setOpaque(false);
		
		return finalPrompt;
	}
		
		
	/**
	 * This method gets the image files via a helper method.
	 */
	private ArrayList<ImageIcon> generateImageIcons() {
		
		ArrayList<ImageIcon> imageIcons = new ArrayList<ImageIcon>();
		ImageIcon bgWorld = Icon.createImageIcon("/images/imagesForMap/WorldMap.png");
		ImageIcon switchIcon = Icon.createImageIcon("/images/imagesForMap/switchIcon.png");
		ImageIcon buttonIcon = Icon.createImageIcon("/images/imagesForMap/buttonIcon.png");
		ImageIcon leverIcon = Icon.createImageIcon("/images/imagesForMap/leverIcon.png");
		ImageIcon prybarIcon = Icon.createImageIcon("/images/items/prybarIcon.png");
		ImageIcon oilIcon = Icon.createImageIcon("/images/items/oilIcon.png");
		ImageIcon menuIcon = Icon.createImageIcon("/images/UI/menuWindow.png");
		ImageIcon doorIcon = Icon.createImageIcon("/images/imagesForMap/door.png");

		imageIcons.add(bgWorld);
		imageIcons.add(switchIcon);
		imageIcons.add(buttonIcon);
		imageIcons.add(leverIcon);
		imageIcons.add(prybarIcon);
		imageIcons.add(oilIcon);
		imageIcons.add(menuIcon);
		imageIcons.add(doorIcon);
		return imageIcons;
	}
	
	
	/**
	 * This method generates labels out of the image icons generated.
	 * @param imageIcons - a list which stores the image icons used in the game map
	 */
	private void generateLabels(ArrayList<ImageIcon> imageIcons) {

		bgWorldLabel = new JLabel("", imageIcons.get(0), JLabel.CENTER);
		switchIconLabel = new JLabel(imageIcons.get(1));
		buttonIconLabel = new JLabel(imageIcons.get(2));
		leverIconLabel = new JLabel(imageIcons.get(3));
		prybarIconLabel = new JLabel(imageIcons.get(4));
		oilIconLabel = new JLabel(imageIcons.get(5));
		menuWindowLabel = new JLabel(imageIcons.get(6));	
		doorLabel = new JLabel(imageIcons.get(7));	
	}
	
	
	/**
	 * This method sets the image bounds for fhen they are added to the main game's layers
	 * @param imageIcons - a list which stores the image icons used in the game map
	 */
	private void setBounds(ArrayList<ImageIcon> imageIcons) {
		
		finishPanel.setBounds(BGWORLDX, BGWORLDY, imageIcons.get(0).getIconWidth(), imageIcons.get(0).getIconHeight());
		startPausePanel.setBounds(BGWORLDX, BGWORLDY, imageIcons.get(0).getIconWidth(), imageIcons.get(0).getIconHeight());
		savedGamesPanel.setBounds(BGWORLDX, BGWORLDY, imageIcons.get(0).getIconWidth(), imageIcons.get(0).getIconHeight());
		bgWorldLabel.setBounds(BGWORLDX, BGWORLDY, imageIcons.get(0).getIconWidth(), imageIcons.get(0).getIconHeight());
		eventPanel.setBounds(EVENTSWINDOWX, EVENTSWINDOWY, eventsGUI.getEventsBG().getIconWidth(), eventsGUI.getEventsBG().getIconHeight());
		messagePanel.setBounds(MESSAGEWINDOWX, MESSAGEWINDOWY, eventsGUI.getMessageBG().getIconWidth(), eventsGUI.getMessageBG().getIconHeight());		
		menuPanel.setBounds(MENUWINDOWX, MENUWINDOWY, imageIcons.get(6).getIconWidth(), imageIcons.get(6).getIconHeight());
		popPanel.setBounds(BGWORLDX, BGWORLDY, imageIcons.get(0).getIconWidth(), imageIcons.get(0).getIconHeight());
		movementPanel.setBounds(movementGUI.getX(), movementGUI.getY(), imageIcons.get(0).getIconWidth(), imageIcons.get(0).getIconHeight());
		switchIconLabel.setBounds(switchLocation.getXCol(), switchLocation.getYRow(), imageIcons.get(1).getIconWidth(), imageIcons.get(1).getIconHeight());
		buttonIconLabel.setBounds(buttonLocation.getXCol(), buttonLocation.getYRow(), imageIcons.get(2).getIconWidth(), imageIcons.get(2).getIconHeight());
		leverIconLabel.setBounds(leverLocation.getXCol(), leverLocation.getYRow(), imageIcons.get(3).getIconWidth(), imageIcons.get(3).getIconHeight());
		prybarIconLabel.setBounds(prybarLocation.getXCol(), prybarLocation.getYRow(), imageIcons.get(4).getIconWidth(), imageIcons.get(4).getIconHeight());
		oilIconLabel.setBounds(oilLocation.getXCol(), oilLocation.getYRow(), imageIcons.get(5).getIconWidth(), imageIcons.get(5).getIconHeight());
		menuWindowLabel.setBounds(MENUWINDOWX, MENUWINDOWY, imageIcons.get(6).getIconWidth(), imageIcons.get(6).getIconHeight());
		doorLabel.setBounds(doorLocation.getXCol(), doorLocation.getYRow(), imageIcons.get(7).getIconWidth(), imageIcons.get(7).getIconHeight());
	}
	
	
	/**
	 * This method adds some of the different components to to the game container
	 */
	private void addComponents() {
		layeredPane.add(bgWorldLabel, new Integer(BG_LAYER));
		layeredPane.add(switchIconLabel, new Integer(ICON_LAYER));
		layeredPane.add(buttonIconLabel, new Integer(ICON_LAYER));
		layeredPane.add(leverIconLabel, new Integer(ICON_LAYER));
		layeredPane.add(oilIconLabel, new Integer(ICON_LAYER));
		layeredPane.add(prybarIconLabel, new Integer(ICON_LAYER));
		layeredPane.add(doorLabel, new Integer(ICON_LAYER));
		layeredPane.add(menuWindowLabel, new Integer(UI_LAYER));
		
		
		layeredPane.add(startPausePanel, new Integer(OVER_LAYER));
		layeredPane.add(savedGamesPanel, new Integer(TOP_LAYER));
		layeredPane.add(finishPanel, new Integer(TOP_LAYER));
		layeredPane.add(eventPanel, new Integer(EVENTS_LAYER));
		layeredPane.add(messagePanel, new Integer(EVENTS_LAYER));
		layeredPane.add(menuPanel, new Integer(MENU_LAYER));
		layeredPane.add(popPanel, new Integer(MENU_LAYER));
		
		layeredPane.add(movementPanel, new Integer(PLAYER_LAYER));
		
		savedGamesPanel.setVisible(false);
		messagePanel.setVisible(false);
		finishPanel.setVisible(false);
		eventPanel.setVisible(false);
		popPanel.setVisible(false);
	
		add(layeredPane);
	}
	
	
	/**
	 * This main method calls the method which will initialize the game gui
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WorldGUI();
			}
		});
	}
	
	
	/**
	 * @return the eventsGUI
	 */
	public EventsGUI getEventsGUI() {
		return eventsGUI;
	}

	/**
	 * @return the menuGUI
	 */
	public MenuGUI getMenuGUI() {
		return menuGUI;
	}

	/**
	 * @return the movementGUI
	 */
	public MovementGUI getMovementGUI() {
		return movementGUI;
	}

	/**
	 * @return the startPausePanel
	 */
	public JLayeredPane getStartPausePanel() {
		return startPausePanel;
	}

	/**
	 * @return the eventPanel
	 */
	public JLayeredPane getEventPanel() {
		return eventPanel;
	}

	/**
	 * @return the messagePanel
	 */
	public JPanel getMessagePanel() {
		return messagePanel;
	}

	/**
	 * @return the menuPanel
	 */
	public JPanel getMenuPanel() {
		return menuPanel;
	}

	/**
	 * @return the popPanel
	 */
	public JLayeredPane getPopPanel() {
		return popPanel;
	}

	/**
	 * @return the prybarIconLabel
	 */
	public JLabel getPrybarIconLabel() {
		return prybarIconLabel;
	}

	/**
	 * @return the oilIconLabel
	 */
	public JLabel getOilIconLabel() {
		return oilIconLabel;
	}

	/**
	 * @return the playButton
	 */
	public JLabel getPlayButton() {
		return playButton;
	}

	/**
	 * @return the loadSave
	 */
	public LoadSaveGUI getLoadSaveGUI() {
		return loadSaveGUI;
	}
	
	/**
	 * @return the savedGamesPanel
	 */
	public JLayeredPane getSavedGamesPanel() {
		return savedGamesPanel;
	}

	/**
	 * @return the worldMain
	 */
	public WorldMain getWorldMain() {
		return worldMain;
	}

	/**
	 * @return the finishPanel
	 */
	public JLayeredPane getFinishPanel() {
		return finishPanel;
	}

	/**
	 * @return the playAgain
	 */
	public JLabel getPlayAgain() {
		return playAgain;
	}

	/**
	 * @return the exitGame
	 */
	public JLabel getExitGame() {
		return exitGame;
	}

	/**
	 * @return the doorLabel
	 */
	public JLabel getDoorLabel() {
		return doorLabel;
	}
	
	/**
	 * @return the gameInstructions
	 */
	public JLabel getGameInstructions() {
		return gameInstructions;
	}


	/**
	 * @return the textInstructions
	 */
	public String getTextInstructions() {
		return textInstructions;
	}
	
}
