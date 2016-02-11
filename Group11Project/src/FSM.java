import java.util.ArrayList;

/**
 * Finite-State Machine
 * An instance of this should be created to manage state switches for a program.
 * States should be classes which implement the FSMState interface.
 * Note: Is not stack-based; may implement stack if needed
 */
public class FSM {
    private ArrayList<FSMState> states;         // Instances of all the states that this machine controls
    private FSMState activeState = null;

    public FSM(){
        states = new ArrayList<>();
    }

    public void run(){
        if(activeState != null)
            activeState.execute();
    }

    public void setState(FSMState state){
        activeState = state;
    }

    public FSMState getState(){
        return activeState;
    }

    public ArrayList<FSMState> getStates(){
        return states;
    }
}
