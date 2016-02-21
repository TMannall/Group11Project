import org.jsfml.audio.Sound;
import org.jsfml.graphics.RenderWindow;

import java.util.ArrayList;
import java.util.Random;
public class EventGenerator {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private SoundFX sound;

    private DbUser dbUser = new DbUser("Events");
    private String[] eventTypes = {"ASSIST", "COMBAT", "EXPLORE", "TEXT", "TRADE"};
    private float[] probabilities = {(float)0.5, (float)0.15, (float)0.15, (float)0.1, (float)0.1};          // Should add up to 1, note index[0] = ASSIST, index[1] = COMBAT etc
    private String currEvent = null;
    private Random randGenerator = new Random();

    protected String eventType;
    protected String query;
    protected String[] titles;
    protected ArrayList<String> results;

    protected int totalEventCount = 0;
    protected String eventText;
    protected int event_ID = 0;
    protected int consequence_ID = 0;
    protected String consequence_text;
    String[] playerStatsList = {"gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters", "reloadBoost", "mastSpeed", "bridgeDefence", "quartersRegainStr", "maxWater", "maxFood"};
    protected int[] playerStatsChange = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    String[] itemNames = new String[3];
    int[][] itemStats = new int[3][16];

    // For combat events
    private int enemyID;

    // For trade events
    private int[] itemLevelCount = {0,0,0};
    private String[] itemLvlNames = {"low", "medium", "high"};

    private int[] item_ID = {0,0,0,0,0,0};
    private String[] item_level = {"","","","","",""};
    //    private String[] itemNames = {"","","","","",""};
    private int[] gold = {0,0,0,0,0,0};
    private int[] food = {0,0,0,0,0,0};
    private int[] water = {0,0,0,0,0,0};
    private int[] hull_HP = {0,0,0,0,0,0};
    private int[] cannonStrength = {0,0,0,0,0,0};
    private int[] guns = {0,0,0,0,0,0};
    private int[] masts = {0,0,0,0,0,0};
    private int[] bridge = {0,0,0,0,0,0};
    private int[] hold = {0,0,0,0,0,0};
    private int[] quarters = {0,0,0,0,0,0};


    public EventGenerator(FSM stateMachine, GameDriver driver, RenderWindow window, Random randGenerator, SoundFX sound, Textures textures){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.randGenerator = randGenerator;
        this.sound = sound;
        this.textures = textures;
    }

    public void genRandomEvent(){
        int randIndex = -1;
        double random = randGenerator.nextDouble() * 1;
        for (int i = 0; i < eventTypes.length; i++){
            random -= probabilities[i];
            if(random <= 0.0d){
                randIndex = i;
                break;
            }
        }
        currEvent = eventTypes[randIndex];
        loadEvent();
    }

    public void setProbabilities(float assist, float combat, float explore, float text, float trade){
        probabilities = new float[]{assist, combat, explore, text, trade};
    }


    public void loadEvent(){
        switch(currEvent){
            case "ASSIST":
                eventType = "assist_events";
                setMaximumEvents();
                loadAssistEvent();
                break;
            case "COMBAT":
                eventType = "combat_events";
                setMaximumEvents();
                loadCombatEvent();
                break;
            case "EXPLORE":
                eventType = "exploration_events";
                setMaximumEvents();
                loadExploreEvent();
                break;
            case "TEXT":
                eventType = "text_events";
                setMaximumEvents();
                loadTextEvent();
                break;
            case "TRADE":
                eventType = "trade_events";
                setMaximumEvents();
                loadTradeEvent();
                break;
        }
    }

    public void loadAssistEvent(){
        //get the event text
        event_ID = randGenerator.nextInt(totalEventCount) + 1;
        query = "SELECT event_text FROM assist_events WHERE event_ID =" + Integer.toString(event_ID) + ";";
        titles = new String[]{"event_text"};
        eventText = (String)dbUser.getQuery(query,titles).get(0);
        System.out.println(eventText);

        //preload consequence...
        consequence_ID = randGenerator.nextInt(5) + 1;
        query = "SELECT * FROM assist_consequences WHERE event_ID =" + event_ID + " AND consequence_ID = " + consequence_ID + ";";
        titles = new String[]{"event_ID", "consequence_ID", "consequence_text", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
        results = dbUser.getQuery(query, titles);
        consequence_text = results.get(2);
        for(int i = 0; i < playerStatsList.length - 6; i++)
            playerStatsChange[i] = Integer.parseInt(results.get(i+3));

        //display random event
        System.out.println(event_ID + "  " + consequence_ID + "  " + consequence_text + "  " + playerStatsChange[0] +
                "  " + playerStatsChange[1] + "  " + playerStatsChange[2] + "  " + playerStatsChange[3] + "  " +
                playerStatsChange[4] + "  " +  playerStatsChange[5] + "  " +  playerStatsChange[6] + "  " +
                playerStatsChange[7] + "  " +  playerStatsChange[8] + "  " +  playerStatsChange[9]);

    }

    public void loadCombatEvent(){
        //get the event text
        event_ID = randGenerator.nextInt(totalEventCount) + 1;
        query = "SELECT eventText FROM combat_events WHERE event_ID =" + Integer.toString(event_ID) + ";";
        titles = new String[]{"eventText"};
        eventText = (String)dbUser.getQuery(query,titles).get(0);
        System.out.println(eventText);

        //preload enemy...
        enemyID = randGenerator.nextInt(5) + 1;
        query = "SELECT * FROM combat_enemies WHERE event_ID =" + event_ID + " AND enemyID =" + enemyID + ";";
        titles = new String[]{"event_ID", "enemyID", "enemyNation", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
        results = dbUser.getQuery(query, titles);
        consequence_text = results.get(2);
        for(int i = 0; i < playerStatsList.length - 6; i++)
            playerStatsChange[i] = Integer.parseInt(results.get(i+3));

        //display random event
        System.out.println(event_ID + "  " + consequence_ID + "  " + consequence_text + "  " + playerStatsChange[0] +
                "  " + playerStatsChange[1] + "  " + playerStatsChange[2] + "  " + playerStatsChange[3] + "  " +
                playerStatsChange[4] + "  " +  playerStatsChange[5] + "  " +  playerStatsChange[6] + "  " +
                playerStatsChange[7] + "  " +  playerStatsChange[8] + "  " +  playerStatsChange[9]);
    }

    public void loadExploreEvent(){
        //get the event text
        event_ID = randGenerator.nextInt(totalEventCount) + 1;
        query = "SELECT event_text FROM exploration_events WHERE event_ID =" + Integer.toString(event_ID) + ";";
        titles = new String[]{"event_text"};
        eventText = (String)dbUser.getQuery(query,titles).get(0);
        System.out.println(eventText);

        //preload consequence...
        consequence_ID = randGenerator.nextInt(5) + 1;
        query = "SELECT * FROM exploration_consequences WHERE event_ID =" + event_ID + " AND consequence_ID = " + consequence_ID + ";";
        titles = new String[]{"event_ID", "consequence_ID", "consequence_text", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
        results = dbUser.getQuery(query, titles);
        consequence_text = results.get(2);
        for(int i = 0; i < playerStatsList.length - 6; i++)
            playerStatsChange[i] = Integer.parseInt(results.get(i+3));

        //display event
        System.out.println(event_ID + "  " + consequence_ID + "  " + consequence_text + "  " + playerStatsChange[0] +
                "  " + playerStatsChange[1] + "  " + playerStatsChange[2] + "  " + playerStatsChange[3] + "  " +
                playerStatsChange[4] + "  " +  playerStatsChange[5] + "  " +  playerStatsChange[6] + "  " +
                playerStatsChange[7] + "  " +  playerStatsChange[8] + "  " +  playerStatsChange[9]);

    }

    public void loadTextEvent(){
        //get the event
        event_ID = randGenerator.nextInt(totalEventCount) + 1;
        query = "SELECT * FROM text_events WHERE event_ID =" + event_ID + ";";
        titles = new String[]{"event_ID", "event_text", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
        results = dbUser.getQuery(query, titles);
        eventText = results.get(1);
        for(int i = 0; i < playerStatsList.length - 6; i++)
            playerStatsChange[i] = Integer.parseInt(results.get(i+2));
        System.out.println(eventText);

        //display event
        System.out.println(event_ID + "  " + eventText + "  " + playerStatsChange[0] + "  "+ playerStatsChange[1]
                + "  " + playerStatsChange[2] + "  " + playerStatsChange[3] + "  " + playerStatsChange[4] + "  " +
                playerStatsChange[5] + "  " +  playerStatsChange[6] + "  " +  playerStatsChange[7] + "  " +
                playerStatsChange[8] + "  " +  playerStatsChange[9]);
    }

    public void loadTradeEvent(){
        //get the event text
        event_ID = randGenerator.nextInt(totalEventCount) + 1;
        query = "SELECT * FROM trade_events WHERE event_ID =" + event_ID + ";";
        titles = new String[]{"event_ID", "event_text", "low_item_count", "medium_item_count", "high_item_count"};
        results = dbUser.getQuery(query, titles);
        eventText = results.get(1);
        itemLevelCount[0] = 1;
        itemLevelCount[1] = 1;
        itemLevelCount[2] = 1;
        System.out.println(eventText);
        System.out.println("low: " + itemLevelCount[0] + "  " + "medium: " + itemLevelCount[1] + "  " + "high: " + itemLevelCount[2] + "  ");
        //preload consequence...
        int count = 0;
        for (int itemLevels = 0; itemLevels < 3; itemLevels++)
        {
            for(int i = 0; i < itemLevelCount[itemLevels]; i++)
            {
                String currentItemLvl = itemLvlNames[itemLevels];
                int randomItemIndex = randGenerator.nextInt(10) + 1 + (itemLevels * 100);
                query = "SELECT * FROM items WHERE item_ID =" + randomItemIndex + ";";
                titles = new String[]{"item_ID", "item_level", "item_name", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
                results = dbUser.getQuery(query, titles);
                item_ID[count] = Integer.parseInt(results.get(0));
                item_level[count] = results.get(1);

                gold[count] = Integer.parseInt(results.get(3));
                food[count] = Integer.parseInt(results.get(4));
                water[count] = Integer.parseInt(results.get(5));
                hull_HP[count] = Integer.parseInt(results.get(6));
                cannonStrength[count] = Integer.parseInt(results.get(7));
                guns[count] = Integer.parseInt(results.get(8));
                masts[count] = Integer.parseInt(results.get(9));
                bridge[count] = Integer.parseInt(results.get(10));
                hold[count] = Integer.parseInt(results.get(11));
                quarters[count] = Integer.parseInt(results.get(12));

                itemNames[count] = results.get(2);
                for(int stat = 3; stat < 13; stat++)
                {
                    itemStats[count][stat-3] = Integer.parseInt(results.get(stat));
                }
                count++;
            }
        }

        //display event
        for(int c = 0; c < 3; c++)
            for(int i = 0; i < 10; i++)
            {
                System.out.println(itemStats[c][i]);
            }
    }

    public void setMaximumEvents(){
        //Get the count of events
        query = "SELECT COUNT(event_ID) AS count_of_events FROM " + eventType + ";";
        titles = new String[]{"count_of_events"};
        results = dbUser.getQuery(query, titles);
        totalEventCount = Integer.parseInt(results.get(0));
    }

    public void genEventState(){
        switch(currEvent){
            case "ASSIST":
                driver.setEncounter(new AssistEvent(stateMachine, driver, window, textures, randGenerator, this, sound));
                break;
            case "COMBAT":
                driver.setEncounter(new CombatEvent(stateMachine, driver, window, textures, randGenerator, this, sound, driver.getPlayerShip()));
                break;
            case "EXPLORE":
                driver.setEncounter(new ExploreEvent(stateMachine, driver, window, textures, randGenerator, this, sound));
                break;
            case "TEXT":
                driver.setEncounter(new TextEvent(stateMachine, driver, window, textures, randGenerator, this, sound, driver.getPlayerShip()));
                break;
            case "TRADE":
                driver.setEncounter(new TradeEvent(stateMachine, driver, window, textures, randGenerator, this, sound, driver.getPlayerShip()));
                break;
        }
        stateMachine.setState(driver.getEncounter());
    }

    public int[] getEventEffects(){
        return playerStatsChange;
    }

    public String getEventText() {
        return eventText;
    }

    public int getEventType(){
        switch(currEvent){
            case "ASSIST":
                return 8;
            case "COMBAT":
                return 6;
            case "EXPLORE":
                return 8;
            case "TEXT":
                return 9;
            case "TRADE":
            default:
                return 0;
        }
    }

    public String getConsequence()
    {
        return consequence_text;
    }

    public String[] getItemNames(){
        return itemNames;
    }

    public int[][] getItemStats() {
        return itemStats;
    }
}
