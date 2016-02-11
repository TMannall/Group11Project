import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

import java.util.concurrent.TimeUnit;

public class PlayerShip extends Ship {

    private int food;
    private int water;
    private int gold;

    public PlayerShip(Textures textures, GameDriver driver, RenderWindow window, float scale, int xPos, int yPos){
        super(textures, driver, window, scale, xPos, yPos);
        setup();
    }

    public void setup(){
        guns = new ShipSection(textures, driver, window, "textures/ship_gun_deck.png", "Guns", this);
        masts = new ShipSection(textures, driver, window, "textures/ship_masts.png", "Masts", this);
        bridge = new ShipSection(textures, driver, window, "textures/ship_bridge.png", "Bridge", this);
        hold = new ShipSection(textures, driver, window, "textures/ship_hold.png", "Hold", this);
        quarters = new ShipSection(textures, driver, window, "textures/ship_medical.png", "Quarters", this);

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

        reloadTimer = new Timer();          // Move this to somewhere better so clock isn't started at construction?
    }

    public void attack(ShipSection clicked){
        if (!gunLoaded){
            System.out.println("PLAYER CANNONS STILL RELOADING!");
            return;
        }
        if(clicked.isTargetable()){
            System.out.println("-------------PLAYER MOVE---------------");
            System.out.println("ENEMY " + clicked.getType() + " CLICKED!");
            System.out.println(clicked.getType() + "HP: " + clicked.getHP());

            System.out.println("FIRING GUNS!");
            int dmg = (randGenerator.nextInt(15 - 10 + 1) + 10) * gunStr; // Random damage between 10 and 15, multiplied by gunStr modifier
            System.out.println("HIT FOR " + dmg + " DMG");
            clicked.damage(dmg);
            gunLoaded = false;
            reloadTimer.restart();

            System.out.println(clicked.getType() + "HP: " + clicked.getHP());
            System.out.println("---------------------------------");
        }
        else{
            System.out.println("-------------PLAYER MOVE---------------");
            System.out.println(clicked.getType() + " HAS BEEN DESTROYED! CANNOT TARGET!");
            System.out.println("---------------------------------");
        }
    }

    @Override
    public void checkReload(){
        long elapsed = reloadTimer.time(TimeUnit.SECONDS);
        if(elapsed >= (2/reloadBoost)){
            gunLoaded = true;
            System.out.println("PLAYER CANNONS RELOADED - FIRE!");
        }
        else{
            gunLoaded = false;
        }
    }

}
