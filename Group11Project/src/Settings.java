import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Settings state for Endless Sea
 */
public class Settings extends FSMState{

    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    private static int numberOfButtons = 4;
    private static int buttonIndex = 0;
    Text[] buttons = new Text[numberOfButtons];
    Text title;

    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static int titleFontSize = 80;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;

    private static String Title = "ENDLESS SEA Settings";
    private SoundClass sound = new SoundClass();

    public Settings(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures) {
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;

        setup();
    }

    public void setup(){
        if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;

        Font sansRegular = new Font();
        try {
            sansRegular.loadFromFile(
                    Paths.get(FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        title = new Text(Title, sansRegular, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 80);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            buttons[i] = new Text();
        }

        buttons[0].setFont(sansRegular);
        buttons[0].setColor(Color.BLUE);
        buttons[0].setString("Music Volume: " + sound.getMusicVolume());
        buttons[0].setPosition(driver.getWinWidth() / 2 - 90, 200);

        buttons[1].setFont(sansRegular);
        buttons[1].setColor(Color.BLACK);
        buttons[1].setString("Sound Volume: " + sound.getSoundVolume());
        buttons[1].setPosition(driver.getWinWidth() / 2 - 90, 270);

        buttons[2].setFont(sansRegular);
        buttons[2].setColor(Color.BLACK);
        buttons[2].setString("Setting 3");
        buttons[2].setPosition(driver.getWinWidth() / 2 - 90, 340);

        buttons[3].setFont(sansRegular);
        buttons[3].setColor(Color.BLACK);
        buttons[3].setString("Main Menu");
        buttons[3].setPosition(driver.getWinWidth() / 2 - 90, 410);
    }

    @Override
    public void execute() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);

        try {
            Thread.sleep(10); //1000); // this delays the animation frames if it's too high
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        displaySettings();
    }

    public void displaySettings() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);
        window.draw(title);

        for (int i = 0; i < numberOfButtons; i++) {
            window.draw(buttons[i]);
        }

        window.display();

        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.UP) {
                        moveUp();
                        break;
                    } else if (keyEvent.key == Keyboard.Key.DOWN) {
                        moveDown();
                        break;
                    }

                    /**
                     * This part of the code is where the button functionality is implemented.
                     * Use this part for integrating the menu with the main program.
                     */
                    else if (keyEvent.key == Keyboard.Key.RETURN) {
                        switch (getButtonIndex()) {
                            case 0: //New game

                                break;
                            case 1: //Settings 2

                                break;
                            case 2: //Settings 3

                                break;
                            case 3: //Main menu
                                stateMachine.setState(stateMachine.getStates().get(0));
                                break;
                        }
                    }
            }
        }
    }

    public void moveUp() {
        if (buttonIndex - 1 >= 0) {
            buttons[buttonIndex].setColor(Color.BLACK);
            buttonIndex--;
            buttons[buttonIndex].setColor(Color.YELLOW);
        }
    }

    public void moveDown() {
        if (buttonIndex + 1 < numberOfButtons) {
            buttons[buttonIndex].setColor(Color.BLACK);
            buttonIndex++;
            buttons[buttonIndex].setColor(Color.YELLOW);
        }
    }

    int getButtonIndex(){
        return buttonIndex;
    }
}