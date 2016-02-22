import org.jsfml.graphics.*;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import java.util.Random;

/**
 * Trade Event state class for dealing with the trade events the player comes across, this includes the displaying
 * of the different items the user can buy along with support for this.
*/
public class TradeEvent extends Events{
    private PlayerShip playerShip;
    private static final int amountOfItems = 3;
    private String[] itemStrings;
    private int[][] itemStats;

    Sprite messageScroll;
    Text[] btn = new Text[4];
    Text[] itemName = new Text[amountOfItems];
    Text[] itemPrice = new Text[amountOfItems];
    boolean[] itemBought = {false, false, false};           // Used to ensure each item can only be bought once
    Text title;

    Sprite[] textButton = new Sprite[4];
    Sprite[] hoverButton = new Sprite[4];
    Sprite[] pushButton = new Sprite[4];

    IntRect[] recti = new IntRect[4];
    FloatRect[] rectf = new FloatRect[4];

    public TradeEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, SoundFX sound, PlayerShip playerShip){
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound);
        this.playerShip = playerShip;
        setup();
    }

    public void setup() {
        playerShip.addEventComplete();
        System.out.println("EVENTS COMPLETED: " + playerShip.getEventsCompleted());

        // Get relevant stuff from database
        this.itemStats = eventGenerator.getItemStats();
        this.itemStrings = eventGenerator.getItemNames();

        // Set up scroll + title
        messageScroll = textures.createSprite(textures.ingameWindow_, 0, 0, 800, 500);    //MESSAGE SCROLL
        messageScroll.setPosition(driver.getWinWidth() / 2, 380);
        messageScroll.setScale((float) 1.25, 1);

        title = new Text(eventGenerator.getEventText(), fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2 - 35, 150);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);

        // Add item names to array for displaying later + set up "buy" buttons
        for(int i = 0; i < amountOfItems; i++){
            itemName[i] = new Text(itemStrings[i], fontStyle, 24);
            itemName[i].setPosition(320, 290 + (100*i));
            itemName[i].setOrigin(0,0);
            itemName[i].setColor(Color.BLACK);
            itemName[i].setStyle(Text.REGULAR);

            itemPrice[i] = new Text(Integer.toString((-1) * itemStats[i][0]) + "G", fontStyle, 24);
            itemPrice[i].setPosition(620, 290 + (100*i));
            itemPrice[i].setOrigin(0,0);
            itemPrice[i].setColor(Color.BLACK);
            itemPrice[i].setStyle(Text.REGULAR);

            btn[i] = new Text("Buy", fontStyle, 24);
            btn[i].setPosition(820, 290 + (100*i));
            btn[i].setOrigin(0,0);
            btn[i].setColor(Color.CYAN);
            btn[i].setStyle(Text.REGULAR);

            textButton[i] = textures.createSprite(textures.userInterface, 300, 21, 125, 60);
            hoverButton[i] = textures.createSprite(textures.userInterface, 300, 100, 125, 60);
            pushButton[i] = textures.createSprite(textures.userInterface, 300, 179, 125, 60);

            textButton[i].setPosition(btn[i].getPosition().x + 22, btn[i].getPosition().y + 14);
            pushButton[i].setPosition(btn[i].getPosition().x, btn[i].getPosition().y + 8);
        }

        btn[3] = new Text("Leave", fontStyle, 28);
        btn[3].setPosition(564, 632);
        btn[3].setOrigin(0,0);
        btn[3].setColor(Color.CYAN);
        btn[3].setStyle(Text.REGULAR);

        textButton[3] =  textures.createSprite(textures.userInterface, 23, 21, 250, 60);
        hoverButton[3] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
        pushButton[3] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);

        textButton[3].setPosition(600, 650);
        pushButton[3].setPosition(600, 700);

        // Set bounds for buy buttons
        for(int i = 0; i < amountOfItems + 1; i++){
            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
            recti[i] = new IntRect(rectf[i]);
        }
    }

    public void execute() {
        if(consumeResources) {
            consumeResources = false;
            consumeResources();
        }
        window.clear();
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        window.draw(messageScroll);

        displayItems();

        window.display();

        for (Event event : window.pollEvents()){
            switch (event.type){
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                        for(int i = 0; i < amountOfItems + 1; i++){
                            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
                        }
                        // Item 1
                        if(rectf[0].contains(xPos, yPos)){
                            buyItem(1);
                        }
                        // Item 2
                        if(rectf[1].contains(xPos, yPos)){
                            buyItem(2);
                        }
                        // Item 3
                        if(rectf[2].contains(xPos, yPos)){
                            buyItem(3);
                        }
                        // Leave
                        if(rectf[3].contains(xPos, yPos)){
                            stateMachine.setState(stateMachine.getStates().get(4));
                        }
                        break;


            }
        }

    }

    public void displayItems(){
        window.draw(title);

        for(int i = 0; i < amountOfItems; i++){
            window.draw(itemName[i]);
            window.draw(itemPrice[i]);

            if(!itemBought[i]) {
                if ((recti[i].contains(Mouse.getPosition(window)) && isMouseOver()))
                    textButton[i].setTextureRect(new IntRect(300, 100, 125, 60));
                else if (!isMouseOver())
                    textButton[i].setTextureRect(new IntRect(300, 21, 125, 60));
                window.draw(textButton[i]);
            }
            window.draw(btn[i]);
        }

        // Display leave button
        if ((recti[3].contains(Mouse.getPosition(window)) && isMouseOver()))
            textButton[3].setTextureRect(new IntRect(23, 100, 250, 60));
        else if (!isMouseOver())
            textButton[3].setTextureRect(new IntRect(23, 21, 250, 60));

        window.draw(textButton[3]);
        window.draw(btn[3]);
    }

    public void buyItem(int item){
        if(itemBought[item - 1])
            return;         // Item already bought;
        else if(playerShip.getCurrGold() < -1 * itemStats[item - 1][0]) {
            System.out.println("YOU CANNOT AFFORD THIS!");
            return;         // Player can't afford
        }
        else{
            itemBought[item - 1] = true;
            playerShip.addGold(itemStats[item - 1][0]);
            playerShip.addFood(itemStats[item - 1][1]);
            playerShip.addWater(itemStats[item - 1][2]);
            playerShip.damageHull(itemStats[item - 1][3]);
            playerShip.addGunStr(itemStats[item - 1][4]);
            playerShip.guns.repair(itemStats[item - 1][5]);
            playerShip.masts.repair(itemStats[item - 1][6]);
            playerShip.bridge.repair(itemStats[item - 1][7]);
            playerShip.hold.repair(itemStats[item - 1][8]);
            playerShip.quarters.repair(itemStats[item - 1][9]);
            playerShip.addReloadBoost(itemStats[item - 1][10]);
            playerShip.addMastSpeed(itemStats[item - 1][11]);
            playerShip.addBridgeDefence(itemStats[item - 1][12]);
            playerShip.addQuartersRegainStr(itemStats[item - 1][13]);
            playerShip.addMaxWater(itemStats[item - 1][14]);
            playerShip.addMaxFood(itemStats[item - 1][15]);

            btn[item-1].setString("Bought!");
            btn[item-1].setPosition(801, 290 + (100*(item-1)));
        }
    }

    public boolean isMouseOver(){
        for(int i = 0; i < amountOfItems + 1; i++){
            if(recti[i].contains(Mouse.getPosition(window))){
                return true;
            }
        }
        return false;
    }
}