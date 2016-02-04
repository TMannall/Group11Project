/**
 * Created by Aidan on 25/01/2016.
 */
import java.util.Random;

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

        public String getRandomType()
        {
            typeSelected = list[random.nextInt(100)];
            System.out.println("Getter = " + typeSelected);
            return typeSelected;
        }

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

        public void setProbabilities(int[] probabilities)
        {
            this.probabilities = probabilities;
            resetRandomProbabilities();
        }

        public void setTypes(String[] types)
        {
            this.types = types;
            resetRandomProbabilities();
        }
}


