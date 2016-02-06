import org.jsfml.graphics.RenderWindow;

import java.util.ArrayList;

public class Ship{
    public enum ShipType{
        PLAYER, STANDARD            // STANDARD = STANDARD ENEMY SHIP, REPLACE W/ BRITISH, DUTCH ETC LATER
    }

    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;

    private int hullHP = 100;  // Overall ship integrity; 0 = game over, ship sinks
    private int gunStr = 1; // Cannon strength (modifies damage dealt). 1 = default starting strength

    protected ShipSection guns;
    protected ShipSection masts;
    protected ShipSection bridge;
    protected ShipSection hold;
    protected ShipSection quarters;

    ArrayList<ShipSection> sections;

    protected float scale;
    protected int xPos;
    protected int yPos;

    public Ship(Textures textures, GameDriver driver, RenderWindow window, ShipType type, float scale, int xPos, int yPos){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        this.scale = scale;
        this.xPos = xPos;
        this.yPos = yPos;
        sections = new ArrayList<>();

        setup(type);
    }

    public void setup(ShipType type){
        switch(type){
            case PLAYER:
                guns = new ShipSection(textures, driver, window, "textures/ship_gun_deck.png", "Guns", this);
                masts = new ShipSection(textures, driver, window, "textures/ship_masts.png", "Masts", this);
                bridge = new ShipSection(textures, driver, window, "textures/ship_bridge.png", "Bridge", this);
                hold = new ShipSection(textures, driver, window, "textures/ship_hold.png", "Hold", this );
                quarters = new ShipSection(textures, driver, window, "textures/ship_medical.png", "Quarters", this);
                break;
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
    }

    public void draw(){
        for(ShipSection section : sections){
            window.draw(section.sprite);
        }
    }

    public void validateClick(int x, int y){
        for(ShipSection section: sections){
            float leftBound = section.sprite.getGlobalBounds().left;
            float rightBound = leftBound + section.sprite.getGlobalBounds().width;
            float topBound = section.sprite.getGlobalBounds().top;
            float bottomBound = topBound + section.sprite.getGlobalBounds().height;

            if(section.isTargetable() && x > leftBound && x < rightBound && y > topBound && y < bottomBound){
                System.out.println("---------------------------------");
                System.out.println("ENEMY " +section.getType() + " CLICKED!");
                System.out.println(section.getType() + "HP: " + section.getHP());
                System.out.println("FIRING GUNS!");

                section.damage(10 * gunStr);          // CHANGE THIS TO DO RANDOM DAMAGE BASED ON GUNSTR MODIFIER

                System.out.println(section.getType() + "HP: " + section.getHP());
                System.out.println("---------------------------------");
                break;
            }
            else if(!section.isTargetable() && x > leftBound && x < rightBound && y > topBound && y < bottomBound){
                System.out.println("---------------------------------");
                System.out.println(section.getType() + " HAS BEEN DESTROYED! CANNOT TARGET!");
                System.out.println("---------------------------------");
            }
        }
    }

    public void damageHull(int change){
        System.out.println("SHIP HULL DAMAGED!");
        hullHP -= change;

        if(hullHP <= 0){
            hullHP = 0;
            System.out.println("HULL COMPROMISED! GAME OVER!");
        }
    }

    public void repairHull(int change){
        hullHP += change;
        if(hullHP > 100)
            hullHP = 100;
    }
}
