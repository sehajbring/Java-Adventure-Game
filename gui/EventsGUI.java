// import java libraries and declare package
package gui;
import connect.*;
import java.awt.*;
import javax.swing.*;

/** 
 * Generate EventsGUI class which will handle and display the GUI for
 * the game events.
 * @author Leia Astorga, Grace Lan, Sehajveer Bring
 */
public class EventsGUI {
	
	public static final long serialVersionUID = 0412171700;
	
	// declare and initialize constants
	private final static String SWITCH = "switch event panel";
	private final static String BUTTON = "button event panel";
	private final static String LEVER = "lever event panel";
	
	// declare and initialize instance variables
	private EventsMain eventsMain = new EventsMain(this);
	
	private ImageIcon messageBG;
	private ImageIcon eventsBG = Icon.createImageIcon("/images/UI/eventsWindow.png");
	private ImageIcon closeButton = Icon.createImageIcon("/images/UI/closeButton.png");
	private ImageIcon btnBlue = Icon.createImageIcon("/images/events/buttonBlue.png");
	private ImageIcon btnRed = Icon.createImageIcon("/images/events/buttonRed.png");
	private ImageIcon btnGreen = Icon.createImageIcon("/images/events/buttonGreen.png");
	private ImageIcon btnGray = Icon.createImageIcon("/images/events/buttonGray.png");
	private ImageIcon btnBright = Icon.createImageIcon("/images/events/buttonBright.png");
	

	private JLabel messageWindow;
	private JLabel eventsWindow;
	private JLabel closeEventButton = new JLabel(closeButton);
	
	// generate panels for events
	private JLayeredPane eventContainer = new JLayeredPane();
	private JPanel eventPanel = new JPanel(new CardLayout());
	private JPanel switchPanel = new JPanel(new GridBagLayout());
	private JPanel buttonPanel = new JPanel(new GridLayout(4, 5));
	private JPanel leverPanel = new JPanel(new GridBagLayout());
	private JPanel messagePanel = new JPanel(new BorderLayout());
	
	// instance variables for button event
	private JButton[] buttonArray = new JButton[21];
	private int buttonSequence = 0;
	
	// instance variables for switch event
	private boolean boxOpen = false;
	private JLabel switchImage = new JLabel();
	private JLabel boxImage = new JLabel();
	private ImageIcon switchIconClosed;
	private ImageIcon switchIconOpen;
	private ImageIcon switchIconEmpty;
	private ImageIcon switchIconOff;
	private ImageIcon switchIconOn;
	
	// instance variables for lever event
	private boolean cleanedLever = false;
	private JLabel leverImage = new JLabel();
	private ImageIcon leverIconRusty;
	private ImageIcon leverIconClean;
	private ImageIcon leverIconOn;
	
	
	/**
	 * This default constructor adds the event panels to the main event container
	 */
	public EventsGUI() {
		// ******** << DEBUGGING MENU FOR EVENTS ONLY >> ******** //
		// Source: https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
		JPanel eventMenuPane = new JPanel(new FlowLayout());
        String menuOptions[] = { SWITCH, BUTTON, LEVER };
        JComboBox<String> eventMenu = new JComboBox<>(menuOptions);
        eventMenu.setEditable(false);
        eventMenu.addItemListener(eventsMain);
        eventMenuPane.add(eventMenu);
        eventMenuPane.setOpaque(false);
        eventMenuPane.setVisible(false); // REMOVE THIS LINE TO ACTIVATE EVENT DEBUGGING
        // ^^*****************************************************************^^ //
		
        // call methods which generate each event space's GUI
        createImageFiles();
		createSwitchSpace();
		createButtonGrid();
		createLeverSpace();
		createMessageWindow();
		
		// add each event's panel to the event main panel
		eventPanel.setOpaque(false);
		eventPanel.add(switchPanel, SWITCH);
		eventPanel.add(buttonPanel, BUTTON);
		eventPanel.add(leverPanel, LEVER);

		// add the event main panel and its background to the event container
		eventsWindow = new JLabel(eventsBG);
		eventsWindow.setBounds(0, 0, getEventsBG().getIconWidth(), getEventsBG().getIconHeight());
		eventPanel.setBounds(0, 0, getEventsBG().getIconWidth(), getEventsBG().getIconHeight());
		eventMenuPane.setBounds(150, 450, 300, 30);
		
		// add the close button for events
		closeEventButton.addMouseListener(eventsMain);
		closeEventButton.setBounds(540, 0, closeButton.getIconWidth(), closeButton.getIconHeight()); // close button position
		     
		// assemble event components in the main event container
		eventContainer.add(eventsWindow, new Integer(0));
		eventContainer.add(eventPanel, new Integer(1));
		eventContainer.add(closeEventButton, new Integer(2));
		eventContainer.add(eventMenuPane, new Integer(2));

		eventContainer.setOpaque(false);
	}


	/**
	 * This method gets the image files via a helper method.
	 */
	private void createImageFiles() {
		switchIconClosed = Icon.createImageIcon("/images/events/switchImageClosed.png");
		switchIconOpen = Icon.createImageIcon("/images/events/switchImageOpen.png");
		switchIconEmpty = Icon.createImageIcon("/images/events/switchImageEmpty.png");
		switchIconOff = Icon.createImageIcon("/images/events/switchImageOff.png");
		switchIconOn = Icon.createImageIcon("/images/events/switchImageOn.png");
		leverIconClean = Icon.createImageIcon("/images/events/leverImageClean.png");
		leverIconRusty = Icon.createImageIcon("/images/events/leverImageRusty.png");
		leverIconOn = Icon.createImageIcon("/images/events/leverImageOn.png");
	}
	
	
	
	/**
	 * This method generates the switch and box image for the switch event.
	 */
	private void createSwitchSpace() {
		// create constraints for switchPanel layout (required)
		GridBagConstraints constraints = new GridBagConstraints();
		
		// display the image icon for the box as empty or not depending on whether the player has already found the paper
		if (boxOpen && eventsMain.getEventsLogic().getPaperFound()) {
			boxImage.setIcon(switchIconEmpty);
		} else if (boxOpen && !eventsMain.getEventsLogic().getPaperFound()) {
			boxImage.setIcon(switchIconOpen);
		} else if (!boxOpen) {
			boxImage.setIcon(switchIconClosed);
		}
		
		// display the image icon as on or off depending on its current state before being clicked again
		if (eventsMain.getEventsLogic().getSwitchSetting()) {
			switchImage.setIcon(switchIconOn);
		} else {
			switchImage.setIcon(switchIconOff);
		}
		
		// add listeners to the container
		switchImage.addMouseListener(eventsMain);
		boxImage.addMouseListener(eventsMain);
		boxImage.addMouseMotionListener(eventsMain);
		
		// add each image to the container
		constraints.gridy = 0;
		switchPanel.add(boxImage, constraints);
		
		constraints.gridy = 1;
		switchPanel.add(switchImage, constraints);
		
		switchPanel.setOpaque(false);
	}
	
	
	/**
	 * This method generates a 5x4 grid of buttons that the user must press in
	 * the correct sequence as written on a piece of paper found in the house
	 */
	public void createButtonGrid() {
		buttonPanel.setOpaque(false);
		
		// designs array of buttons
		for (int counter = 1; counter <= 20; counter++) {
			buttonArray[counter] = new JButton();
			buttonArray[counter].setOpaque(false);
			buttonArray[counter].setVisible(true);
			buttonArray[counter].setBorderPainted(false);
			buttonArray[counter].setHorizontalTextPosition(JButton.CENTER);
			buttonArray[counter].setVerticalTextPosition(SwingConstants.CENTER);
			buttonArray[counter].setFont(new Font("Courier", Font.PLAIN, 15));
			buttonArray[counter].setText("<html><font color = white>" + Integer.toString(counter) + "</font></html>");
			// add action listener to each button
			buttonArray[counter].addActionListener(eventsMain);
			// add each button to the container
			buttonPanel.add(buttonArray[counter]);
		}
		
		// set colour for ach row of buttons
		for (int counter = 1; counter <= 20; counter++) {
			if (counter >= 1 && counter < 6) {
				buttonArray[counter].setIcon(btnBlue);
			} else if (counter > 5 && counter < 11) {
				buttonArray[counter].setIcon(btnGreen);
			} else if (counter > 10 && counter < 16) {
				buttonArray[counter].setIcon(btnRed);
			} else if (counter > 15 && counter < 21) {
				buttonArray[counter].setIcon(btnGray);
			}
		}
	}
	
	
	/**
	 * This method generates the lever image for the lever event.
	 */
	private void createLeverSpace() {
		GridBagConstraints leverConstraints = new GridBagConstraints();
		
		if (cleanedLever) {
			leverImage.setIcon(leverIconClean);
		} else if (!cleanedLever) {
			leverImage.setIcon(leverIconRusty);
		} else if (eventsMain.getEventsLogic().getLeverSetting()) {
			leverImage.setIcon(leverIconOn);
		}
		
		leverImage.addMouseListener(eventsMain);
		leverImage.addMouseMotionListener(eventsMain);
		
		// add the lever image to the container
		leverPanel.add(leverImage, leverConstraints);
		leverPanel.setOpaque(false);
	}
	

	/**
	 * This method generates the message window which will display information for the user
	 * during game events.
	 */
	private void createMessageWindow() {
		messageBG = Icon.createImageIcon("/images/UI/messageWindow.png");
		messageWindow = new JLabel(messageBG);
		
		messageWindow = new JLabel("");
		messageWindow.setFont(new Font("Courier", Font.PLAIN, 20));
		messageWindow.setIcon(messageBG);
		messageWindow.setIconTextGap(-messageBG.getIconWidth()+10);
		messageWindow.setHorizontalTextPosition(0);
		messageWindow.setOpaque(false);
				
		messagePanel.add(messageWindow, BorderLayout.CENTER);
		messagePanel.setOpaque(false);
	}
	

	/**
	 * 
	 * @return the Switch string
	 */
	public String getSWITCH() {
		return SWITCH;
	}
	
	/**
	 * 
	 * @return the Button string
	 */
	public String getBUTTON() {
		return BUTTON;
	}
	
	/**
	 * 
	 * @return the Lever string
	 */
	public String getLEVER() {
		return LEVER;
	}
	
	/**
	 * @return the eventsMain
	 */
	public EventsMain getEventsMain() {
		return eventsMain;
	}

	/**
	 * @param eventsMain the eventsMain to set
	 */
	public void setEventsMain(EventsMain eventsMain) {
		this.eventsMain = eventsMain;
	}

	/**
	 * @return the eventsBG
	 */
	public ImageIcon getEventsBG() {
		return eventsBG;
	}

	/**
	 * @param eventsBG the eventsBG to set
	 */
	public void setEventsBG(ImageIcon eventsBG) {
		this.eventsBG = eventsBG;
	}

	/**
	 * @return the messageBG
	 */
	public ImageIcon getMessageBG() {
		return messageBG;
	}

	/**
	 * @param messageBG the messageBG to set
	 */
	public void setMessageBG(ImageIcon messageBG) {
		this.messageBG = messageBG;
	}

	/**
	 * @return the btnBlue
	 */
	public ImageIcon getBtnBlue() {
		return btnBlue;
	}

	/**
	 * @return the btnRed
	 */
	public ImageIcon getBtnRed() {
		return btnRed;
	}

	/**
	 * @return the btnGreen
	 */
	public ImageIcon getBtnGreen() {
		return btnGreen;
	}

	/**
	 * @return the btnGray
	 */
	public ImageIcon getBtnGray() {
		return btnGray;
	}
	
	/**
	 * @return the btnBright
	 */
	public ImageIcon getBtnBright() {
		return btnBright;
	}

	/**
	 * @return the messageWindow
	 */
	public JLabel getMessageWindow() {
		return messageWindow;
	}
	
	/**
	 * @return the eventContainer
	 */
	public JLayeredPane getEventContainer() {
		return eventContainer;
	}

	/**
	 * @return the eventPanel
	 */
	public JPanel getEventPanel() {
		return eventPanel;
	}

	/**
	 * @return the buttonPanel
	 */
	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @return the messagePanel
	 */
	public JPanel getMessagePanel() {
		return messagePanel;
	}

	/**
	 * @return the buttonArray
	 */
	public JButton[] getButtonArray() {
		return buttonArray;
	}

	/**
	 * @param buttonArray the buttonArray to set
	 */
	public void setButtonArray(JButton[] buttonArray) {
		this.buttonArray = buttonArray;
	}

	/**
	 * @return the buttonSequence
	 */
	public int getButtonSequence() {
		return buttonSequence;
	}


	/**
	 * @param buttonSequence the buttonSequence to set
	 */
	public void setButtonSequence(int buttonSequence) {
		this.buttonSequence = buttonSequence;
	}

	/**
	 * @return the boxOpen
	 */
	public boolean getBoxOpen() {
		return boxOpen;
	}

	/**
	 * @param boxOpen the boxOpen to set
	 */
	public void setBoxOpen(boolean boxOpen) {
		this.boxOpen = boxOpen;
	}

	/**
	 * @return the switchImage
	 */
	public JLabel getSwitchImage() {
		return switchImage;
	}

	/**
	 * @return the boxImage
	 */
	public JLabel getBoxImage() {
		return boxImage;
	}

	/**
	 * @return the switchIconClosed
	 */
	public ImageIcon getSwitchIconClosed() {
		return switchIconClosed;
	}

	/**
	 * @return the switchIconOpen
	 */
	public ImageIcon getSwitchIconOpen() {
		return switchIconOpen;
	}

	/**
	 * @return the switchIconEmpty
	 */
	public ImageIcon getSwitchIconEmpty() {
		return switchIconEmpty;
	}

	/**
	 * @return the switchIconOff
	 */
	public ImageIcon getSwitchIconOff() {
		return switchIconOff;
	}

	/**
	 * @return the switchIconOn
	 */
	public ImageIcon getSwitchIconOn() {
		return switchIconOn;
	}

	/**
	 * @return the cleanedLever
	 */
	public boolean getCleanedLever() {
		return cleanedLever;
	}

	/**
	 * @param cleanedLever the cleanedLever to set
	 */
	public void setCleanedLever(boolean cleanedLever) {
		this.cleanedLever = cleanedLever;
	}

	/**
	 * @return the leverImage
	 */
	public JLabel getLeverImage() {
		return leverImage;
	}

	/**
	 * @return the leverIconRusty
	 */
	public ImageIcon getLeverIconRusty() {
		return leverIconRusty;
	}

	/**
	 * @return the leverIconClean
	 */
	public ImageIcon getLeverIconClean() {
		return leverIconClean;
	}

	/**
	 * @return the leverIconOn
	 */
	public ImageIcon getLeverIconOn() {
		return leverIconOn;
	}

	/**
	 * @return the closeEventButton
	 */
	public JLabel getCloseEventButton() {
		return closeEventButton;
	}

	
}