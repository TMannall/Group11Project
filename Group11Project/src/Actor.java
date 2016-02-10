import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class Actor {
    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;
    protected Sprite sprite;
    protected Texture t;

    public Actor(Textures textures, GameDriver driver, RenderWindow window, String texture){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        t = textures.loadTexture(texture);
        sprite = textures.createSprite(t, 0, 0, 1280, 720); //textures.LoadTexture(texture);
    }

}
