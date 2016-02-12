/**
 * Created by Aidan on 25/01/2016.
 */
public class TextEventGetter extends Events
{
    public TextEventGetter(DbUser myDbUser)
    {
        super(myDbUser);
        eventType = "text_events";
        this.setMaximumEvents();
    }

    public void loadEvent()
    {
        //get the event
        event_ID = random.nextInt(totalEventCount) + 1;
        query = "SELECT * FROM text_events WHERE event_ID =" + event_ID + ";";
        titles = new String[]{"event_ID", "event_text", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
        results = myDbUser.getQuery(query, titles);
        eventText = results.get(1);
        for(int i = 0; i < playerStatsList.length; i++)
            playerStatsChange[i] = Integer.parseInt(results.get(i+2));
        System.out.println(eventText);
    }

    public void runEvent()
    {
        loadEvent();
        //Display event
        System.out.println(event_ID + "  " + eventText + "  " + playerStatsChange[0] + "  "+ playerStatsChange[1]
                + "  " + playerStatsChange[2] + "  " + playerStatsChange[3] + "  " + playerStatsChange[4] + "  " +
                playerStatsChange[5] + "  " +  playerStatsChange[6] + "  " +  playerStatsChange[7] + "  " +
                playerStatsChange[8] + "  " +  playerStatsChange[9]);
    }
}
