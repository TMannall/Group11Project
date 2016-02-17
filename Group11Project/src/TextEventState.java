import org.jsfml.graphics.*;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Aidan on 16/02/2016.
 */
public class TextEventState extends FSMState{
    /**
     * Event state class for Endless Sea
     */
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private Random randGenerator;
    private EventExampleDriver eventDriver;
    private int[] eventEffects = {0,0,0,0,0,0,0,0,0,0};
    public String attackedText = " \nhas Attacked You!";
    public String titleString = attackedText;

    Sprite messageScroll;
    Text text = new Text();
    Text title;
    IntRect recti;
    FloatRect rectf;
    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";
    private static int titleFontSize = 50;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;


    float leftBound;
    float rightBound;
    float topBound;
    float bottomBound;
    Sprite textButton;
    Sprite hoverButton;
    Sprite pushButton;

    public TextEventState(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, EventExampleDriver eventDriver){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        randGenerator = new Random();
        this.eventDriver = eventDriver;
        setup();
    }

    public void setup(){
        messageScroll = textures.createSprite(textures.messageScroll_, 0, 0, 900, 821);	//MESSAGE SCROLL
        messageScroll.setPosition(driver.getWinWidth() / 2, 400);

        if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;
        Font fontStyle = new Font();
        try {
            fontStyle.loadFromFile(
                    Paths.get(FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        title = new Text(titleString, fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 300);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);


        text = new Text();
        text.setFont(fontStyle);
        text.setColor(Color.RED);
        text.setString("OK");
        text.setPosition(700, 500);
        text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2);


        textButton = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
        hoverButton = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
        pushButton = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
        textButton.setPosition(text.getPosition().x, text.getPosition().y + 8);
        pushButton.setPosition(text.getPosition().x, text.getPosition().y + 8);

        rectf = new FloatRect(textButton.getGlobalBounds().left, textButton.getGlobalBounds().top,
        textButton.getGlobalBounds().width, textButton.getGlobalBounds().height);
        recti = new IntRect(rectf);
    }

    public void execute() {
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        window.draw(textButton);
        window.draw(text);
        window.draw(messageScroll);
        window.draw(title);
        window.draw(textButton);
        displayMenu();
    }

    public void displayMenu()
    {
        window.draw(textButton);
        window.draw(text);
        for (Event event : window.pollEvents())
        {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    leftBound = text.getGlobalBounds().left;
                    rightBound = leftBound + text.getGlobalBounds().width;
                    topBound = text.getGlobalBounds().top;
                    bottomBound = topBound + text.getGlobalBounds().height;
                    // Add events/actions here when islands are clicked on
                        if (xPos > leftBound && xPos < rightBound && yPos > topBound && yPos < bottomBound) {
                            //System.out.println("Island Clicked!");
                            //Island
                            System.out.println("Text Event Happens...");
                            stateMachine.setState(stateMachine.getStates().get(3));
                            break;
                        }
                    }
//                    break;
        }
        window.display();
    }

    public void updateInfo(int[] eventEffects, String eventText)
    {
        this.eventEffects = eventEffects;
        this.titleString = eventText + attackedText;
    }
}
