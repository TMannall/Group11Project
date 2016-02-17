package endlesssea.events;

/**
 * Created by Aidan on 25/01/2016.
 */
public class TradeEventGetter extends Events
{
    private int[] itemLevelCount = {0,0,0};
    private String[] itemLvlNames = {"low", "medium", "high"};

    private int[] item_ID = {0,0,0,0,0,0};
    private String[] item_level = {"","","","","",""};
    private String[] itemNames = {"","","","","",""};
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

    public TradeEventGetter(DbUser myDbUser)
    {
        super(myDbUser);
        eventType = "trade_events";
        this.setMaximumEvents();
    }

    public void loadEvent()
    {
        //get the event text
        event_ID = random.nextInt(totalEventCount) + 1;
        query = "SELECT * FROM trade_events WHERE event_ID =" + event_ID + ";";
        titles = new String[]{"event_ID", "event_text", "low_item_count", "medium_item_count", "high_item_count"};
        results = myDbUser.getQuery(query, titles);
        eventText = results.get(1);
        itemLevelCount[0] = Integer.parseInt(results.get(2));
        itemLevelCount[1] = Integer.parseInt(results.get(3));
        itemLevelCount[2] = Integer.parseInt(results.get(4));
        System.out.println(eventText);
        System.out.println("low: " + itemLevelCount[0] + "  " + "medium: " + itemLevelCount[1] + "  " + "high: " + itemLevelCount[2] + "  ");
        //preload consequence...
        int count = 0;
        for (int itemLevels = 0; itemLevels < 3; itemLevels++)
        {
            for(int i = 0; i < itemLevelCount[itemLevels]; i++)
            {
                String currentItemLvl = itemLvlNames[itemLevels];
                int randomItemIndex = random.nextInt(10) + 1 + (itemLevels * 100);
                query = "SELECT * FROM items WHERE item_ID =" + randomItemIndex + ";";
                titles = new String[]{"item_ID", "item_level", "item_name", "gold", "food", "water", "hull_HP", "cannonStrength", "guns", "masts", "bridge", "hold", "quarters"};
                results = myDbUser.getQuery(query, titles);
                item_ID[count] = Integer.parseInt(results.get(0));
                item_level[count] = results.get(1);
                itemNames[count] = results.get(2);
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
                count++;
            }
        }
    }

    public void runEvent()
    {
        loadEvent();
        //Display event
        for(int count = 0; count < 6; count++)
            System.out.println(item_ID[count] + "  " + item_level[count] + "  " + itemNames[count] + "  " + gold[count]
                    + "  " + food[count] + "  " + water[count] + "  " + hull_HP[count] + "  " + cannonStrength[count] +
                    "  " +  guns[count] + "  " +  masts[count] + "  " +  bridge[count] + "  " +  hold[count] + "  " +
                    quarters[count]);
    }
}
