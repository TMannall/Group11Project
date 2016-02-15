import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Aidan on 15/02/2016.
 */
public class EventState extends FSMState {
    /**
     * Event state class for Endless Sea
     */
        private FSM stateMachine;
        private GameDriver driver;
        private RenderWindow window;
        private Textures textures;
        private Random randGenerator;
        private EventExampleDriver eventDriver;

        Sprite messageScroll;
        private static int numberOfButtons = 2;
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
        private static String Title = "XXXXXXXX Have attacked you!";

        Sprite[] textButton = new Sprite[numberOfButtons];
        Sprite[] hoverButton = new Sprite[numberOfButtons];
        Sprite[] pushButton = new Sprite[numberOfButtons];

        public EventState(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, EventExampleDriver eventDriver){
            this.stateMachine = stateMachine;
            this.driver = driver;
            this.window = window;
            this.textures = textures;
            randGenerator = new Random();
            this.eventDriver = eventDriver;
            setup();
        }

        public void setup(){
            messageScroll = textures.createSprite(textures.messageScroll_, 0, 0, 782, 713);	//MESSAGE SCROLL
            messageScroll.setPosition(500, 300);

            if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
            else FontPath = JdkFontPath;
            Font fontStyle = new Font();
            try {
                fontStyle.loadFromFile(
                        Paths.get(FontPath + FontFile));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

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
            text[0].setString("Attack!");
            text[0].setPosition(driver.getWinWidth() / 2, 280);
            text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

            text[1].setFont(fontStyle);
            text[1].setColor(Color.CYAN);
            text[1].setString("RUN AWAY!");
            text[1].setPosition(driver.getWinWidth() / 2, 350);
            text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);

            for(int i = 0; i < numberOfButtons; i++){
                textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
                hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
                pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
            }
            for(int i = 0; i < numberOfButtons; i++){
                textButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
                pushButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
            }
            for(int i = 0; i < numberOfButtons; i++){
                rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                        textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
                recti[i] = new IntRect(rectf[i]);
            }
            for(int i = 0; i < numberOfButtons; i++){
//                if((recti[i].contains(Mouse.getPosition(window)) && isMouseOver())){
                    textButton[i].setTextureRect(new IntRect(23, 100, 250, 60));
//                }
//                else if(!isMouseOver()){
//                    textButton[i].setTextureRect(new IntRect(23, 21, 250, 60));
//                }
            }
        }

        @Override
        // Update this method with what should be added to the window (using window variable)
        public void execute() {
            textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
            window.draw(textures.ocean);
//            displayMenu();
            for(int i = 0; i < numberOfButtons; i++) {
                window.draw(textButton[i]);
                window.draw(text[i]);
            }
            window.draw(messageScroll);
            for(int i = 0; i < numberOfButtons; i++){
                    window.draw(textButton[i]);
                    window.draw(text[i]);
                }
                window.display();
            try {
                Thread.sleep(10); //1000); // this delays the animation frames if it's too high
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void displayMenu()
        {

            for(int i = 0; i < numberOfButtons; i++){
//                if((recti[i].contains(Mouse.getPosition(window)) && isMouseOver())){
                    window.draw(textButton[i]);
                    window.draw(text[i]);
//                }
//                else if(!isMouseOver()){
//                    window.draw(textButton[i]);
//                    window.draw(text[i]);
//                }
            }
        }

//        public boolean isMouseOver(){
//            for(int i = 0; i < numberOfButtons; i++){
//                if(recti[i].contains(Mouse.getPosition(window))){
//                    return true;
//                }
//            }
//            return false;
//        }

	/*public int getSpriteIndex(int x, int y){
		for(int i = 0; i < maxSprites; i++){
			if (x > leftBound[i] && x < rightBound[i] && y > topBound[i] && y < bottomBound[i]) {
				System.out.println("Island Clicked!");
			}
		}
	}*/

	/*public int getSpriteIndex(){
		return spriteIndex;
	}*/
}