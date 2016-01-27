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

    // user interface
    private Sprite userInterface = LoadTexture("textures/user_interface.png");
    public Sprite button = setFrame(userInterface, 23, 21, 250, 60); // hover: 23, 100, 250, 60   push: 23, 179, 250, 60
    public Sprite buttonSmall = setFrame(userInterface, 300, 21, 125, 60); // hover: 300, 100, 125, 60   push: 300, 179, 125, 60
    public Sprite shipIcon = setFrame(userInterface, 549, 11, 254, 92);
    public Sprite waypoint = setFrame(userInterface, 466, 24, 56, 56); // visited: 466, 103, 56, 56

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

    /**
     * takes a loaded texture and returns a defined section as a frame
     * this allows us to only load the texture once.
     *
     * to change frames, use textureName.setTextureRect(new IntRect(a, b, c, d));
     * where a, b, c, d corresponds to the new frame.
     *
     * @param sprite
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @return
     */
    public Sprite setFrame(Sprite sprite, int ax, int ay, int bx, int by) {
        sprite.setTextureRect(new IntRect(ax, ay, bx, by));
        return sprite;
    }
}
