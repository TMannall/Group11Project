import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Aidan on 25/01/2016.
 */
public class ExplorationEventGetter extends Events{

    public ExplorationEventGetter()
    {
        super("ExplorationEvents.xml");
    }

    public void loadEvent()
    {
        System.out.println("Exploration Load Event");
    }

    public void runEvent()
    {
        System.out.println("***************************************");
        System.out.println("Exploration run Event");
        System.out.println("***************************************");
        loadEvent();
        System.out.println("***************************************");
    }

    public void debugEvent()
    {
        System.out.println("Exploration Debug Event");
        System.out.println("*******************************************************");
        Node rootNode = doc.getElementsByTagName("ExplorationEvents").item(0);
        System.out.println(rootNode.getTextContent());
        System.out.println("*******************************************************");
    }

}
