import org.jsfml.graphics.RenderWindow;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Settings state class, deals with the displaying and changing of the settings for the game including the resolution
 * (still to be implemented) and volume levels.
 */
public class PlayerShip extends Ship {
    private EnemyShip enemyShip = null;     // Current enemy ship when in combat
    private UI ui;
    private int eventsCompleted = 0;        // Tracks number of events completed by the player

    public PlayerShip(Textures textures, GameDriver driver, RenderWindow window, Random randGenerator, SoundFX sound, ShipType type, float scale, int xPos, int yPos){
        super(textures, driver, window, randGenerator, sound, type, scale, xPos, yPos);
        setup();
    }

    public void setup(){
        guns = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 525, 0, 343, 125), "Guns", this, "Player");
        masts = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 525, 129, 343, 115), "Masts", this, "Player");
        bridge = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 0, 0, 525, 365), "Bridge", this, "Player");
        hold = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 525, 241, 343, 125), "Hold", this, "Player");
        quarters = new ShipSection(textures, driver, window, textures.createSprite(textures.shipLv3, 868, 0, 826, 365), "Quarters", this, "Player");

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

        // jack: for some reason the game doesn't like it when these sprites are positioned elsewhere
        marineBritishAnimations.get(0).setPosition(776, 276);
        marineBritishAnimations.get(1).setPosition(752, 281);
        marineBritishAnimations.get(2).setPosition(717, 276);
        marineBritishAnimations.get(3).setPosition(696, 283);
        marineBritishAnimations.get(4).setPosition(664, 281);
        marineBritishAnimations.get(5).setPosition(641, 278);
        marineBritishAnimations.get(6).setPosition(612, 282);
        marineBritishAnimations.get(7).setPosition(547, 284);
        marineBritishAnimations.get(8).setPosition(515, 285);
        marineBritishAnimations.get(9).setPosition(458, 282);

        marineNeutralAnimations.get(0).setPosition(436, 434);
        marineNeutralAnimations.get(1).setPosition(458, 439);
        marineNeutralAnimations.get(2).setPosition(490, 441);
        marineNeutralAnimations.get(3).setPosition(513, 435);
        marineNeutralAnimations.get(4).setPosition(543, 428);
        marineNeutralAnimations.get(5).setPosition(566, 432);
        marineNeutralAnimations.get(6).setPosition(594, 427);
        marineNeutralAnimations.get(7).setPosition(660, 430);
        marineNeutralAnimations.get(8).setPosition(700, 427);
        marineNeutralAnimations.get(9).setPosition(742, 433);
        // jack: end sprite positioning

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
            int dmg;
            if(guns.isTargetable())
                dmg = (int)((randGenerator.nextInt(15 - 10 + 1) + 10) * gunStr); // Random damage between 10 and 15, multiplied by gunStr modifier
            else{
                dmg = (int)((randGenerator.nextInt(15 - 10 + 1) + 10) * (gunStr / 2));
            }

            System.out.println("PRE-ARMOUR DMG: " + dmg);
            if(enemyShip.bridge.isTargetable())
                dmg = (int)(dmg / enemyShip.getBridgeDefence());
            else
                dmg = (int)(dmg / (enemyShip.getBridgeDefence()/2));   // Enemy bridge destroyed, deal double damage
            System.out.println("HIT FOR " + dmg + " DMG");
            clicked.damage(dmg);
            gunLoaded = false;
            ui.setReload(0);
            reloadTimer.restart();

            // Animate cannon fire
            resetAnimation();

            System.out.println(clicked.getType() + "HP: " + clicked.getHP());
            System.out.println("---------------------------------");
        }
        else{
            System.out.println("-------------PLAYER MOVE---------------");
            System.out.println(clicked.getType() + " HAS BEEN DESTROYED! CANNOT TARGET!");
            System.out.println("---------------------------------");
        }
    }

    /**
     * Check if reload has happened
     */
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

    public void addEventComplete(){
        eventsCompleted++;
    }

    public int getEventsCompleted(){
        return eventsCompleted;
    }

    public void setEnemyShip(EnemyShip enemyShip){
        this.enemyShip = enemyShip;
    }
}