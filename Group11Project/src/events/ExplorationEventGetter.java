package events;

/**
 * Created by Aidan on 25/01/2016.
 */
public class ExplorationEventGetter extends Events
{
    private static Boolean accepted = false;

    public ExplorationEventGetter(DbUser myDbUser)
    {
        super(myDbUser);
        eventType = "exploration_events";
        this.setMaximumEvents();
    }

    public void loadEvent()
    {
        //get the event text
        event_ID = random.nextInt(totalEventCount) + 1;
        query = "SELECT event_text FROM exploration_events WHERE event_ID =" + Integer.toString(event_ID) + ";";
        titles = new String[]{"event_text"};
        eventText = (String)myDbUser.getQuery(query,titles).get(0);
        System.out.println(eventText);

        //preload consequence...
        consequence_ID = random.nextInt(5) + 1;
        query = "SELECT * FROM exploration_consequences WHERE event_ID =" + event_ID + " AND consequence_ID = " + consequence_ID + ";";
        titles = new String[]{"event_ID", "consequence_ID", "consequence_text", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
        results = myDbUser.getQuery(query, titles);
        consequence_text = results.get(2);
        for(int i = 0; i < playerStatsList.length; i++)
            playerStatsChange[i] = Integer.parseInt(results.get(i+3));
    }

    public void runEvent()
    {
        loadEvent();
        //display event
        System.out.println(event_ID + "  " + consequence_ID + "  " + consequence_text + "  " + playerStatsChange[0] +
                "  " + playerStatsChange[1] + "  " + playerStatsChange[2] + "  " + playerStatsChange[3] + "  " +
                playerStatsChange[4] + "  " +  playerStatsChange[5] + "  " +  playerStatsChange[6] + "  " +
                playerStatsChange[7] + "  " +  playerStatsChange[8] + "  " +  playerStatsChange[9]);
    }
}
