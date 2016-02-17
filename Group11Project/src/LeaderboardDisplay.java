import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.FloatRect;
import org.jsfml.window.event.MouseEvent;
import org.jsfml.window.event.MouseButtonEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * LeaderboardDisplay state for Endless Sea
 */
public class LeaderboardDisplay extends FSMState{

    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    private static int numberOfButtons = 11;
    private static int buttonIndex = 0;
    Text[] text = new Text[numberOfButtons];
    Text title;
    FloatRect[] rect = new FloatRect[numberOfButtons];

    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static int titleFontSize = 80;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;

    private static String Title = "Leaderboard";
    private Leaderboard leaderboard = new Leaderboard();

    public LeaderboardDisplay(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures) {
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
        title.setColor(Color.CYAN);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }

        for (int i = 0; i < numberOfButtons-1; i++) {
            text[i].setFont(sansRegular);
            text[i].setColor(Color.CYAN);

            text[i].setString((i+1) + ". " + leaderboard.getName(i+1) + " - " + leaderboard.getScore(i+1) + " - " + leaderboard.getDate(i+1));
            text[i].setPosition(driver.getWinWidth() / 2, 200+(i*40));
            text[i].setOrigin(text[i].getLocalBounds().width / 2, text[i].getLocalBounds().height / 2);
        }

        text[10].setFont(sansRegular);
        text[10].setColor(Color.CYAN);
        text[10].setString("Main Menu");
        text[10].setPosition(driver.getWinWidth() / 2, 660);
        text[10].setOrigin(text[10].getLocalBounds().width / 2, text[10].getLocalBounds().height / 2);
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

        showLeaderboard();
    }

    public void showLeaderboard() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);
        window.draw(title);

        Sprite[] textButton = new Sprite[numberOfButtons];
        Sprite[] hoverButton = new Sprite[numberOfButtons];
        Sprite[] pushButton = new Sprite[numberOfButtons];

        for(int i = 10; i < numberOfButtons; i++){
            textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
            hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
            pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
        }

        for(int i = 10; i < numberOfButtons; i++){

            textButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
            hoverButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
            pushButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
            window.draw(textButton[i]);
        }

        for (int i = 0; i < numberOfButtons; i++) {
            window.draw(text[i]);
        }


        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.RETURN) {
                        stateMachine.setState(stateMachine.getStates().get(0));
                    }
                    break;
                case MOUSE_BUTTON_PRESSED:
                    MouseEvent mouseClicked = event.asMouseButtonEvent();
                    if(mouseClicked.type == Event.Type.MOUSE_BUTTON_PRESSED){
                        //System.out.println("Pos Clicked: " + mouseClicked.position);
                        for(int i = 10; i < numberOfButtons; i++){
                            rect[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
                        }
                        //Main Menu
                        if(rect[10].contains(mouseClicked.position.x, mouseClicked.position.y)){
                            window.draw(pushButton[10]);
                            window.draw(text[10]);
                            stateMachine.setState(stateMachine.getStates().get(0));
                        }
                    }
                    break;
            }
        }
        window.display();
    }
}