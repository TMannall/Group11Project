import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class ShipSection extends Actor{
    private Ship ship;
    private String type;
    private Sprite icon;
    private Random randGenerator;
    private double weight;      // Chance of being selected by enemy ship if this belongs to a player
    private int HP = 100;
    private boolean targetable = true;

    public ShipSection(Textures textures, GameDriver driver, RenderWindow window, Sprite sprite, String type, Ship ship){
        super(textures, driver, window, sprite);
        this.ship = ship;
        this.type = type;
        randGenerator = new Random();

        if(ship.shipType == Ship.ShipType.PLAYER){
            switch(type){
                case "Guns":
                    icon = textures.createSprite(textures.userInterface, 934, 383, 50, 50);
                    icon.setPosition(0, 0);
                    break;
                case "Masts":
                    icon = textures.createSprite(textures.userInterface, 988, 383, 50, 50);
                    icon.setPosition(280, 540);
                    break;
                case "Bridge":
                    icon = textures.createSprite(textures.userInterface, 988, 448, 50, 50);
                    icon.setPosition(0,0);
                    break;
                case "Hold":
                    icon =  textures.createSprite(textures.userInterface, 934, 448, 50, 50);
                    icon.setPosition(0, 0);
                    break;
                case "Quarters":
                    icon = textures.createSprite(textures.userInterface, 934, 511, 50, 50);
                    icon.setPosition(0, 0);
                    break;
            }
        }
        else{
            switch(type){
                case "Guns":
                    icon = textures.createSprite(textures.userInterface, 934, 383, 50, 50);
                    icon.setPosition(580, 260);
                    break;
                case "Masts":
                    icon = textures.createSprite(textures.userInterface, 988, 383, 50, 50);
                    icon.setPosition(580, 208);
                    break;
                case "Bridge":
                    icon = textures.createSprite(textures.userInterface, 988, 448, 50, 50);
                    icon.setPosition(760, 208);
                    break;
                case "Hold":
                    icon =  textures.createSprite(textures.userInterface, 934, 448, 50, 50);
                    icon.setPosition(580, 156);
                    break;
                case "Quarters":
                    icon = textures.createSprite(textures.userInterface, 934, 511, 50, 50);
                    icon.setPosition(454, 208);
                    break;
            }
        }
        icon.setScale((float)0.75, (float)0.75);
    }

    public String getType(){
        return type;
    }

    public void damage(int change){
        HP -= change;

        if(HP <= 0){
            HP = 0;
            targetable = false;
            System.out.println(type + " HAS BEEN DESTROYED!");
            // Section destroyed so damage overall ship integrity (rand number 25-40)
            int hullDmg = randGenerator.nextInt(40 - 25 + 1) + 25;
            ship.damageHull(hullDmg);
        }
    }

    public int getHP(){
        return HP;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public Sprite getIcon(){
        return icon;
    }

    public void repair(int change){
        HP += change;
        if(HP > 100)
            HP = 100;
        if(HP > 0)
            targetable = true;
    }

    public boolean isTargetable(){
        return targetable;
    }
}

