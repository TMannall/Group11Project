import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlayerShip extends Ship {
    private UI ui;

    public PlayerShip(Textures textures, GameDriver driver, RenderWindow window, Random randGenerator, ShipType type, float scale, int xPos, int yPos){
        super(textures, driver, window, randGenerator, type, scale, xPos, yPos);
        setup();
    }

    public void setup(){
        guns = new ShipSection(textures, driver, window, textures.shipGunDeck, "Guns", this, "Player");
        masts = new ShipSection(textures, driver, window, textures.shipMasts, "Masts", this, "Player");
        bridge = new ShipSection(textures, driver, window, textures.shipBridge, "Bridge", this, "Player");
        hold = new ShipSection(textures, driver, window, textures.shipSupplies, "Hold", this, "Player");
        quarters = new ShipSection(textures, driver, window, textures.shipMedical, "Quarters", this, "Player");

        sections.add(guns);
        sections.add(masts);
        sections.add(bridge);
        sections.add(hold);
        sections.add(quarters);

        for(ShipSection section : sections){
            section.sprite.scale(scale, scale);
            section.sectionHighlight.scale(scale, scale);
        }

        guns.sprite.setPosition((xPos + 434) * scale, (yPos - 118) * scale);
        guns.sectionHighlight.setPosition((xPos + 434) * scale, (yPos - 118) * scale);

        masts.sprite.setPosition((xPos + 434) * scale, yPos * scale);
        masts.sectionHighlight.setPosition((xPos + 434) * scale, yPos * scale);

        bridge.sprite.setPosition(xPos * scale, yPos * scale);
        bridge.sectionHighlight.setPosition(xPos * scale, yPos * scale);

        hold.sprite.setPosition((xPos + 434) * scale, (yPos + 118) * scale);
        hold.sectionHighlight.setPosition((xPos + 434) * scale, (yPos + 118) * scale);

        quarters.sprite.setPosition((xPos + 999) * scale, yPos * scale);
        quarters.sectionHighlight.setPosition((xPos + 999) * scale, yPos * scale);

        // Position gun smoke animations for each gun
        gunAnimations.get(0).setPosition(352, 427);
        gunAnimations.get(1).setPosition(413, 421);
        gunAnimations.get(2).setPosition(474, 417);
        gunAnimations.get(3).setPosition(524, 414);
        gunAnimations.get(4).setPosition(541, 415);
        gunAnimations.get(5).setPosition(572, 413);
        gunAnimations.get(6).setPosition(604, 413);
        gunAnimations.get(7).setPosition(633, 412);
        gunAnimations.get(8).setPosition(666, 413);
        gunAnimations.get(9).setPosition(721, 418);


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
            ui.setReload(0);
            reloadTimer.restart();

            // Animate cannon fire
            resetAnimation();
            animating = true;

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
        if(elapsed >= (baseReload/reloadBoost)){
            gunLoaded = true;
            System.out.println("PLAYER CANNONS RELOADED - FIRE!");
        }
        else{
            gunLoaded = false;
        }
    }

    public void drawHighlight(ShipSection section){
            window.draw(section.sectionHighlight);
    }

    public void setUI(UI ui){
        this.ui = ui;
    }
}