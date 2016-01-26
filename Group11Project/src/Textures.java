import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Textures {

    public Sprite mainMenu;
    public Sprite captainTestPortrait;

    public Textures() {
        mainMenu = LoadTexture("textures/main_menu.png");
        captainTestPortrait = LoadTexture("textures/captain_portrait_example.png");
    }


    public Sprite LoadTexture(String fileName) {
        Texture texture = new Texture( );

        try {
            texture.loadFromFile(Paths.get(fileName));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        texture.setSmooth(true);

        Sprite image = new Sprite(texture);
        return image;
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
