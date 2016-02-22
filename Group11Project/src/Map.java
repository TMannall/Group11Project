import org.jsfml.graphics.*;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseEvent;
import org.jsfml.window.event.MouseButtonEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

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
	private EventGenerator eventGenerator;

	private static int noOfSprites = 8;
	private static int maxSprites = 8;
	private static int maxWaypoints = 13;

	Sprite[] island = new Sprite[maxSprites];
	Sprite[] waypoints = new Sprite[maxWaypoints];
	//							0		1			2		3			4		5			6			7		8			9			10		11		12
	int[][] wayPointsXY = {{130,300},{300,200},{490,300},{700,340},{650,150},{950,160},{1250,160},{1000,340},{1150,320},{270,550},{400,400},{580,480},{800,550}};
	int[][] wayPointConnections =
			{   {1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},{0,2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},{1,3,4,10,-1,-1,-1,-1,-1,-1,-1,-1,-1},
					{2,7,11,12,-1,-1,-1,-1,-1,-1,-1,-1,-1},{2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},{7,8,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
					{8,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},{3,5,8,12,-1,-1,-1,-1,-1,-1,-1,-1,-1},{5,6,7,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
					{10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},{2,11,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},{3,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
					{3,7,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};
	int currentWayPoint = 0;
	int tavelledCount = 0;
	Sprite ship;


	float[] leftBound = new float[maxWaypoints];
	float[] rightBound = new float[maxWaypoints];
	float[] topBound = new float[maxWaypoints];
	float[] bottomBound = new float[maxWaypoints];


	public Map(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, EventGenerator eventGenerator){
		this.stateMachine = stateMachine;
		this.driver = driver;
		this.window = window;
		this.textures = textures;
		randGenerator = new Random();
		this.eventGenerator = eventGenerator;
		setup();
	}

	public void setup(){
		for(int point = 0; point < maxWaypoints; point++) {
			waypoints[point] = textures.createSprite(textures.userInterface, 466, 24, 56, 56);
			waypoints[point].setPosition(wayPointsXY[point][0],wayPointsXY[point][1]);
		}
		ship = textures.createSprite(textures.userInterface, 549, 11, 254, 92);
		ship.setPosition(waypoints[currentWayPoint].getPosition().x,waypoints[currentWayPoint].getPosition().y);
		ship.setScale((float)0.75, (float)0.75);

		island[0] = textures.createSprite(textures.mapDecoration, 0, 0, 179, 114);	//island 1
		island[1] = textures.createSprite(textures.mapDecoration, 182, 0, 168, 131); //island 2
		island[2] = textures.createSprite(textures.mapDecoration, 369, 5, 166, 125); //island 3
		island[3] = textures.createSprite(textures.mapDecoration, 540, 5, 167, 193); //island 4
		island[4] = textures.createSprite(textures.mapDecoration, 10, 360, 127, 67); //island 5
		island[5] = textures.createSprite(textures.mapDecoration, 4, 144, 164, 194); //island 6
		island[6] = textures.createSprite(textures.mapDecoration, 200, 136, 219, 276); //island 7
		island[7] = textures.createSprite(textures.mapDecoration, 431, 227, 200, 192);	//island port
		island[0].setPosition(170, 80);
		island[1].setPosition(460, 170);
		island[2].setPosition(780, 210);
		island[3].setPosition(1120, 130);
		island[4].setPosition(120, 590);
		island[5].setPosition(325, 385);
		island[6].setPosition(600, 590);
		island[7].setPosition(1000, 530);
	}

	@Override
	// Update this method with what should be added to the window (using window variable)
	public void execute() {
		textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
		textures.mapConnections.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
		window.draw(textures.ocean);
		window.draw(textures.mapConnections);
		ship.setPosition(waypoints[currentWayPoint].getPosition().x,waypoints[currentWayPoint].getPosition().y);
		window.draw(ship);
		for(int i = 0; i < maxSprites; i++){
			window.draw(island[i]);
		}

		for(int i = 0; i < maxWaypoints; i++){
			window.draw(waypoints[i]);
		}
		window.draw(ship);
		window.display();

		for(Event event : window.pollEvents()){
			switch (event.type) {
				case CLOSED:
					window.close();
					break;
				case MOUSE_BUTTON_PRESSED:
					int xPos = event.asMouseEvent().position.x;
					int yPos = event.asMouseEvent().position.y;

					for(int i = 0; i < maxWaypoints; i++){
						leftBound[i] = waypoints[i].getGlobalBounds().left;
						rightBound[i] = leftBound[i] + waypoints[i].getGlobalBounds().width;
						topBound[i] = waypoints[i].getGlobalBounds().top;
						bottomBound[i] = topBound[i] + waypoints[i].getGlobalBounds().height;
					}

					// Add events/actions here when islands are clicked on
					for(int i = 0; i < maxWaypoints; i++){
						if (xPos > leftBound[i] && xPos < rightBound[i] && yPos > topBound[i] && yPos < bottomBound[i]) {
							switch(i){
								case 0:	//Island 1
									if (canTravelTo(i)){
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("1 Clicked");
										eventGenerator.setProbabilities(1, 0, 0, 0, 0, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 1: //Island 2
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("2 Clicked");
										eventGenerator.setProbabilities(0, 1, 0, 0, 0, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 2: //Island 3
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("3 Clicked");
										eventGenerator.setProbabilities(0, 0, 1, 0, 0, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 3: //Island 4
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("4 Clicked");
										eventGenerator.setProbabilities(0, 0, 0, 1, 0, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 4: //Island 5 //Trade
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("5 Clicked");
										eventGenerator.setProbabilities(0, 0, 0, 0, 1,0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 5: //Island 6 //Text
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("6 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float)0.2, (float)0.2, (float)0.2, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 6: //Island 7 //Exploration
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("7 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 7: //Island Port //Assist
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("8 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 8: //Island 3
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("9 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
										break;
								case 9: //Island 3
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("10 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float)0.2, (float)0.2, (float)0.2, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 10: //Island 3
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("11 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 11: //Island 3
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("12 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2 ,0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
									break;
								case 12: //Island 13
									if (canTravelTo(i)) {
										currentWayPoint = i;
										tavelledCount++;
										System.out.println("13 Clicked");
										eventGenerator.setProbabilities((float) 0.2, (float) 0.2, (float)0.2, (float)0.2, (float)0.2, 0);
										eventGenerator.genRandomEvent();
										eventGenerator.genEventState();
									}
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
	}

	public boolean canTravelTo(int pointClicked){
		for(int i = 0; i < wayPointConnections.length; i++)
			if(pointClicked == wayPointConnections[currentWayPoint][i])
				return true;
		return false;
	}
}