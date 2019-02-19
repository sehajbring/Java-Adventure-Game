		"ESCAPE ADVENTURES OF THE GRUMPY FACE"
       			 (Version 13.04.17.00)

AUTHORS:	Leia Astorga
		Grace Lan
		Anindya Mandal
		Sehajveer Bring

CREDITS:	Sources used for methods are referenced in the method descriptions
		All images used were either purchased for use by Grace Lan or created by Leia Astorga
		Player icon is based on the generic angry emoji, but our version was drawn using Photoshop

========
 README  	0. Steps to Win << SPOILERS >>
CONTENTS	1. System Requirements
========	2. Installation
		3. Operation
		4. Files List
		5. Troubleshooting


===================================================================================================
0. STEPS TO WIN									   << SPOILERS >>
===================================================================================================
	
	Collect 'Oil' item on map
	Collect 'Prybar' item on map
	
	Trigger 'Lever' event on map
	Click on 'Oil' in menu, then close
	Drag mouse across Lever image ---------------------------- (removes the rust)
	Click on 'Prybar' in menu, then close -------------------- (then close using the 'x' image)
	Click on Lever image ------------------------------------- (changes lever setting to on/up)

	Trigger 'Switch' event
	Drag mouse across top of box image ----------------------- (works since prybar is still active)
	Collect 'Paper' item inside the box
	Click on the Switch image -------------------------------- (changes switch setting to green)

	Trigger 'Button' event
	Click on 'Paper' in menu, then close
	Click on button 3, then 7, then 14, then 20 -------------- (changes button setting to done)

	Walk into the newly opened black space  ------------------ (is open now - the door disappeared)

	*** also see '3g - Operation' for activating Ghost Mode


===================================================================================================
1. SYSTEM REQUIREMENTS
===================================================================================================

	a. OS:		Linux - Fedora 22
			Mac OS Sierra - 10.12.3
			Windows - 10

	b. Resolution:	1920 x 1200 (16:10)
			1680 x 1050


===================================================================================================
2. INSTALLATION
===================================================================================================

	a. Compile and Run
		All game modules and submodules should be saved in the src folder
		From the src folder, run command line 'javac gui/WorldGUI.java'
		Run command 'java gui/WorldGUI'

	b. SavedGames
		At run time, a SavedGames folder is created in the current directory
		Existing SavedGames folders will not be overwritten
		SavedGames is empty by default
		New files are created as users save from within the game
		.game files can also be copied into the savedGames folder
		Only the following named files are read by the game: game1, game2, game3, game4

	c. TestFilesToCopy
	   	Contains games in different states that can be loaded for testing
		To use these in-game, copy the files into the savedGames folder and re-initialize the game


===================================================================================================
3. OPERATION
===================================================================================================

	a. GAME PLAY
		The player wakes up trapped in a house: The goal is to escape.
		The game can be paused at any time by clicking pause in the Items menu (top right)
		During pause, the user cannot move or click on items in the Items menu

	b. MOVEMENT
		Movement is done using: w = up, a = left, s = down, d= right
			
	c. ITEMS
		Items are acquired by walking over them
		Acquired items are saved in the Items menu
		Clicking on Items in the menu pops up a larger image for closer examination
		A selected item remains active as long as it was the last item clicked on in the menu

	d. EVENTS
		Events are triggered by walking into them

	e. WINNING
		The user wins when they enter the dark room that was locked at the start of the game

	f. LOAD/SAVE
		Click Pause in the Items Menu
		Click Load or Save
		Valid game files are read from the SavedGames folder in the currentDirectory
		User can select whether to overwrite an existing file or fill an empty one
		The game can store 4 possible game slots


	g. GHOST MODE
		Play through the game once, and select play again.
		Play through the game a second time, and select play again.
		This time, the player is replaced by a ghost who can walk through walls
		As a ghost, the player can still collect items and trigger events
		Ghost can cheat by floating through the door to the finish point


===================================================================================================
4. FILES LIST
===================================================================================================

	a. Module: bounds
	-----------------
	       Bounds.java
	   EventSpace.java
	    ItemSpace.java


	b. Module: connect
	------------------
	   EventsMain.java
	 LoadSaveMain.java
	     MenuMain.java
	    WorldMain.java


	c. Module: gui
	--------------
	    EventsGUI.java
	         Icon.java
	  LoadSaveGUI.java
	      MenuGUI.java
	  MovementGUI.java
	     WorldGUI.java


	d. Module: logic
	----------------
	  EventsLogic.java
	     Location.java
	    MenuLogic.java
	   WorldLogic.java


	e. Module: images
	-----------------

		i. Submodule: events
		--------------------
		     leverImageOn.png
	          leverImageClean.png
		  leverImageRusty.png
		switchImageClosed.png
		 switchImageEmpty.png
		   switchImageOff.png
		    switchImageOn.png
		  switchImageOpen.png
		       buttonBlue.png
		       buttonGray.png
		      buttonGreen.png
		        buttonRed.png
		     buttonBright.png

		ii. Submodule: imagesForMap
		---------------------------
		       buttonIcon.png
		             door.png
		             face.png
		            ghost.png
		        leverIcon.png
		       switchIcon.png
		         worldMap.png

		iii. Submodule: items
		---------------------
		        colorless.png
		              oil.png
		          oilIcon.png
		            paper.png
		        paperIcon.png
	                   prybar.png
	               prybarIcon.png

		iv. Submodule: UI
		-----------------
		      closeButton.png
		     eventsWindow.png
		             load.png
		           loadBG.png
		       loadButton.png
	               menuWindow.png
	            messageWindow.png
	              pauseButton.png
		         popLabel.png
		             save.png
		       saveButton.png


	f. Module: TestFilesToCopy
	-------------------------
	        game1.game  startingValues  (Game state containing starting values)	
	        game2.game  allDone	    (Game tasks done. Just enter door to trigger ghost)
	        game3.game  testCorrupt	    (Corrupt .game file that cannot be read)
	        game4.game  ghostGame	    (Ghost is activated and walk through walls)


===================================================================================================
5. TROUBLESHOOTING
===================================================================================================
	
	a. Files Not Found	If files required for the game to initialize are not found,
				a dialog warns the user of the error and closes the game.
				Check that all required files are saved to the current directory.

	b. SavedGames Folder 	If the game cannot create a SavedGames folder in the current path,
				a dialog warns the user of the error and closes the game.
						
				This would happen if any part of the path to the current directory
				contained a space. We amended this using line 55 in LoadSaveGUI():
				All “%20” values in the URL are replaced with a String “ “ (space).
				The SavedGames directory is created in the LoadSaveGUI constructor.


