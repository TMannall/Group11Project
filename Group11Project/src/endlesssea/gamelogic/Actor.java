package endlesssea.gamelogic;

import endlesssea.GameDriver;
import endlesssea.Textures;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class Actor {
    protected Textures textures;
    protected GameDriver driver;
    protected RenderWindow window;
    protected Sprite sprite;
    protected Texture t;

    public Actor(Textures textures, GameDriver driver, RenderWindow window, Sprite sprite){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        //t = endlesssea.textures.loadTexture(texture);
        //sprite = endlesssea.textures.createSprite(t, 0, 0, 1280, 720);
        // endlesssea.textures.LoadTexture(texture);
        this.sprite = sprite;
    }

}
