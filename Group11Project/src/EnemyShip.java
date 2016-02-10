import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;

/**
 * Created by Rohjo on 10/02/2016.
 */
public class EnemyShip extends Ship {

    private ShipSection target = null;     // Current target ShipSection of the enemy when attacking

    public enum ShipType{
        STANDARD            // STANDARD = STANDARD ENEMY SHIP, REPLACE W/ BRITISH, DUTCH ETC LATER
    }

    private ShipType type;

    public EnemyShip(Textures textures, GameDriver driver, RenderWindow window, ShipType type, float scale, int xPos, int yPos){
        super(textures, driver, window, scale, xPos, yPos);
        this.type=type;

        setup();
    }

    public void setup(){
        switch(type){
            case STANDARD:
                guns = new ShipSection(textures, driver, window, "textures/ship_gun_deck.png", "Guns", this);
                masts = new ShipSection(textures, driver, window, "textures/ship_masts.png", "Masts", this);
                bridge = new ShipSection(textures, driver, window, "textures/ship_bridge.png", "Bridge", this);
                hold = new ShipSection(textures, driver, window, "textures/ship_hold.png", "Hold", this);
                quarters = new ShipSection(textures, driver, window, "textures/ship_medical.png", "Quarters", this);
                break;
            default:
                System.out.println("ERROR");
                break;
        }

        guns.sprite.setPosition((xPos + 434) * scale, (yPos - 98) * scale);
        masts.sprite.setPosition((xPos + 434) * scale, yPos * scale);
        bridge.sprite.setPosition(xPos * scale, yPos * scale);        // was 300
        hold.sprite.setPosition((xPos + 434) * scale, (yPos + 118) * scale);
        quarters.sprite.setPosition((xPos + 999) * scale, yPos * scale);

        sections.add(guns);
        sections.add(masts);
        sections.add(bridge);
        sections.add(hold);
        sections.add(quarters);

        for(ShipSection section : sections){
            section.sprite.scale(scale, scale);
        }

        reloadTimer = new Clock();          // Move this to somewhere better so clock isn't started at construction?
    }

    public ShipSection validateClicked(int x, int y){
        if(!gunLoaded)
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
    public void attack(){
        if(target == null)
            // choose new target randomly, weighted as follows:
            // guns: 40%
            // masts: 20%
            // quarters: 10%
            // hold: 15%
            // bridge: 15%
            System.out.println("Choose new target!");

            /*
            How to do weighted randomness:
            Item[] items = ...;

            // Compute the total weight of all items together
                double totalWeight = 0.0d;
                for (Item i : items)
                {
                    totalWeight += i.getWeight();
                }
                // Now choose a random item
                    int randomIndex = -1;
                    double random = Math.random() * totalWeight;
                    for (int i = 0; i < items.length; ++i)
                    {
                        random -= items[i].getWeight();
                        if (random <= 0.0d)
                        {
                            randomIndex = i;
                            break;
                        }
                    }
                    Item myRandomItem = items[randomIndex];
             */
            /* How it works:
            1. Give some arbitrary ordering to items... (i1, i2, ..., in)... with weights w1, w2, ..., wn.
            2. Choose a random number between 0 and 1 (with sufficient granularity, by using any randomization function and appropriate scaling). Call this r0.
            3. Let j = 1
            4. Subtract wj from your r(j-1) to get rj. If rj <= 0, then you select item ij. Otherwise, increment j and repeat.
             */


        // attack target section


        // if target section destroyed, set target to null

    }


}
