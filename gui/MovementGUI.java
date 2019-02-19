// import java libraries and declare package
package gui;
import java.awt.*;
import javax.swing.*;

/** 
 * Source: http://zetcode.com/tutorials/javagamestutorial/movingsprites/
 * Generate MovementGUI class which will handle and display the GUI for
 * the game movement
 * @author Sehajveer Bring, Grace Lan, Anindya Mandal
 */
public class MovementGUI extends JPanel {
	
	public static final long serialVersionUID = 0412171700;
   
	// declare instance variables
	private ImageIcon playerIcon;
	private ImageIcon playerImage;
	private ImageIcon ghostImage;
    
    private int dx;
    private int dy;
    private int x;
    private int y;
  	

	/**
     * This method acts as the default constructor for the MovementGUI class
     */
    public MovementGUI() {
    	x = 72;
    	y = 30;
    	playerIcon = Icon.createImageIcon("/images/imagesForMap/face.png");
    	playerImage = Icon.createImageIcon("/images/imagesForMap/face.png");
    	ghostImage = Icon.createImageIcon("/images/imagesForMap/ghost.png");
        createMovement();
    }
    
    
    /**
     * This method initializes the movement by setting panel attributes
     * and initializing a game timer to enable smooth movement for when
     * the user holds a key down.
     */
    private void createMovement() {
        setFocusable(true);
        setOpaque(false);
    }


    /**
     * This method paints graphics components using imported graphic libraries. It draws
     * the player icon based on our chosen images and coordinates and draws it on this
     * panel.
     * @param g - Graphics component
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(playerIcon.getImage(), getX(), getY(), this);
    }

	/**
	 * @return the dx
	 */
	public int getDX() {
		return dx;
	}

	/**
	 * @param dx the dx to set
	 */
	public void setDX(int dx) {
		this.dx = dx;
	}

	/**
	 * @return the dy
	 */
	public int getDY() {
		return dy;
	}

	/**
	 * @param dy the dy to set
	 */
	public void setDY(int dy) {
		this.dy = dy;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the playerIcon
	 */
	public ImageIcon getPlayerIcon() {
		return playerIcon;
	}

	/**
	 * @param playerIcon the playerIcon to set
	 */
	public void setPlayerIcon(ImageIcon playerIcon) {
		this.playerIcon = playerIcon;
	}
	
	/**
	 * @return the playerImage
	 */
	public ImageIcon getPlayerImage() {
		return playerImage;
	}

	/**
	 * @param playerImage the playerImage to set
	 */
	public void setPlayerImage(ImageIcon playerImage) {
		this.playerImage = playerImage;
	}

	/**
	 * @return the ghostImage
	 */
	public ImageIcon getGhostImage() {
		return ghostImage;
	}
	
	/**
	 * @param ghostImage the ghostImage to set
	 */
	public void setGhostImage(ImageIcon ghostImage) {
		this.ghostImage = ghostImage;
	}


}
