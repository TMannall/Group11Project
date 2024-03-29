import org.jsfml.graphics.*;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.util.Random;

/**
 * Explore Event class used to display the exploring the map events, this class deals with the window text and displaying of event text to the
 * player.
 */
public class ExploreEvent extends Events {

    Sprite messageScroll;
    private static int numberOfButtons = 2;
    Text[] text = new Text[numberOfButtons];
    Text title;
    IntRect[] recti = new IntRect[numberOfButtons];
    FloatRect[] rectf = new FloatRect[numberOfButtons];
    private int[] eventEffects = {0,0,0,0,0,0,0,0,0,0};
    public String attackedText = "";
    public String titleString = attackedText;

    float[] leftBound = new float[2];
    float[] rightBound = new float[2];
    float[] topBound = new float[2];
    float[] bottomBound = new float[2];
    Sprite[] textButton = new Sprite[numberOfButtons];
    Sprite[] hoverButton = new Sprite[numberOfButtons];
    Sprite[] pushButton = new Sprite[numberOfButtons];

    public ExploreEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, SoundFX sound){
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound);
        setup();
    }

    public void setup(){
        messageScroll = textures.createSprite(textures.ingameWindow_, 0, 0, 800, 500);	//MESSAGE SCROLL
        messageScroll.setPosition(driver.getWinWidth() / 2, 360);
        messageScroll.setScale((float)1.25, 1);

        title = new Text(eventGenerator.getEventText(), fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2 - 35, 300);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }
        text[0].setFont(fontStyle);
        text[0].setColor(driver.BROWN);
        text[0].setString("Explore");
        text[0].setPosition(500, 500);
        text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(fontStyle);
        text[1].setColor(driver.BROWN);
        text[1].setString("Decline");
        text[1].setPosition(770, 500);
        text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);

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


    public void execute(){
        if(consumeResources) {
            consumeResources = false;
            consumeResources();
        }
        window.clear();

        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        for(int i = 0; i < numberOfButtons; i++) {
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
        window.draw(messageScroll);
        window.draw(title);

        displayMenu();
        window.display();
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    for(int i = 0; i < 2; i++){
                        leftBound[i] = textButton[i].getGlobalBounds().left;
                        rightBound[i] = leftBound[i] + textButton[i].getGlobalBounds().width;
                        topBound[i] = textButton[i].getGlobalBounds().top;
                        bottomBound[i] = topBound[i] + textButton[i].getGlobalBounds().height;
                    }
                    // Add events/actions here when islands are clicked on
                    for(int i = 0; i < 2; i++) {
                        if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {
                            switch (i) {
                                case 0:
                                    FSMState consequence = new AfterEvent(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound, AfterEvent.Consequence.EXPLORE_ACCEPT);
                                    stateMachine.setState(consequence);
                                    break;
                                case 1:
                                    stateMachine.setState(stateMachine.getStates().get(4)); // Declined, return to map
                                    break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    public void displayMenu(){
        for(int i = 0; i < numberOfButtons; i++){
            if ((recti[i].contains(Mouse.getPosition(window)) && isMouseOver()))
                textButton[i].setTextureRect(new IntRect(23, 100, 250, 60));
            else if (!isMouseOver())
                textButton[i].setTextureRect(new IntRect(23, 21, 250, 60));
            window.draw(textButton[i]);
            window.draw(text[i]);
        }
    }

    public boolean isMouseOver(){
        for(int i = 0; i < numberOfButtons; i++){
            if(recti[i].contains(Mouse.getPosition(window))){
                return true;
            }
        }
        return false;
    }
}
