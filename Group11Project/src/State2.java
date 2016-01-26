/**
 * Created by Rohjo on 23/01/2016.
 * Updated by TMnannall on 26/01/2016.
 */

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

public class State2 implements FSMState {

     // Define resolution of screen Width
     private static int screenWidth = 1280;
     // Define resolution of screen Height
     private static int screenHeight = 720;
     // Define the Game title
     private static String gameName = "Endless Ocean";

     public void showGame() {
         // Create a window
         RenderWindow window = new RenderWindow();
         window.create(new VideoMode(screenWidth, screenHeight), gameName, WindowStyle.DEFAULT);

         // Limit Frame Rate to 30
         window.setFramerateLimit(30);

         // Main loop
         while (window.isOpen()) {

             // Update window
             window.display();

             // Handle events
             for (Event event : window.pollEvents()) {
                 if (event.type == Event.Type.CLOSED) {
                     // The user pressed the close button
                     window.close();
                 }
             }
         }
     }

    public void execute(){
        System.out.println("State2 is executing!");
        State2 t = new State2();
        t.showGame();
    }
}
