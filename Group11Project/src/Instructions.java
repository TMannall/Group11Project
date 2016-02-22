import org.jsfml.graphics.*;
import org.jsfml.window.event.Event;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Class used to display the instructions for the game, these are divided up into different pages in order to explain
 * each aspect of the game.
 */
public class Instructions extends FSMState{

    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    private static int numberOfButtons = 3;
    Text[] text = new Text[numberOfButtons];
    IntRect[] recti = new IntRect[numberOfButtons];
    FloatRect[] rectf = new FloatRect[numberOfButtons];
    String[] buttonString= {"Previous","Menu", "Next"};
    Sprite[] instBackdrop = new Sprite[7];
    int[] backdropYPos = {0,720,1440,2160,2880,3600,4320};

    int currentInstruction = 0;

    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";
    private static String FontFile = "vinque.ttf";
    private String FontPath;


    float[] leftBound = new float[numberOfButtons];
    float[] rightBound = new float[numberOfButtons];
    float[] topBound = new float[numberOfButtons];
    float[] bottomBound = new float[numberOfButtons];
    Sprite[] textButton = new Sprite[numberOfButtons];
    Sprite[] hoverButton = new Sprite[numberOfButtons];
    Sprite[] pushButton = new Sprite[numberOfButtons];
    Font fontStyle;

    public Instructions(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        setup();
    }

    public void setup(){

        if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;
        fontStyle = new Font();
        try {
            fontStyle.loadFromFile(
                    Paths.get(FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }

        for(int i = 0; i < 7; i++)
        {
            instBackdrop[i] = textures.createSprite(textures.instructions, 0, backdropYPos[i], 1280, 720);
            instBackdrop[i].setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        }

        for(int i = 0; i < numberOfButtons; i++)
        {
            text[i].setFont(fontStyle);
            text[i].setColor(Color.CYAN);
            text[i].setString(buttonString[i]);
            text[i].setPosition(295 + (i*350), 680);
            text[i].setOrigin(text[i].getLocalBounds().width / 2, text[i].getLocalBounds().height / 2);
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
        for(int i = 0; i < numberOfButtons; i++){
            rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top,
                    textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
            recti[i] = new IntRect(rectf[i]);
        }
    }

    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        displayMenu();
    }

    public void displayMenu()
    {
        window.draw(instBackdrop[currentInstruction]);
        for(int i = 0; i < numberOfButtons; i++){
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    for(int i = 0; i < 3; i++){
                        leftBound[i] = textButton[i].getGlobalBounds().left;
                        rightBound[i] = leftBound[i] + textButton[i].getGlobalBounds().width;
                        topBound[i] = textButton[i].getGlobalBounds().top;
                        bottomBound[i] = topBound[i] + textButton[i].getGlobalBounds().height;
                    }
                    for(int i = 0; i < 3; i++) {
                        if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {
                            switch (i) {
                                case 0:    //Previous
                                    System.out.println("Previous");
                                    if(currentInstruction > 0)
                                        currentInstruction--;
                                    break;
                                case 1: //Menu
                                    System.out.println("Menu");
                                    currentInstruction = 0;
                                    stateMachine.setState(stateMachine.getStates().get(0));
                                    break;
                                case 2: //Next
                                    System.out.println("Next");
                                    if(currentInstruction < 6)
                                        currentInstruction++;
                                    break;
                            }
                        }
                    }
                    break;
            }
        }
        window.display();
    }
}