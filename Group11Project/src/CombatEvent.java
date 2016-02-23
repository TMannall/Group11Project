import org.jsfml.graphics.*;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import java.util.Random;

/**
 * Combat Event state class for dealing with the combat events, including setting difficulty and initiating the AI.
 */
public class CombatEvent extends Events {
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

    protected UI ui;
    protected PlayerShip playerShip;
    protected EnemyShip enemyShip;
    protected EnemyShip.Difficulty difficulty;

    public CombatEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, SoundFX sound, PlayerShip playerShip) {
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound);
        this.playerShip = playerShip;
        sound.playBackgroundMusic("music_combat");
        setup();
    }

    public void setup() {
        messageScroll = textures.createSprite(textures.ingameWindow_, 0, 0, 800, 500);    //MESSAGE SCROLL
        messageScroll.setPosition(driver.getWinWidth() / 2, 400);

        setTitle();

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
        text[1].setString("RETREAT!");
        text[1].setPosition(770, 500);
        text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);

        for (int i = 0; i < numberOfButtons; i++) {
            textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
            hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
            pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
        }
        for (int i = 0; i < numberOfButtons; i++) {
            textButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
            pushButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
        }
        for (int i = 0; i < numberOfButtons; i++) {
            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
            recti[i] = new IntRect(rectf[i]);
        }

        chooseDifficulty();
        enemyShip = new EnemyShip(textures, driver, window, randGenerator, sound, Ship.ShipType.STANDARD, (float) 0.5, 600, 420, difficulty);

        ui = new UI(textures, driver, window, playerShip, enemyShip);
        playerShip.setEnemyShip(enemyShip);
        playerShip.setUI(ui);
    }

    public void execute() {
        if(consumeResources) {
            consumeResources = false;
            consumeResources();
        }
        if (activeCombat)
            executeCombat();
        else
            displayAlert();

        window.display();
    }

    public void displayAlert() {
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);

        window.draw(messageScroll);
        window.draw(title);
        for (int i = 0; i < numberOfButtons; i++) {
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
                    for (int i = 0; i < 2; i++) {
                        leftBound[i] = textButton[i].getGlobalBounds().left;
                        rightBound[i] = leftBound[i] + textButton[i].getGlobalBounds().width;
                        topBound[i] = textButton[i].getGlobalBounds().top;
                        bottomBound[i] = topBound[i] + textButton[i].getGlobalBounds().height;
                    }
                    // Add events/actions here when islands are clicked on
                    for (int i = 0; i < 2; i++) {
                        if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {
                            //System.out.println("Island Clicked!");
                            switch (i) {
                                case 0:    // Attack
                                    System.out.println("Attack Clicked");
                                    activeCombat = true;
                                    break;
                                case 1: // Run
                                    System.out.println("Run Clicked");
                                    FSMState run = new AfterEvent(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound, AfterEvent.Consequence.COMBAT_PLAYER_RETREAT);
                                    stateMachine.setState(run);
                                    break;
                            }
                        }
                    }
                    break;
            }
        }

    }

    public void executeCombat() {
        window.clear();
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        enemyShip.draw();
        playerShip.draw();
        ShipSection hovered = mouseOver();
        if (hovered != null)
            window.draw(hovered.sectionHighlight);
        ui.draw();

        // Cannon animation
        playerShip.animateGuns();
        enemyShip.animateGuns();

        playerShip.animateMarine();

        if (!playerShip.isGunLoaded())
            playerShip.checkReload();

        if (!enemyShip.isGunLoaded())
            enemyShip.checkReload();
        else
            actionAI();     //NOTE AI WILL ATTACK AS SOON AS GUNS RELOAD - CHANGE THIS LATER TO WAIT RANDOM TIME BASED ON DIFFICULTY


        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    ShipSection clicked = enemyShip.validateClicked(playerShip, xPos, yPos);
                    if (clicked != null) {
                        playerShip.attack(clicked);
                        checkWin();
                    }
                    break;
            }
        }
    }

    public void actionAI() {
        boolean attack;

        // Test whether to attack or retreat
        // Test if masts are still in tact; if not, enemy cannot retreat and must always attack until it or player dies
        if (!enemyShip.masts.isTargetable())
            attack = true;
        else {
            // Masts are OK, can retreat if wanted
            if (enemyShip.getHullHP() < 50 && enemyShip.getHullHP() >= 25) {
                // V. low chance to retreat
                double rand = randGenerator.nextDouble();
                if (rand >= .95) {
                    attack = false;
                } else
                    attack = true;
            } else if (enemyShip.getHullHP() < 25) {
                // Higher chance to retreat
                double rand = randGenerator.nextDouble();
                if (rand >= .90) {
                    attack = false;
                } else {
                    attack = true;
                }
            } else {
                attack = true;
            }
        }

        if (attack) {
            enemyShip.attack(playerShip);
            if (playerShip.getHullHP() <= 0) {
                stateMachine.setState(stateMachine.getStates().get(7));     // Game over, player ship destroyed
                sound.stopBackgroundMusic();
            }
        }
        // Retreat instead
        else {
            FSMState success = new AfterEvent(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound, AfterEvent.Consequence.COMBAT_AI_RETREAT);
            stateMachine.setState(success);
            sound.stopBackgroundMusic();
        }
    }

    public void checkWin() {
        if (enemyShip.getHullHP() <= 0) {
            // Create new temporary success state & move to it
            FSMState success = new AfterEvent(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound, AfterEvent.Consequence.COMBAT_KILL, difficulty);
            stateMachine.setState(success);
            sound.stopBackgroundMusic();
        }
    }

    public ShipSection mouseOver() {
        ShipSection hovered = enemyShip.validateHover(Mouse.getPosition(window).x, Mouse.getPosition(window).y);
        if (hovered != null)
            return hovered;

        hovered = playerShip.validateHover(Mouse.getPosition(window).x, Mouse.getPosition(window).y);
        if (hovered != null)
            return hovered;

        return null;
    }

    public void chooseDifficulty(){
        if(playerShip.getEventsCompleted() < 4)
            difficulty = EnemyShip.Difficulty.EASY;
        else if(playerShip.getEventsCompleted() < 9)
            difficulty = EnemyShip.Difficulty.MEDIUM;
        else
            difficulty = EnemyShip.Difficulty.HARD;
    }

    public void setTitle(){
        title = new Text(eventGenerator.getEventText(), fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 300);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);
    }
}