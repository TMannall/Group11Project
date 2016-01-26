import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Textures {

    // splash screens
    public Sprite mainMenu = LoadTexture("textures/main_menu.png");
    public Sprite ocean = LoadTexture("textures/ocean.png");

    // user interface (will be frames)
    public Sprite userInterface = LoadTexture("textures/user_interface.png");
    public Sprite sailor1 = LoadTexture("textures/sailor_1.png");

    // ship models
    public Sprite shipLv1 = LoadTexture("textures/ship_level_1.png");
    public Sprite shipLv2 = LoadTexture("textures/ship_level_1.png");
    public Sprite shipLv3 = LoadTexture("textures/ship_level_3.png");

    // character portraits
    public Sprite captainTestPortrait = LoadTexture("textures/captain_portrait_example.png");


    /**
     * takes the location of the texture and returns the image as a sprite ready
     * to be used in-game
     *
     * @param fileName
     * @return
     */
    public Sprite LoadTexture(String fileName) {
        Texture texture = new Texture( );

        try {
            texture.loadFromFile(Paths.get(fileName));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        texture.setSmooth(true);

        Sprite sprite = new Sprite(texture);
        sprite.setOrigin(Vector2f.div(
                new Vector2f(texture.getSize()), 2));

        return sprite;
    }

    // frames wip
    public Sprite LoadFrameTexture(String fileName) {
        Texture texture = new Texture( );

        try {
            texture.loadFromFile(Paths.get(fileName));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        texture.setSmooth(true);
        Sprite sprite = new Sprite(texture);
        //sprite.setTextureRect();
        return sprite;
    }
}
