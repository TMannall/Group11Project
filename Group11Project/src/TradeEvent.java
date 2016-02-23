import org.jsfml.graphics.*;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import java.util.Random;

/**
 * Trade Event state class for dealing with the trade events the player comes across, this includes the displaying
 * of the different items the user can buy along with support for this.
*/

//public Sprite uiIconGold = createSprite(userInterface, 1168, 448, 50, 50);
//playerShip.getCurrentGold();
//playerShip.getCurrentFood();
//playerShip.getCurrentWater();

public class TradeEvent extends Events{
    private PlayerShip playerShip;
    private static final int amountOfItems = 3;
    private String[] itemStrings;
    private int[][] itemStats;

    public Sprite[] icons = new Sprite[3];
    public Text[] stats = new Text[3];
    public String[] statNames = new String[3];

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


        icons = new Sprite[3];
        icons[0] = textures.createSprite(textures.userInterface, 1168, 448, 50, 50);
        icons[1] = textures.createSprite(textures.userInterface, 1168, 383, 50, 50);
        icons[2] = textures.createSprite(textures.userInterface, 1168, 511, 50, 50);


//        public Text[] stats = new Text[3];

        //public Sprite uiIconGold = createSprite(userInterface, 1168, 448, 50, 50);
//;
//playerShip.getCurrentFood();
//playerShip.getCurrentWater();

        // Get relevant stuff from database
        this.itemStats = eventGenerator.getItemStats();
        this.itemStrings = eventGenerator.getItemNames();

        // Set up scroll + title
        messageScroll = textures.createSprite(textures.ingameWindow_, 0, 0, 800, 500);    //MESSAGE SCROLL
        messageScroll.setPosition(driver.getWinWidth() / 2, 360);
        messageScroll.setScale((float) 1.25, (float)1.25);

        title = new Text(eventGenerator.getEventText(), fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2 - 35, 150);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);

        for(int i = 0; i < 3; i++)
        {
            icons[i].setPosition(1400, 300+(i*100));
            icons[i].setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        }

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
            btn[i].setColor(driver.BROWN);
            btn[i].setStyle(Text.REGULAR);

            textButton[i] = textures.createSprite(textures.userInterface, 300, 21, 125, 60);
            hoverButton[i] = textures.createSprite(textures.userInterface, 300, 100, 125, 60);
            pushButton[i] = textures.createSprite(textures.userInterface, 300, 179, 125, 60);

            textButton[i].setPosition(btn[i].getPosition().x + 22, btn[i].getPosition().y + 14);
            pushButton[i].setPosition(btn[i].getPosition().x, btn[i].getPosition().y + 8);
        }

        btn[3] = new Text("Leave", fontStyle, 28);
        btn[3].setPosition(606, 578);
        btn[3].setOrigin(0,0);
        btn[3].setColor(driver.BROWN);
        btn[3].setStyle(Text.REGULAR);

        textButton[3] =  textures.createSprite(textures.userInterface, 23, 21, 250, 60);
        hoverButton[3] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
        pushButton[3] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);

        textButton[3].setPosition(640, 595);
        pushButton[3].setPosition(640, 595);

        // Set bounds for buy buttons
        for(int i = 0; i < amountOfItems + 1; i++){
            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
            recti[i] = new IntRect(rectf[i]);
        }
    }

    public void execute() {
        statNames[0] = Integer.toString(playerShip.getCurrGold());
        statNames[1] = Integer.toString(playerShip.getCurrFood());
        statNames[2] = Integer.toString(playerShip.getCurrWater());
        for(int i = 0; i < 3; i++)
        {
            stats[i] = new Text(statNames[i], fontStyle, titleFontSize-10);
            stats[i].setPosition(1400, 350+(i*100));
            stats[i].setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
            stats[i].setColor(Color.BLACK);
            stats[i].setStyle(Text.BOLD);
        }
        if(consumeResources) {
            consumeResources = false;
            consumeResources();
        }
        window.clear();
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        window.draw(messageScroll);

        for(int i = 0; i < 3; i++)
        {
            window.draw(stats[i]);
            window.draw(icons[i]);
        }


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