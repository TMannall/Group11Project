import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Textures {

    public LoadTexture mainMenu;
    public LoadTexture captainTestPortrait;

    public Textures() {
        mainMenu = new LoadTexture("textures/main_menu.png");
        captainTestPortrait = new LoadTexture("textures/captain_portrait_example.png");
    }


    public class LoadTexture {
        public LoadTexture(String fileName) {
            Texture texture = new Texture();
            try {
                texture.loadFromFile(Paths.get(fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            texture.setSmooth(true);
        }
    }

    /**
     * takes the
     */
    public class LoadSprite {
        public LoadSprite(String fileName) {
            Texture texture = new Texture( );
            try {
                texture.loadFromFile(Paths.get(fileName));
            } catch (IOException ex) {
                ex.printStackTrace( );
            }
            texture.setSmooth(true);

            Sprite img = new Sprite(texture);
            img.setOrigin(Vector2f.div(
                    new Vector2f(texture.getSize()), 2));
        }
    }


    public class LoadFrameTexture {
        public LoadFrameTexture(String fileName) {
            Texture texture = new Texture();
            try {
                texture.loadFromFile(Paths.get(fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            texture.setSmooth(true);
        }
    }
}
