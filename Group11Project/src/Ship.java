import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    public void checkReload(){
        long elapsed = reloadTimer.time(TimeUnit.SECONDS);
        if(elapsed >= (baseReload/reloadBoost)){
            gunLoaded = true;
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
            System.out.println("HULL COMPROMISED!");
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

    public Timer getReloadTimer(){
        return reloadTimer;
    }

    public int getHullHP(){
        return hullHP;
    }
}