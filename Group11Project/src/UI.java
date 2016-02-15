import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;

public class UI {

    private Textures textures;
    private GameDriver driver;
    private RenderWindow window;
    private Ship playerShip;

    private Sprite healthBarBg;
    private Sprite healthBarFg;

    private int previousHP = 100;

    public UI(Textures textures, GameDriver driver, RenderWindow window, Ship playerShip){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        this.playerShip = playerShip;
        healthBarBg = textures.createSprite(textures.healthBarBg, 0, 0, 200, 20);
        healthBarFg = textures.createSprite(textures.healthBarFg, 0, 0, 200, 20);

        // Setting origins to 0,0 because the foreground one must be 0,0 for scaling resizing to work properly
        healthBarBg.setOrigin(0,0);
        healthBarBg.setPosition(300, 10);
        healthBarFg.setOrigin(0, 0);
        healthBarFg.setPosition(300, 10);

    }

    // Called to draw HP bar as a single thing where it's meant to be on the UI
    // Actual implementation draws each sprite as necessary
    public void draw(){

        // Draw background where it should be
        // Draw foreground on top of background, where its width is proportional to the current health
        window.draw(healthBarBg);

        // If hull has been damaged, animate health bar
        if(playerShip.getHullHP() < previousHP){
            previousHP--;
        }

        float scale = previousHP/(float)100;
        healthBarFg.setScale(scale, 1);
        window.draw(healthBarFg);
    }

}
