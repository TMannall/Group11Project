import java.util.ArrayList;
import java.util.Random;

/**
 * @Author Aidan Lennie on 25/01/2016.
 */
public abstract class Events
{
    protected int level = 0;
    protected String eventType;
    protected Random random;
    protected String query;
    protected String[] titles;
    protected DbUser myDbUser;
    protected ArrayList<String> results;

    protected int totalEventCount = 0;
    protected String eventText;
    protected int event_ID = 0;
    protected int consequence_ID = 0;
    protected String consequence_text;
    String[] playerStatsList = {"gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
    protected int[] playerStatsChange = {0,0,0,0,0,0,0,0,0,0};
    String[] itemNames = new String[3];
    int[][] itemStats = new int[3][10];

    public Events(DbUser myDbUser)
    {
        random = new Random();
        this.myDbUser = myDbUser;
    }

    public void setMaximumEvents()
    {
        //Get the count of events
        query = "SELECT COUNT(event_ID) AS count_of_events FROM " + eventType + ";";
        titles = new String[]{"count_of_events"};
        results = myDbUser.getQuery(query, titles);
        totalEventCount = Integer.parseInt(results.get(0));
    }

    public int[] getEventEffects()
    {
        return playerStatsChange;
    }

    public abstract void loadEvent();
    public abstract void runEvent();

    public String getEventText()
    {
        return eventText;
    }

    public String getConsequence(){
        return consequence_text;
    }

    public String[] getItemNames(){
        return itemNames;
    }

    public int[][] getItemStats(){
        return itemStats;
    }
}
