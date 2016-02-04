import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Aidan on 25/01/2016.
 */
public class TradeEventGetter extends Events{

    private int easyItemsCount = 0, mediumItemsCount = 0, hardItemsCount = 0;
    private int easyItemsMax = 6, mediumItemsMax = 3, hardItemsMax = 1;

    public TradeEventGetter()
    {
        super("TradeEvents.xml");
    }

    public void loadEvent()
    {
        System.out.println("/////////////////////////////////");
        System.out.println("Trade Load Event");
        System.out.println("/////////////////////////////////");

        //Get trade events info
        NodeList rootNodes = doc.getElementsByTagName("TradeEvents");
        Node rootNode = rootNodes.item(0);
        Element rootElement = (Element) rootNode;
        NodeList easyItems = rootElement.getElementsByTagName("easyItems");
        NodeList mediumItems = rootElement.getElementsByTagName("mediumItems");
        NodeList hardItems = rootElement.getElementsByTagName("hardItems");
        //Get counts of items
        easyItemsCount = easyItems.getLength();
        mediumItemsCount = mediumItems.getLength();
        hardItemsCount = hardItems.getLength();
        //Pick random items
            //easy
        Node[] randomItems = new Node[easyItemsMax + mediumItemsMax + hardItemsMax];
        for(int i = 0; i < easyItemsMax; i++)
        {
            randomItems[i] = easyItems.item(random.nextInt(easyItemsCount));
        }
            //medium
        for(int i = easyItemsMax; i < easyItemsMax + mediumItemsMax; i++)
        {
            randomItems[i] = mediumItems.item(random.nextInt(mediumItemsCount));
        }
            //hard
        for(int i = easyItemsMax + mediumItemsMax; i < easyItemsMax + mediumItemsMax + hardItemsMax; i++)
        {
            randomItems[i] = hardItems.item(random.nextInt(hardItemsCount));
        }
        for(int i = 0; i < easyItemsMax + mediumItemsMax + hardItemsMax; i++)
        {
            System.out.println(randomItems[i].getTextContent());
        }

        //display

        //let user buy items





        //Get List of Events
//        NodeList rootNodes = doc.getElementsByTagName("AssistEvents");
//        Node rootNode = rootNodes.item(0);
//        Element rootElement = (Element) rootNode;
//        NodeList eventNodes = rootElement.getElementsByTagName("event");

//        //Pick random event
//        int indexOfRandomEvent = random.nextInt(eventNodes.getLength());
//        System.out.println("Random event picker: " + indexOfRandomEvent);
//        Node eventPickedNode = eventNodes.item(indexOfRandomEvent);
//        Element eventPickedElement = (Element) eventPickedNode;
//        Node theTextNode = eventPickedElement.getElementsByTagName("eventText").item(0);
//        Element textElement = (Element) theTextNode;
//        String eventText = textElement.getTextContent();
//        System.out.println("Event: " + eventText);

        //display random event
        //Temp
        JFrame frame = new JFrame();
        frame.setSize(600,300);
        JPanel panel = new JPanel();
//        JLabel eventTextLabel = new JLabel(eventText + "--\n" + chanceOfSuccess + "% Chance of winning...");
        JButton accept = new JButton("Accept");

        //Get Accept or Decline of User
        accept.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                        accepted = true;
//                        System.out.println(" option : " + accepted);
                        frame.dispose();
                    }
                }
        );
//        panel.add(eventTextLabel);
        panel.add(accept);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Temp
        System.out.println("/////////////////////////////////");
    }

    public void runEvent()
    {
        System.out.println("***************************************");
        System.out.println("Trade run Event");
        System.out.println("***************************************");
        loadEvent();
        System.out.println("***************************************");
    }

    public void debugEvent()
    {
        System.out.println("Trade Debug Event");
        System.out.println("*******************************************************");
        Node rootNode = doc.getElementsByTagName("TradeEvents").item(0);
        System.out.println(rootNode.getTextContent());
        System.out.println("*******************************************************");
    }

}
