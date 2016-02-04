import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Aidan on 25/01/2016.
 */
public class CombatEventGetter extends Events{

    public CombatEventGetter()
    {
        super("CombatEvents.xml");
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
        Node rootNode = doc.getElementsByTagName("CombatEvents").item(0);
        System.out.println(rootNode.getTextContent());
        System.out.println("*******************************************************");
    }

}
