// import java libraries and declare package
package connect;
import gui.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Generate class called LoadSaveMain which acts as an action listener for the gui components
 * of the loadSave class.
 * @author Leia Astorga, Grace Lan
 */
public class LoadSaveMain implements MouseListener {
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and initialize instance variables
	private LoadSaveGUI loadSaveGUI;
	private boolean tempHidePause = false;
	
	public LoadSaveMain(LoadSaveGUI loadSaveGUI) {
		this.loadSaveGUI = loadSaveGUI;
	}
	
	/**
	 * Sources: http://www.avajava.com/tutorials/lessons/how-do-i-read-a-string-from-a-file-line-by-line.html
	 *          https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
	 * This method respond to mouse clicks applied to the buttons in applied to the load save feature.
	 * The player can decide whether to load or save a game. Once in the saved games panel, the user can
	 * click on their chosen game or hide the panel and resume game play.
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseClicked(MouseEvent mouseEvent) {
		JLabel whichLabel = (JLabel)mouseEvent.getSource();
		JLayeredPane startPausePanel = loadSaveGUI.getWorldGUI().getStartPausePanel();
		JLabel[] gameSlotArray = loadSaveGUI.getGameSlotArray();
		String fileToRead = null;
		
		for (int index = 0; index < gameSlotArray.length; index++) {
			if (whichLabel == gameSlotArray[index]) {
				fileToRead = loadSaveGUI.getGameDirectory() + "game" + (index + 1) + ".game";
				if (loadSaveGUI.getSavePage()) {
					saveGame(fileToRead);
				} else {
					loadGame(fileToRead);
				}
			}
		}
		
		if (whichLabel == loadSaveGUI.getCloseButton()) {
			tempHidePause = false;
			startPausePanel.setVisible(true);
			loadSaveGUI.getSavedGamesPanel().setVisible(false);
		
		} else if (whichLabel == loadSaveGUI.getLoadButton() || whichLabel == loadSaveGUI.getSaveButton()) {
			
			if (whichLabel == loadSaveGUI.getLoadButton()) {
				loadSaveGUI.setLoadArray(new ArrayList<String>());
				loadSaveGUI.setSavePage(false);
			} else if (whichLabel == loadSaveGUI.getSaveButton()) {
				loadSaveGUI.setSaveArray(new ArrayList<String>());
				loadSaveGUI.setSavePage(true);
			}
			
			tempHidePause = true;
			startPausePanel.setVisible(false);
			loadSaveGUI.updateGameSlots();
			loadSaveGUI.getSavedGamesPanel().setVisible(true);
		}
		
	}
	
		
	public void loadGame(String fileToRead) {
		try {
			
			if (fileToRead != null) {
				
				BufferedReader myReader = new BufferedReader(new FileReader(fileToRead));
				while (myReader.ready()) {
					String line = myReader.readLine();
					loadSaveGUI.getLoadArray().add(line);
				}
				
				myReader.close();
				loadSaveGUI.setGameProgress();
				JOptionPane.showMessageDialog(new JFrame(), "Game successfully loaded.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
			}
				
		} catch (FileNotFoundException fExcept) {
			JOptionPane.showMessageDialog(new JFrame(), "Error: No Game Found In Chosen Slot.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
	
		} catch (IOException iExcept) {
			System.out.println("Error: IO System Problem. Could Not Read File.");
		}

	}
	
	
	/**
	 * This method saves a game from the existing game state.
	 * @param fileToRead - the path of the file
	 */
	public void saveGame(String fileToRead) {
		try {
			
			if (fileToRead != null) {
				JFrame saveFrame = new JFrame();
				String gameName = (String)JOptionPane.showInputDialog(saveFrame, "What Should We Call This Game?", "Enter Game Name",
						JOptionPane.PLAIN_MESSAGE, null, null, "...");
			
				if (gameName != null) {
					BufferedWriter writeFile = new BufferedWriter(new FileWriter(fileToRead));

					loadSaveGUI.setSaveArray(new ArrayList<String>());
					loadSaveGUI.saveGameValues(gameName);
				
					for (int counter = 0; counter < loadSaveGUI.getSaveArray().size(); counter++) {
						writeFile.write(loadSaveGUI.getSaveArray().get(counter) + "\n");
					}
					writeFile.close();
					loadSaveGUI.updateGameSlots();
					loadSaveGUI.refreshGameSlots();
					JOptionPane.showMessageDialog(new JFrame(), "Game successfully saved.", "Save Game", JOptionPane.INFORMATION_MESSAGE);
				}
			}
				
		} catch (IOException iExcept) {
			JOptionPane.showMessageDialog(new JFrame(), "Error: IO System Problem. Could Not Create File.", "Save Game", JOptionPane.INFORMATION_MESSAGE);
		}

	}
	
	
	/**
	 * These unused methods are required for MouseListener to function
	 * @param mouseEvent - the mouse listener
	 */
	public void mouseReleased(MouseEvent mouseEvent) {}
	public void mouseEntered(MouseEvent mouseEvent) {}
	public void mouseExited(MouseEvent mouseEvent) {}
	public void mousePressed(MouseEvent mouseEvent) {}

	/**
	 * @return the tempHidePause
	 */
	public boolean getTempHidePause() {
		return tempHidePause;
	}
	
}