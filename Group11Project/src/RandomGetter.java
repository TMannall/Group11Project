/**
 * @Author Aidan Lennie on 25/01/2016.
 */
import java.util.Random;

/**
 * Method for randomly generating numbers for the event system
 */
public class RandomGetter {

    private Random random;
    private int[] probabilities;
    private String[] types;
    private String[] list = new String[100];
    private String typeSelected;

    public RandomGetter(int[] probabilities, String[] types)
    {
        this.probabilities = probabilities;
        this.types = types;
        random = new Random();
        resetRandomProbabilities();
    }

    /**
     * Method for getting random types
     * @return typeSelected
     */
    public String getRandomType()
    {
        typeSelected = list[random.nextInt(100)];
        System.out.println("event type: " + typeSelected);
        return typeSelected;
    }

    /**
     * Method for resetting random probabilities for events
     */
    public void resetRandomProbabilities()
    {
        int count = 0, i = 0;
        for (String type : types)
        {
            for (int countOfType = 0; countOfType < probabilities[i]; countOfType++)
                list[count++] = type;
            i++;
        }
    }

    /**
     * Method for setting probabilies of events
     * @param probabilities
     */
    public void setProbabilities(int[] probabilities)
    {
        this.probabilities = probabilities;
        resetRandomProbabilities();
    }

    /**
     * Method for setting the type of event
     * @param types
     */
    public void setTypes(String[] types)
    {
        this.types = types;
        resetRandomProbabilities();
    }
}


