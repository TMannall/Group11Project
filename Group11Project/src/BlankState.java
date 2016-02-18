import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseEvent;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class BlankState extends FSMState{
	
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private EventExampleDriver eventDriver;
	
    private static int numberOfButtons = 2;
    Text[] text = new Text[numberOfButtons];
	IntRect[] recti = new IntRect[numberOfButtons];
	FloatRect[] rectf = new FloatRect[numberOfButtons];

    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static String FontFile = "vinque.ttf";
    private String FontPath;

    private static String Title = "ENDLESS SEA";

    public BlankState(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures) {
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
		
        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }
		
        text[0].setFont(fontStyle);
        text[0].setColor(Color.CYAN);
        text[0].setString("Map");
        text[0].setPosition(driver.getWinWidth() / 2, 260);
		text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(fontStyle);
        text[1].setColor(Color.CYAN);
        text[1].setString("Ship");
        text[1].setPosition(driver.getWinWidth() / 2, 330);
		text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);
	}
    public void execute() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        displayState();
    }

    public void displayState() {
		
		Sprite[] textButton = new Sprite[numberOfButtons];
		
		for(int i = 0; i < numberOfButtons; i++){
			textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
		}
		
		for(int i = 0; i < numberOfButtons; i++){
			textButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
		}
		
        for(int i = 0; i < numberOfButtons; i++) {
			window.draw(textButton[i]);
            window.draw(text[i]);
        }
		
		for(int i = 0; i < numberOfButtons; i++){
			rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top, 
									textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
			recti[i] = new IntRect(rectf[i]);
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
						//MAP
						if(rectf[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
							stateMachine.setState(stateMachine.getStates().get(3));
						}
						//SHIP
						if(rectf[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
							stateMachine.setState(stateMachine.getStates().get(2));
						}
					}
					break;
					}
				}
			window.display();
		}
}
