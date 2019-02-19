// import java libraries and declare package
package gui;
import logic.*;
import connect.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.security.*;

/**
 * Generate class called LoadSave for saving and loading a copy of a given
 * saved game state.
 * @author Leia Astorga, Grace Lan, Sehajveer Bring
 *
 */
public class LoadSaveGUI {
	
	public static final long serialVersionUID = 0412171700;

	// declare and initialize instance variables
	private WorldGUI worldGUI;
	private LoadSaveMain loadSaveMain;
	
	private JLayeredPane savedGamesPanel = new JLayeredPane();
	private JPanel loadSavePanel;
	private JLabel loadButton;
	private JLabel saveButton;
	
	private ImageIcon gameSlot = Icon.createImageIcon("/images/UI/messageWindow.png");
	private ImageIcon closeButtonIcon = Icon.createImageIcon("/images/UI/closeButton.png");
	private ImageIcon loadBGIcon = Icon.createImageIcon("/images/UI/loadBG.png");
	private ImageIcon loadIcon = Icon.createImageIcon("/images/UI/load.png");
	private ImageIcon saveIcon = Icon.createImageIcon("/images/UI/save.png");
	private ImageIcon loadButtonIcon = Icon.createImageIcon("/images/UI/loadButton.png");
	private ImageIcon saveButtonIcon = Icon.createImageIcon("/images/UI/saveButton.png");
	
	private ArrayList<String> loadArray = new ArrayList<String>();
	private ArrayList<String> saveArray = new ArrayList<String>();
	private String[] gameNames = new String[4];
	private String[]  gameDates = new String[4];
	
	private JLabel[] gameSlotArray = new JLabel[4];
	private JLabel closeButton = new JLabel(closeButtonIcon);	
	
	private JLabel loadBG = new JLabel(loadBGIcon);
	private JLabel slotsLabel = new JLabel();
	
	private boolean savePage;

	private URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
	private String locString = location.getPath() + "savedGames/"; 
	private String gameDirectory = locString.replaceAll("%20", " ");
	
	/**
	 * Default Constructor and method which generates a folder for saving games.
	 * @param worldGUI - an instance of the worldGUI class so this class can modify various aspects of the game map
	 */
	public LoadSaveGUI(WorldGUI worldGUI) {
		this.worldGUI = worldGUI;
		loadSaveMain = new LoadSaveMain(this);
		
		File checkDir = new File(gameDirectory);
		if (!checkDir.exists()) {
			new File(gameDirectory).mkdir();
		}
	}
	
	
	/**
	 * This method generates the panel which will contain the buttons that prompt loading or
	 * saving the game.
	 */
	public void generateLoadSavePanel() {
		
		loadSavePanel = new JPanel(new GridLayout(1,2));
		loadSavePanel.setOpaque(false);
		
		loadButton = new JLabel("");
		loadButton.setIcon(loadButtonIcon);
		loadButton.setFont(new Font("Courier", Font.PLAIN, 15));
		loadButton.setHorizontalTextPosition(JButton.CENTER);
		loadButton.setVerticalTextPosition(JButton.CENTER);
		loadButton.addMouseListener(loadSaveMain);
		
		saveButton = new JLabel("");
		saveButton.setIcon(saveButtonIcon);
		saveButton.setFont(new Font("Courier", Font.PLAIN, 15));
		saveButton.setHorizontalTextPosition(JButton.CENTER);
		saveButton.setVerticalTextPosition(JButton.CENTER);
		saveButton.addMouseListener(loadSaveMain);
		
		loadSavePanel.add(loadButton);
		loadSavePanel.add(saveButton);
	}


	/**
	 * This method generates a panel that overlays the rest of the game. Pressing the play
	 * button on this panel allows the user to return to the game and reactivate key presses.
	 */
	public void gameSlots() {
		updateGameSlots();
		
		for (int counter = 0; counter < gameSlotArray.length; counter++) {
			gameSlotArray[counter] = new JLabel("<html><font color = white>Game Name: " + gameNames[counter] + 
												 "<br>Save Date: " + gameDates[counter] + "</font></html>");
			gameSlotArray[counter].setIcon(gameSlot);
			gameSlotArray[counter].setFont(new Font("Courier", Font.PLAIN, 20));
			gameSlotArray[counter].setHorizontalAlignment(SwingConstants.LEFT);
			gameSlotArray[counter].setHorizontalTextPosition(SwingConstants.CENTER);
			gameSlotArray[counter].setVerticalTextPosition(SwingConstants.CENTER);
			gameSlotArray[counter].addMouseListener(loadSaveMain);
		}
		
		closeButton.addMouseListener(loadSaveMain);
		slotsLabel.setBounds(20, 25, loadIcon.getIconWidth(), loadIcon.getIconHeight());
		loadBG.setBounds(0, 0, loadBGIcon.getIconWidth(), loadBGIcon.getIconHeight());
		gameSlotArray[0].setBounds(120, 25, gameSlot.getIconWidth(), gameSlot.getIconHeight());
		gameSlotArray[1].setBounds(120, 200, gameSlot.getIconWidth(), gameSlot.getIconHeight());
		gameSlotArray[2].setBounds(120, 375, gameSlot.getIconWidth(), gameSlot.getIconHeight());
		gameSlotArray[3].setBounds(120, 550, gameSlot.getIconWidth(), gameSlot.getIconHeight());
		closeButton.setBounds(1150, 25, closeButtonIcon.getIconWidth(), closeButtonIcon.getIconHeight());
		
		savedGamesPanel.add(slotsLabel, new Integer(0));
		savedGamesPanel.add(loadBG, new Integer(0));
		savedGamesPanel.add(gameSlotArray[0], new Integer(1));
		savedGamesPanel.add(gameSlotArray[1], new Integer(1));
		savedGamesPanel.add(gameSlotArray[2], new Integer(1));
		savedGamesPanel.add(gameSlotArray[3], new Integer(1));
		savedGamesPanel.add(closeButton, new Integer(2));
		
	}


	/**
	 * Source: https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
	 * This method takes the existing game values and save them to an array.
	 * @param fileToSave - the name of the file to be saved
	 */
	public void saveGameValues(String gameName) {
		
		MovementGUI movementGUI = worldGUI.getMovementGUI();
		EventsGUI eventsGUI = worldGUI.getEventsGUI();
		EventsLogic eventsLogic = worldGUI.getEventsGUI().getEventsMain().getEventsLogic();
		MenuLogic menuLogic = worldGUI.getMenuGUI().getMenuLogic();
		WorldLogic worldLogic = worldGUI.getWorldMain().getWorldLogic();
		
		String playerX = new Integer(movementGUI.getX()).toString();
		String playerY = new Integer(movementGUI.getY()).toString();
		String playThrough = new Integer(worldGUI.getWorldMain().getPlayThrough()).toString();
		
		String boxOpen = String.valueOf(eventsGUI.getBoxOpen());
		String cleanedLever = String.valueOf(eventsGUI.getCleanedLever());
		
		String switchSetting = String.valueOf(eventsLogic.getSwitchSetting());
		String buttonSetting = String.valueOf(eventsLogic.getButtonSetting());
		String leverSetting = String.valueOf(eventsLogic.getLeverSetting());
		String paperFound = String.valueOf(eventsLogic.getPaperFound());
		
		String gotOil = String.valueOf(menuLogic.getGotOil());
		String gotPrybar = String.valueOf(menuLogic.getGotPrybar());
		String gameWon = String.valueOf(worldLogic.getGameWon());
		
		DateFormat gameTime = new SimpleDateFormat("MM/dd/yy HH:mm");
		Date dateTime = new Date();
		
		saveArray.add(playerX);
		saveArray.add(playerY);
		saveArray.add(playThrough);
		saveArray.add(boxOpen);
		saveArray.add(cleanedLever);
		saveArray.add(switchSetting);
		saveArray.add(buttonSetting);
		saveArray.add(leverSetting);
		saveArray.add(paperFound);
		saveArray.add(gotOil);
		saveArray.add(gotPrybar);
		saveArray.add(gameWon);
		saveArray.add("Name: " + gameName);
		saveArray.add("Date: " + gameTime.format(dateTime));
	
	}
	
	
	/**
	 * This method takes the values obtained from the read file and
	 * updates the game settings according to these saved values.
	 */
	public void setGameProgress() {
		
		EventsGUI eventsGUI = worldGUI.getEventsGUI();
		EventsLogic eventsLogic = worldGUI.getEventsGUI().getEventsMain().getEventsLogic();
		MenuLogic menuLogic = worldGUI.getMenuGUI().getMenuLogic();
		WorldLogic worldLogic = worldGUI.getWorldMain().getWorldLogic();
		
		try {
			eventsGUI.setBoxOpen(parseBoolean(loadArray.get(3)));
			eventsGUI.setCleanedLever(parseBoolean(loadArray.get(4)));
			eventsGUI.setButtonSequence(0);
			
			eventsLogic.setSwitchSetting(parseBoolean(loadArray.get(5)));
			eventsLogic.setButtonSetting(parseBoolean(loadArray.get(6)));
			eventsLogic.setLeverSetting(parseBoolean(loadArray.get(7)));
			eventsLogic.setPaperFound(parseBoolean(loadArray.get(8)));
			
			menuLogic.setGotOil(parseBoolean(loadArray.get(9)));
			menuLogic.setGotPrybar(parseBoolean(loadArray.get(10)));
			worldLogic.setGameWon(parseBoolean(loadArray.get(11)));
			
			updatePlayerPosition();
			updateMap();
			updateSwitch();
			updateButton();
			updateLever();
			
		} catch (InvalidParameterException ipExcept) {
			JOptionPane.showMessageDialog(new JFrame(), "Error: Parameter Could Not Be Parsed to Boolean.\nFile Corrupted. Load Game Incomplete.");
		}
		
	}
	
	
	/**
	 * This method updates the game slots in the game depending on
	 * the current saved files that exist in the directory.
	 */
	public void updateGameSlots() {
		File[] gameFiles = searchDirectory();
		
		if (savePage) {
			slotsLabel.setIcon(saveIcon);
		} else {
			slotsLabel.setIcon(loadIcon);
		}
		
		for (int index = 0; index < gameFiles.length; index++) {
			try {
				if (gameFiles[index] == null) {
					throw new FileNotFoundException("");
				}
				
				BufferedReader myReader = new BufferedReader(new FileReader(gameFiles[index]));
				while (myReader.ready()) {
					String line = myReader.readLine();
					if (line.contains("Name: ")) {
						String gameName = line.substring(6);
						gameNames[index] = gameName;
						if (gameName == null) {
							throw new FileNotFoundException("");
						}
					}
					
					if (line.contains("Date: ")) {
						String gameTime = line.substring(6);
						gameDates[index] = gameTime;
						if (gameTime == null) {
							throw new FileNotFoundException("");
						}
					}
				}
				myReader.close();
			} catch (FileNotFoundException fExcept) {
				gameNames[index] = ("emptyGame" + Integer.toString(index + 1));
				gameDates[index] = ("00/00/00 --:--");
				continue;
			} catch (IOException iExcept) {
				System.out.println("Error: IO System Problem. Could Not Read File.");
			}
		}
	}
	
	
	/**
	 * This method updates the labels attached to each game slot button.
	 */
	public void refreshGameSlots() {
		for (int counter = 0; counter < gameSlotArray.length; counter++) {
			gameSlotArray[counter].setText("<html><font color = white>Game Name: " + gameNames[counter] + 
										   "<br><br>Saved: " + gameDates[counter] + "</font></html>");
		}
	}
	
	
	/**
	 * Source: http://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder
	 * This method searches the current directory for all existing game files saved. The game values
	 * can then be used to update the existing game slots listed in the saved games panel.
	 * @return gameFiles - array of the matching game files found in the current directory
	 */
	private File[] searchDirectory() {
		File directoryFile = new File(gameDirectory);
		File[] filesFound = directoryFile.listFiles();
		File[] gameFiles = new File[4];
		
		try {
			for (int index = 0; index < filesFound.length; index++) {
				
				String filename = filesFound[index].getName();
				
				if (filename.equals("game1.game")) {
					gameFiles[0] = filesFound[index];
				} else if (filename.equals("game2.game")) {
					gameFiles[1] = filesFound[index];
				} else if (filename.equals("game3.game")) {
					gameFiles[2] = filesFound[index];
				} else if (filename.equals("game4.game")) {
					gameFiles[3] = filesFound[index];
				}
			
			}
			
		} catch (NullPointerException nExcept) {
			JOptionPane.showMessageDialog(new JFrame(), "ERROR: Could Not Create SavedGames Folder.\nExiting Game.", "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		return gameFiles;
	}
	
	
	/**
	 * This method updates the player's current position based on the loaded game settings
	 */
	public void updatePlayerPosition() {
		
		try {
			// apply loaded player location to current game
			worldGUI.getMovementGUI().setX(Integer.parseInt(loadArray.get(0)));
			worldGUI.getMovementGUI().setY(Integer.parseInt(loadArray.get(1)));
			worldGUI.getWorldMain().setPlayThrough(Integer.parseInt(loadArray.get(2)));
		} catch (NumberFormatException nExcept) {
			JOptionPane.showMessageDialog(new JFrame(), "Error: Parameter Could Not Be Parsed to Integer. \nFile Corrupted. Load Game Incomplete.");
		}
	}
	
	
	/**
	 * This method updates the current game state's map based on the loaded game settings
	 */
	private void updateMap() {
		ArrayList<JLabel> popImages = worldGUI.getMenuGUI().getPopImages();
		EventsLogic eventsLogic = worldGUI.getEventsGUI().getEventsMain().getEventsLogic();
		MenuGUI menuGUI = worldGUI.getMenuGUI();
		MenuLogic menuLogic = worldGUI.getMenuGUI().getMenuLogic();
		WorldMain worldMain = worldGUI.getWorldMain();
		MovementGUI movementGUI = worldGUI.getMovementGUI();
		
		popImages.get(0).setVisible(false);
		popImages.get(1).setVisible(false);
		popImages.get(2).setVisible(false);
		menuGUI.getPopContainer().setVisible(false);
		menuGUI.getMenuLogic().setItemSelected("");
		eventsLogic.setCloseEvents(true);
		
		if (worldMain.getPlayThrough() < 2) {
			movementGUI.setPlayerIcon(movementGUI.getPlayerImage());
			worldMain.getCheckBounds().livePlayer();
		} else {
			movementGUI.setPlayerIcon(movementGUI.getGhostImage());
			worldMain.getCheckBounds().deadPlayer();
		}
		
		// set icons on game map based on whether they have been found already
		worldGUI.getOilIconLabel().setVisible(!menuLogic.getGotOil());
		worldGUI.getPrybarIconLabel().setVisible(!menuLogic.getGotPrybar());
	}
	
	
	/**
	 * This method updates the current game state's switch event based on the loaded game settings
	 */
	private void updateSwitch() {
		EventsGUI eventsGUI = worldGUI.getEventsGUI();
		EventsLogic eventsLogic = worldGUI.getEventsGUI().getEventsMain().getEventsLogic();
		
		if (eventsLogic.getSwitchSetting()) {
			eventsGUI.getSwitchImage().setIcon(eventsGUI.getSwitchIconOn());
		} else {
			eventsGUI.getSwitchImage().setIcon(eventsGUI.getSwitchIconOff());
		}
		
		if (eventsGUI.getBoxOpen()) {
			if (eventsLogic.getPaperFound()) {
				eventsGUI.getBoxImage().setIcon(eventsGUI.getSwitchIconEmpty());
			} else {
				eventsGUI.getBoxImage().setIcon(eventsGUI.getSwitchIconOpen());
			}
		} else {
			eventsGUI.getBoxImage().setIcon(eventsGUI.getSwitchIconClosed());
		}
	}
	
	
	/**
	 * This method updates the current game state's button event based on the loaded game settings
	 */
	private void updateButton() {
		EventsMain eventsMain = worldGUI.getEventsGUI().getEventsMain();
		if (eventsMain.getEventsLogic().getButtonSetting()) {
			eventsMain.completeButtons();
		} else {
			eventsMain.resetButtons();
		}
	}
	
	
	/**
	 * This method updates the current game state's lever event based on the loaded game settings
	 */
	private void updateLever() {
		EventsGUI eventsGUI = worldGUI.getEventsGUI();
		if (eventsGUI.getCleanedLever()) {
			if (eventsGUI.getEventsMain().getEventsLogic().getLeverSetting()) {
				eventsGUI.getLeverImage().setIcon(eventsGUI.getLeverIconOn());
			} else {
				eventsGUI.getLeverImage().setIcon(eventsGUI.getLeverIconClean());
			}
		} else {
			eventsGUI.getLeverImage().setIcon(eventsGUI.getLeverIconRusty());
		}
	}
	
		
	/**
	 * Source: http://beginnersbook.com/2014/07/convert-string-to-boolean-primitive-in-java/
	 * This method parses a string parameter into a boolean
	 * @param someText - string to be parsed into a boolean
	 * @return true or false
	 */
	private boolean parseBoolean(String someText) throws InvalidParameterException {
		
		boolean tempBoolean = false;
		
		if (someText.equalsIgnoreCase("true")) {
			tempBoolean = true;
		} else if (someText.equalsIgnoreCase("false")) {
			tempBoolean = false;
		} else {
			throw new InvalidParameterException("");
		}

		return tempBoolean;
	}
	
	
	/**
	 * @return the loadSaveMain
	 */
	public LoadSaveMain getLoadSaveMain() {
		return loadSaveMain;
	}

	/**
	 * @return the loadSavePanel
	 */
	public JPanel getLoadSavePanel() {
		return loadSavePanel;
	}

	/**
	 * @param loadSavePanel the loadSavePanel to set
	 */
	public void setLoadSavePanel(JPanel loadSavePanel) {
		this.loadSavePanel = loadSavePanel;
	}
	
	/**
	 * @return the worldGUI
	 */
	public WorldGUI getWorldGUI() {
		return worldGUI;
	}

	/**
	 * @return the savedGamesPanel
	 */
	public JLayeredPane getSavedGamesPanel() {
		return savedGamesPanel;
	}
	
	/**
	 * @return the loadButton
	 */
	public JLabel getLoadButton() {
		return loadButton;
	}

	/**
	 * @return the saveButton
	 */
	public JLabel getSaveButton() {
		return saveButton;
	}

	/**
	 * @return the gameSlotArray
	 */
	public JLabel[] getGameSlotArray() {
		return gameSlotArray;
	}

	/**
	 * @return the loadArray
	 */
	public ArrayList<String> getLoadArray() {
		return loadArray;
	}

	/**
	 * @param loadArray the loadArray to set
	 */
	public void setLoadArray(ArrayList<String> loadArray) {
		this.loadArray = loadArray;
	}
	
	/**
	 * @return the saveArray
	 */
	public ArrayList<String> getSaveArray() {
		return saveArray;
	}
	
	/**
	 * @param saveArray the saveArray to set
	 */
	public void setSaveArray(ArrayList<String> saveArray) {
		this.saveArray = saveArray;
	}

	/**
	 * @return the gameDirectory
	 */
	public String getGameDirectory() {
		return gameDirectory;
	}

	/**
	 * @return the closeButton
	 */
	public JLabel getCloseButton() {
		return closeButton;
	}
	
	/**
	 * @return savePage - true/false whether the listener works to save file or load file
	 */
	public boolean getSavePage() {
		return savePage;
	}
	
	/**
	 * @param savePage - set the savePage
	 */
	public void setSavePage(boolean savePage) {
		this.savePage = savePage;
	}


}