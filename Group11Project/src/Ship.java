import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

public class Ship extends Actor{
    private int hullHP;  // Overall ship integrity; 0 = game over, ship sinks
    private int gunStr; // Cannon strength (modifies damage dealt)

    private ShipSection guns;
    private ShipSection masts;
    private ShipSection bridge;
    private ShipSection hold;
    private ShipSection quarters;

    private enum Sections{GUNS, MASTS, BRIDGE, HOLD, QUARTERS};

    public Ship(Textures textures, GameDriver driver, RenderWindow window){
        guns = new ShipSection(Textures textures, GameDriver driver, RenderWindow window, SPRITE);
        masts = new ShipSection(Textures textures, GameDriver driver, RenderWindow window, SPRITE);
        bridge = new ShipSection(Textures textures, GameDriver driver, RenderWindow window, SPRITE);
        hold = new ShipSection(Textures textures, GameDriver driver, RenderWindow window, SPRITE);
        quarters = new ShipSection(Textures textures, GameDriver driver, RenderWindow window, SPRITE);
    }

    public void setPos(Sections section, float xPos, float yPos){
        switch(section){
            case GUNS:
                guns.sprite.setPosition(xPos,yPos);
                break;
            case MASTS:
                masts.sprite.setPosition(xPos, yPos);
                break;
            case BRIDGE:
                bridge.sprite.setPosition(xPos, yPos);
                break;
            case HOLD:
                hold.sprite.setPosition(xPos, yPos);
                break;
            case QUARTERS:
                quarters.sprite.setPosition(xPos, yPos);
        }
    }

    public void draw(){
        window.draw(guns.sprite);
        window.draw(masts.sprite);
        window.draw(bridge.sprite);
        window.draw(hold.sprite);
        window.draw(quarters.sprite);
    }
}
