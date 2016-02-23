import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Abstract ship class, used to initiate the ship settings to be inherited by the PlayerShip and EnemyShip classes.
 */
public abstract class Ship{
    public enum ShipType{
        PLAYER, STANDARD            // STANDARD = STANDARD ENEMY SHIP, REPLACE W/ BRITISH, DUTCH ETC LATER
    }

    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;
    protected SoundFX sound;

    protected Random randGenerator;
    protected Timer reloadTimer;
    protected ShipType shipType;

    protected int baseReload = 5;     // Base reload time across the game (seconds)
    protected boolean gunLoaded = true;   // True when cannons can fire; false when reloading

    // Game-mutable ship stats
    private int playerScore = 0;    // Player's score for the leaderboard
    private int currGold = 0;
    private int maxFood = 100;
    private int currFood = maxFood;
    private int maxWater = 100;
    private int currWater = maxWater;

    private int hullHP = 100;  // Overall ship integrity; 0 = game over, ship sinks
    protected float gunStr = 1; // Cannon strength (modifies damage dealt). 1 = default starting strength
    protected float reloadBoost = (float)1;   // Cannon reload modifier. 1 = reloads at standard rate, 2 = double rate etc
    private int mastSpeed = 1; // Mast speed modifier. 1 standard rate, 2 = double rate
    protected float bridgeDefence = 1; // Defence modifier. 1 = standard defence, 2 = double defence (halves dmg received)
    private int quartersRegainStr = 1; // Quarters HP regain modifier. 1 = standard, 2 = double regain

    protected ShipSection guns;
    protected ShipSection masts;
    protected ShipSection bridge;
    protected ShipSection hold;
    protected ShipSection quarters;

    protected ArrayList<ShipSection> sections;

    protected ArrayList<Sprite> gunAnimations;
    protected Clock animClock;
    int[] gunAnimFrames = new int[10];

    protected ArrayList<Sprite> marineAnimations;
    int[] marineAnimFrames = new int[10];

    protected float scale;
    protected int xPos;
    protected int yPos;

    public Ship(Textures textures, GameDriver driver, RenderWindow window, Random randGenerator, SoundFX sound, ShipType shipType, float scale, int xPos, int yPos){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        this.randGenerator = randGenerator;
        this.sound = sound;
        this.shipType = shipType;
        this.scale = scale;
        this.xPos = xPos;
        this.yPos = yPos;

        sections = new ArrayList<>();
        gunAnimations = new ArrayList<>();
        marineAnimations = new ArrayList<>();

        // Setup cannon smoke animation sprites - must be positioned individually for player and enemy ships
        animClock = new Clock();
        for(int i = 0; i < 10; i++) {
            gunAnimations.add(textures.createSprite(textures.cannonSmoke, 0, 0, 51, 114));
            gunAnimations.get(i).setTextureRect(new IntRect(0, 0, 0, 0));       // Hides to begin with
            gunAnimations.get(i).setScale(scale, scale);
            gunAnimFrames[i] = 28;          // prevents firing straight away (only when attacking)
        }
    }

    // Abstract setup method that should be called on all subclasses after the super constructor to set textures/sprites
    public abstract void setup();

    public void draw(){
        for(ShipSection section : sections){
            window.draw(section.sprite);
            window.draw(section.getIcon());
        }
        for(Sprite smoke : gunAnimations){
            window.draw(smoke);
        }
    }

    // Animate ONE loop of the cannon fire
    public void animateGuns(){
        if(animClock.getElapsedTime().asMilliseconds() >= 50){
                animClock.restart();

                for(int i = 0; i < 10; i++){
                    gunAnimFrames[i]++;

                    if (gunAnimFrames[i] == 5) {
                        sound.playSoundOnce("cannon_02");
                    }

                    if(gunAnimFrames[i] > 27) {
                        gunAnimFrames[i] = 28;
                    }

                    int frameRow = gunAnimFrames[i] / 12;
                    int frameCol = gunAnimFrames[i] % 12;

                    gunAnimations.get(i).setTextureRect(new IntRect(frameCol * 51, frameRow * 114, 51, 114));

                }
            }

    }

    // MUST BE CALLED BEFORE ANOTHER GUN ANIMATION CAN HAPPEN
    public void resetAnimation(){
        for (int i = 0; i < 10; i++){
            gunAnimFrames[i] = (i*3) - 30;
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

    public ShipSection validateHover(int x, int y){
        for(ShipSection section: sections){
            float leftBound = section.sprite.getGlobalBounds().left;
            float rightBound = leftBound + section.sprite.getGlobalBounds().width;
            float topBound = section.sprite.getGlobalBounds().top;
            float bottomBound = topBound + section.sprite.getGlobalBounds().height;

            if (x > leftBound && x < rightBound && y > topBound && y < bottomBound) {
                return section;
            }
        }
        return null;
    }

    public boolean isGunLoaded(){
        return gunLoaded;
    }

    public void setGunLoaded(boolean loaded){
        gunLoaded = loaded;
    }

    public float getGunStr(){
        return gunStr;
    }

    public Timer getReloadTimer(){
        return reloadTimer;
    }

    public int getHullHP(){
        return hullHP;
    }

    public int getCurrWater() {
        return currWater;
    }

    public void addWater(int change) {
        currWater += change;
        if(currWater > maxWater)
            currWater = maxWater;
    }

    public int getMaxWater() {
        return maxWater;
    }

    public void addMaxWater(int change) {
        maxWater += change;
    }

    public int getCurrFood() {
        return currFood;
    }

    public void addFood(int change) {
        currFood += change;
        if(currFood > maxFood)
            currFood = maxFood;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public void addMaxFood(int change) {
        maxFood += change;
    }

    public int getCurrGold() {
        return currGold;
    }

    public void addGold(int change) {
         currGold += change;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void addPlayerScore(int change) {
        playerScore += change;
    }

    public void addGunStr(float change) {
        gunStr += change;
    }

    public float getReloadBoost() {
        return reloadBoost;
    }

    public void addReloadBoost(float change) {
        reloadBoost += change;
    }

    public int getMastSpeed() {
        return mastSpeed;
    }

    public void addMastSpeed(int change) {
        mastSpeed += change;
    }

    public float getBridgeDefence() {
        return bridgeDefence;
    }

    public void addBridgeDefence(float change) {
        bridgeDefence += change;
    }

    public int getQuartersRegainStr() {
        return quartersRegainStr;
    }

    public void addQuartersRegainStr(int change) {
        quartersRegainStr += change;
    }
}