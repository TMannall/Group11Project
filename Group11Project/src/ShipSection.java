import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

public class ShipSection extends Actor{
    private int HP;

    public ShipSection(Textures textures, GameDriver driver, RenderWindow window, Sprite sprite){
        super(textures, driver, window, sprite);
    }

}

