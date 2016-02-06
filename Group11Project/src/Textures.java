import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Textures {
    // to make the animations work for individual sprites, the sprites need to be loaded in the actual game class files
    // the framed textures with animations that are loaded here are templates to be copied and used elsewhere with new names
    // if an animated sprite has the same variable name as another, both of the sprites will animate at the same time


    // splash screens
    public Sprite mainMenu = LoadTexture("textures/main_menu.png");
    public Sprite ocean = LoadTexture("textures/ocean.png");

    // user interface
    public Sprite userInterface1 = LoadTexture("textures/user_interface.png");
    public Sprite userInterface2 = LoadTexture("textures/user_interface.png");
    public Sprite userInterface3 = LoadTexture("textures/user_interface.png");
    public Sprite userInterface4 = LoadTexture("textures/user_interface.png");
    public Sprite button = setFrame(userInterface1, 23, 21, 250, 60); // hover: 23, 100, 250, 60   push: 23, 179, 250, 60
    public Sprite buttonSmall = setFrame(userInterface2, 300, 21, 125, 60); // hover: 300, 100, 125, 60   push: 300, 179, 125, 60
    public Sprite shipIcon = setFrame(userInterface3, 549, 11, 254, 92);
    public Sprite waypoint = setFrame(userInterface4, 466, 24, 56, 56); // visited: 466, 103, 56, 56

    // unit sprites
    public Sprite sailor1 = LoadTexture("textures/sailor_1.png"); // hello sailor!

    public Sprite britishMarine = LoadTexture("textures/marine_british_fire_musket.png");
    public Sprite frenchMarine = LoadTexture("textures/marine_french_fire_musket.png");
    public Sprite spanishMarine = LoadTexture("textures/marine_spanish_fire_musket.png");
    public Sprite neutralMarine = LoadTexture("textures/marine_neutral_fire_musket.png");
    public Sprite britishMarineFire = setFrame(britishMarine, 0, 0, 65, 185);
    public Sprite frenchMarineFire = setFrame(frenchMarine, 0, 0, 65, 185);
    public Sprite spanishMarineFire = setFrame(spanishMarine, 0, 0, 65, 185);
    public Sprite neutralMarineFire = setFrame(neutralMarine, 0, 0, 65, 185);

    // ship models
    public Sprite ship1 = LoadTexture("textures/ship_level_3.png");
    public Sprite ship2 = LoadTexture("textures/ship_level_3.png");
    public Sprite ship3 = LoadTexture("textures/ship_level_3.png");
    public Sprite ship4 = LoadTexture("textures/ship_level_3.png");
    public Sprite ship5 = LoadTexture("textures/ship_level_3.png");
    public Sprite shipBridge = setFrame(ship1, 0, 0, 525, 365);
    public Sprite shipGunDeck = setFrame(ship2, 525, 0, 343, 125);
    public Sprite shipMasts = setFrame(ship3, 525, 129, 343, 115);
    public Sprite shipSupplies = setFrame(ship4, 525, 241, 343, 125);
    public Sprite shipMedical = setFrame(ship5, 868, 0, 826, 365);


    // character portraits
    public Sprite captainTestPortrait = LoadTexture("textures/captain_portrait_example.png");


    /**
     * takes the location of the texture and returns the image as a sprite ready
     * to be used in-game
     *
     * sets the origin of the texture to the centre
     *
     * @param fileName texture file path
     * @return loaded texture file
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
     * sets the origin of the frame to the centre
     *
     * @param sprite texture file to use
     * @param ax x starting coordinate
     * @param ay y starting coordinate
     * @param bx width
     * @param by height
     * @return returns the frame from the texture
     */
    public Sprite setFrame(Sprite sprite, int ax, int ay, int bx, int by) {
        sprite.setTextureRect(new IntRect(ax, ay, bx, by));
        sprite.setOrigin(Vector2f.div(
                new Vector2f(bx, by), 2));
        return sprite;
    }
}
