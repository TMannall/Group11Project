import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.window.event.MouseEvent;
import org.jsfml.window.Mouse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Settings state for Endless Sea
 */
public class Settings extends FSMState{

    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private SoundFX sound;

    private static int numberOfButtons = 3;
	private static int numberOfArrows = 2;
    private static int buttonIndex = 0;
    Text[] text = new Text[numberOfButtons];
    Text title;
	FloatRect[] rect = new FloatRect[numberOfButtons];
	FloatRect[] leftArrowRect = new FloatRect[numberOfArrows];
	FloatRect[] rightArrowRect = new FloatRect[numberOfArrows];
	IntRect[] recti = new IntRect[1];
	float musicVol = 100;
	float soundVol = 100;

    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static int titleFontSize = 80;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;
    public Color BROWN = new Color(56, 35, 5);


    private static String Title = "Settings";

    public Settings(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, SoundFX sound) {
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        this.sound = sound;
        setup();
    }

    public void setup(){
        if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;

        Font sansRegular = new Font();
        try {
            sansRegular.loadFromFile(
                    Paths.get(FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        title = new Text(Title, sansRegular, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 80);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.WHITE);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }

        text[0].setFont(sansRegular);
        text[0].setColor(BROWN);
        text[0].setString("Music : " + sound.getMusicVolume() + "%");
        text[0].setPosition(driver.getWinWidth() / 2, 260);
		text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(sansRegular);
        text[1].setColor(BROWN);
        text[1].setString("Sound : " + sound.getSoundVolume() + "%");
        text[1].setPosition(driver.getWinWidth() / 2, 330);
		text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);

        text[2].setFont(sansRegular);
        text[2].setColor(BROWN);
        text[2].setString("Main Menu");
        text[2].setPosition(driver.getWinWidth() / 2, 400);
		text[2].setOrigin(text[2].getLocalBounds().width / 2, text[2].getLocalBounds().height / 2);
    }

    @Override
    public void execute() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);

        try {
            Thread.sleep(10); //1000); // this delays the animation frames if it's too high
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        displaySettings();
    }

    public void displaySettings() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);
        window.draw(title);
		
		Sprite[] textButton = new Sprite[numberOfButtons];
		Sprite[] hoverButton = new Sprite[numberOfButtons];
		Sprite[] pushButton = new Sprite[numberOfButtons];
		Sprite[] leftArrow = new Sprite[numberOfArrows];
		Sprite[] rightArrow = new Sprite[numberOfArrows];
		
		
		for(int i = 0; i < numberOfArrows; i++){
			leftArrow[i] = textures.createSprite(textures.userInterface, 945, 579, 70, 51);
			rightArrow[i] = textures.createSprite(textures.userInterface, 1016, 579, 70, 51);
		}
		
		for(int i = 0; i < numberOfButtons; i++){
			textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
			hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
			pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
		}
		
		for(int i = 0; i < numberOfButtons; i++){
			textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
			hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
			pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
		}

        for(int i = 0; i < numberOfButtons; i++){
            textButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
            pushButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
        }

        for(int i = 0; i < numberOfButtons; i++) {
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
		
		leftArrow[0].setPosition(text[0].getPosition().x - 170, text[0].getPosition().y + 8);
		rightArrow[0].setPosition(text[0].getPosition().x + 170, text[0].getPosition().y + 8);
		leftArrow[1].setPosition(text[1].getPosition().x - 170, text[1].getPosition().y + 8);
		rightArrow[1].setPosition(text[1].getPosition().x + 170, text[1].getPosition().y + 8);
			
		textButton[0].setPosition(text[0].getPosition().x, text[0].getPosition().y + 8);
		textButton[1].setPosition(text[1].getPosition().x, text[1].getPosition().y + 8);
		textButton[2].setPosition(text[2].getPosition().x, text[2].getPosition().y + 8);
		//textButton[3].setPosition(text[3].getPosition().x, text[3].getPosition().y + 8);
			
		hoverButton[0].setPosition(text[0].getPosition().x, text[0].getPosition().y + 8);
		hoverButton[1].setPosition(text[1].getPosition().x, text[1].getPosition().y + 8);
		hoverButton[2].setPosition(text[2].getPosition().x, text[2].getPosition().y + 8);
		//hoverButton[3].setPosition(text[3].getPosition().x, text[3].getPosition().y + 8);
			
		pushButton[0].setPosition(text[0].getPosition().x, text[0].getPosition().y + 8);
		pushButton[1].setPosition(text[1].getPosition().x, text[1].getPosition().y + 8);
		pushButton[2].setPosition(text[2].getPosition().x, text[2].getPosition().y + 8);
		//pushButton[3].setPosition(text[3].getPosition().x, text[3].getPosition().y + 8);

        for (int i = 0; i < numberOfButtons; i++) {
			//window.draw(textButton[i]);
            window.draw(text[i]);
        }
		
		for(int i = 0; i < numberOfArrows; i++){
			window.draw(leftArrow[i]);
			window.draw(rightArrow[i]);
		}
		
        rect[0] = new FloatRect(textButton[2].getGlobalBounds().left, textButton[2].getGlobalBounds().top,
								textButton[2].getGlobalBounds().width, textButton[2].getGlobalBounds().height);
        recti[0] = new IntRect(rect[0]);
		
        if((recti[0].contains(Mouse.getPosition(window)) && isMouseOver())){
            textButton[2].setTextureRect(new IntRect(23, 100, 250, 60));
            window.draw(textButton[2]);
            window.draw(text[2]);
        }
        else if(!isMouseOver()){
            textButton[2].setTextureRect(new IntRect(23, 21, 250, 60));
            window.draw(textButton[2]);
            window.draw(text[2]);
        }
  
		
		/*window.draw(textButton[3]);
		window.draw(text[3]);*/

        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.UP) {
                        moveUp();
                        break;
                    } else if (keyEvent.key == Keyboard.Key.DOWN) {
                        moveDown();
                        break;
                    }

                    /**
                     * This part of the code is where the button functionality is implemented.
                     * Use this part for integrating the menu with the main program.
                     */
                    else if (keyEvent.key == Keyboard.Key.RETURN) {
                        switch (getButtonIndex()) {
                            case 0: //Music Vol

                                break;
                            case 1: //Sound Vol

                                break;
                            case 2: //Settings 3

                                break;
                            /*case 3: //Main menu
                                stateMachine.setState(stateMachine.getStates().get(0));
                                break;*/
                        }
                    }
					break;
				case MOUSE_BUTTON_PRESSED:
					MouseEvent mouseClicked = event.asMouseButtonEvent();
					if(mouseClicked.type == Event.Type.MOUSE_BUTTON_PRESSED){
						
						for(int i = 0; i < numberOfButtons; i++){
							rect[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top, 
										textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
						}
						
						for(int i = 0; i < numberOfArrows; i++){
							leftArrowRect[i] = new FloatRect(leftArrow[i].getGlobalBounds().left, leftArrow[i].getGlobalBounds().top, 
															leftArrow[i].getGlobalBounds().width, leftArrow[i].getGlobalBounds().height);
							rightArrowRect[i] = new FloatRect(rightArrow[i].getGlobalBounds().left, rightArrow[i].getGlobalBounds().top, 
															rightArrow[i].getGlobalBounds().width, rightArrow[i].getGlobalBounds().height);
						}
						
						//Music down
						if(leftArrowRect[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
							musicVol -= 10;
							sound.setMusicVolume(musicVol);
							text[0].setString("Music : " + sound.getMusicVolume() + "%");
							window.draw(text[0]);
						}
						//Music up
						if(rightArrowRect[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
							musicVol += 10;
							sound.setMusicVolume(musicVol);
							text[0].setString("Music : " + sound.getMusicVolume() + "%");
							window.draw(text[0]);
						}
						//Sound down
						if(leftArrowRect[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
							soundVol -= 10;
							sound.setSoundVolume(soundVol);
							text[1].setString("Sound : " + sound.getSoundVolume() + "%");
							window.draw(text[1]);
						}
						//Sound up
						if(rightArrowRect[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
							soundVol += 10;
							sound.setSoundVolume(soundVol);
							text[1].setString("Sound : " + sound.getSoundVolume() + "%");
							window.draw(text[1]);
						}
						
						//Music Vol
						if(rect[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
							window.draw(pushButton[0]);
							window.draw(text[0]);
						}
						//Sound Vol
						if(rect[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
							//textButton[0].setTextureRect(new IntRect(23, 100, 250, 60));
							//window.draw(textButton[0]);
							window.draw(pushButton[1]);
							window.draw(text[1]);
						}
						//Main Menu
						if(rect[2].contains(mouseClicked.position.x, mouseClicked.position.y)){
							window.draw(pushButton[2]);
							window.draw(text[2]);
							stateMachine.setState(stateMachine.getStates().get(0));
						}
					}
					break;
				}
			}
		window.display();
    }
	
	//public void 

    public void moveUp() {
        if (buttonIndex - 1 >= 0) {
            text[buttonIndex].setColor(Color.CYAN);
            buttonIndex--;
            text[buttonIndex].setColor(Color.MAGENTA);
        }
    }

    public void moveDown() {
        if (buttonIndex + 1 < numberOfButtons) {
            text[buttonIndex].setColor(Color.CYAN);
            buttonIndex++;
            text[buttonIndex].setColor(Color.MAGENTA);
        }
    }

    int getButtonIndex(){
        return buttonIndex;
    }
	
    public boolean isMouseOver(){
        if(recti[0].contains(Mouse.getPosition(window))){
            return true;
        }
        return false;
    }
}
