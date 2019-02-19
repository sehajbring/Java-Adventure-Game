// declare package and import java libraries
package gui;
import java.io.*;
import java.net.*;
import javax.swing.*;

/*
 * Generate Icon class, which is used by all other GUI game classes to create their
 * ImageIcons.
 * @author Leia Astprga
 * 
 */
public class Icon {
	
	public static final long serialVersionUID = 0412171700;
	
	/**
	 * Source: http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
	 * This method gets an image file as a resource and returns an ImageOcon if the image
	 * file exists.
	 * @return ImageIcon - created image icon out of an existing image file
	 */
	public static ImageIcon createImageIcon(String imagePath) {
		try {
			URL imgURL = Icon.class.getResource(imagePath);
			if (imgURL == null) {
				throw new FileNotFoundException("Error: Could Not Find File '" + imagePath + "'");
			}
			return new ImageIcon(imgURL);
			
		} catch (FileNotFoundException fExcept) {
			fExcept.getMessage();
			JOptionPane.showMessageDialog(new JFrame(), "ERROR: Essential Image Files Not Found.\nExiting Game.", "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return null;
		}
	}
	
}
