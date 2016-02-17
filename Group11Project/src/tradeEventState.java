import org.jsfml.graphics.*;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Aidan on 16/02/2016.
 */
public class TradeEventState extends FSMState{
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
    private static final String[] playerStatsList = {"Gold", "Food", "Water", "Hull", "Cannons", "Guns", "Masts", "Bridge", "Hold", "Quarters"};
    public String titleString = "";
    private static final int amountOfItems = 3;
    private String[] itemStrings;
    private int[][] itemStats;

    Sprite messageScroll;
    Text text = new Text();
    Text[][] statsNames = new Text[amountOfItems][playerStatsList.length];
    Text[][] statsChanges = new Text[amountOfItems][playerStatsList.length];
    Text[] itemName = new Text[amountOfItems];
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
    Font fontStyle;

    public TradeEventState(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, EventExampleDriver eventDriver){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        randGenerator = new Random();
        this.eventDriver = eventDriver;
        setup();
    }

    public void setup() {
        messageScroll = textures.createSprite(textures.messageScroll_, 0, 0, 900, 821);    //MESSAGE SCROLL
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
        title.setPosition(driver.getWinWidth() / 2, 150);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);

        for (int item = 0; item < amountOfItems; item++)
        {
            itemName[item] = new Text("" + item, fontStyle, 13);
            itemName[item].setPosition(380, 290 + (item * 80));
            itemName[item].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            itemName[item].setColor(Color.BLACK);
            itemName[item].setStyle(Text.BOLD);

            statsNames[item][0] = new Text(playerStatsList[0], fontStyle, 11);
            statsNames[item][0].setPosition(380, 300 + (item * 80));
            statsNames[item][0].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            statsNames[item][0].setColor(Color.BLACK);
            statsNames[item][0].setStyle(Text.BOLD);
            for (int i = 1; i < statsNames[item].length; i++) {
                statsNames[item][i] = new Text(playerStatsList[i], fontStyle, 11);
                statsNames[item][i].setPosition(statsNames[item][i - 1].getPosition().x + 15 + (playerStatsList[i - 1].length() * 7), 300 + (item *80));
                statsNames[item][i].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
                statsNames[item][i].setColor(Color.BLACK);
                statsNames[item][i].setStyle(Text.BOLD);
            }
        }

        for(int item = 0; item < amountOfItems; item++) {
            statsChanges[item][0] = new Text(Integer.toString(eventEffects[0]), fontStyle, 11);
            statsChanges[item][0].setPosition(380, 330 + (item *80));
            statsChanges[item][0].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            statsChanges[item][0].setColor(Color.BLACK);
            statsChanges[item][0].setStyle(Text.BOLD);
            for (int i = 1; i < statsNames[item].length; i++) {
                statsChanges[item][i] = new Text(Integer.toString(eventEffects[i]), fontStyle, 11);
                statsChanges[item][i].setPosition(statsNames[item][i - 1].getPosition().x + 15 + (playerStatsList[i - 1].length() * 7), 350 + (item *80));
                statsChanges[item][i].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
                statsChanges[item][i].setColor(Color.BLACK);
                statsChanges[item][i].setStyle(Text.BOLD);
            }
        }

        text = new Text();
        text.setFont(fontStyle);
        text.setColor(Color.RED);
        text.setString("OK");
        text.setPosition(650, 550);
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
        window.draw(textButton);
        displayMenu();
    }

    public void displayMenu()
    {
        this.eventEffects = eventDriver.getEventEffects();
        this.itemStats = eventDriver.getItemStats();
        this.itemStrings = eventDriver.getItemNames();
//        statsChanges[0] = new Text(Integer.toString(eventEffects[0]), fontStyle, 11);
//        statsChanges[0].setPosition(400, 430);
//        statsChanges[0].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
//        statsChanges[0].setColor(Color.BLACK);
//        statsChanges[0].setStyle(Text.BOLD);
        for(int item = 0; item < amountOfItems; item++) {
            itemName[item] = new Text(itemStrings[item], fontStyle, 13);
            itemName[item].setPosition(380, 290 + (item * 80));
            itemName[item].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            itemName[item].setColor(Color.BLACK);
            itemName[item].setStyle(Text.BOLD);
            for (int i = 0; i < statsChanges[0].length; i++) {
                statsChanges[item][i] = new Text(Integer.toString(itemStats[item][i]), fontStyle, 11);
                statsChanges[item][i].setPosition(statsNames[item][i].getPosition().x + 15, 350 + (item *80));
                statsChanges[item][i].setOrigin(text.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
                statsChanges[item][i].setColor(Color.BLACK);
                statsChanges[item][i].setStyle(Text.BOLD);
            }
        }

        title = new Text(eventDriver.getEventText(), fontStyle, titleFontSize);

        title.setPosition(driver.getWinWidth() / 2, 200);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);
        window.draw(textButton);
        window.draw(text);
        window.draw(title);
        for(int item = 0; item < amountOfItems; item++)
        {
            window.draw(itemName[item]);
            for(int i = 0; i < statsNames[0].length; i++)
            {
                window.draw(statsNames[item][i]);
                window.draw(statsChanges[item][i]);
            }
        }

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
                        int[] eventEffects = eventDriver.getEventEffects();
                        for (int i  = 0; i < eventEffects.length; i++)
                            System.out.println(eventEffects[i]);
                        stateMachine.setState(stateMachine.getStates().get(3));
                        break;
                    }
            }
//                    break;
        }
        window.display();
    }
}
