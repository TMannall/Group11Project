import org.jsfml.graphics.RenderWindow;

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
        textures.mainMenu.setPosition(driver.getWinWidth()/2, driver.getWinHeight()/2);
        window.draw(textures.mainMenu);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
