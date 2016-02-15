import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;

import java.text.DecimalFormat;

import org.jsfml.graphics.Text;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.Mouse;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseEvent;
import org.jsfml.window.event.MouseButtonEvent;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Main menu state for Endless Sea
 */
public class Menu extends FSMState{

    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    private static int numberOfButtons = 4;
    Text[] text = new Text[numberOfButtons];
    Text title;
	IntRect[] recti = new IntRect[numberOfButtons];
	FloatRect[] rectf = new FloatRect[numberOfButtons];

    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static int titleFontSize = 80;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;

    private static String Title = "ENDLESS SEA";
    private SoundClass sound = new SoundClass();

    public Menu(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures) {
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;

        setup();
    }

    public void setup(){
        if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;

        Font fontStyle = new Font();
        try {
            fontStyle.loadFromFile(
                    Paths.get(FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        sound.playBackgroundMusic("music_main_menu");

        title = new Text(Title, fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 100);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.CYAN);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }

        text[0].setFont(fontStyle);
        text[0].setColor(Color.CYAN);
        text[0].setString("New Game");
        text[0].setPosition(driver.getWinWidth() / 2, 280);
		text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(fontStyle);
        text[1].setColor(Color.CYAN);
        text[1].setString("Leaderboard");
        text[1].setPosition(driver.getWinWidth() / 2, 350);
		text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);

        text[2].setFont(fontStyle);
        text[2].setColor(Color.CYAN);
        text[2].setString("Settings");
        text[2].setPosition(driver.getWinWidth() / 2, 420);
		text[2].setOrigin(text[2].getLocalBounds().width / 2, text[2].getLocalBounds().height / 2);

        text[3].setFont(fontStyle);
        text[3].setColor(Color.CYAN);
        text[3].setString("Exit");
        text[3].setPosition(driver.getWinWidth() / 2, 490);
		text[3].setOrigin(text[3].getLocalBounds().width / 2, text[3].getLocalBounds().height / 2);
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

        displayMenu();
    }

    public void displayMenu() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);
		window.draw(title);
		
		Sprite[] textButton = new Sprite[numberOfButtons];
		Sprite[] hoverButton = new Sprite[numberOfButtons];
		Sprite[] pushButton = new Sprite[numberOfButtons];
		
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

        // jack: sprite testing
        /*for(int i = 0; i < 5; i++) {
            driver.marineList.get(i).setPosition((200 * i/2), 500);
            window.draw(driver.marineList.get(i));
        }*/
        // jack: end sprite testing
		
		//window.draw(hoverButton[0]);
        //window.display();
		
		for(int i = 0; i < numberOfButtons; i++){
			rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top, 
									textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
			recti[i] = new IntRect(rectf[i]);
		}
		
		for(int i = 0; i < numberOfButtons; i++){
			if((recti[i].contains(Mouse.getPosition(window)) && isMouseOver())){
				textButton[i].setTextureRect(new IntRect(23, 100, 250, 60));
				window.draw(textButton[i]);
				window.draw(text[i]);
			}
			else if(!isMouseOver()){
				textButton[i].setTextureRect(new IntRect(23, 21, 250, 60));
				window.draw(textButton[i]);
				window.draw(text[i]);				
			}
		}

        for (Event event : window.pollEvents()) {	
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
				case MOUSE_BUTTON_PRESSED:
					MouseEvent mouseClicked = event.asMouseButtonEvent();
					if(mouseClicked.type == Event.Type.MOUSE_BUTTON_PRESSED){
						for(int i = 0; i < numberOfButtons; i++){
							rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top, 
										textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
						}
						//New Game
						if(rectf[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
							window.draw(pushButton[0]);
							window.draw(text[0]);
							sound.stopBackgroundMusic();
							//stateMachine.setState(stateMachine.getStates().get(2));
							stateMachine.setState(stateMachine.getStates().get(5));
							sound.playBackgroundMusic("music_combat");
						}
						//Leaderboards
						if(rectf[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
							window.draw(pushButton[1]);
							window.draw(text[1]);
						}
						//Settings
						if(rectf[2].contains(mouseClicked.position.x, mouseClicked.position.y)){
							window.draw(pushButton[2]);
							window.draw(text[2]);
							stateMachine.setState(stateMachine.getStates().get(1));
						}
						//Exit
						if(rectf[3].contains(mouseClicked.position.x, mouseClicked.position.y)){
							window.draw(pushButton[3]);
							window.draw(text[3]);
							window.close();
						}
					}
					break;
					}
				}
			window.display();
		}
		
	public boolean isMouseOver(){
		for(int i = 0; i < numberOfButtons; i++){
			if(recti[i].contains(Mouse.getPosition(window))){
				return true;
			}
		}
		return false; 
	}
}
