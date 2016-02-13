import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Textures class for Endless Sea, handles loading textures and sprites for the game
 */
public class Textures {

    // Splash screens
    public Texture mainMenu_ = loadTexture("textures/main_menu.png");
    public Sprite mainMenu = createSprite(mainMenu_, 0, 0, 1920, 1080);
    public Texture ocean_ = loadTexture("textures/ocean.png");
    public Sprite ocean = createSprite(ocean_, 0, 0, 1280, 720);

    // User interface
    public Texture userInterface = loadTexture("textures/user_interface.png");
    public Sprite button = createSprite(userInterface, 23, 21, 250, 60); // hover: 23, 100, 250, 60   push: 23, 179, 250, 60
    public Sprite buttonSmall = createSprite(userInterface, 300, 21, 125, 60); // hover: 300, 100, 125, 60   push: 300, 179, 125, 60
    public Sprite shipIcon = createSprite(userInterface, 549, 11, 254, 92);
    public Sprite waypoint = createSprite(userInterface, 466, 24, 56, 56); // visited: 466, 103, 56, 56

    private Texture mapDecoration = loadTexture("textures/map_decoration.png");
    public Sprite island1 = createSprite(mapDecoration, 0, 0, 179, 114);
    public Sprite island2 = createSprite(mapDecoration, 182, 0, 168, 131);
    public Sprite island3 = createSprite(mapDecoration, 369, 5, 166, 125);
    public Sprite island4 = createSprite(mapDecoration, 540, 5, 167, 193);
    public Sprite island5 = createSprite(mapDecoration, 4, 144, 164, 194);
    public Sprite island6 = createSprite(mapDecoration, 10, 360, 127, 67);
    public Sprite island7 = createSprite(mapDecoration, 431, 227, 200, 192);
    public Sprite islandPort = createSprite(mapDecoration, 200, 136, 219, 276);

    public Texture healthBarBg = loadTexture("textures/hpbar_back_test.png");
    public Texture healthBarFg = loadTexture("textures/hpbar_front_test.png");

    // Unit sprites
    public Texture sailor1 = loadTexture("textures/sailor_1.png"); // hello sailor!

    public Texture britishMarine = loadTexture("textures/marine_british_fire_musket.png");
    public Texture frenchMarine = loadTexture("textures/marine_french_fire_musket.png");
    public Texture spanishMarine = loadTexture("textures/marine_spanish_fire_musket.png");
    public Texture neutralMarine = loadTexture("textures/marine_neutral_fire_musket.png");
    public Sprite britishMarineFire = createSprite(britishMarine, 0, 0, 65, 185);
    public Sprite frenchMarineFire = createSprite(frenchMarine, 0, 0, 65, 185);
    public Sprite spanishMarineFire = createSprite(spanishMarine, 0, 0, 65, 185);
    public Sprite neutralMarineFire = createSprite(neutralMarine, 0, 0, 65, 185);

    // Ship models
    public Texture shipLv1 = loadTexture("textures/ship_level_1.png");
    public Texture shipLv2 = loadTexture("textures/ship_level_1.png");
    public Texture shipLv3 = loadTexture("textures/ship_level_3.png");
        // player ship
    public Sprite shipBridge = createSprite(shipLv3, 0, 0, 525, 365);
    public Sprite shipGunDeck = createSprite(shipLv3, 525, 0, 343, 125);
    public Sprite shipMasts = createSprite(shipLv3, 525, 129, 343, 115);
    public Sprite shipSupplies = createSprite(shipLv3, 525, 241, 343, 125);
    public Sprite shipMedical = createSprite(shipLv3, 868, 0, 826, 365);
        // ai ship
    public Sprite AIshipBridge = createSprite(shipLv3, 0, 0, 525, 365);
    public Sprite AIshipGunDeck = createSprite(shipLv3, 525, 0, 343, 125);
    public Sprite AIshipMasts = createSprite(shipLv3, 525, 129, 343, 115);
    public Sprite AIshipSupplies = createSprite(shipLv3, 525, 241, 343, 125);
    public Sprite AIshipMedical = createSprite(shipLv3, 868, 0, 826, 365);


    // Character portraits
    public Texture captainTestPortrait = loadTexture("textures/captain_portrait_example.png");


    /**
     * loads a texture from file, throws exception when this fails
     *
     * @param fileName texture file path
     * @return returns the loaded texture to be used for sprites
     */
    public Texture loadTexture(String fileName) {
        Texture texture = new Texture();
        try {
            texture.loadFromFile(Paths.get(fileName));
        } catch (IOException ex) {
            ex.printStackTrace( );
        }
        texture.setSmooth(true);
        return texture;
    }

    /**
     * creates a sprite from a texture, this is done separately so we can have
     * multiple sprites using the same texture
     *
     * @param texture texture file to use
     * @param x x starting coordinate
     * @param y y starting coordinate
     * @param width width
     * @param height height
     * @return resturns the sprite for in-game use
     */
    public Sprite createSprite(Texture texture, int x, int y, int width, int height) {
        Sprite sprite = new Sprite(texture);
        sprite.setTextureRect(new IntRect(x, y, width, height));
        sprite.setOrigin(Vector2f.div(
                new Vector2f(width, height), 2));
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
     * @param sprite sprite to change
     * @param x x starting coordinate
     * @param y y starting coordinate
     * @param width width
     * @param height height
     * @return returns the new frame for the sprite
     */
    public Sprite setFrame(Sprite sprite, int x, int y, int width, int height) {
        sprite.setTextureRect(new IntRect(x, y, width, height));
        sprite.setOrigin(Vector2f.div(
                new Vector2f(width, height), 2));
        return sprite;
    }
}
