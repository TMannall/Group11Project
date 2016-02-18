import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class TestMenu{
	private static int menuWidth  = 1024;
	private static int menuHeight = 768;
	private static int numberOfButtons = 4;
	private static int buttonIndex = 0;
	Text[] buttons = new Text[numberOfButtons];

	private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
	private static String JdkFontPath = "textures/";
	private static String JreFontPath = "textures/";

	private static int titleFontSize = 80;
	private static int buttonFontSize = 32;
	private static String FontFile = "vinque.ttf";
	private String FontPath;

	private static String Title = "ENDLESS SEA";
	
	int getButtonIndex(){
		return buttonIndex;
	}
	
	public void moveUp(){
		if(buttonIndex - 1 >= 0){
			buttons[buttonIndex].setColor(Color.BLACK);
			buttonIndex--;
			buttons[buttonIndex].setColor(Color.BLUE);
		}
	} 
	
	public void moveDown(){
		if(buttonIndex + 1 < numberOfButtons){
			buttons[buttonIndex].setColor(Color.BLACK);
			buttonIndex++;
			buttons[buttonIndex].setColor(Color.BLUE);
		}		
	}

	public void displayMenu(){
		if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
		else FontPath = JdkFontPath;
		
		Font sansRegular = new Font();
			try {
				sansRegular.loadFromFile(
						Paths.get(FontPath+FontFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}
			
			Text title = new Text(Title, sansRegular, titleFontSize);
			title.setPosition(menuWidth/2,80);
			title.setOrigin(title.getLocalBounds().width/2, title.getLocalBounds().height/2);
			title.setColor(Color.BLACK);
			title.setStyle(Text.BOLD);
			
			for (int i=0; i<numberOfButtons; i++){
				buttons[i] = new Text();
			}
			
			buttons[0].setFont(sansRegular);
			buttons[0].setColor(Color.BLUE);
			buttons[0].setString("New Game");
			buttons[0].setPosition(menuWidth/2-90, 200);
			
			buttons[1].setFont(sansRegular);
			buttons[1].setColor(Color.BLACK);
			buttons[1].setString("Load Game");
			buttons[1].setPosition(menuWidth/2-90, 270);

			buttons[2].setFont(sansRegular);
			buttons[2].setColor(Color.BLACK);
			buttons[2].setString("Settings");
			buttons[2].setPosition(menuWidth/2-90, 340);
			
			buttons[3].setFont(sansRegular);
			buttons[3].setColor(Color.BLACK);
			buttons[3].setString("Exit");
			buttons[3].setPosition(menuWidth/2-90, 410);
			
	
		RenderWindow window = new RenderWindow( );
		
		window.create(new VideoMode(menuWidth, menuHeight), Title, WindowStyle.DEFAULT);
			
		while (window.isOpen()) {
			window.clear(Color.WHITE);
			window.draw(title);

			for(int i=0; i<numberOfButtons; i++){
				window.draw(buttons[i]);
			}
			
			window.display();

			for (Event event : window.pollEvents()) {
				switch(event.type)
				{
				case CLOSED:
					window.close();
					break;
				case KEY_PRESSED:
					KeyEvent keyEvent = event.asKeyEvent();
					if(keyEvent.key == Keyboard.Key.UP)
					{
						moveUp();
						break;
					}
					else if(keyEvent.key == Keyboard.Key.DOWN)
					{
						moveDown();
						break;
					}
					
/*****************************************************************
*This part of the code is where the button functionality is		   *
*implemented. Use this part for integrating the menu with the    *
*main program                                                    *
******************************************************************/
					else if(keyEvent.key == Keyboard.Key.RETURN)
					{
						switch(getButtonIndex())
						{	
							
							case 0: //New Game

								break;
							case 1: //Load Game

								break;
							case 2: //Settings button

								break;
							case 3: //Exit button
								window.close();
								break;
						}
					}
				}
			}
		}
	}
		
	public static void main (String args[]) {
		TestMenu t = new TestMenu();
		t.displayMenu();
	}
}
