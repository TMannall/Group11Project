import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.text.DecimalFormat;


/**
 * Main menu state for Endless Sea
 */
public class Menu implements FSMState {

    GameDriver driver;
    RenderWindow window;
    Textures textures;
    public Menu(GameDriver driver, RenderWindow window, Textures textures){
        this.driver = driver;
        this.window = window;
        this.textures = textures;
    }

    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        //window.draw(textures.mainMenu);

        // jack: testing animations
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);

        textures.shipLv3.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        textures.shipLv3.setScale(1.2f, 1.2f);
        window.draw(textures.shipLv3);

        textures.britishMarineFire.setPosition(driver.getWinWidth() / 2 + 350, driver.getWinHeight() / 2 - 75);
        window.draw(textures.britishMarineFire);
        textures.spanishMarineFire.setPosition(driver.getWinWidth() / 2 + 400, driver.getWinHeight() / 2 - 80);
        window.draw(textures.spanishMarineFire);
        textures.frenchMarineFire.setPosition(driver.getWinWidth() / 2 + 450, driver.getWinHeight() / 2 - 70);
        window.draw(textures.frenchMarineFire);
        textures.neutralMarineFire.setPosition(driver.getWinWidth() / 2 + 500, driver.getWinHeight() / 2 - 85);
        window.draw(textures.neutralMarineFire);
        // jack: testing animations end

        try {
            Thread.sleep(10); //1000); // this delays the animation frames if it's too high
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
