import org.jsfml.graphics.RenderWindow;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Class used to manage a basic ship
 */
public abstract class Ship{
    public enum ShipType{
        PLAYER, STANDARD            // STANDARD = STANDARD ENEMY SHIP, REPLACE W/ BRITISH, DUTCH ETC LATER
    }

    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;

    protected Random randGenerator;

    protected ShipType shipType;

    private int hullHP = 100;  // Overall ship integrity; 0 = game over, ship sinks
    protected int gunStr = 1; // Cannon strength (modifies damage dealt). 1 = default starting strength
    protected int reloadBoost = 1;   // Cannon reload modifier. 1 = reloads at standard rate, 2 = double rate etc
    protected int baseReload = 5;     // Base reload time across the game (seconds)
    protected boolean gunLoaded = true;   // True when cannons can fire; false when reloading
    protected Timer reloadTimer;

    //Define ship sections
    protected ShipSection guns;
    protected ShipSection masts;
    protected ShipSection bridge;
    protected ShipSection hold;
    protected ShipSection quarters;

    protected ArrayList<ShipSection> sections;

    protected float scale;
    protected int xPos;
    protected int yPos;

    public Ship(Textures textures, GameDriver driver, RenderWindow window, ShipType shipType, float scale, int xPos, int yPos){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        this.shipType = shipType;
        this.scale = scale;
        this.xPos = xPos;
        this.yPos = yPos;

        randGenerator = new Random();
        sections = new ArrayList<>();
    }

    // Abstract setup method that should be called on all subclasses after the super constructor to set textures/sprites
    public abstract void setup();

    public void draw(){
        for(ShipSection section : sections){
            window.draw(section.sprite);
            window.draw(section.getIcon());
        }
    }

    /**
     * Method for checking whether weapons have been reloaded
     */
    public void checkReload(){
        long elapsed = reloadTimer.time(TimeUnit.SECONDS);
        if(elapsed >= (baseReload/reloadBoost)){
            gunLoaded = true;
        }
        else{
            gunLoaded = false;
        }
    }

    /**
     * Method takes a damage amount and applies it to ship's hull, also manages whether hull has been destroyed
     * @param change
     */
    public void damageHull(int change){
        System.out.println("SHIP HULL DAMAGED!");
        hullHP -= change;

        if(hullHP <= 0){
            hullHP = 0;
            System.out.println("HULL COMPROMISED!");
        }
    }

    /**
     * Method used to repair the hull of the ship, prevents hull being set higher than 100
     * @param change
     */
    public void repairHull(int change){
        hullHP += change;
        if(hullHP > 100)
            hullHP = 100;
    }

    /**
     * Method checks whether gun is currently loaded
     * @return gunLoaded
     */
    public boolean isGunLoaded(){
        return gunLoaded;
    }

    /**
     * Method used to set the gun to loaded
     * @param loaded
     */
    public void setGunLoaded(boolean loaded){
        gunLoaded = loaded;
    }

    /**
     * Method for checking gun strength
     * @return gunStr
     */
    public int getGunStr(){
        return gunStr;
    }

    /**
     * Method for getting current reload timer
     * @return reloadTimer
     */
    public Timer getReloadTimer(){
        return reloadTimer;
    }

    /**
     * Method for getting the current Hull health
     * @return hullHP
     */
    public int getHullHP(){
        return hullHP;
    }
}