import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Aidan on 25/01/2016.
 */
public class TextEventGetter extends Events{

    public TextEventGetter()
    {
        super("TextEvents.xml");
    }

    public void loadEvent()
    {
        System.out.println("Text Load Event");
    }

    public void runEvent()
    {
        System.out.println("***************************************");
        System.out.println("Text run Event");
        System.out.println("***************************************");
        loadEvent();
        System.out.println("***************************************");
    }


    public void debugEvent()
    {
        System.out.println("Text Debug Event");
        System.out.println("*******************************************************");
        Node rootNode = doc.getElementsByTagName("TextEvents").item(0);
        System.out.println(rootNode.getTextContent());
        System.out.println("*******************************************************");
    }

}
