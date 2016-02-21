import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

import java.util.Random;

public class ShipSection extends Actor{
    private Ship ship;
    private String type;
    private Sprite icon;
    private Random randGenerator;
    private double weight;      // Chance of being selected by enemy ship if this belongs to a player
    private int HP = 100;
    private boolean targetable = true;

    public ShipSection(Textures textures, GameDriver driver, RenderWindow window, Sprite sprite, String type, Ship ship, String shipType){
        super(textures, driver, window, sprite);
        this.type = type;
        this.ship = ship;
        randGenerator = new Random();

        if(shipType.equals("Enemy")){
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
                    icon = textures.createSprite(textures.userInterface, 934, 448, 50, 50);
                    icon.setPosition(580, 156);
                    break;
                case "Quarters":
                    icon = textures.createSprite(textures.userInterface, 934, 511, 50, 50);
                    icon.setPosition(454, 208);
                    break;
            }
        }
        else{
            switch(type) {
                case "Guns":
                    icon = textures.createSprite(textures.userInterface, 934, 383, 50, 50);
                    icon.setPosition(629, 460);
                    break;
                case "Masts":
                    icon = textures.createSprite(textures.userInterface, 988, 383, 50, 50);
                    icon.setPosition(629, 508);
                    break;
                case "Bridge":
                    icon = textures.createSprite(textures.userInterface, 988, 448, 50, 50);
                    icon.setPosition(450, 508);
                    break;
                case "Hold":
                    icon = textures.createSprite(textures.userInterface, 934, 448, 50, 50);
                    icon.setPosition(629, 564);
                    break;
                case "Quarters":
                    icon = textures.createSprite(textures.userInterface, 934, 511, 50, 50);
                    icon.setPosition(749, 508);
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

            switch(type) {
                case "Guns":
                    icon.setTextureRect(new IntRect(1051, 383, 50, 50));
                    break;
                case "Masts":
                    icon.setTextureRect(new IntRect(1105, 383, 50, 50));
                    break;
                case "Bridge":
                    icon.setTextureRect(new IntRect(1105, 448, 50, 50));
                    break;
                case "Hold":
                    icon.setTextureRect(new IntRect(1051, 448, 50, 50));
                    break;
                case "Quarters":
                    icon.setTextureRect(new IntRect(1051, 511, 50, 50));
                    break;
            }
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

