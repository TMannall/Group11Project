import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

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
    private int reloadBoost = 1;   // Cannon reload modifier. 1 = reloads at standard rate, 2 = double rate etc
    private boolean gunLoaded = true;   // True when cannons can fire; false when reloading
    private Clock reloadTimer;

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

        Sprite shipBridge = textures.createSprite(textures.shipLv3, 0, 0, 525, 365);
        Sprite shipGunDeck = textures.createSprite(textures.shipLv3, 525, 0, 343, 125);
        Sprite shipMasts = textures.createSprite(textures.shipLv3, 525, 129, 343, 115);
        Sprite shipSupplies = textures.createSprite(textures.shipLv3, 525, 241, 343, 125);
        Sprite shipMedical = textures.createSprite(textures.shipLv3, 868, 0, 826, 365);

        Sprite AIshipBridge = textures.createSprite(textures.shipLv3, 0, 0, 525, 365);
        Sprite AIshipGunDeck = textures.createSprite(textures.shipLv3, 525, 0, 343, 125);
        Sprite AIshipMasts = textures.createSprite(textures.shipLv3, 525, 129, 343, 115);
        Sprite AIshipSupplies = textures.createSprite(textures.shipLv3, 525, 241, 343, 125);
        Sprite AIshipMedical = textures.createSprite(textures.shipLv3, 868, 0, 826, 365);

        switch(type){
            case PLAYER:
                guns = new ShipSection(textures, driver, window, shipGunDeck, "Guns", this);
                masts = new ShipSection(textures, driver, window, shipMasts, "Masts", this);
                bridge = new ShipSection(textures, driver, window, shipBridge, "Bridge", this);
                hold = new ShipSection(textures, driver, window, shipSupplies, "Hold", this );
                quarters = new ShipSection(textures, driver, window, shipMedical, "Quarters", this);
                break;
            case STANDARD:
                guns = new ShipSection(textures, driver, window, AIshipGunDeck, "Guns", this);
                masts = new ShipSection(textures, driver, window, AIshipMasts, "Masts", this);
                bridge = new ShipSection(textures, driver, window, AIshipBridge, "Bridge", this);
                hold = new ShipSection(textures, driver, window, AIshipSupplies, "Hold", this);
                quarters = new ShipSection(textures, driver, window, AIshipMedical, "Quarters", this);
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

    public void draw(){
        for(ShipSection section : sections){
            window.draw(section.sprite);
        }
    }

    public ShipSection validateClick(int x, int y){
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

    public void checkReload(){
        Time time = reloadTimer.getElapsedTime();
        float elapsed = time.asSeconds();
        if(elapsed >= (2/reloadBoost)){
            gunLoaded = true;
            System.out.println("CANNONS RELOADED - FIRE!");
        }
        else{
            gunLoaded = false;
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

    public boolean isGunLoaded(){
        return gunLoaded;
    }

    public void setGunLoaded(boolean loaded){
        gunLoaded = loaded;
    }

    public int getGunStr(){
        return gunStr;
    }

    public Clock getReloadTimer(){
        return reloadTimer;
    }
}
