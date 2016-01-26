import org.jsfml.graphics.RenderWindow;

/**
 * Main menu state for Endless Sea
 */
public class Menu implements FSMState {

    RenderWindow window;
    public Menu(RenderWindow window){
        this.window = window;
    }

    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {
        System.out.println("Menu executing!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
