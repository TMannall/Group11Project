import org.jsfml.graphics.*;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class AfterEvent extends Events{
    public enum Consequence {
        COMBAT_KILL, COMBAT_AI_RETREAT, COMBAT_PLAYER_RETREAT,
        ASSIST_ACCEPT, EXPLORE_ACCEPT
    }

    private PlayerShip playerShip;

    private int[] eventEffects = {0,0,0,0,0,0,0,0,0,0};
    private static final String[] playerStatsList = {"Gold", "Food", "Water", "Hull HP", "Cannons", "Cannon HP", "Mast HP", "Bridge HP", "Hold HP", "Quarters HP"};
    private static final String[] playerStatsListDisplay = {"Gold: ", "Food: ", "Water: ", "Hull HP: ", "Cannon HP: ", "Mast HP: ", "Bridge HP: ", "Hold HP: ", "Quarters HP: "};

    Sprite messageScroll;
    Text btn;
    Text[] statsNames = new Text[playerStatsListDisplay.length];
    Text[] currStats = new Text[playerStatsListDisplay.length];
    Text[] statsChanges = new Text[playerStatsList.length];
    Text title;
    IntRect recti;
    FloatRect rectf;

    float leftBound;
    float rightBound;
    float topBound;
    float bottomBound;
    Sprite textButton;
    Sprite hoverButton;
    Sprite pushButton;

    Consequence consequence;
    EnemyShip.Difficulty combatDifficulty;

    public AfterEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, SoundFX sound, Consequence consequence){
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound);
        this.consequence = consequence;
        this.playerShip = driver.getPlayerShip();
        setup();
    }

    public AfterEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, SoundFX sound, Consequence consequence, EnemyShip.Difficulty difficulty){
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound);
        this.consequence = consequence;
        this.playerShip = driver.getPlayerShip();
        combatDifficulty = difficulty;
        setup();
    }

    public void setup(){
        playerShip.addEventComplete();
        System.out.println("EVENTS COMPLETED: " + playerShip.getEventsCompleted());


        // Get consequences/stat changes & apply them to player for assist/exploration events
        this.eventEffects = eventGenerator.getEventEffects();
        if(consequence == Consequence.ASSIST_ACCEPT || consequence == Consequence.EXPLORE_ACCEPT)
            applyChanges();
        else if(consequence == Consequence.COMBAT_KILL)
            applyCombatChanges();

        // Set up scroll
        messageScroll = textures.createSprite(textures.messageScroll_, 0, 0, 900, 821);	//MESSAGE SCROLL
        messageScroll.setPosition(driver.getWinWidth() / 2, 380);
        messageScroll.setScale((float)1.25, 1);

        genMessage();       // Generate title based on what event this is displayed after

        // Get current stats of ship
        currStats[0] = new Text(Integer.toString(playerShip.getCurrGold()), fontStyle, 24);
        currStats[1] = new Text(Integer.toString(playerShip.getCurrFood()) + "/" + Integer.toString(playerShip.getMaxFood()), fontStyle, 24);
        currStats[2] = new Text(Integer.toString(playerShip.getCurrWater()) + "/" + Integer.toString(playerShip.getMaxWater()), fontStyle, 24);
        currStats[3] = new Text(Integer.toString(playerShip.getHullHP()) + "/100", fontStyle, 24);
        currStats[4] = new Text(Integer.toString(playerShip.guns.getHP()) + "/100", fontStyle, 24);
        currStats[5] = new Text(Integer.toString(playerShip.masts.getHP()) + "/100", fontStyle, 24);
        currStats[6] = new Text(Integer.toString(playerShip.bridge.getHP()) + "/100", fontStyle, 24);
        currStats[7] = new Text(Integer.toString(playerShip.hold.getHP()) + "/100", fontStyle, 24);
        currStats[8] = new Text(Integer.toString(playerShip.quarters.getHP()) + "/100", fontStyle, 24);

        // Set up array of Text to display each stat + position of current stats
        for(int i = 0; i < 5; i++){
            statsNames[i] = new Text(playerStatsListDisplay[i], fontStyle, 24);
            statsNames[i].setPosition(280, 270 +(90 * i));
            statsNames[i].setOrigin(0, 0);
            statsNames[i].setColor(Color.BLACK);
            statsNames[i].setStyle(Text.REGULAR);

            currStats[i].setPosition(400, 270 + (90 * i));
            currStats[i].setOrigin(0, 0);
            currStats[i].setColor(Color.BLACK);
            currStats[i].setStyle(Text.REGULAR);
        }
        for(int c = 4; c < playerStatsListDisplay.length; c++){
            statsNames[c] = new Text(playerStatsListDisplay[c], fontStyle, 24);
            statsNames[c].setPosition(640, 270 + (68 * (c - 4)));
            statsNames[c].setOrigin(0, 0);
            statsNames[c].setColor(Color.BLACK);
            statsNames[c].setStyle(Text.REGULAR);

            currStats[c].setPosition(840, 270 + (68 * (c - 4)));
            currStats[c].setOrigin(0, 0);
            currStats[c].setColor(Color.BLACK);
            currStats[c].setStyle(Text.REGULAR);
        }

        // Set up array of stat changes
        for(int j = 0; j < 5; j++){
            int change = eventEffects[j];
            String changeStr;
            statsChanges[j] = new Text("", fontStyle, 24);
            if(change < 0){
                changeStr = "(-" + Integer.toString((-1) * change) + ")";
                statsChanges[j].setColor(Color.RED);
            }
            else if(change > 0){
                changeStr = "(+" + Integer.toString(change) + ")";
                statsChanges[j].setColor(Color.GREEN);
            }
            else{
                changeStr = "";
            }
            statsChanges[j].setString(changeStr);
            statsChanges[j].setPosition(520, 270 +(90 * j));
            statsChanges[j].setOrigin(0, 0);
            statsChanges[j].setStyle(Text.REGULAR);
        }
        for(int j = 5; j < playerStatsList.length; j++ ){
            int change = eventEffects[j];
            String changeStr;
            statsChanges[j] = new Text("", fontStyle, 24);
            if(change < 0){
                changeStr = "(-" + Integer.toString((-1) * change) + ")";
                statsChanges[j].setColor(Color.RED);
            }
            else if(change > 0){
                changeStr = "(+" + Integer.toString(change) + ")";
                statsChanges[j].setColor(Color.GREEN);
            }
            else{
                changeStr = "";
            }

            statsChanges[j].setString(changeStr);
            statsChanges[j].setPosition(960, 270 + (68 * (j - 5)));
            statsChanges[j].setOrigin(0, 0);
            statsChanges[j].setStyle(Text.REGULAR);
        }

       // Set up "OK" button
        btn = new Text("OK", fontStyle, 28);
        btn.setPosition(618, 633);
        btn.setOrigin(0, 0);
        btn.setColor(Color.CYAN);
        btn.setStyle(Text.REGULAR);

        textButton =  textures.createSprite(textures.userInterface, 23, 21, 250, 60);
        hoverButton = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
        pushButton = textures.createSprite(textures.userInterface, 23, 179, 250, 60);

        textButton.setPosition(640, 650);
        pushButton.setPosition(640, 700);

        rectf = new FloatRect(textButton.getGlobalBounds().left, textButton.getGlobalBounds().top,
                    textButton.getGlobalBounds().width, textButton.getGlobalBounds().height);
        recti = new IntRect(rectf);

    }

    public void execute() {
        window.clear();
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        window.draw(messageScroll);
        displayAlert();

        window.display();
        for (Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    leftBound = textButton.getGlobalBounds().left;
                    rightBound = leftBound + textButton.getGlobalBounds().width;
                    topBound = textButton.getGlobalBounds().top;
                    bottomBound = topBound + textButton.getGlobalBounds().height;
                    // Add events/actions here when islands are clicked on
                    if (xPos > leftBound && xPos < rightBound && yPos > topBound && yPos < bottomBound) {
                        stateMachine.setState(stateMachine.getStates().get(4));     // Go to map
                        break;
                    }
            }
        }
    }

    public void displayAlert(){
        window.draw(title);

        for(int i = 0; i < statsNames.length; i++) {
            window.draw(statsNames[i]);
            window.draw(currStats[i]);
        }

        for(int j = 0; j < statsChanges.length; j++){
            if(j != 4)
                window.draw(statsChanges[j]);
        }

        if ((recti.contains(Mouse.getPosition(window)) && isMouseOver()))
            textButton.setTextureRect(new IntRect(23, 100, 250, 60));
        else if (!isMouseOver())
            textButton.setTextureRect(new IntRect(23, 21, 250, 60));

        window.draw(textButton);
        window.draw(btn);
    }

    public void genMessage(){
        switch(consequence){
            case COMBAT_KILL:
                title = new Text("Enemy defeated!\nYou plundered some resources!", fontStyle, titleFontSize);
                break;
            case COMBAT_PLAYER_RETREAT:
                title = new Text("You retreated from the battle", fontStyle, titleFontSize);
                break;
            case COMBAT_AI_RETREAT:
                title = new Text("Your enemy retreated!", fontStyle, titleFontSize);
                break;
            case ASSIST_ACCEPT:
                title = new Text(eventGenerator.getConsequence(), fontStyle, titleFontSize);
                break;
            case EXPLORE_ACCEPT:
                title = new Text(eventGenerator.getConsequence(), fontStyle, titleFontSize);
        }
        title.setPosition(driver.getWinWidth() / 2 - 35, 100);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);
    }

    public void applyChanges(){
        playerShip.addGold(eventEffects[0]);
        playerShip.addFood(eventEffects[1]);
        playerShip.addWater(eventEffects[2]);
        if(eventEffects[3] < 0)
            playerShip.damageHull((-1) * eventEffects[3]);
        else
            playerShip.repairHull(eventEffects[3]);
        playerShip.addGunStr(eventEffects[4]);

        if(eventEffects[5] < 0)
            playerShip.guns.damage((-1) * eventEffects[5]);
        else
            playerShip.guns.repair(eventEffects[5]);
        if(eventEffects[6] < 0)
            playerShip.masts.damage((-1) * eventEffects[6]);
        else
            playerShip.masts.repair(eventEffects[6]);
        if(eventEffects[7] < 0)
            playerShip.bridge.damage((-1) * eventEffects[7]);
        else
            playerShip.bridge.repair(eventEffects[7]);
        if(eventEffects[8] < 0)
            playerShip.hold.damage((-1) * eventEffects[8]);
        else
            playerShip.hold.repair(eventEffects[8]);
        if(eventEffects[9] < 0)
            playerShip.quarters.damage((-1) * eventEffects[9]);
        else
            playerShip.quarters.repair(eventEffects[9]);

        System.out.println("GOLD: " + playerShip.getCurrGold());
        System.out.println("FOOD: " + playerShip.getCurrFood());
        System.out.println("WATER: " + playerShip.getCurrWater());
        System.out.println("HULL: " + playerShip.getHullHP());
        System.out.println("GUN STR:" + playerShip.getGunStr());
        System.out.println("GUN HP:" + playerShip.guns.getHP());
        System.out.println("MAST HP:" + playerShip.masts.getHP());
        System.out.println("BRIDGE HP:" + playerShip.bridge.getHP());
        System.out.println("HOLD HP: " + playerShip.hold.getHP());
        System.out.println("QUARTERS HP: " + playerShip.quarters.getHP());

        checkGameOverAfter();
    }

    public void applyCombatChanges(){
        int difficultyMod = 1;
        switch(combatDifficulty){
            case MEDIUM:
                difficultyMod = 2;      // Double rewards
                break;
            case HARD:
                difficultyMod = 3;      // Triple rewards
                break;
        }

        playerShip.addGold(eventEffects[0] * difficultyMod);
        playerShip.addFood(eventEffects[1]);
        playerShip.addWater(eventEffects[2]);

        System.out.println("GOLD: " + playerShip.getCurrGold());
        System.out.println("FOOD: " + playerShip.getCurrFood());
        System.out.println("WATER: " + playerShip.getCurrWater());
    }

    public boolean isMouseOver(){
            if(recti.contains(Mouse.getPosition(window))){
                return true;
            }
        return false;
    }

    public void checkGameOverAfter(){
        if(playerShip.getHullHP() <= 0 || playerShip.getCurrFood() <= 0 || playerShip.getCurrWater() <= 0){
            stateMachine.setState(stateMachine.getStates().get(7));     // Game over, player hull destroyed or out of food/water
        }
    }

    public void consumeResources(){
        // Do nothing, overrides Events one
    }
}
