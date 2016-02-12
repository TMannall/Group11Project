/**
 * Created by Aidan on 25/01/2016.
 */
public class CombatEventGetter extends Events{

    public CombatEventGetter(DbUser myDbUser)
    {
        super(myDbUser);
    }

    public void loadEvent()
    {
        System.out.println("Combat Load Event");
    }

    public void runEvent()
    {
        System.out.println("***************************************");
        System.out.println("Combat run Event");
        System.out.println("***************************************");
        loadEvent();
        System.out.println("***************************************");
    }


    public void debugEvent()
    {
        System.out.println("Combat Debug Event");
        System.out.println("*******************************************************");

        System.out.println("*******************************************************");
    }

}
