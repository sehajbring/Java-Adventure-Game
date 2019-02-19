// declare package
package logic;

/**
 * Generate Location class which stores a grid value that can be converted into pixels.
 * @author Leia Astorga, Grace Lan, Sehajveer Bring
 */
public class Location {
	
	public static final long serialVersionUID = 0412171700;
	
	private int xCol;
	private int yRow;

	/**
	* Default constructor
	*/
	public Location() {
		this(0,0);
	}


	/**
	* Largest constructor
	*/
	public Location(int xCol, int yRow) {
		this.xCol = xCol;
		this.yRow = yRow;
	}


	/**
	* Copy constructor
	*/
	public Location(Location locationInstance) {
		this.xCol = locationInstance.xCol;
		this.yRow = locationInstance.yRow;
	}


	/**
	* Static method which calculates the pixels of a given row/column value
	*/
	public static int toPixels(int someValue) {
		int pixels = someValue*48;
		return pixels;
	}


	/**
	* Getter for xCol instance variable
	*/
	public int getXCol() {
		Location copyLocation = new Location(this);
		copyLocation.xCol = toPixels(this.xCol);
		return copyLocation.xCol;
	}


	/**
	* Setter for xCol instance variable
	*/
	public void setXCol(int newXCol) {
		this.xCol = newXCol;
	}


	/**
	*  Getter for yRow instance variable
	*/
	public int getYRow() {
		Location copyLocation = new Location(this);
		copyLocation.yRow = toPixels(this.yRow);
		return copyLocation.yRow;
	}


	/**
	* Setter for yRow instance variable
	*/
	public void setYRow(int newYRow) {
		this.yRow = newYRow;
	}

	
	/**
	 * This method creates a print statement for terminal to display
	 * the location of an item.
	 */
	public String toString() {
		return "Location (" + getXCol() + ", " + getYRow() + ")";
	}

	
	/**
	* Main method for testing this class
	*/
	public static void main(String[] args) {
		// creating location with default values
		Location defaultLocation = new Location();
		System.out.println("LOCATION CREATED - INITIALIZED TO DEFAULT SETTINGS");
		System.out.println("Default Location: " + defaultLocation);
		System.out.println("XCol of Default: " + defaultLocation.getXCol());
		System.out.println("YRow of Default: " + defaultLocation.getYRow());
		System.out.println("Pixels of Default's X: " + Location.toPixels(defaultLocation.getXCol()));
		System.out.println("Pixels of Default's Y: " + Location.toPixels(defaultLocation.getYRow()) + "\n");

		// creating location for item at (15,3)
		Location itemLocation = new Location(15,3);
		System.out.println("ITEM CREATED @ COL 15 & ROW 3");
		System.out.println("Item Location: " + itemLocation);
		System.out.println("XCol of Item: " + itemLocation.getXCol());
		System.out.println("YRow of Item: " + itemLocation.getYRow());

		// getting pixel count (static method so no instance needed)
		System.out.println("Pixels of item's X: " + Location.toPixels(itemLocation.getXCol()));
		System.out.println("Pixels of item's Y: " + Location.toPixels(itemLocation.getYRow()) + "\n");
		
		System.out.println("USING STATIC METHOD TO GET PIXEL COUNT FOR A GIVEN INT:");
		System.out.println("Pixels for int 20: " + Location.toPixels(15) + "\n");

		// setting new coordinates for item to (5,10)
		itemLocation.setXCol(5);
		itemLocation.setYRow(10);
		System.out.println("UPDATE ITEM LOCATION TO (5,10): " + itemLocation);
		System.out.println("New XCol of Item: " + itemLocation.getXCol());
		System.out.println("New YRow of Item: " + itemLocation.getYRow());
		System.out.println("New pixels of item's X: " + Location.toPixels(itemLocation.getXCol()));
		System.out.println("New pixels of item's Y: " + Location.toPixels(itemLocation.getYRow()) + "\n");

		// create a copy of item location
		Location copyItemLocation = new Location(itemLocation);
		System.out.println("AVOIDING PRIVACY LEAK BY USING COPY CONSTRUCTOR:");
		System.out.println("Original itemLocation: " + copyItemLocation);
		System.out.println("Copied itemLocation:   " + itemLocation + "\n");
		
		Location stealLocation = itemLocation;
		System.out.println("CREATING A PRIVACY LEAK BY CREATING A DIRECT COPY:");
		System.out.println("Original itemLocation: " + itemLocation);
		System.out.println("Copied itemLocation:   " + stealLocation + "\n");

	}


}