import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class Actor {
    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;
    protected Sprite sprite;

    public Actor(Textures textures, GameDriver driver, RenderWindow window, Sprite sprite){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        this.sprite = sprite;
    }

}
