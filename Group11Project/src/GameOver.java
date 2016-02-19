import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;

import java.text.DecimalFormat;

import org.jsfml.graphics.Text;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.Mouse;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseEvent;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.TextEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameOver extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
		private static int numberOfButtons = 3;
    private Text[] text = new Text[numberOfButtons];
		private boolean[] disabled = new boolean[numberOfButtons];
    private Text title;
		private Text message = new Text();
		private String messageText = "";
		private IntRect[] recti = new IntRect[numberOfButtons];
		private FloatRect[] rectf = new FloatRect[numberOfButtons];
		
    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static int titleFontSize = 80;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;

		private Leaderboard leaderboard;
    private static String Title = "Game Over";
		
		private boolean textEntry = true;
		private String playerName = "|";
		private Text name = new Text();

		private Sprite background;
		
    public GameOver(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Leaderboard leaderboard) {
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
				this.leaderboard = leaderboard;
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

        title = new Text(Title, fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 100);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.CYAN);
        title.setStyle(Text.BOLD);

        for (int i = 0; i < numberOfButtons; i++) {
            text[i] = new Text();
        }

        text[0].setFont(fontStyle);
        text[0].setColor(Color.CYAN);
        text[0].setString("Main Menu");
        text[0].setPosition(driver.getWinWidth() / 2, 520);
				text[0].setOrigin(text[0].getLocalBounds().width / 2, text[0].getLocalBounds().height / 2);

        text[1].setFont(fontStyle);
        text[1].setColor(Color.CYAN);
        text[1].setString("Exit");
        text[1].setPosition(driver.getWinWidth() / 2, 590);
				text[1].setOrigin(text[1].getLocalBounds().width / 2, text[1].getLocalBounds().height / 2);

        text[2].setFont(fontStyle);
        text[2].setColor(Color.CYAN);
        text[2].setString("Submit Score");
        text[2].setPosition(driver.getWinWidth() / 2, 360);
				text[2].setOrigin(text[2].getLocalBounds().width / 2, text[2].getLocalBounds().height / 2);
				
				message.setFont(fontStyle);
        message.setColor(Color.CYAN);
				messageText = "Your score is: " + driver.score + "\nEnter your name below:";
        message.setString(messageText);
        message.setPosition(driver.getWinWidth() / 2, 250);
				message.setOrigin(message.getLocalBounds().width / 2, message.getLocalBounds().height / 2);

				name.setFont(fontStyle);
        name.setColor(Color.CYAN);
        name.setString("|");
        name.setPosition(driver.getWinWidth() / 2, 300);
				name.setOrigin(name.getLocalBounds().width / 2, name.getLocalBounds().height / 2);

				background = textures.createSprite(textures.backgroundLeaderboard, 0, 0, 600, 120);
				background.setPosition(driver.getWinWidth() / 2, (driver.getWinHeight() / 2)-90);
				
				//
				//try to submit high score, the message will depend on it
				//
		}
		
    @Override
    public void execute() {
 /*       System.out.println("GAME OVER!");

        System.exit(0);

        window.display();

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
            }
        }
*/
        textures.gameover.setOrigin(0, 0);
        window.draw(textures.gameover);

        try {
            Thread.sleep(10); //1000); // this delays the animation frames if it's too high
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        displayGameOver();		
    }


    public void displayGameOver() {
        textures.gameover.setOrigin(0, 0);
        window.draw(textures.gameover);
				window.draw(title);
				window.draw(background);				
				if(!disabled[2])
				{
					messageText = "Your score is: " + driver.score + "\nEnter your name below:";
				}
        message.setString(messageText);
				message.setPosition(driver.getWinWidth() / 2, 250);
				message.setOrigin(message.getLocalBounds().width / 2, message.getLocalBounds().height / 2);				
				window.draw(message);
				name.setString(playerName);
				window.draw(name);
				
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
					if(!disabled[i])
					{	
						window.draw(textButton[i]);
            window.draw(text[i]);
					}	
        }
		
				for(int i = 0; i < numberOfButtons; i++){
					rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top, 
											textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
					recti[i] = new IntRect(rectf[i]);
				}
				
				for(int i = 0; i < numberOfButtons; i++){
					if((recti[i].contains(Mouse.getPosition(window)) && isMouseOver() && disabled[i] == false)){
						textButton[i].setTextureRect(new IntRect(23, 100, 250, 60));
						window.draw(textButton[i]);
						window.draw(text[i]);
					}
					else if(!isMouseOver() && disabled[i] == false){
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
								case TEXT_ENTERED:
									if(textEntry)
									{
										TextEvent textEvent = event.asTextEvent();
										if((textEvent.character > 47 && textEvent.character < 58) || (textEvent.character > 64 && textEvent.character < 91) || (textEvent.character > 96 && textEvent.character < 123))
										{ //only valid characters allowed
											if(playerName.length() == 1 && playerName.charAt(0) == '|')
											{
												playerName = "" + textEvent.character + "|";
												name.setPosition(name.getPosition().x-7, name.getPosition().y);
											}
											else if(playerName.length() == 10 && playerName.charAt(9) == '|')
											{
												char[] temp = playerName.toCharArray();
												temp[9] = textEvent.character;
												playerName = String.valueOf(temp);
												textEntry = false;
												name.setPosition(name.getPosition().x-7, name.getPosition().y);
											}
											else
											{
												int index = playerName.length();
												char[] temp = playerName.toCharArray();
												temp[index-1] = textEvent.character;
												playerName = String.valueOf(temp);
												playerName += "|";
												name.setPosition(name.getPosition().x-8, name.getPosition().y);
											}
										}
									}
									break;										
								case MOUSE_BUTTON_PRESSED:
									MouseEvent mouseClicked = event.asMouseButtonEvent();
									if(mouseClicked.type == Event.Type.MOUSE_BUTTON_PRESSED){
										for(int i = 0; i < numberOfButtons; i++){
											rectf[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top, 
														textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
										}
										//Main Menu
										if(rectf[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
											window.draw(pushButton[0]);
											window.draw(text[0]);
											playerName = "|";
											textEntry = true;
											disabled[2] = false;
											name.setPosition(driver.getWinWidth() / 2, 300);
											stateMachine.setState(stateMachine.getStates().get(0));
										}
										//Exit
										else if(rectf[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
											window.draw(pushButton[1]);
											window.draw(text[1]);
											window.close();
										}
										//Submit score
										else if(rectf[2].contains(mouseClicked.position.x, mouseClicked.position.y)){
											if(playerName.length() > 0 && playerName.charAt(0) != '|')
											{
												window.draw(pushButton[2]);
												window.draw(text[2]);
												if(playerName.charAt(playerName.length()-1) == '|')
												{
													char[] temp = playerName.toCharArray();
													temp[playerName.length()-1] = ' ';
													playerName = String.valueOf(temp);
												}
												if(leaderboard.submit(playerName, driver.score))
												{
													playerName = "";
													disabled[2] = true;
													messageText = "Your score is now in the leaderboard.";
													message.setString(messageText);
													message.setPosition(driver.getWinWidth() / 2, 250);
													message.setOrigin(message.getLocalBounds().width / 2, message.getLocalBounds().height / 2);
												}
												else
												{
													playerName = "";
													disabled[2] = true;
													messageText = "Your score was too low for the leaderboard.";
													message.setString(messageText);
													message.setPosition(driver.getWinWidth() / 2, 250);
													message.setOrigin(message.getLocalBounds().width / 2, message.getLocalBounds().height / 2);
												}
											}	
										}
									}
									break;					
						}
				}
			window.display();
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
