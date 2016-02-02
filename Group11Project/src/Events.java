import org.w3c.dom.Document;

import java.util.Random;

/**
 * Created by Aidan on 25/01/2016.
 */
public abstract class Events {

    protected Document doc;
    protected XMLReader xmlReader;
    protected String eventType;
    protected Random random;

    public Events(String eventType)
    {
        this.eventType = eventType;
        xmlReader = new XMLReader(eventType);
        doc = xmlReader.getDoc();
        random = new Random();
    }

    public abstract void loadEvent();

    public abstract void runEvent();

    public abstract void debugEvent();

}
