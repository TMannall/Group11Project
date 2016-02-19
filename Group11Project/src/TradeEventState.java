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
    Text[] text = new Text[4];
    Text[][] statsNames = new Text[amountOfItems][playerStatsList.length];
    Text[][] statsChanges = new Text[amountOfItems][playerStatsList.length];
    Text[] itemName = new Text[amountOfItems];
    Text title;
    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";
    private static int titleFontSize = 30;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;


    float[] leftBound = new float[4];
    float[] rightBound = new float[4];
    float[] topBound = new float[4];
    float[] bottomBound = new float[4];
    Sprite[] textButton = new Sprite[4];
    Sprite[] hoverButton = new Sprite[4];
    Sprite[] pushButton = new Sprite[4];
    IntRect[] recti = new IntRect[4];
    FloatRect[] rectf = new FloatRect[4];
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
        messageScroll.setPosition(driver.getWinWidth() / 2 - 35, 400);

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
        title.setPosition(driver.getWinWidth() / 2 - 35, 150);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);

        for(int i = 0; i < 4; i++)
            text[i] = new Text();

        for (int item = 0; item < amountOfItems; item++)
        {
            itemName[item] = new Text("" + item, fontStyle, 13);
            itemName[item].setPosition(380, 290 + (item * 80));
            itemName[item].setOrigin(text[3].getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            itemName[item].setColor(Color.BLACK);
            itemName[item].setStyle(Text.BOLD);

            text[item].setFont(fontStyle);
            text[item].setColor(Color.RED);
            text[item].setString("Buy Item");
            text[item].setPosition(520, itemName[item].getPosition().y - 14);
            text[item].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);
            text[item].setScale((float)0.5,(float)0.5);

            statsNames[item][0] = new Text(playerStatsList[0], fontStyle, 11);
            statsNames[item][0].setPosition(330, 300 + (item * 80));
            statsNames[item][0].setOrigin(text[3].getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            statsNames[item][0].setColor(Color.BLACK);
            statsNames[item][0].setStyle(Text.BOLD);
            for (int i = 1; i < statsNames[item].length; i++) {
                statsNames[item][i] = new Text(playerStatsList[i], fontStyle, 11);
                statsNames[item][i].setPosition(statsNames[item][i - 1].getPosition().x + 15 + (playerStatsList[i - 1].length() * 7), 300 + (item *80));
                statsNames[item][i].setOrigin(text[3].getLocalBounds().width / 2, title.getLocalBounds().height / 2);
                statsNames[item][i].setColor(Color.BLACK);
                statsNames[item][i].setStyle(Text.BOLD);
            }
        }

        for(int item = 0; item < amountOfItems; item++) {
            statsChanges[item][0] = new Text(Integer.toString(eventEffects[0]), fontStyle, 11);
            statsChanges[item][0].setPosition(statsNames[item][0].getPosition().x, 330 + (item *80));
            statsChanges[item][0].setOrigin(text[3].getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            statsChanges[item][0].setColor(Color.BLACK);
            statsChanges[item][0].setStyle(Text.BOLD);
            for (int i = 1; i < statsNames[item].length; i++) {
                statsChanges[item][i] = new Text(Integer.toString(eventEffects[i]), fontStyle, 11);
                statsChanges[item][i].setPosition(statsNames[item][i].getPosition().x + 15, 350 + (item *80));
                statsChanges[item][i].setOrigin(text[3].getLocalBounds().width / 2, title.getLocalBounds().height / 2);
                statsChanges[item][i].setColor(Color.BLACK);
                statsChanges[item][i].setStyle(Text.BOLD);
            }
        }

        for(int i = 0; i < 3; i++) {
            textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
            hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
            pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
            textButton[i].setPosition(text[i].getPosition().x + 15, text[i].getPosition().y + 5);
            pushButton[i].setPosition(text[i].getPosition().x + 15, text[i].getPosition().y + 5);
            textButton[i].setScale((float)0.5, (float)0.5);
            pushButton[i].setScale((float)0.5, (float)0.5);
        }

        text[3] = new Text();
        text[3].setFont(fontStyle);
        text[3].setColor(Color.RED);
        text[3].setString("OK");
        text[3].setPosition(620, 550);
        text[3].setOrigin(text[3].getLocalBounds().width / 2, text[3].getLocalBounds().height / 2);
        textButton[3] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
        hoverButton[3] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
        pushButton[3] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
        textButton[3].setPosition(text[3].getPosition().x, text[3].getPosition().y + 8);
        pushButton[3].setPosition(text[3].getPosition().x, text[3].getPosition().y + 8);

        for(int i = 0; i < 4; i++) {
            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
            recti[i] = new IntRect(rectf[i]);
        }
    }

    public void execute() {
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        for(int i = 0; i < 4; i++) {
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
        window.draw(messageScroll);
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
            itemName[item].setOrigin(text[item].getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            itemName[item].setColor(Color.BLACK);
            itemName[item].setStyle(Text.BOLD);
            for (int i = 0; i < statsChanges[0].length; i++) {
                statsChanges[item][i] = new Text(Integer.toString(itemStats[item][i]), fontStyle, 11);
                statsChanges[item][i].setPosition(statsNames[item][i].getPosition().x + 65, 350 + (item *80));
                statsChanges[item][i].setOrigin(text[item].getLocalBounds().width / 2, title.getLocalBounds().height / 2);
                statsChanges[item][i].setColor(Color.BLACK);
                statsChanges[item][i].setStyle(Text.BOLD);
            }
        }

        title = new Text(eventDriver.getEventText(), fontStyle, titleFontSize);

        title.setPosition(driver.getWinWidth() / 2 - 35, 200);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);
        for(int i = 0; i < 4; i++) {
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
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

                    for(int i = 0; i < 4; i++){
                        leftBound[i] = textButton[i].getGlobalBounds().left;
                        rightBound[i] = leftBound[i] + textButton[i].getGlobalBounds().width;
                        topBound[i] = textButton[i].getGlobalBounds().top;
                        bottomBound[i] = topBound[i] + textButton[i].getGlobalBounds().height;
                    }
                    // Add events/actions here when islands are clicked on
                    for(int i = 0; i < 4; i++) {
                        if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {

                            switch (i) {
                                case 0:    //Item 1
                                    System.out.println("item 1 Clicked");
                                    for (int x = 0; x < itemStats[0].length; x++)
                                        System.out.println(itemStats[i][x]);
                                    break;
                                case 1: //Item 2
                                    System.out.println("item 2 Clicked");
                                    for (int x = 0; x < itemStats[0].length; x++)
                                        System.out.println(itemStats[i][x]);
                                    break;
                                case 2: //Item 3
                                    System.out.println("item 3 Clicked");
                                    for (int x = 0; x < itemStats[0].length; x++)
                                        System.out.println(itemStats[i][x]);
                                    break;
                                case 3: //OK
                                    System.out.println("exited");
                                    stateMachine.setState(stateMachine.getStates().get(3));
                                    break;
                            }


                            break;
                        }
                    }
            }
//                    break;
        }
        window.display();
    }
}
