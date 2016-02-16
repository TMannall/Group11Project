import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;

import java.util.ArrayList;

public class UI {

    private Textures textures;
    private GameDriver driver;
    private RenderWindow window;
    private Ship playerShip;
    private Ship enemyShip;

    private Sprite AIHullHPBg;
    private Sprite AIHullHPFg;
    private Sprite AIHullHPOverlay;
    private Sprite AISectionHPOverlay;
    private Sprite AIGunHPBg;
    private Sprite AIGunHPFg;
    private Sprite AIMastHPBg;
    private Sprite AIMastHPFg;
    private Sprite AIBridgeHPBg;
    private Sprite AIBridgeHPFg;
    private Sprite AIHoldHPBg;
    private Sprite AIHoldHPFg;
    private Sprite AIQuartersHPBg;
    private Sprite AIQuartersHPFg;

    private Sprite playerHullHPBg;
    private Sprite playerHullHPFg;
    private Sprite playerHullHPOverlay;
    private Sprite playerSectionHPOverlay;
    private Sprite playerReloadOverlay;
    private Sprite playerGunHPBg;
    private Sprite playerGunHPFg;
    private Sprite playerMastHPBg;
    private Sprite playerMastHPFg;
    private Sprite playerBridgeHPBg;
    private Sprite playerBridgeHPFg;
    private Sprite playerHoldHPBg;
    private Sprite playerHoldHPFg;
    private Sprite playerQuartersHPBg;
    private Sprite playerQuartersHPFg;

    private ArrayList<Sprite> UIelements;

    private float smallBarScale = (float)0.755;
    private float largeBarScale = (float)2.955;

    private int previousAIHullHP = 100;
    private int previousAIGunHP = 100;
    private int previousAIMastHP = 100;
    private int previousAIBridgeHP = 100;
    private int previousAIHoldHP = 100;
    private int previousAIQuartersHP = 100;

    public UI(Textures textures, GameDriver driver, RenderWindow window, Ship playerShip, Ship enemyShip){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        this.playerShip = playerShip;
        this.enemyShip = enemyShip;
        UIelements = new ArrayList<>();


        // HP bar backgrounds and foregrounds
        UIelements.add(AIHullHPBg = textures.createSprite(textures.userInterface, 317, 528, 237, 29, largeBarScale, 1, 0, 0));
        UIelements.add(AIHullHPFg = textures.createSprite(textures.userInterface, 317, 432, 237, 29, largeBarScale, 1, 0, 0));
        UIelements.add(AIGunHPBg = textures.createSprite(textures.userInterface, 317, 528, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIGunHPFg = textures.createSprite(textures.userInterface, 317, 432, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIMastHPBg = textures.createSprite(textures.userInterface, 317, 528, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIMastHPFg = textures.createSprite(textures.userInterface, 317, 432, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIBridgeHPBg = textures.createSprite(textures.userInterface, 317, 528, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIBridgeHPFg = textures.createSprite(textures.userInterface, 317, 432, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIHoldHPBg = textures.createSprite(textures.userInterface, 317, 528, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIHoldHPFg = textures.createSprite(textures.userInterface, 317, 432, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIQuartersHPBg = textures.createSprite(textures.userInterface, 317, 528, 237, 29, smallBarScale, 1, 0, 0));
        UIelements.add(AIQuartersHPFg = textures.createSprite(textures.userInterface, 317, 432, 237, 29, smallBarScale, 1, 0, 0));
        // TO DO: ADD PLAYER VERSIONS


        AIHullHPBg.setPosition(290, 20);
        AIHullHPFg.setPosition(290,20);
        AIGunHPBg.setPosition(1090, 85);
        AIGunHPFg.setPosition(1090, 85);
        AIMastHPBg.setPosition(1090, 135);
        AIMastHPFg.setPosition(1090, 135);
        AIHoldHPBg.setPosition(1090, 185);
        AIHoldHPFg.setPosition(1090, 185);
        AIBridgeHPBg.setPosition(1090, 235);
        AIBridgeHPFg.setPosition(1090, 235);
        AIQuartersHPBg.setPosition(1090, 285);
        AIQuartersHPFg.setPosition(1090, 285);

        // HP bar overlays
        UIelements.add(AIHullHPOverlay = textures.createSprite(textures.userInterface, 22, 264, 994, 73));
        UIelements.add(AISectionHPOverlay = textures.createSprite(textures.userInterface, 24, 349, 250, 261));
        UIelements.add(playerHullHPOverlay = textures.createSprite(textures.userInterface, 11, 639, 994, 73));
        UIelements.add(playerSectionHPOverlay = textures.createSprite(textures.userInterface, 643, 353, 250, 261));
        UIelements.add(playerReloadOverlay = textures.createSprite(textures.userInterface, 310, 350, 320, 66));

        AIHullHPOverlay.setPosition(driver.getWinWidth() / 2, 35);
        AISectionHPOverlay.setPosition(driver.getWinWidth() - (AISectionHPOverlay.getGlobalBounds().width)/2, 200);
        playerHullHPOverlay.setPosition(driver.getWinWidth()/2, driver.getWinHeight()-35);
        playerSectionHPOverlay.setPosition(driver.getWinWidth() - (playerSectionHPOverlay.getGlobalBounds().width)/2, 520);
        playerReloadOverlay.setPosition(playerReloadOverlay.getGlobalBounds().width/2 - 8, 605);

    }

    // Called to draw HP bar as a single thing where it's meant to be on the UI
    // Actual implementation draws each sprite as necessary
    public void draw(){

        // AI Hull
        if(enemyShip.getHullHP() < previousAIHullHP){
            previousAIHullHP--;
        }
        AIHullHPFg.setScale((previousAIHullHP/(float)100)*largeBarScale, 1);

        // AI Guns
        if(enemyShip.guns.getHP() < previousAIGunHP){
            previousAIGunHP--;
        }
        AIGunHPFg.setScale((previousAIGunHP/(float)100)*smallBarScale, 1);

        // AI Masts
        if(enemyShip.masts.getHP() < previousAIMastHP){
            previousAIMastHP--;
        }
        AIMastHPFg.setScale((previousAIMastHP/(float)100)*smallBarScale, 1);

        // AI Hold
        if(enemyShip.hold.getHP() < previousAIHoldHP){
            previousAIHoldHP--;
        }
        AIHoldHPFg.setScale((previousAIHoldHP/(float)100)*smallBarScale, 1);

        // AI Bridge
        if(enemyShip.bridge.getHP() < previousAIBridgeHP){
            previousAIBridgeHP--;
        }
        AIBridgeHPFg.setScale((previousAIBridgeHP/(float)100)*smallBarScale, 1);

        // AI Quarters
        if(enemyShip.quarters.getHP() < previousAIQuartersHP){
            previousAIQuartersHP--;
        }
        AIQuartersHPFg.setScale((previousAIQuartersHP/(float)100)*smallBarScale, 1);

        // Draw all UI elements
        for(Sprite sprite : UIelements){
            window.draw(sprite);
        }
    }

}