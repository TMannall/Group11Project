import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Text;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Main menu state for Endless Sea, consists of a number of buttons which can be pressed to change which state is
 * being shown.
 */
public class Menu extends FSMState{

    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private SoundFX sound;

    private static int numberOfButtons = 5;
    Text[] text = new Text[numberOfButtons];
    Text title;
    IntRect[] recti = new IntRect[numberOfButtons];
    FloatRect[] rectf = new FloatRect[numberOfButtons];

    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static int titleFontSize = 80;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;

    public Color BROWN = new Color(56, 35, 5);

    //private static String Title = "ENDLESS SEA";
    private static String Title = "";

    public Menu(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, SoundFX sound) {
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        this.sound = sound;
        setup();
    }

    public void setup(){
        if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;

        Font fontStyle = new Font();
        try {
            fontStyle.loadFromFile(
                    Paths.get(FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        sound.playBackgroundMusic("music_main_menu");

        title = new Text(Title, fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 100);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(BROWN);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }

        text[0].setFont(fontStyle);
        text[0].setColor(BROWN);
        text[0].setString("New Game");
        text[0].setPosition(driver.getWinWidth() / 2, 280);
        text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(fontStyle);
        text[1].setColor(BROWN);
        text[1].setString("Instructions");
        text[1].setPosition(driver.getWinWidth() / 2, 350);
        text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);

        text[2].setFont(fontStyle);
        text[2].setColor(BROWN);
        text[2].setString("Leaderboard");
        text[2].setPosition(driver.getWinWidth() / 2, 420);
        text[2].setOrigin(text[2].getLocalBounds().width / 2, text[2].getLocalBounds().height / 2);

        text[3].setFont(fontStyle);
        text[3].setColor(BROWN);
        text[3].setString("Settings");
        text[3].setPosition(driver.getWinWidth() / 2, 490);
        text[3].setOrigin(text[3].getLocalBounds().width / 2, text[3].getLocalBounds().height / 2);

        text[4].setFont(fontStyle);
        text[4].setColor(BROWN);
        text[4].setString("Exit");
        text[4].setPosition(driver.getWinWidth() / 2, 560);
        text[4].setOrigin(text[4].getLocalBounds().width / 2, text[4].getLocalBounds().height / 2);

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

        displayMenu();
    }

    public void displayMenu() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);
        window.draw(title);

        textures.splashTitle.setPosition(driver.getWinWidth() / 2, 100);
        window.draw(textures.splashTitle);

        Sprite[] textButton = new Sprite[numberOfButtons];
        Sprite[] hoverButton = new Sprite[numberOfButtons];
        Sprite[] pushButton = new Sprite[numberOfButtons];

        for(int i = 0; i < numberOfButtons; i++){
            textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
            hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
            pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
        }

        for(int i = 0; i < numberOfButtons; i++){
            textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
            hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
            pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
        }

        for(int i = 0; i < numberOfButtons; i++){
            textButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
            pushButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
        }

        for(int i = 0; i < numberOfButtons; i++) {
            window.draw(textButton[i]);
            window.draw(text[i]);
        }

        // jack: sprite testing
        /*for(int i = 0; i < 5; i++) {
            driver.marineList.get(i).setPosition((200 * i/2), 500);
            window.draw(driver.marineList.get(i));
        }*/
        // jack: end sprite testing

        //window.draw(hoverButton[0]);
        //window.display();

        for(int i = 0; i < numberOfButtons; i++){
            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
            recti[i] = new IntRect(rectf[i]);
        }

        for(int i = 0; i < numberOfButtons; i++){
            if((recti[i].contains(Mouse.getPosition(window)) && isMouseOver())){
                textButton[i].setTextureRect(new IntRect(23, 100, 250, 60));
                window.draw(textButton[i]);
                window.draw(text[i]);
            }
            else if(!isMouseOver()){
                textButton[i].setTextureRect(new IntRect(23, 21, 250, 60));
                window.draw(textButton[i]);
                window.draw(text[i]);
            }
        }

        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    MouseEvent mouseClicked = event.asMouseButtonEvent();
                    if(mouseClicked.type == Event.Type.MOUSE_BUTTON_PRESSED){
                        for(int i = 0; i < numberOfButtons; i++){
                            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
                        }
                        //New Game
                        if(rectf[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
                            window.draw(pushButton[0]);
                            window.draw(text[0]);
                            sound.stopBackgroundMusic();
                            sound.playBackgroundMusic("ambient_ocean");
                            // Reset game
                            driver.genNewPlayer();
                            driver.map = new Map(stateMachine, driver, window, textures, driver.eventGenerator);
                            stateMachine.getStates().set(4, driver.map);
                            stateMachine.setState(stateMachine.getStates().get(3)); //go to cpt selection menu
                        }
                        //Instructions
                        if(rectf[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
                            window.draw(pushButton[1]);
                            window.draw(text[1]);
                            stateMachine.setState(stateMachine.getStates().get(8));
                        }
                        //Leaderboards
                        if(rectf[2].contains(mouseClicked.position.x, mouseClicked.position.y)){
                            window.draw(pushButton[2]);
                            window.draw(text[2]);
                            stateMachine.setState(stateMachine.getStates().get(2));
                        }
                        //Settings
                        if(rectf[3].contains(mouseClicked.position.x, mouseClicked.position.y)){
                            window.draw(pushButton[3]);
                            window.draw(text[3]);
                            stateMachine.setState(stateMachine.getStates().get(1));
                        }
                        //Exit
                        if(rectf[4].contains(mouseClicked.position.x, mouseClicked.position.y)){
                            window.draw(pushButton[4]);
                            window.draw(text[4]);
                            window.close();
                        }
                    }
                    break;
            }
        }
        window.display();
    }

    /**
     * Checks whether user has mouse over button
     * @return true
     */
    public boolean isMouseOver(){
        for(int i = 0; i < numberOfButtons; i++){
            if(recti[i].contains(Mouse.getPosition(window))){
                return true;
            }
        }
        return false;
    }
}