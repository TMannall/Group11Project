import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

public class ShipSection extends Actor{
    private Ship ship;
    private String type;
    private int HP = 100;
    private boolean targetable = true;

    public ShipSection(Textures textures, GameDriver driver, RenderWindow window, Sprite texture, String type, Ship ship){
        super(textures, driver, window, texture);
        this.ship = ship;
        this.type = type;
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

            ship.damageHull(25);        // Section destroyed so damage overall ship integrity
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

