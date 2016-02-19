import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;

import java.util.ArrayList;
import java.util.Random;

public class EnemyShip extends Ship {

    private ShipSection target = null;     // Current target ShipSection of the enemy when attacking

    public EnemyShip(Textures textures, GameDriver driver, RenderWindow window, Random randGenerator, ShipType type, float scale, int xPos, int yPos){
        super(textures, driver, window, randGenerator, type, scale, xPos, yPos);
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
        masts.sprite.scale(-scale, -scale);
        bridge.sprite.scale(-scale, scale);
        hold.sprite.scale(-scale, -scale);
        quarters.sprite.scale(-scale, scale);

        guns.sprite.setPosition((xPos + 584) * scale, (yPos + 118) * scale);
        masts.sprite.setPosition((xPos + 584) * scale, yPos * scale);
        bridge.sprite.setPosition((xPos + 1019) * scale, yPos * scale);        // was 300
        hold.sprite.setPosition((xPos + 584) * scale, (yPos - 118) * scale);
        quarters.sprite.setPosition(xPos * scale, yPos * scale);

        reloadTimer = new Timer();          // Move this to somewhere better so clock isn't started at construction?
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
            // Set chances of each section to be targeted by AI
            // All weights must add up to 1.0
            player.guns.setWeight(0.4);
            player.masts.setWeight(0.2);
            player.bridge.setWeight(0.15);
            player.hold.setWeight(0.15);
            player.quarters.setWeight(0.1);

            // Determine new target based on weighting
            target = randomSelect(player);

            // Check target hasn't been destroyed - keep choosing until find one that hasn't
            while(!target.isTargetable()){
                target = randomSelect(player);
            }
        }

        // Randomly attack section, weighted towards attacking target
        double targetWeight = 0.6;          // 1 = maximum; guarantees target is selected
        double random = randGenerator.nextDouble();
        if(random <= targetWeight){
            // Attack target
            System.out.println("--------------AI MOVE-----------------");
            System.out.println("AI SAVED TARGET: " + target.getType());
            System.out.println("AI ACTUAL TARGET:  " + target.getType());
            System.out.println(target.getType() + "HP: " + target.getHP());

            int dmg = (randGenerator.nextInt(15 - 10 + 1) + 10) * gunStr; // Random damage between 10 and 15, multiplied by gunStr modifier
            System.out.println("HIT FOR " + dmg + " DMG");
            target.damage(dmg);
            gunLoaded = false;
            reloadTimer.restart();

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

            int dmg = (randGenerator.nextInt(15 - 10 + 1) + 10) * gunStr; // Random damage between 10 and 15, multiplied by gunStr modifier
            System.out.println("HIT FOR " + dmg + " DMG");
            toAttack.damage(dmg);
            gunLoaded = false;
            reloadTimer.restart();

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


}