/**
 * Created by Rohjo on 23/01/2016.
 */
public class StateTestDriver {

    public static void main(String[] args) {
        FSM machine = new FSM();
        State1 state1 = new State1();
        State2 state2 = new State2();


        // Here's the game loop
        int i = 0;      // Counter to alternate the states, just to show how it works
        while(true){
            if(i % 2 == 0){
                machine.setState(state1);
            }
            else{
                machine.setState(state2);
            }
            machine.run();
            i++;

            // Delaying the output so it's not spamming the console
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
