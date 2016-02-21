import org.jsfml.graphics.*;
import org.jsfml.window.event.Event;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 * @Author Aidan Lennie on 25/01/2016.
 */
public class AssExpEventState extends FSMState{
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
    public String attackedText = "";
    public String titleString = attackedText;

    Sprite messageScroll;
    private static int numberOfButtons = 2;
    Text[] text = new Text[numberOfButtons];
    Text title;
    IntRect[] recti = new IntRect[numberOfButtons];
    FloatRect[] rectf = new FloatRect[numberOfButtons];
    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";
    private static int titleFontSize = 30;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;


    float[] leftBound = new float[2];
    float[] rightBound = new float[2];
    float[] topBound = new float[2];
    float[] bottomBound = new float[2];
    Sprite[] textButton = new Sprite[numberOfButtons];
    Sprite[] hoverButton = new Sprite[numberOfButtons];
    Sprite[] pushButton = new Sprite[numberOfButtons];
    Font fontStyle;

    public AssExpEventState(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, EventExampleDriver eventDriver){
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
        fontStyle = new Font();
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

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }
        text[0].setFont(fontStyle);
        text[0].setColor(Color.RED);
        text[0].setString("ACCEPT!");
        text[0].setPosition(500, 500);
        text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(fontStyle);
        text[1].setColor(Color.YELLOW);
        text[1].setString("DECLINE!");
        text[1].setPosition(770, 500);
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
    }

    public void execute() {
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        for(int i = 0; i < numberOfButtons; i++) {
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
        window.draw(messageScroll);
        window.draw(title);
        for(int i = 0; i < numberOfButtons; i++){
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
        displayMenu();
    }

    public void displayMenu()
    {
        title = new Text(eventDriver.getEventText(), fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 300);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);
        for(int i = 0; i < numberOfButtons; i++){
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    for(int i = 0; i < 2; i++){
                        leftBound[i] = textButton[i].getGlobalBounds().left;
                        rightBound[i] = leftBound[i] + textButton[i].getGlobalBounds().width;
                        topBound[i] = textButton[i].getGlobalBounds().top;
                        bottomBound[i] = topBound[i] + textButton[i].getGlobalBounds().height;
                    }
                    // Add events/actions here when islands are clicked on
                    for(int i = 0; i < 2; i++) {
                        if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {
                            //System.out.println("Island Clicked!");
                            switch (i) {
                                case 0:    //Island 1
                                    System.out.println("Accepted");
                                    stateMachine.setState(stateMachine.getStates().get(12));
                                    break;
                                case 1: //Island 2
                                    System.out.println("Declined");
                                    stateMachine.setState(stateMachine.getStates().get(3));
                                    break;
                            }
                        }
                    }
                    break;
            }
        }
        window.display();
    }
}
