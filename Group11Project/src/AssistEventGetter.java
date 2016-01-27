/**
 * Created by Aidan on 25/01/2016.
 */
import org.jsfml.audio.Sound;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AssistEventGetter extends Events{

    private RandomGetter selector;
    private int chanceOfSuccess;
    private int chanceOfFail;
    private static Boolean accepted = false;

    public AssistEventGetter()
    {
        super("AssistEvents.xml");
    }

    public void loadEvent()
    {
        System.out.println("/////////////////////////////////");
        System.out.println("Assist Load Event");
        System.out.println("/////////////////////////////////");

        //Get List of Events
        NodeList rootNodes = doc.getElementsByTagName("AssistEvents");
        Node rootNode = rootNodes.item(0);
        Element rootElement = (Element) rootNode;
        NodeList eventNodes = rootElement.getElementsByTagName("event");

        //Pick random event
        int indexOfRandomEvent = random.nextInt(eventNodes.getLength());
        System.out.println("Random event picker: " + indexOfRandomEvent);
        Node eventPickedNode = eventNodes.item(indexOfRandomEvent);
        Element eventPickedElement = (Element) eventPickedNode;
        Node theTextNode = eventPickedElement.getElementsByTagName("eventText").item(0);
        Element textElement = (Element) theTextNode;
        String eventText = textElement.getTextContent();
        System.out.println("Event: " + eventText);

        //Has Won Or Lost?
        chanceOfSuccess = random.nextInt(100);
        chanceOfFail = 100 - chanceOfSuccess;
        selector = new RandomGetter(new int[]{chanceOfSuccess, chanceOfFail},new String[]{"won","lost"});
        String wonOrLost = selector.getRandomType();

        //display random event
        //Temp
        JFrame frame = new JFrame();
        frame.setSize(600,300);
        JPanel panel = new JPanel();
        JLabel eventTextLabel = new JLabel(eventText + "--\n" + chanceOfSuccess + "% Chance of winning...");
        JButton accept = new JButton("Accept");
        JButton decline = new JButton("Decline");
        //Get Accept or Decline of User
        accept.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        accepted = true;
                        System.out.println(" option : " + accepted);
                        frame.dispose();
                    }
                }
        );
        decline.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        accepted = false;
                        System.out.println(" option : " + accepted);
                        frame.dispose();
                    }
                }
        );
        panel.add(eventTextLabel);
        panel.add(accept);
        panel.add(decline);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Temp
        System.out.println("/////////////////////////////////");
    }

    public void runEvent()
    {
        System.out.println("***************************************");
        System.out.println("Assist run Event");
        System.out.println("***************************************");
        loadEvent();
        System.out.println("***************************************");
    }


    public void debugEvent()
    {
        System.out.println("Assist Debug Event");
        System.out.println("*******************************************************");
        Node rootNode = doc.getElementsByTagName("AssistEvents").item(0);
        System.out.println(rootNode.getTextContent());
        System.out.println("*******************************************************");
    }

}
