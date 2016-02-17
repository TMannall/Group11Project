import events.EventExampleDriver;
import org.jsfml.graphics.*;
import org.jsfml.window.event.Event;

import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;
import statemachine.FSM;
import statemachine.FSMState;

import java.util.Random;

/**
 * Map state class for Endless Sea
 */
public class Map extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private Random randGenerator;
    private EventExampleDriver eventDriver;
	
	private static int noOfSprites = 8;
	private static int maxSprites = 8;
	//private static int spriteIndex = 0;
	
	Sprite[] island = new Sprite[maxSprites];

	float[] leftBound = new float[maxSprites];
	float[] rightBound = new float[maxSprites];
	float[] topBound = new float[maxSprites];
	float[] bottomBound = new float[maxSprites];

    public Map(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, EventExampleDriver eventDriver){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        randGenerator = new Random();
        this.eventDriver = eventDriver;
        setup();
    }

    public void setup(){

		island[0] = textures.createSprite(textures.mapDecoration, 0, 0, 179, 114);	//island 1
		island[1] = textures.createSprite(textures.mapDecoration, 182, 0, 168, 131); //island 2
		island[2] = textures.createSprite(textures.mapDecoration, 369, 5, 166, 125); //island 3
		island[3] = textures.createSprite(textures.mapDecoration, 540, 5, 167, 193); //island 4
		island[4] = textures.createSprite(textures.mapDecoration, 4, 144, 164, 194); //island 5
		island[5] = textures.createSprite(textures.mapDecoration, 10, 360, 127, 67); //island 6
		island[6] = textures.createSprite(textures.mapDecoration, 431, 227, 200, 192); //island 7
		island[7] = textures.createSprite(textures.mapDecoration, 200, 136, 219, 276);	//island port
		
		island[0].setPosition(120, 130);
		island[1].setPosition(420, 130);
		island[2].setPosition(720, 130);
		island[3].setPosition(1020, 130);
		island[4].setPosition(120, 430);
		island[5].setPosition(420, 430);
		island[6].setPosition(720, 430);
		island[7].setPosition(1020, 430);
    }
	
    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
		
		for(int i = 0; i < maxSprites; i++){
			window.draw(island[i]);
		}

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
					int xPos = event.asMouseEvent().position.x;
					int yPos = event.asMouseEvent().position.y;
										
					for(int i = 0; i < maxSprites; i++){
						leftBound[i] = island[i].getGlobalBounds().left;
						rightBound[i] = leftBound[i] + island[i].getGlobalBounds().width;
						topBound[i] = island[i].getGlobalBounds().top;
						bottomBound[i] = topBound[i] + island[i].getGlobalBounds().height;
					}
					
		// Add events/actions here when islands are clicked on
					for(int i = 0; i < maxSprites; i++){
						if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {
							//System.out.println("Island Clicked!");
							switch(i){
								case 0:	//Island 1
									System.out.println("Island 1 Clicked");
                                    eventDriver.resetProbabilities(0,100,0,0,0);
                                    eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
								case 1: //Island 2
								    System.out.println("Island 2 Clicked");
									eventDriver.resetProbabilities(20,20,20,20,20);
									eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
								case 2: //Island 3
									System.out.println("Island 3 Clicked");
                                    eventDriver.resetProbabilities(20,20,20,20,20);
                                    eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
								case 3: //Island 4
									System.out.println("Island 4 Clicked");
                                    eventDriver.resetProbabilities(20,20,20,20,20);
                                    eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
								case 4: //Island 5 //Trade
									System.out.println("Island 5 Clicked");
                                    eventDriver.resetProbabilities(0,0,0,0,100);
                                    eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
								case 5: //Island 6 //Text
									System.out.println("Island 6 Clicked");
                                    eventDriver.resetProbabilities(0,0,0,100,0);
                                    eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
								case 6: //Island 7 //Exploration
									System.out.println("Island 7 Clicked");
                                    eventDriver.resetProbabilities(0,0,100,0,0);
                                    eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
								case 7: //Island Port //Assist
									System.out.println("Island Port Clicked");
                                    eventDriver.resetProbabilities(100,0,0,0,0);
                                    eventDriver.runEvent();
									stateMachine.setState(stateMachine.getStates().get(eventDriver.getEventType()));
									break;
							}
						}
					}

                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.ESCAPE) {
                        stateMachine.setState(stateMachine.getStates().get(2));
                        break;
                    }
            }
        }
		window.display();
    }

	/*public int getSpriteIndex(int x, int y){
		for(int i = 0; i < maxSprites; i++){
			if (x > leftBound[i] && x < rightBound[i] && y > topBound[i] && y < bottomBound[i]) {
				System.out.println("Island Clicked!");
			}
		}
	}*/
	
	/*public int getSpriteIndex(){
		return spriteIndex;
	}*/
}
