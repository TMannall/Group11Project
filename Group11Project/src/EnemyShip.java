import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Enemy Ship class which is used to create the AI ship the player is fighting. In this class everything from the
 * sprites for the ship to the AI functionality is created, including methods for scaling the difficulty.
 */
public class EnemyShip extends Ship {
    public enum Difficulty{
        EASY, MEDIUM, HARD, BOSS
    }
    private Difficulty difficulty;
    private PlayerShip playerShip;
    private ShipSection target = null;     // Current target ShipSection of the enemy when attacking
    private double targetWeight = 0.6;          // Threshold for attacking current target; 1 = maximum; guarantees target is selected

    public EnemyShip(Textures textures, GameDriver driver, RenderWindow window, Random randGenerator, SoundFX sound, ShipType type, float scale, int xPos, int yPos, Difficulty difficulty){
        super(textures, driver, window, randGenerator, sound, type, scale, xPos, yPos);
        this.difficulty = difficulty;
        this.playerShip = driver.getPlayerShip();
        setup();
    }

    public void setup(){
        switch(shipType){
            case STANDARD:
                guns = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 525, 0, 343, 125), "Guns", this, "Enemy");
                masts = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 525, 129, 343, 115), "Masts", this, "Enemy");
                bridge = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 0, 0, 525, 365), "Bridge", this, "Enemy");
                hold = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 525, 241, 343, 125), "Hold", this, "Enemy");
                quarters = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 868, 0, 826, 365), "Quarters", this, "Enemy");
                break;
            default:
                System.out.println("ERROR");
                break;
        }

        sections.add(guns);
        sections.add(masts);
        sections.add(bridge);
        sections.add(hold);
        sections.add(quarters);

        guns.sprite.scale(-scale, -scale);
        guns.sectionHighlight.scale(-scale, -scale);

        masts.sprite.scale(-scale, -scale);
        masts.sectionHighlight.scale(-scale, -scale);

        bridge.sprite.scale(-scale, scale);
        bridge.sectionHighlight.scale(-scale, scale);

        hold.sprite.scale(-scale, -scale);
        hold.sectionHighlight.scale(-scale, -scale);

        quarters.sprite.scale(-scale, scale);
        quarters.sectionHighlight.scale(-scale, scale);

        guns.sprite.setPosition((xPos + 584) * scale, (yPos + 118) * scale);
        guns.sectionHighlight.setPosition((xPos + 584) * scale, (yPos + 118) * scale);

        masts.sprite.setPosition((xPos + 584) * scale, yPos * scale);
        masts.sectionHighlight.setPosition((xPos + 584) * scale, yPos * scale);

        bridge.sprite.setPosition((xPos + 1019) * scale, yPos * scale);        // was 300
        bridge.sectionHighlight.setPosition((xPos + 1019) * scale, yPos * scale);

        hold.sprite.setPosition((xPos + 584) * scale, (yPos - 118) * scale);
        hold.sectionHighlight.setPosition((xPos + 584) * scale, (yPos - 118) * scale);

        quarters.sprite.setPosition(xPos * scale, yPos * scale);
        quarters.sectionHighlight.setPosition(xPos * scale, yPos * scale);

        reloadTimer = new Timer();          // Move this to somewhere better so clock isn't started at construction?

        for(int i = 0; i < 10; i++) {
            gunAnimations.get(i).setScale(-scale, -scale);
        }
        // Position gun smoke animations for each gun
        gunAnimations.get(0).setPosition(859, 294);         // Starting from right to mirror playerShip
        gunAnimations.get(1).setPosition(797, 299);
        gunAnimations.get(2).setPosition(735, 303);
        gunAnimations.get(3).setPosition(685, 306);
        gunAnimations.get(4).setPosition(668, 306);
        gunAnimations.get(5).setPosition(637, 308);
        gunAnimations.get(6).setPosition(604, 308);
        gunAnimations.get(7).setPosition(575, 308);
        gunAnimations.get(8).setPosition(542, 307);
        gunAnimations.get(9).setPosition(478, 306);

        setDifficulty();
    }

    public ShipSection validateClicked(Ship player, int x, int y){
        if(!player.gunLoaded)
            System.out.println("CANNONS STILL RELOADING!");
        else {
            for (ShipSection section : sections) {
                float leftBound = section.sprite.getGlobalBounds().left;
                float rightBound = leftBound + section.sprite.getGlobalBounds().width;
                float topBound = section.sprite.getGlobalBounds().top;
                float bottomBound = topBound + section.sprite.getGlobalBounds().height;

                if (x > leftBound && x < rightBound && y > topBound && y < bottomBound) {
                    return section;
                }
            }
        }
        return null;
    }

    // Attacks a section on the player's ship
    public void attack(Ship player){
        if(target == null) {
            // Determine new target based on weighting
            target = randomSelect(player);

            // Check target hasn't been destroyed - keep choosing until find one that hasn't
            while(!target.isTargetable()){
                target = randomSelect(player);
            }
        }

        // Randomly attack section, weighted towards attacking target
        double random = randGenerator.nextDouble();
        if(random <= targetWeight){
            // Attack target
            System.out.println("--------------AI MOVE-----------------");
            System.out.println("AI SAVED TARGET: " + target.getType());
            System.out.println("AI ACTUAL TARGET:  " + target.getType());
            System.out.println(target.getType() + "HP: " + target.getHP());

            int dmg;
            if(guns.isTargetable())
                dmg = (randGenerator.nextInt(15 - 10 + 1) + 10) * (int)gunStr; // Random damage between 10 and 15, multiplied by gunStr modifier
            else
                dmg = (int)((randGenerator.nextInt(15 - 10 + 1) + 10) * (gunStr / 2)); // AI guns destroyed, halved damage dealt
            System.out.println("PRE-ARMOUR DMG: " + dmg);
            if(player.bridge.isTargetable())
                dmg = (int)(dmg / player.getBridgeDefence());
            else
                dmg = (int)(dmg / (player.getBridgeDefence()/2));   // Player bridge destroyed, deal double damage
            System.out.println("HIT FOR " + dmg + " DMG");
            target.damage(dmg);
            gunLoaded = false;
            reloadTimer.restart();

            // Animate cannon fire
            resetAnimation();

            System.out.println(target.getType() + "HP: " + target.getHP());
            System.out.println("-------------------------------------");
        }
        else{
            // Attack another random section
            ShipSection toAttack = randomSelect(player);

            // Check target hasn't been destroyed - keep choosing until find one that hasn't
            while(!toAttack.isTargetable()) {
                toAttack = randomSelect(player);
            }

            // Attack the temporary target
            System.out.println("--------------AI MOVE-----------------");
            System.out.println("AI SAVED TARGET: " + target.getType());
            System.out.println("AI ACTUAL TARGET:  " + toAttack.getType());
            System.out.println(toAttack.getType() + "HP: " + toAttack.getHP());

            int dmg;
            if(guns.isTargetable())
                dmg = (randGenerator.nextInt(15 - 10 + 1) + 10) * (int)gunStr; // Random damage between 10 and 15, multiplied by gunStr modifier
            else
                dmg = (int)((randGenerator.nextInt(15 - 10 + 1) + 10) * (gunStr / 2)); // AI guns destroyed, halved damage dealt
            System.out.println("PRE-ARMOUR DMG: " + dmg);
            if(player.bridge.isTargetable())
                dmg = (int)(dmg / player.getBridgeDefence());
            else
                dmg = (int)(dmg / (player.getBridgeDefence()/2));   // Player bridge destroyed, deal double damage
            System.out.println("HIT FOR " + dmg + " DMG");
            toAttack.damage(dmg);
            gunLoaded = false;
            reloadTimer.restart();

            // Animate cannon fire
            resetAnimation();

            System.out.println(toAttack.getType() + "HP: " + toAttack.getHP());
            System.out.println("--------------------------------------");

        }

        // Check if target has been destroyed to find a new one next time around
        // Done here because the toAttack could select the target anyway by random & destroy it
        if(!target.isTargetable()){
            target = null;
        }

    }

    public ShipSection randomSelect(Ship player){
        int randIndex = -1;
        double random = randGenerator.nextDouble() * 1;
        for (int i = 0; i < player.sections.size(); i++){
            random -= player.sections.get(i).getWeight();
            if(random <= 0.0d){
                randIndex = i;
                break;
            }
        }
        return player.sections.get(randIndex);
    }

    public void drawHighlight(ShipSection section){
        window.draw(section.sectionHighlight);
    }

    public void setDifficulty(){
        System.out.println("DIFFICULTY CHOSEN: " + difficulty);
        switch(difficulty){
            case EASY:      // Fires much slower than player can
                baseReload = 7;
                // Set chances of each section to be targeted by AI
                // All weights must add up to 1.0
                playerShip.guns.setWeight(0.1);
                playerShip.masts.setWeight(0.1);
                playerShip.bridge.setWeight(0.2);
                playerShip.hold.setWeight(0.3);
                playerShip.quarters.setWeight(0.3);
                targetWeight = 0.4;
                break;
            case MEDIUM:    // Fires faster & hits harder
                baseReload = 6;
                gunStr = 2;
                // Set chances of each section to be targeted by AI
                // All weights must add up to 1.0
                playerShip.guns.setWeight(0.4);
                playerShip.masts.setWeight(0.2);
                playerShip.bridge.setWeight(0.15);
                playerShip.hold.setWeight(0.15);
                playerShip.quarters.setWeight(0.1);
                targetWeight = 0.6;
                break;
            case HARD:  // Fires at same rate as player, hits harder & takes reduced damage
                baseReload = 5;
                gunStr = 2;
                bridgeDefence = 2;
                // Set chances of each section to be targeted by AI
                // All weights must add up to 1.0
                playerShip.guns.setWeight(0.4);
                playerShip.masts.setWeight(0.2);
                playerShip.bridge.setWeight(0.15);
                playerShip.hold.setWeight(0.15);
                playerShip.quarters.setWeight(0.1);
                targetWeight = 0.75;
                break;
            case BOSS:
                // Set chances of each section to be targeted by AI
                // All weights must add up to 1.0
                playerShip.guns.setWeight(0.4);
                playerShip.masts.setWeight(0.2);
                playerShip.bridge.setWeight(0.15);
                playerShip.hold.setWeight(0.15);
                playerShip.quarters.setWeight(0.1);
                targetWeight = 1;
                break;
        }
    }
}