import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

public abstract class Actor {
    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;
    protected Sprite sprite;

    public Actor(Textures textures, GameDriver driver, RenderWindow window, String texture){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        sprite = textures.LoadTexture(texture);
    }

}
