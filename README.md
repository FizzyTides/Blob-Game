BLOB 2D GAME

NOTE: These instructions are for Eclipse.
Java Version: JavaSE-11 and up is used to play our game.

Import the Project:

To import the project:

Step 1: Pull the project from the remote repository.
Step 2: Open Eclipse and click on the File menu.
Step 3: Click on the "Import" option in the drop-down file menu. A new window will open to select the type of project.
Step 4: Click "Maven" option and select "Existing maven project" option under the drop-down menu.
Step 5: Browse your project in your copmuter and the click "Finish" button. They project will be imported in you IDE.  

-----------------------------------------------------------------------------------------------------------------

Building and Running the Game:

To build an run game:

Step 1: Open Your IDE and Open the "Blob2DGame" folder in your IDE.
Step 2: In the package explorer window at the left, Select the "src/main/java" folder under the drop down menu of "Blob2DGame".
Step 3: Click on the run button in your IDE. A new window will appear to ask how would you like to run the build. 
Step 4: Select the "Java Application" from the menu, and Run the build.
	The Game window will launch, and the game must be ready to play. "ENJOY!!"

------------------------------------------------------------------------------------------------------------------

Testing the Game:

To test the game, you would have to set up the JUnit initially.

Configuring Build Path:

Step 1: Right click on the parent folder "Blob2DGame", and the select "Build Path" option from the drop-down menu.
Step 2: From the Build path menu, select the "Configure Build Path" option. A new window will open to configure the build path.
Step 3: From the top bar, select the "Libraries" menu option and the click on the "Add Library" option at the rigth side of the window.
Step 4: Select "JUnit" for the library type and click "Next" button.
Step 5: Select "JUnit 5" for the library version from the drop-down menu and click on the "Finish" Button.
Step 6: Now click on the "Apply and Close" button.


Configurin Run Path:

Step 1: Right click again on the parent folder "Blob2DGame", and the select "Run As" option from the drop-down menu.
Step 2: From the Run As menu, select the "RUn Configuration" option. A new window will open to configure the run path.
Step 3: From the Configurations menu at the left side of the window, select the "JUnit" option.
Step 4: Then select the "java" option from the drop-down list.
Step 5: Select the "JUnit 5" from the "Test runner" drop-down list, and hit run.

This will automatically run all the tests.

NOTE: These steps are only needed to be done when you tests the game for the first time.

To run the tests afterwards:

Step 1: Open Your IDE and Open the "Blob2DGame" folder in your IDE.
Step 2: In the package explorer window at the left, Select the "src/test/java" folder under the drop down menu of "Blob2DGame" folder.
Step 3: Click on the run button in your IDE. A new window will appear to ask how would you like to run the build. 
Step 4: Select the "JUnit" option from the menu, and Run the build.

------------------------------------------------------------------------------------------------------------------

Exporting Jar File:

Step 1: Right click project folder "Blob2DGame"
Step 2: Select Export
Step 3: Under Java select Runnable Jar File
Step 4: Hit Next
Step 5: Set Launch Configuration to "Game - Blob2DGame"
Step 6: Set Export Destination
Step 7: Set Library Handling to "Package required libraries..."
Step 8: Finish

------------------------------------------------------------------------------------------------------------------

Exporting JavaDocs using maven:

Step 1: Open Terminal or windows powershell
Step 2: Change directories to project folder containing pom.xml, \project\Blob2DGame
Step 3: Run mvn javadoc:javadoc to extract the HTML Javadoc
Step 4: Find Html Javadocs under \project\Blob2DGame\target\site\apidocs\
Step 5: Done

------------------------------------------------------------------------------------------------------------------

Opening Javadoc Jar: 

Step 1: Use either 7Zip or Winrar
Step 2: Go to: \project\Blob2DGame\target
Step 3: Right Click Javadoc Jar
Step 4: Select Open archive using 7 Zip or Winrar (whichever you chose)
Step 5: Done