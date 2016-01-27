/**
 * Created by Aidan on 25/01/2016.
 */
public class EventExampleDriver
{
    private String[] eventTypes = {"AssistEvents.xml", "CombatEvents.xml", "ExplorationEvents.xml", "TextEvents.xml", "TradeEvents.xml"};
    private Events[] events;
    private int[] probabilities;
    private String eventSelected;

    public EventExampleDriver(int AsProb,int cOProb,int eXProb,int tEProb,int tRProb)
    {
        this.resetProbabilities(AsProb, cOProb, eXProb, tEProb, tRProb);
        events = new Events[5];
        events[0] = new AssistEventGetter();
        events[1] = new CombatEventGetter();
        events[2] = new ExplorationEventGetter();
        events[3] = new TextEventGetter();
        events[4] = new TradeEventGetter();
        this.getRandomEvent();
        this.debugEvent();
    }

    public void getRandomEvent()
    {
        RandomGetter getEvent = new RandomGetter(probabilities, eventTypes);
        eventSelected = getEvent.getRandomType();
    }

    public void debugEvent()
    {
        System.out.println("-----------------------");
        int indexOfEvent = 0;
        for (String eventType : eventTypes)
        {
            if (eventType.equals(eventSelected))
            {
                events[indexOfEvent].debugEvent();
                events[indexOfEvent].runEvent();
            }
            indexOfEvent++;
        }
    }

    public void resetProbabilities(int AsProb, int cOProb, int eXProb, int tEProb, int tRProb)
    {
        probabilities = new int[]{AsProb, cOProb, eXProb, tEProb, tRProb};
    }
}
