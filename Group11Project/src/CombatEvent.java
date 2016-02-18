import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.Random;

public class CombatEvent extends Events {
    private int[] eventEffects = {0,0,0,0,0,0,0,0,0,0};
    public String attackedText = "";
    public String titleString = attackedText;

    Sprite messageScroll;
    private static int numberOfButtons = 2;
    Text[] text = new Text[numberOfButtons];
    Text title;
    IntRect[] recti = new IntRect[numberOfButtons];
    FloatRect[] rectf = new FloatRect[numberOfButtons];

    float[] leftBound = new float[2];
    float[] rightBound = new float[2];
    float[] topBound = new float[2];
    float[] bottomBound = new float[2];
    Sprite[] textButton = new Sprite[numberOfButtons];
    Sprite[] hoverButton = new Sprite[numberOfButtons];
    Sprite[] pushButton = new Sprite[numberOfButtons];

    boolean activeCombat = false;       // Set to true to disable "entering combat" alert & begin encounter

    private UI ui;
    private PlayerShip playerShip;
    private EnemyShip enemyShip;


    public CombatEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, Ship playerShip, UI ui){
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator);
        setup();
    }

    public void setup(){
        messageScroll = textures.createSprite(textures.messageScroll_, 0, 0, 900, 821);	//MESSAGE SCROLL
        messageScroll.setPosition(driver.getWinWidth() / 2, 400);

        title = new Text(eventGenerator.getEventText(), fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 300);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }
        text[0].setFont(fontStyle);
        text[0].setColor(Color.RED);
        text[0].setString("ATTACK!");
        text[0].setPosition(500, 500);
        text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(fontStyle);
        text[1].setColor(Color.YELLOW);
        text[1].setString("RUN AWAY!");
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

        //playerShip = new PlayerShip(textures, driver, window, randGenerator, Ship.ShipType.PLAYER, (float)0.5, 800, 1020);
        enemyShip = new EnemyShip(textures, driver, window, randGenerator, Ship.ShipType.STANDARD, (float)0.5, 600, 420);

        ui = new UI(textures, driver, window, playerShip, enemyShip);
        playerShip.setUI(ui);
    }

    public void execute(){
        if(activeCombat)
            executeCombat();
        else
            displayAlert();

        window.display();
    }

    public void displayAlert(){
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);

        window.draw(messageScroll);
        window.draw(title);
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
                        leftBound[i] = text[i].getGlobalBounds().left;
                        rightBound[i] = leftBound[i] + text[i].getGlobalBounds().width;
                        topBound[i] = text[i].getGlobalBounds().top;
                        bottomBound[i] = topBound[i] + text[i].getGlobalBounds().height;
                    }
                    // Add events/actions here when islands are clicked on
                    for(int i = 0; i < 2; i++) {
                        if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {
                            //System.out.println("Island Clicked!");
                            switch (i) {
                                case 0:    // Attack
                                    System.out.println("Attack Clicked");
                                    activeCombat = true;
                                    break;
                                case 1: // Run
                                    System.out.println("Run Clicked");
                                    stateMachine.setState(stateMachine.getStates().get(3));
                                    break;
                            }
                        }
                    }
                    break;
            }
        }

    }

    public void executeCombat(){
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        playerShip.draw();
        enemyShip.draw();
        ui.draw();

        if(!playerShip.isGunLoaded())
            playerShip.checkReload();

        if(!enemyShip.isGunLoaded())
            enemyShip.checkReload();
        else
            actionAI();     //NOTE AI WILL ATTACK AS SOON AS GUNS RELOAD - CHANGE THIS LATER TO WAIT RANDOM TIME BASED ON DIFFICULTY

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    ShipSection clicked = enemyShip.validateClicked(playerShip, xPos, yPos);
                    if(clicked != null){
                        playerShip.attack(clicked);
                        checkWin();
                    }
                    break;
                // Not sure why this doesn't
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.ESCAPE) {
                        stateMachine.setState(stateMachine.getStates().get(0));
                        break;
                    } else if (keyEvent.key == Keyboard.Key.M) {
                        stateMachine.setState(stateMachine.getStates().get(3));
                    }
                    break;
            }
        }
    }

    public void actionAI(){
        // choose to attack or run away
        if(enemyShip.getHullHP() < 20)
            System.out.println("RUN AWAY!");
        else{
            enemyShip.attack(playerShip);
            if(playerShip.getHullHP() <= 0){
                stateMachine.setState(stateMachine.getStates().get(4));
            }
        }
    }

    public void checkWin(){
        if(enemyShip.getHullHP() <= 0){
            // Create new temporary success state & move to it
            FSMState success = new SuccessState(stateMachine, driver, window, textures);
            stateMachine.setState(success);
        }
    }
}
