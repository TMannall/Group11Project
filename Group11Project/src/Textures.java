import org.jsfml.graphics.IntRect;
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
    public Sprite button = LoadFrameTexture("textures/user_interface.png", 23, 21, 250, 60); // hover: 23, 100, 250, 60   push: 23, 179, 250, 60
    public Sprite buttonSmall = LoadFrameTexture("textures/user_interface.png", 300, 21, 125, 60); // hover: 300, 100, 125, 60   push: 300, 179, 125, 60
    public Sprite shipIcon = LoadFrameTexture("textures/user_interface.png", 549, 11, 254, 92);
    public Sprite waypoint = LoadFrameTexture("textures/user_interface.png", 466, 24, 56, 56); // visited: 466, 103, 56, 56
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
    public Sprite LoadFrameTexture(String fileName, int ax, int ay, int bx, int by) {
        Texture texture = new Texture( );

        try {
            texture.loadFromFile(Paths.get(fileName));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }

        texture.setSmooth(true);
        Sprite sprite = new Sprite(texture);
        sprite.setTextureRect(new IntRect(ax, ay, bx, by));
        return sprite;
    }
}
