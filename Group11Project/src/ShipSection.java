import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

import java.util.Random;

public class ShipSection extends Actor{
    private Ship ship;
    private String type;
    private Random randGenerator;
    private int HP = 100;
    private boolean targetable = true;

    public ShipSection(Textures textures, GameDriver driver, RenderWindow window, String texture, String type, Ship ship){
        super(textures, driver, window, texture);
        this.ship = ship;
        this.type = type;
        randGenerator = new Random();
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

