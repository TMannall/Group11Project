import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

import java.util.ArrayList;
import java.util.Random;

public abstract class Ship{


    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;

    protected Random randGenerator;

    private int hullHP = 100;  // Overall ship integrity; 0 = game over, ship sinks
    protected int gunStr = 1; // Cannon strength (modifies damage dealt). 1 = default starting strength
    protected int reloadBoost = 1;   // Cannon reload modifier. 1 = reloads at standard rate, 2 = double rate etc
    protected boolean gunLoaded = true;   // True when cannons can fire; false when reloading
    protected Clock reloadTimer;

    protected ShipSection guns;
    protected ShipSection masts;
    protected ShipSection bridge;
    protected ShipSection hold;
    protected ShipSection quarters;

    ArrayList<ShipSection> sections;

    protected float scale;
    protected int xPos;
    protected int yPos;

    public Ship(Textures textures, GameDriver driver, RenderWindow window, float scale, int xPos, int yPos){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
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
        }
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

    public int getHullHP(){
        return hullHP;
    }
}
