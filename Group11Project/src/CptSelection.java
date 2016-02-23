import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * CptSelection state for Endless Sea, used to allow the player to select a captain before the game begins
 */
public class CptSelection extends FSMState{
	
	private String[] cptNamesAndStats = {"Nemo\n\n+ Gun Strength\n- Defence", "Bluetooth\n\n+ Max Resources\n- Gun Reload", "Keira Swann\n\n+ Defence\n- Food", "Barbossa\n\n+ 50 Gold\n- Hull HP"};

	private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
	private SoundFX sound;

    private static int numberOfButtons = 5;
    private static int buttonIndex = 0;
    Text[] text = new Text[numberOfButtons];
    Text title;
		IntRect[] recti = new IntRect[numberOfButtons];
		FloatRect[] rectf = new FloatRect[numberOfButtons];
		FloatRect[] rect = new FloatRect[numberOfButtons];

	float leftBound;
	float rightBound;
	float topBound;
	float bottomBound;

    private static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    private static String JdkFontPath = "textures/";
    private static String JreFontPath = "textures/";

    private static int titleFontSize = 80;
    private static int buttonFontSize = 32;
    private static String FontFile = "vinque.ttf";
    private String FontPath;

    private static String Title = "Choose your Captain";

    public CptSelection(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, SoundFX sound) {
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

				for (int i = 0; i < numberOfButtons-1; i++) { //cpts
					text[i].setFont(sansRegular);
					text[i].setColor(Color.CYAN);
					text[i].setString(cptNamesAndStats[i]);
					text[i].setPosition(220+(i*300), (driver.getWinHeight()/2)+140);
					text[i].setOrigin(text[i].getLocalBounds().width / 2, text[i].getLocalBounds().height / 2);
        }

				//back to menu button
        text[numberOfButtons-1].setFont(sansRegular);
        text[numberOfButtons-1].setColor(Color.CYAN);
        text[numberOfButtons-1].setString("Main Menu");
        text[numberOfButtons-1].setPosition(driver.getWinWidth() / 2, 660);
				text[numberOfButtons-1].setOrigin(text[numberOfButtons-1].getLocalBounds().width / 2, text[numberOfButtons-1].getLocalBounds().height / 2);
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

        showCptSelection();
    }

    public void showCptSelection() {
        textures.mainMenu.setOrigin(0, 0);
        //textures.mainMenu.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.mainMenu);
        window.draw(title);
		
				Sprite[] textButton = new Sprite[numberOfButtons];
				Sprite[] hoverButton = new Sprite[numberOfButtons];
				Sprite[] pushButton = new Sprite[numberOfButtons];
				Sprite[] background = new Sprite[numberOfButtons-1];
				Sprite[] portrait = new Sprite[numberOfButtons-1];
			
				for(int i = numberOfButtons-1; i < numberOfButtons; i++){
					textButton[i] = textures.createSprite(textures.userInterface, 23, 21, 250, 60);
					hoverButton[i] = textures.createSprite(textures.userInterface, 23, 100, 250, 60);
					pushButton[i] = textures.createSprite(textures.userInterface, 23, 179, 250, 60);
				}

				for(int i = numberOfButtons-1; i < numberOfButtons; i++){

					textButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
					hoverButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
					pushButton[i].setPosition(text[i].getPosition().x, text[i].getPosition().y + 8);
					window.draw(textButton[i]);
				}	

				leftBound = textButton[4].getGlobalBounds().left;
				rightBound = leftBound + textButton[4].getGlobalBounds().width;
				topBound = textButton[4].getGlobalBounds().top;
				bottomBound = topBound + textButton[4].getGlobalBounds().height;

				portrait[0] = textures.createSprite(textures.cptPortraitA, 0, 0, 163, 200);
				portrait[1] = textures.createSprite(textures.cptPortraitB, 0, 0, 163, 200);
				portrait[2] = textures.createSprite(textures.cptPortraitC, 0, 0, 163, 200);
				portrait[3] = textures.createSprite(textures.cptPortraitD, 0, 0, 163, 200);
				
				for (int i = 0; i < numberOfButtons-1; i++) {
					background[i] = textures.createSprite(textures.backgroundCptSel, 0, 0, 250, 450);
					background[i].setPosition(text[i].getPosition().x, text[i].getPosition().y-100);
					window.draw(background[i]);
					portrait[i].setPosition(text[i].getPosition().x, text[i].getPosition().y-190);		
				}

				for(int i = 0; i < numberOfButtons-1; i++){
					rectf[i] = new FloatRect(background[i].getGlobalBounds().left, background[i].getGlobalBounds().top, 
											background[i].getGlobalBounds().width, background[i].getGlobalBounds().height);
					recti[i] = new IntRect(rectf[i]);
				}
				
				for(int i = 0; i < numberOfButtons-1; i++){
					if((recti[i].contains(Mouse.getPosition(window)) && isMouseOver())){
						background[i].setTextureRect(new IntRect(23, 100, 250, 450));
						window.draw(background[i]);
					}
					else if(!isMouseOver()){
						background[i].setTextureRect(new IntRect(23, 21, 250, 0));
						window.draw(background[i]);	
					}
				}	
				
        for (int i = 0; i < numberOfButtons; i++) {
            window.draw(text[i]);
						if(i<numberOfButtons-1)
						{
							window.draw(portrait[i]);
						}
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
						for(int i = numberOfButtons-1; i < numberOfButtons; i++){
							rect[i] = new FloatRect(textButton[i].getGlobalBounds().left, textButton[i].getGlobalBounds().top, 
										textButton[i].getGlobalBounds().width, textButton[i].getGlobalBounds().height);
						}
						if(recti[0].contains(mouseClicked.position.x, mouseClicked.position.y)){
							System.out.println("Cpt #1 Selected");
							driver.getPlayerShip().addGunStr((float)0.5);
							driver.getPlayerShip().addBridgeDefence(-(float)0.5);
							System.out.println("GUN STR: " + driver.getPlayerShip().getGunStr());
							System.out.println("BRIDGE DEF: " + driver.getPlayerShip().getBridgeDefence());
							//stateMachine.setState(stateMachine.getStates().get(4));
							startGame();
						}
						else if(recti[1].contains(mouseClicked.position.x, mouseClicked.position.y)){
							System.out.println("Cpt #2 Selected");
							driver.getPlayerShip().addMaxFood(10);
							driver.getPlayerShip().addMaxWater(10);
							driver.getPlayerShip().addReloadBoost(-(float)0.1);
							System.out.println("MAX FOOD: " + driver.getPlayerShip().getMaxFood());
							System.out.println("MAX WATER: " + driver.getPlayerShip().getMaxWater());
							System.out.println("RELOAD BOOST: " +driver.getPlayerShip().getReloadBoost() );
							//stateMachine.setState(stateMachine.getStates().get(4));
							startGame();
						}
						else if(recti[2].contains(mouseClicked.position.x, mouseClicked.position.y)){
							System.out.println("Cpt #3 Selected");
							driver.getPlayerShip().addBridgeDefence((float)0.5);
							driver.getPlayerShip().addFood(-15);
							System.out.println("BRIDGE DEF: " + driver.getPlayerShip().getBridgeDefence());
							System.out.println("STARTING FOOD:" + driver.getPlayerShip().getCurrFood());
							//stateMachine.setState(stateMachine.getStates().get(4));
							startGame();
						}
						else if(recti[3].contains(mouseClicked.position.x, mouseClicked.position.y)){
							System.out.println("Cpt #4 Selected");
							driver.getPlayerShip().addGold(50);
							driver.getPlayerShip().damageHull(25);
							System.out.println("GOLD: " + driver.getPlayerShip().getCurrGold());
							System.out.println("HULL HP:" + driver.getPlayerShip().getHullHP());
							//stateMachine.setState(stateMachine.getStates().get(4));
							startGame();
						}
						//Main Menu
						else if(mouseClicked.position.x > leftBound && mouseClicked.position.x < rightBound && mouseClicked.position.y > topBound && mouseClicked.position.y < bottomBound){
							window.draw(pushButton[4]);
							window.draw(text[4]);
							sound.playBackgroundMusic("music_main_menu");
							startGame();
						}
					}
					break;
				}
			}
			window.display();
    }
	public boolean isMouseOver(){
		for(int i = 0; i < numberOfButtons-1; i++){
			if(recti[i].contains(Mouse.getPosition(window))){
				return true;
			}
		}
		return false; 
	}

	public void startGame(){
		driver.eventGenerator.setProbabilities(0, 0, 1, 0, 0, 0);
		driver.eventGenerator.genRandomEvent();
		driver.eventGenerator.genEventState();
	}
}
