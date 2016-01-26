import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

/**
 * Main menu state for Endless Sea
 */
public class Menu implements FSMState {

    RenderWindow window;
    Textures textures;
    public Menu(RenderWindow window, Textures textures){
        this.window = window;
        this.textures = textures;
    }

    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {
        System.out.println("Menu executing!");

        textures.mainMenu.setPosition(0, 0);
        window.draw(textures.mainMenu);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
