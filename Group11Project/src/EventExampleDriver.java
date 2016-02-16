/**
 * Created by Aidan on 25/01/2016.
 */
public class EventExampleDriver
{
    private String[] eventTypes = {"Assist Events", "Combat Events", "Exploration Events", "Text Events", "Trade Events"};
    private Events[] events;
    private int[] probabilities;
    private String eventSelected;
    private Events currentEvent;

    public EventExampleDriver()
    {
        events = new Events[5];
        DbUser myDbUser;
        myDbUser = new DbUser("Events");
        events[0] = new AssistEventGetter(myDbUser);
        events[1] = new CombatEventGetter(myDbUser);
        events[2] = new ExplorationEventGetter(myDbUser);
        events[3] = new TextEventGetter(myDbUser);
        events[4] = new TradeEventGetter(myDbUser);
    }

    public void getRandomEvent()
    {
        RandomGetter getEvent = new RandomGetter(probabilities, eventTypes);
        eventSelected = getEvent.getRandomType();
    }

    public void runEvent()
    {
        System.out.println();
        this.getRandomEvent();
        int indexOfEvent = 0;
        for (String eventType : eventTypes)
        {
            if (eventType.equals(eventSelected)) {
                currentEvent = events[indexOfEvent];
                currentEvent.runEvent();
            }
            indexOfEvent++;
        }
        System.out.println();
    }

    public void resetProbabilities(int AsProb, int cOProb, int eXProb, int tEProb, int tRProb)
    {
        probabilities = new int[]{AsProb, cOProb, eXProb, tEProb, tRProb};
    }

    public int[] getEventEffects()
    {
        int indexOfEvent = 0;
        for (String eventType : eventTypes)
        {
            if (eventType.equals(eventSelected))
                return events[indexOfEvent].getEventEffects();
            indexOfEvent++;
        }
        return null;
    }

    public String getEventText()
    {
        return currentEvent.getEventText();
    }
}
